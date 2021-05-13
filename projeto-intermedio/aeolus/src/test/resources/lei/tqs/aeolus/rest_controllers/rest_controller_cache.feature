Feature: Verify the cache RestAPI endpoints

  Scenario: Request the size of the cache
    Given that the cache has currently 20 locations stored
    When a request of "/api/cache/size" is received on REST api
    Then the REST api should respond with the value 20
    And the method cacheSize should be called 1 time
