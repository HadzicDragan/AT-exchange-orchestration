package com.adh.exchange;

import com.adh.exchange.bybit.BybitSecret;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ApiSecretUnitTest {

    private static final String API_SECRET = "API_SECRET";

    @InjectMocks
    private BybitSecret apiSecret;

    @Test
    void apiKeyNotPresent() {
        assertThrows(IllegalConfigurationException.class, () -> apiSecret.apiKey());
    }

    @Test
    void mockApiKeyIsPresent() {
        BybitSecret mocked = Mockito.mock(BybitSecret.class);
        Mockito.when(mocked.apiKey()).thenReturn(API_SECRET);

        assertEquals(API_SECRET, mocked.apiKey());
    }
}
