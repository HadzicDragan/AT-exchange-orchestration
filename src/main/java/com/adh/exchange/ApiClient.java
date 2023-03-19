package com.adh.exchange;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

public class ApiClient {

    private final HttpRequest.Builder builder;

    private ApiClient(final String url) {
        this.builder = HttpRequest.newBuilder(URI.create(url));
    }

    public static ApiClient of(String url) {
        return new ApiClient(url);
    }

    public ApiClient headers(Map<String, String> headers) {
        headers.forEach(this.builder::header);
        return this;
    }

    public ApiClient get() {
        this.builder.GET();
        return this;
    }

    public ApiClient post(String body) {
        this.contentTypeApplicationJson();
        this.builder.POST(HttpRequest.BodyPublishers.ofString(body));
        return this;
    }

    public ApiClient contentTypeApplicationJson() {
        this.builder.header("Content-Type", "application/json");
        return this;
    }

    public String buildAsString() {
        final HttpRequest request = this.builder.build();

        try {
            HttpResponse response = HttpClient.newHttpClient()
                    .send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                // request failed
            }
            return (String) response.body();
        } catch (IOException | InterruptedException ex) {
            return null;
        }
    }
}
