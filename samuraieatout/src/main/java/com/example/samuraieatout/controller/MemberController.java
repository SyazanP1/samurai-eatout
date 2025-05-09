package com.example.samuraieatout.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.samuraieatout.entity.Member;
import com.example.samuraieatout.form.EditMemberForm;
import com.example.samuraieatout.form.ResetPasswordForm;
import com.example.samuraieatout.form.ResetPasswordSendEmailForm;
import com.example.samuraieatout.form.SignupForm;
import com.example.samuraieatout.security.UserDetailsImpl;
import com.example.samuraieatout.service.CertificationService;
import com.example.samuraieatout.service.MemberService;
import com.example.samuraieatout.service.StripeService;
import com.stripe.exception.StripeException;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class MemberController {
	private final MemberService memberService;
	private final CertificationService certificationService;
	private final StripeService stripeService;

	public MemberController(MemberService memberService, CertificationService certificationService,
			StripeService stripeService) {
		this.memberService = memberService;
		this.certificationService = certificationService;
		this.stripeService = stripeService;
	}

	@GetMapping("/signup")
	public String signup(Model model) {
		model.addAttribute("signupForm", new SignupForm());
		return "member/signup";
	}

	@PostMapping("/signup")
	public String signup(@ModelAttribute @Validated SignupForm signupForm,
			BindingResult bindingResult,
			Model model,
			RedirectAttributes redirectAttributes,
			HttpServletRequest httpServletRequest) {

		//	メールアドレス重複、パスワード不一致の場合エラーを追加
		bindingResult = memberService.addErrorBindingResult(bindingResult, signupForm.getEmail(),
				signupForm.getPassword(), signupForm.getPasswordConfirm());

		//	バリデーションエラーがあればフォームを再表示
		if (bindingResult.hasErrors()) {
			return "member/signup";
		}

		Member member = memberService.registSignup(signupForm.getEmail(), signupForm.getPassword(),
				signupForm.getName());

		//	認証メール送信イベントを発行
		memberService.publishCertificateMailEvent(member, httpServletRequest);
		redirectAttributes.addFlashAttribute("sendMailMessage", "認証メールを送信しました。メールに記載されたURLにアクセスして、会員登録を完了させてください。");

		return "redirect:/login";
	}

	//	メールで送信した認証用のURLに対して
	@GetMapping("/signup/verify")
	public String verify(@RequestParam(name = "token") String token, Model model) {

		Boolean orRegist = certificationService.completeRegistMember(token);

		if (orRegist) {
			model.addAttribute("successMessage", "会員登録が完了しました");
		} else {
			model.addAttribute("errorMessage", "トークンが無効です");
		}

		return "login";
	}

	//	決済処理Stripeへ遷移させる前の中継ぎ画面
	@GetMapping("/paidMember")
	public String reliefForStripe(HttpServletRequest httpServletRequest, Model model) throws StripeException {
		String stripeUrl = stripeService.createRedirectUrl(httpServletRequest);

		model.addAttribute("stripeUrl", stripeUrl);
		return "member/paidMember";
	}

	//	決済処理終了後のwebhookに対して
	@PostMapping("/stripe/webhook")
	public ResponseEntity<Void> webhook(@RequestBody String payload,
			@RequestHeader("Stripe-Signature") String sigHeader) throws StripeException {

		stripeService.obtainCustomer(payload, sigHeader);
		stripeService.obtainSubscription(payload, sigHeader);

		stripeService.saveStripeInfo();

		return ResponseEntity.ok().build();
	}

	//	会員基本情報編集画面
	@GetMapping("/editMember")
	public String showEditPaid(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl,
			HttpServletRequest httpServletRequest, Model model) throws StripeException {
		//	Stripeの決済情報編集用ポータルサイトリンクを作成
		Member member = userDetailsImpl.getMember();
		String stripeUrl = stripeService.createEditPaidUrl(member, httpServletRequest);

		EditMemberForm editMemberForm = new EditMemberForm(member.getId(), member.getName(), member.getEmail());

		model.addAttribute("stripeUrl", stripeUrl);
		model.addAttribute("editMemberForm", editMemberForm);
		return "member/editMember";
	}

	@PostMapping("/updateMember")
	public String updateMember(@ModelAttribute @Validated EditMemberForm editMemberForm,
			BindingResult bindingResult,
			Model model,
			RedirectAttributes redirectAttributes,
			HttpServletRequest httpServletRequest) {

		//	メールアドレス重複、パスワード不一致の場合エラーを追加
		bindingResult = memberService.addErrorBindingResult(bindingResult, editMemberForm.getEmail(),
				editMemberForm.getPassword(), editMemberForm.getPasswordConfirm());

		//	バリデーションエラーがあればフォームを再表示
		if (bindingResult.hasErrors()) {
			return "member/editMember";
		}
		//		Member member = memberService.registSignup(editMemberForm.getEmail(), editMemberForm.getPassword(),
		//				editMemberForm.getName());
		Member member = memberService.obtainMember(editMemberForm.getId());
		memberService.updateMember(member, editMemberForm);

		if (memberService.isEmailChange(member.getEmail(), editMemberForm.getEmail())) {

			//	認証メール送信イベントを発行
			//			memberService.publishCertificateMailEvent(member, httpServletRequest);
			//			redirectAttributes.addFlashAttribute("sendMailMessage",
			//					"認証メールを送信しました。メールに記載されたURLにアクセスして、メールアドレスの変更を完了してください。");

		}

		return "redirect:/login";

	}

	//	@GetMapping("/updateMember/verify")
	//	public String verifyUpdateMember(@RequestParam(name = "token") String token, Model model) {
	//
	//		Boolean orRegist = certificationService.completeRegistMember(token);
	//
	//		if (orRegist) {
	//			model.addAttribute("successMessage", "メールアドレスの変更が完了しました");
	//		} else {
	//			model.addAttribute("errorMessage", "トークンが無効です");
	//		}
	//
	//		return "login";
	//	}

	//	パスワードリセット 画面
	@GetMapping("/member/resetPassword")
	public String resetPassword(Model model) {
		//	メールアドレスを入力させて、それ宛にメールを送信する
		ResetPasswordSendEmailForm resetPasswordSendEmailForm = new ResetPasswordSendEmailForm();
		model.addAttribute("resetPasswordSendEmailForm", resetPasswordSendEmailForm);

		return "member/resetPasswordSendEmail";
	}

	//	パスワードリセット　メール送信
	@PostMapping("/member/resetPassword")
	public String sendResetPasswordEmail(
			@ModelAttribute @Validated ResetPasswordSendEmailForm resetPasswordSendEmailForm,
			BindingResult bindingResult,
			HttpServletRequest httpServletRequest,
			RedirectAttributes redirectAttributes) {

		String email = resetPasswordSendEmailForm.getEmail();
		bindingResult = memberService.addErrorBindingResultForResetPassword(bindingResult, email);

		// 	エラーチェック
		if (bindingResult.hasErrors()) {

			return "member/resetPasswordSendEmail";
		}

		Member member = memberService.obtainMemberFromEmail(email);
		memberService.publishResetMailEvent(member, httpServletRequest);
		redirectAttributes.addFlashAttribute("successSendEmail", "パスワード再設定用メールを送信しました。");

		return "redirect:/login";
	}

	//	パスワードリセット メールに記載されたURLをクリック
	@GetMapping("/member/resetPassword/verify")
	public String inputPassword(@RequestParam(name = "token") String token, Model model,
			RedirectAttributes redirectAttributes) {

		Member member = certificationService.obtainMemberFromToken(token);

		//	URLが有効であればフォームを表示
		if (member != null) {
			ResetPasswordForm resetPasswordForm = new ResetPasswordForm(member);
			model.addAttribute("resetPasswordForm", resetPasswordForm);
			return "member/resetPassword";

		} else {
			redirectAttributes.addFlashAttribute("errorResetPasswordMessage", "パスワードリセット用のURLが有効ではありません。");
			return "redirect:/login";
		}

	}

	//	パスワードリセット 新パスワードに更新
	@PostMapping("/member/resetPassword/updatePassword")
	public String updatePassword(@ModelAttribute @Validated ResetPasswordForm resetPasswordForm,
			BindingResult bindingResult, UserDetailsImpl userDetailsImpl,
			RedirectAttributes redirectAttributes) {

		bindingResult = memberService.addErrorBindingResult(bindingResult, null, resetPasswordForm.getPassword(),
				resetPasswordForm.getPasswordConfirm());

		//	バリデーションの追加が必要
		if (bindingResult.hasErrors()) {

			return "/member/resetPassword";
		}

		memberService.updateResetPassword(userDetailsImpl.getMember(), resetPasswordForm.getPassword());
		redirectAttributes.addFlashAttribute("successResetPassword", "パスワードが再設定されました。ログインをお試しください");

		return "redirect:/login";
	}

}
