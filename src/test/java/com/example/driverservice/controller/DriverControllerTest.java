package com.example.driverservice.controller;

import com.example.driverservice.convert.DriverDtoConverter;
import com.example.driverservice.dto.DriverDto;
import com.example.driverservice.model.Driver;
import com.example.driverservice.repo.DriverRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.example.driverservice.util.TestUtils.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class DriverControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private DriverRepo driverRepo;
    @MockBean
    private DriverDtoConverter driverDtoConverter;

    @Test
    void registration_shouldReturnDriverDto_whenCredentialsValid() throws Exception {
        doReturn(Optional.empty()).when(driverRepo).findByEmailOrUsername(DEFAULT_EMAIL, DEFAULT_USERNAME);
        doReturn(getDefaultDriver()).when(driverRepo).save(any(Driver.class));
        doReturn(getDefaultDriverDto()).when(driverDtoConverter).convertDriverToDriverDto(any(Driver.class));

        mockMvc.perform(post("/api/v1/drivers").content("{\n" +
                        "    \"fullName\": \"Evgeniy\",\n" +
                        "    \"username\": \"Kappa\",\n" +
                        "    \"email\": \"AnotherOneMail@tut.by\",\n" +
                        "    \"password\": \"654321\"\n" +
                        "}").contentType("application/json"))
                .andExpect(status().isCreated())
                .andExpect(content().json("{\"id\":1," +
                        "\"fullName\":\"Default fullname\"," +
                        "\"username\":\"Username\"," +
                        "\"email\":\"SomeEmail@Gmail.com\"," +
                        "\"registerDate\":\"3923-12-31T21:00:00.000+00:00\"," +
                        "\"availability\":true," +
                        "\"rating\":3.0}\n"));
    }

    @Test
    void registration_shouldReturnInvalidLoginResponse_whenCredentialsNotValid() throws Exception {
        doReturn(Optional.empty()).when(driverRepo).findByEmailOrUsername(DEFAULT_EMAIL, DEFAULT_USERNAME);

        mockMvc.perform(post("/api/v1/drivers").content("{\n" +
                        "    \"fullName\": \"\",\n" +
                        "    \"username\": \"\",\n" +
                        "    \"email\": \"AnotherOtut.by\",\n" +
                        "    \"password\": \"321\"\n" +
                        "}").contentType("application/json"))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"password\":\"Password must be at least 6 symbols or longer\"," +
                        "\"fullName\":\"Your name field must not be empty\"," +
                        "\"email\":\"Email should be valid\"," +
                        "\"username\":\"Your username field must not be empty\"}\n"));
    }

    @Test
    void getAllDrivers_shouldReturnDriversDto() throws Exception {
        Driver driver = getDefaultDriver();
        List<Driver> driverList = new ArrayList<>(Arrays.asList(driver, driver));
        DriverDto driverDto = getDefaultDriverDto();
        doReturn(driverList).when(driverRepo).findAll();
        doReturn(driverDto).when(driverDtoConverter).convertDriverToDriverDto(any(Driver.class));

        mockMvc.perform(get("/api/v1/drivers"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"drivers\":[" +
                        "{\"id\":1," +
                        "\"fullName\":\"Default fullname\"," +
                        "\"username\":\"Username\"," +
                        "\"email\":\"SomeEmail@Gmail.com\"," +
                        "\"registerDate\":\"3923-12-31T21:00:00.000+00:00\"," +
                        "\"availability\":true,\"rating\":3.0}" +
                        ",{\"id\":1," +
                        "\"fullName\":\"Default fullname\"," +
                        "\"username\":\"Username\"," +
                        "\"email\":\"SomeEmail@Gmail.com\"," +
                        "\"registerDate\":\"3923-12-31T21:00:00.000+00:00\"," +
                        "\"availability\":true," +
                        "\"rating\":3.0}]}\n"));
    }

//    @Test
//    void getDriver_shouldReturnDriverDto_whenLoginValid() throws Exception {
//        DriverDto driverDto = getDefaultDriverDto();
//        doReturn(Optional.of(getDefaultDriver())).when(driverRepo).findByEmailAndPassword("Vano228@tut.by", "Vanya228");
//        doReturn(driverDto).when(driverDtoConverter).convertDriverToDriverDto(any(Driver.class));
//
//        mockMvc.perform(post("/api/v1/drivers/login").content("{\n" +
//                "    \"email\": \"Vano228@tut.by\",\n" +
//                "    \"password\": \"Vanya228\"\n" +
//                "}").contentType("application/json"))
//                .andExpect(status().isOk())
//                .andExpect(content().json("{\"id\":1," +
//                        "\"fullName\":\"Default fullname\"," +
//                        "\"username\":\"Username\"," +
//                        "\"email\":\"SomeEmail@Gmail.com\"," +
//                        "\"registerDate\":\"3923-12-31T21:00:00.000+00:00\"," +
//                        "\"availability\":true," +
//                        "\"rating\":3.0}\n"));
//    }

