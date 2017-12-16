package com.cezarykluczynski.stapi.etl.trading_card.creation.dto;

import com.google.common.collect.ImmutableSet;

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

	public static final Set<String> SPARSE_HEADERS;

	static {
		SPARSE_HEADERS = ImmutableSet.<String>builder()
		.add("Cards per Candy")
		.add("Cards per Box")
		.add("Cards per Item")
		.add("Cards in Set")
		.add("Cards per Set")
		.add("Cards per Video")
		.add("Cards per Comic")
		.add("Cards per Tin")
		.add("Tins per Case")
		.add("Cards per Sheet")
		.add("Cards per Model")
		.add("Cards per Video Set")
		.add("CD’s per Pack")
		.build();
	}

}
