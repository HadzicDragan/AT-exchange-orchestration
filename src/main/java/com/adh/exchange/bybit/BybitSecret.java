package com.adh.exchange.bybit;

import com.adh.exchange.ApiSecret;
import com.adh.exchange.IllegalConfigurationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.Objects;

@PropertySource("classpath:secret.properties")
@Component
public class BybitSecret implements ApiSecret {
    @Value("${api.key}")
    private String apiKey;

    @Value("${api.secret}")
    private String apiSecret;

    private static final String RECV_WINDOW = "5000";

    /**
     * Retrieve the API key for the account, if the key can not be fetched
     * exception will stop the program from continuing to function.
     *
     * @return API key secret associated to the account
     */
    @Override
    public final String apiKey() {
        if (Objects.isNull(this.apiKey) || this.apiKey.isEmpty()) {
            throw new IllegalConfigurationException("api.key configuration could not be retrieved.");
        }
        return this.apiKey;
    }

    @Override
    public String apiSecret() {
        if (Objects.isNull(this.apiSecret) || this.apiSecret.isEmpty()) {
            throw new IllegalConfigurationException("api.secret configuration could not be retrieved.");
        }
        return apiSecret;
    }

    public String getRecvWindow() {
        return RECV_WINDOW;
    }
}
