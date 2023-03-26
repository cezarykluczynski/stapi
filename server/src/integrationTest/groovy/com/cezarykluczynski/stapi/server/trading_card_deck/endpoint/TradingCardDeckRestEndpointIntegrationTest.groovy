package com.cezarykluczynski.stapi.server.trading_card_deck.endpoint

import com.cezarykluczynski.stapi.client.rest.model.TradingCardDeckBaseResponse
import com.cezarykluczynski.stapi.client.rest.model.TradingCardDeckFullResponse
import com.cezarykluczynski.stapi.client.rest.model.TradingCardDeckSearchCriteria
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import com.cezarykluczynski.stapi.server.common.endpoint.AbstractEndpointIntegrationTest
import spock.lang.Requires

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_TRADING_CARDS)
})
class TradingCardDeckRestEndpointIntegrationTest extends AbstractEndpointIntegrationTest {

	void "gets trading card deck by UID"() {
		when:
		TradingCardDeckFullResponse tradingCardDeckFullResponse = stapiRestClient.tradingCardDeck.get('TCD00000136110')

		then:
		tradingCardDeckFullResponse.tradingCardDeck.name == 'The Quotable Klingon'
	}

	void "there are more than five trading card decks with name 'Case Topper'"() {
		given:
		TradingCardDeckSearchCriteria tradingCardDeckSearchCriteria = new TradingCardDeckSearchCriteria(
				name: 'Case Topper'
		)

		when:
		TradingCardDeckBaseResponse tradingCardDeckBaseResponse = stapiRestClient.tradingCardDeck.search(tradingCardDeckSearchCriteria)

		then:
		tradingCardDeckBaseResponse.tradingCardDecks.size() > 5
	}

}
