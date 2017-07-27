package com.cezarykluczynski.stapi.server.trading_card.endpoint

import com.cezarykluczynski.stapi.client.v1.rest.model.TradingCardBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.TradingCardFullResponse
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import spock.lang.Requires

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_TRADING_CARDS)
})
class TradingCardRestEndpointIntegrationTest extends AbstractTradingCardEndpointIntegrationTest {

	void setup() {
		createRestClient()
	}

	void "gets trading card by UID"() {
		when:
		TradingCardFullResponse tradingCardFullResponse = stapiRestClient.tradingCardApi.tradingCardGet('TC000000007293', null)

		then:
		tradingCardFullResponse.tradingCard.name == 'Mirror, Mirror'
	}

	void "there are more than five trading cards with name 'Locutus of Borg'"() {
		when:
		TradingCardBaseResponse tradingCardBaseResponse = stapiRestClient.tradingCardApi.tradingCardSearchPost(null, null, null, null,
				'Locutus of Borg', null, null)

		then:
		tradingCardBaseResponse.tradingCards.size() > 5
	}

}
