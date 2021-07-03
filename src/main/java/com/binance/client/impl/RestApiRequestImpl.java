package com.binance.client.impl;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.binance.client.RequestOptions;
import com.binance.client.exception.BinanceApiException;
import com.binance.client.impl.utils.JsonWrapperArray;
import com.binance.client.impl.utils.UrlParamsBuilder;
import com.binance.client.model.ResponseResult;
import com.binance.client.model.market.*;
import com.binance.client.model.trade.*;
import com.binance.client.model.enums.*;

import okhttp3.Request;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class RestApiRequestImpl {

    private static final Logger log = LoggerFactory.getLogger(RestApiRequestImpl.class);

    private String apiKey;
    private String secretKey;
    private String serverUrl;

    RestApiRequestImpl(String apiKey, String secretKey, RequestOptions options) {
        this.apiKey = apiKey;
        this.secretKey = secretKey;
        this.serverUrl = options.getUrl();
    }

    private Request createRequestByGet(String address, UrlParamsBuilder builder) {
        log.debug("Request URL "+ serverUrl);
        return createRequestByGet(serverUrl, address, builder);
    }

    private Request createRequestByGet(String url, String address, UrlParamsBuilder builder) {
        return createRequest(url, address, builder);
    }

    private Request createRequest(String url, String address, UrlParamsBuilder builder) {
        String requestUrl = url + address;
        log.debug("Request URL "+ serverUrl);
        if (builder != null) {
            if (builder.hasPostParam()) {
                return new Request.Builder().url(requestUrl).post(builder.buildPostBody())
                        .addHeader("Content-Type", "application/json")
                        .addHeader("client_SDK_Version", "binance_futures-1.0.1-java").build();
            } else {
                return new Request.Builder().url(requestUrl + builder.buildUrl())
                        .addHeader("Content-Type", "application/x-www-form-urlencoded")
                        .addHeader("client_SDK_Version", "binance_futures-1.0.1-java").build();
            }
        } else {
            return new Request.Builder().url(requestUrl).addHeader("Content-Type", "application/x-www-form-urlencoded")
                    .addHeader("client_SDK_Version", "binance_futures-1.0.1-java")
                    .build();
        }
    }

    private Request createRequestWithSignature(String url, String address, UrlParamsBuilder builder) {
        if (builder == null) {
            throw new BinanceApiException(BinanceApiException.RUNTIME_ERROR,
                    "[Invoking] Builder is null when create request with Signature");
        }
        String requestUrl = url + address;
        new ApiSignature().createSignature(apiKey, secretKey, builder);
        if (builder.hasPostParam()) {
            requestUrl += builder.buildUrl();
            return new Request.Builder().url(requestUrl).post(builder.buildPostBody())
                    .addHeader("Content-Type", "application/json")
                    .addHeader("X-MBX-APIKEY", apiKey)
                    .addHeader("client_SDK_Version", "binance_futures-1.0.1-java")
                    .build();
        } else if (builder.checkMethod("PUT")) {
            requestUrl += builder.buildUrl();
            return new Request.Builder().url(requestUrl)
                    .put(builder.buildPostBody())
                    .addHeader("Content-Type", "application/x-www-form-urlencoded")
                    .addHeader("X-MBX-APIKEY", apiKey)
                    .addHeader("client_SDK_Version", "binance_futures-1.0.1-java")
                    .build();
        } else if (builder.checkMethod("DELETE")) {
            requestUrl += builder.buildUrl();
            return new Request.Builder().url(requestUrl)
                    .delete()
                    .addHeader("Content-Type", "application/x-www-form-urlencoded")
                    .addHeader("client_SDK_Version", "binance_futures-1.0.1-java")
                    .addHeader("X-MBX-APIKEY", apiKey)
                    .build();
        } else {
            requestUrl += builder.buildUrl();
            return new Request.Builder().url(requestUrl)
                    .addHeader("Content-Type", "application/x-www-form-urlencoded")
                    .addHeader("client_SDK_Version", "binance_futures-1.0.1-java")
                    .addHeader("X-MBX-APIKEY", apiKey)
                    .build();
        }
    }

    private Request createRequestByPostWithSignature(String address, UrlParamsBuilder builder) {
        return createRequestWithSignature(serverUrl, address, builder.setMethod("POST"));
    }

    private Request createRequestByGetWithSignature(String address, UrlParamsBuilder builder) {
        return createRequestWithSignature(serverUrl, address, builder);
    }

    private Request createRequestByPutWithSignature(String address, UrlParamsBuilder builder) {
        return createRequestWithSignature(serverUrl, address, builder.setMethod("PUT"));
    }

    private Request createRequestByDeleteWithSignature(String address, UrlParamsBuilder builder) {
        return createRequestWithSignature(serverUrl, address, builder.setMethod("DELETE"));
    }

    private Request createRequestWithApikey(String url, String address, UrlParamsBuilder builder) {
        if (builder == null) {
            throw new BinanceApiException(BinanceApiException.RUNTIME_ERROR,
                    "[Invoking] Builder is null when create request with Signature");
        }
        String requestUrl = url + address;
        requestUrl += builder.buildUrl();
        if (builder.hasPostParam()) {
            return new Request.Builder().url(requestUrl)
                    .post(builder.buildPostBody())
                    .addHeader("Content-Type", "application/json")
                    .addHeader("X-MBX-APIKEY", apiKey)
                    .addHeader("client_SDK_Version", "binance_futures-1.0.1-java")
                    .build();
        } else if (builder.checkMethod("DELETE")) {
            return new Request.Builder().url(requestUrl)
                    .delete()
                    .addHeader("Content-Type", "application/x-www-form-urlencoded")
                    .addHeader("X-MBX-APIKEY", apiKey)
                    .addHeader("client_SDK_Version", "binance_futures-1.0.1-java")
                    .build();
        } else if (builder.checkMethod("PUT")) {
            return new Request.Builder().url(requestUrl)
                    .put(builder.buildPostBody())
                    .addHeader("Content-Type", "application/x-www-form-urlencoded")
                    .addHeader("X-MBX-APIKEY", apiKey)
                    .addHeader("client_SDK_Version", "binance_futures-1.0.1-java")
                    .build();
        } else {
            return new Request.Builder().url(requestUrl)
                    .addHeader("Content-Type", "application/x-www-form-urlencoded")
                    .addHeader("X-MBX-APIKEY", apiKey)
                    .addHeader("client_SDK_Version", "binance_futures-1.0.1-java")
                    .build();
        }
    }

    private Request createRequestByGetWithApikey(String address, UrlParamsBuilder builder) {
        return createRequestWithApikey(serverUrl, address, builder);
    }

    RestApiRequest<ExchangeInformation> getExchangeInformation() {
        RestApiRequest<ExchangeInformation> request = new RestApiRequest<>();
        UrlParamsBuilder builder = UrlParamsBuilder.build();
        request.request = createRequestByGet("/fapi/v1/exchangeInfo", builder);

        request.jsonParser = (jsonWrapper -> {
            ExchangeInformation result = new ExchangeInformation();
            result.setTimezone(jsonWrapper.getString("timezone"));
            result.setServerTime(jsonWrapper.getLong("serverTime"));

            List<RateLimit> elementList = new LinkedList<>();
            JsonWrapperArray dataArray = jsonWrapper.getJsonArray("rateLimits");
            dataArray.forEach((item) -> {
                RateLimit element = new RateLimit();
                element.setRateLimitType(item.getString("rateLimitType"));
                element.setInterval(item.getString("interval"));
                element.setIntervalNum(item.getLong("intervalNum"));
                element.setLimit(item.getLong("limit"));
                elementList.add(element);
            });
            result.setRateLimits(elementList);

            List<ExchangeFilter> filterList = new LinkedList<>();
            JsonWrapperArray filterArray = jsonWrapper.getJsonArray("exchangeFilters");
            filterArray.forEach((item) -> {
                ExchangeFilter filter = new ExchangeFilter();
                filter.setFilterType(item.getString("filterType"));
                filter.setMaxNumOrders(item.getLong("maxNumOrders"));
                filter.setMaxNumAlgoOrders(item.getLong("maxNumAlgoOrders"));
                filterList.add(filter);
            });
            result.setExchangeFilters(filterList);

            List<ExchangeInfoEntry> symbolList = new LinkedList<>();
            JsonWrapperArray symbolArray = jsonWrapper.getJsonArray("symbols");
            symbolArray.forEach((item) -> {
                ExchangeInfoEntry symbol = new ExchangeInfoEntry();
                symbol.setSymbol(item.getString("symbol"));
                symbol.setStatus(item.getString("status"));
                symbol.setMaintMarginPercent(item.getBigDecimal("maintMarginPercent"));
                symbol.setRequiredMarginPercent(item.getBigDecimal("requiredMarginPercent"));
                symbol.setBaseAsset(item.getString("baseAsset"));
                symbol.setQuoteAsset(item.getString("quoteAsset"));
                symbol.setPricePrecision(item.getLong("pricePrecision"));
                symbol.setQuantityPrecision(item.getLong("quantityPrecision"));
                symbol.setBaseAssetPrecision(item.getLong("baseAssetPrecision"));
                symbol.setQuotePrecision(item.getLong("quotePrecision"));
                symbol.setOrderTypes(item.getJsonArray("orderTypes").convert2StringList());
                symbol.setTimeInForce(item.getJsonArray("orderTypes").convert2StringList());
                List<List<Map<String, String>>> valList = new LinkedList<>();
                JsonWrapperArray valArray = item.getJsonArray("filters");
                valArray.forEach((val) -> {
                    valList.add(val.convert2DictList());
                });
                symbol.setFilters(valList);
                symbolList.add(symbol);
            });
            result.setSymbols(symbolList);

            return result;
        });
        return request;
    }

    RestApiRequest<OrderBook> getOrderBook(String symbol, Integer limit) {
        RestApiRequest<OrderBook> request = new RestApiRequest<>();
        UrlParamsBuilder builder = UrlParamsBuilder.build()
                .putToUrl("symbol", symbol)
                .putToUrl("limit", limit);
        request.request = createRequestByGet("/fapi/v1/depth", builder);

        request.jsonParser = (jsonWrapper -> {
            OrderBook result = new OrderBook();
            result.setLastUpdateId(jsonWrapper.getLong("lastUpdateId"));

            List<OrderBookEntry> elementList = new LinkedList<>();
            JsonWrapperArray dataArray = jsonWrapper.getJsonArray("bids");
            dataArray.forEachAsArray((item) -> {
                OrderBookEntry element = new OrderBookEntry();
                element.setPrice(item.getBigDecimalAt(0));
                element.setQty(item.getBigDecimalAt(1));
                elementList.add(element);
            });
            result.setBids(elementList);

            List<OrderBookEntry> askList = new LinkedList<>();
            JsonWrapperArray askArray = jsonWrapper.getJsonArray("asks");
            askArray.forEachAsArray((item) -> {
                OrderBookEntry element = new OrderBookEntry();
                element.setPrice(item.getBigDecimalAt(0));
                element.setQty(item.getBigDecimalAt(1));
                askList.add(element);
            });
            result.setAsks(askList);

            return result;
        });
        return request;
    }

    RestApiRequest<List<Trade>> getRecentTrades(String symbol, Integer limit) {
        RestApiRequest<List<Trade>> request = new RestApiRequest<>();
        UrlParamsBuilder builder = UrlParamsBuilder.build()
                .putToUrl("symbol", symbol)
                .putToUrl("limit", limit);
        request.request = createRequestByGet("/fapi/v1/trades", builder);

        request.jsonParser = (jsonWrapper -> {
            List<Trade> result = new LinkedList<>();
            JsonWrapperArray dataArray = jsonWrapper.getJsonArray("data");
            dataArray.forEach((item) -> {
                Trade element = new Trade();
                element.setId(item.getLong("id"));
                element.setPrice(item.getBigDecimal("price"));
                element.setQty(item.getBigDecimal("qty"));
                element.setQuoteQty(item.getBigDecimal("quoteQty"));
                element.setTime(item.getLong("time"));
                element.setIsBuyerMaker(item.getBoolean("isBuyerMaker"));
                result.add(element);
            });

            return result;
        });
        return request;
    }

    RestApiRequest<List<Trade>> getOldTrades(String symbol, Integer limit, Long fromId) {
        RestApiRequest<List<Trade>> request = new RestApiRequest<>();
        UrlParamsBuilder builder = UrlParamsBuilder.build()
                .putToUrl("symbol", symbol)
                .putToUrl("limit", limit)
                .putToUrl("fromId", fromId);
        request.request = createRequestByGetWithApikey("/fapi/v1/historicalTrades", builder);

        request.jsonParser = (jsonWrapper -> {
            List<Trade> result = new LinkedList<>();
            JsonWrapperArray dataArray = jsonWrapper.getJsonArray("data");
            dataArray.forEach((item) -> {
                Trade element = new Trade();
                element.setId(item.getLong("id"));
                element.setPrice(item.getBigDecimal("price"));
                element.setQty(item.getBigDecimal("qty"));
                element.setQuoteQty(item.getBigDecimal("quoteQty"));
                element.setTime(item.getLong("time"));
                element.setIsBuyerMaker(item.getBoolean("isBuyerMaker"));
                result.add(element);
            });

            return result;
        });
        return request;
    }

    RestApiRequest<List<AggregateTrade>> getAggregateTrades(String symbol, Long fromId,
                                                            Long startTime, Long endTime, Integer limit) {
        RestApiRequest<List<AggregateTrade>> request = new RestApiRequest<>();
        UrlParamsBuilder builder = UrlParamsBuilder.build()
                .putToUrl("symbol", symbol)
                .putToUrl("fromId", fromId)
                .putToUrl("startTime", startTime)
                .putToUrl("endTime", endTime)
                .putToUrl("limit", limit);
        request.request = createRequestByGet("/fapi/v1/aggTrades", builder);

        request.jsonParser = (jsonWrapper -> {
            List<AggregateTrade> result = new LinkedList<>();
            JsonWrapperArray dataArray = jsonWrapper.getJsonArray("data");
            dataArray.forEach((item) -> {
                AggregateTrade element = new AggregateTrade();
                element.setId(item.getLong("a"));
                element.setPrice(item.getBigDecimal("p"));
                element.setQty(item.getBigDecimal("q"));
                element.setFirstId(item.getLong("f"));
                element.setLastId(item.getLong("l"));
                element.setTime(item.getLong("T"));
                element.setIsBuyerMaker(item.getBoolean("m"));
                result.add(element);
            });

            return result;
        });
        return request;
    }

    RestApiRequest<List<Candlestick>> getCandlestick(String symbol, CandlestickInterval interval, Long startTime,
                                                     Long endTime, Integer limit) {
        RestApiRequest<List<Candlestick>> request = new RestApiRequest<>();
        UrlParamsBuilder builder = UrlParamsBuilder.build()
                .putToUrl("symbol", symbol)
                .putToUrl("interval", interval)
                .putToUrl("startTime", startTime)
                .putToUrl("endTime", endTime)
                .putToUrl("limit", limit);
        request.request = createRequestByGet("/fapi/v1/klines", builder);

        request.jsonParser = (jsonWrapper -> {
            List<Candlestick> result = new LinkedList<>();
            JsonWrapperArray dataArray = jsonWrapper.getJsonArray("data");
            dataArray.forEachAsArray((item) -> {
                Candlestick element = new Candlestick();
                element.setOpenTime(item.getLongAt(0));
                element.setOpen(item.getBigDecimalAt(1));
                element.setHigh(item.getBigDecimalAt(2));
                element.setLow(item.getBigDecimalAt(3));
                element.setClose(item.getBigDecimalAt(4));
                element.setVolume(item.getBigDecimalAt(5));
                element.setCloseTime(item.getLongAt(6));
                element.setQuoteAssetVolume(item.getBigDecimalAt(7));
                element.setNumTrades(item.getIntegerAt(8));
                element.setTakerBuyBaseAssetVolume(item.getBigDecimalAt(9));
                element.setTakerBuyQuoteAssetVolume(item.getBigDecimalAt(10));
                result.add(element);
            });

            return result;
        });
        return request;
    }

    RestApiRequest<List<Candlestick>> getContinuousCandlesticks(String pair, ContractType contractType, CandlestickInterval interval, Long startTime,
                                                     Long endTime, Integer limit) {
        RestApiRequest<List<Candlestick>> request = new RestApiRequest<>();
        UrlParamsBuilder builder = UrlParamsBuilder.build()
                .putToUrl("pair", pair)
                .putToUrl("contractType", contractType)
                .putToUrl("interval", interval)
                .putToUrl("startTime", startTime)
                .putToUrl("endTime", endTime)
                .putToUrl("limit", limit);
        request.request = createRequestByGet("/fapi/v1/continuousKlines", builder);

        request.jsonParser = (jsonWrapper -> {
            List<Candlestick> result = new LinkedList<>();
            JsonWrapperArray dataArray = jsonWrapper.getJsonArray("data");
            dataArray.forEachAsArray((item) -> {
                Candlestick element = new Candlestick();
                element.setOpenTime(item.getLongAt(0));
                element.setOpen(item.getBigDecimalAt(1));
                element.setHigh(item.getBigDecimalAt(2));
                element.setLow(item.getBigDecimalAt(3));
                element.setClose(item.getBigDecimalAt(4));
                element.setVolume(item.getBigDecimalAt(5));
                element.setCloseTime(item.getLongAt(6));
                element.setQuoteAssetVolume(item.getBigDecimalAt(7));
                element.setNumTrades(item.getIntegerAt(8));
                element.setTakerBuyBaseAssetVolume(item.getBigDecimalAt(9));
                element.setTakerBuyQuoteAssetVolume(item.getBigDecimalAt(10));
                result.add(element);
            });

            return result;
        });
        return request;
    }

    RestApiRequest<List<Candlestick>> getIndexPriceCandlesticks(String pair, CandlestickInterval interval, Long startTime,
                                                                Long endTime, Integer limit) {
        RestApiRequest<List<Candlestick>> request = new RestApiRequest<>();
        UrlParamsBuilder builder = UrlParamsBuilder.build()
                .putToUrl("pair", pair)
                .putToUrl("interval", interval)
                .putToUrl("startTime", startTime)
                .putToUrl("endTime", endTime)
                .putToUrl("limit", limit);
        request.request = createRequestByGet("/fapi/v1/indexPriceKlines", builder);

        request.jsonParser = (jsonWrapper -> {
            List<Candlestick> result = new LinkedList<>();
            JsonWrapperArray dataArray = jsonWrapper.getJsonArray("data");
            dataArray.forEachAsArray((item) -> {
                Candlestick element = new Candlestick();
                element.setOpenTime(item.getLongAt(0));
                element.setOpen(item.getBigDecimalAt(1));
                element.setHigh(item.getBigDecimalAt(2));
                element.setLow(item.getBigDecimalAt(3));
                element.setClose(item.getBigDecimalAt(4));
                element.setCloseTime(item.getLongAt(6));
                element.setNumTrades(item.getIntegerAt(8));
                result.add(element);
            });

            return result;
        });
        return request;
    }

    RestApiRequest<List<Candlestick>> getMarkPriceCandlesticks(String pair, CandlestickInterval interval, Long startTime,
                                                                Long endTime, Integer limit) {
        RestApiRequest<List<Candlestick>> request = new RestApiRequest<>();
        UrlParamsBuilder builder = UrlParamsBuilder.build()
                .putToUrl("pair", pair)
                .putToUrl("interval", interval)
                .putToUrl("startTime", startTime)
                .putToUrl("endTime", endTime)
                .putToUrl("limit", limit);
        request.request = createRequestByGet("/fapi/v1/markPriceKlines", builder);

        request.jsonParser = (jsonWrapper -> {
            List<Candlestick> result = new LinkedList<>();
            JsonWrapperArray dataArray = jsonWrapper.getJsonArray("data");
            dataArray.forEachAsArray((item) -> {
                Candlestick element = new Candlestick();
                element.setOpenTime(item.getLongAt(0));
                element.setOpen(item.getBigDecimalAt(1));
                element.setHigh(item.getBigDecimalAt(2));
                element.setLow(item.getBigDecimalAt(3));
                element.setClose(item.getBigDecimalAt(4));
                element.setCloseTime(item.getLongAt(6));
                element.setNumTrades(item.getIntegerAt(8));
                result.add(element);
            });

            return result;
        });
        return request;
    }

    RestApiRequest<List<MarkPrice>> getMarkPrice(String symbol) {
        RestApiRequest<List<MarkPrice>> request = new RestApiRequest<>();
        UrlParamsBuilder builder = UrlParamsBuilder.build()
                .putToUrl("symbol", symbol);
        request.request = createRequestByGet("/fapi/v1/premiumIndex", builder);

        request.jsonParser = (jsonWrapper -> {
            List<MarkPrice> result = new LinkedList<>();
            JsonWrapperArray dataArray = new JsonWrapperArray(new JSONArray());
            if (jsonWrapper.containKey("data")) {
                dataArray = jsonWrapper.getJsonArray("data");
            } else {
                dataArray.add(jsonWrapper.convert2JsonObject());
            }
            dataArray.forEach((item) -> {
                MarkPrice element = new MarkPrice();
                element.setSymbol(item.getString("symbol"));
                element.setMarkPrice(item.getBigDecimal("markPrice"));
                element.setIndexPrice(item.getBigDecimal("indexPrice"));
                element.setLastFundingRate(item.getBigDecimal("lastFundingRate"));
                element.setNextFundingTime(item.getLong("nextFundingTime"));
                element.setInterestRate(item.getBigDecimal("interestRate"));
                element.setTime(item.getLong("time"));
                result.add(element);
            });

            return result;

        });
        return request;
    }

    RestApiRequest<List<FundingRate>> getFundingRate(String symbol, Long startTime, Long endTime, Integer limit) {
        RestApiRequest<List<FundingRate>> request = new RestApiRequest<>();
        UrlParamsBuilder builder = UrlParamsBuilder.build()
                .putToUrl("symbol", symbol)
                .putToUrl("startTime", startTime)
                .putToUrl("endTime", endTime)
                .putToUrl("limit", limit);
        request.request = createRequestByGet("/fapi/v1/fundingRate", builder);

        request.jsonParser = (jsonWrapper -> {
            List<FundingRate> result = new LinkedList<>();
            JsonWrapperArray dataArray = jsonWrapper.getJsonArray("data");
            dataArray.forEach(item -> {
                FundingRate element = new FundingRate();
                element.setSymbol(item.getString("symbol"));
                element.setFundingRate(item.getBigDecimal("fundingRate"));
                element.setFundingTime(item.getLong("fundingTime"));
                result.add(element);
            });

            return result;
        });
        return request;
    }

    RestApiRequest<List<PriceChangeTicker>> get24hrTickerPriceChange(String symbol) {
        RestApiRequest<List<PriceChangeTicker>> request = new RestApiRequest<>();
        UrlParamsBuilder builder = UrlParamsBuilder.build()
                .putToUrl("symbol", symbol);
        request.request = createRequestByGet("/fapi/v1/ticker/24hr", builder);

        request.jsonParser = (jsonWrapper -> {
            List<PriceChangeTicker> result = new LinkedList<>();
            JsonWrapperArray dataArray = new JsonWrapperArray(new JSONArray());
            if (jsonWrapper.containKey("data")) {
                dataArray = jsonWrapper.getJsonArray("data");
            } else {
                dataArray.add(jsonWrapper.convert2JsonObject());
            }
            dataArray.forEach((item) -> {
                PriceChangeTicker element = new PriceChangeTicker();
                element.setSymbol(item.getString("symbol"));
                element.setPriceChange(item.getBigDecimal("priceChange"));
                element.setPriceChangePercent(item.getBigDecimal("priceChangePercent"));
                element.setWeightedAvgPrice(item.getBigDecimal("weightedAvgPrice"));
                element.setPrevClosePrice(item.getBigDecimal("prevClosePrice"));
                element.setLastPrice(item.getBigDecimal("lastPrice"));
                element.setLastQty(item.getBigDecimal("lastQty"));
                element.setOpenPrice(item.getBigDecimal("openPrice"));
                element.setHighPrice(item.getBigDecimal("highPrice"));
                element.setLowPrice(item.getBigDecimal("lowPrice"));
                element.setVolume(item.getBigDecimal("volume"));
                element.setQuoteVolume(item.getBigDecimal("quoteVolume"));
                element.setOpenTime(item.getLong("openTime"));
                element.setCloseTime(item.getLong("closeTime"));
                element.setFirstId(item.getLong("firstId"));
                element.setLastId(item.getLong("lastId"));
                element.setCount(item.getLong("count"));
                result.add(element);
            });

            return result;
        });
        return request;
    }

    RestApiRequest<List<SymbolPrice>> getSymbolPriceTicker(String symbol) {
        RestApiRequest<List<SymbolPrice>> request = new RestApiRequest<>();
        UrlParamsBuilder builder = UrlParamsBuilder.build()
                .putToUrl("symbol", symbol);
        request.request = createRequestByGet("/fapi/v1/ticker/price", builder);

        request.jsonParser = (jsonWrapper -> {
            List<SymbolPrice> result = new LinkedList<>();
            JsonWrapperArray dataArray = new JsonWrapperArray(new JSONArray());
            if (jsonWrapper.containKey("data")) {
                dataArray = jsonWrapper.getJsonArray("data");
            } else {
                dataArray.add(jsonWrapper.convert2JsonObject());
            }
            dataArray.forEach((item) -> {
                SymbolPrice element = new SymbolPrice();
                element.setSymbol(item.getString("symbol"));
                element.setPrice(item.getBigDecimal("price"));
                element.setTime(item.getLong("time"));
                result.add(element);
            });

            return result;
        });
        return request;
    }

    RestApiRequest<List<SymbolOrderBook>> getSymbolOrderBookTicker(String symbol) {
        RestApiRequest<List<SymbolOrderBook>> request = new RestApiRequest<>();
        UrlParamsBuilder builder = UrlParamsBuilder.build()
                .putToUrl("symbol", symbol);
        request.request = createRequestByGet("/fapi/v1/ticker/bookTicker", builder);

        request.jsonParser = (jsonWrapper -> {
            List<SymbolOrderBook> result = new LinkedList<>();
            JsonWrapperArray dataArray = new JsonWrapperArray(new JSONArray());
            if (jsonWrapper.containKey("data")) {
                dataArray = jsonWrapper.getJsonArray("data");
            } else {
                dataArray.add(jsonWrapper.convert2JsonObject());
            }
            dataArray.forEach((item) -> {
                SymbolOrderBook element = new SymbolOrderBook();
                element.setSymbol(item.getString("symbol"));
                element.setBidPrice(item.getBigDecimal("bidPrice"));
                element.setBidQty(item.getBigDecimal("bidQty"));
                element.setAskPrice(item.getBigDecimal("askPrice"));
                element.setAskQty(item.getBigDecimal("askQty"));
                element.setTime(item.getLong("time"));
                result.add(element);
            });

            return result;
        });
        return request;
    }

    RestApiRequest<List<LiquidationOrder>> getLiquidationOrders(String symbol, AutoCloseType autoCloseType, Long startTime, Long endTime,
                                                                Integer limit) {
        RestApiRequest<List<LiquidationOrder>> request = new RestApiRequest<>();
        UrlParamsBuilder builder = UrlParamsBuilder.build()
                .putToUrl("symbol", symbol)
                .putToUrl("startTime", startTime)
                .putToUrl("endTime", endTime)
                .putToUrl("limit", limit);
        request.request = createRequestByGetWithApikey("/fapi/v1/allForceOrders", builder);

        request.jsonParser = (jsonWrapper -> {
            List<LiquidationOrder> result = new LinkedList<>();
            JsonWrapperArray dataArray = jsonWrapper.getJsonArray("data");

            dataArray.forEach((item) -> {
                LiquidationOrder element = new LiquidationOrder();
                element.setOrderId(item.getLong("orderId"));
                element.setSymbol(item.getString("symbol"));
                element.setStatus(item.getString("status"));
                element.setClientOrderId(item.getString("clientOrderId"));
                element.setPrice(item.getBigDecimal("price"));
                element.setAveragePrice(item.getBigDecimal("avgPrice"));
                element.setOrigQty(item.getBigDecimal("origQty"));
                element.setExecutedQty(item.getBigDecimal("executedQty"));
                element.setCumQuote(item.getBigDecimal("cumQuote"));
                element.setTimeInForce(item.getString("timeInForce"));
                element.setType(item.getString("type"));
                element.setReduceOnly(item.getBoolean("reduceOnly"));
                element.setClosePosition(item.getBoolean("closePosition"));
                element.setSide(item.getString("side"));
                element.setPositionSide(item.getString("positionSide"));
                element.setStopPrice(item.getBigDecimal("stopPrice"));
                element.setWorkingType(item.getString("workingType"));
                element.setOrigType(item.getString("origType"));
                element.setTime(item.getLong("time"));
                element.setUpdateTime(item.getLong("updateTime"));

                result.add(element);
            });

            return result;
        });
        return request;
    }

    RestApiRequest<List<Object>> postBatchOrders(String batchOrders) {
        RestApiRequest<List<Object>> request = new RestApiRequest<>();
        UrlParamsBuilder builder = UrlParamsBuilder.build()
                .putToUrl("batchOrders", batchOrders);
        request.request = createRequestByPostWithSignature("/fapi/v1/batchOrders", builder);

        request.jsonParser = (jsonWrapper -> {
            JSONObject jsonObject = jsonWrapper.getJson();

            // success results
            List<Object> listResult = new ArrayList<>();
            JSONArray jsonArray = (JSONArray) jsonObject.get("data");
            jsonArray.forEach(obj -> {
                if (((JSONObject) obj).containsKey("code")) {
                    ResponseResult responseResult = new ResponseResult();
                    responseResult.setCode(((JSONObject) obj).getInteger("code"));
                    responseResult.setMsg(((JSONObject) obj).getString("msg"));
                    listResult.add(responseResult);
                } else {
                    Order o = new Order();
                    JSONObject jsonObj = (JSONObject) obj;
                    o.setClientOrderId(jsonWrapper.getString("clientOrderId"));
                    o.setCumQty(jsonWrapper.getBigDecimal("cumQty"));
                    o.setCumQuote(jsonWrapper.getBigDecimal("cumQuote"));
                    o.setExecutedQty(jsonWrapper.getBigDecimal("executedQty"));
                    o.setOrderId(jsonWrapper.getLong("orderId"));
                    o.setAvgPrice(jsonWrapper.getBigDecimal("avgPrice"));
                    o.setOrigQty(jsonWrapper.getBigDecimal("origQty"));
                    o.setPrice(jsonWrapper.getBigDecimal("price"));
                    o.setReduceOnly(jsonWrapper.getBoolean("reduceOnly"));
                    o.setSide(jsonWrapper.getString("side"));
                    o.setPositionSide(jsonWrapper.getString("positionSide"));
                    o.setStatus(jsonWrapper.getString("status"));
                    o.setStopPrice(jsonWrapper.getBigDecimal("stopPrice"));
                    o.setClosePosition(jsonWrapper.getBoolean("closePosition"));
                    o.setSymbol(jsonWrapper.getString("symbol"));
                    o.setTimeInForce(jsonWrapper.getString("timeInForce"));
                    o.setType(jsonWrapper.getString("type"));
                    o.setOrigType(jsonWrapper.getString("origType"));
                    if (jsonWrapper.containKey("activatePrice")) {
                        o.setActivatePrice(jsonWrapper.getBigDecimal("activatePrice"));
                    }
                    if (jsonWrapper.containKey("priceRate")) {
                        o.setActivatePrice(jsonWrapper.getBigDecimal("priceRate"));
                    }
                    o.setUpdateTime(jsonWrapper.getLong("updateTime"));
                    o.setWorkingType(jsonWrapper.getString("workingType"));
                    o.setPriceProtect(jsonWrapper.getBoolean("priceProtect"));
                    listResult.add(o);
                }
            });
            return listResult;
        });
        return request;
    }

    RestApiRequest<Order> postOrder(String symbol, OrderSide side, PositionSide positionSide, OrderType orderType,
            TimeInForce timeInForce, String quantity, String reduceOnly, String price, String newClientOrderId,
             String stopPrice, String closePosition, String activationPrice, String callBackRate, WorkingType workingType, String priceProtect, NewOrderRespType newOrderRespType) {
        RestApiRequest<Order> request = new RestApiRequest<>();
        UrlParamsBuilder builder = UrlParamsBuilder.build()
                .putToUrl("symbol", symbol)
                .putToUrl("side", side)
                .putToUrl("positionSide", positionSide)
                .putToUrl("type", orderType)
                .putToUrl("timeInForce", timeInForce)
                .putToUrl("quantity", quantity)
                .putToUrl("price", price)
                .putToUrl("reduceOnly", reduceOnly)
                .putToUrl("newClientOrderId", newClientOrderId)
                .putToUrl("stopPrice", stopPrice)
                .putToUrl("closePosition", closePosition)
                .putToUrl("activationPrice", activationPrice)
                .putToUrl("callBackRate", callBackRate)
                .putToUrl("workingType", workingType)
                .putToUrl("priceProtect", priceProtect)
                .putToUrl("newOrderRespType", newOrderRespType);

        request.request = createRequestByPostWithSignature("/fapi/v1/order", builder);

        request.jsonParser = (jsonWrapper -> {
            Order result = new Order();
            result.setClientOrderId(jsonWrapper.getString("clientOrderId"));
            result.setCumQuote(jsonWrapper.getBigDecimal("cumQuote"));
            result.setExecutedQty(jsonWrapper.getBigDecimal("executedQty"));
            result.setOrderId(jsonWrapper.getLong("orderId"));
            result.setAvgPrice(jsonWrapper.getBigDecimal("avgPrice"));
            result.setOrigQty(jsonWrapper.getBigDecimal("origQty"));
            result.setPrice(jsonWrapper.getBigDecimal("price"));
            result.setReduceOnly(jsonWrapper.getBoolean("reduceOnly"));
            result.setSide(jsonWrapper.getString("side"));
            result.setPositionSide(jsonWrapper.getString("positionSide"));
            result.setStatus(jsonWrapper.getString("status"));
            result.setStopPrice(jsonWrapper.getBigDecimal("stopPrice"));
            result.setClosePosition(jsonWrapper.getBoolean("closePosition"));
            result.setSymbol(jsonWrapper.getString("symbol"));
            result.setTimeInForce(jsonWrapper.getString("timeInForce"));
            result.setType(jsonWrapper.getString("type"));
            result.setOrigType(jsonWrapper.getString("origType"));
            if (jsonWrapper.containKey("activatePrice")) {
                result.setActivatePrice(jsonWrapper.getBigDecimal("activatePrice"));
            }
            if (jsonWrapper.containKey("priceRate")) {
                result.setActivatePrice(jsonWrapper.getBigDecimal("priceRate"));
            }
            result.setUpdateTime(jsonWrapper.getLong("updateTime"));
            result.setWorkingType(jsonWrapper.getString("workingType"));
            result.setPriceProtect(jsonWrapper.getBoolean("priceProtect"));
            return result;
        });
        return request;
    }

    RestApiRequest<ResponseResult> changePositionSide(String dual) {
        RestApiRequest<ResponseResult> request = new RestApiRequest<>();
        UrlParamsBuilder builder = UrlParamsBuilder.build()
                .putToUrl("dualSidePosition", dual);
        request.request = createRequestByPostWithSignature("/fapi/v1/positionSide/dual", builder);

        request.jsonParser = (jsonWrapper -> {
            ResponseResult result = new ResponseResult();
            result.setCode(jsonWrapper.getInteger("code"));
            result.setMsg(jsonWrapper.getString("msg"));
            return result;
        });
        return request;
    }

    RestApiRequest<ResponseResult> changeMarginType(String symbolName, MarginType marginType) {
        RestApiRequest<ResponseResult> request = new RestApiRequest<>();
        UrlParamsBuilder builder = UrlParamsBuilder.build()
                .putToUrl("symbol", symbolName)
                .putToUrl("marginType", marginType);
        request.request = createRequestByPostWithSignature("/fapi/v1/marginType", builder);

        request.jsonParser = (jsonWrapper -> {
            ResponseResult result = new ResponseResult();
            result.setCode(jsonWrapper.getInteger("code"));
            result.setMsg(jsonWrapper.getString("msg"));
            return result;
        });
        return request;
    }

    RestApiRequest<JSONObject> addPositionMargin(String symbol, Integer type, String amount, PositionSide positionSide) {
        RestApiRequest<JSONObject> request = new RestApiRequest<>();
        UrlParamsBuilder builder = UrlParamsBuilder.build()
                .putToUrl("symbol", symbol)
                .putToUrl("amount", amount)
                .putToUrl("positionSide", positionSide.name())
                .putToUrl("type", type);
        request.request = createRequestByPostWithSignature("/fapi/v1/positionMargin", builder);

        request.jsonParser = (jsonWrapper -> {
            JSONObject result = new JSONObject();
            result.put("code", jsonWrapper.getInteger("code"));
            result.put("msg", jsonWrapper.getString("msg"));
            result.put("amount", jsonWrapper.getDouble("amount"));
            result.put("type", jsonWrapper.getInteger("type"));
            return result;
        });
        return request;
    }

    RestApiRequest<List<WalletDeltaLog>> getPositionMarginHistory(String symbol, Integer type, Long startTime, Long endTime, Integer limit) {
        RestApiRequest<List<WalletDeltaLog>> request = new RestApiRequest<>();
        UrlParamsBuilder builder = UrlParamsBuilder.build()
                .putToUrl("symbol", symbol)
                .putToUrl("type", type)
                .putToUrl("startTime", startTime)
                .putToUrl("endTime", endTime)
                .putToUrl("limit", limit);
        request.request = createRequestByGet("/fapi/v1/positionMargin/history", builder);

        request.jsonParser = (jsonWrapper -> {
            List<WalletDeltaLog> logs = new LinkedList<>();
            JsonWrapperArray dataArray = jsonWrapper.getJsonArray("data");
            dataArray.forEach((item) -> {
                WalletDeltaLog log = new WalletDeltaLog();
                log.setSymbol(item.getString("symbol"));
                log.setAmount(item.getString("amount"));
                log.setAsset(item.getString("asset"));
                log.setTime(item.getLong("time"));
                log.setPositionSide(item.getString("positionSide"));
                log.setType(item.getInteger("type"));
                logs.add(log);
            });
            return logs;
        });
        return request;
    }

    RestApiRequest<JSONObject> getPositionSide() {
        RestApiRequest<JSONObject> request = new RestApiRequest<>();
        UrlParamsBuilder builder = UrlParamsBuilder.build();
        request.request = createRequestByGetWithSignature("/fapi/v1/positionSide/dual", builder);

        request.jsonParser = (jsonWrapper -> {
            JSONObject result = new JSONObject();
            result.put("dualSidePosition", jsonWrapper.getBoolean("dualSidePosition"));
            return result;
        });
        return request;
    }

    RestApiRequest<Order> cancelOrder(String symbol, Long orderId, String origClientOrderId) {
        RestApiRequest<Order> request = new RestApiRequest<>();
        UrlParamsBuilder builder = UrlParamsBuilder.build()
                .putToUrl("symbol", symbol)
                .putToUrl("orderId", orderId)
                .putToUrl("origClientOrderId", origClientOrderId);
        request.request = createRequestByDeleteWithSignature("/fapi/v1/order", builder);

        request.jsonParser = (jsonWrapper -> {
            Order result = new Order();
            result.setClientOrderId(jsonWrapper.getString("clientOrderId"));
            result.setCumQuote(jsonWrapper.getBigDecimal("cumQuote"));
            result.setExecutedQty(jsonWrapper.getBigDecimal("executedQty"));
            result.setOrderId(jsonWrapper.getLong("orderId"));
            result.setAvgPrice(jsonWrapper.getBigDecimal("avgPrice"));
            result.setOrigQty(jsonWrapper.getBigDecimal("origQty"));
            result.setPrice(jsonWrapper.getBigDecimal("price"));
            result.setReduceOnly(jsonWrapper.getBoolean("reduceOnly"));
            result.setSide(jsonWrapper.getString("side"));
            result.setPositionSide(jsonWrapper.getString("positionSide"));
            result.setStatus(jsonWrapper.getString("status"));
            result.setStopPrice(jsonWrapper.getBigDecimal("stopPrice"));
            result.setClosePosition(jsonWrapper.getBoolean("closePosition"));
            result.setSymbol(jsonWrapper.getString("symbol"));
            result.setTimeInForce(jsonWrapper.getString("timeInForce"));
            result.setType(jsonWrapper.getString("type"));
            result.setOrigType(jsonWrapper.getString("origType"));
            if (jsonWrapper.containKey("activatePrice")) {
                result.setActivatePrice(jsonWrapper.getBigDecimal("activatePrice"));
            }
            if (jsonWrapper.containKey("priceRate")) {
                result.setActivatePrice(jsonWrapper.getBigDecimal("priceRate"));
            }
            result.setUpdateTime(jsonWrapper.getLong("updateTime"));
            result.setWorkingType(jsonWrapper.getString("workingType"));
            result.setPriceProtect(jsonWrapper.getBoolean("priceProtect"));
            return result;
        });
        return request;
    }

    RestApiRequest<ResponseResult> cancelAllOpenOrder(String symbol) {
        RestApiRequest<ResponseResult> request = new RestApiRequest<>();
        UrlParamsBuilder builder = UrlParamsBuilder.build()
                .putToUrl("symbol", symbol);
        request.request = createRequestByDeleteWithSignature("/fapi/v1/allOpenOrders", builder);

        request.jsonParser = (jsonWrapper -> {
            ResponseResult responseResult = new ResponseResult();
            responseResult.setCode(jsonWrapper.getInteger("code"));
            responseResult.setMsg(jsonWrapper.getString("msg"));
            return responseResult;
        });
        return request;
    }

    RestApiRequest<List<Object>> batchCancelOrders(String symbol, String orderIdList, String origClientOrderIdList) {
        RestApiRequest<List<Object>> request = new RestApiRequest<>();
        UrlParamsBuilder builder = UrlParamsBuilder.build();
        builder.putToUrl("symbol", symbol);
        if (StringUtils.isNotBlank(orderIdList)) {
            builder.putToUrl("orderIdList", orderIdList);
        } else {
            builder.putToUrl("origClientOrderIdList", origClientOrderIdList);
        }
        request.request = createRequestByDeleteWithSignature("/fapi/v1/batchOrders", builder);

        request.jsonParser = (jsonWrapper -> {
            JSONObject jsonObject = jsonWrapper.getJson();

            // success results
            List<Object> listResult = new ArrayList<>();
            JSONArray jsonArray = (JSONArray) jsonObject.get("data");
            jsonArray.forEach(obj -> {
                if (((JSONObject)obj).containsKey("code")) {
                    ResponseResult responseResult = new ResponseResult();
                    responseResult.setCode(((JSONObject)obj).getInteger("code"));
                    responseResult.setMsg(((JSONObject)obj).getString("msg"));
                    listResult.add(responseResult);
                } else {
                    Order o = new Order();
                    JSONObject jsonObj = (JSONObject) obj;
                    o.setClientOrderId(jsonWrapper.getString("clientOrderId"));
                    o.setCumQty(jsonWrapper.getBigDecimal("cumQty"));
                    o.setCumQuote(jsonWrapper.getBigDecimal("cumQuote"));
                    o.setExecutedQty(jsonWrapper.getBigDecimal("executedQty"));
                    o.setOrderId(jsonWrapper.getLong("orderId"));
                    o.setAvgPrice(jsonWrapper.getBigDecimal("avgPrice"));
                    o.setOrigQty(jsonWrapper.getBigDecimal("origQty"));
                    o.setPrice(jsonWrapper.getBigDecimal("price"));
                    o.setReduceOnly(jsonWrapper.getBoolean("reduceOnly"));
                    o.setSide(jsonWrapper.getString("side"));
                    o.setPositionSide(jsonWrapper.getString("positionSide"));
                    o.setStatus(jsonWrapper.getString("status"));
                    o.setStopPrice(jsonWrapper.getBigDecimal("stopPrice"));
                    o.setClosePosition(jsonWrapper.getBoolean("closePosition"));
                    o.setSymbol(jsonWrapper.getString("symbol"));
                    o.setTimeInForce(jsonWrapper.getString("timeInForce"));
                    o.setType(jsonWrapper.getString("type"));
                    o.setOrigType(jsonWrapper.getString("origType"));
                    if (jsonWrapper.containKey("activatePrice")) {
                        o.setActivatePrice(jsonWrapper.getBigDecimal("activatePrice"));
                    }
                    if (jsonWrapper.containKey("priceRate")) {
                        o.setActivatePrice(jsonWrapper.getBigDecimal("priceRate"));
                    }
                    o.setUpdateTime(jsonWrapper.getLong("updateTime"));
                    o.setWorkingType(jsonWrapper.getString("workingType"));
                    o.setPriceProtect(jsonWrapper.getBoolean("priceProtect"));
                    listResult.add(o);
                }
            });
            return listResult;
        });
        return request;
    }

    RestApiRequest<JSONObject> autoCancelAllOrders(String symbol, Long countdownTime) {
        RestApiRequest<JSONObject> request = new RestApiRequest<>();
        UrlParamsBuilder builder = UrlParamsBuilder.build();
        builder.putToUrl("symbol", symbol);
        builder.putToUrl("countdownTime", countdownTime);

        request.request = createRequestByDeleteWithSignature("/fapi/v1/countdownCancelAll", builder);

        request.jsonParser = (jsonWrapper -> {
            JSONObject result = new JSONObject();
            result.put("symbol", jsonWrapper.getString("symbol"));
            result.put("countdownTime", jsonWrapper.getLong("countdownTime"));
            return result;
        });
        return request;
    }

    RestApiRequest<Order> getOrder(String symbol, Long orderId, String origClientOrderId) {
        RestApiRequest<Order> request = new RestApiRequest<>();
        UrlParamsBuilder builder = UrlParamsBuilder.build()
                .putToUrl("symbol", symbol)
                .putToUrl("orderId", orderId)
                .putToUrl("origClientOrderId", origClientOrderId);
        request.request = createRequestByGetWithSignature("/fapi/v1/order", builder);

        request.jsonParser = (jsonWrapper -> {
            Order result = new Order();
            result.setClientOrderId(jsonWrapper.getString("clientOrderId"));
            result.setCumQuote(jsonWrapper.getBigDecimal("cumQuote"));
            result.setExecutedQty(jsonWrapper.getBigDecimal("executedQty"));
            result.setOrderId(jsonWrapper.getLong("orderId"));
            result.setAvgPrice(jsonWrapper.getBigDecimal("avgPrice"));
            result.setOrigQty(jsonWrapper.getBigDecimal("origQty"));
            result.setPrice(jsonWrapper.getBigDecimal("price"));
            result.setReduceOnly(jsonWrapper.getBoolean("reduceOnly"));
            result.setSide(jsonWrapper.getString("side"));
            result.setPositionSide(jsonWrapper.getString("positionSide"));
            result.setStatus(jsonWrapper.getString("status"));
            result.setStopPrice(jsonWrapper.getBigDecimal("stopPrice"));
            result.setClosePosition(jsonWrapper.getBoolean("closePosition"));
            result.setSymbol(jsonWrapper.getString("symbol"));
            result.setTimeInForce(jsonWrapper.getString("timeInForce"));
            result.setType(jsonWrapper.getString("type"));
            result.setOrigType(jsonWrapper.getString("origType"));
            if (jsonWrapper.containKey("activatePrice")) {
                result.setActivatePrice(jsonWrapper.getBigDecimal("activatePrice"));
            }
            if (jsonWrapper.containKey("priceRate")) {
                result.setActivatePrice(jsonWrapper.getBigDecimal("priceRate"));
            }
            result.setUpdateTime(jsonWrapper.getLong("updateTime"));
            result.setWorkingType(jsonWrapper.getString("workingType"));
            result.setPriceProtect(jsonWrapper.getBoolean("priceProtect"));
            return result;
        });
        return request;
    }

    RestApiRequest<Order> getOpenOrder(String symbol, Long orderId, String origClientOrderId) {
        RestApiRequest<Order> request = new RestApiRequest<>();
        UrlParamsBuilder builder = UrlParamsBuilder.build()
                .putToUrl("symbol", symbol)
                .putToUrl("orderId", orderId)
                .putToUrl("origClientOrderId", origClientOrderId);

        request.request = createRequestByGetWithSignature("/fapi/v1/openOrder", builder);

        request.jsonParser = (jsonWrapper -> {
            Order result = new Order();
            result.setClientOrderId(jsonWrapper.getString("clientOrderId"));
            result.setCumQty(jsonWrapper.getBigDecimal("cumQty"));
            result.setCumQuote(jsonWrapper.getBigDecimal("cumQuote"));
            result.setExecutedQty(jsonWrapper.getBigDecimal("executedQty"));
            result.setOrderId(jsonWrapper.getLong("orderId"));
            result.setAvgPrice(jsonWrapper.getBigDecimal("avgPrice"));
            result.setOrigQty(jsonWrapper.getBigDecimal("origQty"));
            result.setPrice(jsonWrapper.getBigDecimal("price"));
            result.setReduceOnly(jsonWrapper.getBoolean("reduceOnly"));
            result.setSide(jsonWrapper.getString("side"));
            result.setPositionSide(jsonWrapper.getString("positionSide"));
            result.setStatus(jsonWrapper.getString("status"));
            result.setStopPrice(jsonWrapper.getBigDecimal("stopPrice"));
            result.setClosePosition(jsonWrapper.getBoolean("closePosition"));
            result.setSymbol(jsonWrapper.getString("symbol"));
            result.setTimeInForce(jsonWrapper.getString("timeInForce"));
            result.setType(jsonWrapper.getString("type"));
            result.setOrigType(jsonWrapper.getString("origType"));
            if (jsonWrapper.containKey("activatePrice")) {
                result.setActivatePrice(jsonWrapper.getBigDecimal("activatePrice"));
            }
            if (jsonWrapper.containKey("priceRate")) {
                result.setActivatePrice(jsonWrapper.getBigDecimal("priceRate"));
            }
            result.setUpdateTime(jsonWrapper.getLong("updateTime"));
            result.setWorkingType(jsonWrapper.getString("workingType"));
            result.setPriceProtect(jsonWrapper.getBoolean("priceProtect"));
            return result;
        });
        return request;
    }

    RestApiRequest<List<Order>> getOpenOrders(String symbol) {
        RestApiRequest<List<Order>> request = new RestApiRequest<>();
        UrlParamsBuilder builder = UrlParamsBuilder.build()
                .putToUrl("symbol", symbol);

        request.request = createRequestByGetWithSignature("/fapi/v1/openOrders", builder);

        request.jsonParser = (jsonWrapper -> {
            List<Order> result = new LinkedList<>();
            JsonWrapperArray dataArray = jsonWrapper.getJsonArray("data");
            dataArray.forEach((item) -> {
                Order o = new Order();
                o.setClientOrderId(jsonWrapper.getString("clientOrderId"));
                o.setCumQty(jsonWrapper.getBigDecimal("cumQty"));
                o.setCumQuote(jsonWrapper.getBigDecimal("cumQuote"));
                o.setExecutedQty(jsonWrapper.getBigDecimal("executedQty"));
                o.setOrderId(jsonWrapper.getLong("orderId"));
                o.setAvgPrice(jsonWrapper.getBigDecimal("avgPrice"));
                o.setOrigQty(jsonWrapper.getBigDecimal("origQty"));
                o.setPrice(jsonWrapper.getBigDecimal("price"));
                o.setReduceOnly(jsonWrapper.getBoolean("reduceOnly"));
                o.setSide(jsonWrapper.getString("side"));
                o.setPositionSide(jsonWrapper.getString("positionSide"));
                o.setStatus(jsonWrapper.getString("status"));
                o.setStopPrice(jsonWrapper.getBigDecimal("stopPrice"));
                o.setClosePosition(jsonWrapper.getBoolean("closePosition"));
                o.setSymbol(jsonWrapper.getString("symbol"));
                o.setTimeInForce(jsonWrapper.getString("timeInForce"));
                o.setType(jsonWrapper.getString("type"));
                o.setOrigType(jsonWrapper.getString("origType"));
                if (jsonWrapper.containKey("activatePrice")) {
                    o.setActivatePrice(jsonWrapper.getBigDecimal("activatePrice"));
                }
                if (jsonWrapper.containKey("priceRate")) {
                    o.setActivatePrice(jsonWrapper.getBigDecimal("priceRate"));
                }
                o.setUpdateTime(jsonWrapper.getLong("updateTime"));
                o.setWorkingType(jsonWrapper.getString("workingType"));
                o.setPriceProtect(jsonWrapper.getBoolean("priceProtect"));
                result.add(o);
            });
            return result;
        });
        return request;
    }

    RestApiRequest<List<Order>> getAllOrders(String symbol, Long orderId, Long startTime, Long endTime, Integer limit) {
        RestApiRequest<List<Order>> request = new RestApiRequest<>();
        UrlParamsBuilder builder = UrlParamsBuilder.build()
                .putToUrl("symbol", symbol)
                .putToUrl("orderId", orderId)
                .putToUrl("startTime", startTime)
                .putToUrl("endTime", endTime)
                .putToUrl("limit", limit);
        request.request = createRequestByGetWithSignature("/fapi/v1/allOrders", builder);

        request.jsonParser = (jsonWrapper -> {
            List<Order> result = new LinkedList<>();
            JsonWrapperArray dataArray = jsonWrapper.getJsonArray("data");
            dataArray.forEach((item) -> {
                Order o = new Order();
                o.setClientOrderId(jsonWrapper.getString("clientOrderId"));
                o.setCumQty(jsonWrapper.getBigDecimal("cumQty"));
                o.setCumQuote(jsonWrapper.getBigDecimal("cumQuote"));
                o.setExecutedQty(jsonWrapper.getBigDecimal("executedQty"));
                o.setOrderId(jsonWrapper.getLong("orderId"));
                o.setAvgPrice(jsonWrapper.getBigDecimal("avgPrice"));
                o.setOrigQty(jsonWrapper.getBigDecimal("origQty"));
                o.setPrice(jsonWrapper.getBigDecimal("price"));
                o.setReduceOnly(jsonWrapper.getBoolean("reduceOnly"));
                o.setSide(jsonWrapper.getString("side"));
                o.setPositionSide(jsonWrapper.getString("positionSide"));
                o.setStatus(jsonWrapper.getString("status"));
                o.setStopPrice(jsonWrapper.getBigDecimal("stopPrice"));
                o.setClosePosition(jsonWrapper.getBoolean("closePosition"));
                o.setSymbol(jsonWrapper.getString("symbol"));
                o.setTimeInForce(jsonWrapper.getString("timeInForce"));
                o.setType(jsonWrapper.getString("type"));
                o.setOrigType(jsonWrapper.getString("origType"));
                if (jsonWrapper.containKey("activatePrice")) {
                    o.setActivatePrice(jsonWrapper.getBigDecimal("activatePrice"));
                }
                if (jsonWrapper.containKey("priceRate")) {
                    o.setActivatePrice(jsonWrapper.getBigDecimal("priceRate"));
                }
                o.setUpdateTime(jsonWrapper.getLong("updateTime"));
                o.setWorkingType(jsonWrapper.getString("workingType"));
                o.setPriceProtect(jsonWrapper.getBoolean("priceProtect"));
                result.add(o);
            });
            return result;
        });
        return request;
    }

    RestApiRequest<List<AccountBalance>> getBalance() {
        RestApiRequest<List<AccountBalance>> request = new RestApiRequest<>();
        UrlParamsBuilder builder = UrlParamsBuilder.build();
        request.request = createRequestByGetWithSignature("/fapi/v2/balance", builder);

        request.jsonParser = (jsonWrapper -> {
            List<AccountBalance> result = new LinkedList<>();
            JsonWrapperArray dataArray = jsonWrapper.getJsonArray("data");
            dataArray.forEach((item) -> {
                AccountBalance element = new AccountBalance();
                element.setAccountAlias(item.getString("accountAlias"));
                element.setAsset(item.getString("asset"));
                element.setBalance(item.getBigDecimal("balance"));
                element.setCrossWalletBalance(item.getBigDecimal("crossWalletBalance"));
                element.setCrossUnPnl(item.getBigDecimal("crossUnPnl"));
                element.setAvailableBalance(item.getBigDecimal("availableBalance"));
                element.setMaxWithdrawAmount(item.getBigDecimal("maxWithdrawAmount"));
                result.add(element);
            });
            return result;
        });
        return request;
    }

    RestApiRequest<AccountInformation> getAccountInformation() {
        RestApiRequest<AccountInformation> request = new RestApiRequest<>();
        UrlParamsBuilder builder = UrlParamsBuilder.build();
        request.request = createRequestByGetWithSignature("/fapi/v2/account", builder);

        request.jsonParser = (jsonWrapper -> {
            AccountInformation result = new AccountInformation();
            result.setFeeTier(jsonWrapper.getBigDecimal("feeTier"));
            result.setCanTrade(jsonWrapper.getBoolean("canTrade"));
            result.setCanDeposit(jsonWrapper.getBoolean("canDeposit"));
            result.setCanWithdraw(jsonWrapper.getBoolean("canWithdraw"));
            result.setUpdateTime(jsonWrapper.getLong("updateTime"));
            result.setTotalInitialMargin(jsonWrapper.getBigDecimal("totalInitialMargin"));
            result.setTotalMaintMargin(jsonWrapper.getBigDecimal("totalMaintMargin"));
            result.setTotalWalletBalance(jsonWrapper.getBigDecimal("totalWalletBalance"));
            result.setTotalUnrealizedProfit(jsonWrapper.getBigDecimal("totalUnrealizedProfit"));
            result.setTotalMarginBalance(jsonWrapper.getBigDecimal("totalMarginBalance"));
            result.setTotalPositionInitialMargin(jsonWrapper.getBigDecimal("totalPositionInitialMargin"));
            result.setTotalOpenOrderInitialMargin(jsonWrapper.getBigDecimal("totalOpenOrderInitialMargin"));
            result.setTotalCrossWalletBalance(jsonWrapper.getBigDecimal("totalCrossWalletBalance"));
            result.setTotalCrossUnPnl(jsonWrapper.getBigDecimal("totalCrossUnPnl"));
            result.setAvailableBalance(jsonWrapper.getBigDecimal("availableBalance"));
            result.setMaxWithdrawAmount(jsonWrapper.getBigDecimal("maxWithdrawAmount"));

            List<Asset> assetList = new LinkedList<>();
            JsonWrapperArray assetArray = jsonWrapper.getJsonArray("assets");
            assetArray.forEach((item) -> {
                Asset element = new Asset();
                element.setAsset(item.getString("asset"));
                element.setWalletBalance(item.getBigDecimal("walletBalance"));
                element.setUnrealizedProfit(item.getBigDecimal("unrealizedProfit"));
                element.setMarginBalance(item.getBigDecimal("marginBalance"));
                element.setMaintMargin(item.getBigDecimal("maintMargin"));
                element.setInitialMargin(item.getBigDecimal("initialMargin"));
                element.setPositionInitialMargin(item.getBigDecimal("positionInitialMargin"));
                element.setOpenOrderInitialMargin(item.getBigDecimal("openOrderInitialMargin"));
                element.setCrossWalletBalance(item.getBigDecimal("crossWalletBalance"));
                element.setCrossUnPnl(item.getBigDecimal("crossUnPnl"));
                element.setAvailableBalance(item.getBigDecimal("availableBalance"));
                element.setMaxWithdrawAmount(item.getBigDecimal("maxWithdrawAmount"));
                assetList.add(element);
            });
            result.setAssets(assetList);

            List<Position> positionList = new LinkedList<>();
            JsonWrapperArray positionArray = jsonWrapper.getJsonArray("positions");
            positionArray.forEach((item) -> {
                Position element = new Position();
                element.setSymbol(item.getString("symbol"));
                element.setInitialMargin(item.getBigDecimal("initialMargin"));
                element.setMaintMargin(item.getBigDecimal("maintMargin"));
                element.setUnrealizedProfit(item.getBigDecimal("unrealizedProfit"));
                element.setPositionInitialMargin(item.getBigDecimal("positionInitialMargin"));
                element.setOpenOrderInitialMargin(item.getBigDecimal("openOrderInitialMargin"));
                element.setLeverage(item.getBigDecimal("leverage"));
                element.setIsolated(item.getBoolean("isolated"));
                element.setEntryPrice(item.getString("entryPrice"));
                element.setMaxNotional(item.getString("maxNotional"));
                element.setPositionSide(item.getString("positionSide"));
                element.setPositionAmt(item.getBigDecimal("positionAmt"));
                positionList.add(element);
            });
            result.setPositions(positionList);
            return result;
        });
        return request;
    }

    RestApiRequest<Leverage> changeInitialLeverage(String symbol, Integer leverage) {
        RestApiRequest<Leverage> request = new RestApiRequest<>();
        UrlParamsBuilder builder = UrlParamsBuilder.build()
                .putToUrl("symbol", symbol)
                .putToUrl("leverage", leverage);
        request.request = createRequestByPostWithSignature("/fapi/v1/leverage", builder);

        request.jsonParser = (jsonWrapper -> {
            Leverage result = new Leverage();
            result.setLeverage(jsonWrapper.getBigDecimal("leverage"));
            if(jsonWrapper.getString("maxNotionalValue").equalsIgnoreCase("INF")) {
                result.setMaxNotionalValue(Double.POSITIVE_INFINITY);
            } else {
                result.setMaxNotionalValue(jsonWrapper.getDouble("maxNotionalValue"));
            }
            result.setSymbol(jsonWrapper.getString("symbol"));
            return result;
        });
        return request;
    }

    RestApiRequest<List<PositionRisk>> getPositionRisk(String symbol) {
        RestApiRequest<List<PositionRisk>> request = new RestApiRequest<>();
        UrlParamsBuilder builder = UrlParamsBuilder.build()
        .putToUrl("symbol", symbol);
        request.request = createRequestByGetWithSignature("/fapi/v2/positionRisk", builder);

        request.jsonParser = (jsonWrapper -> {
            List<PositionRisk> result = new LinkedList<>();
            JsonWrapperArray dataArray = jsonWrapper.getJsonArray("data");
            dataArray.forEach((item) -> {
                PositionRisk element = new PositionRisk();
                element.setEntryPrice(item.getBigDecimal("entryPrice"));
                element.setMarginType(item.getString("marginType"));
                element.setAutoAddMargin(item.getBoolean("isAutoAddMargin"));
                element.setLeverage(item.getBigDecimal("leverage"));
                element.setMaxNotionalValue(item.getDouble("maxNotionalValue"));
                element.setLiquidationPrice(item.getBigDecimal("liquidationPrice"));
                element.setMarkPrice(item.getBigDecimal("markPrice"));
                element.setPositionAmt(item.getBigDecimal("positionAmt"));
                element.setSymbol(item.getString("symbol"));
                element.setIsolatedMargin(item.getString("isolatedMargin"));
                element.setPositionSide(item.getString("positionSide"));
                element.setUnrealizedProfit(item.getBigDecimal("unRealizedProfit"));
                result.add(element);
            });
            return result;
        });
        return request;
    }

    RestApiRequest<List<MyTrade>> getAccountTrades(String symbol, Long startTime, Long endTime, 
            Long fromId, Integer limit) {
        RestApiRequest<List<MyTrade>> request = new RestApiRequest<>();
        UrlParamsBuilder builder = UrlParamsBuilder.build()
                .putToUrl("symbol", symbol)
                .putToUrl("startTime", startTime)
                .putToUrl("endTime", endTime)
                .putToUrl("fromId", fromId)
                .putToUrl("limit", limit);
        request.request = createRequestByGetWithSignature("/fapi/v1/userTrades", builder);

        request.jsonParser = (jsonWrapper -> {
            List<MyTrade> result = new LinkedList<>();
            JsonWrapperArray dataArray = jsonWrapper.getJsonArray("data");
            dataArray.forEach((item) -> {
                MyTrade element = new MyTrade();
                element.setIsBuyer(item.getBoolean("buyer"));
                element.setCommission(item.getBigDecimal("commission"));
                element.setCommissionAsset(item.getString("commissionAsset"));
                element.setCounterPartyId(item.getLongOrDefault("counterPartyId", 0));
                element.setIsMaker(item.getBoolean("maker"));
                element.setOrderId(item.getLong("orderId"));
                element.setPrice(item.getBigDecimal("price"));
                element.setQty(item.getBigDecimal("qty"));
                element.setQuoteQty(item.getBigDecimal("quoteQty"));
                element.setRealizedPnl(item.getBigDecimal("realizedPnl"));
                element.setSide(item.getString("side"));
                element.setPositionSide(item.getString("positionSide"));
                element.setSymbol(item.getString("symbol"));
                element.setTime(item.getLong("time"));
                result.add(element);
            });
            return result;
        });
        return request;
    }

    RestApiRequest<List<Income>> getIncomeHistory(String symbol, IncomeType incomeType, Long startTime, Long endTime, 
            Integer limit) {
        RestApiRequest<List<Income>> request = new RestApiRequest<>();
        UrlParamsBuilder builder = UrlParamsBuilder.build()
                .putToUrl("symbol", symbol)
                .putToUrl("incomeType", incomeType)
                .putToUrl("startTime", startTime)
                .putToUrl("endTime", endTime)
                .putToUrl("limit", limit);
        request.request = createRequestByGetWithSignature("/fapi/v1/income", builder);

        request.jsonParser = (jsonWrapper -> {
            List<Income> result = new LinkedList<>();
            JsonWrapperArray dataArray = jsonWrapper.getJsonArray("data");
            dataArray.forEach((item) -> {
                Income element = new Income();
                element.setSymbol(item.getString("symbol"));
                element.setIncomeType(item.getString("incomeType"));
                element.setIncome(item.getBigDecimal("income"));
                element.setAsset(item.getString("asset"));
                element.setInfo(item.getString("info"));
                element.setTime(item.getLong("time"));
                element.setTranId(item.getLong("tranId"));
                element.setTradeId(item.getString("tradeId"));

                result.add(element);
            });
            return result;
        });
        return request;
    }

    RestApiRequest<String> startUserDataStream() {
        RestApiRequest<String> request = new RestApiRequest<>();
        UrlParamsBuilder builder = UrlParamsBuilder.build();

        request.request = createRequestByPostWithSignature("/fapi/v1/listenKey", builder);

        request.jsonParser = (jsonWrapper -> {
            String result = jsonWrapper.getString("listenKey");
            return result;
        });
        return request;
    }

    RestApiRequest<String> keepUserDataStream(String listenKey) {
        RestApiRequest<String> request = new RestApiRequest<>();
        UrlParamsBuilder builder = UrlParamsBuilder.build()
                .putToUrl("listenKey", listenKey);

        request.request = createRequestByPutWithSignature("/fapi/v1/listenKey", builder);

        request.jsonParser = (jsonWrapper -> {
            String result = "Ok";
            return result;
        });
        return request;
    }

    RestApiRequest<String> closeUserDataStream(String listenKey) {
        RestApiRequest<String> request = new RestApiRequest<>();
        UrlParamsBuilder builder = UrlParamsBuilder.build()
                .putToUrl("listenKey", listenKey);

        request.request = createRequestByDeleteWithSignature("/fapi/v1/listenKey", builder);

        request.jsonParser = (jsonWrapper -> {
            String result = "Ok";
            return result;
        });
        return request;
    }

    RestApiRequest<OpenInterest> getOpenInterest(String symbol) {
        RestApiRequest<OpenInterest> request = new RestApiRequest<>();
        UrlParamsBuilder builder = UrlParamsBuilder.build()
                .putToUrl("symbol", symbol);


        request.request = createRequestByGetWithSignature("/futures/data/openInterest", builder);

        request.jsonParser = (jsonWrapper -> {
            OpenInterest result = new OpenInterest();
            JsonWrapperArray dataArray = jsonWrapper.getJsonArray("data");
            dataArray.forEach((item) -> {
                result.setSymbol(item.getString("symbol"));
                result.setOpenInterest(item.getBigDecimal("openInterest"));
                result.setTimestamp(item.getLong("timestamp"));
            });
            return result;
        });
        return request;
    }

    RestApiRequest<List<OpenInterestStat>> getOpenInterestStat(String symbol, PeriodType period, Long startTime, Long endTime, Long limit) {
        RestApiRequest<List<OpenInterestStat>> request = new RestApiRequest<>();
        UrlParamsBuilder builder = UrlParamsBuilder.build()
                .putToUrl("symbol", symbol)
                .putToUrl("period", period.getCode())
                .putToUrl("startTime", startTime)
                .putToUrl("endTime", endTime)
                .putToUrl("limit", limit);
        
        
//        request.request = createRequestByGetWithSignature("/gateway-api//v1/public/future/data/openInterestHist", builder);
        request.request = createRequestByGetWithSignature("/futures/data/openInterestHist", builder);

        request.jsonParser = (jsonWrapper -> {
            List<OpenInterestStat> result = new LinkedList<>();
            JsonWrapperArray dataArray = jsonWrapper.getJsonArray("data");
            dataArray.forEach((item) -> {
                OpenInterestStat element = new OpenInterestStat();
                element.setSymbol(item.getString("symbol"));
                element.setSumOpenInterest(item.getBigDecimal("sumOpenInterest"));
                element.setSumOpenInterestValue(item.getBigDecimal("sumOpenInterestValue"));
                element.setTimestamp(item.getLong("timestamp"));

                result.add(element);
            });
            return result;
        });
        return request;
    }

    RestApiRequest<List<CommonLongShortRatio>> getTopTraderAccountRatio(String symbol, PeriodType period, Long startTime, Long endTime, Long limit) {
        RestApiRequest<List<CommonLongShortRatio>> request = new RestApiRequest<>();
        UrlParamsBuilder builder = UrlParamsBuilder.build()
                .putToUrl("symbol", symbol)
                .putToUrl("period", period.getCode())
                .putToUrl("startTime", startTime)
                .putToUrl("endTime", endTime)
                .putToUrl("limit", limit);


//        request.request = createRequestByGetWithSignature("/gateway-api//v1/public/future/data/topLongShortAccountRatio", builder);
        request.request = createRequestByGetWithSignature("/futures/data/topLongShortAccountRatio", builder);

        request.jsonParser = (jsonWrapper -> {
            List<CommonLongShortRatio> result = new LinkedList<>();
            JsonWrapperArray dataArray = jsonWrapper.getJsonArray("data");
            dataArray.forEach((item) -> {
                CommonLongShortRatio element = new CommonLongShortRatio();
                element.setSymbol(item.getString("symbol"));
                element.setLongAccount(item.getBigDecimal("longAccount"));
                element.setLongShortRatio(item.getBigDecimal("longShortRatio"));
                element.setShortAccount(item.getBigDecimal("shortAccount"));
                element.setTimestamp(item.getLong("timestamp"));

                result.add(element);
            });
            return result;
        });
        return request;
    }

    RestApiRequest<List<CommonLongShortRatio>> getTopTraderPositionRatio(String symbol, PeriodType period, Long startTime, Long endTime, Long limit) {
        RestApiRequest<List<CommonLongShortRatio>> request = new RestApiRequest<>();
        UrlParamsBuilder builder = UrlParamsBuilder.build()
                .putToUrl("symbol", symbol)
                .putToUrl("period", period.getCode())
                .putToUrl("startTime", startTime)
                .putToUrl("endTime", endTime)
                .putToUrl("limit", limit);


//        request.request = createRequestByGetWithSignature("/gateway-api//v1/public/future/data/topLongShortPositionRatio", builder);
        request.request = createRequestByGetWithSignature("/futures/data/topLongShortPositionRatio", builder);

        request.jsonParser = (jsonWrapper -> {
            List<CommonLongShortRatio> result = new LinkedList<>();
            JsonWrapperArray dataArray = jsonWrapper.getJsonArray("data");
            dataArray.forEach((item) -> {
                CommonLongShortRatio element = new CommonLongShortRatio();
                element.setSymbol(item.getString("symbol"));
                element.setLongAccount(item.getBigDecimal("longAccount"));
                element.setLongShortRatio(item.getBigDecimal("longShortRatio"));
                element.setShortAccount(item.getBigDecimal("shortAccount"));
                element.setTimestamp(item.getLong("timestamp"));

                result.add(element);
            });
            return result;
        });
        return request;
    }

    RestApiRequest<List<CommonLongShortRatio>> getGlobalAccountRatio(String symbol, PeriodType period, Long startTime, Long endTime, Long limit) {
        RestApiRequest<List<CommonLongShortRatio>> request = new RestApiRequest<>();
        UrlParamsBuilder builder = UrlParamsBuilder.build()
                .putToUrl("symbol", symbol)
                .putToUrl("period", period.getCode())
                .putToUrl("startTime", startTime)
                .putToUrl("endTime", endTime)
                .putToUrl("limit", limit);


//        request.request = createRequestByGetWithSignature("/gateway-api//v1/public/future/data/globalLongShortAccountRatio", builder);
        request.request = createRequestByGetWithSignature("/futures/data/globalLongShortAccountRatio", builder);

        request.jsonParser = (jsonWrapper -> {
            List<CommonLongShortRatio> result = new LinkedList<>();
            JsonWrapperArray dataArray = jsonWrapper.getJsonArray("data");
            dataArray.forEach((item) -> {
                CommonLongShortRatio element = new CommonLongShortRatio();
                element.setSymbol(item.getString("symbol"));
                element.setLongAccount(item.getBigDecimal("longAccount"));
                element.setLongShortRatio(item.getBigDecimal("longShortRatio"));
                element.setShortAccount(item.getBigDecimal("shortAccount"));
                element.setTimestamp(item.getLong("timestamp"));

                result.add(element);
            });
            return result;
        });
        return request;
    }

    RestApiRequest<List<TakerLongShortStat>> getTakerLongShortRatio(String symbol, PeriodType period, Long startTime, Long endTime, Long limit) {
        RestApiRequest<List<TakerLongShortStat>> request = new RestApiRequest<>();
        UrlParamsBuilder builder = UrlParamsBuilder.build()
                .putToUrl("symbol", symbol)
                .putToUrl("period", period.getCode())
                .putToUrl("startTime", startTime)
                .putToUrl("endTime", endTime)
                .putToUrl("limit", limit);


//        request.request = createRequestByGetWithSignature("/gateway-api//v1/public/future/data/globalLongShortAccountRatio", builder);
        request.request = createRequestByGetWithSignature("/futures/data/takerlongshortRatio", builder);

        request.jsonParser = (jsonWrapper -> {
            List<TakerLongShortStat> result = new LinkedList<>();
            JsonWrapperArray dataArray = jsonWrapper.getJsonArray("data");
            dataArray.forEach((item) -> {
                TakerLongShortStat element = new TakerLongShortStat();
                element.setBuySellRatio(item.getBigDecimal("buySellRatio"));
                element.setSellVol(item.getBigDecimal("sellVol"));
                element.setBuyVol(item.getBigDecimal("buyVol"));
                element.setTimestamp(item.getLong("timestamp"));

                result.add(element);
            });
            return result;
        });
        return request;
    }

}
