package member.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.JsonNode;

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
				return "p";
			}  //아이디는 맞고 비밀번호를 틀릴 경우
			else {
				MemberDto loginMember = memberService.selectMemberByMemberId(memberDto.getMemberId());  //로그인 한 멤버의 id숫자 가져옴
				session.setAttribute("loginMember", loginMember);
				return "t";
			}
		}
	}
	//로그인 완료 페이지 이동
	@GetMapping("complete")
	public String complete() {
		return "member/loginComplete";
	}
	//로그아웃 기능 구현
	@GetMapping("logout")
	public String logout(HttpSession session) {
		session.removeAttribute("loginMember");  //세션에서 로그인 정보 삭제
		if(session.getAttribute("access_token") != null) {
			JsonNode accessToken = (JsonNode) session.getAttribute("access_token");
			session.removeAttribute("access_token");
		}
		return "member/logout";
	}
}
