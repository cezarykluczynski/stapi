package com.cezarykluczynski.stapi.model.trading_card.query

import org.springframework.data.jpa.repository.JpaContext
import spock.lang.Specification

class TradingCardQueryBuilderFactoryTest extends Specification {

	private JpaContext jpaContextMock

	private TradingCardQueryBuilderFactory tradingCardQueryBuilderFactory

	void setup() {
		jpaContextMock = Mock()
	}

	void "TradingCardQueryBuilderFactory is created"() {
		when:
		tradingCardQueryBuilderFactory = new TradingCardQueryBuilderFactory(jpaContextMock)

		then:
		tradingCardQueryBuilderFactory != null
	}

}
