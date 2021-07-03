package com.binance.client.model.trade;

import com.binance.client.constant.BinanceApiConstants;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.math.BigDecimal;

public class PositionRisk {

    private BigDecimal entryPrice;

    private String marginType;

    private Boolean isAutoAddMargin;

    private String isolatedMargin;

    private BigDecimal leverage;

    private BigDecimal liquidationPrice;

    private BigDecimal markPrice;

    private Double maxNotionalValue;

    private BigDecimal positionAmt;

    private String symbol;

    private BigDecimal unrealizedProfit;

    private String positionSide;


    public Boolean getAutoAddMargin() {
        return isAutoAddMargin;
    }

    public void setAutoAddMargin(Boolean autoAddMargin) {
        isAutoAddMargin = autoAddMargin;
    }

    public BigDecimal getEntryPrice() {
        return entryPrice;
    }

    public void setEntryPrice(BigDecimal entryPrice) {
        this.entryPrice = entryPrice;
    }

    public BigDecimal getLeverage() {
        return leverage;
    }

    public void setLeverage(BigDecimal leverage) {
        this.leverage = leverage;
    }

    public Double getMaxNotionalValue() {
        return maxNotionalValue;
    }

    public void setMaxNotionalValue(Double maxNotionalValue) {
        this.maxNotionalValue = maxNotionalValue;
    }

    public BigDecimal getLiquidationPrice() {
        return liquidationPrice;
    }

    public void setLiquidationPrice(BigDecimal liquidationPrice) {
        this.liquidationPrice = liquidationPrice;
    }

    public BigDecimal getMarkPrice() {
        return markPrice;
    }

    public void setMarkPrice(BigDecimal markPrice) {
        this.markPrice = markPrice;
    }

    public BigDecimal getPositionAmt() {
        return positionAmt;
    }

    public void setPositionAmt(BigDecimal positionAmt) {
        this.positionAmt = positionAmt;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public BigDecimal getUnrealizedProfit() {
        return unrealizedProfit;
    }

    public void setUnrealizedProfit(BigDecimal unrealizedProfit) {
        this.unrealizedProfit = unrealizedProfit;
    }

    public String getIsolatedMargin() {
        return isolatedMargin;
    }

    public void setIsolatedMargin(String isolatedMargin) {
        this.isolatedMargin = isolatedMargin;
    }

    public String getPositionSide() {
        return positionSide;
    }

    public void setPositionSide(String positionSide) {
        this.positionSide = positionSide;
    }

    public String getMarginType() {
        return marginType;
    }

    public void setMarginType(String marginType) {
        this.marginType = marginType;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, BinanceApiConstants.TO_STRING_BUILDER_STYLE).append("entryPrice", entryPrice)
                .append("marginType", marginType).append("isAutoAddMargin", isAutoAddMargin)
                .append("isolatedMargin", isolatedMargin).append("leverage", leverage)
                .append("liquidationPrice", liquidationPrice).append("markPrice", markPrice)
                .append("maxNotionalValue", maxNotionalValue).append("positionAmt", positionAmt)
                .append("symbol", symbol).append("unrealizedProfit", unrealizedProfit)
                .append("positionSide", positionSide).toString();
    }
}
