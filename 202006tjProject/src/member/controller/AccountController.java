package member.controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import member.dto.MemberDto;
import member.dto.MemberVO;
import member.dto.MemberVOForAPI;
import member.service.MemberService;

@Controller
@RequestMapping("/account")
public class AccountController {
	@Autowired
	MemberService memberService;

	// 회원정보 리스트 조회 기능
	@GetMapping("/showInfo")
	public String memberInfoListShow() {
		return "/account/showInfo";
	}

	// 회원정보 수정 기능-비동기?
	@GetMapping("/confirmP")
	public String memberConfirmPasswordForm() {
		return "/account/confirmPassword";
	}

	@ResponseBody
	@PostMapping("/confirmP")
	public String memberConfirmPassword(HttpSession session, String passwordC) {
		MemberDto member = (MemberDto) session.getAttribute("loginMember");
		if (member.getPassword().equals(passwordC)) {
			return "t";
		} else {
			return "f";
		}
	}

	// 회원 정보 수정
	@GetMapping("/update")
	public String memberModifyForm() {
		return "/account/modify";
	}

	@PostMapping("/update")
	public String memberModify(HttpSession session, @ModelAttribute("MemberVo") @Valid MemberVO memberVo,
			BindingResult result, Model m) {
		MemberDto loginMember = (MemberDto) session.getAttribute("loginMember");
		MemberDto changeMember = new MemberDto(memberVo.getMemberId(), memberVo.getPassword(), memberVo.getEmail(),
				memberVo.getPhone(), memberVo.getAddress());
		// 생성자에 없는 id값을 넣어줌
		changeMember.setId(memberVo.getId());
		// 만약 메일을 바꾸면 인증을 취소함.
		if (loginMember.getEmail().equals(changeMember.getEmail())) {
			changeMember.setCertified(loginMember.getCertified());
		} else {
			changeMember.setCertified(0);
		}
		// 유효성 검사
		if (result.hasErrors()) {
			List<FieldError> errors = result.getFieldErrors();
			for (FieldError fe : errors) {
				m.addAttribute("e" + fe.getField(), fe.getField());
			}
			return "account/modify";
		} else {
			try {
				memberService.updateMember(changeMember);
			} catch (Exception e) {
				return "account/modify";
			}
		}
		// 마지막 값(regDate)을 채워줌
		changeMember.setRegDate(loginMember.getRegDate());
		// 로그인 된 맴버의 정보를 최종 변경
		session.setAttribute("loginMember", changeMember);
		return "account/modifyComplete";
	}

	// 비동기 이메일, 폰번호 검사
	@ResponseBody
	@PostMapping("/confirmEmail")
	public String confirmEmail(HttpSession session, String email) {
		MemberDto member = (MemberDto) session.getAttribute("loginMember");
		if (member.getEmail().equals(email)) {
			return "s";
		} else {
			if (memberService.selectMemberByEmail(email) == null) {
				return "t";
			} else {
				return "f";
			}
		}
	}

	@ResponseBody
	@PostMapping("/confirmPhone")
	public String confirmPhone(HttpSession session, String phone) {
		MemberDto member = (MemberDto) session.getAttribute("loginMember");
		if (member.getPhone().equals(phone)) {
			return "s";
		} else {
			if (memberService.selectMemberByPhone(phone) == null) {
				return "t";
			} else {
				return "f";
			}
		}
	}

	@GetMapping("/forAPIupdate")
	public String APIModifyForm() {
		return "/account/modifyAPI";
	}

	@PostMapping("forAPIupdate")
	public String APIModify(@ModelAttribute("MemberVo") @Valid MemberVOForAPI memberVo, BindingResult result, Model m,
			HttpSession session) {
		MemberDto member = new MemberDto();
		MemberDto loginMember = (MemberDto) session.getAttribute("loginMember");
		MemberDto changeMember = new MemberDto(memberVo.getMemberId(), memberVo.getPassword(), memberVo.getEmail(),
				memberVo.getPhone(), memberVo.getAddress());
		changeMember.setId(memberVo.getId());
		if (loginMember.getEmail().equals(changeMember.getEmail())) {
			changeMember.setCertified(loginMember.getCertified());
		} else {
			changeMember.setCertified(0);
		}
		;
		if (result.hasErrors()) {
			List<FieldError> errors = result.getFieldErrors();
			System.out.println(result.toString());
			for (FieldError fe : errors) {
				m.addAttribute("e" + fe.getField(), fe.getField());
				System.out.println(fe.getField());
			}
			m.addAttribute("member", member);
			return "account/modifyAPI";
		} else {
			try {
				memberService.updateMember(changeMember);
			} catch (Exception e) {
				m.addAttribute("member", member);
				return "account/modifyAPI";
			}
		}
		changeMember.setRegDate(loginMember.getRegDate());
		session.setAttribute("loginMember", changeMember);
		return "account/modifyComplete";
	}

	// 공유한 회원목록 조회기능
	public String sharingMemberListShow() {
		return "";
	}
	// 공유한 회원 정보 조회 기능-비동기?
}
