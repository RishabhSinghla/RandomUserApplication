package com.nagarro.userapp.service;

/**
 * @author rishabhsinghla
 * SortStrategyFactory class for name or age sorting
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SortStrategyFactory {

	@Autowired
	private NameSortStrategy nameSortStrategy;

	@Autowired
	private AgeSortStrategy ageSortStrategy;

	public SortStrategy getSortStrategy(String sortType) {
		switch (sortType.toUpperCase()) {
		case "NAME":
			return nameSortStrategy;
		case "AGE":
			return ageSortStrategy;
		default:
			throw new IllegalArgumentException("Invalid sort type: " + sortType);
		}
	}
}
