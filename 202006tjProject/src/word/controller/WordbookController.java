package word.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import member.dto.MemberDto;
import member.service.MailService;
import member.service.MemberService;
import member.service.TempCharKey;
import word.dto.WordbookDto;
import word.service.WordbookService;

@Controller
@RequestMapping("wordbook")
public class WordbookController {
	@Autowired
	WordbookService wordbookService;
	@Autowired
	MemberService memberService;
	@Autowired
	MailService mailService;

	// 단어장 목록 조회 기능 /기본조회 owner,guest, 수정일 순
	@GetMapping("showlist")
	public String wordbookListShow(HttpSession session, Model m, String pageNumStr) { // 세션 모델
		MemberDto loginMember = (MemberDto) session.getAttribute("loginMember");
		if (loginMember == null) {
			m.addAttribute("loginPlease", "로그인이 필요한 서비스입니다.");
		} else if (loginMember.getCertified() == 0) {
			m.addAttribute("certifyPlease", "인증이 필요한 서비스입니다.");
		} else {
			try {
				int pageNum = pageNumStr == null || pageNumStr == "" ? 1 : Integer.parseInt(pageNumStr);
				int ea = 6;// 페이지에 띄울 갯수 정의(정책)
				int loginId = loginMember.getId();
				// 단어장 총 갯수
				int totalCnt;
				totalCnt = wordbookService.selectWordbookCountByOwnerIdOrGuestId(loginId);
				// 페이지 리스트
				int pages = totalCnt % ea == 0 ? totalCnt / ea : totalCnt / ea + 1;
				List<Integer> pageNumList = getPageList(pageNum, ea, pages);
				m.addAttribute("pageNumList", pageNumList);
				m.addAttribute("pageNum", pageNum);
				m.addAttribute("pages", pages);
				// 단어장 리스트 ownerId와 guestId를 통한 조회 페이지 당 6개씩 출력
				List<WordbookDto> list = wordbookService.selectWordbookByOwnerIdOrGuestIdJoin(loginId, (pageNum - 1) * 6, ea);
				// 등록일을 날짜만 표현
				for (int i = 0; i < list.size(); i++) {
					if (list.get(i).getRegDate() != null)
						list.get(i).setRegDateStr(list.get(i).getRegDate().toString().substring(0, 10));
					if (list.get(i).getuDate() != null)
						list.get(i).setuDateStr(list.get(i).getuDate().toString().substring(0, 10));
				}
				if (list.size() == 0) {
					m.addAttribute("listNull", "단어장을 만들어 보세요!");	//조회할 단어장이 없을 때
				} else {
					m.addAttribute("listNull", "");
				}
				m.addAttribute("method", "");
				m.addAttribute("list", list);
			} catch (NullPointerException e) {
				mailService.sendErorrMail(e.toString());
				return "error/wrongAccess";
			} catch (Exception e) {
				mailService.sendErorrMail(e.toString());
				return "error/wrongAccess";
			}
		}
		return "wordbook/wordbookList";
	}

