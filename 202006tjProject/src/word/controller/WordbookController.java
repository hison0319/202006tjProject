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
import java.util.HashMap;
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

import member.dto.MemberDto;
import word.dto.WordbookDto;
import word.service.WordbookService;

@Controller
@RequestMapping("wordbook")
public class WordbookController {
	@Autowired
	WordbookService wordbookService;
	//단어장 목록 조회 기능
	@GetMapping("showlist")
	public String wordbookListShow() {
		return "wordbook/wordbookList";
	}
	
	//단어장 생성 페이지
	@GetMapping("form")
	public String wordbookForm(HttpSession session) {
		MemberDto loginMember = (MemberDto) session.getAttribute("loginMember");
		if(loginMember == null) {
			return "member/loginPlease";
		}
		else if(loginMember.getCertified()==0) {
			return "account/certifyPlease";
		}
		return "wordbook/wordbookUpdateForm";
	}
	
	@PostMapping("complete")
	public String wordbookInsert(HttpSession session, Model m, String title, @RequestParam(required = false) String text, @RequestParam(required = false) File file) throws IOException {
		String clientId = "FaGdiV_h1RX1lMv2w2tW";//애플리케이션 클라이언트 아이디값";
        String clientSecret = "NhiFrOn9g1";//애플리케이션 클라이언트 시크릿값";
        String apiURL = "https://openapi.naver.com/v1/papago/n2mt";
        MemberDto loginMember = (MemberDto) session.getAttribute("loginMember");
        JSONParser parser = new JSONParser();
		String regex = "[^-^{A-Z}^{a-z}]+";
		String today = LocalDateTime.now().format(DateTimeFormatter.ofPattern("YYYY_MM_dd"));
		String now = LocalDateTime.now().format(DateTimeFormatter.ofPattern("hh_mm_ss"));
		Path relativePath = Paths.get("");
		File path = new File("..\\eclipse-workspace\\jsonFiles\\"+ today);
		File json = new File("..\\eclipse-workspace\\jsonFiles\\" + today + "\\" + now + ".json");
		path.mkdirs();
		String jsonText = "{";
		
		if(loginMember == null) {
			return "member/loginPlease";
		}
		else if(loginMember.getCertified()==0) {
			return "account/certifyPlease";
		}
		else {
			if (file != null) {
				String fileExtension = file.getName().substring(file.getName().lastIndexOf(".") + 1);
				if (fileExtension.equals("txt")) {
					try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "MS949"));
							BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(json), "MS949"))) {
						String s;
						String fileText = "";
						while ((s = br.readLine()) != null) {
							fileText += (s + " ");
						}
						fileText = fileText.replaceAll(regex, " ");
						String[] textArr = fileText.split(" ");
						String word;
						String[] responseBody = new String[textArr.length];
						for (int i = 0; i < textArr.length; i++) {
							try {
								word = URLEncoder.encode(textArr[i], "UTF-8");
							} catch (UnsupportedEncodingException e) {
								throw new RuntimeException("인코딩 실패", e);
							}
							
							Map<String, String> requestHeaders = new HashMap<>();
							requestHeaders.put("X-Naver-Client-Id", clientId);
							requestHeaders.put("X-Naver-Client-Secret", clientSecret);
							
							responseBody[i] = post(apiURL, requestHeaders, word);
							try {
								JSONObject resultJson = (JSONObject) parser.parse(responseBody[i]);
								JSONObject message = (JSONObject) resultJson.get("message");
								JSONObject result = (JSONObject) message.get("result");
								if(!textArr[i].equals(result.get("translatedText")) && textArr[i].length() >1) {
									jsonText += "\""+textArr[i]+"\":\""+result.get("translatedText")+"\",";
								}
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
						jsonText = jsonText.substring(0, jsonText.length()-1);
						jsonText += "}";
						bw.write(jsonText);
						bw.flush();
						
						m.addAttribute("text", jsonText);
						
						wordbookService.insertWordbook(
								new WordbookDto(0, loginMember.getId(), 0, 0, title
										, relativePath.toAbsolutePath().getParent() + "\\eclipse-workspace\\jsonFiles\\"
												+ today + "\\" + now + ".json"));
						return "wordbook/wordbookUpdateComplete";
					} catch (FileNotFoundException e) {
						e.printStackTrace();
						return null;
					} catch (IOException e) {
						e.printStackTrace();
						return null;
					}
				} else {  //파일 형식이 txt가 아닐 경우
					return "error";
				}
			}
			
			else {
				try(BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(json), "MS949"))){
					text = text.replaceAll(regex, " ");
					String[] textArr = text.split("\\s");
					String word;
					String[] responseBody = new String[textArr.length];
					for (int i = 0; i < textArr.length; i++) {
						try {
							word = URLEncoder.encode(textArr[i], "UTF-8");
						} catch (UnsupportedEncodingException e) {
							throw new RuntimeException("인코딩 실패", e);
						}
						
						Map<String, String> requestHeaders = new HashMap<>();
						requestHeaders.put("X-Naver-Client-Id", clientId);
						requestHeaders.put("X-Naver-Client-Secret", clientSecret);
						
						responseBody[i] = post(apiURL, requestHeaders, word);
						try {
							JSONObject resultJson = (JSONObject) parser.parse(responseBody[i]);
							JSONObject message = (JSONObject) resultJson.get("message");
							JSONObject result = (JSONObject) message.get("result");
							if(!textArr[i].equals(result.get("translatedText")) && textArr[i].length() >1) {
								jsonText += "\""+textArr[i]+"\":\""+result.get("translatedText")+"\",";
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					jsonText = jsonText.substring(0, jsonText.length()-1);
					jsonText += "}";
					bw.write(jsonText);
					bw.flush();
					
					m.addAttribute("text", jsonText);
					wordbookService.insertWordbook(
							new WordbookDto(0, loginMember.getId(), 0, 0, title
									, relativePath.toAbsolutePath().getParent() + "\\eclipse-workspace\\jsonFiles\\"
											+ today + "\\" + now + ".json"));
					return "wordbook/wordbookUpdateComplete";
					
				}
			}
		}
	}
	
	private static String post(String apiUrl, Map<String, String> requestHeaders, String text){
        HttpURLConnection con = connect(apiUrl);
        String postParams = "source=en&target=ko&text=" + text; //원본언어: 한국어 (ko) -> 목적언어: 영어 (en)
        try {
            con.setRequestMethod("POST");
            for(Map.Entry<String, String> header :requestHeaders.entrySet()) {
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
            } else {  // 에러 응답
                return readBody(con.getErrorStream());
            }
        } catch (IOException e) {
            throw new RuntimeException("API 요청과 응답 실패", e);
        } finally {
            con.disconnect();
        }
    }

    private static HttpURLConnection connect(String apiUrl){
        try {
            URL url = new URL(apiUrl);
            return (HttpURLConnection)url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
        } catch (IOException e) {
            throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
        }
    }

    private static String readBody(InputStream body){
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
