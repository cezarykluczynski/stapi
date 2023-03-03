package com.cezarykluczynski.stapi.server.trading_card.endpoint

import com.cezarykluczynski.stapi.client.api.dto.TradingCardSearchCriteria
import com.cezarykluczynski.stapi.client.v1.rest.model.TradingCardBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.TradingCardFullResponse
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import com.cezarykluczynski.stapi.server.common.endpoint.AbstractEndpointIntegrationTest
import spock.lang.Requires

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_TRADING_CARDS)
})
class TradingCardRestEndpointIntegrationTest extends AbstractEndpointIntegrationTest {

	void "gets trading card by UID"() {
		when:
		TradingCardFullResponse tradingCardFullResponse = stapiRestClient.tradingCard.get('TC000000007293')

		then:
		tradingCardFullResponse.tradingCard.name == 'Mirror, Mirror'
	}

	void "there are more than five trading cards with name 'Locutus of Borg'"() {
		given:
		TradingCardSearchCriteria tradingCardSearchCriteria = new TradingCardSearchCriteria(
				name: 'Locutus of Borg'
		)

		when:
		TradingCardBaseResponse tradingCardBaseResponse = stapiRestClient.tradingCard.search(tradingCardSearchCriteria)

		then:
		tradingCardBaseResponse.tradingCards.size() > 5
	}

}
