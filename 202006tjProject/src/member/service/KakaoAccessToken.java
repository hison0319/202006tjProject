package member.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class KakaoAccessToken {
	public static JsonNode getKakaoAccessToken(String code) {
		
		final String RequestUrl = "https://kauth.kakao.com/oauth/token"; //Host
		final List<NameValuePair> postParams = new ArrayList<NameValuePair>();
		
		postParams.add(new BasicNameValuePair("grant_type", "authorization_code"));
		postParams.add(new BasicNameValuePair("client_id", "e4e1b163f0c81274eec3c688bcfe3f54"));
		postParams.add(new BasicNameValuePair("redirect_uri", "http://localhost:8080/kakaologin"));
		postParams.add(new BasicNameValuePair("code", code)); //로그인 과정 중 얻은 코드
		
		final HttpClient client = HttpClientBuilder.create().build();
		final HttpPost post = new HttpPost(RequestUrl);
			
		JsonNode returnNode = null;
		
		try {
			post.setEntity(new UrlEncodedFormEntity(postParams));
			
			final HttpResponse response = client.execute(post);
			
			//JSON형태 반환값 처리
			ObjectMapper mapper = new ObjectMapper();
			
			returnNode = mapper.readTree(response.getEntity().getContent());
		} catch(UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return returnNode;
	}
}
