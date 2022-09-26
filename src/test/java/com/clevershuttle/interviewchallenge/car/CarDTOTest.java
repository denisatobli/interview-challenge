package com.clevershuttle.interviewchallenge.car;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static com.clevershuttle.interviewchallenge.car.Status.AVAILABLE;
import static com.clevershuttle.interviewchallenge.car.Status.IN_MAINTENANCE;
import static org.assertj.core.api.BDDAssertions.then;

public class CarDTOTest {

    @Test
    void should_convert_entity_to_dto() {
        // given
        var carEntity = CarEntity.builder()
                .id(1L)
                .licensePlate("L-CS8877E")
                .brand("BMW")
                .manufacturer("BMW")
                .operationCity("Hamburg")
                .status(AVAILABLE)
                .lastUpdatedAt(LocalDateTime.now())
                .build();

        var carDTO = CarDTO.fromEntity(carEntity);

        then(carDTO.getId()).isEqualTo(carEntity.getId());
        then(carDTO.getLicensePlate()).isEqualTo(carEntity.getLicensePlate());
        then(carDTO.getBrand()).isEqualTo(carEntity.getBrand());
        then(carDTO.getManufacturer()).isEqualTo(carEntity.getManufacturer());
        then(carDTO.getOperationCity()).isEqualTo(carEntity.getOperationCity());
        then(carDTO.getStatus()).isEqualTo(carEntity.getStatus());
    }

    @Test
    void should_convert_dto_to_entity() {
        // given
        var dto = new CarDTO(
                1L,
                "L-CS8877E",
                "MBW",
                "BMW",
                "Hamburg",
                IN_MAINTENANCE
        );

        var carEntity = dto.toEntity();

        then(carEntity.getId()).isEqualTo(dto.getId());
        then(carEntity.getLicensePlate()).isEqualTo(dto.getLicensePlate());
        then(carEntity.getBrand()).isEqualTo(dto.getBrand());
        then(carEntity.getManufacturer()).isEqualTo(dto.getManufacturer());
        then(carEntity.getOperationCity()).isEqualTo(dto.getOperationCity());
        then(carEntity.getStatus()).isEqualTo(dto.getStatus());
        then(carEntity.getCreatedAt()).isNotNull();
        then(carEntity.getLastUpdatedAt()).isNull();
    }

}
