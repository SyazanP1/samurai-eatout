package com.example.samuraieatout.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.samuraieatout.entity.Member;
import com.example.samuraieatout.service.AdminMemberService;

@Controller
@RequestMapping("/admin/member")
public class AdminMemberController {
	private final AdminMemberService adminMemberService;
	
	public AdminMemberController(AdminMemberService adminMemberService) {
		this.adminMemberService = adminMemberService;
	}

	@GetMapping
	public String showMember(@RequestParam(name = "keywordName", required = false) String keywordName, Model model,
			@PageableDefault(page = 0, size = 10, sort = "id", direction = Direction.ASC) Pageable pageable) {
		
		Page<Member> pageMember = adminMemberService.obtainMember(keywordName, pageable);
		model.addAttribute("pageMember", pageMember);
		
		return "admin/member/home";
	}
}