	// 단어장 목록 조회 기능 /조회 owner 수정일 순
	@GetMapping("showlistOwner")
	public String wordbookListShowOwner(HttpSession session, Model m, String pageNumStr) { // 세션 모델
		MemberDto loginMember = (MemberDto) session.getAttribute("loginMember");
		if (loginMember == null) {
			m.addAttribute("loginPlease", "로그인이 필요한 서비스입니다.");
		} else if (loginMember.getCertified() == 0) {
			m.addAttribute("certifyPlease", "인증이 필요한 서비스입니다.");
		} else {
			int totalCnt;
			try {
				int pageNum = pageNumStr == null ? 1 : Integer.parseInt(pageNumStr);
				int ea = 6;// 페이지에 띄울 갯수 정의(정책)
				int loginId = loginMember.getId();
				// 단어장 총 갯수
				totalCnt = wordbookService.selectWordbookCountByOwnerId(loginId);
				// 페이지 리스트
				int pages = totalCnt % ea == 0 ? totalCnt / ea : totalCnt / ea + 1;
				List<Integer> pageNumList = getPageList(pageNum, ea, pages);
				m.addAttribute("pageNumList", pageNumList);
				m.addAttribute("pageNum", pageNum);
				m.addAttribute("pages", pages);
				// 단어장 리스트 ownerId를 통한 조회 페이지 당 6개씩 출력
				List<WordbookDto> list = wordbookService.selectWordbookByOwnerIdJoin(loginId, (pageNum - 1) * 6, ea);
				// 등록일을 날짜만 표현
				for (int i = 0; i < list.size(); i++) {
					if (list.get(i).getRegDate() != null)
						list.get(i).setRegDateStr(list.get(i).getRegDate().toString().substring(0, 10));
					if (list.get(i).getuDate() != null)
						list.get(i).setuDateStr(list.get(i).getuDate().toString().substring(0, 10));
				}
				if (list.size() == 0) {
					m.addAttribute("listNull", "단어장을 만들어 보세요!");
				} else {
					m.addAttribute("listNull", "");
				}
				m.addAttribute("method", "Owner");
				m.addAttribute("list", list);
			} catch (Exception e) {
				mailService.sendErorrMail(e.toString());
				return "error/wrongAccess";
			}
		}
		return "wordbook/wordbookList";
	}

	// 단어장 목록 조회 기능 /조회 guest 수정일 순
	@GetMapping("showlistGuest")
	public String wordbookListShowGuest(HttpSession session, Model m, String pageNumStr) { // 세션 모델
		MemberDto loginMember = (MemberDto) session.getAttribute("loginMember");
		if (loginMember == null) {
			m.addAttribute("loginPlease", "로그인이 필요한 서비스입니다.");
		} else if (loginMember.getCertified() == 0) {
			m.addAttribute("certifyPlease", "인증이 필요한 서비스입니다.");
		} else {
			try {
				int pageNum = pageNumStr == null ? 1 : Integer.parseInt(pageNumStr);
				int ea = 6;// 페이지에 띄울 갯수 정의(정책)
				int loginId = loginMember.getId();
				// 단어장 총 갯수
				int totalCnt;
				totalCnt = wordbookService.selectWordbookCountByGuestId(loginId);
				// 페이지 리스트 guestId를 통한 조회 페이지 당 6개씩 출력
				int pages = totalCnt % ea == 0 ? totalCnt / ea : totalCnt / ea + 1;
				List<Integer> pageNumList = getPageList(pageNum, ea, pages);
				m.addAttribute("pageNumList", pageNumList);
				m.addAttribute("pageNum", pageNum);
				m.addAttribute("pages", pages);
				// 단어장 리스트
				List<WordbookDto> list = wordbookService.selectWordbookByGuestIdJoin(loginId, (pageNum - 1) * 6, ea);
				// 등록일을 날짜만 표현
				for (int i = 0; i < list.size(); i++) {
					if (list.get(i).getRegDate() != null)
						list.get(i).setRegDateStr(list.get(i).getRegDate().toString().substring(0, 10));
					if (list.get(i).getuDate() != null)
						list.get(i).setuDateStr(list.get(i).getuDate().toString().substring(0, 10));
				}
				if (list.size() == 0) {
					m.addAttribute("listNull", "단어장을 만들어 보세요!");
				} else {
					m.addAttribute("listNull", "");
				}
				m.addAttribute("list", list);
				m.addAttribute("method", "Guest");
			} catch (Exception e) {
				mailService.sendErorrMail(e.toString());
				return "error/wrongAccess";
			}
		}
		return "wordbook/wordbookList";
	}

