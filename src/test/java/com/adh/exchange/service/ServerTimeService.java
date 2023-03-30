package com.adh.exchange.service;

import com.adh.exchange.bybit.service.LastRequestLookupFacade;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ServerTimeService {

   @Autowired
   private LastRequestLookupFacade lookupFacade;


   @Test
   void doStuff() {
       String serverTime = lookupFacade.getServerTime();
       Assertions.assertNotNull(serverTime);
   }
}
