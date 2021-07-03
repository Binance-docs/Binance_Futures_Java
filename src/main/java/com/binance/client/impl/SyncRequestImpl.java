package com.binance.client.impl;

import com.alibaba.fastjson.JSONObject;
import com.binance.client.SyncRequestClient;
import com.binance.client.model.ResponseResult;
import com.binance.client.model.market.*;
import com.binance.client.model.enums.*;
import com.binance.client.model.trade.*;

import java.util.List;

public class SyncRequestImpl implements SyncRequestClient {

    private final RestApiRequestImpl requestImpl;

    SyncRequestImpl(RestApiRequestImpl requestImpl) {
        this.requestImpl = requestImpl;
    }

    
    @Override
    public ExchangeInformation getExchangeInformation() {
        return RestApiInvoker.callSync(requestImpl.getExchangeInformation());
    }
    
    @Override
    public OrderBook getOrderBook(String symbol, Integer limit) {
        return RestApiInvoker.callSync(requestImpl.getOrderBook(symbol, limit));
    }
    
    @Override
    public List<Trade> getRecentTrades(String symbol, Integer limit) {
        return RestApiInvoker.callSync(requestImpl.getRecentTrades(symbol, limit));
    }
    
    @Override
    public List<Trade> getOldTrades(String symbol, Integer limit, Long fromId) {
        return RestApiInvoker.callSync(requestImpl.getOldTrades(symbol, limit, fromId));
    }
    
    @Override
    public List<AggregateTrade> getAggregateTrades(String symbol, Long fromId, Long startTime, 
            Long endTime, Integer limit) {
        return RestApiInvoker.callSync(requestImpl.getAggregateTrades(symbol, fromId, startTime, endTime, limit));
    }
    
    @Override
    public List<Candlestick> getCandlestick(String symbol, CandlestickInterval interval, Long startTime, 
            Long endTime, Integer limit) {
        return RestApiInvoker.callSync(requestImpl.getCandlestick(symbol, interval, startTime, endTime, limit));
    }

    @Override
    public List<Candlestick> getContinuousCandlesticks(String pair, ContractType contractType, CandlestickInterval interval, Long startTime,
                                                Long endTime, Integer limit){
        return RestApiInvoker.callSync(requestImpl.getContinuousCandlesticks(pair, contractType, interval, startTime, endTime, limit));
    }

    public List<Candlestick> getIndexPriceCandlesticks(String pair, CandlestickInterval interval, Long startTime,
                                                       Long endTime, Integer limit){
        return RestApiInvoker.callSync(requestImpl.getIndexPriceCandlesticks(pair, interval, startTime, endTime, limit));
    }

    public List<Candlestick> getMarkPriceCandlesticks(String pair, CandlestickInterval interval, Long startTime,
                                                       Long endTime, Integer limit){
        return RestApiInvoker.callSync(requestImpl.getMarkPriceCandlesticks(pair, interval, startTime, endTime, limit));
    }
    
    @Override
    public List<MarkPrice> getMarkPrice(String symbol) {
        return RestApiInvoker.callSync(requestImpl.getMarkPrice(symbol));
    }
    
    @Override
    public List<FundingRate> getFundingRate(String symbol, Long startTime, Long endTime, Integer limit) {
        return RestApiInvoker.callSync(requestImpl.getFundingRate(symbol, startTime, endTime, limit));
    }
    
    @Override
    public List<PriceChangeTicker> get24hrTickerPriceChange(String symbol) {
        return RestApiInvoker.callSync(requestImpl.get24hrTickerPriceChange(symbol));
    }
    
    @Override
    public List<SymbolPrice> getSymbolPriceTicker(String symbol) {
        return RestApiInvoker.callSync(requestImpl.getSymbolPriceTicker(symbol));
    }
    
    @Override
    public List<SymbolOrderBook> getSymbolOrderBookTicker(String symbol) {
        return RestApiInvoker.callSync(requestImpl.getSymbolOrderBookTicker(symbol));
    }
    
    @Override
    public List<LiquidationOrder> getLiquidationOrders(String symbol, AutoCloseType type, Long startTime, Long endTime, Integer limit) {
        return RestApiInvoker.callSync(requestImpl.getLiquidationOrders(symbol, type, startTime, endTime, limit));
    }

    @Override
    public List<Object> postBatchOrders(String batchOrders) {
        return RestApiInvoker.callSync(requestImpl.postBatchOrders(batchOrders));
    }
    
    @Override
    public Order postOrder(String symbol, OrderSide side, PositionSide positionSide, OrderType orderType,
            TimeInForce timeInForce, String quantity, String price, String reduceOnly,
            String newClientOrderId, String stopPrice, String closePosition, String activationPrice,
                           String callbackRate, WorkingType workingType, String priceProtect, NewOrderRespType newOrderRespType) {
        return RestApiInvoker.callSync(requestImpl.postOrder(symbol, side, positionSide, orderType,
                timeInForce, quantity, reduceOnly, price, newClientOrderId, stopPrice,
                 closePosition, activationPrice, callbackRate, workingType, priceProtect, newOrderRespType));
    }
    
    @Override
    public Order cancelOrder(String symbol, Long orderId, String origClientOrderId) {
        return RestApiInvoker.callSync(requestImpl.cancelOrder(symbol, orderId, origClientOrderId));
    }

    @Override
    public ResponseResult cancelAllOpenOrder(String symbol) {
      return RestApiInvoker.callSync(requestImpl.cancelAllOpenOrder(symbol));
    }

