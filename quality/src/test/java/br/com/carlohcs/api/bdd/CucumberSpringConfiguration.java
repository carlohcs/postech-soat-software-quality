package br.com.carlohcs.api.bdd;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@CucumberContextConfiguration
// To run the Cucumber tests,
// it's necessary to run the application and after, run the cucumber suite tests
public class CucumberSpringConfiguration {
}
