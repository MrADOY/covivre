package fr.istv.covivre.covivre;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class CovivreApplication {

	public static void main(String[] args) {
		SpringApplication.run(CovivreApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/uuid/").allowedOrigins("*");
				registry.addMapping("/uuid/temporary-token").allowedOrigins("*");
				registry.addMapping("/users/alert-users").allowedOrigins("*");
			}
		};
	}

}
