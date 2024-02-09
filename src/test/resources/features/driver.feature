Feature: Driver Service
  Scenario: Creating driver with unique credentials
    Given A driver with email "UniqueEmail@tut.by" and username "Unique user" doesn't exist
    When Create request with email "UniqueEmail@tut.by" and username "Unique user" passed to the registration method
    Then The response should return driver data

  Scenario: Creating driver with non unique credentials
    Given A driver with email "UniqueEmail@tut.by" and username "Unique user" exists
    When Create request with email "UniqueEmail@tut.by" and username "Unique user" passed to the registration method
    Then The InvalidLoginException should be thrown

  Scenario: Getting driver's data with correct credentials
    Given A driver with email "CorrectEmail@gmail.com" and password "123456" exists
    When Create request with email "CorrectEmail@gmail.com" and password "123456" passed to the login method
    Then The response should return driver data

  Scenario: Getting driver's data with incorrect credentials
    Given A driver with email "CorrectEmail@gmail.com" and password "123456" doesn't exist
    When Create request with email "CorrectEmail@gmail.com" and password "123456" passed to the login method
    Then The InvalidLoginException should be thrown

  Scenario: Updating existing driver profile
    Given A driver with id 1 exists
    When Create request with id 1 passed to the addOrUpdate method
    Then The response should return driver data

  Scenario: Adding new driver
    Given A driver with id 5 does not exist
    When Create request with id 5 passed to the addOrUpdate method
    Then The response should return driver data

  Scenario: Deleting existing driver
    Given A driver with id 1 exists
    When Create request with id 1 passed to the delete method
    Then The response should return driver data

  Scenario: Changing availability status of existing driver
    Given A driver with id 1 exists
    When Create request with id 1 passed to the changeStatus method
    Then The response should return driver data