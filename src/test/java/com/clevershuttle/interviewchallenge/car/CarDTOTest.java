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
        // when
        var carDTO = CarDTO.fromEntity(carEntity);

        // then
        then(carDTO.id()).isEqualTo(carEntity.getId());
        then(carDTO.licensePlate()).isEqualTo(carEntity.getLicensePlate());
        then(carDTO.brand()).isEqualTo(carEntity.getBrand());
        then(carDTO.manufacturer()).isEqualTo(carEntity.getManufacturer());
        then(carDTO.operationCity()).isEqualTo(carEntity.getOperationCity());
        then(carDTO.status()).isEqualTo(carEntity.getStatus());
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

        // when
        var carEntity = dto.toEntity();

        // then
        then(carEntity.getId()).isEqualTo(dto.id());
        then(carEntity.getLicensePlate()).isEqualTo(dto.licensePlate());
        then(carEntity.getBrand()).isEqualTo(dto.brand());
        then(carEntity.getManufacturer()).isEqualTo(dto.manufacturer());
        then(carEntity.getOperationCity()).isEqualTo(dto.operationCity());
        then(carEntity.getStatus()).isEqualTo(dto.status());
        then(carEntity.getCreatedAt()).isNotNull();
        then(carEntity.getLastUpdatedAt()).isNull();
    }

}
