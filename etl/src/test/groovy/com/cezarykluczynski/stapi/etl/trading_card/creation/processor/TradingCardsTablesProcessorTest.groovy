package com.cezarykluczynski.stapi.etl.trading_card.creation.processor

import com.cezarykluczynski.stapi.etl.trading_card.creation.service.TradingCardTableFilter
import com.cezarykluczynski.stapi.model.trading_card_deck.entity.TradingCardDeck
import com.google.common.collect.Sets
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import spock.lang.Specification

class TradingCardsTablesProcessorTest extends Specification {

	private TradingCardTableFilter tradingCardTableFilterMock

	private TradingCardDecksTableProcessor tradingCardsTableProcessorMock

	private TradingCardsTablesProcessor tradingCardsTablesProcessor

	void setup() {
		tradingCardTableFilterMock = Mock()
		tradingCardsTableProcessorMock = Mock()
		tradingCardsTablesProcessor = new TradingCardsTablesProcessor(tradingCardTableFilterMock, tradingCardsTableProcessorMock)
	}

	void "when elements are empty, empty set is returned"() {
		when:
		Set<TradingCardDeck> tradingCardDeckSet = tradingCardsTablesProcessor.process(new Elements())

		then:
		0 * _
		tradingCardDeckSet.empty
	}

	void "when table is detected as one not holding cards, it is skipped"() {
		given:
		Element element = Mock()

		when:
		Set<TradingCardDeck> tradingCardDeckSet = tradingCardsTablesProcessor.process(new Elements(element))

		then:
		1 * tradingCardTableFilterMock.isNonCardTable(element) >> true
		0 * _
		tradingCardDeckSet.empty
	}

	void "when table holds card decks, it is passed to TradingCardsTableProcessor, and it results is added to returned set"() {
		given:
		Element element = Mock()
		TradingCardDeck tradingCardDeck1 = Mock()
		TradingCardDeck tradingCardDeck2 = Mock()

		when:
		Set<TradingCardDeck> tradingCardDeckSet = tradingCardsTablesProcessor.process(new Elements(element))

		then:
		1 * tradingCardTableFilterMock.isNonCardTable(element) >> false
		1 * tradingCardsTableProcessorMock.process(element) >> Sets.newHashSet(tradingCardDeck1, tradingCardDeck2)
		0 * _
		tradingCardDeckSet.size() == 2
		tradingCardDeckSet.contains tradingCardDeck1
		tradingCardDeckSet.contains tradingCardDeck2
	}

}
