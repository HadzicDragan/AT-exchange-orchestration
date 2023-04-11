package com.adh.exchange.bybit.api.client;

import com.adh.exchange.TypeConverter;
import com.adh.exchange.bybit.api.dto.ServerTimeResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ServerTimeClientTest {

    private ServerTimeClient client;

    @Autowired
    public ServerTimeClientTest(TypeConverter converter) {
        this.client = new ServerTimeClient(converter);
    }

    @Test
    void getServerTimeResponse() {
        ServerTimeResponse serverTime = client.getCurrentServerTime();

        Assertions.assertNotNull(serverTime);
    }
}
