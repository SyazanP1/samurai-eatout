package com.example.samuraieatout.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.samuraieatout.entity.Authority;
import com.example.samuraieatout.entity.LocalStripe;
import com.example.samuraieatout.entity.Member;
import com.example.samuraieatout.repository.AuthorityRepository;
import com.example.samuraieatout.repository.LocalStripeRepository;
import com.example.samuraieatout.repository.MemberRepository;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.model.Event;
import com.stripe.model.StripeObject;
import com.stripe.model.Subscription;
import com.stripe.model.checkout.Session;
import com.stripe.net.Webhook;
import com.stripe.param.checkout.SessionCreateParams;

import jakarta.servlet.http.HttpServletRequest;

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

	//	webhookはイベントごとに受信されるため、異なるイベントのデータをフィールドで保管しておく
	private String customerId = "";
	private String customerEmail = "";
	private String subscriptionId = "";

	private LocalStripeRepository localStripeRepository;
	private MemberRepository memberRepository;
	private AuthorityRepository authorityRepository;

	public StripeService(LocalStripeRepository localStripeRepository, MemberRepository memberRepository, AuthorityRepository authorityRepository) {
		this.localStripeRepository = localStripeRepository;
		this.memberRepository = memberRepository;
		this.authorityRepository = authorityRepository;
	}

	//	決済用のリンクを作成
	public String createRedirectUrl(HttpServletRequest httpServletRequest) throws StripeException {

		// Set your secret key. Remember to switch to your live secret key in production.
		// See your keys here: https://dashboard.stripe.com/apikeys
		Stripe.apiKey = stripeApiKey;
		String requestUrl = new String(httpServletRequest.getRequestURL());

		// The price ID passed from the client
		//   String priceId = request.queryParams("priceId");
		//	String priceId = "{{PRICE_ID}}";

		SessionCreateParams params = new SessionCreateParams.Builder()
				.setSuccessUrl(requestUrl.replaceAll("/paidMember", "") + "/login")
				.setCancelUrl("https://example.com/canceled.html")
				.setMode(SessionCreateParams.Mode.SUBSCRIPTION)
				.addLineItem(new SessionCreateParams.LineItem.Builder()
						// For metered billing, do not pass quantity
						.setQuantity(1L)
						.setPrice(stripeFeeId)
						.build())
				.build();

		Session session = Session.create(params);
		String url = session.getUrl();

		// Redirect to the URL returned on the Checkout Session.
		// With Spark, you can redirect with:
		//   response.redirect(session.getUrl(), 303);
		//   return "";

		return url;
	}

	//	支払い情報編集用のリンクを作成
	//	参考サイト
	//	https://docs.stripe.com/customer-management/integrate-customer-portal?shell=true&api=true&resource=billing_portal%20sessions&action=create&architecture-style=resources&lang=java#redirect
	public String createEditPaidUrl(Member member, HttpServletRequest httpServletRequest) throws StripeException {
		Stripe.apiKey = stripeApiKey;
		String requestUrl = new String(httpServletRequest.getRequestURL());
		LocalStripe localStripe = localStripeRepository.findByMember(member);
		//		String requestUrl = new String(httpServletRequest.getRequestURL());

		//	import文に記述するとエラー「インポート com.stripe.param.billingportal.SessionCreateParams は、別の import 文と一致しません」が発生
		//	そのため、パッケージも指定する完全修飾クラス名で記述している
		com.stripe.param.billingportal.SessionCreateParams params = new com.stripe.param.billingportal.SessionCreateParams.Builder()
				//	テスト用に固定値を入れている
				//				.setCustomer("cus_SF2y5SPZAWZl9Q")
				.setCustomer(localStripe.getCustomerId())
				.setReturnUrl(requestUrl.replaceAll("/editMember", "") + "/home")
				.build();

		com.stripe.model.billingportal.Session session = com.stripe.model.billingportal.Session.create(params);

		return session.getUrl();
	}

	//	webhookを受けて、顧客（Customer）情報を取得
	//	参考 https://terakoya.sejuku.net/question/detail/50343
	public void obtainCustomer(String payload, String sigHeader) throws StripeException {
		Stripe.apiKey = stripeApiKey;
		Event event = null;

		//		try {
		//			event = Webhook.constructEvent(payload, sigHeader, webhookSecret);
		//		} catch (SignatureVerificationException e) {
		//			return HttpStatus.BAD_REQUEST;
		//		}

		event = Webhook.constructEvent(payload, sigHeader, webhookSecret);

		if ("customer.created".equals(event.getType())) {

			//	顧客情報を取得
			StripeObject stripeObject = event.getDataObjectDeserializer().getObject().get();
			Customer customer = (Customer) stripeObject;

			customerId = customer.getId();
			customerEmail = customer.getEmail();
		}

	}

	//	webhookを受けて、サブスクリプション（Subscription）情報を取得
	public void obtainSubscription(String payload, String sigHeader) throws StripeException {
		Stripe.apiKey = stripeApiKey;
		Event event = null;

		event = Webhook.constructEvent(payload, sigHeader, webhookSecret);

		if ("customer.subscription.created".equals(event.getType())) {
			//	サブスクリプション情報を取得

			//			StripeObject stripeObject = event.getDataObjectDeserializer().getObject().get();
			//			Subscription subscription = (Subscription) stripeObject;
			Subscription subscription = (Subscription) event.getDataObjectDeserializer().getObject().orElse(null);
			subscriptionId = subscription.getId();
		}

	}

	//	webhookで取得した情報をローカルに保存
	public void saveStripeInfo() {
		//	各webhookイベントで得た値をフィールドで保管できた状態でのみ処理が行われる
		if (!customerId.isEmpty() && !customerEmail.isEmpty() && !subscriptionId.isEmpty()) {

			Member member = memberRepository.findByEmail(customerEmail);
			
			//	会員権限を有料会員に更新
			updatePaidAuthority(member);

			//	Stripe情報をローカルに保存
			LocalStripe localStripe = new LocalStripe();
			localStripe.setMember(member);
			localStripe.setCustomerId(customerId);
			localStripe.setSubscriptionId(subscriptionId);

			localStripeRepository.save(localStripe);

			//	次回以降の処理で使用する可能性があるため、空文字で初期化しておく
			customerId = "";
			customerEmail = "";
			subscriptionId = "";
		}
	}

	//	会員権限を有料に更新
	public void updatePaidAuthority(Member member) {
		Authority authority = authorityRepository.getReferenceById(2);
		member.setAuthority(authority);
		memberRepository.save(member);
	}
}
