package au.com.auspost.crossprotocolservice.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import au.com.auspost.crossprotocolservice.dao.ElasticSearchDAO;
import au.com.auspost.crossprotocolservice.dto.GenericResponse;
import au.com.auspost.crossprotocolservice.util.CSV;

@Controller
@PropertySource("classpath:application.properties")
public class ApplicationController {
	@Autowired
	private ElasticSearchDAO searchDao;
	
	private String csvFile = "/Users/muhammadahmed/work/training/crossprotocolservice/src/main/resources/test.csv";
	@Value("${elastic.jdbc.uri}")	
	private String resourceUri;
	
	@RequestMapping(value = "/resourcesX", method = RequestMethod.GET, produces = 
		{org.springframework.http.MediaType.TEXT_PLAIN_VALUE,
		org.springframework.http.MediaType.APPLICATION_JSON_VALUE,
		org.springframework.http.MediaType.APPLICATION_XML_VALUE})
					
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
	
	
	
	@RequestMapping(value = "/resources/{index}", method = RequestMethod.POST, produces = 
		{org.springframework.http.MediaType.TEXT_PLAIN_VALUE,
		org.springframework.http.MediaType.APPLICATION_JSON_VALUE,
		org.springframework.http.MediaType.APPLICATION_XML_VALUE})
	@ResponseBody
	public List<Map<String,String>> findSingle(@PathVariable("index") String index, @RequestParam(name="whereClause", required=false) String whereClause) throws Exception {

		return searchDao.find(index, whereClause);
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
