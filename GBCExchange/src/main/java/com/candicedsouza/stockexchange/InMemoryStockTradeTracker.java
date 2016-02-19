package com.candicedsouza.stockexchange;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.candicedsouza.stockexchange.entities.Trade;
import com.candicedsouza.stockexchange.interfaces.TradeTracker;

public class InMemoryStockTradeTracker implements TradeTracker{

	private Map<String, List<Trade>> stockTradeMap;
	
	public InMemoryStockTradeTracker() {
		this.stockTradeMap = new HashMap<String, List<Trade>>();
	}
	
	public void trackTrade(String stockSymbol, Trade trade){
		if(!stockTradeMap.containsKey(stockSymbol)){
			stockTradeMap.put(stockSymbol, new ArrayList<Trade>());
		}
		List<Trade> tradeList = stockTradeMap.get(stockSymbol);
		
		tradeList.add(trade);
	}
	
	public List<Trade> getRecentTradesByTimeDuration(String stockSymbol, int sourceDuration, TimeUnit sourceUnit){
		List<Trade> list = new LinkedList<Trade>();
		long timeInMilliSeconds = TimeUnit.MILLISECONDS.convert(sourceDuration, sourceUnit);

		long cutoffTime = (new Date()).getTime() - timeInMilliSeconds;
		Date cutoffTimeStamp = new Date(cutoffTime);
		
		for(Trade entry : stockTradeMap.get(stockSymbol)){
			if(entry.getTimestamp().after(cutoffTimeStamp)){
				list.add(entry);
			}
		}
		
		return list;
	}
	
	public List<Trade> getAllTrades(String stockSymbol){
		return stockTradeMap.get(stockSymbol);
	}
}
