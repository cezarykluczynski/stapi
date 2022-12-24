package com.cezarykluczynski.stapi.client.api.rest

import static com.cezarykluczynski.stapi.client.api.rest.AbstractRestClientTest.SORT
import static com.cezarykluczynski.stapi.client.api.rest.AbstractRestClientTest.SORT_SERIALIZED

import com.cezarykluczynski.stapi.client.api.dto.TradingCardDeckSearchCriteria
import com.cezarykluczynski.stapi.client.v1.rest.api.TradingCardDeckApi
import com.cezarykluczynski.stapi.client.v1.rest.model.TradingCardDeckBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.TradingCardDeckFullResponse
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
		1 * tradingCardDeckApiMock.v1RestTradingCardDeckGet(UID, null) >> tradingCardDeckFullResponse
		0 * _
		tradingCardDeckFullResponse == tradingCardDeckFullResponseOutput
	}

	void "searches entities"() {
		given:
		TradingCardDeckBaseResponse tradingCardDeckBaseResponse = Mock()

		when:
		TradingCardDeckBaseResponse tradingCardDeckBaseResponseOutput = tradingCardDeck.search(PAGE_NUMBER, PAGE_SIZE, SORT_SERIALIZED, NAME,
				TRADING_CARD_SET_UID)

		then:
		1 * tradingCardDeckApiMock.v1RestTradingCardDeckSearchPost(PAGE_NUMBER, PAGE_SIZE, SORT_SERIALIZED, null, NAME, TRADING_CARD_SET_UID) >>
				tradingCardDeckBaseResponse
		0 * _
		tradingCardDeckBaseResponse == tradingCardDeckBaseResponseOutput
	}

	void "searches entities with criteria"() {
		given:
		TradingCardDeckBaseResponse tradingCardDeckBaseResponse = Mock()
		TradingCardDeckSearchCriteria tradingCardDeckSearchCriteria = new TradingCardDeckSearchCriteria(
				pageNumber: PAGE_NUMBER,
				pageSize: PAGE_SIZE,
				name: NAME,
				tradingCardSetUid: TRADING_CARD_SET_UID)
		tradingCardDeckSearchCriteria.sort.addAll(SORT)

		when:
		TradingCardDeckBaseResponse tradingCardDeckBaseResponseOutput = tradingCardDeck.search(tradingCardDeckSearchCriteria)

		then:
		1 * tradingCardDeckApiMock.v1RestTradingCardDeckSearchPost(PAGE_NUMBER, PAGE_SIZE, SORT_SERIALIZED, null, NAME, TRADING_CARD_SET_UID) >>
				tradingCardDeckBaseResponse
		0 * _
		tradingCardDeckBaseResponse == tradingCardDeckBaseResponseOutput
	}

}
