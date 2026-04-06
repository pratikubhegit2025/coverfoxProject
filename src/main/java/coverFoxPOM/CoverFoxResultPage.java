package coverFoxPOM;

import java.util.List;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Reporter;
public class CoverFoxResultPage {
@FindBy(xpath = "//div[contains(text(),'matching Health')]") private WebElement resultText;
@FindBy(className = "plan-card-container") private List<WebElement> planList;
public CoverFoxResultPage(WebDriver driver)
{
PageFactory.initElements(driver, this);
}
public int getCountFromText() {
	Reporter.log("Getting policy count");
	String resultInString = resultText.getText().substring(0, 2);
	int countFromText = Integer.parseInt(resultInString);
	return countFromText;
}

public int getCountFromBanner()
{
    Reporter.log("Getting policy count from displayed");
    int countFromBanner = planList.size();
    return countFromBanner;
}

/**
 * Robust insurance count reader — extracts the leading number from result text.
 * Handles single-digit ("5 matching...") and multi-digit ("12 matching...") counts.
 * Use this in new test cases instead of getCountFromText().
 */
public int getInsuranceCount()
{
    Reporter.log("Getting insurance count from result header");
    String fullText = resultText.getText().trim();           // e.g. "12 matching Health Insurance plans"
    String countStr = fullText.split(" ")[0].trim();        // first word = the number
    int count = Integer.parseInt(countStr);
    System.out.println("[Result] Insurance count from text  : " + count);
    System.out.println("[Result] Insurance cards on page    : " + planList.size());
    return count;
}
public void validateResult()
{
	
String resultInString = resultText.getText().substring(0, 2);
int resultNumber = Integer.parseInt(resultInString);
int planListNumber = planList.size();
System.out.println("Result number is "+resultNumber);
System.out.println("Plan list number is "+planListNumber);
if(resultNumber==planListNumber)
{
System.out.println("results are matching TC is passed");
System.out.println("Testing Finished");
}
else {
System.out.println("results are not matching TC is Failed");
}
}
}