	// 단어장 목록 검색 기능 /기본조회 owner,guest, 수정일 순
	@GetMapping("showlistSearch")
	public String wordbookListShearch(HttpSession session, Model m, String pageNumStr, String keyword) { // 세션 모델
		MemberDto loginMember = (MemberDto) session.getAttribute("loginMember");
		if (loginMember == null) {
			m.addAttribute("loginPlease", "로그인이 필요한 서비스입니다.");
		} else if (loginMember.getCertified() == 0) {
			m.addAttribute("certifyPlease", "인증이 필요한 서비스입니다.");
		} else {
			try {
				int pageNum = pageNumStr == null ? 1 : Integer.parseInt(pageNumStr);
				int ea = 6;// 페이지에 띄울 갯수 정의(정책)
				int loginId = loginMember.getId();
				// 단어장 총 갯수
				int totalCnt;
				totalCnt = wordbookService.selectWordbookCountBySearchJoin(loginId, keyword);
				// 페이지 리스트
				int pages = totalCnt % ea == 0 ? totalCnt / ea : totalCnt / ea + 1;
				List<Integer> pageNumList = getPageList(pageNum, ea, pages);
				m.addAttribute("pageNumList", pageNumList);
				m.addAttribute("pageNum", pageNum);
				m.addAttribute("pages", pages);
				// 단어장 리스트 title과 memberId조회를 통한 검색, ownerId와 guestId를 통한 조회 페이지 당 6개씩 출력
				List<WordbookDto> list = wordbookService.selectWordbookBySearchJoin(loginId, keyword, (pageNum - 1) * 6, ea);
				// 등록일을 날짜만 표현
				for (int i = 0; i < list.size(); i++) {
					if (list.get(i).getRegDate() != null)
						list.get(i).setRegDateStr(list.get(i).getRegDate().toString().substring(0, 10));
					if (list.get(i).getuDate() != null)
						list.get(i).setuDateStr(list.get(i).getuDate().toString().substring(0, 10));
				}
				if (list.size() == 0) {
					m.addAttribute("listNull", "단어장을 만들어 보세요!");
				} else {
					m.addAttribute("listNull", "");
				}
				m.addAttribute("method", "Search");
				m.addAttribute("keyword",keyword);
				m.addAttribute("list", list);
			} catch (Exception e) {
				mailService.sendErorrMail(e.toString());
				return "error/wrongAccess";
			}
		}
		return "wordbook/wordbookList";
	}
	
	// 단어장 목록 조회 기능 /중요 owner,guest, 수정일 순
		@GetMapping("showlistFavorite")
		public String wordbookListShowFavorite(HttpSession session, Model m, String pageNumStr) { // 세션 모델
			MemberDto loginMember = (MemberDto) session.getAttribute("loginMember");
			if (loginMember == null) {
				m.addAttribute("loginPlease", "로그인이 필요한 서비스입니다.");
			} else if (loginMember.getCertified() == 0) {
				m.addAttribute("certifyPlease", "인증이 필요한 서비스입니다.");
			} else {
				try {
					int pageNum = pageNumStr == null || pageNumStr == "" ? 1 : Integer.parseInt(pageNumStr);
					int ea = 6;// 페이지에 띄울 갯수 정의(정책)
					int loginId = loginMember.getId();
					// 단어장 총 갯수
					int totalCnt;
					totalCnt = wordbookService.selectWordbookCountByOwnerIdOrGuestIdFavorite(loginId);
					// 페이지 리스트
					int pages = totalCnt % ea == 0 ? totalCnt / ea : totalCnt / ea + 1;
					List<Integer> pageNumList = getPageList(pageNum, ea, pages);
					m.addAttribute("pageNumList", pageNumList);
					m.addAttribute("pageNum", pageNum);
					m.addAttribute("pages", pages);
					// 단어장 리스트 favorite의 값이 1인 단어장만 조회
					List<WordbookDto> list = wordbookService.selectWordbookByOwnerIdOrGuestIdFavoriteJoin(loginId, (pageNum - 1) * 6,
							ea);
					// 등록일을 날짜만 표현
					for (int i = 0; i < list.size(); i++) {
						if (list.get(i).getRegDate() != null)
							list.get(i).setRegDateStr(list.get(i).getRegDate().toString().substring(0, 10));
						if (list.get(i).getuDate() != null)
							list.get(i).setuDateStr(list.get(i).getuDate().toString().substring(0, 10));
					}
					if (list.size() == 0) {
						m.addAttribute("listNull", "단어장을 만들어 보세요!");
					} else {
						m.addAttribute("listNull", "");
					}
					m.addAttribute("method", "Favorite");
					m.addAttribute("list", list);
				} catch (Exception e) {
					mailService.sendErorrMail(e.toString());
					return "error/wrongAccess";
				}
			}
			return "wordbook/wordbookList";
		}

