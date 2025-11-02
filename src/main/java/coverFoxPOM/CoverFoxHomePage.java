package coverFoxPOM;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;
public class CoverFoxHomePage
{
//varible declartion-->webElements
//WebElement name=driver.findElement(By.x);
@FindBy(xpath = "//div[text()='Male']") private WebElement gender;
@FindBy(xpath = "(//li[@class='nav-item-dropdown'])[1]") private WebElement insurance;
@FindBy(xpath = "//body/header[@class='react_native_hide']/div[@id='top-bar']/div[@class='main-header']/ul[@class='nav-items-left hidden-xs header-ver2']/li[1]") private WebElement car;
@FindBy(xpath = "//body/header[@class='react_native_hide']/div[@id='top-bar']/div[@class='main-header']/ul[@class='nav-items-left hidden-xs header-ver2']/li[1]/ul[1]/li[1]") private WebElement carInsurane;



//constructor-->variable initialize

public CoverFoxHomePage(WebDriver driver)
{
PageFactory.initElements(driver, this);
}
//methods
public void clickOnGenderButton()
{
	Reporter.log("Clicking on gender button");
gender.click();
}
public void insuranceDropDown(WebDriver driver) {
	Reporter.log("Mouse overing on insurance button");
	Actions action = new Actions(driver);
	action.moveToElement(insurance).perform();
	car.click();
	carInsurane.click();
}
}