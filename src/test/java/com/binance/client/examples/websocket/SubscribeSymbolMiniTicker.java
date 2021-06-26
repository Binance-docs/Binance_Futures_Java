package com.binance.client.examples.websocket;

import com.binance.client.SubscriptionClient;

public class SubscribeSymbolMiniTicker {

    public static void main(String[] args) {

        SubscriptionClient client = SubscriptionClient.create();
   
        client.subscribeSymbolMiniTickerEvent("btcusdt", ((event) -> {
            System.out.println(event);
            client.unsubscribeAll();
        }), null);

    }

}
