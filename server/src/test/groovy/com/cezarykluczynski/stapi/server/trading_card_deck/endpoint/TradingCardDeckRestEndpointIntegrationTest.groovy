package com.cezarykluczynski.stapi.server.trading_card_deck.endpoint

import com.cezarykluczynski.stapi.client.v1.rest.model.TradingCardDeckBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.TradingCardDeckFullResponse
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import spock.lang.Requires

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_TRADING_CARDS)
})
class TradingCardDeckRestEndpointIntegrationTest extends AbstractTradingCardDeckEndpointIntegrationTest {

	void setup() {
		createRestClient()
	}

	void "gets trading card deck by UID"() {
		when:
		TradingCardDeckFullResponse tradingCardDeckFullResponse = stapiRestClient.tradingCardDeckApi.tradingCardDeckGet('TCD00000136110', null)

		then:
		tradingCardDeckFullResponse.tradingCardDeck.name == 'The Quotable Klingon'
	}

	void "there are more than five trading card decks with name 'Case Topper'"() {
		when:
		TradingCardDeckBaseResponse tradingCardDeckBaseResponse = stapiRestClient.tradingCardDeckApi.tradingCardDeckSearchPost(null, null, null, null,
				'Case Topper', null)

		then:
		tradingCardDeckBaseResponse.tradingCardDecks.size() > 5
	}

}
