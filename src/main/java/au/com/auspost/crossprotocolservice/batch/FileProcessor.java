package au.com.auspost.crossprotocolservice.batch;

import java.io.File;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.JsonNode;

import au.com.auspost.crossprotocolservice.dao.ElasticSearchDAO;
@Component
public class FileProcessor {
	
	@Value("${application.inputDir}")
	private String readFrom;
	@Value("${application.processedDir}")
	private String processedDir;
	@Autowired
	private ElasticSearchDAO elasticSearchDAO;
	
	@Scheduled(fixedRate=500)
	public void process() {
		File inputFolder = new File(readFrom);
		File[] files = inputFolder.listFiles();
		List<File> filesList = Arrays.asList(files);
		filesList.forEach(file -> {
			String fileName = FilenameUtils.getBaseName(file.getName());
			String fileExtention = FilenameUtils.getExtension(file.getName());
			
			FileReader fileReader = FileReaderFactory.getInstance(fileExtention);
			try {
				JsonNode jsonNode = fileReader.readFileAsJsonNode(file);
				Iterator<JsonNode> it = jsonNode.iterator();
				//elasticSearchDAO.deleteIndex(fileName);
				int id = 1;
				while(it.hasNext()) {
					elasticSearchDAO.update(fileName, fileExtention,(id++)+"", it.next().toString());
				}
				FileUtils.moveFileToDirectory(
					      FileUtils.getFile(file.getAbsolutePath()), 
					      FileUtils.getFile(processedDir+"/"+file.getName()), true);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
	}
}
