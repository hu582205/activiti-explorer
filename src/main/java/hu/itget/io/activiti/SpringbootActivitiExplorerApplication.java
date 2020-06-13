package hu.itget.io.activiti;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = { "org.activiti.rest", "hu.itget" }, exclude = {
		org.activiti.spring.boot.SecurityAutoConfiguration.class,
		org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class })
public class SpringbootActivitiExplorerApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringbootActivitiExplorerApplication.class, args);
	}

}
