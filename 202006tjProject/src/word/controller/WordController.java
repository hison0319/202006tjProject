package word.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import member.dto.MemberDto;
import word.dto.SharingDto;
import word.service.SharingService;
import word.service.WordbookService;

@Controller
@RequestMapping("word")
public class WordController {
	@Autowired
	WordbookService wordbookService;
	@Autowired
	SharingService sharingService;  //테
	//단어 목록 조회 기능
	@RequestMapping("showlist")
	public String wordListShow(HttpSession session, String wordbookid) {
		try{
			MemberDto loginMember = (MemberDto)session.getAttribute("loginMember");
			if(loginMember.getCertified() == 0) {
				return "error/certifyPlease";  //미인증 시
			}
			if(wordbookid ==null) {
				throw new NumberFormatException();
			}
			session.setAttribute("wordbookid", wordbookid);
		} catch (NumberFormatException | IllegalStateException e) {
			return "error/wrongAccess";  //잘못된 접근(주소로 직접 접근 등)
		} catch (NullPointerException e) {
			return "error/loginPlease";  //비로그인 시
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
			if(loginMember.getCertified() == 0) {
				return "{\"nope\":\"certifyPlease\"}";
			}
			loginId = loginMember.getId();  //로그인 아이디 확인
		} catch (NullPointerException e) {  //비로그인 상태
			return "{\"nope\":\"loginPlease\"}";
		}
		boolean isOK = false;
		try {
			int wordbookId = Integer.parseInt((String) session.getAttribute("wordbookid"));
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
				System.out.println("출력 FNFE");
				return "{\"nope\":\"notExist\"}";
			} catch (IOException e) {
				e.printStackTrace();
			} 
		} catch (NullPointerException e) {  //존재하지 않는 단어장
			System.out.println("출력 NPE");
			return "{\"nope\":\"notExist\"}";
		} catch (NumberFormatException | IllegalStateException e) {
			System.out.println("출력 NFE, ISE");
			return "{\"nope\":\"wrongAccess\"}";  //잘못된 접근(주소로 직접 접근 등)
		}
		System.out.println("ㅇㅅㅇ");
		return null;
	}
	
	//단어  수정 기능
	@PostMapping("update")
	@ResponseBody
	public String wordUpdate(HttpSession session, String wordbookid, String[] word, String[] trans) {
		MemberDto loginMember = null;
		int loginId;
		try{
			loginMember = (MemberDto) session.getAttribute("loginMember");
			if(loginMember.getCertified() == 0) {
				System.out.println("업데이트 미인증");
				return "{\"nope\":\"certifyPlease\"}";  //미인증 시
			}
			loginId = loginMember.getId();  //로그인 아이디 확인
			int wordbookId = Integer.parseInt(wordbookid);
			try {
				if (wordbookService.selectWordbookById(wordbookId).getOwnerId() != loginId) {  //단어장 주인일 아닐경우
					System.out.println("업데이트 권한 없음");
					return "{\"nope\":\"notAllowed\"}";
				}
			} catch (NullPointerException e) {  //존재하지 않는 단어장
				System.out.println("업데이트 NPE 1");
				return "{\"nope\":\"notExist\"}";
			}
			String address = wordbookService.selectWordbookById(wordbookId).getWordbookAddress();
			File file = new File(address);
			try(BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "MS949"))){
				String str = "[";
				for (int i = 0; i < word.length; i++) {
					str += "{\"word\":\""+word[i]+"\",\"trans\":\""+trans[i]+"\"},";
				}
				str = str.substring(0,str.length()-1);
				bw.write(str);
				bw.append(']');
				bw.flush();
			} catch (UnsupportedEncodingException e) {
			} catch (FileNotFoundException e) {
				System.out.println("업데이트 FNFE");
				return "{\"nope\":\"notExist\"}";
			} catch (IOException e) {
				System.out.println("업데이트 IOE");
				return "{\"nope\":\"wrongAccess\"}";
			}
		} catch (NullPointerException e) {  //비로그인 시
			System.out.println("업데이트 NPE 2");
			return "{\"nope\":\"loginPlease\"}";
		} catch (NumberFormatException | IllegalStateException e) {
			return "{\"nope\":\"wrongAccess\"}";  //잘못된 접근(주소로 직접 접근 등)
		}
		return "{\"message\":\"success\"}";
	}
}
