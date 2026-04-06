package ai;

import ai.AgentReportParser.SuiteReport;
import ai.AgentReportParser.TestResult;

import java.io.File;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * CoverFoxTestAgent — Autonomous AI agent for the CoverFox automation project.
 *
 * ┌─────────────────────────────────────────────────────────────────────┐
 * │  Three modes — run via:                                             │
 * │                                                                     │
 * │  mvn exec:java -Dexec.mainClass="ai.CoverFoxTestAgent"             │
 * │       → default mode: analyze last test run results                 │
 * │                                                                     │
 * │  mvn exec:java -Dexec.mainClass="ai.CoverFoxTestAgent"             │
 * │               -Dexec.args="suggest"                                 │
 * │       → suggest new test cases based on current coverage            │
 * │                                                                     │
 * │  mvn exec:java -Dexec.mainClass="ai.CoverFoxTestAgent"             │
 * │               -Dexec.args="coverage"                                │
 * │       → full coverage gap analysis with scoring                     │
 * │                                                                     │
 * │  Or just double-click run-agent.bat for a menu-driven experience    │
 * └─────────────────────────────────────────────────────────────────────┘
 *
 * Requires: OPENAI_API_KEY environment variable
 */
public class CoverFoxTestAgent {

    private static final String RESULTS_XML =
        System.getProperty("user.dir") + "/test-output/testng-results.xml";

    private static final String TEST_DIR =
        System.getProperty("user.dir") + "/src/test/java/coverFoxTest/";

    private static final String POM_DIR =
        System.getProperty("user.dir") + "/src/main/java/coverFoxPOM/";

    // -----------------------------------------------------------------------
    // ENTRY POINT
    // -----------------------------------------------------------------------

    public static void main(String[] args) throws Exception {
        printBanner();

        String mode = (args != null && args.length > 0) ? args[0].toLowerCase() : "analyze";

        switch (mode) {
            case "analyze":
                analyzeResults();
                break;
            case "suggest":
                suggestNewTests();
                break;
            case "coverage":
                analyzeTestCoverage();
                break;
            default:
                System.out.println("[Agent] Unknown mode: '" + mode + "'");
                System.out.println("[Agent] Valid modes: analyze | suggest | coverage");
                System.out.println("[Agent] Example: mvn exec:java -Dexec.mainClass=\"ai.CoverFoxTestAgent\" -Dexec.args=\"suggest\"");
        }

        System.out.println("\n[Agent] Done. " + timestamp());
    }

    // -----------------------------------------------------------------------
    // MODE 1 — ANALYZE: reads testng-results.xml, sends failures to AI
    // -----------------------------------------------------------------------

    public static void analyzeResults() throws Exception {
        System.out.println("[Agent] MODE  : Analyze Latest Test Run");
        System.out.println("[Agent] Source: " + RESULTS_XML);
        System.out.println();

        SuiteReport report = AgentReportParser.parse(RESULTS_XML);

        // Print the summary box
        printSummaryBox(report);

        // ── All tests passed ──────────────────────────────────────────────
        if (report.failed == 0 && report.total > 0) {
            System.out.println("\n[Agent] All " + report.total + " tests passed.");
            System.out.println("[Agent] Asking AI for quality insights on the passing run...\n");

            String prompt = "A Selenium TestNG automation suite for coverfox.com just completed.\n"
                + "Results: " + report.passed + "/" + report.total + " tests passed, 0 failures.\n\n"
                + "As a QA lead, provide 3 short bullet points:\n"
                + "1. What this result tells us about product quality\n"
                + "2. One risk that is NOT covered by these tests\n"
                + "3. One concrete action to strengthen the suite further\n"
                + "Keep each bullet to 1-2 sentences.";

            String insight = AIResponseParser.extractMessage(AIClient.callOpenAI(prompt));
            printSection("AI QUALITY INSIGHTS", insight);
            return;
        }

        // ── Failures: analyze each one ────────────────────────────────────
        if (!report.failures.isEmpty()) {
            System.out.println("\n[Agent] Analyzing " + report.failures.size()
                + " failure(s) with AI...\n");

            int index = 1;
            for (TestResult failure : report.failures) {
                System.out.println("── Failure " + index++ + " of " + report.failures.size()
                    + " ──────────────────────────────────────────────");
                analyzeOneFailure(failure);
            }
        }

        // ── Slow tests ────────────────────────────────────────────────────
        if (!report.slowTests.isEmpty()) {
            System.out.println("\n[Agent] Detected " + report.slowTests.size()
                + " slow test(s) (over 10 seconds):");
            for (TestResult s : report.slowTests) {
                System.out.printf("         - %s : %,d ms%n", s.testName, s.durationMs);
            }

            StringBuilder slowList = new StringBuilder();
            for (TestResult s : report.slowTests) {
                slowList.append("- ").append(s.testName).append(": ")
                        .append(s.durationMs).append("ms\n");
            }

            String slowPrompt =
                "These Selenium tests for coverfox.com are running too slowly (>10 seconds):\n\n"
                + slowList
                + "\nFor each test, suggest one specific optimization.\n"
                + "Common causes: Thread.sleep(), implicit waits, missing explicit waits, "
                + "slow page rendering. Be concrete about the fix.";

            String slowAdvice = AIResponseParser.extractMessage(AIClient.callOpenAI(slowPrompt));
            printSection("AI ADVICE — SLOW TESTS", slowAdvice);
        }

        // ── Overall recommendation ────────────────────────────────────────
        System.out.println("\n[Agent] Generating overall run recommendation...");
        String overallPrompt =
            "Selenium TestNG run summary for coverfox.com:\n"
            + "  Total: " + report.total + " | Passed: " + report.passed
            + " | Failed: " + report.failed + " | Skipped: " + report.skipped + "\n\n"
            + "As a QA lead, give exactly 3 bullet-point next actions. Be specific and short.";

        String overall = AIResponseParser.extractMessage(AIClient.callOpenAI(overallPrompt));
        printSection("RECOMMENDED NEXT ACTIONS", overall);
    }

