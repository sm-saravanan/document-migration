package com.veeva.document.migration.api.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.veeva.document.migration.model.WonderDrugDocument;

/**
 * Client program to create document into Veeva Vault using API
 * @author Saran
 *
 */
@Component
public class DocumentClient {
	
	@Autowired
	AuthClient auth;
	@Value("${veeva.api.document.url}")
	String documentURL;
	
	private String sessionId;
	public DocumentClient() { 
	   System.out.println(sessionId);
	  
	}
	public String upload(WonderDrugDocument document) {
	    sessionId = auth.getSessionId();

		MultiValueMap<String, Object> body
		  = new LinkedMultiValueMap<>();
		body.add("file", getDocument(document.getFilePath()));
		body.add("external_id__v", document.getDocumentID());
		body.add("name__v", document.getDocumentName());
		body.add("type__v", document.getType());
		body.add("subtype__v", document.getSubtype());
		body.add("external_approved_date__c", document.getApprovedDate());

		// Following Attributes should be derived based on the business rules or load them from database configuration if its static	
		
		body.add("lifecycle__v", "Draft to Effective Lifecycle");
		body.add("country__v", "unitedStates");
		body.add("training_impact__c", "true");
		body.add("owning_department__v", "00D000000000201");
		body.add("owning_facility__v", "00F000000000101");
		body.add("department__v", "00D000000000201");

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", sessionId);
		headers.add("Content-Type", MediaType.MULTIPART_FORM_DATA_VALUE);
		headers.add("Accept", "*/*");
		HttpEntity<MultiValueMap<String, Object>> requestEntity
		 = new HttpEntity<>(body, headers);

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate
		  .postForEntity(documentURL, requestEntity, String.class);
		return response.getBody();
	}
	
	private FileSystemResource getDocument(String path) {
		FileSystemResource resource = new FileSystemResource(path);
		return resource;
	}

}
