package member.service;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class KakaoUserInfo {
	public static JsonNode getKakaoUserInfo(JsonNode accessToken) {
		final String RequestUrl = "https://kapi.kakao.com/v2/user/me";
		final HttpClient client = HttpClientBuilder.create().build();
		final HttpPost post = new HttpPost(RequestUrl);
		
		//add header
		post.addHeader("Authorization", "Bearer " + accessToken);
		
		JsonNode returnNode = null;
		
		//AccessToken을 통해 발급받은 토큰을 RequestUrl으로 보내고 사용자 정보를 json형태로 받아옴.
		try {
			final HttpResponse response = client.execute(post);
			
			//JSON형태 반환값 처리
			ObjectMapper mapper = new ObjectMapper();
			returnNode = mapper.readTree(response.getEntity().getContent());
			
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			//Clear resources
		}
		//다시 json형태로 반환
		return returnNode;
	}
}
