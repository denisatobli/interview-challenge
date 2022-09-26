package com.clevershuttle.interviewchallenge.car;

import com.clevershuttle.interviewchallenge.exception.EntityNotFoundException;
import com.clevershuttle.interviewchallenge.exception.LicensePlateExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService{

    private final CarRepository carRepository;

    public List<CarDTO> findAllCars() {
        return carRepository.findAll()
                .stream()
                .map(CarDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public CarDTO findCar(Long id) {
        return carRepository.findById(id)
                .map(CarDTO::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(String.format("No entity found with id %s", id)));
    }

    public CarDTO createCar(CarDTO carDTO) {
        var car = carDTO.toEntity();
        ensureLicensePlateIsUnique(car.getLicensePlate());
        return CarDTO.fromEntity(carRepository.saveAndFlush(car));
    }

    public void deleteCar(Long id) {
        if (!carRepository.existsById(id)) {
            throw new EntityNotFoundException(String.format("No entity found with id %s", id));
        }

        carRepository.deleteById(id);
    }

    public CarDTO updateCar(Long id, CarDTO carDTO) {
        return carRepository.findById(id)
                .map(carEntity -> {
                    ensureLicensePlateIsUnique(id, carDTO.getLicensePlate());
                    applyUpdate(carEntity, carDTO);
                    return carEntity;
                })
                .map(carRepository::saveAndFlush)
                .map(CarDTO::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(String.format("No entity found with id %s", id)));
    }

    private static void applyUpdate(CarEntity carEntity, CarDTO carDTO) {
        carEntity.setLicensePlate(carDTO.getLicensePlate());
        carEntity.setBrand(carDTO.getBrand());
        carEntity.setManufacturer(carDTO.getManufacturer());
        carEntity.setOperationCity(carDTO.getOperationCity());
        carEntity.setStatus(carDTO.getStatus());
        carEntity.setLastUpdatedAt(LocalDateTime.now());
    }

    private void ensureLicensePlateIsUnique(String licencePlate) {
        if (carRepository.existsByLicensePlate(licencePlate)) {
            throw new LicensePlateExistException(String.format("Licence plate %s already exists", licencePlate));
        }
    }

    private void ensureLicensePlateIsUnique(Long id, String licencePlate) {
        if (carRepository.existsByLicensePlateAndIdNot(licencePlate, id)) {
            throw new LicensePlateExistException(String.format("Licence plate %s already exists", licencePlate));
        }

    }

}
