package com.cezarykluczynski.stapi.client.rest.facade

import static AbstractFacadeTest.SORT
import static AbstractFacadeTest.SORT_SERIALIZED

import com.cezarykluczynski.stapi.client.rest.api.TradingCardSetApi
import com.cezarykluczynski.stapi.client.rest.model.TradingCardSetBaseResponse
import com.cezarykluczynski.stapi.client.rest.model.TradingCardSetFullResponse
import com.cezarykluczynski.stapi.client.rest.model.TradingCardSetSearchCriteria
import com.cezarykluczynski.stapi.util.AbstractTradingCardSetTest

class TradingCardSetTest extends AbstractTradingCardSetTest {

	private TradingCardSetApi tradingCardSetApiMock

	private TradingCardSet tradingCardSet

	void setup() {
		tradingCardSetApiMock = Mock()
		tradingCardSet = new TradingCardSet(tradingCardSetApiMock)
	}

	void "gets single entity"() {
		given:
		TradingCardSetFullResponse tradingCardSetFullResponse = Mock()

		when:
		TradingCardSetFullResponse tradingCardSetFullResponseOutput = tradingCardSet.get(UID)

		then:
		1 * tradingCardSetApiMock.v1GetTradingCardSet(UID) >> tradingCardSetFullResponse
		0 * _
		tradingCardSetFullResponse == tradingCardSetFullResponseOutput
	}

	void "searches entities with criteria"() {
		given:
		TradingCardSetBaseResponse tradingCardSetBaseResponse = Mock()
		TradingCardSetSearchCriteria tradingCardSetSearchCriteria = new TradingCardSetSearchCriteria(
				pageNumber: PAGE_NUMBER,
				pageSize: PAGE_SIZE,
				name: NAME,
				releaseYearFrom: RELEASE_YEAR_FROM,
				releaseYearTo: RELEASE_YEAR_TO,
				cardsPerPackFrom: CARDS_PER_PACK_FROM,
				cardsPerPackTo: CARDS_PER_PACK_TO,
				packsPerBoxFrom: PACKS_PER_BOX_FROM,
				packsPerBoxTo: PACKS_PER_BOX_TO,
				boxesPerCaseFrom: BOXES_PER_CASE_FROM,
				boxesPerCaseTo: BOXES_PER_CASE_TO,
				productionRunFrom: PRODUCTION_RUN_FROM,
				productionRunTo: PRODUCTION_RUN_TO,
				productionRunUnit: PRODUCTION_RUN_UNIT,
				cardWidthFrom: CARD_WIDTH_FROM,
				cardWidthTo: CARD_WIDTH_TO,
				cardHeightFrom: CARD_HEIGHT_FROM,
				cardHeightTo: CARD_HEIGHT_TO)
		tradingCardSetSearchCriteria.sort = SORT

		when:
		TradingCardSetBaseResponse tradingCardSetBaseResponseOutput = tradingCardSet.search(tradingCardSetSearchCriteria)

		then:
		1 * tradingCardSetApiMock.v1SearchTradingCardSets(PAGE_NUMBER, PAGE_SIZE, SORT_SERIALIZED, NAME, RELEASE_YEAR_FROM,
				RELEASE_YEAR_TO, CARDS_PER_PACK_FROM, CARDS_PER_PACK_TO, PACKS_PER_BOX_FROM, PACKS_PER_BOX_TO, BOXES_PER_CASE_FROM, BOXES_PER_CASE_TO,
				PRODUCTION_RUN_FROM, PRODUCTION_RUN_TO, PRODUCTION_RUN_UNIT, CARD_WIDTH_FROM, CARD_WIDTH_TO, CARD_HEIGHT_FROM, CARD_HEIGHT_TO) >>
				tradingCardSetBaseResponse
		0 * _
		tradingCardSetBaseResponse == tradingCardSetBaseResponseOutput
	}

}
