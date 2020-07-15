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
	//회원가입 폼 이동
	@GetMapping("/form")
	public String form() {
		return "member/signupForm";
	}
	//회원가입 기능
	@PostMapping("/insert")
	public String insert(@ModelAttribute("MemberVo") @Valid MemberVO memberVo, BindingResult result, Model m) {
		MemberDto member = new MemberDto(memberVo.getMemberId(), memberVo.getPassword(), 
				memberVo.getEmail(), memberVo.getPhone(), memberVo.getAddress());
		// 유효성 검사
		if(result.hasErrors()) {
			List<FieldError> errors = result.getFieldErrors();	//에러 발생시 리스트에 저장
			for(FieldError fe : errors) {
				m.addAttribute("e"+fe.getField(), fe.getField());	//MODEL에 에러난 값을 저장해서 화면으로 다시 출력
			}
			m.addAttribute("member",member);
			return "member/signupForm";
		} else {
			try {
				memberService.insertMember(member); //에러 미 발생 시 멤버 추가
			} catch (Exception e) {
				m.addAttribute("member",member);	//추가 시 에러 발생하면 다시 MODEL에 에러난 값 저장후 화면 출력
				return "member/signupForm";
			}
		}
		return "member/signupComplete";	//성공 시 성공페이지 이동
	}
	
	//비동기 식 아이디 중복확인
	@ResponseBody
	@PostMapping(value="/confirmMemberId", produces="text/plain;charset=UTF-8")
	public String confirmMemberId(String memberId) {
		if(memberService.selectMemberByMemberId(memberId)==null) {
			return "t";
		} else {
			return "f";
		}
	}
	//비동기 식 이메일 중복확인
	@ResponseBody
	@PostMapping("/confirmEmail")
	public String confirmEmail(String email) {
		if(memberService.selectMemberByEmail(email)==null) {
			return "t";
		} else {
			return "f";
		}
	}
	//비동기 식 전화번호 중복확인
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
