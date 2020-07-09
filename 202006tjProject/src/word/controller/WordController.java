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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	SharingService sharingService;
	
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
		} catch (NumberFormatException | IllegalStateException e) {
			return "error/wrongAccess";  //잘못된 접근(주소로 직접 접근 등)
		} catch (NullPointerException e) {
			return "error/loginPlease";  //비로그인 시
		}
		return "word/wordList";
	}
	
	@RequestMapping("getwords")
	@ResponseBody
	public String getWordbook(HttpSession session, String wordbookid) {
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
			int wordbookId = Integer.parseInt(wordbookid);
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
			try(BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"))) {
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
			e.printStackTrace();
			System.out.println("출력 NFE, ISE");
			return "{\"nope\":\"wrongAccess\"}";  //잘못된 접근(주소로 직접 접근 등)
		}
		System.out.println("ㅇㅅㅇ");
		return null;
	}
	
	//장문 추가 페이지
	@GetMapping("form")
	public String insertForm(Model m, String wordbookid) {
		try {
			int wordbookId = Integer.parseInt(wordbookid);
			m.addAttribute("wordbookId", wordbookId);
			m.addAttribute("title", wordbookService.selectWordbookById(wordbookId).getTitle());
		} catch (NumberFormatException | IllegalStateException e) {
			return "error/wrongAccess";  //잘못된 접근(주소로 직접 접근 등)
		}
		return "word/wordUpdateForm";
	}
	
	//단어 개별 추가 기능
	@PostMapping("insert")
	@ResponseBody
	public String wordInsert(HttpSession session, String wordbookid, String[] word, String[] trans) {
		MemberDto loginMember = null;
		int loginId;
		try{
			loginMember = (MemberDto) session.getAttribute("loginMember");
			if(loginMember.getCertified() == 0) {
				System.out.println("인서트 미인증");
				return "{\"nope\":\"certifyPlease\"}";  //미인증 시
			}
			loginId = loginMember.getId();  //로그인 아이디 확인
			int wordbookId = Integer.parseInt(wordbookid);
			try {
				if (wordbookService.selectWordbookById(wordbookId).getOwnerId() != loginId) {  //단어장 주인일 아닐경우
					System.out.println("추가 권한 없음");
					return "{\"nope\":\"notAllowed\"}";
				}
			} catch (NullPointerException e) {  //존재하지 않는 단어장
				System.out.println("인서트 NPE 1");
				return "{\"nope\":\"notExist\"}";
			}
			String address = wordbookService.selectWordbookById(wordbookId).getWordbookAddress();
			File origFile = new File(address);
			if(word!=null) {
				try(BufferedReader br = new BufferedReader(new InputStreamReader(
						new FileInputStream(origFile),"UTF-8"))){
					JSONParser jParser = new JSONParser();
					JSONArray jsonArray = (JSONArray)jParser.parse(br);
					String origStr = jsonArray.toString();
					origStr = origStr.replace(']', ',');
					int index = jsonArray.size();
					for (int i = 0; i < word.length; i++) {
						if(!word[i].isBlank() || !trans[i].isBlank()) {
							origStr += "{\"index\":" + index + ",\"word\":\"" + word[i] + "\",\"trans\":\""
									+ trans[i] + "\",\"favorite\":" + 0 + "},";
							index++;
						}
					}
					origStr = origStr.substring(0,origStr.length()-1);
					origStr +="]";
					try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(origFile),"UTF-8"))){
						bw.write(origStr);
						bw.flush();
					}
				} catch (UnsupportedEncodingException e) {
					System.out.println("what");
				} catch (FileNotFoundException e) {
					System.out.println("인서트 FNFE");
					return "{\"nope\":\"notExist\"}";
				} catch (IOException e) {
					System.out.println("인서트 IOE");
					return "{\"nope\":\"wrongAccess\"}";
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (NullPointerException e) {  //비로그인 시
			System.out.println("인서트 NPE 2");
			return "{\"nope\":\"loginPlease\"}";
		} catch (NumberFormatException | IllegalStateException e) {
			return "{\"nope\":\"wrongAccess\"}";  //잘못된 접근(주소로 직접 접근 등)
		}
		return "{\"message\":\"success\"}";
	}
	
	//장문 추가 기능
	@PostMapping("complete")
	public String wordUpdate(HttpSession session, Model m, String wordbookid, 
			@RequestParam(required = false)File file, @RequestParam(required = false)String text) {
		MemberDto loginMember = (MemberDto) session.getAttribute("loginMember");
		if (loginMember == null) { // 비로그인
			return "error/loginPlease";
		} else if (loginMember.getCertified() == 0) { // 미인증
			return "error/certifyPlease";
		} else { // 정상 작동
			int wordbookId = 0;
			if(wordbookid == null || wordbookid.isBlank()) {
				return "error/wrongAccess";
			}
			else {
				wordbookId = Integer.parseInt(wordbookid);
			}
			JSONParser parser = new JSONParser();
			String regex = "[^-^{A-Z}^{a-z}]+";
			String clientId = "FaGdiV_h1RX1lMv2w2tW";// 애플리케이션 클라이언트 아이디값";
			String clientSecret = "NhiFrOn9g1";// 애플리케이션 클라이언트 시크릿값";
			String apiURL = "https://openapi.naver.com/v1/papago/n2mt";
			Map<String, String> requestHeaders = new HashMap<>();
			requestHeaders.put("X-Naver-Client-Id", clientId);
			requestHeaders.put("X-Naver-Client-Secret", clientSecret);
			String filePath = wordbookService.selectWordbookById(wordbookId).getWordbookAddress();
			File origFile = new File(filePath);
			JSONParser jParser = new JSONParser();
			if (file != null) {
				String fileExtension = file.getName().substring(file.getName().lastIndexOf(".") + 1);
				if (fileExtension.equals("txt")) {
					try (BufferedReader obr = new BufferedReader(
								new InputStreamReader(new FileInputStream(origFile), "UTF-8"));
							BufferedReader br = new BufferedReader(
									new InputStreamReader(new FileInputStream(file), "UTF-8"))) {
						
						JSONArray jsonArray = (JSONArray)jParser.parse(obr);  //기존 파일 사용가능 형태로 변환
						int index = jsonArray.size();  //추가하는 단어의 index 시작 값
						String jsonText = jsonArray.toString();  //기존 파일 내용 String
						jsonText = jsonText.replace(']', ',');  //json 끝의 ]를 ,로 변경
						String s;  //readLine() 메서드를 위한 임시 String
						String fileText = "";  //업로드한 파일의 내용을 담을 String
						while ((s = br.readLine()) != null) {  //남은 내용이 있으면 실행
							fileText += (s + " ");
						}
						fileText = fileText.replaceAll(regex, " ");  //-를 제외한 영어단어 외의 문자 제거
						String[] textArr = fileText.split("\\s");  //공백 기준으로 단어를 만들어 배열에 저장
						String word;  //단어를 인코딩해서 담을 임시 String
						int[] count = new int[textArr.length];  //같은 단어 등장 횟수
						String[] responseBody = new String[textArr.length];  //word를 보낸 결과로 반환되는 json 형태를 가진 String을 담을 배열
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
						String tempEng;
						int tempCnt;
						String tempTrans;
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
									JSONObject resultJson = (JSONObject) parser.parse(responseBody[i]);  //json으로 변환
									JSONObject message = (JSONObject) resultJson.get("message");  //json의 key(message)로 값 반환
									JSONObject result = (JSONObject) message.get("result");  //json의 key(result)로 값 반환
									if (!textArr[i].equals(result.get("translatedText"))) {
										jsonText += "{\"index\":" + index + ",\"word\":\"" + textArr[i] + "\",\"trans\":\""
												+ result.get("translatedText") + "\",\"favorite\":" + 0 + "},";
										index++;
									}
								} catch (Exception e) {
									e.printStackTrace();
								}
							}
						}
						jsonText = jsonText.substring(0, jsonText.length() - 1);
						jsonText += "]";
						try(BufferedWriter obw = new BufferedWriter(
									new OutputStreamWriter(new FileOutputStream(origFile), "UTF-8"))){
							obw.write(jsonText);
							obw.flush();
						}
						return "word/wordUpdateComplete";
					} catch (FileNotFoundException e) {
						e.printStackTrace();
						return null;
					} catch (IOException e) {
						e.printStackTrace();
						return null;
					} catch (ParseException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else { // 파일 형식이 txt가 아닐 경우
					return "error/wrongFileType"; //``
				}
			}

			else {
				try (BufferedReader obr = new BufferedReader(
							new InputStreamReader(new FileInputStream(origFile), "UTF-8"))) {
					JSONArray jsonArray = (JSONArray)jParser.parse(obr);  //기존 파일 사용가능 형태로 변환
					int index = jsonArray.size();  //추가하는 단어의 index 시작 값
					String jsonText = jsonArray.toString();  //기존 파일 내용 String
					jsonText = jsonText.replace(']', ',');  //json 끝의 ]를 ,로 변경
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
					String tempEng;
					int tempCnt;
					String tempTrans;
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
									jsonText += "{\"index\":" + index + ",\"word\":\"" + textArr[i] + "\",\"trans\":\""
											+ result.get("translatedText") + "\",\"favorite\":" + 0 + "},";
									index++;
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}
					jsonText = jsonText.substring(0, jsonText.length() - 1);
					jsonText += "]";
					try(BufferedWriter obw = new BufferedWriter(
							new OutputStreamWriter(new FileOutputStream(origFile), "UTF-8"))){
					obw.write(jsonText);
					obw.flush();
				}
					return "word/wordUpdateComplete";

				} catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ParseException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		return "wordbook/wordbookUpdateComplete";  //``
	}
	
	//단어 개별 수정 기능
	@PostMapping("update")
	@ResponseBody
	public String wordUpdate(HttpSession session, String wordbookid, int[] index, String[] word, String[] trans, int[] favorite) {
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
				File origFile = new File(address);
			try(BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(origFile), "UTF-8"))){
				String str = "[";
				for (int i = 0; i < word.length; i++) {
					if(!word[i].isBlank() || !trans[i].isBlank()) {
						str += "{\"index\":" + index[i] + ",\"word\":\"" + word[i] + "\",\"trans\":\""
								+ trans[i] + "\",\"favorite\":" + favorite[i] + "},";
					}
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
