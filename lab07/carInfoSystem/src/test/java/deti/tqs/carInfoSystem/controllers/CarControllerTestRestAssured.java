package deti.tqs.carInfoSystem.controllers;

import deti.tqs.carInfoSystem.entities.Car;
import deti.tqs.carInfoSystem.services.CarManagerService;
import deti.tqs.utils.JsonUtil;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@WebMvcTest(controllers = CarRestController.class)
public class CarControllerTestRestAssured {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CarManagerService service;

    @Test
    public void whenPostToCreateCar_ThenCreateCar() throws Exception {
        Car tesla = new Car("Tesla", "Model S");

        Mockito.when(service.save(Mockito.any())).thenReturn(tesla);

        RestAssuredMockMvc.mockMvc(mvc);

        RestAssuredMockMvc.given()
                .header("Content-Type", "application/json")
                .body(JsonUtil.toJson(tesla))
                .post("/api/car")
                .then().assertThat().statusCode(201)
                .and().body("maker", CoreMatchers.equalTo(tesla.getMaker()))
                .and().body("model", CoreMatchers.equalTo(tesla.getModel()));

        Mockito.verify(service, Mockito.times(1)).save(Mockito.any());
    }

    @Test
    public void returnAllCarsTest() {
        Car tesla = new Car("Tesla", "Model S");
        Car mercedes = new Car("Mercedes", "Preto");
        Car opel = new Car("Opel", "Corsa");

        List<Car> stand = Arrays.asList(tesla, mercedes, opel);

        Mockito.when(service.getAllCars()).thenReturn(stand);
        RestAssuredMockMvc.mockMvc(mvc);

        RestAssuredMockMvc.given()
                .when()
                .get("/api/car")
                .then()
                .assertThat()
                .statusCode(200)
                .and().body("", Matchers.hasSize(3))
                .and().body("maker[0]", CoreMatchers.is(tesla.getMaker()))
                .and().body("maker[1]", CoreMatchers.is(mercedes.getMaker()));

        Mockito.verify(service, Mockito.times(1)).getAllCars();
    }

    @Test
    public void returnCarTest() {
        Car mercedes = new Car("Mercedes", "Preto");
        mercedes.setCarId(1L);

        Mockito.when(service.getCarDetails(1L)).thenReturn(Optional.of(mercedes));
        RestAssuredMockMvc.mockMvc(mvc);

        RestAssuredMockMvc.given()
                .when()
                .get("/api/car/1")
                .then()
                .assertThat()
                .statusCode(200)
                .and().body("maker", CoreMatchers.is(mercedes.getMaker()));

        Mockito.verify(service, Mockito.times(1)).getCarDetails(1L);
    }
}
