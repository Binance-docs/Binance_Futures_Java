package com.binance.client.model.trade;

import com.binance.client.constant.BinanceApiConstants;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.math.BigDecimal;

public class Asset {

    private String asset;

    private BigDecimal walletBalance;

    private BigDecimal unrealizedProfit;

    private BigDecimal marginBalance;

    private BigDecimal maintMargin;

    private BigDecimal initialMargin;

    private BigDecimal positionInitialMargin;

    private BigDecimal openOrderInitialMargin;

    private BigDecimal crossWalletBalance;

    private BigDecimal crossUnPnl;

    private BigDecimal availableBalance;

    private BigDecimal maxWithdrawAmount;

    public BigDecimal getWalletBalance() { return walletBalance; }

    public void setWalletBalance(BigDecimal walletBalance) { this.walletBalance = walletBalance; }

    public BigDecimal getCrossWalletBalance() { return crossWalletBalance; }

    public void setCrossWalletBalance(BigDecimal crossWalletBalance) {
        this.crossWalletBalance = crossWalletBalance;
    }

    public BigDecimal getCrossUnPnl() {
        return crossUnPnl;
    }

    public void setCrossUnPnl(BigDecimal crossUnPnl) {
        this.crossUnPnl = crossUnPnl;
    }

    public BigDecimal getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(BigDecimal availableBalance) {
        this.availableBalance = availableBalance;
    }

    public String getAsset() {
        return asset;
    }

    public void setAsset(String asset) {
        this.asset = asset;
    }

    public BigDecimal getInitialMargin() {
        return initialMargin;
    }

    public void setInitialMargin(BigDecimal initialMargin) {
        this.initialMargin = initialMargin;
    }

    public BigDecimal getMaintMargin() {
        return maintMargin;
    }

    public void setMaintMargin(BigDecimal maintMargin) {
        this.maintMargin = maintMargin;
    }

    public BigDecimal getMarginBalance() {
        return marginBalance;
    }

    public void setMarginBalance(BigDecimal marginBalance) {
        this.marginBalance = marginBalance;
    }

    public BigDecimal getMaxWithdrawAmount() {
        return maxWithdrawAmount;
    }

    public void setMaxWithdrawAmount(BigDecimal maxWithdrawAmount) {
        this.maxWithdrawAmount = maxWithdrawAmount;
    }

    public BigDecimal getOpenOrderInitialMargin() {
        return openOrderInitialMargin;
    }

    public void setOpenOrderInitialMargin(BigDecimal openOrderInitialMargin) {
        this.openOrderInitialMargin = openOrderInitialMargin;
    }

    public BigDecimal getPositionInitialMargin() {
        return positionInitialMargin;
    }

    public void setPositionInitialMargin(BigDecimal positionInitialMargin) {
        this.positionInitialMargin = positionInitialMargin;
    }

    public BigDecimal getUnrealizedProfit() {
        return unrealizedProfit;
    }

    public void setUnrealizedProfit(BigDecimal unrealizedProfit) {
        this.unrealizedProfit = unrealizedProfit;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, BinanceApiConstants.TO_STRING_BUILDER_STYLE).append("asset", asset)
                .append("walletBalance", walletBalance).append("unrealizedProfit", unrealizedProfit)
                .append("marginBalance", marginBalance).append("maintMargin", maintMargin).append("initialMargin", initialMargin)
                .append("positionInitialMargin", positionInitialMargin).append("openOrderInitialMargin", openOrderInitialMargin)
                .append("crossWalletBalance", crossWalletBalance).append("crossUnPnl", crossUnPnl)
                .append("availableBalance", availableBalance).append("maxWithdrawAmount", maxWithdrawAmount)
                .toString();
    }
}
