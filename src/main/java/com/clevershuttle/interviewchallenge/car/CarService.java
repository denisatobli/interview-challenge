package com.clevershuttle.interviewchallenge.car;

import java.util.List;

public interface CarService {

    List<CarDTO> findAllCars();

    CarDTO findCar(Long id);

    CarDTO createCar(CarDTO carDTO);

    void deleteCar(Long id);

    CarDTO updateCar(Long id, CarDTO carDTO);

}
