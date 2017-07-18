package com.cezarykluczynski.stapi.etl.trading_card.creation.processor

import com.cezarykluczynski.stapi.etl.trading_card.creation.service.TradingCardTableFilter
import com.cezarykluczynski.stapi.model.trading_card_deck.entity.TradingCardDeck
import com.google.common.collect.Lists
import org.jsoup.nodes.Element
import spock.lang.Specification

class TradingCardDeckTableRowProcessorTest extends Specification {

	private static final String NON_CARD_TABLE_TITLE = 'NON_CARD_TABLE_TITLE'
	private static final String TABLE_TITLE_PRICE = '$0.00'

	private static final String NAME = 'NAME'
	private static final String FREQUENCY = 'FREQUENCY'
	private static final String NAME_WITH_FREQUENCY = " ${NAME} ( ${FREQUENCY} )"
	private static final String IGNORED_NAME = 'IGNORED_NAME'

	private ElementTextNodesProcessor elementTextNodesProcessorMock

	private TradingCardTableFilter tradingCardTableFilterMock

	private TradingCardDeckTableRowProcessor tradingCardDeckTableRowProcessor

	void setup() {
		elementTextNodesProcessorMock = Mock()
		tradingCardTableFilterMock = Mock()
		tradingCardDeckTableRowProcessor = new TradingCardDeckTableRowProcessor(elementTextNodesProcessorMock, tradingCardTableFilterMock)
	}

	void "when elements list is empty, null is returned"() {
		when:
		TradingCardDeck tradingCardDeck = tradingCardDeckTableRowProcessor.process(Lists.newArrayList())

		then:
		0 * _
		tradingCardDeck == null
	}

	void "when non-card title is found, null is returned"() {
		given:
		Element header = Mock()

		when:
		TradingCardDeck tradingCardDeck = tradingCardDeckTableRowProcessor.process(Lists.newArrayList(header))

		then:
		1 * elementTextNodesProcessorMock.process(header) >> Lists.newArrayList(NON_CARD_TABLE_TITLE)
		1 * tradingCardTableFilterMock.isNonCardTable(NON_CARD_TABLE_TITLE) >> true
		0 * _
		tradingCardDeck == null
	}

	void "returns null when title list without price is empty"() {
		given:
		Element header = Mock()

		when:
		TradingCardDeck tradingCardDeck = tradingCardDeckTableRowProcessor.process(Lists.newArrayList(header))

		then:
		1 * elementTextNodesProcessorMock.process(header) >> Lists.newArrayList(TABLE_TITLE_PRICE)
		1 * tradingCardTableFilterMock.isNonCardTable(TABLE_TITLE_PRICE) >> false
		0 * _
		tradingCardDeck == null
	}

	void "parses trading card set with frequency"() {
		given:
		Element header = Mock()

		when:
		TradingCardDeck tradingCardDeck = tradingCardDeckTableRowProcessor.process(Lists.newArrayList(header))

		then:
		1 * elementTextNodesProcessorMock.process(header) >> Lists.newArrayList(NAME_WITH_FREQUENCY)
		1 * tradingCardTableFilterMock.isNonCardTable(NAME_WITH_FREQUENCY) >> false
		0 * _
		tradingCardDeck.name == NAME
		tradingCardDeck.frequency == FREQUENCY
	}

	void "parses trading card set without frequency"() {
		given:
		Element header = Mock()

		when:
		TradingCardDeck tradingCardDeck = tradingCardDeckTableRowProcessor.process(Lists.newArrayList(header))

		then:
		1 * elementTextNodesProcessorMock.process(header) >> Lists.newArrayList(NAME, IGNORED_NAME)
		1 * tradingCardTableFilterMock.isNonCardTable(NAME) >> false
		1 * tradingCardTableFilterMock.isNonCardTable(IGNORED_NAME) >> false
		0 * _
		tradingCardDeck.name == NAME
		tradingCardDeck.frequency == null
	}

}
