Generate a data-driven combination test for the CoverFox insurance project.

Before writing code:
1. Read TC05_CoverFox_ValidateInsuranceCombinations.java as the exact style reference
2. Read src/test/java/coverFoxTest/ to find the next TC number

Ask the user (all at once):
- Which variables to combine? (e.g., gender, age, pincode, or other)
- What values for each variable? (list them)
- What should each combination assert? (e.g., insurance count > 0, specific count, page title)
- Should it print a summary table at the end?

Then generate a complete test class that:
1. Uses @DataProvider with all combinations (gender × age × pincode etc.)
2. Has @BeforeClass launching browser
3. Has @BeforeMethod navigating to home URL and initializing page objects
4. Has @Test(dataProvider="...") filling the form with provided data
5. Prints [Step]/[Done] after every action
6. Prints per-row result in this format:
   System.out.printf("[Result] %-7s | Age %-3sy | Pin %-7s | %d plans%n", gender, age, pincode, count);
7. Has @AfterClass that:
   - Closes browser
   - Prints a summary table of all combinations and their results
   - Prints HIGHEST and LOWEST count combinations

Also generates the testngCoverFoxTestNG.xml entry.

Output the complete file. Ask if user wants it written to disk.
