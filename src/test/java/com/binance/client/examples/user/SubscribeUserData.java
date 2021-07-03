package com.binance.client.examples.user;

import com.binance.client.RequestOptions;
import com.binance.client.SubscriptionOptions;
import com.binance.client.SyncRequestClient;
import com.binance.client.SubscriptionClient;
import com.binance.client.constant.BinanceApiConstants;
import com.binance.client.examples.constants.PrivateConfig;

public class SubscribeUserData {

    public static void main(String[] args) {

        RequestOptions options = new RequestOptions();
        options.setUrl(BinanceApiConstants.API_TESTNET_BASE_URL);
        SyncRequestClient syncRequestClient = SyncRequestClient.create(PrivateConfig.API_KEY, PrivateConfig.SECRET_KEY,
                options);

        // Start user data stream
        String listenKey = syncRequestClient.startUserDataStream();
        System.out.println("listenKey: " + listenKey);

        // Keep user data stream
        syncRequestClient.keepUserDataStream(listenKey);

        // Close user data stream
        syncRequestClient.closeUserDataStream(listenKey);

        SubscriptionOptions subscriptionOptions = new SubscriptionOptions();
        subscriptionOptions.setUri(BinanceApiConstants.WS_API_TESTNET_BASE_URL);
        SubscriptionClient client = SubscriptionClient.create(subscriptionOptions);


   
        client.subscribeUserDataEvent(listenKey, System.out::println, null);

    }

}