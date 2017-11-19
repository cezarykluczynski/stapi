package com.cezarykluczynski.stapi.client.api.rest

import com.cezarykluczynski.stapi.client.v1.rest.api.TradingCardApi
import com.cezarykluczynski.stapi.client.v1.rest.model.TradingCardBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.TradingCardFullResponse
import com.cezarykluczynski.stapi.util.AbstractTradingCardTest

class TradingCardTest extends AbstractTradingCardTest {

	private TradingCardApi tradingCardApiMock

	private TradingCard tradingCard

	void setup() {
		tradingCardApiMock = Mock()
		tradingCard = new TradingCard(tradingCardApiMock, API_KEY)
	}

	void "gets single entity"() {
		given:
		TradingCardFullResponse tradingCardFullResponse = Mock()

		when:
		TradingCardFullResponse tradingCardFullResponseOutput = tradingCard.get(UID)

		then:
		1 * tradingCardApiMock.tradingCardGet(UID, API_KEY) >> tradingCardFullResponse
		0 * _
		tradingCardFullResponse == tradingCardFullResponseOutput
	}

	void "searches entities"() {
		given:
		TradingCardBaseResponse tradingCardBaseResponse = Mock()

		when:
		TradingCardBaseResponse tradingCardBaseResponseOutput = tradingCard.search(PAGE_NUMBER, PAGE_SIZE, SORT, NAME, TRADING_CARD_DECK_UID,
				TRADING_CARD_SET_UID)

		then:
		1 * tradingCardApiMock.tradingCardSearchPost(PAGE_NUMBER, PAGE_SIZE, SORT, API_KEY, NAME, TRADING_CARD_DECK_UID, TRADING_CARD_SET_UID) >>
				tradingCardBaseResponse
		0 * _
		tradingCardBaseResponse == tradingCardBaseResponseOutput
	}

}
