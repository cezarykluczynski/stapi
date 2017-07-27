package com.cezarykluczynski.stapi.server.trading_card.query

import com.cezarykluczynski.stapi.model.trading_card.dto.TradingCardRequestDTO
import com.cezarykluczynski.stapi.model.trading_card.repository.TradingCardRepository
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.trading_card.dto.TradingCardRestBeanParams
import com.cezarykluczynski.stapi.server.trading_card.mapper.TradingCardBaseRestMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

class TradingCardRestQueryTest extends Specification {

	private TradingCardBaseRestMapper tradingCardBaseRestMapperMock

	private PageMapper pageMapperMock

	private TradingCardRepository tradingCardRepositoryMock

	private TradingCardRestQuery tradingCardRestQuery

	void setup() {
		tradingCardBaseRestMapperMock = Mock()
		pageMapperMock = Mock()
		tradingCardRepositoryMock = Mock()
		tradingCardRestQuery = new TradingCardRestQuery(tradingCardBaseRestMapperMock, pageMapperMock, tradingCardRepositoryMock)
	}

	void "maps TradingCardRestBeanParams to TradingCardRequestDTO and to PageRequest, then calls repository, then returns result"() {
		given:
		PageRequest pageRequest = Mock()
		TradingCardRestBeanParams tradingCardRestBeanParams = Mock()
		TradingCardRequestDTO tradingCardRequestDTO = Mock()
		Page page = Mock()

		when:
		Page pageOutput = tradingCardRestQuery.query(tradingCardRestBeanParams)

		then:
		1 * tradingCardBaseRestMapperMock.mapBase(tradingCardRestBeanParams) >> tradingCardRequestDTO
		1 * pageMapperMock.fromPageSortBeanParamsToPageRequest(tradingCardRestBeanParams) >> pageRequest
		1 * tradingCardRepositoryMock.findMatching(tradingCardRequestDTO, pageRequest) >> page
		pageOutput == page
	}

}
