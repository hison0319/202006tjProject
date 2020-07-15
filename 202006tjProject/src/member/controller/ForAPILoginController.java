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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.JsonNode;

import member.dto.MemberDto;
//import member.dto.MemberDtoForGoogle;
import member.dto.MemberVO;
import member.service.KakaoAccessToken;
import member.service.KakaoUserInfo;
import member.service.MemberService;
import member.service.TempCharKey;

@Controller
public class ForAPILoginController {
	@Autowired
	MemberService memberService;

	//구글 sign in 기능 구현 미완
//	@ResponseBody
//	@PostMapping("/googlelogin")
//	public String googleLogin(MemberDtoForGoogle googleInfo, HttpSession session, Model m) {
//		System.out.println(googleInfo);
//		System.out.println(googleInfo.getGoogleName());
//		String id = "@" + googleInfo.getGoogleId();
//		String name = googleInfo.getGoogleName();
//		String email = null;
//		if(googleInfo.getGoogleEmail()!=null) {
//			System.out.println(googleInfo.getGoogleEmail());
//			email = googleInfo.getGoogleEmail();
//		}
//		else {
//		}
//		String realId = name + id;
//		MemberDto memberGoogle;
//		try {
//			memberGoogle = memberService.selectMemberByMemberIdforApi(id);
//			System.out.println(memberGoogle);
//			session.setAttribute("loginMember", memberGoogle);
//			session.setAttribute("access_token", "google");
//			return "t";
//		} catch (IndexOutOfBoundsException e) {
//			MemberDto member = new MemberDto();
//			String googlePassword = new TempCharKey().getKey(50, false);
//
//			member.setEmail(email);
//			
//			m.addAttribute("member", member);
//			m.addAttribute("realId", realId);
//			m.addAttribute("nickName", name);
//			m.addAttribute("forAPIPassword", googlePassword);
//			m.addAttribute("realId", realId);
//			System.out.println("마지막~~");
//			System.out.println("member"+member);
//			System.out.println("realId"+realId);
//			System.out.println("name"+name);
//			System.out.println("googlePassword"+googlePassword);
//			System.out.println("realId"+realId);
//			return "f";
//		}
//	}
	
	//카카오 로그인 기능
	@RequestMapping(value = "/kakaologin", produces = "application/json", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String kakaoLogin(@RequestParam("code") String code, Model m, HttpSession session) {
		JsonNode accessToken;
		JsonNode jsonToken = KakaoAccessToken.getKakaoAccessToken(code); //카카오 토큰 발급
		accessToken = jsonToken.get("access_token");

		JsonNode userInfo = KakaoUserInfo.getKakaoUserInfo(accessToken); //발급받은 토큰으로 사용자 유저 정보를 가져옴.
		String id = "!" + userInfo.path("id").asText();	//우리 DB에 맞게 카카오 아이디를 변경하여 저장 (!+kakaoID)
		String name = null;
		String email = null;

		JsonNode properties = userInfo.path("properties");
		JsonNode kakao_account = userInfo.path("kakao_account");	//가져온 유저 정보를 json객체로 저장

		name = properties.path("nickname").asText();	//name에는 kakaoNickname을 저장
		email = kakao_account.path("email").asText();	//email이 있으면 이메일 저장.(string null허용)

		String realId = name + id;	//우리 DB에 맞게 카카오 아이디를 변경하여 저장 (kakaoNickname+!+kakaoID)
		MemberDto memberKakao;
		try {
			memberKakao = memberService.selectMemberByMemberIdforApi(id); //만약 기존에 가입된 회원이라면 로그인 완료.
			memberKakao.setMemberId(name+"(kakao)");
			session.setAttribute("loginMember", memberKakao);
			session.setAttribute("access_token", accessToken);
			return "/hosting";
		} catch (IndexOutOfBoundsException e) {	//로그인되 회원이 아니면 회원가입창으로 이동.
			MemberDto member = new MemberDto();
			String kakaoPassword = new TempCharKey().getKey(50, false);

			member.setEmail(email);

			m.addAttribute("member", member);
			m.addAttribute("realId", realId);
			m.addAttribute("nickName", name);
			m.addAttribute("forAPIPassword", kakaoPassword);
			m.addAttribute("realId", realId);	//이동 시 정보들을 MODEL에 저장.

			return "/member/forAPISignup";
		}
	}

	// 비동기 식 이메일 중복확인
	@ResponseBody
	@PostMapping("/forAPIConfirmEmail")
	public String confirmEmail(String email) {
		if (memberService.selectMemberByEmail(email) == null) {
			return "t";
		} else {
			return "f";
		}
	}
	// 비동기 식 전화번호 중복확인
	@ResponseBody
	@PostMapping("/forAPIConfirmPhone")
	public String confirmPhone(String phone) {
		if (memberService.selectMemberByPhone(phone) == null) {
			return "t";
		} else {
			return "f";
		}
	}
	
	//카카오 사용자 회원가입 기능
	@PostMapping("forAPISignup")
	public String APISignup(@ModelAttribute("MemberVo") @Valid MemberVO memberVo, BindingResult result, Model m,
			String realId, String nickName, String forAPIPassword) {
		MemberDto member = new MemberDto();
		member.setEmail(memberVo.getEmail());
		member.setPhone(memberVo.getPhone());
		member.setAddress(memberVo.getAddress());
		//유효성 검사
		if (result.hasErrors()) {
			List<FieldError> errors = result.getFieldErrors();
			for (FieldError fe : errors) {
				m.addAttribute("e" + fe.getField(), fe.getField());
			}
			m.addAttribute("member", member);
			m.addAttribute("realId", realId);
			m.addAttribute("nickName", nickName);
			m.addAttribute("forAPIPassword", forAPIPassword);
			return "member/forAPISignup";
		} else {
			try {
				member.setMemberId(realId); //변형된 아이디 값을 저장.
				member.setPassword(forAPIPassword);
				memberService.insertMember(member);
			} catch (Exception e) {
				m.addAttribute("member", member);	//유효성 검사에서 에러 발생시 기존 값을 넣고 다시 회원가입 페이지로 이동
				m.addAttribute("realId", realId);
				m.addAttribute("nickName", nickName);
				m.addAttribute("forAPIPassword", forAPIPassword);
				return "member/forAPISignup";
			}
		}
		return "hosting";
	}
	
	//회원 탈퇴
	@GetMapping("/delete")
	public String memberDelete(int id, HttpSession session) {
		memberService.deleteMember(id);
		session.removeAttribute("loginMember");  //세션에서 로그인 정보 삭제
		if(session.getAttribute("access_token") != null) {
			JsonNode accessToken = (JsonNode) session.getAttribute("access_token");
			session.removeAttribute("access_token");
		}
		//회원이 소유한 단어장과 공유받은 단어장 DB에서 삭제되도록 기능 구현 필요
		return "/account/memberDeleteComplete";
	}
}
