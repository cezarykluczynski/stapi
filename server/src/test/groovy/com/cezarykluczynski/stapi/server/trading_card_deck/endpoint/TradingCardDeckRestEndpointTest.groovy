package com.cezarykluczynski.stapi.server.trading_card_deck.endpoint

import com.cezarykluczynski.stapi.client.v1.rest.model.TradingCardDeckBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.TradingCardDeckFullResponse
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams
import com.cezarykluczynski.stapi.server.common.endpoint.AbstractRestEndpointTest
import com.cezarykluczynski.stapi.server.trading_card_deck.dto.TradingCardDeckRestBeanParams
import com.cezarykluczynski.stapi.server.trading_card_deck.reader.TradingCardDeckRestReader

class TradingCardDeckRestEndpointTest extends AbstractRestEndpointTest {

	private static final String UID = 'UID'
	private static final String NAME = 'NAME'

	private TradingCardDeckRestReader tradingCardDeckRestReaderMock

	private TradingCardDeckRestEndpoint tradingCardDeckRestEndpoint

	void setup() {
		tradingCardDeckRestReaderMock = Mock()
		tradingCardDeckRestEndpoint = new TradingCardDeckRestEndpoint(tradingCardDeckRestReaderMock)
	}

	void "passes get call to TradingCardDeckRestReader"() {
		given:
		TradingCardDeckFullResponse tradingCardDeckFullResponse = Mock()

		when:
		TradingCardDeckFullResponse tradingCardDeckFullResponseOutput = tradingCardDeckRestEndpoint.getTradingCardDeck(UID)

		then:
		1 * tradingCardDeckRestReaderMock.readFull(UID) >> tradingCardDeckFullResponse
		tradingCardDeckFullResponseOutput == tradingCardDeckFullResponse
	}

	void "passes search get call to TradingCardDeckRestReader"() {
		given:
		PageSortBeanParams pageAwareBeanParams = Mock()
		pageAwareBeanParams.pageNumber >> PAGE_NUMBER
		pageAwareBeanParams.pageSize >> PAGE_SIZE
		TradingCardDeckBaseResponse tradingCardDeckResponse = Mock()

		when:
		TradingCardDeckBaseResponse tradingCardDeckResponseOutput = tradingCardDeckRestEndpoint.searchTradingCardDeck(pageAwareBeanParams)

		then:
		1 * tradingCardDeckRestReaderMock.readBase(_ as TradingCardDeckRestBeanParams) >> {
				TradingCardDeckRestBeanParams tradingCardDeckRestBeanParams ->
			assert pageAwareBeanParams.pageNumber == PAGE_NUMBER
			assert pageAwareBeanParams.pageSize == PAGE_SIZE
			tradingCardDeckResponse
		}
		tradingCardDeckResponseOutput == tradingCardDeckResponse
	}

	void "passes search post call to TradingCardDeckRestReader"() {
		given:
		TradingCardDeckRestBeanParams tradingCardDeckRestBeanParams = new TradingCardDeckRestBeanParams(name: NAME)
		TradingCardDeckBaseResponse tradingCardDeckResponse = Mock()

		when:
		TradingCardDeckBaseResponse tradingCardDeckResponseOutput = tradingCardDeckRestEndpoint.searchTradingCardDeck(tradingCardDeckRestBeanParams)

		then:
		1 * tradingCardDeckRestReaderMock.readBase(tradingCardDeckRestBeanParams as TradingCardDeckRestBeanParams) >> {
				TradingCardDeckRestBeanParams params ->
			assert params.name == NAME
			tradingCardDeckResponse
		}
		tradingCardDeckResponseOutput == tradingCardDeckResponse
	}

}
