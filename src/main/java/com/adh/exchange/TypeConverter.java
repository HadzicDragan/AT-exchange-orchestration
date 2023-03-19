package com.adh.exchange;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class TypeConverter {

    private final ObjectMapper mapper;

    public TypeConverter() {
        this.mapper = new ObjectMapper();
    }

    public String writeObjectToString(Object object) {
        try {
            return this.mapper.writeValueAsString(object);
        } catch (IOException ex) {
            // TODO - add correct logging
            return null;
        }
    }

    public <T> T stringToType(final String jsonValue, final Class<T> type) {
        try {
            return this.mapper.readValue(jsonValue, type);
        } catch (IOException ex) {
            // TODO - add correct logging
            return null;
        }
    }
}
