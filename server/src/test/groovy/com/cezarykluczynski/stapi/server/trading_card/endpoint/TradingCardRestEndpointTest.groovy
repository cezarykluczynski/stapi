package com.cezarykluczynski.stapi.server.trading_card.endpoint

import com.cezarykluczynski.stapi.client.v1.rest.model.TradingCardBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.TradingCardFullResponse
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams
import com.cezarykluczynski.stapi.server.common.endpoint.AbstractRestEndpointTest
import com.cezarykluczynski.stapi.server.trading_card.dto.TradingCardRestBeanParams
import com.cezarykluczynski.stapi.server.trading_card.reader.TradingCardRestReader

class TradingCardRestEndpointTest extends AbstractRestEndpointTest {

	private static final String UID = 'UID'
	private static final String NAME = 'NAME'

	private TradingCardRestReader tradingCardRestReaderMock

	private TradingCardRestEndpoint tradingCardRestEndpoint

	void setup() {
		tradingCardRestReaderMock = Mock()
		tradingCardRestEndpoint = new TradingCardRestEndpoint(tradingCardRestReaderMock)
	}

	void "passes get call to TradingCardRestReader"() {
		given:
		TradingCardFullResponse tradingCardFullResponse = Mock()

		when:
		TradingCardFullResponse tradingCardFullResponseOutput = tradingCardRestEndpoint.getTradingCard(UID)

		then:
		1 * tradingCardRestReaderMock.readFull(UID) >> tradingCardFullResponse
		tradingCardFullResponseOutput == tradingCardFullResponse
	}

	void "passes search get call to TradingCardRestReader"() {
		given:
		PageSortBeanParams pageAwareBeanParams = Mock()
		pageAwareBeanParams.pageNumber >> PAGE_NUMBER
		pageAwareBeanParams.pageSize >> PAGE_SIZE
		TradingCardBaseResponse tradingCardResponse = Mock()

		when:
		TradingCardBaseResponse tradingCardResponseOutput = tradingCardRestEndpoint.searchTradingCard(pageAwareBeanParams)

		then:
		1 * tradingCardRestReaderMock.readBase(_ as TradingCardRestBeanParams) >> {
				TradingCardRestBeanParams tradingCardRestBeanParams ->
			assert pageAwareBeanParams.pageNumber == PAGE_NUMBER
			assert pageAwareBeanParams.pageSize == PAGE_SIZE
			tradingCardResponse
		}
		tradingCardResponseOutput == tradingCardResponse
	}

	void "passes search post call to TradingCardRestReader"() {
		given:
		TradingCardRestBeanParams tradingCardRestBeanParams = new TradingCardRestBeanParams(name: NAME)
		TradingCardBaseResponse tradingCardResponse = Mock()

		when:
		TradingCardBaseResponse tradingCardResponseOutput = tradingCardRestEndpoint.searchTradingCard(tradingCardRestBeanParams)

		then:
		1 * tradingCardRestReaderMock.readBase(tradingCardRestBeanParams as TradingCardRestBeanParams) >> {
				TradingCardRestBeanParams params ->
			assert params.name == NAME
			tradingCardResponse
		}
		tradingCardResponseOutput == tradingCardResponse
	}

}
