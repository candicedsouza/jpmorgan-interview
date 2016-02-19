package com.candicedsouza.stockexchange.entities;

public class Stock {
	private final String stockSymbol;
	private final StockType type;
	private final double fixedDividend;
	private final int parValue;
	
	public Stock(String stockSymbol, StockType type, double fixedDividend, int parValue){
		this.stockSymbol = stockSymbol;
		this.type = type;
		this.fixedDividend = fixedDividend;
		this.parValue = parValue;
	}

	public String getStockSymbol() {
		return stockSymbol;
	}

	public StockType getType() {
		return type;
	}

	public double getFixedDividend() {
		return fixedDividend;
	}

	public int getParValue() {
		return parValue;
	}
		
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(fixedDividend);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + parValue;
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((stockSymbol == null) ? 0 : stockSymbol.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Stock other = (Stock) obj;
		if (Double.doubleToLongBits(fixedDividend) != Double.doubleToLongBits(other.fixedDividend))
			return false;
		if (parValue != other.parValue)
			return false;
		if (stockSymbol == null) {
			if (other.stockSymbol != null)
				return false;
		} else if (!stockSymbol.equals(other.stockSymbol))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Stock [price=" + ", stockSymbol=" + stockSymbol + ", type=" + type + ", lastDividend="
				+ ", fixedDividend=" + fixedDividend + ", parValue=" + parValue + "]";
	}
	
	
}
