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
@RequestMapping("findpw")
public class FindPwController {
	@Autowired
	MemberService memberService;

	
	//비밀번호 찾기 창 이동
	@GetMapping("form")
	public String loginForm() {
		return "member/findPwForm";
	}
	
	//아이디 찾기 기능 구현
	@ResponseBody
	@PostMapping("findingpw")
	public String loginService(HttpSession session, Model m, MemberDto memberDto) {
		if(memberService.selectMemberByMemberId(memberDto.getEmail())==null) {
			return null;
		}  //이메일 틀릴 경우
		else {
			MemberDto loginMember = memberService.selectMemberByMemberId(memberDto.getMemberId());  //로그인 한 멤버의 id숫자 가져옴
				session.setAttribute("loginMember", loginMember);
				System.out.println(loginMember);
				return "t";
		}
	}
	
	@GetMapping("complete")
	public String complete() {
		return "member/findPwComplete";
	}

}
