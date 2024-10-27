package br.com.carlohcs.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "br.com.carlohcs.api")
public class RestApiApplication {
    public static void main(String[] args) {

        SpringApplication.run(RestApiApplication.class);
    }
}
