package com.adh.exchange.bybit;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

@Component
public class EncryptionExample {
    final static String API_KEY = "XXXXXXXXX";
    final static String API_SECRET = "XXXXXXXXX";
    final static String TIMESTAMP = Long.toString(ZonedDateTime.now().toInstant().toEpochMilli());
    final static String RECV_WINDOW = "5000";

//    public static void main(String[] args) throws NoSuchAlgorithmException, InvalidKeyException {
//        EncryptionExample encryptionTest = new Encryption();
//
//        encryptionTest.placeOrder();
//
////        encryptionTest.getOpenOrder();
//    }
    private final ObjectMapper mapper;

    public EncryptionExample(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    /**
     * POST: place a spot v3 order
     */
    public void placeOrder() throws NoSuchAlgorithmException, InvalidKeyException, JsonProcessingException {
        Map<String, Object> map = new HashMap<>();
        map.put("symbol", "BTCUSDT");
        map.put("side", "Buy");
        map.put("orderType", "Limit");
        map.put("orderQty", "0.01");
        map.put("orderPrice", "19000");
        map.put("timeInForce", "GTC");

        String signature = genPostSign(map);
        mapper.writeValueAsString(map);

        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder(URI.create("https://api-testnet.bybit.com/spot/v3/private/order"))
                .header("X-BAPI-API-KEY", API_KEY)
                .header("X-BAPI-SIGN", signature)
                .header("X-BAPI-SIGN-TYPE", "2")
                .header("X-BAPI-TIMESTAMP", TIMESTAMP)
                .header("X-BAPI-RECV-WINDOW", RECV_WINDOW)
                .header("Content-Type", "application/json")
//                .post(RequestBody.create(mediaType, jsonMap))
                .POST(null)
                .build();
        try {

            HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());
            assert response.body() != null;
            System.out.println(response.body());
        }catch (IOException | InterruptedException e){
            e.printStackTrace();
        }
    }

    /**
     * GET: query open order
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     */
    public void getOpenOrder() throws NoSuchAlgorithmException, InvalidKeyException {
        Map<String, Object> map = new HashMap<>();

        map.put("symbol", "BTCUSDT");

        String signature = genGetSign(map);
        StringBuilder sb = genQueryStr(map);

        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder(URI.create("https://api-testnet.bybit.com/spot/v3/private/open-orders?" + sb))
                .header("X-BAPI-API-KEY", API_KEY)
                .header("X-BAPI-SIGN", signature)
                .header("X-BAPI-SIGN-TYPE", "2")
                .header("X-BAPI-TIMESTAMP", TIMESTAMP)
                .header("X-BAPI-RECV-WINDOW", RECV_WINDOW)
                .GET()
                .build();
        try {
            HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());
            assert response.body() != null;
            System.out.println(response.body());
        }catch (IOException | InterruptedException e){
            e.printStackTrace();
        }
    }

    /**
     * The way to generate the sign for POST requests
     * @param params: Map input parameters
     * @return signature used to be a parameter in the header
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     */
    private static String genPostSign(Map<String, Object> params) throws NoSuchAlgorithmException, InvalidKeyException, JsonProcessingException {
        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new SecretKeySpec(API_SECRET.getBytes(), "HmacSHA256");
        sha256_HMAC.init(secret_key);

        ObjectMapper mapper = new ObjectMapper();
        String paramJson = mapper.writeValueAsString(params);
        String sb = TIMESTAMP + API_KEY + RECV_WINDOW + paramJson;
        return bytesToHex(sha256_HMAC.doFinal(sb.getBytes()));
    }

    /**
     * The way to generate the sign for GET requests
     * @param params: Map input parameters
     * @return signature used to be a parameter in the header
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     */
    private static String genGetSign(Map<String, Object> params) throws NoSuchAlgorithmException, InvalidKeyException {
        StringBuilder sb = genQueryStr(params);
        String queryStr = TIMESTAMP + API_KEY + RECV_WINDOW + sb;

        Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
        SecretKeySpec secret_key = new SecretKeySpec(API_SECRET.getBytes(), "HmacSHA256");
        sha256_HMAC.init(secret_key);
        return bytesToHex(sha256_HMAC.doFinal(queryStr.getBytes()));
    }

    /**
     * To convert bytes to hex
     * @param hash
     * @return hex string
     */
    private static String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

    /**
     * To generate query string for GET requests
     * @param map
     * @return
     */
    private static StringBuilder genQueryStr(Map<String, Object> map) {
        Set<String> keySet = map.keySet();
        Iterator<String> iter = keySet.iterator();
        StringBuilder sb = new StringBuilder();
        while (iter.hasNext()) {
            String key = iter.next();
            sb.append(key)
                    .append("=")
                    .append(map.get(key))
                    .append("&");
        }
        sb.deleteCharAt(sb.length() - 1);
        return sb;
    }
}
