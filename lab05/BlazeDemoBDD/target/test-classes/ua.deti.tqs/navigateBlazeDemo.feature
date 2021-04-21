Feature: Navigate through the blazeDemo website
  This website should allow the selection and purchase of flights

  Scenario: Verify full workflow until purchase has been made
    When a customer goes to the url 'https://blazedemo.com/'
    Then the customer clicks the element with name 'fromPort'
    And the customer clicks the css element with the name '.form-inline:nth-child(1) > option:nth-child(1)'
    And the customer clicks the element with name 'toPort'
    And the customer clicks the option '//option[. = \'London\']'
    And the customer clicks the css element with the name '.form-inline:nth-child(4) > option:nth-child(3)'
    And the customer clicks the css element with the name '.btn-primary'
    Then the customer should see the 'h3' element with the text 'Flights from Paris to London:'
    Then the customer clicks the css element with the name 'tr:nth-child(2) .btn'
    And the customer clicks the element with id 'inputName'
    And the customer sends the keys 'António José'
    And the customer clicks the element with id 'zipCode'
    And the customer sends the keys '3333'
    And the customer clicks the css element with the name 'option:nth-child(2)'
    And the customer clicks the element with id 'creditCardNumber'
    And the customer sends the keys '1231313131312312312312'
    And the customer clicks the css element with the name '.btn-primary'
    Then the customer verifies that the title of the page is 'BlazeDemo Confirmation'
    Then the customer can quit from the website