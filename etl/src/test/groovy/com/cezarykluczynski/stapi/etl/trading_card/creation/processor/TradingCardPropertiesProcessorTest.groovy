package com.cezarykluczynski.stapi.etl.trading_card.creation.processor

import com.cezarykluczynski.stapi.util.constant.TagName
import org.assertj.core.util.Lists
import org.jsoup.nodes.Element
import org.jsoup.nodes.TextNode
import spock.lang.Specification

class TradingCardPropertiesProcessorTest extends Specification {

	private static final String NUMBER = 'NUMBER'
	private static final String NAME = 'NAME'
	private static final String PRICE = '$1.23'
	private static final String COPYRIGHT = '(©2007)'
	private static final String MADE_WITH_NUMBER = '(200 Made)'
	private static final String PRODUCTION_RUN_RAW = '200'
	private static final String MADE_WITHOUT_NUMBER = '(Fewer Made)'
	private static final Integer RELEASED_YEAR = 2007
	private static final Integer PRODUCTION_RUN = 200

	private TradingCardSetItemsProcessor tradingCardSetItemsProcessorMock

	private TradingCardPropertiesProcessor tradingCardPropertiesProcessor

	void setup() {
		tradingCardSetItemsProcessorMock = Mock()
		tradingCardPropertiesProcessor = new TradingCardPropertiesProcessor(tradingCardSetItemsProcessorMock)
	}

	void "returns null when no properties could be extracted"() {
		given:
		Element a = new Element(TagName.A)
		a.text(PRICE)

		when:
		TradingCardPropertiesProcessor.TradingCardProperties tradingCardProperties = tradingCardPropertiesProcessor.process(a)

		then:
		0 * _
		tradingCardProperties == null
	}

	void "parses anchor with only name"() {
		given:
		Element a = new Element(TagName.A)
		a.text(NAME)

		when:
		TradingCardPropertiesProcessor.TradingCardProperties tradingCardProperties = tradingCardPropertiesProcessor.process(a)

		then:
		0 * _
		tradingCardProperties.name == NAME
		tradingCardProperties.number == null
		tradingCardProperties.releaseYear == null
		tradingCardProperties.productionRun == null
	}

	void "parses anchor with all values"() {
		given:
		Element a = new Element(TagName.A)
		a.childNodes = Lists.newArrayList(
				new TextNode(NUMBER, null),
				new Element(TagName.BR),
				new TextNode(NAME, null),
				new Element(TagName.BR),
				new TextNode(MADE_WITHOUT_NUMBER, null),
				new Element(TagName.BR),
				new TextNode(MADE_WITH_NUMBER, null),
				new Element(TagName.BR),
				new TextNode(COPYRIGHT, null),
				new Element(TagName.BR),
				new TextNode(PRICE, null),
				new Element(TagName.BR),
				new TextNode('Some text to skip', null))

		when:
		TradingCardPropertiesProcessor.TradingCardProperties tradingCardProperties = tradingCardPropertiesProcessor.process(a)

		then:
		1 * tradingCardSetItemsProcessorMock.process(PRODUCTION_RUN_RAW) >> PRODUCTION_RUN
		0 * _
		tradingCardProperties.name == NAME
		tradingCardProperties.number == NUMBER
		tradingCardProperties.releaseYear == RELEASED_YEAR
		tradingCardProperties.productionRun == PRODUCTION_RUN
	}

}
