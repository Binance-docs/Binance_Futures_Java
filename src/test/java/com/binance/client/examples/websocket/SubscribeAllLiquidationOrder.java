package com.binance.client.examples.websocket;

import com.binance.client.SubscriptionClient;

public class SubscribeAllLiquidationOrder {

    public static void main(String[] args) {

        SubscriptionClient client = SubscriptionClient.create();
   
        client.subscribeAllLiquidationOrderEvent(System.out::println, null);

    }

}
