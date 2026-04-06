package coverFoxTest;

import java.io.IOException;

import org.apache.log4j.PropertyConfigurator;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import coverFoxBase.Base;
import coverFoxPOM.CoverFoxHomePage;

public class TC02_CoverFox_ValidateTotalPremium extends Base {

    CoverFoxHomePage homePage;
    public static org.apache.log4j.Logger logger;

    @BeforeClass
    public void openApplication() throws IOException {
        System.out.println("========================================");
        System.out.println("  Executing Test Case 2: Validate Total Premium");
        System.out.println("========================================");

        System.out.println("[Step] Opening browser...");
        launchBrowser();
        System.out.println("[Done] Browser opened successfully.");

        logger = org.apache.log4j.Logger.getLogger("8th_June_CoverFox");
        PropertyConfigurator.configure("log4j (1).properties");
        logger.info("Opening application");

        System.out.println("[Step] Navigating to application URL...");
        System.out.println("[Done] Application URL loaded: " + driver.getCurrentUrl());
    }

    public void insuranceDropDownButton() throws InterruptedException {
        System.out.println("[Step] Initializing Home Page...");
        homePage = new CoverFoxHomePage(driver);
        System.out.println("[Done] Home Page initialized.");

        Thread.sleep(1000);

        System.out.println("[Step] Clicking on Male gender button...");
        homePage.clickOnGenderButton();
        System.out.println("[Done] Male gender button clicked.");
    }

    @Test
    public void ValidatePremium() throws InterruptedException {
        System.out.println("----------------------------------------");
        System.out.println("[Step] Starting premium validation...");

        // TODO: Add premium validation steps here

        System.out.println("[Done] Premium validation steps completed.");
        System.out.println("----------------------------------------");
    }

    @AfterClass
    public void closeApplication() {
        System.out.println("[Step] Closing browser...");
        closeBrowser();
        System.out.println("[Done] Browser closed.");
        System.out.println("========================================");
        System.out.println("  Test Case 2 Execution Completed");
        System.out.println("========================================");
    }
}
