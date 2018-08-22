package au.com.auspost.crossprotocolservice.batch;

import java.io.File;
import java.io.IOException;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class JSONFileReader implements FileReader{

	public JsonNode readFileAsJsonNode(String filePath) throws JsonProcessingException, IOException {
		// TODO Auto-generated method stub
		
		File file = new File(filePath);
		ObjectMapper jsonReader = new ObjectMapper();		 
		return jsonReader.readTree(file);
	}
	public JsonNode readFileAsJsonNode(File file) throws JsonProcessingException, IOException {
		// TODO Auto-generated method stub
				
		ObjectMapper jsonReader = new ObjectMapper();		 
		return jsonReader.readTree(file);
	}
}
