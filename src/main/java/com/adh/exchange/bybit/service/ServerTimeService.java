package com.adh.exchange.bybit.service;

import com.adh.exchange.bybit.LastRequestTimer;
import com.adh.exchange.bybit.api.client.ServerTimeClient;
import com.adh.exchange.bybit.api.dto.ServerTimeRecord;
import com.adh.exchange.bybit.api.dto.ServerTimeResponse;
import org.springframework.stereotype.Service;

@Service
public class ServerTimeService {

    private final ServerTimeClient timeClient;
    private final LastRequestTimer lastRequestTimer;

    public ServerTimeService(final ServerTimeClient timeClient, final LastRequestTimer requestTimer) {
        this.timeClient = timeClient;
        this.lastRequestTimer = requestTimer;
    }

    public String getLastRequestServerTime() {
        if (this.lastRequestTimer.isInvalidValidRequest()) {
            //
            final ServerTimeResponse serverTimeResponse = this.timeClient.getCurrentServerTime();

            // when we receive the new server time we should update the requestTimer to keep in sync the lookup
            // we should pass the server time from the server, but internally keep track of your own time
            final ServerTimeRecord serverTime = serverTimeResponse.getResult();
            this.lastRequestTimer.updateRequest(serverTime.timeSecond(), serverTime.timeNano());
        }
        return this.lastRequestTimer.lastRequestTime();
    }
}
