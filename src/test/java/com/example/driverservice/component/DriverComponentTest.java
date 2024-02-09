package com.example.driverservice.component;

import com.example.driverservice.convert.DriverDtoConverter;
import com.example.driverservice.dto.DriverDto;
import com.example.driverservice.dto.LoginDto;
import com.example.driverservice.kafka.DriverProducer;
import com.example.driverservice.model.Driver;
import com.example.driverservice.repo.DriverRepo;
import com.example.driverservice.service.impl.DriverServiceImpl;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.cucumber.spring.CucumberContextConfiguration;
import org.apache.zookeeper.Op;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static com.example.driverservice.util.Messages.INVALID_LOGIN_MESSAGE;
import static com.example.driverservice.util.TestUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@CucumberContextConfiguration
public class DriverComponentTest {

    @Mock
    private DriverRepo driverRepo;
    @Mock
    private DriverDtoConverter driverDtoConverter;
    @Mock
    private DriverProducer driverProducer;
    @InjectMocks
    private DriverServiceImpl driverService;
    private Exception exception;
    private DriverDto driverResponse;

    @Given("A driver with email {string} and username {string} doesn't exist")
    public void driverWithEmailAndUsernameDoesntExists(String email, String username) {
        doReturn(Optional.empty()).when(driverRepo).findByEmailOrUsername(email, username);
        doReturn(getDefaultDriverDto()).when(driverDtoConverter).convertDriverToDriverDto(any(Driver.class));
    }

    @When("Create request with email {string} and username {string} passed to the registration method")
    public void registerDriverMethodCalled(String email, String username) {
        try {
            driverResponse = driverService.register(Driver.builder()
                    .fullName(DEFAULT_FULLNAME)
                    .username(username)
                    .email(email)
                    .password(DEFAULT_PASSWORD)
                    .registerDate(DEFAULT_REGISTER_DATE)
                    .availability(DEFAULT_AVAILABILITY_STATUS)
                    .rating(DEFAULT_RATING)
                    .build());
        } catch (Exception e) {
            exception = e;
        }
    }

    @Then("The response should return driver data")
    public void responseContainsCreatedDriver() {
        assertEquals(getDefaultDriverDto(), driverResponse);
    }

    @Given("A driver with email {string} and username {string} exists")
    public void aDriverWithEmailAndUsernameExists(String email, String username) {
        doReturn(Optional.of(getDefaultDriver())).when(driverRepo).findByEmailOrUsername(email, username);
    }

    @Then("The InvalidLoginException should be thrown")
    public void theInvalidLoginExceptionShouldBeThrown() {
        assertEquals(exception.getMessage(), INVALID_LOGIN_MESSAGE);
    }

    @Given("A driver with email {string} and password {string} exists")
    public void aDriverWithEmailAndPasswordExists(String email, String password) {
        doReturn(Optional.of(getDefaultDriver())).when(driverRepo).findByEmailAndPassword(email, password);
        doReturn(getDefaultDriverDto()).when(driverDtoConverter).convertDriverToDriverDto(any(Driver.class));
    }

    @When("Create request with email {string} and password {string} passed to the login method")
    public void createRequestWithEmailAndUsernamePassedToTheLoginMethod(String email, String password) {
        try {
            driverResponse = driverService.getDriver(LoginDto.builder()
                            .password(password)
                            .email(email)
                            .build());
        } catch (Exception e) {
            exception = e;
        }
    }

    @Given("A driver with email {string} and password {string} doesn't exist")
    public void aDriverWithEmailAndPasswordDoesntExist(String email, String password) {
        doReturn(Optional.empty()).when(driverRepo).findByEmailAndPassword(email, password);
    }

    @Given("A driver with id {int} exists")
    public void aDriverWithIdExists(int id) {
        doReturn(Optional.of(getDefaultDriver())).when(driverRepo).findById(id);
        doReturn(getDefaultDriverDto()).when(driverDtoConverter).convertDriverToDriverDto(any(Driver.class));
    }

    @When("Create request with id {int} passed to the addOrUpdate method")
    public void createRequestWithIdPassedToTheAddOrUpdateMethod(int id) {
        try {
            driverResponse = driverService.addOrUpdateDriver(getDefaultDriver(), id);
        } catch (Exception e) {
            exception = e;
        }
    }

    @Given("A driver with id {int} does not exist")
    public void aDriverWithIdDoesNotExist(int id) {
        doReturn(Optional.empty()).when(driverRepo).findById(id);
        doReturn(getDefaultDriverDto()).when(driverDtoConverter).convertDriverToDriverDto(any(Driver.class));
    }

    @When("Create request with id {int} passed to the delete method")
    public void createRequestWithIdPassedToTheDeleteMethod(int id) {
        try {
            driverResponse = driverService.deleteDriver(id);
        } catch (Exception e) {
            exception = e;
        }
    }

    @When("Create request with id {int} passed to the changeStatus method")
    public void createRequestWithIdPassedToTheChangeStatusMethod(int id) {
        try {
            driverResponse = driverService.changeStatus(id);
        } catch (Exception e) {
            exception = e;
        }
    }
}
