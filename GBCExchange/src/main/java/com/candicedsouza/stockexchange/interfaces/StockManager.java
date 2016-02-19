package com.candicedsouza.stockexchange.interfaces;

import java.util.Set;

import com.candicedsouza.stockexchange.entities.Stock;

public interface StockManager {
	public Stock getStock(String stockSymbol);
	public Set<String> getAllRegisteredStockSymbols();
	public boolean isStockRegistered(String stockSymbol);
	public double getLastDividend(String stockSymbol);
	public void updateDividend(String stockSymbol, double newDividend);
}
