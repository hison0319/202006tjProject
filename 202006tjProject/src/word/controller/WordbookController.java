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

import javax.servlet.http.HttpServletRequest;
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

import com.fasterxml.jackson.databind.JsonNode;

import member.dto.MemberDto;
import member.service.MemberService;
import word.dto.WordbookDto;
import word.service.WordbookService;

@Controller
@RequestMapping("wordbook")
public class WordbookController {
	@Autowired
	WordbookService wordbookService;
	@Autowired
	MemberService memberService;

	// 단어장 목록 조회 기능 /기본조회 owner,guest, 수정일 순
	@GetMapping("showlist")
	public String wordbookListShow(HttpSession session, Model m, String pageNumStr) { // 세션 모델
		MemberDto loginMember = (MemberDto) session.getAttribute("loginMember");
		if (loginMember == null) {
			m.addAttribute("loginPlease", "로그인이 필요한 서비스입니다.");
		} else if (loginMember.getCertified() == 0) {
			m.addAttribute("certifyPlease", "인증이 필요한 서비스입니다.");
		} else {
			int pageNum = pageNumStr == null || pageNumStr == "" ? 1 : Integer.parseInt(pageNumStr);
			int ea = 6;// 페이지에 띄울 갯수 정의(정책)
			int loginId = loginMember.getId();
			// 단어장 총 갯수
			int totalCnt = wordbookService.selectWordbookCountByOwnerIdOrGuestId(loginId);
			// 페이지 리스트
			int pages = totalCnt % ea == 0 ? totalCnt / ea : totalCnt / ea + 1;
			List<Integer> pageNumList = getPageList(pageNum, ea, pages);
			m.addAttribute("pageNumList", pageNumList);
			m.addAttribute("pageNum", pageNum);
			m.addAttribute("pages", pages);
			// 단어장 리스트
			List<WordbookDto> list = wordbookService.selectWordbookByOwnerIdOrGuestIdJoin(loginId, (pageNum - 1) * 6,
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
			m.addAttribute("method", "");
			m.addAttribute("list", list);
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
			int pageNum = pageNumStr == null ? 1 : Integer.parseInt(pageNumStr);
			int ea = 6;// 페이지에 띄울 갯수 정의(정책)
			int loginId = loginMember.getId();
			// 단어장 총 갯수
			int totalCnt = wordbookService.selectWordbookCountByOwnerId(loginId);
			// 페이지 리스트
			int pages = totalCnt % ea == 0 ? totalCnt / ea : totalCnt / ea + 1;
			List<Integer> pageNumList = getPageList(pageNum, ea, pages);
			m.addAttribute("pageNumList", pageNumList);
			m.addAttribute("pageNum", pageNum);
			m.addAttribute("pages", pages);
			// 단어장 리스트
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
			int pageNum = pageNumStr == null ? 1 : Integer.parseInt(pageNumStr);
			int ea = 6;// 페이지에 띄울 갯수 정의(정책)
			int loginId = loginMember.getId();
			// 단어장 총 갯수
			int totalCnt = wordbookService.selectWordbookCountByGuestId(loginId);
			// 페이지 리스트
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
		}
		m.addAttribute("method", "Guest");
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
			int pageNum = pageNumStr == null ? 1 : Integer.parseInt(pageNumStr);
			int ea = 6;// 페이지에 띄울 갯수 정의(정책)
			int loginId = loginMember.getId();
			// 단어장 총 갯수
			int totalCnt = wordbookService.selectWordbookCountBySearchJoin(loginId, keyword);
			System.out.println(totalCnt);
			// 페이지 리스트
			int pages = totalCnt % ea == 0 ? totalCnt / ea : totalCnt / ea + 1;
			List<Integer> pageNumList = getPageList(pageNum, ea, pages);
			m.addAttribute("pageNumList", pageNumList);
			m.addAttribute("pageNum", pageNum);
			m.addAttribute("pages", pages);
			// 단어장 리스트
			List<WordbookDto> list = wordbookService.selectWordbookBySearchJoin(loginId, keyword, (pageNum - 1) * 6, ea);
			System.out.println(list);
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

	// 단어장 생성 페이지
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

	@PostMapping("favorite") // 비동기 즐겨찾기
	@ResponseBody
	public String toggleFavorite(HttpSession session, HttpServletRequest req, WordbookDto wordbookDto) {
		System.out.println(wordbookDto);
		int id = wordbookDto.getId();
		WordbookDto wordbook = wordbookService.selectWordbookById(id);
		System.out.println(wordbook);
		if (wordbook.getFavorite() == 0) {
			wordbookService.updateWordbook(new WordbookDto(wordbook.getId(), wordbook.getOwnerId(), 1,
					wordbook.getGuestId(), wordbook.getTitle(), wordbook.getWordbookAddress()));
		} else {
			wordbookService.updateWordbook(new WordbookDto(wordbook.getId(), wordbook.getOwnerId(), 0,
					wordbook.getGuestId(), wordbook.getTitle(), wordbook.getWordbookAddress()));
		}
		return "{\"wordbookId\":\"" + id + "\"}";
	}

	@PostMapping("sharing") // 비동기 공유
	@ResponseBody
	public String toggleSharing(HttpSession session, HttpServletRequest req, WordbookDto wordbookDto) {
		int id = wordbookDto.getId();
		WordbookDto wordbook = wordbookService.selectWordbookById(id);
		if (wordbook.getGuestId() == 0) {
			wordbookService.updateWordbook(new WordbookDto(wordbook.getId(), wordbook.getOwnerId(),
					wordbook.getFavorite(), 1, wordbook.getTitle(), wordbook.getWordbookAddress()));
		} else {
			wordbookService.updateWordbook(new WordbookDto(wordbook.getId(), wordbook.getOwnerId(),
					wordbook.getFavorite(), 0, wordbook.getTitle(), wordbook.getWordbookAddress()));
		}
		return "{\"wordbookId\":\"" + id + "\"}";
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
			JSONParser parser = new JSONParser();
			String regex = "[^-^{A-Z}^{a-z}]+";
			String today = LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYY_MM_dd"));
			String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH_mm_ss"));
			Path relativePath = Paths.get("");
			File path = new File("..\\eclipse-workspace\\jsonFiles\\" + today);
			File json = new File("..\\eclipse-workspace\\jsonFiles\\" + today + "\\" + now + ".json");
			path.mkdirs();
			String jsonText = "[";
			String clientId = "FaGdiV_h1RX1lMv2w2tW";// 애플리케이션 클라이언트 아이디값";
			String clientSecret = "NhiFrOn9g1";// 애플리케이션 클라이언트 시크릿값";
			String apiURL = "https://openapi.naver.com/v1/papago/n2mt";
			Map<String, String> requestHeaders = new HashMap<>();
			requestHeaders.put("X-Naver-Client-Id", clientId);
			requestHeaders.put("X-Naver-Client-Secret", clientSecret);
			String tempEng;
			int tempCnt;
			String tempTrans;
			if (file != null) {
				String fileExtension = file.getName().substring(file.getName().lastIndexOf(".") + 1);
				if (fileExtension.equals("txt")) {
					try (BufferedReader br = new BufferedReader(
							new InputStreamReader(new FileInputStream(file), "UTF-8"));
							BufferedWriter bw = new BufferedWriter(
									new OutputStreamWriter(new FileOutputStream(json), "UTF-8"))) {
						String s;
						String fileText = "";
						while ((s = br.readLine()) != null) {
							fileText += (s + " ");
						}
						fileText = fileText.replaceAll(regex, " ");
						String[] textArr = fileText.split("\\s"); // " " 에서 변경
						String word;
						int[] count = new int[textArr.length];
						String[] responseBody = new String[textArr.length];
						for (int i = 0; i < textArr.length; i++) {
							if (textArr[i] != null && textArr[i].length() > 1) {
								try {
									word = URLEncoder.encode(textArr[i], "UTF-8");
								} catch (UnsupportedEncodingException e) {
									throw new RuntimeException("인코딩 실패", e);
								}
								count[i] = 1;
								for (int j = i + 1; j < textArr.length; j++) {
									if (textArr[j] != null && textArr[j].equals(textArr[i])) {
										textArr[j] = null;
										count[i]++;
									}
								}
								responseBody[i] = post(apiURL, requestHeaders, word);
							} else {
								count[i] = 0;
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
						}
						for (int i = 0; i < textArr.length; i++) {
							if (textArr[i] != null && textArr[i].length() > 1) {
								try {
									JSONObject resultJson = (JSONObject) parser.parse(responseBody[i]);
									JSONObject message = (JSONObject) resultJson.get("message");
									JSONObject result = (JSONObject) message.get("result");
									if (!textArr[i].equals(result.get("translatedText"))) {
										jsonText += "{\"word\":\"" + textArr[i] + "\",\"trans\":\""
												+ result.get("translatedText") + "\",\"count\":" + count[i] + "},";
									}
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						}
						jsonText = jsonText.substring(0, jsonText.length() - 1);
						jsonText += "]";
						bw.write(jsonText);
						bw.flush();

						m.addAttribute("text", jsonText);

						wordbookService.insertWordbook(new WordbookDto(0, loginMember.getId(), 0, 0, title,
								relativePath.toAbsolutePath().getParent() + "\\eclipse-workspace\\jsonFiles\\" + today
										+ "\\" + now + ".json"));
						return "wordbook/wordbookUpdateComplete";
					} catch (FileNotFoundException e) {
						e.printStackTrace();
						return null;
					} catch (IOException e) {
						e.printStackTrace();
						return null;
					}
				} else { // 파일 형식이 txt가 아닐 경우
					return "error/wrongFileType";
				}
			}

			else {
				try (BufferedWriter bw = new BufferedWriter(
						new OutputStreamWriter(new FileOutputStream(json), "UTF-8"))) {
					text = text.replaceAll(regex, " ");
					String[] textArr = text.split(" ");
					String word;
					int[] count = new int[textArr.length];
					String[] responseBody = new String[textArr.length];
					for (int i = 0; i < textArr.length; i++) {
						if (textArr[i] != null && textArr[i].length() > 1) {
							try {
								word = URLEncoder.encode(textArr[i], "UTF-8");
							} catch (UnsupportedEncodingException e) {
								throw new RuntimeException("인코딩 실패", e);
							}
							count[i] = 1;
							for (int j = i + 1; j < textArr.length; j++) {
								if (textArr[j] != null && textArr[j].equals(textArr[i])) {
									textArr[j] = null;
									count[i]++;
								}
							}
							responseBody[i] = post(apiURL, requestHeaders, word);
						} else {
							count[i] = 0;
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
					}
					for (int i = 0; i < textArr.length; i++) {
						if (textArr[i] != null && textArr[i].length() > 1) {
							try {
								System.out.println(responseBody[0]);
								JSONObject resultJson = (JSONObject) parser.parse(responseBody[i]);
								JSONObject message = (JSONObject) resultJson.get("message");
								JSONObject result = (JSONObject) message.get("result");
								if (!textArr[i].equals(result.get("translatedText"))) {
									jsonText += "{\"word\":\"" + textArr[i] + "\",\"trans\":\""
											+ result.get("translatedText") + "\",\"count\":" + count[i] + "},";
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
					jsonText = jsonText.substring(0, jsonText.length() - 1);
					jsonText += "]";
					bw.write(jsonText);
					bw.flush();

					m.addAttribute("text", jsonText);
					wordbookService.insertWordbook(new WordbookDto(0, loginMember.getId(), 0, 0, title,
							relativePath.toAbsolutePath().getParent() + "\\eclipse-workspace\\jsonFiles\\" + today
									+ "\\" + now + ".json"));
					return "wordbook/wordbookUpdateComplete";

				} catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		return "wordbook/wordbookUpdateComplete";
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
