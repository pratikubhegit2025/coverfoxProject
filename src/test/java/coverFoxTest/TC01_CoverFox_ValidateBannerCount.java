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
public static org.apache.log4j.Logger logger; //add log4j and slf4j dependencies

// open browser/open an application
@BeforeClass
public void openApplication() throws IOException {
launchBrowser();
logger = org.apache.log4j.Logger.getLogger("CoverFox_Test 2025");
PropertyConfigurator.configure("log4j (1).properties");
logger.info("Opening application");

}
// gender,next click, age selection, pincode, mobile,nect click
@BeforeMethod
public void enterDetails() throws EncryptedDocumentException, IOException,InterruptedException {

homePage = new CoverFoxHomePage(driver);
healthPlanPage = new CoverFoxHealthPlanPage(driver);
memberDetailsPage = new CoverFoxMemberDetailsPage(driver);
addressDetailsPage = new CoverFoxAddressDetailsPage(driver);
resultPage = new CoverFoxResultPage(driver);
homePage.clickOnGenderButton();
logger.info("Clicking on gender button");
Thread.sleep(1000);
healthPlanPage.clickOnNextButton();
logger.info("Clicking on Next button");

Thread.sleep(1000);
memberDetailsPage.handleAgeDropDown(Utility.readDataFromExcel(excelpath,sheetName, 1, 0));
logger.info("Selecting age");
memberDetailsPage.clickOnNextButton();
logger.info("Clicking on next button");
Thread.sleep(1000);
addressDetailsPage.enterPinCode(Utility.readDataFromExcel(excelpath,sheetName, 1, 1));
logger.info("Entering Pincode");
addressDetailsPage.enterMobNum(Utility.readDataFromExcel(excelpath,sheetName, 1, 2));
logger.info("Entering mobile number");
addressDetailsPage.clickOnContinueButton();
logger.info("Clicking on Continue button");
Thread.sleep(4000);
}
@Test
public void valiadatePolicyCount() throws InterruptedException {
int textCount = resultPage.getCountFromText();
int bannerCount = resultPage.getCountFromBanner();
Thread.sleep(1000);
//Assert.fail();
Assert.assertEquals(textCount, bannerCount, "text count not matching with banner count, TC failed");

}
// logout From application
// close Browser/close an application
@AfterClass
public void closeApplication() {
closeBrowser();
}
}
