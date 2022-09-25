package com.clevershuttle.interviewchallenge.car;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Map;

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

    private String payload;

    @Test
    @SneakyThrows
    public void should_return_list_of_cars() {
        // given

        // when
        whenCarsAreRetrieved();

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$.[0].id").value(1L))
                .andExpect(jsonPath("$.[0].brand").value("BMW"))
                .andExpect(jsonPath("$.[0].licensePlate").value("HA-EE111"))
                .andExpect(jsonPath("$.[0].manufacturer").value("BMW"))
                .andExpect(jsonPath("$.[0].operationCity").value("Hamburg"))
                .andExpect(jsonPath("$.[0].status").value("AVAILABLE"))
                .andExpect(jsonPath("$.[0].createdAt").isNotEmpty())
                .andExpect(jsonPath("$.[0].lastUpdatedAt").isNotEmpty());
    }

    @Test
    @SneakyThrows
    public void should_return_car_by_given_id() {
        // given

        // when
        whenACarIsRetrieved();

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("id").value(1L))
                .andExpect(jsonPath("brand").value("BMW"))
                .andExpect(jsonPath("licensePlate").value("HA-EE111"))
                .andExpect(jsonPath("manufacturer").value("BMW"))
                .andExpect(jsonPath("operationCity").value("Hamburg"))
                .andExpect(jsonPath("status").value("AVAILABLE"))
                .andExpect(jsonPath("createdAt").isNotEmpty())
                .andExpect(jsonPath("lastUpdatedAt").isNotEmpty());
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
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("id").value(1L))
                .andExpect(jsonPath("brand").value("BMW"))
                .andExpect(jsonPath("licensePlate").value("HA-EE111"))
                .andExpect(jsonPath("manufacturer").value("BMW"))
                .andExpect(jsonPath("operationCity").value("Hamburg"))
                .andExpect(jsonPath("status").value("AVAILABLE"))
                .andExpect(jsonPath("createdAt").isNotEmpty())
                .andExpect(jsonPath("lastUpdatedAt").isNotEmpty());
    }

    @Test
    @SneakyThrows
    public void should_delete_car_by_given_id() {
        // given

        // when
        whenCarIsDeleted();

        // then
        resultActions.andExpect(status().isNoContent());
    }

    @Test
    @SneakyThrows
    public void should_update_car() {
        // given
        givenCarCreatePayload();

        // when
        whenACarIsUpdated();

        // then
        resultActions.andExpect(status().isOk())
                .andExpect(content().contentType("application/json;charset=UTF-8"))
                .andExpect(jsonPath("id").value(1L))
                .andExpect(jsonPath("brand").value("BMW"))
                .andExpect(jsonPath("licensePlate").value("HA-EE111"))
                .andExpect(jsonPath("manufacturer").value("BMW"))
                .andExpect(jsonPath("operationCity").value("Hamburg"))
                .andExpect(jsonPath("status").value("IN_MAINTENANCE"))
                .andExpect(jsonPath("createdAt").isNotEmpty())
                .andExpect(jsonPath("lastUpdatedAt").isNotEmpty());
    }

    @SneakyThrows
    private void givenCarCreatePayload()
    {
        payload = objectMapper.writeValueAsString(
                Map.of("brand", "BMW",
                        "licensePlate", "HA-EE111",
                        "manufacturer", "BMW",
                        "operationCity", "Berlin",
                        "status", "IN_MAINTENANCE"));
    }

    @SneakyThrows
    private void whenCarsAreRetrieved() {
        resultActions = this.mockMvc.perform(get("/cars"))
                .andDo(print());
    }

    @SneakyThrows
    private void whenACarIsRetrieved() {
        resultActions = this.mockMvc.perform(get("/cars/{id}", 1L))
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
                .perform(delete("/cars/{id}", 1L))
                .andDo(print());
    }

    @SneakyThrows
    private void whenACarIsUpdated() {
        resultActions = this.mockMvc
                .perform(put("/cars/{id}", 1L)
                        .content(payload)
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

}
