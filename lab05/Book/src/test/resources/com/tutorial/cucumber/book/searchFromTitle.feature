Feature: Search by title
  The library should allow the search for books with a specific title or with part of it

  Scenario: Search book with title
    Given a book with the title 'One good book', written by 'Anonymous', published in 2013-03-14
    And another book with the title 'Some other book', written by 'Tim Tomson', published in 2014-08-23
    And another book with the title 'How to cook a dino', written by 'Fred Flintstone', published in 2012-01-01
    And another book with the title 'Another very good book', written by 'Anonymous', published in 2019-01-02
    Then the customer searches for book with title 'Some other book'
    Then 1 book should have been found
    And Book 1 should have the title 'Some other book'


  Scenario: Search portion of a title
    Given a book with the title 'One good book', written by 'Anonymous', published in 2013-03-14
    And another book with the title 'Some other book', written by 'Tim Tomson', published in 2014-08-23
    And another book with the title 'How to cook a dino', written by 'Fred Flintstone', published in 2012-01-01
    And another book with the title 'Another very good book for cooking better', written by 'Anonymous', published in 2019-01-02
    Then the customer searches for books with 'cook' on its title
    Then 2 books should have been found
    And Book 1 should have the title 'How to cook a dino'
    And Book 2 should have the title 'Another very good book for cooking better'