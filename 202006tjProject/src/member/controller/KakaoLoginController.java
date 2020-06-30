package member.controller;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.databind.JsonNode;

import member.service.KakaoAccessToken;
import member.service.KakaoUserInfo;

@Controller
public class KakaoLoginController {
	
	@RequestMapping(value="/kakaologin", produces="application/json", method={RequestMethod.GET, RequestMethod.POST})
	public String kakaoLogin(@RequestParam("code") String code, RedirectAttributes ra, HttpSession session, HttpServletResponse response) {
		JsonNode accessToken;
		JsonNode jsonToken = KakaoAccessToken.getKakaoAccessToken(code);
		accessToken = jsonToken.get("access_token");
		
		JsonNode userInfo = KakaoUserInfo.getKakaoUserInfo(accessToken);
		String id = userInfo.path("id").asText();
		String name = null;
		String email = null;
		
		JsonNode properties = userInfo.path("properties");
		JsonNode kakao_account = userInfo.path("kakao_account");
		
		name = properties.path("nickname").asText();
		email = kakao_account.path("email").asText();
		
		System.out.println("id : "+id);
		System.out.println("name : "+name);
		System.out.println("email : "+email);
		
		return "hosting";
	}
	
	
}
