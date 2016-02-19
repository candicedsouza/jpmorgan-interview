package com.candicedsouza.stockexchange.interfaces;

import java.util.List;
import java.util.concurrent.TimeUnit;

import com.candicedsouza.stockexchange.entities.Trade;

public interface TradeTracker {
	void trackTrade(String stockSymbol, Trade trade);
	List<Trade> getAllTrades(String stockSymbol);
	List<Trade> getRecentTradesByTimeDuration(String stockSymbol, int sourceDuration, TimeUnit sourceUnit);
}
