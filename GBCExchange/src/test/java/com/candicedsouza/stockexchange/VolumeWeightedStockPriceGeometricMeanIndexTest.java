package com.candicedsouza.stockexchange;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.candicedsouza.stockexchange.entities.Stock;
import com.candicedsouza.stockexchange.entities.StockType;
import com.candicedsouza.stockexchange.entities.Trade;
import com.candicedsouza.stockexchange.entities.TradeType;
import com.candicedsouza.stockexchange.interfaces.IndexCalculator;
import com.candicedsouza.stockexchange.interfaces.StockAnalyser;
import com.candicedsouza.stockexchange.interfaces.StockManager;

public class VolumeWeightedStockPriceGeometricMeanIndexTest {

	private IndexCalculator systemUnderTest;
	private StockAnalyser analyser;
	private StockManager manager;
	private InMemoryStockTradeTracker tradeTracker;

	@Before 
	public void setup(){
		Set<Stock> stocks = new HashSet<Stock>();
		stocks.add(new Stock("TEA", StockType.COMMON, 0, 100));
		stocks.add(new Stock("POP", StockType.COMMON, 0, 100));
		stocks.add(new Stock("ALE", StockType.COMMON, 0, 60));
		stocks.add(new Stock("GIN", StockType.PREFERRED, 2, 100));
		stocks.add(new Stock("JOE", StockType.COMMON, 0, 250));
		
		Map<String, Double> stockDivinend =  new HashMap<String, Double>();
		stockDivinend.put("POP", 8.0);
		stockDivinend.put("ALE", 23.0);
		stockDivinend.put("GIN", 8.0);
		stockDivinend.put("JOE", 13.0);
		
		
		Date currentDate = new Date();
		tradeTracker = new InMemoryStockTradeTracker();
		tradeTracker.trackTrade("TEA",new Trade(getTimestamp(currentDate, 9, TimeUnit.MINUTES), 10, TradeType.BUY, 10.36));
		tradeTracker.trackTrade("TEA",new Trade(getTimestamp(currentDate, 6, TimeUnit.MINUTES), 15, TradeType.BUY, 5.36));
		tradeTracker.trackTrade("TEA",new Trade(getTimestamp(currentDate, 7, TimeUnit.MINUTES), 5, TradeType.SELL, 2.6));
		tradeTracker.trackTrade("TEA",new Trade(getTimestamp(currentDate, 3, TimeUnit.MINUTES), 20, TradeType.BUY, 7.47));
		tradeTracker.trackTrade("TEA",new Trade(getTimestamp(currentDate, 11, TimeUnit.MINUTES), 20, TradeType.SELL, 7.47));
		tradeTracker.trackTrade("TEA",new Trade(getTimestamp(currentDate,15, TimeUnit.MINUTES), 20, TradeType.SELL, 7.47));
		
		
		tradeTracker.trackTrade("POP",new Trade(getTimestamp(currentDate,15, TimeUnit.MINUTES), 20, TradeType.SELL, 7.47));
		tradeTracker.trackTrade("POP",new Trade(getTimestamp(currentDate,15, TimeUnit.MINUTES), 12, TradeType.SELL, 5));
		tradeTracker.trackTrade("POP",new Trade(getTimestamp(currentDate,1, TimeUnit.MINUTES), 12, TradeType.SELL, 5));
		
		tradeTracker.trackTrade("ALE",new Trade(getTimestamp(currentDate,15, TimeUnit.DAYS), 20, TradeType.SELL, 7.47));
		tradeTracker.trackTrade("ALE",new Trade(getTimestamp(currentDate,1, TimeUnit.DAYS), 20, TradeType.SELL, 7.47));
		
		tradeTracker.trackTrade("GIN",new Trade(getTimestamp(currentDate,6, TimeUnit.MINUTES), 20, TradeType.SELL, 7.47));
		tradeTracker.trackTrade("GIN",new Trade(getTimestamp(currentDate,4, TimeUnit.MINUTES), 10, TradeType.SELL, 4.32));
		tradeTracker.trackTrade("GIN",new Trade(getTimestamp(currentDate,3, TimeUnit.MINUTES), 2, TradeType.SELL, 87));
		
		
		tradeTracker.trackTrade("JOE",new Trade(getTimestamp(currentDate,2, TimeUnit.MINUTES), 20, TradeType.SELL, 7.47));
		tradeTracker.trackTrade("JOE",new Trade(currentDate, 2, TradeType.SELL, 5.21));
		manager = new InMemoryStockManager(stocks, stockDivinend);
		analyser = new DefaultStockAnalyzer(manager , tradeTracker);
		systemUnderTest = new VolumeWeightedStockPriceGeometricMeanIndex(analyser);
	}

	@Test
	public void testIndexCalulation() {
		double innerVal = 1;
		int nonZeroStockTradeCounter = 0;
		for (String stockSymbol : manager.getAllRegisteredStockSymbols()) {
			if (analyser.calculateVolumeWeightedStockPrice(stockSymbol, 5, TimeUnit.MINUTES) > 0) {
				innerVal = innerVal * analyser.calculateVolumeWeightedStockPrice(stockSymbol, 5, TimeUnit.MINUTES);
			}
			
			if(tradeTracker.getRecentTradesByTimeDuration(stockSymbol, 5, TimeUnit.MINUTES).size()>0){
				nonZeroStockTradeCounter++;
			}
		}
		double indexValue = Math.pow(innerVal, 1.0/nonZeroStockTradeCounter);
		System.out.println("Inner Val: " + innerVal);
		System.out.println("INDEX: " + systemUnderTest.calculateIndex(manager.getAllRegisteredStockSymbols()));
		Assert.assertEquals(indexValue, systemUnderTest.calculateIndex(manager.getAllRegisteredStockSymbols()), 0.01);
	}

	private Date getTimestamp(Date startTime, int sourceDuration, TimeUnit unit) {
		long timeStamp = TimeUnit.MILLISECONDS.convert(sourceDuration, unit);
		long timeBack = (startTime).getTime() - timeStamp;
		return new Date(timeBack);
	}
}
