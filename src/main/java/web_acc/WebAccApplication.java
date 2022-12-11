package web_acc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import web_acc.entity.User;

@SpringBootApplication
public class WebAccApplication {

	public static void main(String[] args) {
		User user = new User();

		SpringApplication.run(WebAccApplication.class, args);
	}

}
