package au.com.auspost.crossprotocolservice.batch;

import com.fasterxml.jackson.databind.JsonNode;

public interface FileReader {
	public JsonNode readFileAsJsonNode(String path) throws Exception;
}
