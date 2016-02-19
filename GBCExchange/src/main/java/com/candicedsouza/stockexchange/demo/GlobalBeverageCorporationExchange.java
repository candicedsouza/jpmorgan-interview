package com.candicedsouza.stockexchange.demo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.candicedsouza.stockexchange.entities.Trade;
import com.candicedsouza.stockexchange.entities.TradeType;
import com.candicedsouza.stockexchange.interfaces.IndexCalculator;
import com.candicedsouza.stockexchange.interfaces.StockAnalyser;
import com.candicedsouza.stockexchange.interfaces.StockManager;
import com.candicedsouza.stockexchange.interfaces.TradeTracker;

public class GlobalBeverageCorporationExchange{

	
	private StockManager stockManager;
	private StockAnalyser analyser;
	private TradeTracker tradeTracker;
	private IndexCalculator index;
	
	public GlobalBeverageCorporationExchange(StockManager stockManager, 
			TradeTracker tradeTracker,
			StockAnalyser analyser,
			IndexCalculator index){

		this.stockManager = stockManager;
		this.tradeTracker = tradeTracker;
		this.analyser = analyser;
		this.index = index;
	}
	
	public void buy(String stockSymbol, int quantity, double price){
		buy(stockSymbol, quantity, price, new Date());
	}
	
	public void buy(String stockSymbol, int quantity, double price, Date timestamp){
		tradeTracker.trackTrade(stockSymbol, new Trade(timestamp, quantity, TradeType.BUY, price));
	}
	
	public void sell(String stockSymbol, int quantity, double price){
		sell(stockSymbol, quantity, price, new Date());
	}
	
	public void sell(String stockSymbol, int quantity, double price, Date timestamp){
		tradeTracker.trackTrade(stockSymbol, new Trade(timestamp, quantity, TradeType.SELL, price));
	}
	
	public double calculateGBCEAllShareIndex(){
		return index.calculateIndex(stockManager.getAllRegisteredStockSymbols());
	}

	public List<Trade> listAllRecentTradesByMinutes(int minutes){
		return listAllRecentTradesByDuration(minutes, TimeUnit.MINUTES);
	}
	
	public List<Trade> listAllRecentTradesByDuration(int sourceDuartion, TimeUnit unit){
		List<Trade> trades = new ArrayList<Trade>();
		for(String stockSymbol : stockManager.getAllRegisteredStockSymbols()){
				trades.addAll(tradeTracker.getRecentTradesByTimeDuration(stockSymbol, sourceDuartion, unit));
		}
	
		System.out.println("All trades in last : " +  sourceDuartion + " " + unit.toString());
		for(Trade trade :trades){
			System.out.println(trade);
		}
		return trades;
	}
	
	public void analyseStock(String stockSymbol, double price){
		System.out.println("Analysis for stock : " + stockSymbol + " at price : " + price);
		System.out.println("Dividend Yield : " + analyser.calculateDividendYield(stockSymbol, price));
		System.out.println("PE Ratio : " + analyser.calculatePERatio(stockSymbol, price));
		System.out.println("List of trades in last 24 hours");
		for(Trade trade : tradeTracker.getRecentTradesByTimeDuration(stockSymbol, 1, TimeUnit.DAYS)){
			System.out.println(trade);
		}
		System.out.println("Calculated Volume Weighted Stock Price based on trades in past 5 minutes :" + analyser.calculateVolumeWeightedStockPrice(stockSymbol, 5, TimeUnit.MINUTES));
	}
	
	public void declareDividendForStock(String stockSymbol, double price){
		stockManager.updateDividend(stockSymbol, price);
	}
}
