package com.adh.exchange.bybit;

import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class TimePair implements Comparable<Instant> {

    // keep our own record of time
    private static final long REDUCED_TIME = ClientUtils.RECV_WINDOW - 500;

    private Instant currentTime;
    private Instant lastStoredCurrentAppTime;
    private Instant lastStoredServerTime;
    private boolean isTimeEnabled;


    private void updateCurrentTime() {

        final Instant reducedTime = this.currentTime.minusMillis(REDUCED_TIME);
    }

    public boolean isTimeInitialized() {
        Boolean
        if (this.lastStoredServerTime == null) {
            return false;
        }
        return true;
    }

    public boolean timeIsNotInitialized() {
        return !this.isTimeInitialized();
    }

    public void enableTime() {
        this.isTimeEnabled = true;
    }

    public boolean isTimeEnabled() {
        return this.isTimeEnabled;
    }

    public void setLastStoredServerTime(final Instant serverTime) {
        this.lastStoredServerTime = serverTime;
    }

    public void setLastStoredCurrentAppTime() {
        this.lastStoredCurrentAppTime = Instant.now();
    }

    @Override
    public int compareTo(Instant o) {
        return 0;
    }
}
