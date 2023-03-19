package com.adh.exchange.bybit.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ServerTimeResponse extends AbstractResponse{

    @JsonProperty(value = "result")
    private ServerTimeRecord result;

    public ServerTimeRecord getResult() {
        return result;
    }

    public void setResult(ServerTimeRecord result) {
        this.result = result;
    }
}
