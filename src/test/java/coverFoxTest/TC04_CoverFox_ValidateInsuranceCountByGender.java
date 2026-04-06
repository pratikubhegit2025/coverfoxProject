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
 * TC04 — Validate Insurance Count by Gender
 *
 * Objective : Verify that CoverFox returns plans for both Male and Female.
 *             Print the count for each gender side by side to spot differences.
 *
 * Age       : 30 (fixed)
 * Pincode   : 400001 (Mumbai, fixed)
 * Mobile    : 9876543210 (fixed)
 * Genders   : Male, Female
 */
public class TC04_CoverFox_ValidateInsuranceCountByGender extends Base {

    CoverFoxHomePage          homePage;
    CoverFoxHealthPlanPage    healthPlanPage;
    CoverFoxMemberDetailsPage memberDetailsPage;
    CoverFoxAddressDetailsPage addressDetailsPage;
    CoverFoxResultPage        resultPage;

    public static org.apache.log4j.Logger logger;

    // Fixed data
    private static final String AGE     = "30";
    private static final String PINCODE = "400001";
    private static final String MOBILE  = "9876543210";

    // Stores counts for comparison summary
    private int maleCount   = -1;
    private int femaleCount = -1;

    // -----------------------------------------------------------------------
    // DATA PROVIDER
    // -----------------------------------------------------------------------
    @DataProvider(name = "genders")
    public Object[][] genders() {
        return new Object[][] {
            { "Male"   },
            { "Female" }
        };
    }

    // -----------------------------------------------------------------------
    // SETUP
    // -----------------------------------------------------------------------
    @BeforeClass
    public void openApplication() throws IOException {
        System.out.println("========================================");
        System.out.println("  Executing Test Case 4: Insurance Count By Gender");
        System.out.println("  Age: " + AGE + " | Pincode: " + PINCODE);
        System.out.println("========================================");

        System.out.println("[Step] Opening browser...");
        launchBrowser();
        System.out.println("[Done] Browser opened.");

        logger = org.apache.log4j.Logger.getLogger("TC04_CoverFox");
        PropertyConfigurator.configure("log4j (1).properties");
        logger.info("TC04 - Opening application");
    }

    @BeforeMethod
    public void navigateToHome() throws IOException {
        System.out.println("\n[Step] Navigating to home page for fresh form...");
        driver.get(Utility.readDataFromPropertyFile("url"));

        homePage           = new CoverFoxHomePage(driver);
        healthPlanPage     = new CoverFoxHealthPlanPage(driver);
        memberDetailsPage  = new CoverFoxMemberDetailsPage(driver);
        addressDetailsPage = new CoverFoxAddressDetailsPage(driver);
        resultPage         = new CoverFoxResultPage(driver);

        System.out.println("[Done] Home page loaded.");
    }

    // -----------------------------------------------------------------------
    // TEST
    // -----------------------------------------------------------------------
    @Test(dataProvider = "genders")
    public void validateInsuranceCountForGender(String gender)
            throws InterruptedException, IOException {

        System.out.println("----------------------------------------");
        System.out.println("[TEST] Gender = " + gender + " | Age = " + AGE);
        System.out.println("----------------------------------------");

        // Step 1: Select gender
        System.out.println("[Step] Selecting gender: " + gender + "...");
        homePage.selectGender(gender);
        logger.info("TC04 - Gender selected: " + gender);
        System.out.println("[Done] " + gender + " selected.");
        Thread.sleep(1000);

        // Step 2: Health Plan Next
        System.out.println("[Step] Clicking Next on Health Plan page...");
        healthPlanPage.clickOnNextButton();
        System.out.println("[Done] Health Plan Next clicked.");
        Thread.sleep(1000);

        // Step 3: Select age
        System.out.println("[Step] Selecting age: " + AGE + " years...");
        memberDetailsPage.handleAgeDropDown(AGE);
        System.out.println("[Done] Age " + AGE + " selected.");

        // Step 4: Member Details Next
        System.out.println("[Step] Clicking Next on Member Details page...");
        memberDetailsPage.clickOnNextButton();
        System.out.println("[Done] Member Details Next clicked.");
        Thread.sleep(1000);

        // Step 5: Pincode
        System.out.println("[Step] Entering Pincode: " + PINCODE + "...");
        addressDetailsPage.enterPinCode(PINCODE);
        System.out.println("[Done] Pincode entered.");

        // Step 6: Mobile
        System.out.println("[Step] Entering Mobile: " + MOBILE + "...");
        addressDetailsPage.enterMobNum(MOBILE);
        System.out.println("[Done] Mobile entered.");

        // Step 7: Continue
        System.out.println("[Step] Clicking Continue...");
        addressDetailsPage.clickOnContinueButton();
        System.out.println("[Done] Continue clicked. Waiting for results...");
        Thread.sleep(5000);

        // Step 8: Get count
        System.out.println("[Step] Reading insurance count...");
        int count = resultPage.getInsuranceCount();
        logger.info("TC04 - Gender: " + gender + " | Count: " + count);

        // Store for comparison summary
        if ("Male".equalsIgnoreCase(gender))   { maleCount   = count; }
        if ("Female".equalsIgnoreCase(gender)) { femaleCount = count; }

        // Print result
        System.out.println("┌─────────────────────────────────────────┐");
        System.out.printf( "│  Gender: %-7s │ Insurance Plans: %-5d│%n", gender, count);
        System.out.println("└─────────────────────────────────────────┘");

        // Step 9: Assert > 0
        System.out.println("[Step] Asserting insurance count > 0...");
        Assert.assertTrue(count > 0,
            "Expected at least 1 plan for " + gender + " age " + AGE
            + " but got " + count);
        System.out.println("[PASS] Count " + count + " > 0 for " + gender);
    }

    // -----------------------------------------------------------------------
    // TEARDOWN — prints comparison summary after both genders are tested
    // -----------------------------------------------------------------------
    @AfterClass
    public void closeApplication() {
        // Print gender comparison summary if both ran
        if (maleCount >= 0 && femaleCount >= 0) {
            System.out.println("\n╔══════════════════════════════════════════╗");
            System.out.println("║       GENDER COMPARISON SUMMARY          ║");
            System.out.println("╠══════════════════════════════════════════╣");
            System.out.printf( "║  Male   (age %s, pin %s) → %3d plans  ║%n", AGE, PINCODE, maleCount);
            System.out.printf( "║  Female (age %s, pin %s) → %3d plans  ║%n", AGE, PINCODE, femaleCount);
            System.out.println("╠══════════════════════════════════════════╣");
            if (maleCount > femaleCount) {
                System.out.println("║  Male has MORE plans listed.             ║");
            } else if (femaleCount > maleCount) {
                System.out.println("║  Female has MORE plans listed.           ║");
            } else {
                System.out.println("║  Both genders have EQUAL plans listed.   ║");
            }
            System.out.println("╚══════════════════════════════════════════╝");
        }

        System.out.println("[Step] Closing browser...");
        closeBrowser();
        System.out.println("[Done] Browser closed.");
        System.out.println("========================================");
        System.out.println("  Test Case 4 Execution Completed");
        System.out.println("========================================");
    }
}
