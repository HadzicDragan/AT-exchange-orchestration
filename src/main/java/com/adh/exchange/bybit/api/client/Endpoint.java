package com.adh.exchange.bybit.api.client;

public class Endpoint {

    private Endpoint() {
    }
    public static final String BASE_URL = "https://api.bybit.com/";
    public static final String V3 = "/v3";
    public static final String V5 = "/v5";
    public static final String SERVER_TIME_URI = "/public/time";

    /**
     * Get the Bybit server timestamp
     */
    public static final String SERVER_TIME = BASE_URL + V3 + SERVER_TIME_URI;

    /**
     * Query unfilled or partially filled orders in real-time.
     */
    public static final String ALL_OPEN_ORDERS = V5 + "/order/realtime";

    /**
     * Create the order for spot, spot margin, linear perpetual, inverse futures
     */
    public static final String CREATE_SINGLE_ORDER = V5 + "/order/create";

    /**
     * Modify unfilled or partially filled orders
     */
    public static final String UPDATE_SINGLE_ORDER = V5 + "/order/amend";

    /**
     * Cancel unfilled or partially filled orders
     */
    public static final String CANCEL_SINGLE_ORDER = V5 + "/order/cancel";

    /**
     * Place more than one order in a single request
     */
    public static final String BATCH_CREATE_ORDERS = V5 + "/order/create-batch";

    /**
     * Cancel more than one open order in a single request
     */
    public static final String BATCH_CANCEL_ORDERS = V5 + "/order/cancel-batch";
}
