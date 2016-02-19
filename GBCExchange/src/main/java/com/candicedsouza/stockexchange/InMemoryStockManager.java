package com.candicedsouza.stockexchange;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.candicedsouza.stockexchange.entities.Stock;
import com.candicedsouza.stockexchange.interfaces.StockManager;

public class InMemoryStockManager implements StockManager{
	private Map<String, Stock> registeredStocks = new HashMap<String, Stock>();
	private Map<String, Double> stockDividend = new HashMap<String, Double>();
	
	public InMemoryStockManager(Set<Stock> stocks){
		this(stocks, new HashMap<String, Double>());
	}
	
	public InMemoryStockManager(Set<Stock> stocks, Map<String, Double> stockDividend){
		for(Stock stock : stocks){
			String stockSymbol = stock.getStockSymbol();
			registeredStocks.put(stockSymbol, stock);
			Double dividend = stockDividend.get(stockSymbol);
			if(dividend==null){
				this.stockDividend.put(stockSymbol, 0.0);
			}else{
				this.stockDividend.put(stockSymbol, dividend);
			}
		}
	}
	
	public boolean isStockRegistered(String stockSymbol){
		return registeredStocks.containsKey(stockSymbol);
	}

	public Stock getStock(String stockSymbol){
		return registeredStocks.get(stockSymbol);
	}

	public Set<String> getAllRegisteredStockSymbols() {
		return registeredStocks.keySet();
	}
	
	public double getLastDividend(String stockSymbol){
		Double dividend = stockDividend.get(stockSymbol);
		return dividend==null?0.0:dividend;
	}
	
	public void updateDividend(String stockSymbol, double newDividend){
		stockDividend.put(stockSymbol, newDividend);
	}
}
