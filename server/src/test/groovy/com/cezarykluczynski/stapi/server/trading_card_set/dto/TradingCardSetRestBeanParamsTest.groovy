package com.cezarykluczynski.stapi.server.trading_card_set.dto

import com.cezarykluczynski.stapi.server.common.dto.AbstractsRestBeanParamsTest
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams

class TradingCardSetRestBeanParamsTest extends AbstractsRestBeanParamsTest {

	void "creates TradingCardSetRestBeanParams from PageSortBeanParams"() {
		when:
		TradingCardSetRestBeanParams tradingCardSetRestBeanParams = TradingCardSetRestBeanParams.fromPageSortBeanParams(new PageSortBeanParams(
				pageNumber: PAGE_NUMBER,
				pageSize: PAGE_SIZE,
				sort: SORT
		))

		then:
		tradingCardSetRestBeanParams.pageNumber == PAGE_NUMBER
		tradingCardSetRestBeanParams.pageSize == PAGE_SIZE
		tradingCardSetRestBeanParams.sort == SORT
	}

	void "creates null TradingCardSetRestBeanParams from null PageSortBeanParams"() {
		when:
		TradingCardSetRestBeanParams seriesRestBeanParams = TradingCardSetRestBeanParams.fromPageSortBeanParams null

		then:
		seriesRestBeanParams == null
	}

}
