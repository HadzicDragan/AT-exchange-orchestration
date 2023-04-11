package com.adh.exchange.bybit.service;

import com.adh.exchange.EncryptionException;
import com.adh.exchange.TypeConverter;
import com.adh.exchange.bybit.BybitSecret;
import com.adh.exchange.bybit.ClientUtils;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

@Component
public class EncryptionRequestServiceImpl implements EncryptionRequestService {

    private static final String API_KEY = "X-BAPI-API-KEY";
    private static final String API_SIGNATURE = "X-BAPI-SIGN";
    private static final String API_TIMESTAMP = "X-BAPI-TIMESTAMP";
    private static final String API_RECV_WINDOW = "X-BAPI-RECV-WINDOW";
    private static final String ENCRYPTION_ALGO = "HmacSHA256";

    private final BybitSecret secret;
    private final ServerTimeService timeService;
    private final TypeConverter converter;

    public EncryptionRequestServiceImpl(final ServerTimeService timeService, final BybitSecret secret, TypeConverter converter) {
        this.timeService = timeService;
        this.secret = secret;
        this.converter = converter;
    }

    @Override
    public Map<String, String> createGETHeaders(Map<String, Object> requestParams) {
        final String serverTime = this.timeService.getLastRequestServerTime();
        final String signature = this.generateGETSignature(requestParams, serverTime);

        return this.createHeaders(signature, serverTime);
    }

    @Override
    public Map<String, String> createPOSTHeaders(final Map<String, Object> requestParams) {
        final String serverTime = this.timeService.getLastRequestServerTime();
        final String signature = this.generatePOSTSignature(requestParams, serverTime);

        return this.createHeaders(signature, serverTime);
    }

    private Map<String, String> createHeaders(final String signature, final String serverTime) {
        Map<String, String> headers = new HashMap<>(4);
        headers.put(API_KEY, this.secret.apiKey());
        headers.put(API_SIGNATURE, signature);
        headers.put(API_TIMESTAMP, serverTime);
        headers.put(API_RECV_WINDOW, String.valueOf(ClientUtils.RECV_WINDOW));
        return headers;
    }

    private String generatePOSTSignature(final Map<String, Object> requestParams, final String serverTime) {
        try {
            return this.genPostSignature(requestParams, serverTime);
        } catch (NoSuchAlgorithmException | InvalidKeyException ex) {
            // ADD logging
            // TODO - implement correct exception handling + logging
            throw new EncryptionException();
        }
    }

    private String generateGETSignature(final Map<String, Object> requestParams, final String serverTime) {
        try {
            return this.genGetSignature(requestParams, serverTime);
        } catch (NoSuchAlgorithmException | InvalidKeyException ex) {
            // ADD logging
            // TODO - implement correct exception handling + logging
            throw new EncryptionException();
        }
    }

    private String genPostSignature(final Map<String, Object> params, final String serverTime) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac sha256_HMAC = Mac.getInstance(ENCRYPTION_ALGO);
        final SecretKeySpec secretKey = new SecretKeySpec(this.secret.apiKey().getBytes(), ENCRYPTION_ALGO);
        sha256_HMAC.init(secretKey);

        final String paramJson = converter.writeObjectToString(params);
        final String sb = serverTime + API_KEY + ClientUtils.RECV_WINDOW + paramJson;
        return bytesToHex(sha256_HMAC.doFinal(sb.getBytes()));
    }

    private String genGetSignature(final Map<String, Object> params, final String serverTime) throws NoSuchAlgorithmException, InvalidKeyException {
        final String queryStr = serverTime + API_KEY + ClientUtils.RECV_WINDOW + ClientUtils.genQueryStr(params);

        final Mac sha256_HMAC = Mac.getInstance(ENCRYPTION_ALGO);
        final SecretKeySpec secretKey = new SecretKeySpec(this.secret.apiSecret().getBytes(), ENCRYPTION_ALGO);
        sha256_HMAC.init(secretKey);
        return bytesToHex(sha256_HMAC.doFinal(queryStr.getBytes()));
    }

    private String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }
}

