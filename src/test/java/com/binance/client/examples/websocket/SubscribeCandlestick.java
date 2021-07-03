package com.binance.client.examples.websocket;

import com.binance.client.SubscriptionClient;
import com.binance.client.SubscriptionOptions;
import com.binance.client.constant.BinanceApiConstants;
import com.binance.client.model.enums.CandlestickInterval;

public class SubscribeCandlestick {

    public static void main(String[] args) {

        SubscriptionOptions subscriptionOptions = new SubscriptionOptions();
        subscriptionOptions.setUri(BinanceApiConstants.WS_API_TESTNET_BASE_URL);
        SubscriptionClient client = SubscriptionClient.create(subscriptionOptions);
   
        client.subscribeCandlestickEvent("btcusdt,algousdt,trbusdt", CandlestickInterval.ONE_MINUTE, ((event) -> {
            System.out.println(event);
            //client.unsubscribeAll();
        }), null);

    }

}
