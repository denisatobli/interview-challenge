package com.clevershuttle.interviewchallenge.car;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static com.clevershuttle.interviewchallenge.car.Status.*;
import static org.assertj.core.api.BDDAssertions.then;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CarControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    private ResultActions resultActions;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CarService carService;

    @Autowired
    private CarRepository carRepository;

    private CarDTO carDTO;

    private String payload;

    @BeforeEach
    public final void cleanUpData() {
        carRepository.deleteAll();
    }

    @Test
    @SneakyThrows
    public void should_return_list_of_cars() {
        // given
        givenBmwCar();

        // when
        whenCarsAreRetrieved();

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$.[0].id").value(carDTO.getId()))
                .andExpect(jsonPath("$.[0].brand").value(carDTO.getBrand()))
                .andExpect(jsonPath("$.[0].licensePlate").value(carDTO.getLicensePlate()))
                .andExpect(jsonPath("$.[0].manufacturer").value(carDTO.getManufacturer()))
                .andExpect(jsonPath("$.[0].operationCity").value(carDTO.getOperationCity()))
                .andExpect(jsonPath("$.[0].status").value(carDTO.getStatus().name()));
    }

    @Test
    @SneakyThrows
    public void should_return_car_by_given_id() {
        // given
        givenBmwCar();

        // when
        whenACarIsRetrieved(carDTO.getId());

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("id").value(carDTO.getId()))
                .andExpect(jsonPath("brand").value(carDTO.getBrand()))
                .andExpect(jsonPath("licensePlate").value(carDTO.getLicensePlate()))
                .andExpect(jsonPath("manufacturer").value(carDTO.getManufacturer()))
                .andExpect(jsonPath("operationCity").value(carDTO.getOperationCity()))
                .andExpect(jsonPath("status").value(carDTO.getStatus().name()));
    }

    @Test
    @SneakyThrows
    public void should_create_a_new_car() {
        // given
        givenCarCreatePayload();

        // when
        whenCarIsCreated();

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("id").isNotEmpty())
                .andExpect(jsonPath("brand").value(carDTO.getBrand()))
                .andExpect(jsonPath("licensePlate").value(carDTO.getLicensePlate()))
                .andExpect(jsonPath("manufacturer").value(carDTO.getManufacturer()))
                .andExpect(jsonPath("operationCity").value(carDTO.getOperationCity()))
                .andExpect(jsonPath("status").value(carDTO.getStatus().name()));
    }

    @Test
    @SneakyThrows
    public void should_fail_to_create_a_car_with_an_existing_license_plate() {
        // given
        givenBmwCar();
        givenCarCreatePayload();

        // when
        whenCarIsCreated();

        // then
        resultActions.andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("errorId").value("license-id-already-taken"));
    }


    @Test
    @SneakyThrows
    public void should_delete_car_by_given_id() {
        // given
        givenBmwCar();

        // when
        whenCarIsDeleted();

        // then
        resultActions.andExpect(status().isNoContent());
        then(carService.findAllCars()).isEmpty();
    }

    @Test
    @SneakyThrows
    public void should_update_an_existing_car() {
        // given
        givenBmwCar();
        var updatedCarRequest = givenUpdatedCarDTO();
        payload = toJSON(updatedCarRequest);

        // when
        whenACarIsUpdated();

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType("application/json"))
                .andExpect(jsonPath("id").isNotEmpty())
                .andExpect(jsonPath("brand").value(updatedCarRequest.getBrand()))
                .andExpect(jsonPath("licensePlate").value(updatedCarRequest.getLicensePlate()))
                .andExpect(jsonPath("manufacturer").value(updatedCarRequest.getManufacturer()))
                .andExpect(jsonPath("operationCity").value(updatedCarRequest.getOperationCity()))
                .andExpect(jsonPath("status").value(updatedCarRequest.getStatus().name()));
    }

    @Test
    @SneakyThrows
    public void should_fail_to_update_car_with_an_existing_license_plate() {
        // given
        var existingCar = givenMercedesCar();
        givenBmwCar();
        var updateCarRequest = givenUpdatedCarDTO();
        updateCarRequest.setLicensePlate(existingCar.getLicensePlate());
        payload = toJSON(updateCarRequest);

        // when
        whenACarIsUpdated();

        // then
        resultActions.andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("errorId").value("license-id-already-taken"));
    }

    private void givenBmwCar() {
        carDTO = carService.createCar(
                CarDTO.builder()
                        .licensePlate("L-CS8877E")
                        .brand("MBW")
                        .manufacturer("MBW")
                        .operationCity("Hamburg")
                        .status(IN_MAINTENANCE)
                        .build()
        );
    }

    private CarDTO givenMercedesCar() {
        return carService.createCar(
                CarDTO.builder()
                        .licensePlate("L-CS8877F")
                        .brand("Mercedes")
                        .manufacturer("MBW")
                        .operationCity("Daimler Motors Corporation")
                        .status(IN_MAINTENANCE)
                        .build()
        );
    }

    @SneakyThrows
    private void givenCarCreatePayload() {
        carDTO = CarDTO.builder()
                .licensePlate("L-CS8877E")
                .brand("Mercedes")
                .manufacturer("Daimler Motors Corporation")
                .operationCity("Hamburg")
                .status(AVAILABLE)
                .build();

        payload = toJSON(carDTO);
    }

    @SneakyThrows
    private CarDTO givenUpdatedCarDTO() {
        return new CarDTO(
                1L,
                "L-DS9345H",
                "Mercedes",
                "Daimler Motors Corporation",
                "Berlin",
                OUT_OF_SERVICE
        );
    }

    @SneakyThrows
    private void whenCarsAreRetrieved() {
        resultActions = this.mockMvc.perform(get("/cars"))
                .andDo(print());
    }

    @SneakyThrows
    private void whenACarIsRetrieved(Long id) {
        resultActions = this.mockMvc.perform(get("/cars/{id}", id))
                .andDo(print());
    }

    @SneakyThrows
    private void whenCarIsCreated() {
        resultActions = this.mockMvc.perform(post("/cars")
                        .content(payload)
                        .contentType(APPLICATION_JSON))
                .andDo(print());
    }

    @SneakyThrows
    private void whenCarIsDeleted() {
        resultActions = this.mockMvc
                .perform(delete("/cars/{id}", carDTO.getId()))
                .andDo(print());
    }

    @SneakyThrows
    private void whenACarIsUpdated() {
        resultActions = this.mockMvc
                .perform(put("/cars/{id}", carDTO.getId())
                        .content(payload)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    @SneakyThrows
    private String toJSON(Object object) {
        return objectMapper.writeValueAsString(object);
    }

}
