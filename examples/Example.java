// Google AI Overview Scraper — Scrapeless LLM Chat Scraper (Java example)
//
// Docs:  https://docs.scrapeless.com/en/llm-chat-scraper/quickstart/introduction/
// Token: https://app.scrapeless.com/passport/login?redirect=/quick-start
//
// Run (Java 11+, uses the built-in HttpClient):
//   export SCRAPELESS_API_TOKEN="your_api_token"
//   java Example.java

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class Example {

    private static final String API_URL = "https://api.scrapeless.com/api/v2/scraper/execute";

    public static void main(String[] args) throws Exception {
        String apiToken = System.getenv().getOrDefault("SCRAPELESS_API_TOKEN", "YOUR_API_TOKEN");

        // Optional: add a "webhook" object to receive the result asynchronously.
        String payload = """
            {
              "actor": "scraper.overview",
              "input": {
                "prompt": "Recommended attractions in New York",
                "country": "US",
                "shopping": true
              }
            }
            """;

        HttpClient client = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(30))
                .build();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .timeout(Duration.ofSeconds(180))
                .header("Content-Type", "application/json")
                .header("x-api-token", apiToken)
                .POST(HttpRequest.BodyPublishers.ofString(payload))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() >= 300) {
            throw new RuntimeException("Request failed: " + response.statusCode() + " " + response.body());
        }

        System.out.println("HTTP status: " + response.statusCode());
        System.out.println("Raw response:\n" + response.body());
    }
}
