package com.cezarykluczynski.stapi.server.trading_card_set.endpoint

import com.cezarykluczynski.stapi.client.v1.rest.model.TradingCardSetBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.TradingCardSetFullResponse
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams
import com.cezarykluczynski.stapi.server.common.endpoint.AbstractRestEndpointTest
import com.cezarykluczynski.stapi.server.trading_card_set.dto.TradingCardSetRestBeanParams
import com.cezarykluczynski.stapi.server.trading_card_set.reader.TradingCardSetRestReader

class TradingCardSetRestEndpointTest extends AbstractRestEndpointTest {

	private static final String UID = 'UID'
	private static final String NAME = 'NAME'

	private TradingCardSetRestReader tradingCardSetRestReaderMock

	private TradingCardSetRestEndpoint tradingCardSetRestEndpoint

	void setup() {
		tradingCardSetRestReaderMock = Mock()
		tradingCardSetRestEndpoint = new TradingCardSetRestEndpoint(tradingCardSetRestReaderMock)
	}

	void "passes get call to TradingCardSetRestReader"() {
		given:
		TradingCardSetFullResponse tradingCardSetFullResponse = Mock()

		when:
		TradingCardSetFullResponse tradingCardSetFullResponseOutput = tradingCardSetRestEndpoint.getTradingCardSet(UID)

		then:
		1 * tradingCardSetRestReaderMock.readFull(UID) >> tradingCardSetFullResponse
		tradingCardSetFullResponseOutput == tradingCardSetFullResponse
	}

	void "passes search get call to TradingCardSetRestReader"() {
		given:
		PageSortBeanParams pageAwareBeanParams = Mock()
		pageAwareBeanParams.pageNumber >> PAGE_NUMBER
		pageAwareBeanParams.pageSize >> PAGE_SIZE
		TradingCardSetBaseResponse tradingCardSetResponse = Mock()

		when:
		TradingCardSetBaseResponse tradingCardSetResponseOutput = tradingCardSetRestEndpoint.searchTradingCardSet(pageAwareBeanParams)

		then:
		1 * tradingCardSetRestReaderMock.readBase(_ as TradingCardSetRestBeanParams) >> { TradingCardSetRestBeanParams tradingCardSetRestBeanParams ->
			assert pageAwareBeanParams.pageNumber == PAGE_NUMBER
			assert pageAwareBeanParams.pageSize == PAGE_SIZE
			tradingCardSetResponse
		}
		tradingCardSetResponseOutput == tradingCardSetResponse
	}

	void "passes search post call to TradingCardSetRestReader"() {
		given:
		TradingCardSetRestBeanParams tradingCardSetRestBeanParams = new TradingCardSetRestBeanParams(name: NAME)
		TradingCardSetBaseResponse tradingCardSetResponse = Mock()

		when:
		TradingCardSetBaseResponse tradingCardSetResponseOutput = tradingCardSetRestEndpoint.searchTradingCardSet(tradingCardSetRestBeanParams)

		then:
		1 * tradingCardSetRestReaderMock.readBase(tradingCardSetRestBeanParams as TradingCardSetRestBeanParams) >> {
				TradingCardSetRestBeanParams params ->
			assert params.name == NAME
			tradingCardSetResponse
		}
		tradingCardSetResponseOutput == tradingCardSetResponse
	}

}
