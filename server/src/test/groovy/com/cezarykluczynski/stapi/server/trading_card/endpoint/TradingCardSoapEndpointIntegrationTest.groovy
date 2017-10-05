package com.cezarykluczynski.stapi.server.trading_card.endpoint

import com.cezarykluczynski.stapi.client.v1.soap.TradingCardBase
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardFullResponse
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import spock.lang.Requires

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_TRADING_CARDS)
})
class TradingCardSoapEndpointIntegrationTest extends AbstractTradingCardEndpointIntegrationTest {

	void setup() {
		createSoapClient()
	}

	void "gets trading card by UID"() {
		when:
		TradingCardFullResponse tradingCardFullResponse = stapiSoapClient.tradingCardPortType.getTradingCardFull(new TradingCardFullRequest(
				uid: 'TC000000030111'
		))

		then:
		tradingCardFullResponse.tradingCard.name == 'Lt. Geordi LaForge in Dress Uniform'
	}

	void "there are 50 cards in 'Star Trek: The Original Series Art and Images' set"() {
		when:
		TradingCardBaseResponse tradingCardBaseResponse = stapiSoapClient.tradingCardPortType.getTradingCardBase(new TradingCardBaseRequest(
				tradingCardSetUid: 'TCS00000001641'
		))
		List<TradingCardBase> tradingCardBaseList = tradingCardBaseResponse.tradingCards

		then:
		tradingCardBaseList.size() == 50
	}

}
