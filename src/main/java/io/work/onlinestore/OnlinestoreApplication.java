package io.work.onlinestore;

import io.work.onlinestore.util.exception.ResponseExceptionHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(ResponseExceptionHandler.class)
public class OnlinestoreApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(OnlinestoreApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(OnlinestoreApplication.class);
	}
}

