package deti.tqs.carInfoSystem.services;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import deti.tqs.carInfoSystem.entities.Car;
import deti.tqs.carInfoSystem.repositories.CarRepository;

@Service
@Transactional
public class CarManagerService {

    @Autowired
    private CarRepository carRepository;

    public Car save(Car car) {
        return this.carRepository.save(car);
    }

    public List<Car> getAllCars() {
        return this.carRepository.findAll();
    }

    public Optional<Car> getCarDetails(Long carId) {
        return this.carRepository.findByCarId(carId);
    }
}
