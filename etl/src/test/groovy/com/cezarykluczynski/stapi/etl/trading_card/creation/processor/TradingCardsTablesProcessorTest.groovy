package com.cezarykluczynski.stapi.etl.trading_card.creation.processor

import com.cezarykluczynski.stapi.etl.trading_card.creation.service.TradingCardTableFilter
import com.cezarykluczynski.stapi.model.trading_card.entity.TradingCard
import com.google.common.collect.Sets
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import spock.lang.Specification

class TradingCardsTablesProcessorTest extends Specification {

	private TradingCardTableFilter tradingCardTableFilterMock

	private TradingCardsTableProcessor tradingCardsTableProcessorMock

	private TradingCardsTablesProcessor tradingCardsTablesProcessor

	void setup() {
		tradingCardTableFilterMock = Mock()
		tradingCardsTableProcessorMock = Mock()
		tradingCardsTablesProcessor = new TradingCardsTablesProcessor(tradingCardTableFilterMock, tradingCardsTableProcessorMock)
	}

	void "when elements are empty, empty set is returned"() {
		when:
		Set<TradingCard> tradingCardSet = tradingCardsTablesProcessor.process(new Elements())

		then:
		0 * _
		tradingCardSet.empty
	}

	void "when table is detected as one not holding cards, it is skipped"() {
		given:
		Element element = Mock()

		when:
		Set<TradingCard> tradingCardSet = tradingCardsTablesProcessor.process(new Elements(element))

		then:
		1 * tradingCardTableFilterMock.isNonCardTable(element) >> true
		0 * _
		tradingCardSet.empty
	}

	void "when table holds cards, it is passed to TradingCardsTableProcessor, and it results is added to returned set"() {
		given:
		Element element = Mock()
		TradingCard tradingCard1 = Mock()
		TradingCard tradingCard2 = Mock()

		when:
		Set<TradingCard> tradingCardSet = tradingCardsTablesProcessor.process(new Elements(element))

		then:
		1 * tradingCardTableFilterMock.isNonCardTable(element) >> false
		1 * tradingCardsTableProcessorMock.process(element) >> Sets.newHashSet(tradingCard1, tradingCard2)
		0 * _
		tradingCardSet.size() == 2
		tradingCardSet.contains tradingCard1
		tradingCardSet.contains tradingCard2
	}

}
