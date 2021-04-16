package deti.tqs.carInfoSystem.controllers;

import deti.tqs.carInfoSystem.entities.Car;
import deti.tqs.carInfoSystem.repositories.CarRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@AutoConfigureTestDatabase
@TestPropertySource(locations = "applicationTest.properties")
public class CarRestControllerTemplateIT {

    @LocalServerPort
    int randomServerPort;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CarRepository carRepository;

    @AfterEach
    public void close() {
        carRepository.deleteAll();
    }

    // test create car
    @Test
    public void whenValidInputCar_ThenCreateCarAndSaveIt() {
        Car tesla = new Car("Tesla", "Model S");

        ResponseEntity<Car> response = restTemplate.postForEntity(
                "/api/car", tesla, Car.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        Optional<Car> carFound = carRepository.findByCarId(response.getBody().getCarId());
        assertThat(carFound.get().getMaker()).isEqualTo(tesla.getMaker());
    }

    // test getAllCars
    @Test
    public void when3CarsAreSaved_ThenFindAllReturns3Records() {
        Car tesla = new Car("Tesla", "Model S");
        Car opel = new Car("Opel", "Corsa");
        Car mercedes = new Car("Mercedes", "Preto");

        carRepository.saveAndFlush(tesla);
        carRepository.saveAndFlush(opel);
        carRepository.saveAndFlush(mercedes);

        ResponseEntity<List<Car>> responseCars = restTemplate.exchange(
                "/api/car", HttpMethod.GET, null, new ParameterizedTypeReference<List<Car>>() {
                });

        assertThat(responseCars.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseCars.getBody()).extracting(Car::getMaker).containsExactly("Tesla", "Opel", "Mercedes");
    }

    // test existing id from getCarById
    @Test
    public void whenGetCarByIdExists_ThenReturnCar() {
        Car tesla = new Car("Tesla", "Model S");

        long carId = carRepository.saveAndFlush(tesla).getCarId();
        String url = "/api/car/" + String.valueOf(carId);

        ResponseEntity<Car> response = restTemplate.getForEntity(
            url, Car.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody().getMaker()).isEqualTo(tesla.getMaker());
    }

    // test with not existing id from getCarById
    @Test
    public void whenGetCarByIdNotExists_ThenReturnNotFoundResponse() {
        ResponseEntity<Car> response = restTemplate.getForEntity(
                "/api/car/22", Car.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(response.getBody()).isNull();
    }
}
