package com.binance.client.examples.websocket;

import com.binance.client.SubscriptionClient;

public class SubscribeSymbolBookTicker {

    public static void main(String[] args) {

        SubscriptionClient client = SubscriptionClient.create();
   
        client.subscribeSymbolBookTickerEvent("btcusdt", ((event) -> {
            System.out.println(event);
            client.unsubscribeAll();
        }), null);

    }

}
