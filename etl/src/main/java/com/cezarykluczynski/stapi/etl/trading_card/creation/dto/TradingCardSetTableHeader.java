package com.cezarykluczynski.stapi.etl.trading_card.creation.dto;

import java.util.HashSet;
import java.util.Set;

public class TradingCardSetTableHeader {

	public static final String RELEASED = "Released";
	public static final String CARDS_PER_PACK = "Cards per Pack";
	public static final String PACKS_PER_BOX = "Packs per Box";
	public static final String BOXES_PER_CASE = "Boxes per Case";
	public static final String PRODUCTION_RUN = "Production Run";
	public static final String CARDS_SIZE = "Card Size";
	public static final String MANUFACTURER = "Manufacturer";
	public static final String COUNTRY = "Country";

	public static final Set<String> SPARSE_HEADERS = new HashSet<>();

	static {
		SPARSE_HEADERS.add("Cards per Candy");
		SPARSE_HEADERS.add("Cards per Box");
		SPARSE_HEADERS.add("Cards per Item");
		SPARSE_HEADERS.add("Cards in Set");
		SPARSE_HEADERS.add("Cards per Set");
		SPARSE_HEADERS.add("Cards per Video");
		SPARSE_HEADERS.add("Cards per Comic");
		SPARSE_HEADERS.add("Cards per Tin");
		SPARSE_HEADERS.add("Tins per Case");
		SPARSE_HEADERS.add("Cards per Sheet");
		SPARSE_HEADERS.add("Cards per Model");
		SPARSE_HEADERS.add("Cards per Video Set");
		SPARSE_HEADERS.add("CD’s per Pack");
	}

}
