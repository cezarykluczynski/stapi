package com.cezarykluczynski.stapi.model.trading_card_set.query

import org.springframework.data.jpa.repository.JpaContext
import spock.lang.Specification

class TradingCardSetQueryBuilderFactoryTest extends Specification {

	private JpaContext jpaContextMock

	private TradingCardSetQueryBuilderFactory tradingCardSetQueryBuilderFactory

	void setup() {
		jpaContextMock = Mock()
	}

	void "TradingCardSetQueryBuilderFactory is created"() {
		when:
		tradingCardSetQueryBuilderFactory = new TradingCardSetQueryBuilderFactory(jpaContextMock)

		then:
		tradingCardSetQueryBuilderFactory != null
	}

}
