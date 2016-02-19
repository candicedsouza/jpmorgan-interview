package com.candicedsouza.stockexchange;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import com.candicedsouza.stockexchange.interfaces.IndexCalculator;
import com.candicedsouza.stockexchange.interfaces.StockAnalyser;

public class VolumeWeightedStockPriceGeometricMeanIndex implements IndexCalculator{

	private StockAnalyser analyser;
	
	public VolumeWeightedStockPriceGeometricMeanIndex(StockAnalyser analyser) {
		this.analyser = analyser;
	}

	public double calculateIndex(Set<String> allStockSymbols) {
		double productVolumeWeighted = 1;
		int raisedTo = 0;
		double volumeWeightedStockPrice = 0.0;
		for(String stockSymbol : allStockSymbols){
			volumeWeightedStockPrice = analyser.calculateVolumeWeightedStockPrice(stockSymbol, 5, TimeUnit.MINUTES);
			if(volumeWeightedStockPrice != 0.0){
				productVolumeWeighted = productVolumeWeighted*volumeWeightedStockPrice;
				raisedTo++;
			}
		}
		return Math.pow(productVolumeWeighted, 1.0/raisedTo);
	}

}
