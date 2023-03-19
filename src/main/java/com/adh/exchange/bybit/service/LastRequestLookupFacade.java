package com.adh.exchange.bybit.service;

import org.springframework.stereotype.Component;

@Component
public class LastRequestLookupFacade {

    private final ServerTimeService timeService;

    public LastRequestLookupFacade(final ServerTimeService serverTimeService) {
        this.timeService = serverTimeService;
    }

    /**
     * TODO - add implementation
     * @return
     */
    public String getServerTime() {
        return this.timeService.getLastRequestServerTime();
    }
}
