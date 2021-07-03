package com.binance.client.examples.trade;

import com.binance.client.RequestOptions;
import com.binance.client.SyncRequestClient;
import com.binance.client.examples.constants.PrivateConfig;
import com.binance.client.model.enums.MarginType;

/**
 * @author : wangwanlu
 * @since : 2020/4/23, Thu
 **/
public class ChangeMarginType {

    public static void main(String[] args) {
        RequestOptions options = new RequestOptions();
        SyncRequestClient syncRequestClient = SyncRequestClient.create(PrivateConfig.API_KEY, PrivateConfig.SECRET_KEY,
                options);

        // margin type: ISOLATED, CROSSED
        System.out.println(syncRequestClient.changeMarginType("BTCUSDT", MarginType.ISOLATED));
    }
}
