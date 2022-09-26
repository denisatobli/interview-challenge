package com.clevershuttle.interviewchallenge.car;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.READ_ONLY;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarDTO {

    @JsonProperty(access = READ_ONLY)
    private Long id;

    @Size(min = 9, max = 9)
    @NotBlank(message = "License plate can not be blank!")
    private String licensePlate;

    private String brand;

    private String manufacturer;

    private String operationCity;

    private Status status;

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
