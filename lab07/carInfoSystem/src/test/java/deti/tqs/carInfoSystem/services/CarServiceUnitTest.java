package deti.tqs.carInfoSystem.services;

import deti.tqs.carInfoSystem.entities.Car;
import deti.tqs.carInfoSystem.repositories.CarRepository;
import org.assertj.core.api.Java6Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.swing.text.html.Option;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class CarServiceUnitTest {

    @Mock(lenient = true)
    private CarRepository carRepository;

    @InjectMocks
    private CarManagerService carManagerService;

    @BeforeEach
    public void setUp() {
        Car tesla = new Car("Tesla", "Model S");
        tesla.setCarId(10L);

        Car opel = new Car("Opel", "Corsa");
        Car mercedes = new Car("Mercedes", "Preto");

        List<Car> allCars = Arrays.asList(tesla, opel, mercedes);

        // mock getAllCars()
        Mockito.when(carRepository.findAll()).thenReturn(allCars);

        // mock getCarDetails(Long)
        Mockito.when(carRepository.findByCarId(tesla.getCarId())).thenReturn(Optional.of(tesla));
        Mockito.when(carRepository.findByCarId(11L)).thenReturn(Optional.empty());
    }

    @Test
    public void whenValidId_ThenReturnCar() {
        Optional<Car> car = carManagerService.getCarDetails(10L);
        assertThat(car.get().getMaker()).isEqualTo("Tesla");

        Mockito.verify(carRepository, VerificationModeFactory.times(1)).findByCarId(10L);
    }

    @Test
    public void whenInvalidCarId_ThenReturnNull() {
        Optional<Car> unknown = carManagerService.getCarDetails(11L);
        assertThat(unknown.isEmpty()).isTrue();

        Mockito.verify(carRepository, VerificationModeFactory.times(1)).findByCarId(11L);
    }

    @Test
    public void when3Cars_WhenGetAll_Return3Records() {
        Car tesla = new Car("Tesla", "Model S");
        Car opel = new Car("Opel", "Corsa");
        Car mercedes = new Car("Mercedes", "Preto");

        List<Car> allCars = carManagerService.getAllCars();
        assertThat(allCars).hasSize(3).extracting(Car::getMaker)
                .contains(tesla.getMaker(), opel.getMaker(), mercedes.getMaker());

        Mockito.verify(carRepository, VerificationModeFactory.times(1)).findAll();
    }
}
