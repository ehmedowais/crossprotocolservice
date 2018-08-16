package au.com.auspost.crossprotocolservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
//@ComponentScan(basePackages= {"au.com.auspost.crossprotocolservice","au.com.auspost.crossprotocolservice.config"})
public class CrossprotocolserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrossprotocolserviceApplication.class, args);
	}
}
