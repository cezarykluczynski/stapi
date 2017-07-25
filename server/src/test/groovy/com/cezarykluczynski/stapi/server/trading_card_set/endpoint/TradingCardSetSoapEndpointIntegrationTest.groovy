package com.cezarykluczynski.stapi.server.trading_card_set.endpoint

import com.cezarykluczynski.stapi.client.v1.soap.TradingCardSetBase
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardSetBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardSetBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardSetFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardSetFullResponse
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import spock.lang.Requires

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_TRADING_CARDS)
})
class TradingCardSetSoapEndpointIntegrationTest extends AbstractTradingCardSetEndpointIntegrationTest {

	void setup() {
		createSoapClient()
	}

	void "gets trading card set by UID"() {
		when:
		TradingCardSetFullResponse tradingCardSetFullResponse = stapiSoapClient.tradingCardSetPortType
				.getTradingCardSetFull(new TradingCardSetFullRequest(
						uid: 'TCS00000001396'
				))

		then:
		tradingCardSetFullResponse.tradingCardSet.name == '30 Years of Star Trek Phase One'
	}

	void "gets trading card set by name"() {
		when:
		TradingCardSetBaseResponse tradingCardSetBaseResponse = stapiSoapClient.tradingCardSetPortType
				.getTradingCardSetBase(new TradingCardSetBaseRequest(
						name: 'Roddenberry Promotional Set 2125'
				))
		List<TradingCardSetBase> tradingCardSetBaseList = tradingCardSetBaseResponse.tradingCardSets

		then:
		tradingCardSetBaseList.size() == 1
		tradingCardSetBaseList[0].uid == 'TCS00000001709'
	}

}
