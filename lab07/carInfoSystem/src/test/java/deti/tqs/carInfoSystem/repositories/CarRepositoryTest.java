package deti.tqs.carInfoSystem.repositories;

import deti.tqs.carInfoSystem.entities.Car;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class CarRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private CarRepository carRepository;

    @AfterEach
    public void close() {
        entityManager.clear();
    }

    // test when a car is flushed to db if he is returned
    @Test
    public void whenFindByCarIdExists_ThenReturnCar() {
        Car tesla = new Car("Tesla", "Model X");
        Car teslaFlushed = entityManager.persistAndFlush(tesla);

        Optional<Car> teslaFound = carRepository.findByCarId(teslaFlushed.getCarId());
        assertThat(teslaFound.get()).isEqualTo(tesla);
    }

    // test find by not existing id
    @Test
    public void whenFindByCarIdNotExists_ThenReturnEmptyResult() {
        Optional<Car> unknown = carRepository.findByCarId(10000L);
        assertThat(unknown.isEmpty()).isTrue();
    }

    // test find 3 records
    @Test
    public void when3RecordsAreFlushed_ThenFindAllReturnsAll3Records() {
        Car tesla = new Car("Tesla", "Model S");
        Car opel = new Car("Opel", "Corsa");
        Car mercedes = new Car("Mercedes", "Preto");

        entityManager.persistAndFlush(tesla);
        entityManager.persistAndFlush(opel);
        entityManager.persistAndFlush(mercedes);

        List<Car> carsFound = carRepository.findAll();

        assertThat(carsFound).contains(tesla, opel, mercedes);
    }
}
