package member.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import member.dto.MemberDto;
import member.service.MemberService;

@Controller
@RequestMapping("login")
public class LoginController {
	@Autowired
	MemberService memberService;
	
	//로그인 창 이동
	@GetMapping("form")
	public String loginForm() {
		return "member/loginForm";
	}
	
	//로그인 기능 구현
	@ResponseBody
	@PostMapping("matching")
	public String loginService(HttpSession session, Model m, MemberDto memberDto) {
		if(memberService.selectMemberByMemberId(memberDto.getMemberId())==null) {
			return null;
		}  //아이디를 틀릴 경우
		else {
			if(memberService.selectMemberByMemberIdPw(memberDto.getMemberId(), memberDto.getPassword())==null) {
				System.out.println(memberService.selectMemberByMemberId(memberDto.getMemberId()));
				return "p";
			}  //아이디는 맞고 비밀번호를 틀릴 경우
			else {
				MemberDto loginMember = memberService.selectMemberByMemberId(memberDto.getMemberId());  //로그인 한 멤버의 id숫자 가져옴
				System.out.println(loginMember);
				System.out.println(loginMember.getId());
				session.setAttribute("loginMember", loginMember);
				return "t";
			}
		}
	}
	
	@GetMapping("complete")
	public String complete() {
		return "member/loginComplete";
	}
}
