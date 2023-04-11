package com.adh.exchange.bybit;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;

@ExtendWith(SpringExtension.class)
public class LastRequestTimerUnitTest {

    @Mock
    private ServerTimeLookup timeLookup;

    @InjectMocks
    private LastRequestTimer requestTimer;

    @Test
    void canLookupWhenTimeIsEnabled() {
        when(this.timeLookup.isTimeEnabled()).thenReturn(true);

        final boolean isLastRequestInvalid = this.requestTimer.isInvalidLastLookupRequest();
        assertFalse(isLastRequestInvalid);
    }

    @Test
    void invalidLookupWhenTimeIsDisabled() {
        when(this.timeLookup.isTimeDisabled()).thenReturn(true);

        final boolean isLastRequestInvalid = this.requestTimer.isInvalidLastLookupRequest();
        assertTrue(isLastRequestInvalid);
    }

    @Test
    void canLookupWhenLastStoredTimeIsValid() {
        when(this.timeLookup.isTimeEnabled()).thenReturn(true);
        when(this.timeLookup.isLastStoredTimeInvalid()).thenReturn(false);

        final boolean isLastRequestInvalid = this.requestTimer.isInvalidLastLookupRequest();
        assertFalse(isLastRequestInvalid);
    }

    @Test
    void invalidLookupWhenLastStoredTimeIsInvalid() {
        when(this.timeLookup.isTimeEnabled()).thenReturn(true);
        when(this.timeLookup.isLastStoredTimeInvalid()).thenReturn(true);

        final boolean isLastRequestInvalid = this.requestTimer.isInvalidLastLookupRequest();
        assertTrue(isLastRequestInvalid);
    }


    // TODO do the correct spy tests
    @Test
    void firstTimeLookupRemoteTime() {
        final Instant timeNow = Instant.now();
//        final ServerTimeLookup spyTimeLookup = Mockito.spy(new ServerTimeLookup());
//
//        spyTimeLookup.enableTime();
//        spyTimeLookup.setLastStoredServerTime(null);
//        spyTimeLookup.currentAppTimeFromTime(null);
//        Mockito.verify(spyTimeLookup).isTimeEnabled();
        when(this.timeLookup.isTimeDisabled()).thenReturn(false);
        when(this.timeLookup.timeIsNotInitialized()).thenReturn(true);

        this.requestTimer.updateRequest(String.valueOf(timeNow.toEpochMilli()), String.valueOf(timeNow.getNano()));
    }

    // TODO do the correct spy tests
    @Test
    void lastStoredTimeIsInvalid() {
        final Instant timeNow = Instant.now();

        when(this.timeLookup.isTimeDisabled()).thenReturn(false);
        when(this.timeLookup.timeIsNotInitialized()).thenReturn(false);

        this.requestTimer.updateRequest(String.valueOf(timeNow.toEpochMilli()), String.valueOf(timeNow.getNano()));
    }
}
