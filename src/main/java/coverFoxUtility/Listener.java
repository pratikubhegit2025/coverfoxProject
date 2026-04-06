package coverFoxUtility;
import java.io.IOException;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.Reporter;
import coverFoxBase.Base;
// AI integration imports
import ai.AIClient;
import ai.AIResponseParser;
import ai.PromptBuilder;

public class Listener extends Base implements ITestListener
{
@Override
public void onTestSuccess(ITestResult result) {
Reporter.log("TC "+result.getName()+" passed", true);
}

@Override
public void onTestFailure(ITestResult result) {
    // ---- Existing logic: take screenshot on failure (unchanged) ----
    try {
        Reporter.log("Taking screenshot", true);
        Utility.takeScreenShot(driver, result.getName());
    } catch (IOException e) {
        e.printStackTrace();
    }

    // ---- AI ADDITION: Analyze failure cause via OpenAI ----
    try {
        Throwable throwable = result.getThrowable();
        String exceptionType    = throwable != null ? throwable.getClass().getName() : "Unknown";
        String exceptionMessage = throwable != null ? throwable.getMessage()        : "No message";

        // Collect top 8 stack frames to keep the prompt concise
        StringBuilder stackSummary = new StringBuilder();
        if (throwable != null) {
            StackTraceElement[] frames = throwable.getStackTrace();
            int limit = Math.min(frames.length, 8);
            for (int i = 0; i < limit; i++) {
                stackSummary.append("  at ").append(frames[i].toString()).append("\n");
            }
        }

        // Best-effort: read current page URL (driver may be null if browser never opened)
        String pageUrl = "unknown";
        try {
            if (driver != null) {
                pageUrl = driver.getCurrentUrl();
            }
        } catch (Exception ignored) { /* driver closed or unresponsive */ }

        // Build and send the prompt
        String prompt = PromptBuilder.buildFailureAnalysisPrompt(
                result.getName(),
                exceptionType,
                exceptionMessage,
                stackSummary.toString(),
                pageUrl);

        String rawResponse = AIClient.callOpenAI(prompt);
        String analysis    = AIResponseParser.formatFailureAnalysis(rawResponse);

        // Log to TestNG Reporter (appears in HTML report) and console
        Reporter.log(analysis, true);

    } catch (Exception aiEx) {
        // AI analysis failure must NEVER affect the test run outcome
        Reporter.log("[AI] Analysis skipped due to error: " + aiEx.getMessage(), true);
    }
    // ---- END AI ADDITION ----
}

}
