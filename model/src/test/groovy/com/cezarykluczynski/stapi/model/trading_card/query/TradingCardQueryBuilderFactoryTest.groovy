package com.cezarykluczynski.stapi.model.trading_card.query

import com.cezarykluczynski.stapi.model.common.cache.CachingStrategy
import org.springframework.data.jpa.repository.JpaContext
import spock.lang.Specification

class TradingCardQueryBuilderFactoryTest extends Specification {

	private JpaContext jpaContextMock

	private CachingStrategy cachingStrategyMock

	private TradingCardQueryBuilderFactory tradingCardQueryBuilderFactory

	void setup() {
		jpaContextMock = Mock()
		cachingStrategyMock = Mock()
	}

	void "TradingCardQueryBuilderFactory is created"() {
		when:
		tradingCardQueryBuilderFactory = new TradingCardQueryBuilderFactory(jpaContextMock, cachingStrategyMock)

		then:
		tradingCardQueryBuilderFactory != null
	}

}
