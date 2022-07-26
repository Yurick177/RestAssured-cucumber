@jsonServer1
Feature: Testing REST APIs on JSON Server

  @setupLocal
  Scenario: POST Data to JSON Server

    Given the endpoint is up
    And I want to make a post with data

      | title         | sss         |
      | description   | dddd        |
      | keywords      | aaa bbb ccc |
      | ogTitle       | ooo gggg    |
      | ogDescription | ooo ggg     |
      | ogImage       | oo gg iii   |


    Then I want to post data on "/api/product_service/v2/seo"
    Then I make the post
    And the status code is 201
    Then Save id
    When Get Seo by id on url "/api/product_service/v2/seo"
    Then I make the get
    And  the status code is 200

#  Scenario: PUT Data to JSON Server
#    Given the endpoint is up
#    And I want to put data on id "/posts/1"
#    And I want to put with data
#
#      | title  | All-Star Superman |
#      | author | Grant Morrison    |
#
#    Then I make the put
#    And the status code is 200
#
#  Scenario: PATCH Data to JSON Server
#    Given the endpoint is up
#    And I want to patch data on id "/posts/1"
#    And I want to patch with data
#
#      | title  | Old Man Logan |
#      | author | Jeff Lemire   |
#
#    Then I make the patch
#    And the status code is 200
#
#  Scenario: DELETE Data on JSON Server
#    Given the endpoint is up
#    And I want to delete data on id "/posts/1"
#    Then I make the delete
#    And the status code is 200
	
	
	
	
