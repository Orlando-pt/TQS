package deti.tqs.carInfoSystem.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import deti.tqs.carInfoSystem.entities.Car;
import deti.tqs.carInfoSystem.services.CarManagerService;

@RestController
@RequestMapping("/api")
public class CarRestController {
    
    @Autowired
    private CarManagerService carManagerService;

    @PostMapping("/car")
    public ResponseEntity<Car> createCar(@RequestBody Car car) {
        HttpStatus status = HttpStatus.CREATED;
        Car saved = this.carManagerService.save(car);
        return new ResponseEntity<>(saved, status);
    }

    @GetMapping("/car")
    public List<Car> getAllCars() {
        return this.carManagerService.getAllCars();
    }

    @GetMapping("/car/{id}")
    @ResponseBody
    public ResponseEntity<Car> getCarById(@PathVariable(value = "id", required = true) Long carId) {
        Optional<Car> car = this.carManagerService.getCarDetails(carId);

        if (car.isEmpty())
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(car.get(), HttpStatus.OK);
    }
}
