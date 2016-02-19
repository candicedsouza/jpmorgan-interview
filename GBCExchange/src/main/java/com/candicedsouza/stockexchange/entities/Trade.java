package com.candicedsouza.stockexchange.entities;

import java.util.Date;

public class Trade {
	
	private final Date timestamp;
	private final int quantity;
	private final TradeType type;
	private final double price;

	public Trade(Date timestamp, int quantity, TradeType type, double price){
		this.timestamp = timestamp;
		this.quantity = quantity;
		this.type = type;
		this.price = price;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public int getQuantity() {
		return quantity;
	}

	public TradeType getType() {
		return type;
	}

	public double getPrice() {
		return price;
	}

	@Override
	public String toString() {
		return "Trade [timestamp=" + timestamp + ", quantity=" + quantity + ", type=" + type + ", price=" + price + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(price);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + quantity;
		result = prime * result + ((timestamp == null) ? 0 : timestamp.hashCode());
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
		Trade other = (Trade) obj;
		if (Double.doubleToLongBits(price) != Double.doubleToLongBits(other.price))
			return false;
		if (quantity != other.quantity)
			return false;
		if (timestamp == null) {
			if (other.timestamp != null)
				return false;
		} else if (!timestamp.equals(other.timestamp))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	
}
