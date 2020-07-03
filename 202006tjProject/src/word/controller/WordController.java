package word.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import member.dto.MemberDto;
import word.dto.SharingDto;
import word.dto.WordbookDto;
import word.service.SharingService;
import word.service.WordbookService;

@Controller
@RequestMapping("word")
public class WordController {
	@Autowired
	WordbookService wordbookService;
	@Autowired
	SharingService sharingService;
	//단어 목록 조회 기능
	@GetMapping("showlist")
	public String wordListShow(HttpSession session, HttpServletRequest req) {
		try{
			int wordbookId = Integer.parseInt(req.getParameter("wordbookid"));
			session.setAttribute("wordbookid", wordbookId);
		} catch (NumberFormatException e) {
			return "word/wrongAccess";
		}
		return "word/wordList";
	}
	
	@PostMapping("getwords")
	@ResponseBody
	public String getWordbook(HttpSession session) {
		MemberDto loginMember = null;
		int loginId;
		try{
			loginMember = (MemberDto) session.getAttribute("loginMember");
			loginId = loginMember.getId();  //로그인 아이디 확인
		} catch (NullPointerException e) {  //비로그인 상태
			e.printStackTrace();
			return "{\"nope\":\"loginPlease\"}";
		}
		int wordbookId = (int) session.getAttribute("wordbookid");
		boolean isOK = false;
		try {
			if(loginId<=20) {  //관리자 아이디일 경우
				isOK = true;
			}
			else if (wordbookService.selectWordbookById(wordbookId).getOwnerId() == loginId) {  //단어장 주인일 경우
				isOK = true;
			}
			else {  //공유받은 단어장일 경우
				List<SharingDto> sharing = sharingService.selectSharingByWordbookId(wordbookId);
				for(SharingDto sw : sharing) {
					if(sw.getGuestId() == loginId) {
						isOK =true;
						break;
					}
				}
			}
			if (!isOK) {  //관리자X, 단어장 주인X, 공유받은 단어장X
				return "{\"nope\":\"notAllowed\"}";
			}
			String wordbookAddress = wordbookService.selectWordbookById(wordbookId).getWordbookAddress();
			session.removeAttribute("wordbookid");
			File file = new File(wordbookAddress);
			String s;
			String ajax = "";
			try(BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "MS949"))) {
				while((s=br.readLine()) != null) {
					ajax += s;
				}
				return ajax;
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				return "{\"nope\":\"notExist\"}";
			} catch (IOException e) {
				e.printStackTrace();
			} 
		} catch (NullPointerException e) {  //존재하지 않는 단어장
			e.printStackTrace();
			return "{\"nope\":\"notExist\"}";
		}
		return null;
	}
	//단어 전체 수정 기능
	public String wordInsert() {
		//핵심기능!
		return "";
	}
}
