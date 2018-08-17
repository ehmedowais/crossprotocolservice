package au.com.auspost.crossprotocolservice.elasticsearch;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@PropertySource("classpath:application.properties")
public class Client {
	@Value("${application.elasticsearch.host}")
	private String host;
	
	@Value("{application.elasticsearch.port}")
	private String port;
	
	public ResponseEntity<String> doConnectionStuff() {
		RestTemplate restTemplate = new RestTemplate();
		String resourceUrl = "http://"+host+":"+port+"/_search?q=product_id:B20";
		ResponseEntity<String> response = restTemplate.getForEntity(resourceUrl, String.class);
		return response;
	}
}
