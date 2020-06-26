package member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import member.dto.MemberDto;
import member.service.MemberService;

@Controller
@RequestMapping("/signup")
public class SignupController {
	@Autowired
	MemberService memberService;
	
	@GetMapping("/form")
	public String form() {
		return "member/signupForm";
	}
	
	@PostMapping("/insert")
	public String insert(MemberDto memberDto) {
		memberService.insertMember(memberDto);  //받아온 정보를 DB에 insert
		return "member/signupComplete";
	}
}
