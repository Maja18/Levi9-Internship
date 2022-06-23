package Internship.SocialNetworking;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;



@SpringBootApplication


@EnableWebMvc
@SecurityScheme(name = "javainuseapi", scheme = "bearer", type = SecuritySchemeType.HTTP)
public class SocialNetworkingApplication {

	public static void main(String[] args) {
		SpringApplication.run(SocialNetworkingApplication.class, args);
	}

}
