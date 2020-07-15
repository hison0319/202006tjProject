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

@Controller
@RequestMapping("/certify")
public class CertificationController {
	@Autowired
	MailService mailService;
	
	//인증 메일 전송 폼 이동
	@GetMapping("/form")
	public String certifyForm() {
		return "account/certificationForm";
	}
	//인증 메일 전송. 비동기
	@ResponseBody
	@PostMapping("/form")
	public String getCertify(HttpSession session) {
		MemberDto member = (MemberDto) session.getAttribute("loginMember");
		try {
			mailService.certifySendMail(session, member);
		} catch (Exception e) {
			return "f";
		}
		return "t";
	}
	//인증 페이지 이동
	@GetMapping("/confirm")
	public String getConfirm(Model m, String certifyKey) {
		m.addAttribute("certifyKey",certifyKey);
		return "account/certification";
	}
	//인증 확인 기능
	@ResponseBody
	@PostMapping("/confirm")
	public boolean PostConfirm(HttpSession session, String certifyKey, String memberKey) {
		try {
			//사용자가 입력한 인증키와 발급된 인증키를 확인. 동일 브라우져 내에서만 확인 가능.
			return mailService.memberCertify(session, certifyKey, memberKey);
		} catch (Exception e) {
			return false;
		}
	}
}
