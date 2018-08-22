package au.com.auspost.crossprotocolservice.batch;

import java.io.File;

import com.fasterxml.jackson.databind.JsonNode;

public interface FileReader {
	public JsonNode readFileAsJsonNode(String path) throws Exception;
	public JsonNode readFileAsJsonNode(File file) throws Exception;
}