    @Override
    public List<Object> batchCancelOrders(String symbol, String orderIdList, String origClientOrderIdList) {
        return RestApiInvoker.callSync(requestImpl.batchCancelOrders(symbol, orderIdList, origClientOrderIdList));
    }

    @Override
    public ResponseResult changePositionSide(String dual) {
        return RestApiInvoker.callSync(requestImpl.changePositionSide(dual));
    }

    @Override
    public ResponseResult changeMarginType(String symbolName, MarginType marginType) {
        return RestApiInvoker.callSync(requestImpl.changeMarginType(symbolName, marginType));
    }

    @Override
    public JSONObject addIsolatedPositionMargin(String symbolName, int type, String amount, PositionSide positionSide) {
        return RestApiInvoker.callSync(requestImpl.addPositionMargin(symbolName, type, amount, positionSide));
    }

    @Override
    public List<WalletDeltaLog> getPositionMarginHistory(String symbolName, int type, long startTime, long endTime, int limit) {
        return RestApiInvoker.callSync(requestImpl.getPositionMarginHistory(symbolName, type, startTime, endTime, limit));
    }


    @Override
    public JSONObject getPositionSide() {
        return RestApiInvoker.callSync(requestImpl.getPositionSide());
    }

    @Override
    public JSONObject autoCancelAllOrders(String symbol, Long countdownTime) {
        return RestApiInvoker.callSync(requestImpl.autoCancelAllOrders(symbol, countdownTime));
    }

    @Override
    public Order getOrder(String symbol, Long orderId, String origClientOrderId) {
        return RestApiInvoker.callSync(requestImpl.getOrder(symbol, orderId, origClientOrderId));
    }

    public Order getOpenOrder(String symbol, Long orderId, String origClientOrderId){
        return RestApiInvoker.callSync(requestImpl.getOpenOrder(symbol, orderId, origClientOrderId));
    }

    @Override
    public List<Order> getOpenOrders(String symbol) {
        return RestApiInvoker.callSync(requestImpl.getOpenOrders(symbol));
    }
    
    @Override
    public List<Order> getAllOrders(String symbol, Long orderId, Long startTime, Long endTime, Integer limit) {
        return RestApiInvoker.callSync(requestImpl.getAllOrders(symbol, orderId, startTime, endTime, limit));
    }
    
    @Override
    public List<AccountBalance> getBalance() {
        return RestApiInvoker.callSync(requestImpl.getBalance());
    }
    
    @Override
    public AccountInformation getAccountInformation() {
        return RestApiInvoker.callSync(requestImpl.getAccountInformation());
    }
    
    @Override
    public Leverage changeInitialLeverage(String symbol, Integer leverage) {
        return RestApiInvoker.callSync(requestImpl.changeInitialLeverage(symbol, leverage));
    }
    
    @Override
    public List<PositionRisk> getPositionRisk(String symbol) {
        return RestApiInvoker.callSync(requestImpl.getPositionRisk(symbol));
    }
    
    @Override
    public List<MyTrade> getAccountTrades(String symbol, Long startTime, Long endTime, Long fromId, Integer limit) {
        return RestApiInvoker.callSync(requestImpl.getAccountTrades(symbol, startTime, endTime, fromId, limit));
    }
    
    @Override
    public List<Income> getIncomeHistory(String symbol, IncomeType incomeType, Long startTime, Long endTime, Integer limit) {
        return RestApiInvoker.callSync(requestImpl.getIncomeHistory(symbol, incomeType, startTime, endTime, limit));
    }
    
    @Override
    public String startUserDataStream() {
        return RestApiInvoker.callSync(requestImpl.startUserDataStream());
    }
    
    @Override
    public String keepUserDataStream(String listenKey) {
        return RestApiInvoker.callSync(requestImpl.keepUserDataStream(listenKey));
    }
    
    @Override
    public String closeUserDataStream(String listenKey) {
        return RestApiInvoker.callSync(requestImpl.closeUserDataStream(listenKey));
    }

    @Override
    public OpenInterest getOpenInterest(String symbol) {
        return RestApiInvoker.callSync(requestImpl.getOpenInterest(symbol));
    }

    @Override
    public List<OpenInterestStat> getOpenInterestStat(String symbol, PeriodType period, Long startTime, Long endTime, Long limit) {
        return RestApiInvoker.callSync(requestImpl.getOpenInterestStat(symbol, period, startTime, endTime, limit));
    }

    @Override
    public List<CommonLongShortRatio> getTopTraderAccountRatio(String symbol, PeriodType period, Long startTime, Long endTime, Long limit) {
        return RestApiInvoker.callSync(requestImpl.getTopTraderAccountRatio(symbol, period, startTime, endTime, limit));
    }

    @Override
    public List<CommonLongShortRatio> getTopTraderPositionRatio(String symbol, PeriodType period, Long startTime, Long endTime, Long limit) {
        return RestApiInvoker.callSync(requestImpl.getTopTraderPositionRatio(symbol, period, startTime, endTime, limit));
    }

    @Override
    public List<CommonLongShortRatio> getGlobalAccountRatio(String symbol, PeriodType period, Long startTime, Long endTime, Long limit) {
        return RestApiInvoker.callSync(requestImpl.getGlobalAccountRatio(symbol, period, startTime, endTime, limit));
    }

    @Override
    public List<TakerLongShortStat> getTakerLongShortRatio(String symbol, PeriodType period, Long startTime, Long endTime, Long limit) {
        return RestApiInvoker.callSync(requestImpl.getTakerLongShortRatio(symbol, period, startTime, endTime, limit));
    }
}
