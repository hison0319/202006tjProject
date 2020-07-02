package member.controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.JsonNode;

import member.dto.MemberDto;
import member.dto.MemberVO;
import member.service.KakaoAccessToken;
import member.service.KakaoUserInfo;
import member.service.MemberService;
import member.service.TempCharKey;

@Controller
public class ForAPILoginController {
	@Autowired
	MemberService memberService;
	
	@PostMapping("/googlelogin")
	public String googleLogin(String googleId, String googleName, @RequestParam(required = false)String googleEmail, HttpSession session, Model m) {
		System.out.println("asfdfsdferwwqerwqerxvcxzcv");
		System.out.println(googleName);
		String id = "@" + googleId;
		String name = googleName;
		String email = null;
		if(googleEmail!=null) {
			System.out.println(googleEmail);
			email = googleEmail;
		}
		else {
		}
		String realId = name + id;
		System.out.println("SDFSDFXCXCVCX");
		MemberDto memberGoogle;
		try {
			System.out.println("ewfgfdgfg");
			memberGoogle = memberService.selectMemberByMemberIdforApi(id);
			System.out.println(memberGoogle);
			session.setAttribute("loginMember", memberGoogle);
			session.setAttribute("access_token", "google");
			return "hosting";
		} catch (IndexOutOfBoundsException e) {
			System.out.println("SDFSDFxcvzxcvxxzczvxcvXCXCVCX");
			MemberDto member = new MemberDto();
			String googlePassword = new TempCharKey().getKey(50, false);

			member.setEmail(email);
			
			m.addAttribute("member", member);
			m.addAttribute("realId", realId);
			m.addAttribute("nickName", name);
			m.addAttribute("forAPIPassword", googlePassword);
			m.addAttribute("realId", realId);
			System.out.println("마지막~~");
			System.out.println("member"+member);
			System.out.println("realId"+realId);
			System.out.println("name"+name);
			System.out.println("googlePassword"+googlePassword);
			System.out.println("realId"+realId);
			return "/member/forAPISignup";
		}
	}
	
	@RequestMapping(value = "/kakaologin", produces = "application/json", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String kakaoLogin(@RequestParam("code") String code, Model m, HttpSession session) {
		JsonNode accessToken;
		JsonNode jsonToken = KakaoAccessToken.getKakaoAccessToken(code);
		accessToken = jsonToken.get("access_token");

		JsonNode userInfo = KakaoUserInfo.getKakaoUserInfo(accessToken);
		String id = "!" + userInfo.path("id").asText();
		String name = null;
		String email = null;

		JsonNode properties = userInfo.path("properties");
		JsonNode kakao_account = userInfo.path("kakao_account");

		name = properties.path("nickname").asText();
		email = kakao_account.path("email").asText();

		String realId = name + id;
		MemberDto memberKakao;
		try {
			memberKakao = memberService.selectMemberByMemberIdforApi(id);
			session.setAttribute("loginMember", memberKakao);
			session.setAttribute("access_token", accessToken);
			return "hosting";
		} catch (IndexOutOfBoundsException e) {
			MemberDto member = new MemberDto();
			String kakaoPassword = new TempCharKey().getKey(50, false);

			member.setEmail(email);

			m.addAttribute("member", member);
			m.addAttribute("realId", realId);
			m.addAttribute("nickName", name);
			m.addAttribute("forAPIPassword", kakaoPassword);
			m.addAttribute("realId", realId);

			return "/member/forAPISignup";
		}
	}

	// 비동기 식 이메일, 번호 중복 확인
	@ResponseBody
	@PostMapping("/forAPIConfirmEmail")
	public String confirmEmail(String email) {
		if (memberService.selectMemberByEmail(email) == null) {
			return "t";
		} else {
			return "f";
		}
	}

	@ResponseBody
	@PostMapping("/forAPIConfirmPhone")
	public String confirmPhone(String phone) {
		if (memberService.selectMemberByPhone(phone) == null) {
			return "t";
		} else {
			return "f";
		}
	}

	@PostMapping("forAPISignup")
	public String kakaoSignup(@ModelAttribute("MemberVo") @Valid MemberVO memberVo, BindingResult result, Model m,
			String realId, String nickName, String forAPIPassword) {
		MemberDto member = new MemberDto();
		member.setEmail(memberVo.getEmail());
		member.setPhone(memberVo.getPhone());
		member.setAddress(memberVo.getAddress());
		if (result.hasErrors()) {
			List<FieldError> errors = result.getFieldErrors();
			System.out.println(result.toString());
			for (FieldError fe : errors) {
				m.addAttribute("e" + fe.getField(), fe.getField());
				System.out.println(fe.getField());
			}
			m.addAttribute("member", member);
			m.addAttribute("realId", realId);
			m.addAttribute("nickName", nickName);
			m.addAttribute("forAPIPassword", forAPIPassword);
			return "member/forAPISignup";
		} else {
			try {
				member.setMemberId(realId);
				member.setPassword(forAPIPassword);
				memberService.insertMember(member);
			} catch (Exception e) {
				m.addAttribute("member", member);
				m.addAttribute("realId", realId);
				m.addAttribute("nickName", nickName);
				m.addAttribute("forAPIPassword", forAPIPassword);
				return "member/forAPISignup";
			}
		}
		return "hosting";
	}
}
