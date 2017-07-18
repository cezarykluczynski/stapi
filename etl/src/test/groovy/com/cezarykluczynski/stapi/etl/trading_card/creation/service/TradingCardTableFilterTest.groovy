package com.cezarykluczynski.stapi.etl.trading_card.creation.service

import com.cezarykluczynski.stapi.util.tool.RandomUtil
import spock.lang.Specification

class TradingCardTableFilterTest extends Specification {

	private TradingCardTableFilter tradingCardTableFilter

	void setup() {
		tradingCardTableFilter = new TradingCardTableFilter()
	}

	void "returns true when deck name is among invalid titles"() {
		when:
		boolean isNonCardTable = tradingCardTableFilter.isNonCardTable(RandomUtil.randomItem(TradingCardTableFilter.INVALID_TITLES))

		then:
		isNonCardTable
	}

	void "returns false when deck name is not among invalid titles"() {
		when:
		boolean isNonCardTable = tradingCardTableFilter.isNonCardTable('Base Deck')

		then:
		!isNonCardTable
	}

}
