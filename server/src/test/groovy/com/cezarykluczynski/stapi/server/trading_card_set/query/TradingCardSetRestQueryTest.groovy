package com.cezarykluczynski.stapi.server.trading_card_set.query

import com.cezarykluczynski.stapi.model.trading_card_set.dto.TradingCardSetRequestDTO
import com.cezarykluczynski.stapi.model.trading_card_set.repository.TradingCardSetRepository
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.trading_card_set.dto.TradingCardSetRestBeanParams
import com.cezarykluczynski.stapi.server.trading_card_set.mapper.TradingCardSetBaseRestMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

class TradingCardSetRestQueryTest extends Specification {

	private TradingCardSetBaseRestMapper tradingCardSetBaseRestMapperMock

	private PageMapper pageMapperMock

	private TradingCardSetRepository tradingCardSetRepositoryMock

	private TradingCardSetRestQuery tradingCardSetRestQuery

	void setup() {
		tradingCardSetBaseRestMapperMock = Mock()
		pageMapperMock = Mock()
		tradingCardSetRepositoryMock = Mock()
		tradingCardSetRestQuery = new TradingCardSetRestQuery(tradingCardSetBaseRestMapperMock, pageMapperMock, tradingCardSetRepositoryMock)
	}

	void "maps TradingCardSetRestBeanParams to TradingCardSetRequestDTO and to PageRequest, then calls repository, then returns result"() {
		given:
		PageRequest pageRequest = Mock()
		TradingCardSetRestBeanParams tradingCardSetRestBeanParams = Mock()
		TradingCardSetRequestDTO tradingCardSetRequestDTO = Mock()
		Page page = Mock()

		when:
		Page pageOutput = tradingCardSetRestQuery.query(tradingCardSetRestBeanParams)

		then:
		1 * tradingCardSetBaseRestMapperMock.mapBase(tradingCardSetRestBeanParams) >> tradingCardSetRequestDTO
		1 * pageMapperMock.fromPageSortBeanParamsToPageRequest(tradingCardSetRestBeanParams) >> pageRequest
		1 * tradingCardSetRepositoryMock.findMatching(tradingCardSetRequestDTO, pageRequest) >> page
		pageOutput == page
	}

}
