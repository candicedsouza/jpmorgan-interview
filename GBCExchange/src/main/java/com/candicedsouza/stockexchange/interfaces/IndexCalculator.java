package com.candicedsouza.stockexchange.interfaces;

import java.util.Set;

public interface IndexCalculator {
	double calculateIndex(Set<String> stocks);
}