	// 페이지 네이션 구현 기능
	public List<Integer> getPageList(int pageNum, int ea, int pages) {
		List<Integer> pageNumList = new ArrayList<Integer>();
		int begin;
		if (pageNum % 5 == 1) {
			begin = pageNum;
		} else if (pageNum % 5 == 0) {
			begin = pageNum - 4;
		} else {
			begin = pageNum - (pageNum % 5 - 1);
		}
		for (int i = begin; i <= pages; i++) {
			pageNumList.add(i);
		}
		return pageNumList;
	}

	// 단어장 생성 페이지 이동
	@GetMapping("form")
	public String wordbookForm(HttpSession session) {
		MemberDto loginMember = (MemberDto) session.getAttribute("loginMember");
		if (loginMember == null) {
			return "error/loginPlease";
		} else if (loginMember.getCertified() == 0) {
			return "error/certifyPlease";
		}
		return "wordbook/wordbookUpdateForm";
	}
	
	//공유키를 볼 수 있는 페이지로 이동.(카카오로 접속)
	@GetMapping("sharingKeyForm")
	public String insertBySharingKey(HttpSession session, Model m, String sharingKey, String memberId, String title) {
		m.addAttribute("sharingKey",sharingKey);
		m.addAttribute("memberId",memberId);
		m.addAttribute("title",title);
		return "wordbook/showSharingKey";
	}
	//공유키를 통한 단어장 추가
	@PostMapping("sharingKeyForm")
	public String completeBySharingKey(HttpSession session, Model m, String sharingKey) {
		try {
			MemberDto loginMember = (MemberDto) session.getAttribute("loginMember");
			String idStr = sharingKey.substring(0, sharingKey.indexOf("!"));	//공유키 "!"앞의 아이디 값을 출력
			int wordbookId = Integer.parseInt(idStr);
			WordbookDto ownerWordbook = wordbookService.selectWordbookById(wordbookId);		//아이디값을 조회
			//이미 추가된 단어장에서 같은 제목이 있는지 조회하기 위한 단어장 리스트 생성
			List<WordbookDto> guestWordbookList = wordbookService.selectWordbookByGuestIdCheck(loginMember.getId());
			//공유키 확인
			if (ownerWordbook.getSharingKey().equals(sharingKey)) {
				//guestWordbookList에서 이미 추가된 단어장이 있는지 확인
				for (int i=0; i<guestWordbookList.size(); i++) {
					if (guestWordbookList.get(i).getTitle().equals(ownerWordbook.getTitle())) {
						m.addAttribute("errorMessage","이미 같은 제목의 단어장이 있습니다.");
						return "wordbook/wordbookUpdatefail";
					}
				}
				WordbookDto guestWordbook = new WordbookDto();	//새로운 단어장을 guestId에 로그인 아이디를 넣어 생성.
				guestWordbook.setOwnerId(ownerWordbook.getOwnerId());
				guestWordbook.setGuestId(loginMember.getId());
				guestWordbook.setTitle(ownerWordbook.getTitle());
				guestWordbook.setWordbookAddress(ownerWordbook.getWordbookAddress());
				wordbookService.insertWordbook(guestWordbook);
			} else {
				m.addAttribute("errorMessage","공유키가 맞지 않습니다.");	//공유키가 맞지 않을 시
				return "wordbook/wordbookUpdatefail";
			}
			return "wordbook/wordbookUpdateComplete";
		} catch (Exception e) {
			m.addAttribute("errorMessage","유효한 공유키가 아닙니다.");	//에러 발생 시
			return "wordbook/wordbookUpdatefail";
		}
	}
	//공유키 생성 및 반환
	@PostMapping(value="sharingKey", produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String sharingKey(WordbookDto wordbookDto) {
		try {
			int id = wordbookDto.getId();
			WordbookDto wordbook;
			wordbook = wordbookService.selectWordbookById(id);	//사용자가 선택한 단어장 조회
			String sharingKey = new TempCharKey().getKey(10, false);	//10개의 대소문자 구분된 공유키 생성
			sharingKey = id+"!"+sharingKey;	//아이디 + "!" +공유키로 유효하고 유니크한 공유키 생성
			wordbook.setSharingKey(sharingKey);	//사용자가 선택한 단어장에 생성된 공유키를 삽입
			wordbookService.updateWordbookSharingKey(wordbook); //단어장 수정
			return '"'+sharingKey+'"';	//공유키 반환
		} catch (Exception e) {
			mailService.sendErorrMail(e.toString());
			return "error/wrongAccess";
		}
	}
	
	//공유 취소시 공유한 단어장 제거
	@PostMapping(value="deleteSharing", produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String deleteSharing(WordbookDto wordbookDto) {
		try {	//비동기 처리
			int id = wordbookDto.getId();
			wordbookService.deleteWordbook(id);
			return "1";
		} catch (Exception e) {
			mailService.sendErorrMail(e.toString());
			return "error/wrongAccess";
		}
	}
	
	@PostMapping("favorite") // 비동기 즐겨찾기
	@ResponseBody
	public String toggleFavorite(WordbookDto wordbookDto) {
		int id = wordbookDto.getId();
		WordbookDto wordbook;
		try {
			wordbook = wordbookService.selectWordbookById(id);	//사용자가 조회한 단어장
			if (wordbook.getFavorite() == 0) {
				wordbook.setFavorite(1);
				wordbookService.updateWordbookFavorite(wordbook);
				return "1";	//favorite이 0이면 1로 수정 후 1을 반환
			} else {
				wordbook.setFavorite(0);
				wordbookService.updateWordbookFavorite(wordbook);
				return "0";	//favorite이 1이면 0으로 수정 후 0을 반환
			}
		} catch (Exception e) {
			mailService.sendErorrMail(e.toString());
			return "";
		}
	}

	@PostMapping("complete") // 완료
	public String wordbookInsert(HttpSession session, Model m, String title,
			@RequestParam(required = false) String text, @RequestParam(required = false) File file) {
		MemberDto loginMember = (MemberDto) session.getAttribute("loginMember");
		if (loginMember == null) { // 비로그인
			return "error/loginPlease";
		} else if (loginMember.getCertified() == 0) { // 미인증
			return "error/certifyPlease";
		} else { // 정상 작동
			JSONParser parser = new JSONParser();  //json을 사용하기 위한 객체
			String regex = "[^-^{A-Z}^{a-z}]+";  //-와 알파벳을 제외한 모든 문자
			String today = LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYY_MM_dd"));  //폴더를 위한 현재 날짜
			String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH_mm_ssSSS"));  //파일명을 위한 현재 시각
			Path relativePath = Paths.get("");  //상대경로
			File path = new File("..\\eclipse-workspace\\jsonFiles\\" + today);  //폴더 생성용 파일 객체
			File json = new File("..\\eclipse-workspace\\jsonFiles\\" + today + "\\" + loginMember.getId() + "-" + now + ".json");  //json 파일
			path.mkdirs();  //폴더 생성
			String jsonText = "[";  //json 파일 내용 시작
			String clientId = "FaGdiV_h1RX1lMv2w2tW";  //애플리케이션 클라이언트 아이디값
			String clientSecret = "NhiFrOn9g1";  //애플리케이션 클라이언트 시크릿값
			String apiURL = "https://openapi.naver.com/v1/papago/n2mt";  //api 주소
			Map<String, String> requestHeaders = new HashMap<>();  //api 관련 무언가
			requestHeaders.put("X-Naver-Client-Id", clientId);  //api 관련 무언가
			requestHeaders.put("X-Naver-Client-Secret", clientSecret);  //api 관련 무언가
			String tempEng;  //순서 변경을 위한 임시 String
			int tempCnt;  //순서 변경을 위한 임시 int
			String tempTrans;  //순서 변경을 위한 임시 String
			if (file != null) {  //파일 업로드 시
				String fileExtension = file.getName().substring(file.getName().lastIndexOf(".") + 1);  //확장자 뽑아내기
				if (fileExtension.equals("txt")) {  //확장자가 txt가 맞으면
					try (BufferedReader br = new BufferedReader(
							new InputStreamReader(new FileInputStream(file), "UTF-8"));
							BufferedWriter bw = new BufferedWriter(
									new OutputStreamWriter(new FileOutputStream(json), "UTF-8"))) {
						String s;  //readLine을 위한 임시 String
						String fileText = "";
						while ((s = br.readLine()) != null) {  //마지막 줄이 끝날때까지
							fileText += (s + " ");  //한 줄씩 읽어서 저장
						}
						fileText = fileText.replaceAll(regex, " ");  //-. 알파벳 외 모든 문자 공백으로 변경
						String[] textArr = fileText.split("\\s");  //공백문자를 기준으로 단어로 나눔
						String word;  //인코딩 단어를 담을 임시 String
						int[] count = new int[textArr.length];  //각 단어가 나온 횟수
						String[] responseBody = new String[textArr.length];  //api를 돌려서 나오는 결과json
						for (int i = 0; i < textArr.length; i++) {
							if (textArr[i] != null && textArr[i].length() > 1) {  //한 글자 단어는 무시
								try {
									word = URLEncoder.encode(textArr[i], "UTF-8");  //인코딩
								} catch (UnsupportedEncodingException e) {
									mailService.sendErorrMail(e.toString());
									throw new RuntimeException("인코딩 실패", e);
								}
								count[i] = 1;
								for (int j = i + 1; j < textArr.length; j++) {  //현재 단어 이후의 단어들 모두 확인
									if (textArr[j] != null && textArr[j].equals(textArr[i])) {  //같은 단어가 있으면
										textArr[j] = null;  //나중에 나온 단어를 지움
										count[i]++;  //카운트 증가
									}
								}
								responseBody[i] = post(apiURL, requestHeaders, word);  //api 전송 결과
							} else {
								count[i] = 0;  //null인 경우
							}
						}
						for (int i = 0; i < textArr.length; i++) {
							for (int j = 0; j < textArr.length - 1 - i; j++) {
								if (count[j] < count[j + 1]) {
									tempEng = textArr[j];
									textArr[j] = textArr[j + 1];
									textArr[j + 1] = tempEng;
									tempCnt = count[j];
									count[j] = count[j + 1];
									count[j + 1] = tempCnt;
									tempTrans = responseBody[j];
									responseBody[j] = responseBody[j + 1];
									responseBody[j + 1] = tempTrans;
								}
							}
						}  //count 순으로 순서 변경
						int index = 0;
						for (int i = 0; i < textArr.length; i++) {
							if (textArr[i] != null && textArr[i].length() > 1) {
								try {
									JSONObject resultJson = (JSONObject) parser.parse(responseBody[i]);  //api 결과를 json으로 변경
									JSONObject message = (JSONObject) resultJson.get("message");  //결과에서 message 파트 가져옴
									JSONObject result = (JSONObject) message.get("result");  //message에서 result 파트 가져옴
									if (!textArr[i].equals(result.get("translatedText"))) {  //번역값과 입력값이 같으면 제외
										jsonText += "{\"index\":" + index + ",\"word\":\"" + textArr[i] + "\",\"trans\":\""
												+ result.get("translatedText") + "\",\"favorite\":" + 0 + "},";
										index++;  //인덱스를 1씩 증가시키며 저장
									}
								} catch (Exception e) {
									mailService.sendErorrMail(e.toString());
								}
							}
						}
						jsonText = jsonText.substring(0, jsonText.length() - 1);  //마지막 쉼표 제거
						jsonText += "]";  //json 형식 마지막 문자
						bw.write(jsonText);
						bw.flush();  //파일 쓰기

						m.addAttribute("text", jsonText);

						try {
							//같은 제목이 있는지 조회하기 위한 clientWordbookList생성
							List<WordbookDto> clientWordbookList = wordbookService.selectWordbookByOwnerIdCheck(loginMember.getId());
							for (int i=0; i<clientWordbookList.size(); i++) {
								if (clientWordbookList.get(i).getTitle().equals(title)) {	//같은 제목의 단어장이 있을 시.
									m.addAttribute("errorMessage","이미 같은 제목의 단어장이 있습니다.");
									return "wordbook/wordbookUpdatefail";
								}
							}
							wordbookService.insertWordbook(new WordbookDto(0, loginMember.getId(), 0, 0, title,
									relativePath.toAbsolutePath().getParent() + "\\eclipse-workspace\\jsonFiles\\" + today
									+ "\\" + loginMember.getId() + "-" + now + ".json"));
						} catch (Exception e) {
							mailService.sendErorrMail(e.toString());
							return null;
						}
						return "wordbook/wordbookUpdateComplete";
					} catch (FileNotFoundException e) {
						mailService.sendErorrMail(e.toString());
						return null;
					} catch (IOException e) {
						mailService.sendErorrMail(e.toString());
						return null;
					}
				} else { // 파일 형식이 txt가 아닐 경우
					return "error/wrongFileType";
				}
			}

			else {  //직접 입력 방식
				try (BufferedWriter bw = new BufferedWriter(
						new OutputStreamWriter(new FileOutputStream(json), "UTF-8"))) {
					text = text.replaceAll(regex, " ");  //입력한 값의 -, 알파벳을 제외한 모든 문자 공백으로 변경
					String[] textArr = text.split(" ");  //공백을 기준으로 단어로 나눔
					String word;  //인코딩 단어를 담을 임시 String
					int[] count = new int[textArr.length];  //나온 횟수
					String[] responseBody = new String[textArr.length];  //api 결과를 담을 string
					for (int i = 0; i < textArr.length; i++) {
						if (textArr[i] != null && textArr[i].length() > 1) {  //한 글자 단어 무시
							try {
								word = URLEncoder.encode(textArr[i], "UTF-8");  //인코딩
							} catch (UnsupportedEncodingException e) {
								mailService.sendErorrMail(e.toString());
								throw new RuntimeException("인코딩 실패", e);
							}
							count[i] = 1;
							for (int j = i + 1; j < textArr.length; j++) {
								if (textArr[j] != null && textArr[j].equals(textArr[i])) {  //같은 단어가 나오면
									textArr[j] = null;  //나중에 나온 단어 삭제
									count[i]++;  //카운트 증가
								}
							}
							responseBody[i] = post(apiURL, requestHeaders, word);  //api를 사용해 값을 받아옴
						} else {
							count[i] = 0;  //null인 경우
						}
					}

					for (int i = 0; i < textArr.length; i++) {
						for (int j = 0; j < textArr.length - 1 - i; j++) {
							if (count[j] < count[j + 1]) {
								tempEng = textArr[j];
								textArr[j] = textArr[j + 1];
								textArr[j + 1] = tempEng;
								tempCnt = count[j];
								count[j] = count[j + 1];
								count[j + 1] = tempCnt;
								tempTrans = responseBody[j];
								responseBody[j] = responseBody[j + 1];
								responseBody[j + 1] = tempTrans;
							}
						}
					}  //count 내림차순
					int index = 0;
					for (int i = 0; i < textArr.length; i++) {
						if (textArr[i] != null && textArr[i].length() > 1) {
							try {
								JSONObject resultJson = (JSONObject) parser.parse(responseBody[i]);  //api 결과를 json으로 변경
								JSONObject message = (JSONObject) resultJson.get("message");  //결과에서 message 파트 가져옴
								JSONObject result = (JSONObject) message.get("result");  //message에서 result 파트 가져옴
								if (!textArr[i].equals(result.get("translatedText"))) {  //번역값과 입력값이 같으면 제외
									jsonText += "{\"index\":" + index + ",\"word\":\"" + textArr[i] + "\",\"trans\":\""
											+ result.get("translatedText") + "\",\"favorite\":" + 0 + "},";
									index++;  //인덱스를 1씩 증가시키며 저장
								}
							} catch (Exception e) {
								mailService.sendErorrMail(e.toString());
							}
						}
					}
					jsonText = jsonText.substring(0, jsonText.length() - 1);  //마지막 쉼표 제거
					jsonText += "]";  //json 형식 마지막 문자
					bw.write(jsonText);
					bw.flush();  //파일 쓰기

					m.addAttribute("text", jsonText);
					try {
						//같은 제목이 있는지 조회하기 위한 clientWordbookList생성
						List<WordbookDto> clientWordbookList = wordbookService.selectWordbookByOwnerIdOrGuestIdCheck(loginMember.getId());
						for (int i=0; i<clientWordbookList.size(); i++) {
							if (clientWordbookList.get(i).getTitle().equals(title)) {	//같은 제목의 단어장이 있을 시
								m.addAttribute("errorMessage","이미 같은 제목의 단어장이 있습니다.");
								return "wordbook/wordbookUpdatefail";
							}
						}
						wordbookService.insertWordbook(new WordbookDto(0, loginMember.getId(), 0, 0, title,
								relativePath.toAbsolutePath().getParent() + "\\eclipse-workspace\\jsonFiles\\" + today
								+ "\\" + loginMember.getId() + "-" + now + ".json"));
					} catch (Exception e) {
						mailService.sendErorrMail(e.toString());
					}
					return "wordbook/wordbookUpdateComplete";

				} catch (UnsupportedEncodingException e1) {
					mailService.sendErorrMail(e1.toString());
				} catch (FileNotFoundException e1) {
					mailService.sendErorrMail(e1.toString());
				} catch (IOException e1) {
					mailService.sendErorrMail(e1.toString());
				}
			}
		}
		return "wordbook/wordbookUpdateComplete";
	}
	
	@RequestMapping("delete")  //단어장 삭제
	public String deleteWordbook(HttpSession session, String wordbookid) {
		MemberDto loginMember = (MemberDto) session.getAttribute("loginMember");
		if (loginMember == null) {
			return "error/loginPlease";
		} else if (loginMember.getCertified() == 0) {
			return "error/certifyPlease";
		} else {
			try{
				int wordbookId = Integer.parseInt(wordbookid);
				String wordbookAddress = wordbookService.selectWordbookById(wordbookId).getWordbookAddress();
				File file = new File(wordbookAddress);
				if(!file.delete()) {
					return "error/wrongAccess";
				}
				wordbookService.deleteWordbook(wordbookId);
			} catch( Exception e) {
				mailService.sendErorrMail(e.toString());
				return "error/wrongAccess";
			}
		}
		return "wordbook/wordbookDeleteComplete";
	}
	
	
	private static String post(String apiUrl, Map<String, String> requestHeaders, String text) {
		HttpURLConnection con = connect(apiUrl);
		String postParams = "source=en&target=ko&text=" + text; // 원본언어: 한국어 (ko) -> 목적언어: 영어 (en)
		try {
			con.setRequestMethod("POST");
			for (Map.Entry<String, String> header : requestHeaders.entrySet()) {
				con.setRequestProperty(header.getKey(), header.getValue());
			}

			con.setDoOutput(true);
			try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
				wr.write(postParams.getBytes());
				wr.flush();
			}

			int responseCode = con.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 응답
				return readBody(con.getInputStream());
			} else { // 에러 응답
				return readBody(con.getErrorStream());
			}
		} catch (IOException e) {
			throw new RuntimeException("API 요청과 응답 실패", e);
		} finally {
			con.disconnect();
		}
	}

	private static HttpURLConnection connect(String apiUrl) {
		try {
			URL url = new URL(apiUrl);
			return (HttpURLConnection) url.openConnection();
		} catch (MalformedURLException e) {
			throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
		} catch (IOException e) {
			throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
		}
	}

	private static String readBody(InputStream body) {
		InputStreamReader streamReader = new InputStreamReader(body);

		try (BufferedReader lineReader = new BufferedReader(streamReader)) {
			StringBuilder responseBody = new StringBuilder();

			String line;
			while ((line = lineReader.readLine()) != null) {
				responseBody.append(line);
			}

			return responseBody.toString();
		} catch (IOException e) {
			throw new RuntimeException("API 응답을 읽는데 실패했습니다.", e);
		}
	}
	
}
