package com.cezarykluczynski.stapi.etl.trading_card.creation.processor.card

import com.cezarykluczynski.stapi.util.constant.AttributeName
import com.cezarykluczynski.stapi.util.constant.TagName
import org.jsoup.nodes.Element
import spock.lang.Specification

class TradingCardItemNumberProcessorTest extends Specification {

	private static final String VALID_HREF = 'http://startrekcards.com/?p=5308&item_number=25078&reference=25078'
	private static final String INVALID_HREF = 'http://stapi.co/'
	private static final Integer ITEM_NUMBER = 25078

	private TradingCardItemNumberProcessor tradingCardItemNumberProcessor

	void setup() {
		tradingCardItemNumberProcessor = new TradingCardItemNumberProcessor()
	}

	void "returns null when href is empty"() {
		given:
		Element a = new Element(TagName.A)

		when:
		Integer itemNumber = tradingCardItemNumberProcessor.process(a)

		then:
		itemNumber == null
	}

	void "returns null when href does not match pattern"() {
		given:
		Element a = new Element(TagName.A)
		a.attr(AttributeName.HREF, INVALID_HREF)

		when:
		Integer itemNumber = tradingCardItemNumberProcessor.process(a)

		then:
		itemNumber == null
	}

	void "returns item number when it can be extracted"() {
		given:
		Element a = new Element(TagName.A)
		a.attr(AttributeName.HREF, VALID_HREF)

		when:
		Integer itemNumber = tradingCardItemNumberProcessor.process(a)

		then:
		itemNumber == ITEM_NUMBER
	}

}
