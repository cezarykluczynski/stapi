package com.cezarykluczynski.stapi.etl.trading_card.creation.processor.card

import com.cezarykluczynski.stapi.etl.trading_card.creation.dto.TradingCardProperties
import com.cezarykluczynski.stapi.model.common.service.UidGenerator
import com.cezarykluczynski.stapi.model.trading_card.entity.TradingCard
import com.cezarykluczynski.stapi.util.constant.AttributeName
import com.cezarykluczynski.stapi.util.constant.TagName
import org.assertj.core.util.Lists
import org.jsoup.nodes.Element
import spock.lang.Specification

class TradingCardProcessorTest extends Specification {

	private static final String NAME = 'NAME'
	private static final String NUMBER = 'NUMBER'
	private static final String UID = 'UID'
	private static final Integer ITEM_NUMBER = 45327
	private static final Integer RELEASE_YEAR = 1997
	private static final Integer PRODUCTION_RUN = 200

	private TradingCardItemNumberProcessor tradingCardItemNumberProcessorMock

	private UidGenerator uidGeneratorMock

	private TradingCardPropertiesProcessor tradingCardPropertiesProcessorMock

	private TradingCardProcessor tradingCardProcessor

	void setup() {
		tradingCardItemNumberProcessorMock = Mock()
		uidGeneratorMock = Mock()
		tradingCardPropertiesProcessorMock = Mock()
		tradingCardProcessor = new TradingCardProcessor(tradingCardItemNumberProcessorMock, uidGeneratorMock, tradingCardPropertiesProcessorMock)
	}

	void "when element is empty, null is returned"() {
		given:
		Element td = new Element(TagName.TD)

		when:
		TradingCard tradingCard = tradingCardProcessor.process(td)

		then:
		0 * _
		tradingCard == null
	}

	void "when element contains only an an image that says 'No Image Available', null is returned"() {
		given:
		Element img = new Element(TagName.IMG)
		img.attr(AttributeName.ALT, TradingCardProcessor.NO_IMAGE_AVAILABLE)
		Element td = new Element(TagName.TD)
		td.insertChildren(0, Lists.newArrayList(img))

		when:
		TradingCard tradingCard = tradingCardProcessor.process(td)

		then:
		0 * _
		tradingCard == null
	}

	void "when there is more than one anchor, null is returned"() {
		given:
		Element a1 = new Element(TagName.A)
		Element a2 = new Element(TagName.A)
		Element td = new Element(TagName.TD)
		td.insertChildren(0, Lists.newArrayList(a1, a2))

		when:
		TradingCard tradingCard = tradingCardProcessor.process(td)

		then:
		0 * _
		tradingCard == null
	}

	void "when element contains only an anchor that contains an image, null is returned"() {
		given:
		Element img = new Element(TagName.IMG)
		Element a = new Element(TagName.A)
		Element td = new Element(TagName.TD)
		a.insertChildren(0, Lists.newArrayList(img))
		td.insertChildren(0, Lists.newArrayList(a))

		when:
		TradingCard tradingCard = tradingCardProcessor.process(td)

		then:
		0 * _
		tradingCard == null
	}

	void "when TradingCardItemNumberProcessor returns null, null is returned"() {
		given:
		Element a = new Element(TagName.A)
		Element td = new Element(TagName.TD)
		td.insertChildren(0, Lists.newArrayList(a))

		when:
		TradingCard tradingCard = tradingCardProcessor.process(td)

		then:
		1 * tradingCardItemNumberProcessorMock.process(a) >> null
		0 * _
		tradingCard == null
	}

	void "when TradingCardPropertiesProcessor returns null, null is returned"() {
		given:
		Element a = new Element(TagName.A)
		Element td = new Element(TagName.TD)
		td.insertChildren(0, Lists.newArrayList(a))

		when:
		TradingCard tradingCard = tradingCardProcessor.process(td)

		then:
		1 * tradingCardItemNumberProcessorMock.process(a) >> 1
		1 * tradingCardPropertiesProcessorMock.process(a) >> null
		0 * _
		tradingCard == null
	}

	void "TradingCard is returned"() {
		given:
		Element a = new Element(TagName.A)
		Element td = new Element(TagName.TD)
		td.insertChildren(0, Lists.newArrayList(a))
		TradingCardProperties tradingCardProperties = new TradingCardProperties(
				name: NAME,
				number: NUMBER,
				releaseYear: RELEASE_YEAR,
				productionRun: PRODUCTION_RUN)

		when:
		TradingCard tradingCard = tradingCardProcessor.process(td)

		then:
		1 * tradingCardItemNumberProcessorMock.process(a) >> ITEM_NUMBER
		1 * tradingCardPropertiesProcessorMock.process(a) >> tradingCardProperties
		1 * uidGeneratorMock.generateForTradingCard(ITEM_NUMBER) >> UID
		0 * _
		tradingCard.uid == UID
		tradingCard.name == NAME
		tradingCard.number == NUMBER
		tradingCard.releaseYear == RELEASE_YEAR
		tradingCard.productionRun == PRODUCTION_RUN
	}

}