    private static void analyzeOneFailure(TestResult failure) {
        // Print failure details
        System.out.println("  Test      : " + failure.testName);
        System.out.println("  Class     : " + failure.className);
        System.out.println("  Exception : " + failure.exceptionType);
        System.out.println("  Message   : " + truncate(failure.exceptionMessage, 150));
        if (!failure.stackTrace.isEmpty()) {
            System.out.println("  Stack     :");
            for (String line : failure.stackTrace.split("\n")) {
                System.out.println("    " + line.trim());
            }
        }
        System.out.println();
        System.out.println("  [Agent] Sending to AI for root cause analysis...");

        String prompt = PromptBuilder.buildFailureAnalysisPrompt(
            failure.testName,
            failure.exceptionType,
            failure.exceptionMessage,
            failure.stackTrace,
            "www.coverfox.com"
        );

        String raw      = AIClient.callOpenAI(prompt);
        String analysis = AIResponseParser.extractMessage(raw);

        System.out.println();
        System.out.println(analysis);
        System.out.println();
    }

    // -----------------------------------------------------------------------
    // MODE 2 — SUGGEST: reads existing tests and asks AI what's missing
    // -----------------------------------------------------------------------

    public static void suggestNewTests() throws Exception {
        System.out.println("[Agent] MODE: Suggest New Test Cases\n");

        String testSummary = readFileSummary(TEST_DIR,  ".java", 25);
        String pomSummary  = readFileSummary(POM_DIR,   ".java", 15);

        String prompt =
            "You are a senior QA engineer reviewing test coverage for coverfox.com — "
            + "an Indian health insurance comparison website.\n\n"
            + "APPLICATION FLOW:\n"
            + "  1. Home page: Select gender (Male / Female)\n"
            + "  2. Health Plan page: Click Next\n"
            + "  3. Member Details page: Select age from dropdown\n"
            + "  4. Address Details page: Enter pincode + mobile number, click Continue\n"
            + "  5. Results page: Shows matching health insurance plans with count\n\n"
            + "EXISTING TEST CLASSES:\n" + testSummary + "\n\n"
            + "AVAILABLE PAGE OBJECTS:\n" + pomSummary + "\n\n"
            + "Suggest exactly 5 NEW test cases not already in the list above.\n"
            + "Format each as:\n"
            + "TC-NNN: [Title]\n"
            + "What to test : [1-2 sentences]\n"
            + "Why it matters: [1 sentence]\n"
            + "Priority      : High | Medium | Low\n\n"
            + "Focus on: edge cases, negative paths, UI validations, boundary values.";

        System.out.println("[Agent] Reading current test files...");
        System.out.println("[Agent] Asking AI for suggestions...\n");

        String raw         = AIClient.callOpenAI(prompt);
        String suggestions = AIResponseParser.extractMessage(raw);
        printSection("AI-SUGGESTED NEW TEST CASES", suggestions);

        System.out.println("\n[Agent] To implement any suggestion, run:");
        System.out.println("  claude  (in this folder)");
        System.out.println("  Then type: /new-test  or describe what you want directly.");
    }

    // -----------------------------------------------------------------------
    // MODE 3 — COVERAGE: scores the test suite and identifies gaps
    // -----------------------------------------------------------------------

