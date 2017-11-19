package com.cezarykluczynski.stapi.client.api.rest

import com.cezarykluczynski.stapi.client.v1.rest.api.TradingCardSetApi
import com.cezarykluczynski.stapi.client.v1.rest.model.TradingCardSetBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.TradingCardSetFullResponse
import com.cezarykluczynski.stapi.util.AbstractTradingCardSetTest

class TradingCardSetTest extends AbstractTradingCardSetTest {

	private TradingCardSetApi tradingCardSetApiMock

	private TradingCardSet tradingCardSet

	void setup() {
		tradingCardSetApiMock = Mock()
		tradingCardSet = new TradingCardSet(tradingCardSetApiMock, API_KEY)
	}

	void "gets single entity"() {
		given:
		TradingCardSetFullResponse tradingCardSetFullResponse = Mock()

		when:
		TradingCardSetFullResponse tradingCardSetFullResponseOutput = tradingCardSet.get(UID)

		then:
		1 * tradingCardSetApiMock.tradingCardSetGet(UID, API_KEY) >> tradingCardSetFullResponse
		0 * _
		tradingCardSetFullResponse == tradingCardSetFullResponseOutput
	}

	void "searches entities"() {
		given:
		TradingCardSetBaseResponse tradingCardSetBaseResponse = Mock()

		when:
		TradingCardSetBaseResponse tradingCardSetBaseResponseOutput = tradingCardSet.search(PAGE_NUMBER, PAGE_SIZE, SORT, NAME, RELEASE_YEAR_FROM,
				RELEASE_YEAR_TO, CARDS_PER_PACK_FROM, CARDS_PER_PACK_TO, PACKS_PER_BOX_FROM, PACKS_PER_BOX_TO, BOXES_PER_CASE_FROM, BOXES_PER_CASE_TO,
				PRODUCTION_RUN_FROM, PRODUCTION_RUN_TO, PRODUCTION_RUN_UNIT, CARD_WIDTH_FROM, CARD_WIDTH_TO, CARD_HEIGHT_FROM, CARD_HEIGHT_TO)

		then:
		1 * tradingCardSetApiMock.tradingCardSetSearchPost(PAGE_NUMBER, PAGE_SIZE, SORT, API_KEY, NAME, RELEASE_YEAR_FROM, RELEASE_YEAR_TO,
				CARDS_PER_PACK_FROM, CARDS_PER_PACK_TO, PACKS_PER_BOX_FROM, PACKS_PER_BOX_TO, BOXES_PER_CASE_FROM, BOXES_PER_CASE_TO,
				PRODUCTION_RUN_FROM, PRODUCTION_RUN_TO, PRODUCTION_RUN_UNIT, CARD_WIDTH_FROM, CARD_WIDTH_TO, CARD_HEIGHT_FROM, CARD_HEIGHT_TO) >>
				tradingCardSetBaseResponse
		0 * _
		tradingCardSetBaseResponse == tradingCardSetBaseResponseOutput
	}

}
