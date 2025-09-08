Feature: Contact Us Form

  Scenario: Submit contact form successfully
    Given I navigate to the contact us page
    When I fill and submit the contact form
    Then I should see a success message for contact form
