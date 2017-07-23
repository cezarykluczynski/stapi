package com.cezarykluczynski.stapi.etl.trading_card.creation.processor.deck

import com.cezarykluczynski.stapi.etl.trading_card.creation.processor.card.TradingCardsProcessor
import com.cezarykluczynski.stapi.etl.trading_card.creation.service.TradingCardTableFilter
import com.cezarykluczynski.stapi.model.trading_card.entity.TradingCard
import com.cezarykluczynski.stapi.model.trading_card_deck.entity.TradingCardDeck
import com.google.common.collect.Lists
import com.google.common.collect.Sets
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

	private TradingCardsProcessor tradingCardsProcessorMock

	private TradingCardDeckTableRowProcessor tradingCardDeckTableRowProcessor

	void setup() {
		elementTextNodesProcessorMock = Mock()
		tradingCardTableFilterMock = Mock()
		tradingCardsProcessorMock = Mock()
		tradingCardDeckTableRowProcessor = new TradingCardDeckTableRowProcessor(elementTextNodesProcessorMock, tradingCardTableFilterMock,
				tradingCardsProcessorMock)
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

	void "parses trading card set with frequency, no cards added"() {
		given:
		Element header = Mock()

		when:
		TradingCardDeck tradingCardDeck = tradingCardDeckTableRowProcessor.process(Lists.newArrayList(header))

		then:
		1 * elementTextNodesProcessorMock.process(header) >> Lists.newArrayList(NAME_WITH_FREQUENCY)
		1 * tradingCardTableFilterMock.isNonCardTable(NAME_WITH_FREQUENCY) >> false
		1 * tradingCardsProcessorMock.process(Lists.newArrayList()) >> Sets.newHashSet()
		0 * _
		tradingCardDeck.name == NAME
		tradingCardDeck.frequency == FREQUENCY
		tradingCardDeck.tradingCards.empty
	}

	void "parses trading card set without frequency, and adds cards"() {
		given:
		Element header = Mock()
		Element cards1 = Mock()
		Element cards2 = Mock()
		TradingCard tradingCard1 = new TradingCard(id: 1L)
		TradingCard tradingCard2 = new TradingCard(id: 2L)

		when:
		TradingCardDeck tradingCardDeck = tradingCardDeckTableRowProcessor.process(Lists.newArrayList(header, cards1, cards2))

		then:
		1 * elementTextNodesProcessorMock.process(header) >> Lists.newArrayList(NAME, IGNORED_NAME)
		1 * tradingCardTableFilterMock.isNonCardTable(NAME) >> false
		1 * tradingCardTableFilterMock.isNonCardTable(IGNORED_NAME) >> false
		1 * tradingCardsProcessorMock.process(Lists.newArrayList(cards1, cards2)) >> Sets.newHashSet(tradingCard1, tradingCard2)
		0 * _
		tradingCardDeck.name == NAME
		tradingCardDeck.frequency == null
		tradingCardDeck.tradingCards.size() == 2
		tradingCardDeck.tradingCards.contains tradingCard1
		tradingCardDeck.tradingCards.contains tradingCard2
		tradingCard1.tradingCardDeck == tradingCardDeck
		tradingCard2.tradingCardDeck == tradingCardDeck
	}

}
