package com.cezarykluczynski.stapi.model.trading_card_deck.query

import com.cezarykluczynski.stapi.model.common.cache.CachingStrategy
import org.springframework.data.jpa.repository.JpaContext
import spock.lang.Specification

class TradingCardDeckQueryBuilderFactoryTest extends Specification {

	private JpaContext jpaContextMock

	private CachingStrategy cachingStrategyMock

	private TradingCardDeckQueryBuilderFactory tradingCardDeckQueryBuilderFactory

	void setup() {
		jpaContextMock = Mock()
		cachingStrategyMock = Mock()
	}

	void "TradingCardDeckQueryBuilderFactory is created"() {
		when:
		tradingCardDeckQueryBuilderFactory = new TradingCardDeckQueryBuilderFactory(jpaContextMock, cachingStrategyMock)

		then:
		tradingCardDeckQueryBuilderFactory != null
	}

}
