package deti.tqs.carInfoSystem.controllers;

import deti.tqs.carInfoSystem.entities.Car;
import deti.tqs.carInfoSystem.repositories.CarRepository;
import deti.tqs.utils.JsonUtil;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import static io.restassured.RestAssured.given;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class CarControllerRestAssuredRealDB {

    private static final String BASE_URL = "http://localhost:";

    @LocalServerPort
    int randomServerPort;

    @Autowired
    private CarRepository carRepository;

    @Container
    public static PostgreSQLContainer container = new PostgreSQLContainer("postgres:12")
            .withUsername("duke")
            .withPassword("password")
            .withDatabaseName("test");

    @DynamicPropertySource
    static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.password", container::getPassword);
        registry.add("spring.datasource.username", container::getUsername);
    }

    @AfterEach
    public void resetDb() {
        carRepository.deleteAll();
    }

    @Test
    public void whenValidInputCar_ThenCreateCarAndSaveIt() throws IOException {
        Car tesla = new Car("Tesla", "Model S");
        carRepository.saveAndFlush(tesla);

        given()
                .header("Content-Type", "application/json")
                .body(JsonUtil.toJson(tesla))
                .post(getBaseUrl() + "/api/car")
                .then().assertThat().statusCode(201)
                .and().body("maker", equalTo(tesla.getMaker()))
                .and().body("model", equalTo(tesla.getModel()));
    }

    @Test
    public void when3CarsAreSaved_ThenFindAllReturns3Records() {
        Car tesla = new Car("Tesla", "Model S");
        Car opel = new Car("Opel", "Corsa");
        Car mercedes = new Car("Mercedes", "Preto");

        carRepository.saveAndFlush(tesla);
        carRepository.saveAndFlush(opel);
        carRepository.saveAndFlush(mercedes);


        given().when()
                .get(getBaseUrl() + "/api/car")
                .then()
                .assertThat().statusCode(200)
                .and().body("", hasSize(3))
                .and().body("maker[0]", is(tesla.getMaker()))
                .and().body("maker[1]", is(mercedes.getMaker()));

    }

    @Test
    public void whenGetCarByIdExists_ThenReturnCar() {
        Car tesla = new Car("Tesla", "Model S");

        long carId = carRepository.saveAndFlush(tesla).getCarId();
        String url = getBaseUrl() + "/api/car/" + carId;

        given().when()
                .get(url)
                .then().assertThat().statusCode(200)
                .and().body("maker", equalTo(tesla.getMaker()))
                .and().body("model", equalTo(tesla.getModel()));

    }

    @Test
    public void whenGetCarByIdNotExists_ThenReturnNotFoundResponse() {
        given().when()
                .get(getBaseUrl() + "/api/car/22")
                .then().assertThat().statusCode(404);
    }


    public String getBaseUrl() {
        return BASE_URL + randomServerPort;

    }

}

