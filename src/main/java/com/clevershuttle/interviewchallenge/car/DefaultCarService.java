package com.clevershuttle.interviewchallenge.car;

import com.clevershuttle.interviewchallenge.exception.EntityNotFoundException;
import com.clevershuttle.interviewchallenge.exception.LicensePlateExistException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Service
@Transactional
@RequiredArgsConstructor
public class DefaultCarService implements CarService{

    private final CarRepository carRepository;

    @Override
    @Transactional(readOnly = true)
    public List<CarDTO> findAllCars() {
        return carRepository.findAll()
                .stream()
                .map(CarDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public CarDTO findCar(Long id) {
        return carRepository.findById(id)
                .map(CarDTO::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(format("No entity found with id %s", id)));
    }

    @Override
    public CarDTO createCar(CarDTO carDTO) {
        var car = carDTO.toEntity();
        ensureLicensePlateIsUnique(car.getLicensePlate());
        return CarDTO.fromEntity(carRepository.saveAndFlush(car));
    }

    @Override
    public void deleteCar(Long id) {
        if (!carRepository.existsById(id)) {
            throw new EntityNotFoundException(format("No entity found with id %s", id));
        }

        carRepository.deleteById(id);
    }

    @Override
    public CarDTO updateCar(Long id, CarDTO carDTO) {
        return carRepository.findById(id)
                .map(carEntity -> {
                    ensureLicensePlateIsUnique(id, carDTO.licensePlate());
                    applyUpdate(carEntity, carDTO);
                    return carEntity;
                })
                .map(carRepository::saveAndFlush)
                .map(CarDTO::fromEntity)
                .orElseThrow(() -> new EntityNotFoundException(format("No entity found with id %s", id)));
    }

    private static void applyUpdate(CarEntity carEntity, CarDTO carDTO) {
        carEntity.setLicensePlate(carDTO.licensePlate());
        carEntity.setBrand(carDTO.brand());
        carEntity.setManufacturer(carDTO.manufacturer());
        carEntity.setOperationCity(carDTO.operationCity());
        carEntity.setStatus(carDTO.status());
        carEntity.setLastUpdatedAt(LocalDateTime.now());
    }

    private void ensureLicensePlateIsUnique(String licencePlate) {
        if (carRepository.existsByLicensePlate(licencePlate)) {
            throw new LicensePlateExistException(format("Licence plate %s already exists", licencePlate));
        }
    }

    private void ensureLicensePlateIsUnique(Long id, String licencePlate) {
        if (carRepository.existsByLicensePlateAndIdNot(licencePlate, id)) {
            throw new LicensePlateExistException(format("Licence plate %s already exists", licencePlate));
        }

    }

}
