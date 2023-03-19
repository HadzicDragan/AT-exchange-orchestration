package com.adh.exchange;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * This implementation could be used in the future, as it stands right now
 * blocking requests should be the default implementation
 */
public class NonBlockingApiClient {

    private final WebClient webClient;
    private final Map<String, String> headers;
    private WebClient.RequestHeadersUriSpec<?> request;

    private Class<?> type;

    private NonBlockingApiClient(final String url) {
        this.webClient = WebClient.create(url);
        this.headers = new HashMap<>();
    }

    /**
     * Marked Deprecated because this should not yet be used in any case.
     * This should be explored in the future if this implementation is even needed or not.
     *
     * @param url
     * @return
     */
    @Deprecated
    public static NonBlockingApiClient of(String url) {
        return new NonBlockingApiClient(url);
    }

    public NonBlockingApiClient headers(Map<String, String> headers) {
        this.headers.putAll(headers);
        return this;
    }

    public NonBlockingApiClient toType(Class<?> type) {
        this.type = type;
        return this;
    }

    public NonBlockingApiClient get() {
        this.request = webClient.get();
        return this;
    }

    public Object build() {
        return null;

//        return this.request
//                .accept(MediaType.APPLICATION_JSON)
//                .exchangeToMono(response -> {
//                    if (response.statusCode().isSameCodeAs(HttpStatusCode.valueOf(200))) {
//                        Mono responseRequest = response.toEntity(this.type);
//                    } else {
//                        // TODO: add logging that we could not reach the client with a valid status code
//                    }
//                });
    }

    private void validateValidRequest() {
        if (Objects.isNull(this.request)) {
            throw new RuntimeException();
        }
        if (Objects.isNull(this.type)) {
            throw new RuntimeException();
        }
    }
}
