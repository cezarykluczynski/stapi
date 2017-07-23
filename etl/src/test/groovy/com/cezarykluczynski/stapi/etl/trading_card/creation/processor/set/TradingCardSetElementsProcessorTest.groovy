package com.cezarykluczynski.stapi.etl.trading_card.creation.processor.set

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.trading_card.creation.dto.TradingCarSetHeaderValuePair
import com.cezarykluczynski.stapi.etl.trading_card.creation.dto.TradingCardSetElements
import com.cezarykluczynski.stapi.model.trading_card_set.entity.TradingCardSet
import com.cezarykluczynski.stapi.util.constant.TagName
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import spock.lang.Specification

class TradingCardSetElementsProcessorTest extends Specification {

	private static final String TH1_TEXT = 'TH1_TEXT'
	private static final String TH2_TEXT_RAW = ' TH1_TEXT '
	private static final String TH2_TEXT_CLEANED = 'TH1_TEXT'
	private static final String TD1_TEXT = 'TD1_TEXT'
	private static final String TD2_TEXT_RAW = ' TD2_TEXT '
	private static final String TD2_TEXT_CLEANED = 'TD2_TEXT'

	private TradingCardSetTableValuesEnrichingProcessor tradingCardSetTableValuesEnrichingProcessorMock

	private TradingCardSetElementsProcessor tradingCardSetElementsProcessor

	void setup() {
		tradingCardSetTableValuesEnrichingProcessorMock = Mock()
		tradingCardSetElementsProcessor = new TradingCardSetElementsProcessor(tradingCardSetTableValuesEnrichingProcessorMock)
	}

	void "when there are no header cells, null is returned"() {
		given:
		Element nextToLastRow = Mock()
		Element lastRow = Mock()
		TradingCardSetElements tradingCardSetElements = new TradingCardSetElements(
				nextToLastRow: nextToLastRow,
				lastRow: lastRow)

		when:
		TradingCardSet tradingCardSet =  tradingCardSetElementsProcessor.process(tradingCardSetElements)

		then:
		1 * nextToLastRow.getElementsByTag(TagName.TH) >> new Elements()
		1 * lastRow.getElementsByTag(TagName.TD) >> new Elements()
		0 * _
		tradingCardSet == null

	}

	void "when header cells number is not equal to regular cells number, null is returned"() {
		given:
		Element nextToLastRow = Mock()
		Element lastRow = Mock()
		TradingCardSetElements tradingCardSetElements = new TradingCardSetElements(
				nextToLastRow: nextToLastRow,
				lastRow: lastRow)

		when:
		TradingCardSet tradingCardSet =  tradingCardSetElementsProcessor.process(tradingCardSetElements)

		then:
		1 * nextToLastRow.getElementsByTag(TagName.TH) >> new Elements(new Element(TagName.TH))
		1 * lastRow.getElementsByTag(TagName.TD) >> new Elements(new Element(TagName.TD), new Element(TagName.TD))
		0 * _
		tradingCardSet == null
	}

	void "header and regular cell pairs are passed to TradingCardSetTableValuesEnrichingProcessor"() {
		given:
		Element nextToLastRow = Mock()
		Element lastRow = Mock()
		TradingCardSetElements tradingCardSetElements = new TradingCardSetElements(
				nextToLastRow: nextToLastRow,
				lastRow: lastRow)
		Element th1 = Mock()
		Element th2 = Mock()
		Element td1 = Mock()
		Element td2 = Mock()

		when:
		TradingCardSet tradingCardSet = tradingCardSetElementsProcessor.process(tradingCardSetElements)

		then:
		1 * nextToLastRow.getElementsByTag(TagName.TH) >> new Elements(th1, th2)
		1 * lastRow.getElementsByTag(TagName.TD) >> new Elements(td1, td2)
		1 * th1.text() >> TH1_TEXT
		1 * td1.text() >> TD1_TEXT
		1 * tradingCardSetTableValuesEnrichingProcessorMock.enrich(_ as EnrichablePair) >> {
				EnrichablePair<TradingCarSetHeaderValuePair, TradingCardSet> enrichablePair ->
			enrichablePair.input.headerText == TH1_TEXT
			enrichablePair.input.valueText == TD1_TEXT
			enrichablePair.output != null
		}

		1 * th2.text() >> TH2_TEXT_RAW
		1 * td2.text() >> TD2_TEXT_RAW
		1 * tradingCardSetTableValuesEnrichingProcessorMock.enrich(_ as EnrichablePair) >> {
				EnrichablePair<TradingCarSetHeaderValuePair, TradingCardSet> enrichablePair ->
			enrichablePair.input.headerText == TH2_TEXT_CLEANED
			enrichablePair.input.valueText == TD2_TEXT_CLEANED
			enrichablePair.output != null
		}
		0 * _
		tradingCardSet != null
	}

}
