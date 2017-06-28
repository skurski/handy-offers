package pl.edu.agh.handy.offers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * Spring Boot application main method.
 *
 * Execution of main method fires up embedded servlet container (Tomcat).
 * Check properties values in application.properties file and also
 * SpringConfig class in config package for configuration details.
 *
 * Spring Servlet Initializer is used for external Tomcat deployment (production).
 */
@SpringBootApplication
public class HandyOffersWebAppApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(applicationClass, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(applicationClass);
	}

	private static Class<HandyOffersWebAppApplication> applicationClass = HandyOffersWebAppApplication.class;
}