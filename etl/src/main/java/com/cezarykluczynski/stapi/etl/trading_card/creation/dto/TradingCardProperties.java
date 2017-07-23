package com.cezarykluczynski.stapi.etl.trading_card.creation.dto;

import lombok.Data;

@Data
public class TradingCardProperties {

	private String name;

	private String number;

	private Integer releaseYear;

	private Integer productionRun;

}
