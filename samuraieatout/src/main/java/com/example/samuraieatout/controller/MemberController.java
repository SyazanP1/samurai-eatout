package com.example.samuraieatout.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.samuraieatout.form.SignupForm;
import com.example.samuraieatout.service.CertificationService;
import com.example.samuraieatout.service.MemberService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class MemberController {
	private final MemberService memberService;
	private final CertificationService certificationService;
	
	public MemberController(MemberService memberService, CertificationService certificationService) {
		this.memberService = memberService;
		this.certificationService = certificationService;
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
		bindingResult = memberService.addErrorBindingResult(bindingResult, signupForm);
		
		//	バリデーションエラーがあればフォームを再表示
		if(bindingResult.hasErrors()) {
			return "member/signup";
		}
		
		//	認証メール送信イベントを発行
		String sendMailMessage = memberService.publishCertificateMailEvent(signupForm, httpServletRequest);
		redirectAttributes.addFlashAttribute("sendMailMessage", sendMailMessage);
		
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

}
