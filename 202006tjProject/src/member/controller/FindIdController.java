package member.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import member.service.MemberService;

@Controller
@RequestMapping("findid")
public class FindIdController {
	@Autowired
	MemberService memberService;

	
	//아이디 찾기 창 이동
	@GetMapping("form")
	public String loginForm() {
		return "member/findIdForm";
	}
	
	//아이디 찾기 기능 구현
	@ResponseBody
	@PostMapping("findingid")
	public String loginService(HttpSession session, Model m, String email) {
		if(memberService.selectMemberIdByEmail(email)==null) {
			return "";
		}  //이메일 틀릴 경우
		
		String loginMemberId = memberService.selectMemberIdByEmail(email);  //로그인 한 멤버의 이메일 가져옴
		return loginMemberId;
		
	}
	
	@GetMapping("complete")
	public String complete() {
		return "member/findIdComplete";
	}

}
