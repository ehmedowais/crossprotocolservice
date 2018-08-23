package au.com.auspost.crossprotocolservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@ComponentScan(basePackages= {"au.com.auspost.crossprotocolservice"})
public class CrossprotocolserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrossprotocolserviceApplication.class, args);
	}
}
