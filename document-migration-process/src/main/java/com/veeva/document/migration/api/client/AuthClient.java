package com.veeva.document.migration.api.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

/**
 * Authorization Class
 * 
 * @author Saran
 *
 */
@Component
public class AuthClient {
	@Value("${veeva.api.user}")
	private String username;
	@Value("${veeva.api.password}")
	private String password;
	@Value("${veeva.api.auth.url}")
	private String authUrl;
	/** 
	 * This method is used for getting session Id from Veeva Auth Service
	 * TODO: Call this method only once during migration and based on the expiry
	 * @return String
	 */
	public String getSessionId() {
		String sessionId = "";
		try {
			RestTemplate restTemplate = new RestTemplate();
			MultiValueMap<String, Object> postParameters = new LinkedMultiValueMap<>();
			postParameters.add("username", username);
			postParameters.add("password", password);
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content-Type", "application/x-www-form-urlencoded");
			HttpEntity<MultiValueMap<String, Object>> r = new HttpEntity<>(postParameters, headers);
			AuthResponse responseMessage = restTemplate.postForObject(authUrl, r, AuthResponse.class);
			if (responseMessage.getResponseStatus().equalsIgnoreCase("SUCCESS")) {
				sessionId = responseMessage.getSessionId();
			}
			
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		return sessionId;
	}

}
