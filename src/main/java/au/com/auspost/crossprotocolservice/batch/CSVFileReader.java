package au.com.auspost.crossprotocolservice.batch;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;


public class CSVFileReader implements FileReader {

	public JsonNode readFileAsJsonNode(String path) throws Exception {
		
		File input = new File(path);
		

		CsvSchema csvSchema = CsvSchema.builder().setUseHeader(true).build();
		CsvMapper csvMapper = new CsvMapper();

		// Read data from CSV file
		List<Object> readAll = csvMapper.readerFor(Map.class).with(csvSchema).readValues(input).readAll();

		ObjectMapper mapper = new ObjectMapper();
		JsonNode node = mapper.readTree(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(readAll));
		return node;
	}
	
}
