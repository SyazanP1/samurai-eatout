package com.example.samuraieatout.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.samuraieatout.entity.Authority;
import com.example.samuraieatout.entity.LocalStripe;
import com.example.samuraieatout.entity.Member;
import com.example.samuraieatout.repository.AuthorityRepository;
import com.example.samuraieatout.repository.LocalStripeRepository;
import com.example.samuraieatout.repository.MemberRepository;
import com.example.samuraieatout.security.UserDetailsImpl;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.Event;
import com.stripe.model.Subscription;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import com.stripe.param.checkout.SessionCreateParams;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;

@Service
public class StripeService {
	@Value("${stripe.api-key}")
	private String stripeApiKey;

	//	@Value("${stripe.product-id}")
	//	private String stripePruductId;

	@Value("${stripe.fee-id}")
	private String stripeFeeId;

	@Value("${stripe.webhook-secret}")
	private String webhookSecret;

	private LocalStripeRepository localStripeRepository;
	private MemberRepository memberRepository;
	private AuthorityRepository authorityRepository;
	private AuthService authService;

	public StripeService(LocalStripeRepository localStripeRepository, MemberRepository memberRepository,
			AuthorityRepository authorityRepository, AuthService authService) {
		this.localStripeRepository = localStripeRepository;
		this.memberRepository = memberRepository;
		this.authorityRepository = authorityRepository;
		this.authService = authService;
	}

	//	決済用のリンクを作成
	public String createRedirectUrl(HttpServletRequest httpServletRequest, UserDetailsImpl userDetailsImpl)
			throws StripeException {

		Stripe.apiKey = stripeApiKey;
		String requestUrl = new String(httpServletRequest.getRequestURL());
		String memberId = userDetailsImpl.getMember().getId().toString();

		SessionCreateParams params = new SessionCreateParams.Builder()
				.setSuccessUrl(requestUrl.replaceAll("/paidMember", "") + "/home")
				.setCancelUrl(requestUrl.replaceAll("/paidMember", "") + "/home")
				.setMode(SessionCreateParams.Mode.SUBSCRIPTION)
				.addLineItem(new SessionCreateParams.LineItem.Builder()
						.setQuantity(1L)
						.setPrice(stripeFeeId)
						.build())
				//				.putMetadata("Members_id", memberId)
				.setSubscriptionData(
						SessionCreateParams.SubscriptionData.builder()
								.putMetadata("Members_id", memberId)
								.build())
				.build();

		Session session = Session.create(params);
		String url = session.getUrl();

		return url;
	}

	//	支払い情報編集用のリンクを作成
	//	参考サイト
	//	https://docs.stripe.com/customer-management/integrate-customer-portal?shell=true&api=true&resource=billing_portal%20sessions&action=create&architecture-style=resources&lang=java#redirect
	public String createEditPaidUrl(Member member, HttpServletRequest httpServletRequest) throws StripeException {
		Stripe.apiKey = stripeApiKey;
		String requestUrl = new String(httpServletRequest.getRequestURL());
		String setUrl = "";
		if (requestUrl.contains("edit")) {
			setUrl = requestUrl.replaceAll("/editMember", "") + "/home";
		} else {
			setUrl = requestUrl.replaceAll("/updateMember", "") + "/home";
		}
		
		LocalStripe localStripe = localStripeRepository.findByMember(member);

		//	import文に記述するとエラー「インポート com.stripe.param.billingportal.SessionCreateParams は、別の import 文と一致しません」が発生
		//	そのため、パッケージも指定する完全修飾クラス名で記述している
		com.stripe.param.billingportal.SessionCreateParams params = new com.stripe.param.billingportal.SessionCreateParams.Builder()
				//	テスト用に固定値を入れている
				//				.setCustomer("cus_SF2y5SPZAWZl9Q")
				.setCustomer(localStripe.getCustomerId())
//				.setReturnUrl(requestUrl.replaceAll("/editMember", "") + "/home")
				.setReturnUrl(setUrl)
				.build();

		com.stripe.model.billingportal.Session session = com.stripe.model.billingportal.Session.create(params);

		return session.getUrl();
	}

	//	サブスク登録時のwebhookを受けて、Stripe情報を取得
	@Transactional
	public void gainSubscribeWebhook(String payload, String sigHeader) throws StripeException {
		Stripe.apiKey = stripeApiKey;
		Event event = null;

		event = Webhook.constructEvent(payload, sigHeader, webhookSecret);

		if ("customer.subscription.created".equals(event.getType())) {

			Subscription subscription = (Subscription) event.getDataObjectDeserializer().getObject().orElse(null);
			String subscriptionId = subscription.getId();

			String customerId = subscription.getCustomer();
			Customer customer = Customer.retrieve(customerId);

			Map<String, String> mapMemberId = subscription.getMetadata();
			Integer memberId = Integer.parseInt(mapMemberId.get("Members_id"));
			Member member = memberRepository.getReferenceById(memberId);

			//	権限を有料会員に更新
			//			updatePaidAuthority(memberRepository.findByEmail(customerEmail));
			updatePaidAuthority(member);
			

			Member updatedMember = memberRepository.getReferenceById(memberId);
			//	SecurityContextを更新
			authService.updateSecurityContext(updatedMember);

			//	Stripe情報をローカルに保存
			//			addLocalStripeRecord(subscriptionId, customerId, memberRepository.findByEmail(customerEmail));
			addLocalStripeRecord(subscriptionId, customerId, member);

		}

	}

	//	サブスク解約期日にwebhookを受け、無料会員への変更、及び、ローカルStripe情報を無効化する
	@Transactional
	public void gainCancelWebhook(String payload, String sigHeader)
			throws StripeException {
		Stripe.apiKey = stripeApiKey;
		Event event = null;

		event = Webhook.constructEvent(payload, sigHeader, webhookSecret);

		if ("customer.subscription.deleted".equals(event.getType())) {

			Subscription subscription = (Subscription) event.getDataObjectDeserializer().getObject().orElse(null);
			Map<String, String> mapMemberId = subscription.getMetadata();
			Integer memberId = Integer.parseInt(mapMemberId.get("Members_id"));
			Member member = memberRepository.getReferenceById(memberId);
			updateFreeAuthority(member);
			disableSubscription(member);
			
			//	SecurityContextを更新
			authService.updateSecurityContext(member);
		}

	}

	//	webhookで取得した情報をローカルに保存
	@Transactional
	public void addLocalStripeRecord(String subscriptionId, String customerId, Member member) {

		//	Stripe情報をローカルに保存
		LocalStripe localStripe = new LocalStripe();
		localStripe.setMember(member);
		localStripe.setCustomerId(customerId);
		localStripe.setSubscriptionId(subscriptionId);
		localStripe.setEnable(true);

		localStripeRepository.save(localStripe);
	}

	//	会員権限を有料に更新
	@Transactional
	public void updatePaidAuthority(Member member) {
		Authority authority = authorityRepository.getReferenceById(2);
		member.setAuthority(authority);
		memberRepository.save(member);
	}

	//	会員権限を無料に更新
	@Transactional
	public void updateFreeAuthority(Member member) {
		Authority authority = authorityRepository.getReferenceById(1);
		member.setAuthority(authority);
		memberRepository.save(member);

	}
	
	//	ローカルStripeを無効化
	@Transactional
	public void disableSubscription(Member member) {
		
		LocalStripe localStripe = localStripeRepository.findByMember(member);
		localStripe.setEnable(false);
		localStripeRepository.save(localStripe);
	}
}
