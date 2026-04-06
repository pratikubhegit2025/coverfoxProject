package coverFoxTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
 * TC05 — Validate Insurance Count for All Combinations
 *
 * Objective  : Run every Gender × Age × Pincode combination and record
 *              how many insurance plans are listed for each.
 *              Prints a full comparison table at the end of the suite.
 *
 * Combinations:
 *   Genders  : Male, Female
 *   Ages     : 25, 35, 45
 *   Pincodes : 400001 (Mumbai), 110001 (Delhi)
 *   → Total  : 2 × 3 × 2 = 12 test iterations
 */
public class TC05_CoverFox_ValidateInsuranceCombinations extends Base {

    CoverFoxHomePage          homePage;
    CoverFoxHealthPlanPage    healthPlanPage;
    CoverFoxMemberDetailsPage memberDetailsPage;
    CoverFoxAddressDetailsPage addressDetailsPage;
    CoverFoxResultPage        resultPage;

    public static org.apache.log4j.Logger logger;

    private static final String MOBILE = "9876543210";

    // Stores one result row per combination for the final summary table
    private final List<String[]> combinationResults = new ArrayList<>();

    // -----------------------------------------------------------------------
    // DATA PROVIDER  —  { gender, age, pincode, city }
    // -----------------------------------------------------------------------
    @DataProvider(name = "combinations")
    public Object[][] combinations() {
        return new Object[][] {
            // Mumbai combinations
            { "Male",   "25", "400001", "Mumbai" },
            { "Male",   "35", "400001", "Mumbai" },
            { "Male",   "45", "400001", "Mumbai" },
            { "Female", "25", "400001", "Mumbai" },
            { "Female", "35", "400001", "Mumbai" },
            { "Female", "45", "400001", "Mumbai" },
            // Delhi combinations
            { "Male",   "25", "110001", "Delhi"  },
            { "Male",   "35", "110001", "Delhi"  },
            { "Male",   "45", "110001", "Delhi"  },
            { "Female", "25", "110001", "Delhi"  },
            { "Female", "35", "110001", "Delhi"  },
            { "Female", "45", "110001", "Delhi"  },
        };
    }

    // -----------------------------------------------------------------------
    // SETUP
    // -----------------------------------------------------------------------
    @BeforeClass
    public void openApplication() throws IOException {
        System.out.println("========================================");
        System.out.println("  Executing Test Case 5: All Combinations");
        System.out.println("  Genders: Male/Female | Ages: 25,35,45");
        System.out.println("  Pincodes: 400001(Mumbai), 110001(Delhi)");
        System.out.println("========================================");

        System.out.println("[Step] Opening browser...");
        launchBrowser();
        System.out.println("[Done] Browser opened.");

        logger = org.apache.log4j.Logger.getLogger("TC05_CoverFox");
        PropertyConfigurator.configure("log4j (1).properties");
        logger.info("TC05 - Opening application");
    }

    @BeforeMethod
    public void navigateToHome() throws IOException {
        System.out.println("\n[Step] Navigating to home page...");
        driver.get(Utility.readDataFromPropertyFile("url"));

        homePage           = new CoverFoxHomePage(driver);
        healthPlanPage     = new CoverFoxHealthPlanPage(driver);
        memberDetailsPage  = new CoverFoxMemberDetailsPage(driver);
        addressDetailsPage = new CoverFoxAddressDetailsPage(driver);
        resultPage         = new CoverFoxResultPage(driver);

        System.out.println("[Done] Home page ready.");
    }

