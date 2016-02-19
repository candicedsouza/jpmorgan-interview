package com.candicedsouza.stockexchange.interfaces;

import java.util.concurrent.TimeUnit;

public interface StockAnalyser {
	
	double calculatePERatio(String stockSymbol, double price);
	double calculateDividendYield(String stockSymbol, double price);
	double calculateVolumeWeightedStockPrice(String stockSymbol, int sourceDuration, TimeUnit sourceUnit);
	
}
