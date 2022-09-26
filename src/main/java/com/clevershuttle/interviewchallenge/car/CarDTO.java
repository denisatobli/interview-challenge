package com.clevershuttle.interviewchallenge.car;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY;

@Builder
public record CarDTO(
        @JsonProperty(access = READ_ONLY)
        Long id,
        @Size(min = 9, max = 9)
        @NotBlank(message = "License plate can not be blank!")
        String licensePlate,

        String brand,

        String manufacturer,

        String operationCity,

        Status status
) {

    public static CarDTO fromEntity(CarEntity carEntity) {
        return new CarDTO(
                carEntity.getId(),
                carEntity.getLicensePlate(),
                carEntity.getBrand(),
                carEntity.getManufacturer(),
                carEntity.getOperationCity(),
                carEntity.getStatus()
        );
    }

    public CarEntity toEntity() {
        return CarEntity.builder()
                .id(this.id)
                .licensePlate(this.licensePlate)
                .brand(this.brand)
                .manufacturer(this.manufacturer)
                .operationCity(this.operationCity)
                .status(this.status)
                .build();
    }

}
