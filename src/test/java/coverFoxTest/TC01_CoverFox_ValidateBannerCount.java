package coverFoxTest;
import java.io.IOException;

import org.apache.log4j.PropertyConfigurator;
import org.apache.poi.EncryptedDocumentException;
import org.slf4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import coverFoxBase.Base;
import coverFoxPOM.CoverFoxAddressDetailsPage;
import coverFoxPOM.CoverFoxHealthPlanPage;
import coverFoxPOM.CoverFoxHomePage;
import coverFoxPOM.CoverFoxMemberDetailsPage;
import coverFoxPOM.CoverFoxResultPage;
import coverFoxUtility.Utility;

public class TC01_CoverFox_ValidateBannerCount extends Base {

CoverFoxHomePage homePage;
CoverFoxHealthPlanPage healthPlanPage;
CoverFoxMemberDetailsPage memberDetailsPage;
CoverFoxAddressDetailsPage addressDetailsPage;
CoverFoxResultPage resultPage;
String excelpath = System.getProperty("user.dir")+"\\TestSheets\\TestExcel.xls";
String sheetName = "Sheet4";
public static org.apache.log4j.Logger logger;

@BeforeClass
public void openApplication() throws IOException {
    System.out.println("========================================");
    System.out.println("  Executing Test Case 1: Validate Banner Count");
    System.out.println("========================================");

    System.out.println("[Step] Opening browser...");
    launchBrowser();
    System.out.println("[Done] Browser opened successfully.");

    logger = org.apache.log4j.Logger.getLogger("CoverFox_Test 2025");
    PropertyConfigurator.configure("log4j (1).properties");
    logger.info("Opening application");

    System.out.println("[Step] Navigating to application URL...");
    System.out.println("[Done] Application URL loaded: " + driver.getCurrentUrl());
}

@BeforeMethod
public void enterDetails() throws EncryptedDocumentException, IOException, InterruptedException {

    System.out.println("----------------------------------------");
    System.out.println("[Step] Initializing Page Objects...");
    homePage = new CoverFoxHomePage(driver);
    healthPlanPage = new CoverFoxHealthPlanPage(driver);
    memberDetailsPage = new CoverFoxMemberDetailsPage(driver);
    addressDetailsPage = new CoverFoxAddressDetailsPage(driver);
    resultPage = new CoverFoxResultPage(driver);
    System.out.println("[Done] Page Objects initialized.");

    System.out.println("[Step] Clicking on Male gender button...");
    homePage.clickOnGenderButton();
    logger.info("Clicking on gender button");
    System.out.println("[Done] Male gender button clicked.");

    Thread.sleep(1000);

    System.out.println("[Step] Clicking on Next button on Health Plan page...");
    healthPlanPage.clickOnNextButton();
    logger.info("Clicking on Next button");
    System.out.println("[Done] Next button clicked on Health Plan page.");

    Thread.sleep(1000);

    System.out.println("[Step] Selecting age from dropdown...");
    memberDetailsPage.handleAgeDropDown(Utility.readDataFromExcel(excelpath, sheetName, 1, 0));
    logger.info("Selecting age");
    System.out.println("[Done] Age selected from dropdown.");

    System.out.println("[Step] Clicking on Next button on Member Details page...");
    memberDetailsPage.clickOnNextButton();
    logger.info("Clicking on next button");
    System.out.println("[Done] Next button clicked on Member Details page.");

    Thread.sleep(1000);

    System.out.println("[Step] Entering Pin Code...");
    addressDetailsPage.enterPinCode(Utility.readDataFromExcel(excelpath, sheetName, 1, 1));
    logger.info("Entering Pincode");
    System.out.println("[Done] Pin Code entered.");

    System.out.println("[Step] Entering Mobile Number...");
    addressDetailsPage.enterMobNum(Utility.readDataFromExcel(excelpath, sheetName, 1, 2));
    logger.info("Entering mobile number");
    System.out.println("[Done] Mobile Number entered.");

    System.out.println("[Step] Clicking on Continue button...");
    addressDetailsPage.clickOnContinueButton();
    logger.info("Clicking on Continue button");
    System.out.println("[Done] Continue button clicked. Waiting for results...");

    Thread.sleep(4000);
    System.out.println("[Done] Results page loaded.");
}

@Test
public void valiadatePolicyCount() throws InterruptedException {
    System.out.println("----------------------------------------");
    System.out.println("[Step] Fetching policy count from result text...");
    int textCount = resultPage.getCountFromText();
    System.out.println("[Done] Text count fetched: " + textCount);

    System.out.println("[Step] Fetching policy count from banners...");
    int bannerCount = resultPage.getCountFromBanner();
    System.out.println("[Done] Banner count fetched: " + bannerCount);

    Thread.sleep(1000);

    System.out.println("[Step] Validating text count matches banner count...");
    //Assert.fail();
    Assert.assertEquals(textCount, bannerCount, "text count not matching with banner count, TC failed");
    System.out.println("[Done] Validation PASSED — Text count (" + textCount + ") matches Banner count (" + bannerCount + ").");
}

@AfterClass
public void closeApplication() {
    System.out.println("[Step] Closing browser...");
    closeBrowser();
    System.out.println("[Done] Browser closed.");
    System.out.println("========================================");
    System.out.println("  Test Case 1 Execution Completed");
    System.out.println("========================================");
}
}
