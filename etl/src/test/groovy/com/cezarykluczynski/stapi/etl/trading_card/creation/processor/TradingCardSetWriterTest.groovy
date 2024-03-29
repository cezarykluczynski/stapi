package com.cezarykluczynski.stapi.etl.trading_card.creation.processor

import com.cezarykluczynski.stapi.model.trading_card_set.entity.TradingCardSet
import com.cezarykluczynski.stapi.model.trading_card_set.repository.TradingCardSetRepository
import com.google.common.collect.Lists
import org.springframework.batch.item.Chunk
import spock.lang.Specification

class TradingCardSetWriterTest extends Specification {

	private TradingCardSetRepository tradingCardSetRepositoryMock

	private TradingCardSetWriter tradingCardSetWriter

	void setup() {
		tradingCardSetRepositoryMock = Mock()
		tradingCardSetWriter = new TradingCardSetWriter(tradingCardSetRepositoryMock)
	}

	void "writes all entities using repository"() {
		given:
		TradingCardSet tradingCardSet = new TradingCardSet()
		List<TradingCardSet> tradingCardSetList = Lists.newArrayList(tradingCardSet)

		when:
		tradingCardSetWriter.write(new Chunk(tradingCardSetList))

		then:
		1 * tradingCardSetRepositoryMock.saveAll(tradingCardSetList)
		0 * _
	}

}