//    @Test
//    void getDriver_shouldReturnInvalidLoginResponse_whenLoginInvalid() throws Exception {
//        doReturn(Optional.empty()).when(driverRepo).findByEmailAndPassword("Vano228@tut.by", "Vanya228");
//
//        mockMvc.perform(post("/api/v1/drivers/login").content("{\n" +
//                        "    \"email\": \"Vano228@tut.by\",\n" +
//                        "    \"password\": \"Vanya228\"\n" +
//                        "}").contentType("application/json"))
//                .andExpect(status().isUnauthorized())
//                .andExpect(content().json("{\"errorMessage\":\"Username or Email is already taken\"}"));
//    }

    @Test
    void updateDriver_shouldReturnDriverDto_whenDriverValid() throws Exception {
        doReturn(Optional.of(getDefaultDriver())).when(driverRepo).findById(DEFAULT_ID);
        doReturn(getDefaultDriver()).when(driverRepo).save(any(Driver.class));
        doReturn(getDefaultDriverDto()).when(driverDtoConverter).convertDriverToDriverDto(any(Driver.class));

        mockMvc.perform(put("/api/v1/drivers/1").content("{\n" +
                "    \"fullName\": \"Change\",\n" +
                "    \"username\": \"Params\",\n" +
                "    \"email\": \"SomeOtherMail@tut.by\",\n" +
                "    \"password\": \"1234567\"\n" +
                "}").contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":1," +
                        "\"fullName\":\"Default fullname\"," +
                        "\"username\":\"Username\"," +
                        "\"email\":\"SomeEmail@Gmail.com\"," +
                        "\"registerDate\":\"3923-12-31T21:00:00.000+00:00\"," +
                        "\"availability\":true," +
                        "\"rating\":3.0}\n"));
    }

    @Test
    void updateDriver_shouldReturnInvalidLoginResponse_whenDriverNotValid() throws Exception {
        doReturn(Optional.of(getDefaultDriver())).when(driverRepo).findById(DEFAULT_ID);
        doReturn(getDefaultDriver()).when(driverRepo).save(any(Driver.class));
        doReturn(getDefaultDriverDto()).when(driverDtoConverter).convertDriverToDriverDto(any(Driver.class));

        mockMvc.perform(put("/api/v1/drivers/1").content("{\n" +
                        "    \"fullName\": \"\",\n" +
                        "    \"username\": \"\",\n" +
                        "    \"email\": \"SomeOthetut.by\",\n" +
                        "    \"password\": \"567\"\n" +
                        "}").contentType("application/json"))
                .andExpect(status().isBadRequest())
                .andExpect(content().json("{\"password\":\"Password must be at least 6 symbols or longer\"," +
                        "\"fullName\":\"Your name field must not be empty\"," +
                        "\"email\":\"Email should be valid\"," +
                        "\"username\":\"Your username field must not be empty\"}\n"));
    }

    @Test
    void deleteDriver_shouldReturnDriverDto_whenDriverExist() throws Exception {
        doReturn(Optional.of(getDefaultDriver())).when(driverRepo).findById(DEFAULT_ID);
        doReturn(getDefaultDriverDto()).when(driverDtoConverter).convertDriverToDriverDto(any(Driver.class));

        mockMvc.perform(delete("/api/v1/drivers/1"))
                .andExpect(status().isNoContent())
                .andExpect(content().json("{\"id\":1," +
                        "\"fullName\":\"Default fullname\"," +
                        "\"username\":\"Username\"," +
                        "\"email\":\"SomeEmail@Gmail.com\"," +
                        "\"registerDate\":\"3923-12-31T21:00:00.000+00:00\"," +
                        "\"availability\":true," +
                        "\"rating\":3.0}\n"));
    }

    @Test
    void deleteDriver_shouldReturnDriverNotFoundResponse_whenDriverNotExist() throws Exception {
        doReturn(Optional.empty()).when(driverRepo).findById(DEFAULT_ID);

        mockMvc.perform(delete("/api/v1/drivers/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().json("{\"errorMessage\":\"There's no such driver\"}"));
    }

    @Test
    void changeStatus() throws Exception {
        doReturn(Optional.of(getDefaultDriver())).when(driverRepo).findById(DEFAULT_ID);
        doReturn(getUnavailableDriver()).when(driverRepo).save(any(Driver.class));
        doReturn(getUnavailableDriverDto()).when(driverDtoConverter).convertDriverToDriverDto(any(Driver.class));

        mockMvc.perform(patch("/api/v1/drivers/status/1"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":1," +
                        "\"fullName\":\"Default fullname\"," +
                        "\"username\":\"Username\"," +
                        "\"email\":\"SomeEmail@Gmail.com\"," +
                        "\"registerDate\":\"3923-12-31T21:00:00.000+00:00\"," +
                        "\"availability\":false," +
                        "\"rating\":3.0}\n"));
    }

    @Test
    void getBankData() throws Exception {
        mockMvc.perform(get("/api/v1/drivers/1/bank"))
                .andExpect(status().isOk())
                .andExpect(content().json("{" +
                        "\"cardNumber\":\"1234567890123456\"," +
                        "\"expirationDate\":\"12/26\"," +
                        "\"cvv\":\"123\"," +
                        "\"balance\":90000.0}"));
    }

    @Test
    void askOpinion() {
    }

    @Test
    void updateRating() {
    }
}
