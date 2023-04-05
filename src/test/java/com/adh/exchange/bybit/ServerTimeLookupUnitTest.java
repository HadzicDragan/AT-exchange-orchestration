package com.adh.exchange.bybit;

import com.adh.exchange.bybit.ServerTimeLookup;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ServerTimeLookupUnitTest {

    private ServerTimeLookup timeLookup;

    @BeforeEach
    void init() {
        this.timeLookup = new ServerTimeLookup();
    }

    @Test
    void testTimeIsNotEnabledByDefault() {
        assertTrue(this.timeLookup.isTimeDisabled());
    }

    @Test
    void testTimeIsEnabled() {
        this.timeLookup.enableTime();

        assertTrue(this.timeLookup.isTimeEnabled());
    }

    @Test
    void testTimeIsNotInitialized() {
        assertTrue(this.timeLookup.timeIsNotInitialized());
    }

    @Test
    void testTimeIsPartlyInitializedButNotReady() {
        this.timeLookup.setLastStoredCurrentAppTime();

        assertTrue(this.timeLookup.timeIsNotInitialized());
    }

    @Test
    void testTimeIsInitialized() {
        this.setupInitialTime();

        assertTrue(this.timeLookup.isTimeInitialized());
    }

    @Test
    void testLastStoredTimeIsInvalid() {
        final Instant reducedTime = Instant.now().minusMillis(5000);
        this.setupTime(reducedTime);

        assertTrue(this.timeLookup.isLastStoredTimeInvalid());
    }

    @Test
    void testLastStoredTimeIsValid() {
        final Instant reducedTime = Instant.now().minusMillis(4450);
        this.setupTime(reducedTime);

        assertTrue(this.timeLookup.isLastStoredTimeValid());
    }

    private void setupTime(final Instant time) {
        this.setupInitialTime();
        this.timeLookup.currentAppTimeFromTime(time);
    }

    private void setupInitialTime() {
        this.timeLookup.setLastStoredCurrentAppTime();
        this.timeLookup.setLastStoredServerTime(Instant.now());
    }
}
