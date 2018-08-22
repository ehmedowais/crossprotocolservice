package au.com.auspost.crossprotocolservice.batch;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class XMLFileReader implements FileReader {

	@Override
	public JsonNode readFileAsJsonNode(String path) throws Exception {
		File file = new File(path);
		return readFileAsJsonNode(file);
	}

	@Override
	public JsonNode readFileAsJsonNode(File file) throws Exception {
		// TODO Auto-generated method stub
		XmlMapper xmlMapper = new XmlMapper();
		String xml = inputStreamToString(new FileInputStream(file));
		JsonNode node = xmlMapper.readTree(xml.getBytes());
		return node;
	}
	private String inputStreamToString(InputStream is) throws IOException {
	    StringBuilder sb = new StringBuilder();
	    String line;
	    BufferedReader br = new BufferedReader(new InputStreamReader(is));
	    while ((line = br.readLine()) != null) {
	        sb.append(line);
	    }
	    br.close();
	    return sb.toString();
	}
}
