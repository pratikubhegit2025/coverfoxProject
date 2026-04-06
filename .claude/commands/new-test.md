Create a new Selenium TestNG test case for the CoverFox automation project.

Before writing any code:
1. Read src/test/java/coverFoxTest/ to find the highest existing TC number
2. Read TC03_CoverFox_ValidateInsuranceCountByAge.java as the coding style reference
3. Read testngCoverFoxTestNG.xml to see its current structure

Ask the user these questions (all at once, not one at a time):
- What should this test validate? (describe in plain English)
- Which page does the test start on?
- What input data does it need? (gender, age, pincode, or other)
- Should it be data-driven with multiple input combinations? If yes, list the combinations.
- What is the expected result / what should the assertion check?

Then generate:
1. A complete Java test class file named TC(N+1)_CoverFox_[Description].java
   - Package: coverFoxTest
   - Extends Base
   - @BeforeClass: launchBrowser() + logger setup
   - @BeforeMethod: driver.get(url) + page object initialization
   - @Test: the validation logic with Assert
   - @AfterClass: closeBrowser()
   - System.out.println("[Step] ...") BEFORE every action
   - System.out.println("[Done] ...") AFTER every action (include actual values)
   - If data-driven: use @DataProvider

2. The XML snippet to add to testngCoverFoxTestNG.xml:
   <test thread-count="1" name="TC(N+1) - Description">
       <classes>
           <class name="coverFoxTest.TC(N+1)_CoverFox_Description"/>
       </classes>
   </test>

Output the complete file — not a snippet. Then ask if the user wants you to add the XML entry to testngCoverFoxTestNG.xml automatically.
