package coverFoxTest;

import java.io.IOException;

import org.apache.log4j.PropertyConfigurator;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import coverFoxBase.Base;
import coverFoxPOM.CoverFoxAddressDetailsPage;
import coverFoxPOM.CoverFoxHealthPlanPage;
import coverFoxPOM.CoverFoxHomePage;
import coverFoxPOM.CoverFoxMemberDetailsPage;
import coverFoxPOM.CoverFoxResultPage;
import coverFoxUtility.Utility;

/**
 * TC03 — Validate Insurance Count by Age
 *
 * Objective : Verify that CoverFox returns at least 1 plan for a Male user
 *             across 6 different age groups. Prints the insurance count for
 *             each age so you can compare how the listing changes.
 *
 * Gender    : Male (fixed)
 * Ages      : 18, 25, 30, 35, 40, 45
 * Pincode   : 400001 (Mumbai)
 * Mobile    : 9876543210
 */
public class TC03_CoverFox_ValidateInsuranceCountByAge extends Base {

    CoverFoxHomePage        homePage;
    CoverFoxHealthPlanPage  healthPlanPage;
    CoverFoxMemberDetailsPage memberDetailsPage;
    CoverFoxAddressDetailsPage addressDetailsPage;
    CoverFoxResultPage      resultPage;

    public static org.apache.log4j.Logger logger;

    // Fixed test data
    private static final String PINCODE = "400001";
    private static final String MOBILE  = "9876543210";

    // -----------------------------------------------------------------------
    // DATA PROVIDER — one row per age group to test
    // -----------------------------------------------------------------------
    @DataProvider(name = "ageGroups")
    public Object[][] ageGroups() {
        return new Object[][] {
            { "18" },
            { "25" },
            { "30" },
            { "35" },
            { "40" },
            { "45" }
        };
    }

    // -----------------------------------------------------------------------
    // SETUP
    // -----------------------------------------------------------------------
    @BeforeClass
    public void openApplication() throws IOException {
        System.out.println("========================================");
        System.out.println("  Executing Test Case 3: Insurance Count By Age");
        System.out.println("  Gender: Male | Pincode: " + PINCODE);
        System.out.println("========================================");

        System.out.println("[Step] Opening browser...");
        launchBrowser();
        System.out.println("[Done] Browser opened.");

        logger = org.apache.log4j.Logger.getLogger("TC03_CoverFox");
        PropertyConfigurator.configure("log4j (1).properties");
        logger.info("TC03 - Opening application");
    }

    /**
     * Runs before EACH test iteration (each age row from DataProvider).
     * Navigates back to the home page so the form is fresh.
     */
    @BeforeMethod
    public void navigateToHome() throws IOException {
        System.out.println("\n[Step] Navigating to home page for fresh form...");
        driver.get(Utility.readDataFromPropertyFile("url"));

        homePage          = new CoverFoxHomePage(driver);
        healthPlanPage    = new CoverFoxHealthPlanPage(driver);
        memberDetailsPage = new CoverFoxMemberDetailsPage(driver);
        addressDetailsPage = new CoverFoxAddressDetailsPage(driver);
        resultPage        = new CoverFoxResultPage(driver);

        System.out.println("[Done] Home page loaded. Page objects initialized.");
    }

    // -----------------------------------------------------------------------
    // TEST — runs once per age value from DataProvider
    // -----------------------------------------------------------------------
    @Test(dataProvider = "ageGroups")
    public void validateInsuranceCountForAge(String age)
            throws InterruptedException, IOException {

        System.out.println("----------------------------------------");
        System.out.println("[TEST] Age = " + age + " years | Gender = Male");
        System.out.println("----------------------------------------");

        // Step 1: Select Male gender
        System.out.println("[Step] Clicking on Male gender button...");
        homePage.clickOnGenderButton();
        logger.info("TC03 - Clicked Male for age " + age);
        System.out.println("[Done] Male selected.");
        Thread.sleep(1000);

        // Step 2: Health Plan page — Next
        System.out.println("[Step] Clicking Next on Health Plan page...");
        healthPlanPage.clickOnNextButton();
        System.out.println("[Done] Health Plan Next clicked.");
        Thread.sleep(1000);

        // Step 3: Select age
        System.out.println("[Step] Selecting age: " + age + " years...");
        memberDetailsPage.handleAgeDropDown(age);
        logger.info("TC03 - Age selected: " + age);
        System.out.println("[Done] Age " + age + " selected.");

        // Step 4: Member Details Next
        System.out.println("[Step] Clicking Next on Member Details page...");
        memberDetailsPage.clickOnNextButton();
        System.out.println("[Done] Member Details Next clicked.");
        Thread.sleep(1000);

        // Step 5: Enter Pincode
        System.out.println("[Step] Entering Pincode: " + PINCODE + "...");
        addressDetailsPage.enterPinCode(PINCODE);
        System.out.println("[Done] Pincode entered.");

        // Step 6: Enter Mobile
        System.out.println("[Step] Entering Mobile: " + MOBILE + "...");
        addressDetailsPage.enterMobNum(MOBILE);
        System.out.println("[Done] Mobile number entered.");

        // Step 7: Continue
        System.out.println("[Step] Clicking Continue...");
        addressDetailsPage.clickOnContinueButton();
        System.out.println("[Done] Continue clicked. Waiting for results...");
        Thread.sleep(5000);

        // Step 8: Get insurance count
        System.out.println("[Step] Reading insurance count from result page...");
        int insuranceCount = resultPage.getInsuranceCount();
        logger.info("TC03 - Age: " + age + " | Insurance count: " + insuranceCount);

        // Print summary line
        System.out.println("┌─────────────────────────────────────────┐");
        System.out.printf( "│  Age: %-5s │ Insurance Plans Listed: %-4d │%n", age + "y", insuranceCount);
        System.out.println("└─────────────────────────────────────────┘");

        // Step 9: Assert at least 1 plan is shown
        System.out.println("[Step] Asserting insurance count > 0...");
        Assert.assertTrue(insuranceCount > 0,
            "Expected at least 1 insurance plan for Male age " + age
            + " but got " + insuranceCount);
        System.out.println("[PASS] Insurance count " + insuranceCount + " > 0 for age " + age);
    }

    // -----------------------------------------------------------------------
    // TEARDOWN
    // -----------------------------------------------------------------------
    @AfterClass
    public void closeApplication() {
        System.out.println("\n[Step] Closing browser...");
        closeBrowser();
        System.out.println("[Done] Browser closed.");
        System.out.println("========================================");
        System.out.println("  Test Case 3 Execution Completed");
        System.out.println("========================================");
    }
}
