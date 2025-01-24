Feature: ATT Login and Logout Demo



Background: Steps common for all scenarios
Given User Launch Chrome browser
When User opens URL "https://mapping-ui-uat-9039716ede4e.herokuapp.com/"

Scenario Outline: Demo Login with valid Credentials 
And User enters ATNT Email as "<email>"
And User enters ATNT Password as "<password>"
And Click on Login to sandbox
And Click on Allow Access button
#And click on User Icon
#And click on Logout button
Then Verify Page Title as "AT&T"
Then Verify Page Logo
And click on User Icon
And click on Logout button
And close browser

Examples:
|email|password|
|shawnee@t.digital.testemail.fulluat|Delhi@123|