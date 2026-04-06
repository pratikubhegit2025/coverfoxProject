package ai;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * AIClient — Handles all communication with the OpenAI Chat Completions API.
 *
 * API key is read from environment variable OPENAI_API_KEY (never hardcoded).
 * Uses java.net.http.HttpClient (built-in since Java 11) — no extra HTTP library needed.
 */
public class AIClient {

    private static final String API_URL   = "https://api.openai.com/v1/chat/completions";
    private static final String MODEL     = "gpt-4o-mini";
    private static final int    MAX_TOKENS = 600;

    /**
     * Sends a prompt to OpenAI and returns the raw JSON response body as a String.
     * Use AIResponseParser.extractMessage() to get the plain-text answer.
     *
     * @param prompt  The full prompt string
     * @return        Raw JSON response, or an error sentinel string prefixed with "[AI]"
     */
    public static String callOpenAI(String prompt) {
        String apiKey = System.getenv("OPENAI_API_KEY");
        if (apiKey == null || apiKey.trim().isEmpty()) {
            return "[AI] OPENAI_API_KEY environment variable is not set. Skipping AI analysis.";
        }

        // Build the request JSON body
        JSONObject userMessage = new JSONObject();
        userMessage.put("role", "user");
        userMessage.put("content", prompt);

        JSONObject requestBody = new JSONObject();
        requestBody.put("model", MODEL);
        requestBody.put("messages", new JSONArray().put(userMessage));
        requestBody.put("max_tokens", MAX_TOKENS);
        requestBody.put("temperature", 0.3);  // Lower temperature → more focused, deterministic output

        try {
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL))
                    .header("Content-Type", "application/json")
                    .header("Authorization", "Bearer " + apiKey)
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();

        } catch (IOException e) {
            return "[AI] Network error during OpenAI call: " + e.getMessage();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restore the interrupted status
            return "[AI] OpenAI call interrupted: " + e.getMessage();
        }
    }
}
