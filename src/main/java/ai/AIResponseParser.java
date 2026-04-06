package ai;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * AIResponseParser — Parses raw OpenAI API JSON responses into usable Java types.
 *
 * The OpenAI Chat Completions response looks like:
 * {
 *   "choices": [
 *     { "message": { "role": "assistant", "content": "..." } }
 *   ]
 * }
 *
 * All methods are static — no instantiation required.
 */
public class AIResponseParser {

    // -------------------------------------------------------------------------
    // CORE EXTRACTOR
    // -------------------------------------------------------------------------

    /**
     * Extracts the assistant's plain-text reply from a raw OpenAI response.
     *
     * Handles:
     *   - Successful responses (choices[0].message.content)
     *   - OpenAI API errors  (error.message)
     *   - Sentinel strings from AIClient (prefixed with "[AI]")
     *   - Malformed JSON
     *
     * @param rawResponse  Raw string returned by AIClient.callOpenAI()
     * @return             Human-readable assistant message, or a descriptive error
     */
    public static String extractMessage(String rawResponse) {
        if (rawResponse == null || rawResponse.isEmpty()) {
            return "[AI] Received empty response from OpenAI.";
        }

        // Sentinel strings from AIClient are returned as-is (e.g. key not set, network error)
        if (rawResponse.startsWith("[AI]")) {
            return rawResponse;
        }

        try {
            JSONObject json = new JSONObject(rawResponse);

            // Check for API-level error
            if (json.has("error")) {
                String errorMsg = json.getJSONObject("error").optString("message", "Unknown API error");
                return "[AI] OpenAI API Error: " + errorMsg;
            }

            // Extract the assistant message from choices[0]
            JSONArray choices = json.optJSONArray("choices");
            if (choices != null && choices.length() > 0) {
                return choices
                        .getJSONObject(0)
                        .getJSONObject("message")
                        .getString("content")
                        .trim();
            }

            return "[AI] No choices found in OpenAI response.";

        } catch (Exception e) {
            return "[AI] Failed to parse OpenAI response: " + e.getMessage()
                 + "\nRaw (first 300 chars): " + rawResponse.substring(0, Math.min(300, rawResponse.length()));
        }
    }

    // -------------------------------------------------------------------------
    // TEST CASE PARSER
    // -------------------------------------------------------------------------

    /**
     * Parses AI-generated test cases (from buildTestCaseGenerationPrompt) into a List.
     * Each entry in the list is one complete test case block (TC-001, TC-002, ...).
     *
     * @param rawResponse  Raw string returned by AIClient.callOpenAI()
     * @return             List of test case strings; single-element list on parse failure
     */
    public static List<String> parseTestCases(String rawResponse) {
        List<String> testCases = new ArrayList<>();
        String content = extractMessage(rawResponse);

        // Split on the TC-NNN: pattern that starts each test case
        String[] parts = content.split("(?=TC-\\d{3}:)");
        for (String part : parts) {
            String trimmed = part.trim();
            if (!trimmed.isEmpty()) {
                testCases.add(trimmed);
            }
        }

        // Fall back: return full content as single item if no TC-NNN markers found
        if (testCases.isEmpty()) {
            testCases.add(content);
        }
        return testCases;
    }

    // -------------------------------------------------------------------------
    // FAILURE ANALYSIS FORMATTER
    // -------------------------------------------------------------------------

    /**
     * Returns the failure analysis as a single formatted string, ready for
     * console/Reporter logging.
     *
     * @param rawResponse  Raw string returned by AIClient.callOpenAI()
     * @return             Formatted analysis block
     */
    public static String formatFailureAnalysis(String rawResponse) {
        String message = extractMessage(rawResponse);
        return "\n========== AI FAILURE ANALYSIS ==========\n"
             + message
             + "\n=========================================\n";
    }
}
