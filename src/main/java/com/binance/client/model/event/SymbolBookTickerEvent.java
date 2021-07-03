package com.binance.client.model.event;

import com.binance.client.constant.BinanceApiConstants;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.math.BigDecimal;

public class SymbolBookTickerEvent {

    private Long orderBookUpdateId;

    private Long eventTime;

    private Long transactionTime;

    private String symbol;

    private BigDecimal bestBidPrice;

    private BigDecimal bestBidQty;

    private BigDecimal bestAskPrice;

    private BigDecimal bestAskQty;

    public Long getOrderBookUpdateId() {
        return orderBookUpdateId;
    }

    public void setOrderBookUpdateId(Long orderBookUpdateId) {
        this.orderBookUpdateId = orderBookUpdateId;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public BigDecimal getBestBidPrice() {
        return bestBidPrice;
    }

    public void setBestBidPrice(BigDecimal bestBidPrice) {
        this.bestBidPrice = bestBidPrice;
    }

    public BigDecimal getBestBidQty() {
        return bestBidQty;
    }

    public void setBestBidQty(BigDecimal bestBidQty) {
        this.bestBidQty = bestBidQty;
    }

    public BigDecimal getBestAskPrice() {
        return bestAskPrice;
    }

    public void setBestAskPrice(BigDecimal bestAskPrice) {
        this.bestAskPrice = bestAskPrice;
    }

    public BigDecimal getBestAskQty() {
        return bestAskQty;
    }

    public void setBestAskQty(BigDecimal bestAskQty) {
        this.bestAskQty = bestAskQty;
    }

    public Long getEventTime() {
        return eventTime;
    }

    public void setEventTime(Long eventTime) {
        this.eventTime = eventTime;
    }

    public Long getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(Long transactionTime) {
        this.transactionTime = transactionTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, BinanceApiConstants.TO_STRING_BUILDER_STYLE)
                .append("orderBookUpdateId", orderBookUpdateId).append("eventTime", eventTime)
                .append("transactionTime", transactionTime).append("symbol", symbol)
                .append("bestBidPrice", bestBidPrice).append("bestBidQty", bestBidQty)
                .append("bestAskPrice", bestAskPrice).append("bestAskQty", bestAskQty).toString();
    }
}
