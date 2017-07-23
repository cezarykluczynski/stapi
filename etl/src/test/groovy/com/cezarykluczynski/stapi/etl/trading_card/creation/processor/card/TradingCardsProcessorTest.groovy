package com.cezarykluczynski.stapi.etl.trading_card.creation.processor.card

import com.cezarykluczynski.stapi.model.trading_card.entity.TradingCard
import com.cezarykluczynski.stapi.util.constant.TagName
import com.google.common.collect.Lists
import org.jsoup.nodes.Element
import spock.lang.Specification

class TradingCardsProcessorTest extends Specification {

	private TradingCardProcessor tradingCardProcessorMock

	private TradingCardsProcessor tradingCardsProcessor

	void setup() {
		tradingCardProcessorMock = Mock()
		tradingCardsProcessor = new TradingCardsProcessor(tradingCardProcessorMock)
	}

	void "when passes collection is empty, empty set is returned"() {
		when:
		Set<TradingCard> tradingCardSet = tradingCardsProcessor.process(Lists.newArrayList())

		then:
		tradingCardSet.empty
	}

	void "passes all elements that are tds to TradingCardProcessor, adds their results to returned set non-null are returned"() {
		given:
		Element td1 = new Element(TagName.TD)
		Element td2 = new Element(TagName.TD)
		Element td3 = new Element(TagName.TD)
		Element tr1 = new Element(TagName.TR)
		Element tr2 = new Element(TagName.TR)
		tr1.insertChildren(0, Lists.newArrayList(td1))
		tr2.insertChildren(0, Lists.newArrayList(td2, td3))
		TradingCard tradingCard1 = Mock()
		TradingCard tradingCard2 = Mock()

		when:
		Set<TradingCard> tradingCardSet = tradingCardsProcessor.process(Lists.newArrayList(tr1, tr2))

		then:
		1 * tradingCardProcessorMock.process(td1) >> tradingCard1
		1 * tradingCardProcessorMock.process(td2) >> null
		1 * tradingCardProcessorMock.process(td3) >> tradingCard2
		0 * _
		tradingCardSet.size() == 2
		tradingCardSet.contains tradingCard1
		tradingCardSet.contains tradingCard2
	}

}
