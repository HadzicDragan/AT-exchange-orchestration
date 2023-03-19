package com.adh.exchange.bybit.api.client;

import com.adh.exchange.ApiClient;
import com.adh.exchange.TypeConverter;
import com.adh.exchange.bybit.service.EncryptionRequestService;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class OrderClient {

    private static final String PLACE_LIMIT_ORDER = "LIMIT_ORDER_URL";

    private final EncryptionRequestService encryptionService;
    private final TypeConverter converter;

    public OrderClient(final EncryptionRequestService encryptionService, TypeConverter converter) {
        this.encryptionService = encryptionService;
        this.converter = converter;
    }

    /**
     * Example of how a service should look
     * Real implementation should be implemented
     *
     * TODO - build correct API
     *
     * @return
     */
    public Object placeLimitOrderExample() {
        final Map<String, Object> requestParams = new HashMap<>();
        final Map<String, String> encryptionHeaders = this.encryptionService.createPOSTHeaders(requestParams);

        final String bodyRequest = converter.writeObjectToString(null);

        final String responseData = ApiClient.of(PLACE_LIMIT_ORDER)
                .headers(encryptionHeaders)
                .post(bodyRequest)
                .buildAsString();

        // add the valid Class type
        return converter.stringToType(responseData, null);
    }
}
