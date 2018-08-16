package au.com.auspost.crossprotocolservice.streamreader;

public interface StreamReader {

	
	default String readFile() {
		return null;
	}
}
