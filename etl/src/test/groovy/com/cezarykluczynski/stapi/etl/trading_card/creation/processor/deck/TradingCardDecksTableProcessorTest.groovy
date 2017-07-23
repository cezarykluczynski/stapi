package com.cezarykluczynski.stapi.etl.trading_card.creation.processor.deck

import com.cezarykluczynski.stapi.model.trading_card_deck.entity.TradingCardDeck
import com.cezarykluczynski.stapi.util.constant.AttributeName
import com.cezarykluczynski.stapi.util.constant.TagName
import com.google.common.collect.Lists
import org.jsoup.nodes.Element
import spock.lang.Specification

class TradingCardDecksTableProcessorTest extends Specification {

	TradingCardDeckTableRowProcessor tradingCardDeckTableRowProcessorMock

	private TradingCardDecksTableProcessor tradingCardDecksTableProcessor

	void setup() {
		tradingCardDeckTableRowProcessorMock = Mock()
		tradingCardDecksTableProcessor = new TradingCardDecksTableProcessor(tradingCardDeckTableRowProcessorMock)
	}

	void "when tbody cannot be found, empty set is returned"() {
		given:
		Element table = new Element(TagName.TABLE)

		when:
		Set<TradingCardDeck> tradingCardDeckSet = tradingCardDecksTableProcessor.process(table)

		then:
		0 * _
		tradingCardDeckSet.empty
	}

	void "when tr cannot be found, empty set is returned"() {
		given:
		Element table = new Element(TagName.TABLE)
		Element tbody = new Element(TagName.TBODY)
		table.insertChildren(0, Lists.newArrayList(tbody))

		when:
		Set<TradingCardDeck> tradingCardDeckSet = tradingCardDecksTableProcessor.process(table)

		then:
		0 * _
		tradingCardDeckSet.empty
	}

	void "when trs does not contain header, empty set is returned"() {
		given:
		Element table = new Element(TagName.TABLE)
		Element tbody = new Element(TagName.TBODY)
		Element tr = new Element(TagName.TR)
		table.insertChildren(0, Lists.newArrayList(tbody))
		tbody.insertChildren(0, Lists.newArrayList(tr))

		when:
		Set<TradingCardDeck> tradingCardDeckSet = tradingCardDecksTableProcessor.process(table)

		then:
		0 * _
		tradingCardDeckSet.empty
	}

	void "when there are trs with headers and cards, they are passed further"() {
		given:
		Element table = new Element(TagName.TABLE)
		Element tbody = new Element(TagName.TBODY)
		Element tr1 = new Element(TagName.TR)
		Element tr2 = new Element(TagName.TR)
		Element tr3 = new Element(TagName.TR)
		Element tr4 = new Element(TagName.TR)
		Element tr5 = new Element(TagName.TR)
		Element tr6 = new Element(TagName.TR)
		Element td1 = new Element(TagName.TD)
		td1.attr(AttributeName.COLSPAN, '4')
		Element td2 = new Element(TagName.TD)
		td2.attr(AttributeName.COLSPAN, '4')
		table.insertChildren(0, Lists.newArrayList(tbody))
		tbody.insertChildren(0, Lists.newArrayList(tr1, tr2, tr3, tr4, tr5, tr6))
		tr1.insertChildren(0, Lists.newArrayList(td1))
		tr4.insertChildren(0, Lists.newArrayList(td2))
		TradingCardDeck tradingCardDeck = Mock()

		when:
		Set<TradingCardDeck> tradingCardDeckSet = tradingCardDecksTableProcessor.process(table)

		then:
		1 * tradingCardDeckTableRowProcessorMock.process(Lists.newArrayList(tr1, tr2, tr3)) >> tradingCardDeck
		1 * tradingCardDeckTableRowProcessorMock.process(Lists.newArrayList(tr4, tr5, tr6)) >> null
		0 * _
		tradingCardDeckSet.size() == 1
		tradingCardDeckSet.contains tradingCardDeck
	}

}
