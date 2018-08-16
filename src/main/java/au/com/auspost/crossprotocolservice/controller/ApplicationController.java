package au.com.auspost.crossprotocolservice.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import au.com.auspost.crossprotocolservice.util.CSV;

@Controller
public class ApplicationController {
	private String csvFile = "/Users/muhammadahmed/work/training/crossprotocolservice/src/main/resources/test.csv";

	// @GetMapping("/resources/{resource}")
	@RequestMapping(value = "/resources", method = RequestMethod.GET, produces = 
		{org.springframework.http.MediaType.TEXT_PLAIN_VALUE,
		org.springframework.http.MediaType.APPLICATION_JSON_VALUE,
		org.springframework.http.MediaType.APPLICATION_XML_VALUE})
//			produces = { 
//					"application/xml",
//					"application/json", 
//					"text/html" 
//					})
	@ResponseBody
	public List<Map<String, String>> getResources() {
		
		try {
			List<Map<String, String>> list = doSomething();
			return list;
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	private List<Map<String, String>> doSomething() throws UnsupportedEncodingException, IOException {
		try (InputStream in = new FileInputStream(csvFile);) {
			CSV csv = new CSV(true, ',', in);
			List<String> fieldNames = null;
			if (csv.hasNext())
				fieldNames = new ArrayList<>(csv.next());
			List<Map<String, String>> list = new ArrayList<>();
			while (csv.hasNext()) {
				List<String> x = csv.next();
				Map<String, String> obj = new LinkedHashMap<>();
				for (int i = 0; i < fieldNames.size(); i++) {
					obj.put(fieldNames.get(i), x.get(i));
				}
				list.add(obj);
			}
			return list;
		}
	}
}
