package com.clevershuttle.interviewchallenge.car;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cars")
public class CarController {

    private final CarService carService;

    @GetMapping
    public List<CarDTO> getCars(){
        return carService.findAllCars();
    }

    @GetMapping("/{id}")
    public CarDTO getCar(@PathVariable Long id){
        return carService.findCar(id);
    }

    @PostMapping
    public CarDTO createCar(@Valid @RequestBody CarDTO carDTO){
        return carService.createCar(carDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteCar(@PathVariable Long id){
        carService.deleteCar(id);
    }

    @PutMapping("/{id}")
    public CarDTO updateCar(@PathVariable Long id, @Valid @RequestBody CarDTO carDTO){
        return carService.updateCar(id, carDTO);
    }
}
