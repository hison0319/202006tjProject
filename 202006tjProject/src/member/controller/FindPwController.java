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
	public String loginService(HttpSession session, Model m, String memberId,String email) {
		if(memberService.selectMemberPwByIDEmail(memberId,email)==null) {
			return "";
		}  //이메일 틀릴 경우
		
		String loginMemberPw = memberService.selectMemberPwByIDEmail(memberId,email);  //로그인 한 멤버의 이메일 가져옴
		System.out.println(loginMemberPw);
		return loginMemberPw;
		
	}
	
	@GetMapping("complete")
	public String complete() {
		return "member/findPwComplete";
	}

}
