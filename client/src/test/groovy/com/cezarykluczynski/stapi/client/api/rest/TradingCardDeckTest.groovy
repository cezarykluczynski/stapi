package com.cezarykluczynski.stapi.client.api.rest

import com.cezarykluczynski.stapi.client.v1.rest.api.TradingCardDeckApi
import com.cezarykluczynski.stapi.client.v1.rest.model.TradingCardDeckBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.TradingCardDeckFullResponse
import com.cezarykluczynski.stapi.util.AbstractTradingCardDeckTest

class TradingCardDeckTest extends AbstractTradingCardDeckTest {

	private TradingCardDeckApi tradingCardDeckApiMock

	private TradingCardDeck tradingCardDeck

	void setup() {
		tradingCardDeckApiMock = Mock()
		tradingCardDeck = new TradingCardDeck(tradingCardDeckApiMock, API_KEY)
	}

	void "gets single entity"() {
		given:
		TradingCardDeckFullResponse tradingCardDeckFullResponse = Mock()

		when:
		TradingCardDeckFullResponse tradingCardDeckFullResponseOutput = tradingCardDeck.get(UID)

		then:
		1 * tradingCardDeckApiMock.tradingCardDeckGet(UID, API_KEY) >> tradingCardDeckFullResponse
		0 * _
		tradingCardDeckFullResponse == tradingCardDeckFullResponseOutput
	}

	void "searches entities"() {
		given:
		TradingCardDeckBaseResponse tradingCardDeckBaseResponse = Mock()

		when:
		TradingCardDeckBaseResponse tradingCardDeckBaseResponseOutput = tradingCardDeck.search(PAGE_NUMBER, PAGE_SIZE, SORT, NAME,
				TRADING_CARD_SET_UID)

		then:
		1 * tradingCardDeckApiMock.tradingCardDeckSearchPost(PAGE_NUMBER, PAGE_SIZE, SORT, API_KEY, NAME, TRADING_CARD_SET_UID) >>
				tradingCardDeckBaseResponse
		0 * _
		tradingCardDeckBaseResponse == tradingCardDeckBaseResponseOutput
	}

}
