package com.ecommerceMicro.order_service;

import com.ecommerceMicro.order_service.utilities.InventoryStubs;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.shaded.org.hamcrest.Matchers;

import static org.testcontainers.shaded.org.hamcrest.MatcherAssert.assertThat;

@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWireMock(port = 0)
class OrderServiceApplicationTests {

    @Test
    void contextLoads() {
    }

    @ServiceConnection
    static MySQLContainer mySQLContainer = new MySQLContainer("mysql:8.3.0");

    @LocalServerPort
    private Integer port;

    @BeforeEach
    void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    static {
        mySQLContainer.start();
    }

//    @Test
//    void shouldSubmitOrder() {
//        String submitOrderJson = """
//                				{
//                				"skuCode":"iphone_15",
//                				"price":1000,
//                				"quantity":1
//                				}
//                """;
//
//
//        var responseBodyString = RestAssured.given()
//                .contentType("application/json")
//                .body(submitOrderJson)
//                .when()
//                .post("/api/order")
//                .then()
//                .log()
//                .all()
//                .statusCode(201)
//                .extract()
//                .body().asString();
//        assertThat(responseBodyString, Matchers.is("Order Placed Successfully"));
//
//    }
    @Test
    void shouldSubmitOrder() {
        String submitOrderJson = """
                				{
                				"skuCode":"iphone_15",
                				"price":1000,
                				"quantity":1
                				}
                """;


        InventoryStubs.stubInventoryCall("iphone_15", 1);
        var responseBodyString = RestAssured.given()
                .contentType("application/json")
                .body(submitOrderJson)
                .when()
                .post("/api/order")
                .then()
                .log().all()
                .statusCode(201)
                .extract()
                .body().asString();
        assertThat(responseBodyString, Matchers.is("Order Placed Successfully"));

    }
}
