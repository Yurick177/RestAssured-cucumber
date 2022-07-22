@jsonServer2
Feature: Test JSON Server data

  @setupLocal
  Scenario: Check the length of info
    Given the endpoint is up
    And I want to make a post with data
      | id     | 23              |
      | title  | Uncanny X-Force |
      | author | Rick Remender   |
    Then I make the post
    When I want to get data on id "/posts/23"
    Then I make the get
    When I want to get data on member "title"
    Then the data arrays are greater than 1
    And I want to delete data on id "/posts/23"
    Then I make the delete

	
	
	