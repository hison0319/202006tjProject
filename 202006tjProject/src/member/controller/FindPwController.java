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
import member.service.MailService;
import member.service.MemberService;

@Controller
@RequestMapping("findpw")
public class FindPwController {
	@Autowired
	MemberService memberService;
	@Autowired
	MailService mailService;
	
	//비밀번호 찾기 창 이동
	@GetMapping("form")
	public String loginForm() {
		return "member/findPwForm";
	}
	
	//비밀번호 찾기 기능 구현
	@ResponseBody
	@PostMapping("findingpw")
	public String loginService(HttpSession session, Model m, String memberId, String email) {
		if(memberService.selectMemberInfoByIDEmail(memberId,email)==null) {
			return "";
		}  //이메일 틀릴 경우
		
		try {
			mailService.findPwSendMail(memberId, email);;
		} catch (Exception e) {
			return "";
		}
		return "f";
		
	}
	
	@GetMapping("complete")
	public String complete() {
		return "member/findPwComplete";
	}

}
