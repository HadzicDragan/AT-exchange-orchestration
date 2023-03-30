package com.adh.exchange.bybit;

import com.adh.exchange.IllegalConfigurationException;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.concurrent.atomic.AtomicInteger;

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

    private final AtomicInteger requests = new AtomicInteger(0);
    private Instant lastUpdatedTime;
    private final TimePair pair;

    public LastRequestTimer(final TimePair timePair) {
        this.pair = timePair;
    }

    /**
     * TODO - Add documentation
     */
    public void updateRequest(final String seconds, final String nanoSeconds) {
        this.pair.enableTime();

        if (this.pair.timeIsNotInitialized()) {
            this.setupInitialTime(seconds, nanoSeconds);
            return;
        }

        final Instant exactCurrentTime = this.parseResponseInstant(seconds, nanoSeconds);
        this.setupServerTime(exactCurrentTime);

        final Instant currentAppTime = Instant.now();

        final Instant reducedTime = exactCurrentTime.minusMillis(ClientUtils.RECV_WINDOW);

        // reset the count
        // this is not fully atomic, but just to have a metric for the start
        // afterwards there will be more fine-grained checking of time.
        if (isLastUpdatedTimeInvalid(reducedTime.toEpochMilli())) {
            requests.set(0);
            return;
        }
        requests.getAndIncrement();
    }

    private void setupInitialTime(final String seconds, final String nanoSeconds) {
        final Instant exactCurrentTime = this.parseResponseInstant(seconds, nanoSeconds);
        this.pair.setLastStoredServerTime(exactCurrentTime);
        this.pair.setLastStoredCurrentAppTime();

        this.requests.getAndIncrement();
    }

    /**
     * Validates if last request was done in N seconds, if the current request time has elapsed,
     * higher than N, in this case false will be returned, and the client has to do a new server request to fetch
     * the latest server time.
     * @return
     */
    public boolean isInvalidValidRequest() {
        if (!this.pair.isTimeEnabled()) {
            return false;
        }

        // do the comparison check

        return ;
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
        return this.pair.;
    }

    private Instant parseResponseInstant(final String seconds, final String nanoSeconds) {
        long serverSeconds;
        long serverNanoSeconds;
        try {
            serverSeconds = Long.parseLong(seconds);
            serverNanoSeconds = Long.parseLong(nanoSeconds);
        } catch (NumberFormatException ex) {
            // Invalid date format sent, log the exception
            // change the exception type to custom!
            throw new IllegalConfigurationException();
        }
        return Instant.ofEpochSecond(serverSeconds, serverNanoSeconds);
    }

    private void setupServerTime(final Instant currentTime) {
        if (lastUpdatedTime == null) {
            this.lastUpdatedTime = currentTime;
        }
    }

    private boolean isLastUpdatedTimeValid(final long reducedTimeMilli) {
        long reducedFromLastUpdatedTime = reducedTimeMilli - this.lastUpdatedTime.toEpochMilli();
        return reducedFromLastUpdatedTime <= ClientUtils.RECV_WINDOW;
    }

    private boolean isLastUpdatedTimeInvalid(final long reducedTimeMilli) {
        return this.isLastUpdatedTimeValid(reducedTimeMilli);
    }
}
