Feature: Author search
  The library must allow the search by a author name

  Scenario: Search book by author
    Given a book with the title 'One good book', written by 'Anonymous', published in 2013-03-14
    And another book with the title 'Some other book', written by 'Tim Tomson', published in 2014-08-23
    And another book with the title 'How to cook a dino', written by 'Fred Flintstone', published in 2012-01-01
    And another book with the title 'Another very good book', written by 'Anonymous', published in 2019-01-02
    Then the customer searches for the books with the authorship of 'Anonymous'
    Then 2 books should have been found
    And Book 1 should have the title 'One good book'
    And Book 2 should have the title 'Another very good book'