package com.candicedsouza.stockexchange;

import java.util.List;
import java.util.concurrent.TimeUnit;

import com.candicedsouza.stockexchange.entities.Stock;
import com.candicedsouza.stockexchange.entities.StockType;
import com.candicedsouza.stockexchange.entities.Trade;
import com.candicedsouza.stockexchange.interfaces.StockAnalyser;
import com.candicedsouza.stockexchange.interfaces.StockManager;
import com.candicedsouza.stockexchange.interfaces.TradeTracker;

public class DefaultStockAnalyzer implements StockAnalyser{
	
	private StockManager manager;
	private TradeTracker tradeTracker;
	
	public DefaultStockAnalyzer(StockManager manager, TradeTracker tradeTracker) {
		this.manager = manager;
		this.tradeTracker = tradeTracker;
	}
	
	public double calculateDividendYield(String stockSymbol, double price){
		if(price == 0.0){
			throw new IllegalStateException("Stock price is 0 cannot calculate Dividend Yield");
		}
		Stock stock = manager.getStock(stockSymbol);
		if(stock.getType() == StockType.PREFERRED){
			return (stock.getFixedDividend() * stock.getParValue())/price;
		}
		return manager.getLastDividend(stockSymbol)/price;
	}
	
	public double calculatePERatio(String stockSymbol, double price){
		double lastDividend = manager.getLastDividend(stockSymbol);
		if(lastDividend == 0.0){
			throw new IllegalStateException("Stock has no last dividend cannot compute its PE ratio");
		}
		return price/lastDividend;
	}

	public double calculateVolumeWeightedStockPrice(String stockSymbol, int sourceDuration, TimeUnit sourceUnit){
		double sumTradedPriceQuantity = 0;
		double sumQuantity = 0;
		List<Trade> recentTrades = tradeTracker.getRecentTradesByTimeDuration(stockSymbol, sourceDuration, sourceUnit);
		
		for(Trade trade : recentTrades){
			sumTradedPriceQuantity += (trade.getPrice() * trade.getQuantity());
			sumQuantity += trade.getQuantity();
		}
		
		return recentTrades.size()==0 ? 0.0 : (sumTradedPriceQuantity)/sumQuantity;
	}
	
	
}
