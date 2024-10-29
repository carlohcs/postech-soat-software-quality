package br.com.carlohcs.api.performance;

import io.gatling.javaapi.core.ActionBuilder;

import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import java.time.Duration;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.internal.HttpCheckBuilders.status;

// How to test:
// make performance-test
// After, open:
// open target/gatling/performancesimulation-20241029011840489/index.html
public class PerformanceSimulation extends Simulation {

    private final HttpProtocolBuilder httpProtocol = http.baseUrl("http://localhost:8080")
            .header("Content-Type", "application/json");

    ActionBuilder registerMessage = http("Register Message")
            .post("/messages")
            .body(StringBody("{\"content\": \"Hello Gatling\", \"username\": \"carlohcs\"}"))
            .check(status().is(201))
            .check(jsonPath("$.id").saveAs("messageId"));

    ScenarioBuilder scenarioRegisterMessage = scenario("Register Message")
            .exec(registerMessage);

    {
        setUp(
                scenarioRegisterMessage.injectOpen(
                                rampUsersPerSec(1).to(10).during(Duration.ofSeconds(10)),
                                constantUsersPerSec(10).during(Duration.ofSeconds(20)),
                                rampUsersPerSec(1).to(10).during(Duration.ofSeconds(10))
                        )
                        .protocols(httpProtocol)
        )
                .assertions(
                        global().responseTime().max().lt(1000)
//                        global().successfulRequests().percent().gt(95)
                );
    }
}
