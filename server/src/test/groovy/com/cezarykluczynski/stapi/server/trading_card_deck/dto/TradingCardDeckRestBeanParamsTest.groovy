package com.cezarykluczynski.stapi.server.trading_card_deck.dto

import com.cezarykluczynski.stapi.server.common.dto.AbstractsRestBeanParamsTest
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams

class TradingCardDeckRestBeanParamsTest extends AbstractsRestBeanParamsTest {

	void "creates TradingCardDeckRestBeanParams from PageSortBeanParams"() {
		when:
		TradingCardDeckRestBeanParams tradingCardDeckRestBeanParams = TradingCardDeckRestBeanParams.fromPageSortBeanParams(new PageSortBeanParams(
				pageNumber: PAGE_NUMBER,
				pageSize: PAGE_SIZE,
				sort: SORT
		))

		then:
		tradingCardDeckRestBeanParams.pageNumber == PAGE_NUMBER
		tradingCardDeckRestBeanParams.pageSize == PAGE_SIZE
		tradingCardDeckRestBeanParams.sort == SORT
	}

	void "creates null TradingCardDeckRestBeanParams from null PageSortBeanParams"() {
		when:
		TradingCardDeckRestBeanParams seriesRestBeanParams = TradingCardDeckRestBeanParams.fromPageSortBeanParams null

		then:
		seriesRestBeanParams == null
	}

}
