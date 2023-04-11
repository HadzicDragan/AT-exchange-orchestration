package com.adh.exchange.bybit.service;

import com.adh.exchange.RemoteClientException;
import com.adh.exchange.bybit.LastRequestTimer;
import com.adh.exchange.bybit.api.client.ServerTimeClient;
import com.adh.exchange.bybit.api.dto.ServerTimeRecord;
import com.adh.exchange.bybit.api.dto.ServerTimeResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doNothing;

@ExtendWith(SpringExtension.class)
public class ServerTimeServiceUnitTest {

   private static final String VALID_SERVER_TIME = "12345";

   @Mock
   private ServerTimeClient timeClient;

   @Mock
   private LastRequestTimer lastRequestTimer;

   @InjectMocks
   private ServerTimeServiceImpl timeService;

   @Test
   void canGetServerTimeWithoutRetrievingRemoteData() {
      when(this.lastRequestTimer.isInvalidLastLookupRequest()).thenReturn(false);
      when(this.lastRequestTimer.lastRequestTime()).thenReturn(VALID_SERVER_TIME);

      final String serverTime = this.timeService.getLastRequestServerTime();
      assertEquals(VALID_SERVER_TIME, serverTime);
   }

   @Test
   void canGetServerTimeWhenRetrievedFromRemoteData() {
      when(this.lastRequestTimer.isInvalidLastLookupRequest()).thenReturn(true);
      when(this.timeClient.getCurrentServerTime()).thenReturn(toServerTimeResponse());
      doNothing().when(this.lastRequestTimer).updateRequest(any(), any());
      when(this.lastRequestTimer.lastRequestTime()).thenReturn(VALID_SERVER_TIME);

      final String serverTime = this.timeService.getLastRequestServerTime();
      assertEquals(VALID_SERVER_TIME, serverTime);
   }

   @Test
   void canNotGetServerTimeRemoteDataNotRetrieved() {
      when(this.lastRequestTimer.isInvalidLastLookupRequest()).thenReturn(true);
      when(this.timeClient.getCurrentServerTime()).thenReturn(null);

      assertThrows(RemoteClientException.class, () -> this.timeService.getLastRequestServerTime());
   }

   private ServerTimeResponse toServerTimeResponse() {
      final ServerTimeResponse timeResponse = new ServerTimeResponse();
      timeResponse.setResult(new ServerTimeRecord("01234", "1123"));
      return timeResponse;
   }
}
