package com.cezarykluczynski.stapi.etl.trading_card.creation.dto;

import lombok.Data;
import org.jsoup.nodes.Element;

@Data
public class TradingCardSetElements {

	private Element nextToLastRow;

	private Element lastRow;

}
