package com.cezarykluczynski.stapi.server.trading_card_set.endpoint

import com.cezarykluczynski.stapi.client.v1.rest.model.TradingCardSetBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.TradingCardSetFullResponse
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import spock.lang.Requires

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_TRADING_CARDS)
})
class TradingCardSetRestEndpointIntegrationTest extends AbstractTradingCardSetEndpointIntegrationTest {

	void setup() {
		createRestClient()
	}

	void "gets trading card set by UID"() {
		when:
		TradingCardSetFullResponse tradingCardSetFullResponse = stapiRestClient.tradingCardSetApi.tradingCardSetGet('TCS00000017995', null)

		then:
		tradingCardSetFullResponse.tradingCardSet.name == 'Star Trek: The Next Generation Portfolio Prints Series Two'
	}

	void "'Star Trek 25th Anniversary Series II' is among trading card sets with 10 to 15 cards per set"() {
		when:
		TradingCardSetBaseResponse tradingCardSetBaseResponse = stapiRestClient.tradingCardSetApi.tradingCardSetSearchPost(null, null, null, null,
				null, null, null, 10, 15, null, null, null, null, null, null, null, null, null, null, null)

		then:
		tradingCardSetBaseResponse.tradingCardSets.stream()
				.anyMatch { it.name == 'Star Trek 25th Anniversary Series II' }
	}

}
