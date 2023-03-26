package com.cezarykluczynski.stapi.client.rest.facade

import static AbstractFacadeTest.SORT
import static AbstractFacadeTest.SORT_SERIALIZED

import com.cezarykluczynski.stapi.client.rest.api.TradingCardDeckApi
import com.cezarykluczynski.stapi.client.rest.model.TradingCardDeckBaseResponse
import com.cezarykluczynski.stapi.client.rest.model.TradingCardDeckFullResponse
import com.cezarykluczynski.stapi.client.rest.model.TradingCardDeckSearchCriteria
import com.cezarykluczynski.stapi.util.AbstractTradingCardDeckTest

class TradingCardDeckTest extends AbstractTradingCardDeckTest {

	private TradingCardDeckApi tradingCardDeckApiMock

	private TradingCardDeck tradingCardDeck

	void setup() {
		tradingCardDeckApiMock = Mock()
		tradingCardDeck = new TradingCardDeck(tradingCardDeckApiMock)
	}

	void "gets single entity"() {
		given:
		TradingCardDeckFullResponse tradingCardDeckFullResponse = Mock()

		when:
		TradingCardDeckFullResponse tradingCardDeckFullResponseOutput = tradingCardDeck.get(UID)

		then:
		1 * tradingCardDeckApiMock.v1GetTradingCardDeck(UID) >> tradingCardDeckFullResponse
		0 * _
		tradingCardDeckFullResponse == tradingCardDeckFullResponseOutput
	}

	void "searches entities with criteria"() {
		given:
		TradingCardDeckBaseResponse tradingCardDeckBaseResponse = Mock()
		TradingCardDeckSearchCriteria tradingCardDeckSearchCriteria = new TradingCardDeckSearchCriteria(
				pageNumber: PAGE_NUMBER,
				pageSize: PAGE_SIZE,
				name: NAME,
				tradingCardSetUid: TRADING_CARD_SET_UID)
		tradingCardDeckSearchCriteria.sort = SORT

		when:
		TradingCardDeckBaseResponse tradingCardDeckBaseResponseOutput = tradingCardDeck.search(tradingCardDeckSearchCriteria)

		then:
		1 * tradingCardDeckApiMock.v1SearchTradingCardDecks(PAGE_NUMBER, PAGE_SIZE, SORT_SERIALIZED, NAME, TRADING_CARD_SET_UID) >>
				tradingCardDeckBaseResponse
		0 * _
		tradingCardDeckBaseResponse == tradingCardDeckBaseResponseOutput
	}

}
