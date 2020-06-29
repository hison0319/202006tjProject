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
import org.springframework.web.bind.annotation.ResponseBody;

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
				System.out.println(fe.getField());
			}
			m.addAttribute("member",member);
			return "member/signupForm";
		} else {
			try {
				memberService.insertMember(member);
			} catch (Exception e) {
				m.addAttribute("member",member);
				return "member/signupForm";
			}
		}
		return "member/signupComplete";
	}
	
	//비동기 식 아이디, 이메일, 번호 중복 확인
	@ResponseBody
	@PostMapping(value="/confirmMemberId", produces="text/plain;charset=UTF-8")
	public String confirmMemberId(String memberId) {
		if(memberService.selectMemberByMemberId(memberId)==null) {
			return "t";
		} else {
			return "f";
		}
	}
	@ResponseBody
	@PostMapping("/confirmEmail")
	public String confirmEmail(String email) {
		if(memberService.selectMemberByEmail(email)==null) {
			return "t";
		} else {
			return "f";
		}
	}
	@ResponseBody
	@PostMapping("/confirmPhone")
	public String confirmPhone(String phone) {
		if(memberService.selectMemberByPhone(phone)==null) {
			return "t";
		} else {
			return "f";
		}
	}
}
