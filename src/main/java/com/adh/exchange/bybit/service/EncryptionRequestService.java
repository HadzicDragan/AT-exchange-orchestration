package com.adh.exchange.bybit.service;

import java.util.Map;

public interface EncryptionRequestService {

    /**
     * TODO - add documentation
     *
     * @return
     */
    Map<String, String> createGETHeaders(Map<String, Object> requestParams);

    /**
     * TODO - add documentation
     *
     * @return
     */
    Map<String, String> createPOSTHeaders(Map<String, Object> requestParams);
}
