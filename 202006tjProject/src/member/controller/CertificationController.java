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
	
	@GetMapping("/form")
	public String certifyForm() {
		return "account/certificationForm";
	}
	@ResponseBody
	@PostMapping("/form")
	public String getCertify(HttpSession session) {
		MemberDto member = (MemberDto) session.getAttribute("loginMember");
		try {
			mailService.create(session, member);
		} catch (Exception e) {
			return "f";
		}
		return "t";
	}
	@GetMapping("/confirm")
	public String getConfirm(Model m, String certifyKey) {
		m.addAttribute("certifyKey",certifyKey);
		return "account/certification";
	}
	@ResponseBody
	@PostMapping("/confirm")
	public boolean PostConfirm(HttpSession session, String certifyKey, String memberKey) {
		try {
			System.out.println("aaa");
			return mailService.memberCertify(session, certifyKey, memberKey);
		} catch (Exception e) {
			return false;
		}
	}
}
