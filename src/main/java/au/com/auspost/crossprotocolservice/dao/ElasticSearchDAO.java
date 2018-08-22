package au.com.auspost.crossprotocolservice.dao;

import java.util.Base64;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.Client;
import org.springframework.beans.factory.annotation.Autowired;
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
	
	@Autowired
	Client client;
	
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
	
	public UpdateResponse update(String index, String type, String id, String jsonString) {
		UpdateRequest updateRequest = new UpdateRequest();
		updateRequest.docAsUpsert(true);
		updateRequest.index(index);
		updateRequest.type(type);

		updateRequest.id(id);
		updateRequest.doc(jsonString);
		UpdateResponse response = client.update(updateRequest).actionGet();
		return response;
	}
	
	public void deleteIndex(String indexName) {
		DeleteIndexRequest request = new DeleteIndexRequest(indexName);
		DeleteIndexResponse response = client.admin().indices().delete(request).actionGet();
		System.out.println("Delete acknowledged " + response.isAcknowledged());
	}
}