    public static void analyzeTestCoverage() throws Exception {
        System.out.println("[Agent] MODE: Analyze Test Coverage\n");

        String testSummary = readFileSummary(TEST_DIR, ".java", 40);

        String prompt =
            "You are a QA architect reviewing the test coverage of an automated suite "
            + "for coverfox.com (Indian health insurance comparison website).\n\n"
            + "CURRENT TEST SUITE:\n" + testSummary + "\n\n"
            + "Provide a structured coverage report:\n\n"
            + "1. COVERAGE SCORE    : X / 10 (with 1-line justification)\n"
            + "2. STRENGTHS         : 3 bullet points — what is well covered\n"
            + "3. CRITICAL GAPS     : 3-5 bullet points — important flows NOT tested\n"
            + "4. RISK AREAS        : What could break in production without being caught\n"
            + "5. TOP 3 PRIORITIES  : The 3 most important tests to add next (in order)\n\n"
            + "Be specific. Reference actual test class names where relevant.";

        System.out.println("[Agent] Reading test files for coverage analysis...");
        System.out.println("[Agent] Sending to AI for scoring and gap analysis...\n");

        String raw      = AIClient.callOpenAI(prompt);
        String analysis = AIResponseParser.extractMessage(raw);
        printSection("TEST COVERAGE ANALYSIS", analysis);
    }

    // -----------------------------------------------------------------------
    // FILE READER — reads first N lines of each .java file in a directory
    // -----------------------------------------------------------------------

    private static String readFileSummary(String dirPath, String extension, int linesPerFile) {
        StringBuilder sb  = new StringBuilder();
        File          dir = new File(dirPath);

        if (!dir.exists()) {
            return "(directory not found: " + dirPath + ")";
        }

        File[] files = dir.listFiles((d, n) -> n.endsWith(extension));
        if (files == null || files.length == 0) {
            return "(no " + extension + " files found in " + dirPath + ")";
        }

        for (File f : files) {
            sb.append("\n--- ").append(f.getName()).append(" ---\n");
            try {
                List<String> lines = Files.readAllLines(f.toPath());
                int limit = Math.min(lines.size(), linesPerFile);
                for (int i = 0; i < limit; i++) {
                    String line = lines.get(i).trim();
                    // Include only meaningful lines (skip blank lines and import statements)
                    if (!line.isEmpty() && !line.startsWith("import ")) {
                        sb.append(line).append("\n");
                    }
                }
            } catch (Exception e) {
                sb.append("(could not read: ").append(e.getMessage()).append(")\n");
            }
        }
        return sb.toString();
    }

    // -----------------------------------------------------------------------
    // PRINT HELPERS
    // -----------------------------------------------------------------------

    private static void printBanner() {
        System.out.println();
        System.out.println("╔══════════════════════════════════════════════════════════════╗");
        System.out.println("║            COVERFOX AI TEST AGENT                            ║");
        System.out.println("║  Reads results → Calls OpenAI → Gives actionable advice      ║");
        System.out.println("╠══════════════════════════════════════════════════════════════╣");
        System.out.println("║  Modes: analyze (default) | suggest | coverage               ║");
        System.out.println("╚══════════════════════════════════════════════════════════════╝");
        System.out.println();
    }

    private static void printSummaryBox(SuiteReport report) {
        String statusLabel = (report.failed == 0)
            ? "ALL PASSED"
            : report.failed + " FAILURE(S) DETECTED";

        System.out.println("┌──────────────────────────────────────┐");
        System.out.println("│          TEST RUN SUMMARY            │");
        System.out.println("├──────────────────────────────────────┤");
        System.out.printf( "│  Total   : %-27d│%n", report.total);
        System.out.printf( "│  Passed  : %-27d│%n", report.passed);
        System.out.printf( "│  Failed  : %-27d│%n", report.failed);
        System.out.printf( "│  Skipped : %-27d│%n", report.skipped);
        System.out.println("├──────────────────────────────────────┤");
        System.out.printf( "│  Status  : %-27s│%n", statusLabel);
        System.out.println("└──────────────────────────────────────┘");
    }

    private static void printSection(String title, String content) {
        String bar = "=".repeat(62);
        System.out.println("\n" + bar);
        System.out.println("  " + title);
        System.out.println(bar);
        System.out.println(content);
        System.out.println(bar);
    }

    private static String truncate(String s, int max) {
        if (s == null || s.isEmpty()) return "(none)";
        return s.length() > max ? s.substring(0, max) + "..." : s;
    }

    private static String timestamp() {
        return LocalDateTime.now()
            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
