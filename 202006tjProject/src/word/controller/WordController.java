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
import member.service.MailService;
import word.service.WordbookService;

@Controller
@RequestMapping("word")
public class WordController {
	@Autowired
	WordbookService wordbookService;
	@Autowired
	MailService mailService;
	
	//단어 목록 조회 기능
	@RequestMapping("showlist")
	public String wordListShow(HttpSession session, Model m, String wordbookid) {
		try{
			MemberDto loginMember = (MemberDto)session.getAttribute("loginMember");
			if(loginMember.getCertified() == 0) {
				return "error/certifyPlease";  //미인증 시
			}
			if(wordbookid ==null) {
				throw new NumberFormatException();
			}
			int loginId = loginMember.getId();
			int wordbookId = Integer.parseInt(wordbookid);
			if(loginId <= 20 || loginId == wordbookService.selectWordbookById(wordbookId).getOwnerId()) {
				m.addAttribute("isOwner", true);
			}
			else {
				m.addAttribute("isOwner", false);
			}
		} catch (NumberFormatException | IllegalStateException e) {
			mailService.sendErorrMail(e.toString());
			return "error/wrongAccess";  //잘못된 접근(주소로 직접 접근 등)
		} catch (NullPointerException e) {
			return "error/loginPlease";  //비로그인 시
		} catch (Exception e) {
			mailService.sendErorrMail(e.toString());
			return "error/wrongAccess";
		}
		return "word/wordList";
	}
	
