package br.com.carlohcs.api.bdd;

import br.com.carlohcs.api.model.Message;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class StepDefinition {

    private Response response;

    private Message messageReturned;

    private final String ENDPOINT_MESSAGES = "http://localhost:8080/messages";

    @Given("a new message is registered")
    public Message a_new_message_is_registered() {
        var message = buildMessage();
        response = given()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(message)
                .when()
                    .post(ENDPOINT_MESSAGES);

        return response.then().extract().as(Message.class);
    }

    @When("the message is saved successfully")
    public void the_message_is_saved_successfully() {
        response.then().statusCode(HttpStatus.CREATED.value());
    }

    @Then("the message should be shown")
    public void the_message_should_be_shown() {
        response.then().body("content", is("Hello, World!"));
    }

    private Message buildMessage() {
        return Message.builder()
                .id(UUID.randomUUID())
                .username("carlohcs")
                .content("Hello, World!")
                .build();
    }
}
