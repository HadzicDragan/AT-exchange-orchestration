package com.adh.exchange.bybit.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ServerTimeRecord(
        @JsonProperty(value = "timeNano") String timeNano,
        @JsonProperty(value = "timeSecond") String timeSecond) {
}
