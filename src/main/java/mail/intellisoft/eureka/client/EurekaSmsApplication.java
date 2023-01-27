package mail.intellisoft.eureka.client;

import mail.intellisoft.service.MultipleSubmit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import java.util.Arrays;

@EnableEurekaClient
@SpringBootApplication
public class EurekaSmsApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(EurekaSmsApplication.class);
	}

	public static void main(String[] args) {
		MultipleSubmit multipleSubmit = new MultipleSubmit();
		multipleSubmit.broadcastMessage("Test message from devglan", Arrays.asList("9513059515", "8884377251"));
		SpringApplication.run(EurekaSmsApplication.class, args);
	}

}