    // -----------------------------------------------------------------------
    // TEST
    // -----------------------------------------------------------------------
    @Test(dataProvider = "combinations")
    public void validateInsuranceCountForCombination(
            String gender, String age, String pincode, String city)
            throws InterruptedException, IOException {

        System.out.println("----------------------------------------");
        System.out.println("[TEST] " + gender + " | Age: " + age
                         + " | Pincode: " + pincode + " (" + city + ")");
        System.out.println("----------------------------------------");

        // Step 1: Select gender
        System.out.println("[Step] Selecting gender: " + gender + "...");
        homePage.selectGender(gender);
        System.out.println("[Done] " + gender + " selected.");
        Thread.sleep(1000);

        // Step 2: Health Plan Next
        System.out.println("[Step] Clicking Next on Health Plan page...");
        healthPlanPage.clickOnNextButton();
        System.out.println("[Done] Health Plan Next clicked.");
        Thread.sleep(1000);

        // Step 3: Select age
        System.out.println("[Step] Selecting age: " + age + " years...");
        memberDetailsPage.handleAgeDropDown(age);
        System.out.println("[Done] Age " + age + " selected.");

        // Step 4: Member Details Next
        System.out.println("[Step] Clicking Next on Member Details page...");
        memberDetailsPage.clickOnNextButton();
        System.out.println("[Done] Member Details Next clicked.");
        Thread.sleep(1000);

        // Step 5: Pincode
        System.out.println("[Step] Entering Pincode: " + pincode + " (" + city + ")...");
        addressDetailsPage.enterPinCode(pincode);
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
        logger.info("TC05 - " + gender + " | " + age + "y | " + pincode + " → " + count + " plans");

        // Store result for final summary table
        combinationResults.add(new String[]{ gender, age, pincode, city, String.valueOf(count) });

        // Print per-run result
        System.out.printf("[Result] %-7s | Age %-3sy | Pin %-7s | %-8s → %d plans%n",
            gender, age, pincode, city, count);

        // Step 9: Assert > 0
        System.out.println("[Step] Asserting insurance count > 0...");
        Assert.assertTrue(count > 0,
            "No insurance plans listed for: " + gender
            + ", age=" + age + ", pincode=" + pincode);
        System.out.println("[PASS] " + count + " plans listed — assertion passed.");
    }

    // -----------------------------------------------------------------------
    // TEARDOWN — prints the full combination table
    // -----------------------------------------------------------------------
    @AfterClass
    public void closeApplication() {
        // Print full results table
        System.out.println("\n╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║            INSURANCE COMBINATION RESULTS SUMMARY             ║");
        System.out.println("╠══════════╦═══════╦═════════╦══════════╦══════════════════════╣");
        System.out.println("║  Gender  ║  Age  ║ Pincode ║   City   ║  Insurance Count     ║");
        System.out.println("╠══════════╬═══════╬═════════╬══════════╬══════════════════════╣");

        for (String[] row : combinationResults) {
            System.out.printf("║ %-8s ║ %-5s ║ %-7s ║ %-8s ║  %-20s║%n",
                row[0], row[1] + "y", row[2], row[3], row[4] + " plans");
        }

        System.out.println("╚══════════╩═══════╩═════════╩══════════╩══════════════════════╝");
        System.out.println("Total combinations tested: " + combinationResults.size());

        // Print max/min count insight
        if (!combinationResults.isEmpty()) {
            String[] maxRow = combinationResults.get(0);
            String[] minRow = combinationResults.get(0);
            for (String[] row : combinationResults) {
                if (Integer.parseInt(row[4]) > Integer.parseInt(maxRow[4])) { maxRow = row; }
                if (Integer.parseInt(row[4]) < Integer.parseInt(minRow[4])) { minRow = row; }
            }
            System.out.println("[Insight] HIGHEST count: " + maxRow[4] + " plans"
                + " → " + maxRow[0] + ", age " + maxRow[1] + ", " + maxRow[3]);
            System.out.println("[Insight] LOWEST  count: " + minRow[4] + " plans"
                + " → " + minRow[0] + ", age " + minRow[1] + ", " + minRow[3]);
        }

        System.out.println("[Step] Closing browser...");
        closeBrowser();
        System.out.println("[Done] Browser closed.");
        System.out.println("========================================");
        System.out.println("  Test Case 5 Execution Completed");
        System.out.println("========================================");
    }
}
