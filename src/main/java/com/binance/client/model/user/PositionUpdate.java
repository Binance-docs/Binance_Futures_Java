package com.binance.client.model.user;

import com.binance.client.constant.BinanceApiConstants;
import org.apache.commons.lang3.builder.ToStringBuilder;
import java.math.BigDecimal;

public class PositionUpdate {

    private String symbol;

    private BigDecimal amount;

    private BigDecimal entryPrice;

    private BigDecimal preFee;

    private BigDecimal unrealizedPnl;

    private String marginType;

    private BigDecimal isolatedWallet;

    private String positionSide;

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public BigDecimal getEntryPrice() {
        return entryPrice;
    }

    public void setEntryPrice(BigDecimal entryPrice) {
        this.entryPrice = entryPrice;
    }

    public BigDecimal getPreFee() {
        return preFee;
    }

    public void setPreFee(BigDecimal preFee) {
        this.preFee = preFee;
    }

    public BigDecimal getUnrealizedPnl() {
        return unrealizedPnl;
    }

    public void setUnrealizedPnl(BigDecimal unrealizedPnl) {
        this.unrealizedPnl = unrealizedPnl;
    }

    public String getMarginType() {
        return marginType;
    }

    public void setMarginType(String marginType) {
        this.marginType = marginType;
    }

    public BigDecimal getIsolatedWallet() {
        return isolatedWallet;
    }

    public void setIsolatedWallet(BigDecimal isolatedWallet) {
        this.isolatedWallet = isolatedWallet;
    }

    public String getPositionSide() {
        return positionSide;
    }

    public void setPositionSide(String positionSide) {
        this.positionSide = positionSide;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, BinanceApiConstants.TO_STRING_BUILDER_STYLE).append("symbol", symbol)
                .append("amount", amount).append("entryPrice", entryPrice).append("preFee", preFee)
                .append("unrealizedPnl", unrealizedPnl).append("marginType", marginType).append("isolatedWallet", isolatedWallet)
                .append("positionSide", positionSide).toString();
    }
}
