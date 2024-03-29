package com.cezarykluczynski.stapi.server.trading_card_set.endpoint

import com.cezarykluczynski.stapi.client.rest.model.TradingCardSetBaseResponse
import com.cezarykluczynski.stapi.client.rest.model.TradingCardSetFullResponse
import com.cezarykluczynski.stapi.client.rest.model.TradingCardSetSearchCriteria
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import com.cezarykluczynski.stapi.server.common.endpoint.AbstractEndpointIntegrationTest
import spock.lang.Requires

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_TRADING_CARDS)
})
class TradingCardSetRestEndpointIntegrationTest extends AbstractEndpointIntegrationTest {

	void "gets trading card set by UID"() {
		when:
		TradingCardSetFullResponse tradingCardSetFullResponse = stapiRestClient.tradingCardSet.get('TCS00000017995')

		then:
		tradingCardSetFullResponse.tradingCardSet.name == 'Star Trek: The Next Generation Portfolio Prints Series Two'
	}

	void "'Star Trek 25th Anniversary Series II' is among trading card sets with 10 to 15 cards per pack"() {
		given:
		TradingCardSetSearchCriteria tradingCardSetSearchCriteria = new TradingCardSetSearchCriteria(
				cardsPerPackFrom: 10,
				cardsPerPackTo: 15
		)

		when:
		TradingCardSetBaseResponse tradingCardSetBaseResponse = stapiRestClient.tradingCardSet.search(tradingCardSetSearchCriteria)

		then:
		tradingCardSetBaseResponse.tradingCardSets.stream()
				.anyMatch { it.name == 'Star Trek 25th Anniversary Series II' }
	}

}
