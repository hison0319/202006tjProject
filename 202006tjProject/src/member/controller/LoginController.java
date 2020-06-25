package member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("login")
public class LoginController {
	//로그인 창 이동
	public String loginForm() {
		return "";
	}
	//로그인 기능 구현
	public String loginService() {
		return "";
	}
}
