package com.cezarykluczynski.stapi.client.rest.facade

import static AbstractFacadeTest.SORT
import static AbstractFacadeTest.SORT_SERIALIZED

import com.cezarykluczynski.stapi.client.rest.api.TradingCardApi
import com.cezarykluczynski.stapi.client.rest.model.TradingCardBaseResponse
import com.cezarykluczynski.stapi.client.rest.model.TradingCardFullResponse
import com.cezarykluczynski.stapi.client.rest.model.TradingCardSearchCriteria
import com.cezarykluczynski.stapi.util.AbstractTradingCardTest

class TradingCardTest extends AbstractTradingCardTest {

	private TradingCardApi tradingCardApiMock

	private TradingCard tradingCard

	void setup() {
		tradingCardApiMock = Mock()
		tradingCard = new TradingCard(tradingCardApiMock)
	}

	void "gets single entity"() {
		given:
		TradingCardFullResponse tradingCardFullResponse = Mock()

		when:
		TradingCardFullResponse tradingCardFullResponseOutput = tradingCard.get(UID)

		then:
		1 * tradingCardApiMock.v1GetTradingCard(UID) >> tradingCardFullResponse
		0 * _
		tradingCardFullResponse == tradingCardFullResponseOutput
	}

	void "searches entities with criteria"() {
		given:
		TradingCardBaseResponse tradingCardBaseResponse = Mock()
		TradingCardSearchCriteria tradingCardSearchCriteria = new TradingCardSearchCriteria(
				pageNumber: PAGE_NUMBER,
				pageSize: PAGE_SIZE,
				name: NAME,
				tradingCardDeckUid: TRADING_CARD_DECK_UID,
				tradingCardSetUid: TRADING_CARD_SET_UID)
		tradingCardSearchCriteria.sort = SORT

		when:
		TradingCardBaseResponse tradingCardBaseResponseOutput = tradingCard.search(tradingCardSearchCriteria)

		then:
		1 * tradingCardApiMock.v1SearchTradingCards(PAGE_NUMBER, PAGE_SIZE, SORT_SERIALIZED, NAME, TRADING_CARD_DECK_UID,
				TRADING_CARD_SET_UID) >> tradingCardBaseResponse
		0 * _
		tradingCardBaseResponse == tradingCardBaseResponseOutput
	}

}
