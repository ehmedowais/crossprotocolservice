package au.com.auspost.crossprotocolservice.batch;

public class FileReaderFactory {
	private FileReaderFactory() {
	}

	private static CSVFileReader csvFileReader = new CSVFileReader();
	private static JSONFileReader jsonFileReader = new JSONFileReader();

	public static FileReader getInstance(String fileReaderType) {

		switch (fileReaderType) {
		case "csv":
			return csvFileReader;

		case "json":
			return jsonFileReader;
		default:
			return null;
		}
	}
}
