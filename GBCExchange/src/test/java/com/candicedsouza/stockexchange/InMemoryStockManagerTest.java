package com.candicedsouza.stockexchange;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.candicedsouza.stockexchange.entities.Stock;
import com.candicedsouza.stockexchange.entities.StockType;
import com.candicedsouza.stockexchange.interfaces.StockManager;

public class InMemoryStockManagerTest {
	private StockManager systemUnderTest;
	
	@Before
	public void setup(){
		Set<Stock> stocks = new HashSet<Stock>();
		stocks.add(new Stock("ORG", StockType.COMMON, 0, 123));
		stocks.add(new Stock("RUM", StockType.COMMON, 0, 100));
		stocks.add(new Stock("ICE", StockType.COMMON, 0, 50));
		stocks.add(new Stock("DRY", StockType.PREFERRED, 5, 220));
		
		Map<String, Double> stockDividend = new HashMap<String, Double>();
		stockDividend.put("ICE", 34.67);
		
		systemUnderTest = new InMemoryStockManager(stocks, stockDividend);
	}
	
	@Test
	public void testDividendsProperlyInitialised() {
		Assert.assertEquals(0.0, systemUnderTest.getLastDividend("ORG"), 0.001);
		Assert.assertEquals(0.0, systemUnderTest.getLastDividend("RUM"), 0.001);
		Assert.assertEquals(34.67, systemUnderTest.getLastDividend("ICE"), 0.001);
		Assert.assertEquals(0.0, systemUnderTest.getLastDividend("DRY"), 0.001);
	}
	
	@Test
	public void testDividendNotDeclared(){
		Assert.assertEquals(0.00,  systemUnderTest.getLastDividend("adsfadsF"), 0);
	}
	
}
