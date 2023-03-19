package com.adh.exchange.bybit.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AbstractResponse {
    @JsonProperty(value = "time")
    private float time;
    @JsonProperty(value = "retMsg")
    private String msg;

    @JsonProperty(value = "retExtInfo")
    private Object extraInfo;

    @JsonProperty(value = "retCode")
    private int code;

    public float getTime() {
        return time;
    }

    public void setTime(float time) {
        this.time = time;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getExtraInfo() {
        return extraInfo;
    }

    public void setExtraInfo(Object extraInfo) {
        this.extraInfo = extraInfo;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
