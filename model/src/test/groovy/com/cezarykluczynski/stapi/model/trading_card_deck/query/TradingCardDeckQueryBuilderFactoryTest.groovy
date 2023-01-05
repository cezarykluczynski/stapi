package com.cezarykluczynski.stapi.model.trading_card_deck.query

import org.springframework.data.jpa.repository.JpaContext
import spock.lang.Specification

class TradingCardDeckQueryBuilderFactoryTest extends Specification {

	private JpaContext jpaContextMock

	private TradingCardDeckQueryBuilderFactory tradingCardDeckQueryBuilderFactory

	void setup() {
		jpaContextMock = Mock()
	}

	void "TradingCardDeckQueryBuilderFactory is created"() {
		when:
		tradingCardDeckQueryBuilderFactory = new TradingCardDeckQueryBuilderFactory(jpaContextMock)

		then:
		tradingCardDeckQueryBuilderFactory != null
	}

}
