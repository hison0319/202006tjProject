package member.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("account")
public class AccountController {
	//회원정보 리스트 조회 기능
	@GetMapping("showMemberInfo")
	public String memberInfoListShow() {
		return "/account/accountInfo";
	}
	//회원정보 수정 기능-비동기?
	//공유한 회원목록 조회기능
	public String sharingMemberListShow() {
		return "";
	}
	//공유한 회원 정보 조회 기능-비동기?
}
