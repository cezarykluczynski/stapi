package com.cezarykluczynski.stapi.etl.trading_card.creation.service

import com.cezarykluczynski.stapi.sources.wordpress.dto.Page
import com.cezarykluczynski.stapi.util.tool.RandomUtil
import spock.lang.Specification

class TradingCardSetFilterTest extends Specification {

	private TradingCardSetFilter tradingCardSetFilter

	void setup() {
		tradingCardSetFilter = new TradingCardSetFilter()
	}

	void "returns true when page title is among invalid titles"() {
		given:
		Page page = new Page(renderedTitle: RandomUtil.randomItem(TradingCardSetFilter.INVALID_TITLES))

		when:
		boolean shouldBeFilteredOut = tradingCardSetFilter.shouldBeFilteredOut(page)

		then:
		shouldBeFilteredOut
	}

	void "returns true when page title is not among invalid titles"() {
		given:
		Page page = new Page(renderedTitle: 'Any other title')

		when:
		boolean shouldBeFilteredOut = tradingCardSetFilter.shouldBeFilteredOut(page)

		then:
		!shouldBeFilteredOut
	}

}
