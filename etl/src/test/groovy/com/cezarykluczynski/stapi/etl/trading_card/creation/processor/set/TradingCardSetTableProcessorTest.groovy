package com.cezarykluczynski.stapi.etl.trading_card.creation.processor.set

import com.cezarykluczynski.stapi.etl.trading_card.creation.dto.TradingCardSetElements
import com.cezarykluczynski.stapi.model.trading_card_set.entity.TradingCardSet
import com.cezarykluczynski.stapi.util.constant.TagName
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import spock.lang.Specification

class TradingCardSetTableProcessorTest extends Specification {

	private TradingCardSetElementsProcessor tradingCardSetElementsProcessorMock

	private TradingCardSetTableProcessor tradingCardSetTableProcessor

	void setup() {
		tradingCardSetElementsProcessorMock = Mock()
		tradingCardSetTableProcessor = new TradingCardSetTableProcessor(tradingCardSetElementsProcessorMock)
	}

	void "returns null when tbody cannot be found"() {
		given:
		Element element = Mock()

		when:
		TradingCardSet tradingCardSet = tradingCardSetTableProcessor.process(element)

		then:
		1 * element.getElementsByTag(TagName.TBODY) >> new Elements()
		0 * _
		tradingCardSet == null
	}

	void "returns null when there are no trs in tbody"() {
		given:
		Element element = Mock()
		Element tbody = Mock()

		when:
		TradingCardSet tradingCardSet = tradingCardSetTableProcessor.process(element)

		then:
		1 * element.getElementsByTag(TagName.TBODY) >> new Elements(tbody)
		1 * tbody.getElementsByTag(TagName.TR) >> new Elements()
		0 * _
		tradingCardSet == null
	}

	void "returns null when there are not enough trs in tbody"() {
		given:
		Element element = Mock()
		Element tbody = Mock()

		when:
		TradingCardSet tradingCardSet = tradingCardSetTableProcessor.process(element)

		then:
		1 * element.getElementsByTag(TagName.TBODY) >> new Elements(tbody)
		1 * tbody.getElementsByTag(TagName.TR) >> new Elements(new Element(TagName.TR), new Element(TagName.TR))
		0 * _
		tradingCardSet == null
	}

	void "returns result from TradingCardSetTableProcessor when there is enough trs in tbody"() {
		given:
		Element element = Mock()
		Element tbody = Mock()
		Element tr1 = Mock()
		Element tr2 = Mock()
		Element tr3 = Mock()
		TradingCardSet tradingCardSet = Mock()

		when:
		TradingCardSet tradingCardSetOutput = tradingCardSetTableProcessor.process(element)

		then:
		1 * element.getElementsByTag(TagName.TBODY) >> new Elements(tbody)
		1 * tbody.getElementsByTag(TagName.TR) >> new Elements(tr1, tr2, tr3)
		1 * tradingCardSetElementsProcessorMock.process(_ as TradingCardSetElements) >> { TradingCardSetElements tradingCardSetElements ->
			assert tradingCardSetElements.nextToLastRow == tr2
			assert tradingCardSetElements.lastRow == tr3
			tradingCardSet
		}
		0 * _
		tradingCardSetOutput == tradingCardSet
	}

}
