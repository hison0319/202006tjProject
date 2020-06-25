package member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("singup")
public class SignupController {
	// 회원가입 창 이동
	public String signupForm() {
		return "";
	}

	// 회원가입 기능 구현
	public String signupService() {
		return "";
	}
}
