package member.controller;

import java.util.List;

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

import member.dto.MemberDto;
import member.dto.MemberVO;
import member.service.MemberService;

@Controller
@RequestMapping("/signup")
public class SignupController {
	@Autowired
	MemberService memberService;

	@GetMapping("/form")
	public String form() {
		return "member/signupForm";
	}

	@PostMapping("/insert")
	public String insert(@ModelAttribute("MemberVo") @Valid MemberVO memberVo, BindingResult result, Model m) {
		System.out.println(memberVo);
		MemberDto member = new MemberDto(memberVo.getMemberId(), memberVo.getPassword(), 
				memberVo.getEmail(), memberVo.getPhone(), memberVo.getAddress());
		// 유효성 검사
		if(result.hasErrors()) {
			List<FieldError> errors = result.getFieldErrors();
			for(FieldError fe : errors) {
				m.addAttribute("e"+fe.getField(), fe.getField());
			}
			m.addAttribute("member",member);
			System.out.println(member);
			return "member/signupForm";
		} else {
			System.out.println(member);
			memberService.insertMember(member);
		}
		return "member/signupComplete";
	}
	
	//정규식 기본 설명
/*
 * public String stringReplace(String str) {
		String match = "[^\uAC00-\uD7A3xfe0-9a-zA-Z\\s]";
		str = str.replaceAll(match, "");
		return str;
	한글유니코드(\uAC00-\uD7A3), 숫자 0~9(0-9), 
	영어 소문자a~z(a-z), 대문자A~Z(A-Z), 공백(\s)를 
	제외한(^) 단어일 경우 체크
	}
 * */
}
