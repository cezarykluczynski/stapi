package com.cezarykluczynski.stapi.server.trading_card_deck.query

import com.cezarykluczynski.stapi.model.trading_card_deck.dto.TradingCardDeckRequestDTO
import com.cezarykluczynski.stapi.model.trading_card_deck.repository.TradingCardDeckRepository
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.trading_card_deck.dto.TradingCardDeckRestBeanParams
import com.cezarykluczynski.stapi.server.trading_card_deck.mapper.TradingCardDeckBaseRestMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

class TradingCardDeckRestQueryTest extends Specification {

	private TradingCardDeckBaseRestMapper tradingCardDeckBaseRestMapperMock

	private PageMapper pageMapperMock

	private TradingCardDeckRepository tradingCardDeckRepositoryMock

	private TradingCardDeckRestQuery tradingCardDeckRestQuery

	void setup() {
		tradingCardDeckBaseRestMapperMock = Mock()
		pageMapperMock = Mock()
		tradingCardDeckRepositoryMock = Mock()
		tradingCardDeckRestQuery = new TradingCardDeckRestQuery(tradingCardDeckBaseRestMapperMock, pageMapperMock, tradingCardDeckRepositoryMock)
	}

	void "maps TradingCardDeckRestBeanParams to TradingCardDeckRequestDTO and to PageRequest, then calls repository, then returns result"() {
		given:
		PageRequest pageRequest = Mock()
		TradingCardDeckRestBeanParams tradingCardDeckRestBeanParams = Mock()
		TradingCardDeckRequestDTO tradingCardDeckRequestDTO = Mock()
		Page page = Mock()

		when:
		Page pageOutput = tradingCardDeckRestQuery.query(tradingCardDeckRestBeanParams)

		then:
		1 * tradingCardDeckBaseRestMapperMock.mapBase(tradingCardDeckRestBeanParams) >> tradingCardDeckRequestDTO
		1 * pageMapperMock.fromPageSortBeanParamsToPageRequest(tradingCardDeckRestBeanParams) >> pageRequest
		1 * tradingCardDeckRepositoryMock.findMatching(tradingCardDeckRequestDTO, pageRequest) >> page
		pageOutput == page
	}

}
