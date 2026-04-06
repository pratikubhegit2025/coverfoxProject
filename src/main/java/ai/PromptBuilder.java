package ai;

/**
 * PromptBuilder — Factory class that builds reusable, well-structured prompts
 * for the three AI use cases in this framework:
 *
 *   1. Selenium failure analysis
 *   2. Test case generation from feature/requirement text
 *   3. API response validation
 *
 * All methods are static — no instantiation required.
 */
public class PromptBuilder {

    // -------------------------------------------------------------------------
    // 1. FAILURE ANALYSIS PROMPT
    // -------------------------------------------------------------------------

    /**
     * Builds a prompt for AI-powered Selenium failure root-cause analysis.
     *
     * @param testName         TestNG test method name
     * @param exceptionType    Fully-qualified exception class name
     * @param exceptionMessage Exception message
     * @param stackTraceSummary Top stack frames (5–10 lines)
     * @param pageUrl          URL of the page when failure occurred (may be "unknown")
     * @return                 Formatted prompt string
     */
    public static String buildFailureAnalysisPrompt(
            String testName,
            String exceptionType,
            String exceptionMessage,
            String stackTraceSummary,
            String pageUrl) {

        return "You are a senior Selenium WebDriver automation expert.\n"
             + "Analyze the test failure below and respond with EXACTLY three sections:\n\n"
             + "1. ROOT CAUSE (2-3 sentences)\n"
             + "2. SUGGESTED FIX (specific code change or actionable steps)\n"
             + "3. PREVENTION STRATEGY (how to avoid this class of failure)\n\n"
             + "--- FAILURE DETAILS ---\n"
             + "Test Name      : " + testName + "\n"
             + "Exception Type : " + exceptionType + "\n"
             + "Message        : " + exceptionMessage + "\n"
             + "Page URL       : " + pageUrl + "\n"
             + "Stack Trace    :\n" + stackTraceSummary + "\n"
             + "--- END ---\n\n"
             + "Be concise. Do not repeat the failure details in your answer.";
    }

    // -------------------------------------------------------------------------
    // 2. TEST CASE GENERATION PROMPT
    // -------------------------------------------------------------------------

    /**
     * Builds a prompt to generate structured test cases from a feature description.
     *
     * @param featureText  Free-text feature or requirement description
     * @return             Formatted prompt string
     */
    public static String buildTestCaseGenerationPrompt(String featureText) {
        return "You are an experienced QA engineer.\n"
             + "Generate structured manual test cases for the following feature or requirement.\n\n"
             + "FEATURE DESCRIPTION:\n"
             + featureText + "\n\n"
             + "FORMAT each test case exactly as shown below:\n\n"
             + "TC-001: [Short Title]\n"
             + "Precondition : [What must be true before the test]\n"
             + "Steps        : [Numbered list of actions]\n"
             + "Expected     : [What the system should do]\n"
             + "Priority     : High | Medium | Low\n\n"
             + "Generate 3–5 test cases covering the happy path and at least one negative scenario.\n"
             + "Use TC-001, TC-002, ... numbering.";
    }

    // -------------------------------------------------------------------------
    // 3. API RESPONSE VALIDATION PROMPT
    // -------------------------------------------------------------------------

    /**
     * Builds a prompt to validate an API response against expected behaviour.
     *
     * @param apiResponse       The raw API response body (JSON or text)
     * @param expectedBehavior  Human-readable description of what the API should return
     * @return                  Formatted prompt string
     */
    public static String buildApiValidationPrompt(String apiResponse, String expectedBehavior) {
        return "You are an API testing expert.\n"
             + "Validate the API response below against the expected behaviour and provide:\n\n"
             + "1. VALIDATION STATUS  : PASS or FAIL\n"
             + "2. ISSUES FOUND       : List any missing fields, wrong values, or unexpected data\n"
             + "3. RECOMMENDATIONS    : Specific suggestions to fix any issues\n\n"
             + "EXPECTED BEHAVIOUR:\n"
             + expectedBehavior + "\n\n"
             + "ACTUAL API RESPONSE:\n"
             + apiResponse + "\n\n"
             + "Keep the report concise and actionable.";
    }
}
