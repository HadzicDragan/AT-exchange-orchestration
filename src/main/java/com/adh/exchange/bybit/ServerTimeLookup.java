package com.adh.exchange.bybit;

import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class ServerTimeLookup {

    // keep our own record of time
    private static final long REDUCED_TIME = ClientUtils.RECV_WINDOW - 500;

    private Instant lastStoredCurrentAppTime;
    private Instant lastStoredServerTime;
    private boolean isTimeEnabled;

    public boolean isTimeInitialized() {
        return this.lastStoredServerTime != null && this.lastStoredCurrentAppTime != null;
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

    public boolean isTimeDisabled() {
        return !this.isTimeEnabled();
    }

    public void setLastStoredServerTime(final Instant serverTime) {
        this.lastStoredServerTime = serverTime;
    }

    public long getLastStoredServerTime() {
        return lastStoredServerTime.toEpochMilli();
    }

    public void setLastStoredCurrentAppTime() {
        this.lastStoredCurrentAppTime = Instant.now();
    }

    public void currentAppTimeFromTime(Instant time) {
        this.lastStoredCurrentAppTime = time;
    }

    public boolean isLastStoredTimeValid() {
        long reducedTime = Instant.now().toEpochMilli() - this.lastStoredCurrentAppTime.toEpochMilli();
        return reducedTime < REDUCED_TIME;
    }

    public boolean isLastStoredTimeInvalid() {
        return !this.isLastStoredTimeValid();
    }
}
