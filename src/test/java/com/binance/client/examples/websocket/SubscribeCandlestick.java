package com.binance.client.examples.websocket;

import com.binance.client.SubscriptionClient;
import com.binance.client.model.enums.CandlestickInterval;

public class SubscribeCandlestick {

    public static void main(String[] args) {

        SubscriptionClient client = SubscriptionClient.create();
   
        client.subscribeCandlestickEvent("btcusdt", CandlestickInterval.ONE_MINUTE, ((event) -> {
            System.out.println(event);
            client.unsubscribeAll();
        }), null);

    }

}
