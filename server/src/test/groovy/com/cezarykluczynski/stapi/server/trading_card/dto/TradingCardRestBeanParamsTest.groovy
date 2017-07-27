package com.cezarykluczynski.stapi.server.trading_card.dto

import com.cezarykluczynski.stapi.server.common.dto.AbstractsRestBeanParamsTest
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams

class TradingCardRestBeanParamsTest extends AbstractsRestBeanParamsTest {

	void "creates TradingCardRestBeanParams from PageSortBeanParams"() {
		when:
		TradingCardRestBeanParams tradingCardRestBeanParams = TradingCardRestBeanParams.fromPageSortBeanParams(new PageSortBeanParams(
				pageNumber: PAGE_NUMBER,
				pageSize: PAGE_SIZE,
				sort: SORT
		))

		then:
		tradingCardRestBeanParams.pageNumber == PAGE_NUMBER
		tradingCardRestBeanParams.pageSize == PAGE_SIZE
		tradingCardRestBeanParams.sort == SORT
	}

	void "creates null TradingCardRestBeanParams from null PageSortBeanParams"() {
		when:
		TradingCardRestBeanParams seriesRestBeanParams = TradingCardRestBeanParams.fromPageSortBeanParams null

		then:
		seriesRestBeanParams == null
	}

}
