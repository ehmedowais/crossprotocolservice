package au.com.auspost.crossprotocolservice.dao;

import java.util.Base64;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import au.com.auspost.crossprotocolservice.dto.GenericResponse;

@Repository
public class ElasticSearchDAO {
	@Value("${elastic.jdbc.uri}")	
	private String resourceUri;
	public List<Map<String,String>> find(String index, String criteria) throws Exception {
		if(!StringUtils.isEmpty(criteria)) {
			criteria = new String(Base64.getDecoder().decode(criteria));
			criteria = " WHERE " + criteria;
		}else {
			criteria ="";
		}
		String queryString = "{\n\t\"query\" : \"SELECT * FROM "+ index + criteria +" \"\n}";
		
		RestTemplate restTemplate = new RestTemplate();
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		HttpEntity<String> request = new HttpEntity<String>(queryString, headers);
		
		ResponseEntity<GenericResponse> responseX = restTemplate.postForEntity( resourceUri,  request , GenericResponse.class );
		return responseX.getBody().getJsonObject();
	}
}
