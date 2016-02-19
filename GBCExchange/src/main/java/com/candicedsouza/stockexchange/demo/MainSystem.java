package com.candicedsouza.stockexchange.demo;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import com.candicedsouza.stockexchange.DefaultStockAnalyzer;
import com.candicedsouza.stockexchange.InMemoryStockManager;
import com.candicedsouza.stockexchange.InMemoryStockTradeTracker;
import com.candicedsouza.stockexchange.VolumeWeightedStockPriceGeometricMeanIndex;
import com.candicedsouza.stockexchange.entities.Stock;
import com.candicedsouza.stockexchange.entities.StockType;

public class MainSystem {
	
	public static void main(String[] args){
		
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
		
		System.out.println("Initializing stock exchnage with 5 stocks");
		InMemoryStockManager stockManager = new InMemoryStockManager(stocks, stockDivinend);
		InMemoryStockTradeTracker tracker = new InMemoryStockTradeTracker();
		DefaultStockAnalyzer analyser = new DefaultStockAnalyzer(stockManager, tracker);
		
		GlobalBeverageCorporationExchange stockMarket = new GlobalBeverageCorporationExchange
				(stockManager, tracker, analyser,
				 new VolumeWeightedStockPriceGeometricMeanIndex(analyser));
		
		Date currentDate = new Date();
		
		System.out.println("Simulating stock trading on the exchange");
		stockMarket.buy("POP", 80, 40.0, getTimestamp(currentDate, 14, TimeUnit.MINUTES) );
		stockMarket.buy("POP", 80, 40.0, getTimestamp(currentDate, 13, TimeUnit.MINUTES) );
		stockMarket.buy("POP", 80, 40.0, getTimestamp(currentDate, 12, TimeUnit.MINUTES) );
		stockMarket.buy("POP", 80, 40.0, getTimestamp(currentDate, 10, TimeUnit.MINUTES) );
		stockMarket.buy("POP", 80, 40.0, getTimestamp(currentDate, 9, TimeUnit.MINUTES) );
		stockMarket.buy("POP", 80, 40.0, getTimestamp(currentDate, 8, TimeUnit.MINUTES) );
		stockMarket.buy("POP", 80, 40.0, getTimestamp(currentDate, 7, TimeUnit.MINUTES) );
		stockMarket.buy("POP", 80, 40.0, getTimestamp(currentDate, 6, TimeUnit.MINUTES) );
		stockMarket.buy("POP", 80, 40.0, getTimestamp(currentDate, 3, TimeUnit.MINUTES) );
		
		stockMarket.buy("POP", 80, 40.0, getTimestamp(currentDate, 11, TimeUnit.HOURS) );
		stockMarket.buy("POP", 80, 40.0, getTimestamp(currentDate, 2, TimeUnit.HOURS) );
		stockMarket.buy("POP", 80, 40.0, getTimestamp(currentDate, 3, TimeUnit.HOURS) );
		stockMarket.buy("POP", 80, 40.0, getTimestamp(currentDate, 2, TimeUnit.DAYS) );
		
		stockMarket.buy("TEA", 300, 23.89, new Date());
		stockMarket.sell("TEA", 50, 10.78, new Date());
		stockMarket.sell("TEA", 20, 12.78, getTimestamp(currentDate, 2, TimeUnit.DAYS));

		stockMarket.buy("ALE", 300, 45.86);
		stockMarket.sell("ALE", 280, 65.86);
		stockMarket.buy("GIN", 300, 45.86);

		stockMarket.buy("JOE", 300, 45.86);
				
		stockMarket.analyseStock("POP", 35.0);
		
		System.out.println("Analysis done on one COMMON stock, POP @ price 28.56");
		stockMarket.analyseStock("POP", 28.56);
		
		System.out.println("Analysis done on one PREFERRED stock, GIN @ price 8.571");
		stockMarket.analyseStock("GIN", 8.571);
		
		System.out.println("Displaying all trades done in the last 5 minutes");
		stockMarket.listAllRecentTradesByDuration(5, TimeUnit.MINUTES);
		
		System.out.println("Computing index of trade market : " + stockMarket.calculateGBCEAllShareIndex());
	}
	
	private static Date getTimestamp(Date startTime, int sourceDuration, TimeUnit unit){
		long timeStamp = TimeUnit.MILLISECONDS.convert(sourceDuration, unit);
		long timeBack = (startTime).getTime() - timeStamp;
		return new Date(timeBack);
	}
}
