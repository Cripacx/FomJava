package de.cripacx.fomjava.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import de.cripacx.fomjava.FomJavaApplication;
import de.cripacx.fomjava.exception.FomException;
import de.cripacx.fomjava.exception.recipe.ServerErrorException;
import lombok.AllArgsConstructor;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
public class AzureOpenAI {

    private final String url;
    private final String apiKey;

    public Map<String, Object> sendRequest(String userMessage) throws FomException {
        try {

            // Create a Map to represent the JSON data
            Map<String, Object> requestData = new HashMap<>();
            requestData.put("messages", List.of(
                    Map.of(
                            "role", "system",
                            "content", "Export all ingredients as Json Format and split name and amount. Example: {\"ingredients\":[{\"name\":\"ExampleName\",\"amount\":\"exampleamount\"}]}. And dont add Notes!"
                    ),
                    Map.of(
                            "role", "user",
                            "content", userMessage
                    )
            ));
            requestData.put("temperature", 0.2);
            requestData.put("top_p", 0.95);
            requestData.put("frequency_penalty", 0);
            requestData.put("presence_penalty", 0);
            requestData.put("max_tokens", 2000);

            String body = FomJavaApplication.getGson().toJson(requestData);

            // Create the HttpClient
            HttpClient httpClient = HttpClient.newHttpClient();

            // Create the HttpRequest
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(this.url))
                    .header("Content-Type", "application/json")
                    .header("api-key", this.apiKey)
                    .POST(HttpRequest.BodyPublishers.ofString(body, StandardCharsets.UTF_8))
                    .build();

            // Send the request and get the response
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            // Print the response code and body
            System.out.println("Response Code: " + response.statusCode());
            return FomJavaApplication.getGson().fromJson(response.body(), Map.class);

        } catch (Exception e) {
            throw new ServerErrorException();
        }
    }

}
