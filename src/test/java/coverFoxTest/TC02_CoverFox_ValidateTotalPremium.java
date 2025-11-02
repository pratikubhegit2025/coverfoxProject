package coverFoxTest;

import java.io.IOException;

import org.apache.log4j.PropertyConfigurator;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import coverFoxBase.Base;
import coverFoxPOM.CoverFoxHomePage;

public class TC02_CoverFox_ValidateTotalPremium  extends Base {
	CoverFoxHomePage homePage;
	public static org.apache.log4j.Logger logger; //add log4j and slf4j dependencies
	@BeforeClass
	public void openApplication() throws IOException {
	launchBrowser();
	logger = org.apache.log4j.Logger.getLogger("8th_June_CoverFox");
	PropertyConfigurator.configure("log4j (1).properties");
	logger.info("Opening application");
	}
	public void insuranceDropDownButton() throws InterruptedException {
		homePage = new CoverFoxHomePage(driver);
		Thread.sleep(1000);
		homePage.clickOnGenderButton();
	}
	@Test
	public void ValidatePremium() throws InterruptedException {
		
		
		
		
	}

}