	//단어장 들어가면 처음 펼치는 과정
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
		try {
			int wordbookId = Integer.parseInt(wordbookid);
			if(loginId>21 && wordbookService.selectWordbookById(wordbookId).getOwnerId()==loginId
					&& wordbookService.selectWordbookById(wordbookId).getGuestId()==loginId) {  //관리자, 주인, 공유받은 사람이 아닐 경우
				return "{\"nope\":\"notAllowed\"}";
			}
			String wordbookAddress = wordbookService.selectWordbookById(wordbookId).getWordbookAddress();  //파일 경로
			File file = new File(wordbookAddress);  //파일을 가져옴
			String s;  //readLine을 위한 임시 String
			String ajax = "";  //파일의 내용을 저장할 String
			try(BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"))) {
				while((s=br.readLine()) != null) {
					ajax += s;  //한 줄씩 읽어서 추가
				}
				return ajax;  //파일 내용 반환
			} catch (UnsupportedEncodingException e) {
				mailService.sendErorrMail(e.toString());
			} catch (FileNotFoundException e) {
				mailService.sendErorrMail(e.toString());
				return "{\"nope\":\"notExist\"}";
			} catch (IOException e) {
				mailService.sendErorrMail(e.toString());
			} 
		} catch (NullPointerException e) {  //존재하지 않는 단어장
			mailService.sendErorrMail(e.toString());
			return "{\"nope\":\"notExist\"}";
		} catch (NumberFormatException | IllegalStateException e) {
			mailService.sendErorrMail(e.toString());
			return "{\"nope\":\"wrongAccess\"}";  //잘못된 접근(주소로 직접 접근 등)
		} catch (Exception e) {
			mailService.sendErorrMail(e.toString());
			return "{\"nope\":\"wrongAccess\"}";
		}
		return null;
	}
	
	//장문 추가 페이지
	@GetMapping("form")
	public String insertForm(Model m, String wordbookid) {
		try {
			int wordbookId = Integer.parseInt(wordbookid);
			m.addAttribute("wordbookId", wordbookId);
			m.addAttribute("title", wordbookService.selectWordbookById(wordbookId).getTitle());  //단어장 이름
		} catch (NumberFormatException | IllegalStateException e) {
			return "error/wrongAccess";  //잘못된 접근(주소로 직접 접근 등)
		} catch (Exception e) {
			mailService.sendErorrMail(e.toString());
			return "error/wrongAccess";
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
				return "{\"nope\":\"certifyPlease\"}";  //미인증 시
			}
			loginId = loginMember.getId();  //로그인 아이디 확인
			int wordbookId = Integer.parseInt(wordbookid);
			try {
				if (wordbookService.selectWordbookById(wordbookId).getOwnerId() != loginId) {  //단어장 주인일 아닐경우
					return "{\"nope\":\"notAllowed\"}";
				}
			} catch (NullPointerException e) {  //존재하지 않는 단어장
				mailService.sendErorrMail(e.toString());
				return "{\"nope\":\"notExist\"}";
			} catch (Exception e) {
				mailService.sendErorrMail(e.toString());
				return "{\"nope\":\"wrongAccess\"}";
			}
			String address = wordbookService.selectWordbookById(wordbookId).getWordbookAddress();  //파일 경로
			File origFile = new File(address);  //원래 파일
			if(word!=null) {
				try(BufferedReader br = new BufferedReader(new InputStreamReader(
						new FileInputStream(origFile),"UTF-8"))){  //원래 파일을 불러옴
					JSONParser jParser = new JSONParser();  //json 사용을 위한 객체
					JSONArray jsonArray = (JSONArray)jParser.parse(br);  //파일을 사용할 수 있게 변환
					String origStr = jsonArray.toString();  //원래 파일의 내용
					origStr = origStr.replace(']', ',');  //끝을 더 이어갈 수 있게 변경
					int index = jsonArray.size();  //기존 단어 갯수
					for (int i = 0; i < word.length; i++) {
						if(!word[i].isBlank() || !trans[i].isBlank()) {  //둘 다 공백인게 아니면
							origStr += "{\"index\":" + index + ",\"word\":\"" + word[i] + "\",\"trans\":\""
									+ trans[i] + "\",\"favorite\":" + 0 + "},";
							index++;
						}
					}
					origStr = origStr.substring(0,origStr.length()-1);  //마지막 쉼표 제거
					origStr +="]";  //json 형태 끝
					try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(origFile),"UTF-8"))){
						bw.write(origStr);
						bw.flush();
					}  //원래 파일에서 덮어씀
				} catch (UnsupportedEncodingException e) {
					mailService.sendErorrMail(e.toString());
					return "{\"nope\":\"wrongAccess\"}";
				} catch (FileNotFoundException e) {
					mailService.sendErorrMail(e.toString());
					return "{\"nope\":\"notExist\"}";
				} catch (IOException e) {
					mailService.sendErorrMail(e.toString());
					return "{\"nope\":\"wrongAccess\"}";
				} catch (ParseException e) {
					mailService.sendErorrMail(e.toString());
					return "{\"nope\":\"wrongAccess\"}";
				}
			}
		} catch (NullPointerException e) {  //비로그인 시
			return "{\"nope\":\"loginPlease\"}";
		} catch (NumberFormatException | IllegalStateException e) {
			mailService.sendErorrMail(e.toString());
			return "{\"nope\":\"wrongAccess\"}";  //잘못된 접근(주소로 직접 접근 등)
		} catch (Exception e) {
			mailService.sendErorrMail(e.toString());
			return "{\"nope\":\"wrongAccess\"}";
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
				try{
					wordbookId = Integer.parseInt(wordbookid);
				} catch (NumberFormatException | IllegalStateException e) {  //wordbookid가 숫자가 아닐 경우
					mailService.sendErorrMail(e.toString());
					return "error/wrongAccess";
				}
			}
			JSONParser parser = new JSONParser();  //json을 사용하기 위한 객체
			String regex = "[^-^{A-Z}^{a-z}]+";  //-, 알파벳을 제외한 모든 문자
			String clientId = "FaGdiV_h1RX1lMv2w2tW";// 애플리케이션 클라이언트 아이디값
			String clientSecret = "NhiFrOn9g1";// 애플리케이션 클라이언트 시크릿값
			String apiURL = "https://openapi.naver.com/v1/papago/n2mt";
			Map<String, String> requestHeaders = new HashMap<>();  //api
			requestHeaders.put("X-Naver-Client-Id", clientId);  //api
			requestHeaders.put("X-Naver-Client-Secret", clientSecret);  //api
			String filePath;  //파일 경로
			try {
				filePath = wordbookService.selectWordbookById(wordbookId).getWordbookAddress();
			} catch (Exception e) {
				mailService.sendErorrMail(e.toString());
				return "error/wrongAccess";
			}
			File origFile = new File(filePath);
			JSONParser jParser = new JSONParser();  //json을 사용하기 위한 객체
			if (file != null) {  //파일 업로드 시
				String fileExtension = file.getName().substring(file.getName().lastIndexOf(".") + 1);  //파일 확장자를 뽑아냄
				if (fileExtension.equals("txt")) {  //txt가 맞으면
					try (BufferedReader obr = new BufferedReader(
								new InputStreamReader(new FileInputStream(origFile), "UTF-8"));  //기존 파일 가져옴
							BufferedReader br = new BufferedReader(
									new InputStreamReader(new FileInputStream(file), "UTF-8"))) {  //업로드 파일
						JSONArray jsonArray = (JSONArray)jParser.parse(obr);  //기존 파일 사용가능 형태로 변환
						
						Long[] origIndex = new Long[jsonArray.size()];  //기존 index 배열
						String[] origWord = new String[jsonArray.size()];  //기존 word 배열
						String[] origTrans = new String[jsonArray.size()];  //기존 trans 배열
						Long[] origFavorite = new Long[jsonArray.size()];  //기존 favorite 배열
						int x = 0;
						for (Object o : jsonArray) {
							JSONObject wordObj = (JSONObject) o;  //일반 Object를 json형식으로 변환
							origIndex[x] = (Long) wordObj.get("index");
							origWord[x] = (String) wordObj.get("word");
							origTrans[x] = (String) wordObj.get("trans");
							origFavorite[x] = (Long) wordObj.get("favorite");
							x++;
						}
						int index = jsonArray.size();  //추가하는 단어의 index 시작 값
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
							if (textArr[i] != null && textArr[i].length() > 1) {  //한 글자 단어 무시
								try {
									word = URLEncoder.encode(textArr[i], "UTF-8");  //인코딩
								} catch (UnsupportedEncodingException e) {
									mailService.sendErorrMail(e.toString());
									throw new RuntimeException("인코딩 실패", e);
								}
								count[i] = 1;
								for (int j = i + 1; j < textArr.length; j++) {
									if (textArr[j] != null && textArr[j].equals(textArr[i])) {  //현재 단어 이후
										textArr[j] = null;  //같은 단어 삭제
										count[i]++;  //횟수 증가
									}
								}
								responseBody[i] = post(apiURL, requestHeaders, word);  //api 결과
							} else {
								count[i] = 0;  //null인 경우
							}
						}
						String tempEng;  //순서 변경을 위한 임시 String
						int tempCnt;  //순서 변경을 위한 임시 int
						String tempTrans;  //순서 변경을 위한 임시 String
						String[] transArr = new String[textArr.length];
						for (int i = 0; i < textArr.length; i++) {
							for (int j = 0; j < textArr.length - 1 - i; j++) {
								if (count[j] > count[j + 1]) {
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
						String jsonText="[";  //json 형태 시작
						boolean isContaining;  //기존 단어에 이미 있었는가
						for (int i = 0; i < textArr.length; i++) {
							if (textArr[i] != null && textArr[i].length() > 1) {
								try {
									isContaining=false;
									JSONObject resultJson = (JSONObject) parser.parse(responseBody[i]);  //json으로 변환
									JSONObject message = (JSONObject) resultJson.get("message");  //json의 key(message)로 값 반환
									JSONObject result = (JSONObject) message.get("result");  //json의 key(result)로 값 반환
									transArr[i] = (String) result.get("translatedText");  //해석 배열에 해석값을 담음
									for(int j=0; j<origWord.length; j++) {
										if(textArr[i].equals(origWord[j])) {  //기존에 단어가 이미 있었다면
											textArr[i]=null;  //현재 단어 삭제
											for(String t : origTrans[j].split(", ")) {  //쉼표 후 띄어쓰기 단위로 뜻을 분리
												if(t.equals(transArr[i])) {  //이미 같은 뜻이 있었는지 판단
													isContaining=true;
													break;
												}
											}
											if(!isContaining) {  //새로운 뜻이라면
												origTrans[j] += ", " + transArr[i];  //기존 뜻에 추가
											}
											break;
										}
									}
								} catch (Exception e) {
									mailService.sendErorrMail(e.toString());
								}
							}
						}
						for (int i = 0; i < origWord.length; i++) {  //기존 단어를 먼저 씀
							jsonText += "{\"index\":" + origIndex[i] + ",\"word\":\"" + origWord[i] + "\",\"trans\":\""
									+ origTrans[i] + "\",\"favorite\":" + origFavorite[i] + "},";
						}
						for (int i = 0; i < textArr.length; i++) {  //추가되는 단어를 씀
							if (textArr[i]!=null && !textArr[i].equals(transArr[i])  && textArr[i].length() > 1) {
								jsonText += "{\"index\":" + index + ",\"word\":\"" + textArr[i] + "\",\"trans\":\""
										+ transArr[i] + "\",\"favorite\":" + 0 + "},";
								index++;
							}
						}
						jsonText = jsonText.substring(0, jsonText.length() - 1);  //마지막 쉼표 제거
						jsonText += "]";  //json 마지막 문자
						try(BufferedWriter obw = new BufferedWriter(
									new OutputStreamWriter(new FileOutputStream(origFile), "UTF-8"))){
							obw.write(jsonText);
							obw.flush();
						}  //기존 파일에 덮어씀
						m.addAttribute("wordbookid", wordbookid);
						return "word/wordUpdateComplete";
					} catch (FileNotFoundException e) {
						mailService.sendErorrMail(e.toString());
						return "error/wrongAccess";
					} catch (IOException e) {
						mailService.sendErorrMail(e.toString());
						return "error/wrongAccess";
					} catch (ParseException e) {
						mailService.sendErorrMail(e.toString());
					}
				} else { // 파일 형식이 txt가 아닐 경우
					return "error/wrongFileType"; //``
				}
			}

			else {  //직접 입력
				try (BufferedReader obr = new BufferedReader(
							new InputStreamReader(new FileInputStream(origFile), "UTF-8"))) {
					JSONArray jsonArray = (JSONArray)jParser.parse(obr);  //기존 파일 사용가능 형태로 변환
					int index = jsonArray.size();  //추가하는 단어의 index 시작 값
					/*String jsonText = jsonArray.toString();  //기존 파일 내용 String
					jsonText = jsonText.replace(']', ','); */ //json 끝의 ]를 ,로 변경
					text = text.replaceAll(regex, " ");
					String[] textArr = text.split(" ");
					String word;
					Long[] origIndex = new Long[jsonArray.size()];  //기존 index 배열
					String[] origWord = new String[jsonArray.size()];  //기존 word 배열
					String[] origTrans = new String[jsonArray.size()];  //기존 trans 배열
					Long[] origFavorite = new Long[jsonArray.size()];  //기존 favorite 배열
					int x = 0;
					for (Object o : jsonArray) {
						JSONObject wordObj = (JSONObject) o;  //일반 Object를 json형식으로 변환
						origIndex[x] = (Long) wordObj.get("index");
						origWord[x] = (String) wordObj.get("word");
						origTrans[x] = (String) wordObj.get("trans");
						origFavorite[x] = (Long) wordObj.get("favorite");
						x++;
					}
					int[] count = new int[textArr.length];  //단어 횟수
					String[] responseBody = new String[textArr.length];  //api 결과 배열
					for (int i = 0; i < textArr.length; i++) {
						if (textArr[i] != null && textArr[i].length() > 1) {
							try {
								word = URLEncoder.encode(textArr[i], "UTF-8");  //인코딩
							} catch (UnsupportedEncodingException e) {
								mailService.sendErorrMail(e.toString());
								throw new RuntimeException("인코딩 실패", e);
							}
							count[i] = 1;
							for (int j = i + 1; j < textArr.length; j++) {
								if (textArr[j] != null && textArr[j].equals(textArr[i])) {  //현재 단어 이후
									textArr[j] = null;  //같은 단어 삭제
									count[i]++;  //횟수 증가
								}
							}
							responseBody[i] = post(apiURL, requestHeaders, word);  //api 결과
						} else {
							count[i] = 0;  //null인 경우
						}
					}
					String tempEng;  //순서 변경을 위한 임시 String
					int tempCnt;  //순서 변경을 위한 임시 int
					String tempTrans;  //순서 변경을 위한 임시 String
					String[] transArr = new String[textArr.length];
					for (int i = 0; i < textArr.length; i++) {
						for (int j = 0; j < textArr.length - 1 - i; j++) {
							if (count[j] > count[j + 1]) {
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
					String jsonText="[";  //json 형태 시작
					boolean isContaining;  //기존 단어에 이미 있었는가
					for (int i = 0; i < textArr.length; i++) {
						if (textArr[i] != null && textArr[i].length() > 1) {
							try {
								isContaining=false;
								JSONObject resultJson = (JSONObject) parser.parse(responseBody[i]);  //json으로 변환
								JSONObject message = (JSONObject) resultJson.get("message");  //json의 key(message)로 값 반환
								JSONObject result = (JSONObject) message.get("result");  //json의 key(result)로 값 반환
								transArr[i] = (String) result.get("translatedText");  //해석 배열에 해석값을 담음
								for(int j=0; j<origWord.length; j++) {
									if(textArr[i].equals(origWord[j])) {  //기존에 단어가 이미 있었다면
										textArr[i]=null;  //현재 단어 삭제
										for(String t : origTrans[j].split(", ")) {  //쉼표 후 띄어쓰기 단위로 뜻을 분리
											if(t.equals(transArr[i])) {  //이미 같은 뜻이 있었는지 판단
												isContaining=true;
												break;
											}
										}
										if(!isContaining) {  //새로운 뜻이라면
											origTrans[j] += ", " + transArr[i];  //기존 뜻에 추가
										}
										break;
									}
								}
							} catch (Exception e) {
								mailService.sendErorrMail(e.toString());
							}
						}
					}
					for (int i = 0; i < origWord.length; i++) {  //기존 단어를 먼저 씀
						jsonText += "{\"index\":" + origIndex[i] + ",\"word\":\"" + origWord[i] + "\",\"trans\":\""
								+ origTrans[i] + "\",\"favorite\":" + origFavorite[i] + "},";
					}
					for (int i = 0; i < textArr.length; i++) {  //추가되는 단어를 씀
						if (textArr[i]!=null && !textArr[i].equals(transArr[i]) && textArr[i].length() > 1) {
							jsonText += "{\"index\":" + index + ",\"word\":\"" + textArr[i] + "\",\"trans\":\""
									+ transArr[i] + "\",\"favorite\":" + 0 + "},";
							index++;
						}
					}
					jsonText = jsonText.substring(0, jsonText.length() - 1);  //마지막 쉼표 제거
					jsonText += "]";  //json 마지막 문자
					try(BufferedWriter obw = new BufferedWriter(
								new OutputStreamWriter(new FileOutputStream(origFile), "UTF-8"))){
						obw.write(jsonText);
						obw.flush();
					}  //기존 파일에 덮어씀
					m.addAttribute("wordbookid", wordbookid);
					return "word/wordUpdateComplete";
				} catch (UnsupportedEncodingException e1) {
					mailService.sendErorrMail(e1.toString());
					return "error/wrongAccess";
				} catch (FileNotFoundException e1) {
					mailService.sendErorrMail(e1.toString());
					return "error/wrongAccess";
				} catch (IOException e1) {
					mailService.sendErorrMail(e1.toString());
					return "error/wrongAccess";
				} catch (ParseException e1) {
					mailService.sendErorrMail(e1.toString());
					return "error/wrongAccess";
				}
			}
		}
		return "word/wordUpdateComplete";
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
				return "{\"nope\":\"certifyPlease\"}";  //미인증 시
			}
			loginId = loginMember.getId();  //로그인 아이디 확인
			int wordbookId = Integer.parseInt(wordbookid);
			try {
				if (wordbookService.selectWordbookById(wordbookId).getOwnerId() != loginId) {  //단어장 주인일 아닐경우
					return "{\"nope\":\"notAllowed\"}";
				}
			} catch (NullPointerException e) {  //존재하지 않는 단어장
				mailService.sendErorrMail(e.toString());
				return "{\"nope\":\"notExist\"}";
			} catch (Exception e) {
				mailService.sendErorrMail(e.toString());
				return "{\"nope\":\"wrongAccess\"}";
			}
			String address = wordbookService.selectWordbookById(wordbookId).getWordbookAddress();
			int tempIndex;
			String tempWord;
			String tempTrans;
			int tempFavorite;
			for (int i = 0; i < word.length; i++) {
				for (int j = 0; j < word.length - 1 - i; j++) {
					if (index[j] > index[j + 1]) {
						tempIndex = index[j];
						index[j] = index[j + 1];
						index[j + 1] = tempIndex;
						tempWord = word[j];
						word[j] = word[j + 1];
						word[j + 1] = tempWord;
						tempTrans = trans[j];
						trans[j] = trans[j + 1];
						trans[j + 1] = tempTrans;
						tempFavorite = favorite[j];
						favorite[j] = favorite[j + 1];
						favorite[j + 1] = tempFavorite;
					}
				}
			}
			File origFile = new File(address);
			try(BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(origFile), "UTF-8"))){
				String str = "[";
				for (int i = 0; i < word.length; i++) {
					if(!word[i].isBlank() || !trans[i].isBlank()) {
						str += "{\"index\":" + i + ",\"word\":\"" + word[i] + "\",\"trans\":\""
								+ trans[i] + "\",\"favorite\":" + favorite[i] + "},";
					}
				}
				str = str.substring(0,str.length()-1);
				bw.write(str);
				bw.append(']');
				bw.flush();
			} catch (UnsupportedEncodingException e) {
				mailService.sendErorrMail(e.toString());
				return "{\"nope\":\"wrongAccess\"}";
			} catch (FileNotFoundException e) {
				mailService.sendErorrMail(e.toString());
				return "{\"nope\":\"notExist\"}";
			} catch (IOException e) {
				mailService.sendErorrMail(e.toString());
				return "{\"nope\":\"wrongAccess\"}";
			}
		} catch (NullPointerException e) {  //비로그인 시
			mailService.sendErorrMail(e.toString());
			return "{\"nope\":\"loginPlease\"}";
		} catch (NumberFormatException | IllegalStateException e) {
			mailService.sendErorrMail(e.toString());
			return "{\"nope\":\"wrongAccess\"}";  //잘못된 접근(주소로 직접 접근 등)
		} catch (Exception e) {
			mailService.sendErorrMail(e.toString());
			return "{\"nope\":\"wrongAccess\"}";
		}
		return "{\"message\":\"success\"}";
	}
	
	
	//여기서부턴 api
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
