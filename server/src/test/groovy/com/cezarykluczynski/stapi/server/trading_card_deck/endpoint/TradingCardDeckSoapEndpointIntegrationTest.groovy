package com.cezarykluczynski.stapi.server.trading_card_deck.endpoint

import com.cezarykluczynski.stapi.client.v1.soap.TradingCardDeckBase
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardDeckBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardDeckBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardDeckFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardDeckFullResponse
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardSetBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardSetBaseResponse
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.server.StaticJobCompletenessDecider
import spock.lang.Requires

@Requires({
	StaticJobCompletenessDecider.isStepCompleted(StepName.CREATE_TRADING_CARDS)
})
class TradingCardDeckSoapEndpointIntegrationTest extends AbstractTradingCardDeckEndpointIntegrationTest {

	void setup() {
		createSoapClient()
	}

	void "gets trading card deck by UID"() {
		when:
		TradingCardDeckFullResponse tradingCardDeckFullResponse = stapiSoapClient.tradingCardDeckPortType
				.getTradingCardDeckFull(new TradingCardDeckFullRequest(
						uid: 'TCD00000142903'
				))

		then:
		tradingCardDeckFullResponse.tradingCardDeck.name == 'U.S.S. Voyager'
	}

	void "there are 21 decks in 'Star Trek Aliens' set"() {
		when:
		TradingCardSetBaseResponse tradingCardSetBaseResponse = stapiSoapClient.tradingCardSetPortType
				.getTradingCardSetBase(new TradingCardSetBaseRequest(
						name: 'Star Trek Aliens'
				))
		TradingCardDeckBaseResponse tradingCardDeckBaseResponse = stapiSoapClient.tradingCardDeckPortType
				.getTradingCardDeckBase(new TradingCardDeckBaseRequest(
						tradingCardSetUid: tradingCardSetBaseResponse.tradingCardSets[0].uid
				))
		List<TradingCardDeckBase> tradingCardDeckBaseList = tradingCardDeckBaseResponse.tradingCardDecks

		then:
		tradingCardDeckBaseList.size() == 21
	}

}
