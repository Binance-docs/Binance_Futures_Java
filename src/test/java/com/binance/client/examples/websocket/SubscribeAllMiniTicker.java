package com.binance.client.examples.websocket;

import com.binance.client.SubscriptionClient;

public class SubscribeAllMiniTicker {

    public static void main(String[] args) {

        SubscriptionClient client = SubscriptionClient.create();
   
        client.subscribeAllMiniTickerEvent(System.out::println, null);

    }

}
