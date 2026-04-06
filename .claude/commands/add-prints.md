Add System.out.println() step-by-step print statements to a Java file in the CoverFox project.

Ask the user: which file do you want to add print statements to?

Then read that file and add print statements following these exact rules:

RULES:
1. Before every action (click, sendKeys, navigation, sleep, assertion) add:
   System.out.println("[Step] Describing what is about to happen...");

2. After every action add:
   System.out.println("[Done] Confirming what happened — include actual values where possible");
   Example: System.out.println("[Done] Insurance count: " + count);
   Example: System.out.println("[Done] URL loaded: " + driver.getCurrentUrl());

3. At the start of @BeforeClass add a header banner:
   System.out.println("========================================");
   System.out.println("  Executing [Test Class Name]");
   System.out.println("========================================");

4. At the end of @AfterClass add a footer:
   System.out.println("========================================");
   System.out.println("  [Test Class Name] Execution Completed");
   System.out.println("========================================");

5. DO NOT add prints inside:
   - @FindBy field declarations
   - Constructor bodies of page objects
   - Import statements

6. DO NOT change any existing logic, asserts, or test data

Output the complete updated file. Then ask if the user wants you to write it directly.
