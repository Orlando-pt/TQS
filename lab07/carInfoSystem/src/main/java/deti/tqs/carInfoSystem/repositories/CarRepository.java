package deti.tqs.carInfoSystem.repositories;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import deti.tqs.carInfoSystem.entities.Car;

@Repository
@Transactional
public interface CarRepository extends JpaRepository<Car, Long> {
    
    public Optional<Car> findByCarId(Long id);
    public List<Car> findAll();
}
