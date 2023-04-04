package com.adh.exchange.bybit.service;

import org.springframework.stereotype.Service;

@Service
public class OrderServiceFacade {

    private final OrderService service;

    public OrderServiceFacade(final OrderService service) {
        this.service = service;
    }


}
