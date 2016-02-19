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

import com.candicedsouza.stockexchange.DefaultStockAnalyzer;
import com.candicedsouza.stockexchange.InMemoryStockManager;
import com.candicedsouza.stockexchange.InMemoryStockTradeTracker;
import com.candicedsouza.stockexchange.entities.Stock;
import com.candicedsouza.stockexchange.entities.StockType;
import com.candicedsouza.stockexchange.entities.Trade;
import com.candicedsouza.stockexchange.entities.TradeType;
import com.candicedsouza.stockexchange.interfaces.StockAnalyser;

public class GBCEStockAnalyzerTest {

	private StockAnalyser systemUnderTest;
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
		InMemoryStockTradeTracker tradeTracker = new InMemoryStockTradeTracker();
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
		systemUnderTest = new DefaultStockAnalyzer(new InMemoryStockManager(stocks, stockDivinend), tradeTracker);
	}
	
	@Test(expected = IllegalStateException.class)
	public void testFailedCalculateDividendYieldRatio(){
		systemUnderTest.calculateDividendYield("POP", 0.0);
	}
	
	@Test
	public void testCalculateDividendYield(){
		Assert.assertEquals( 8/20.89, systemUnderTest.calculateDividendYield("POP", 20.89), 0.01);
		Assert.assertEquals( 23/40.89, systemUnderTest.calculateDividendYield("ALE", 40.89), 0.01);
		Assert.assertEquals( (2*100)/48.89, systemUnderTest.calculateDividendYield("GIN", 48.89), 0.01);
		Assert.assertEquals( 13/27.789, systemUnderTest.calculateDividendYield("JOE", 27.789), 0.01);
	}
	
	@Test
	public void testCalculatePERatio(){
		Assert.assertEquals( 20.89/8, systemUnderTest.calculatePERatio("POP", 20.89), 0.01);
		Assert.assertEquals( 40.89/23, systemUnderTest.calculatePERatio("ALE", 40.89), 0.01);
		Assert.assertEquals( 48.89/8, systemUnderTest.calculatePERatio("GIN", 48.89), 0.01);
		Assert.assertEquals( 27.789/13, systemUnderTest.calculatePERatio("JOE", 27.789), 0.01);
	}
	
	@Test(expected = IllegalStateException.class)
	public void testFailedCalculatePERatio(){
		systemUnderTest.calculatePERatio("TEA", 8.35);
	}
	
	@Test
	public void testCalculateVolumeWeightedStockPrice(){
		Assert.assertEquals( 6.928, systemUnderTest.calculateVolumeWeightedStockPrice("TEA", 10, TimeUnit.MINUTES), 0.01);
		Assert.assertEquals( 5, systemUnderTest.calculateVolumeWeightedStockPrice("POP", 15, TimeUnit.MINUTES), 0.01);
		Assert.assertEquals( 7.47, systemUnderTest.calculateVolumeWeightedStockPrice("ALE", 2, TimeUnit.DAYS), 0.01);
		Assert.assertEquals( 18.1, systemUnderTest.calculateVolumeWeightedStockPrice("GIN", 5, TimeUnit.MINUTES), 0.01);
		Assert.assertEquals(5.21, systemUnderTest.calculateVolumeWeightedStockPrice("JOE",1, TimeUnit.MINUTES), 0.01);
	}

	private Date getTimestamp(Date startTime, int sourceDuration, TimeUnit unit){
		long timeStamp = TimeUnit.MILLISECONDS.convert(sourceDuration, unit);
		long timeBack = (startTime).getTime() - timeStamp;
		return new Date(timeBack);
	}

}
