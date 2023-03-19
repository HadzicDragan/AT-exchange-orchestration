package com.adh.exchange.bybit.api.client;

import com.adh.exchange.ApiClient;
import com.adh.exchange.TypeConverter;
import com.adh.exchange.bybit.api.dto.ServerTimeResponse;
import org.springframework.stereotype.Component;

@Component
public class ServerTimeClient {

    private final TypeConverter converter;

    public ServerTimeClient(TypeConverter converter) {
        this.converter = converter;
    }

    public ServerTimeResponse getCurrentServerTime() {
        final String responseValue = ApiClient.of(Endpoint.SERVER_TIME)
                .get()
                .buildAsString();
        return converter.stringToType(responseValue, ServerTimeResponse.class);
    }
}
