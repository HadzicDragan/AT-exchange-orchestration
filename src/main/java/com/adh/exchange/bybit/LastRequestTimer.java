package com.adh.exchange.bybit;

import org.springframework.stereotype.Component;

/**
 * In order to make requests to Bybit we need to retrieve the Server time and add it inside the header.
 * This class is responsible to keep track of when was the last time we made a request to the server to retrieve
 * the server time. This should be explored and fine-grained, so we do not make to many requests.
 *
 * The first test should be to keep the request at 5seconds apart, if this can be extended even further that would be
 * even better.
 */
@Component
public class LastRequestTimer {

    /**
     * TODO - Add documentation
     */
    public void updateRequest(final String seconds) {

    }

    /**
     * Validates if last request was done in N seconds, if the current request time has elapsed,
     * higher than N, in this case false will be returned, and the client has to do a new server request to fetch
     * the latest server time.
     * @return
     */
    public boolean isInvalidValidRequest() {
       return true;
    }

    /**
     * Helper method to describe better how the inverse behavior is done.
     * @return
     */
    public boolean isValidRequest() {
        return !this.isInvalidValidRequest();
    }

    /**
     * Returns the last valid request from the server, this would mean even the updated time is valid,
     * only the valid time will be returned.
     * @return
     */
    public String lastRequestTime() {
        return "time";
    }
}
