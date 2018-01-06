package com.cezarykluczynski.stapi.model.trading_card_set.query

import com.cezarykluczynski.stapi.model.common.cache.CachingStrategy
import org.springframework.data.jpa.repository.JpaContext
import spock.lang.Specification

class TradingCardSetQueryBuilderFactoryTest extends Specification {

	private JpaContext jpaContextMock

	private CachingStrategy cachingStrategyMock

	private TradingCardSetQueryBuilderFactory tradingCardSetQueryBuilderFactory

	void setup() {
		jpaContextMock = Mock()
		cachingStrategyMock = Mock()
	}

	void "TradingCardSetQueryBuilderFactory is created"() {
		when:
		tradingCardSetQueryBuilderFactory = new TradingCardSetQueryBuilderFactory(jpaContextMock, cachingStrategyMock)

		then:
		tradingCardSetQueryBuilderFactory != null
	}

}
