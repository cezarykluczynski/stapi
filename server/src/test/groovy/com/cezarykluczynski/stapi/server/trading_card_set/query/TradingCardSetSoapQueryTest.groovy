package com.cezarykluczynski.stapi.server.trading_card_set.query

import com.cezarykluczynski.stapi.client.v1.soap.RequestPage
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardSetBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardSetFullRequest
import com.cezarykluczynski.stapi.model.trading_card_set.dto.TradingCardSetRequestDTO
import com.cezarykluczynski.stapi.model.trading_card_set.repository.TradingCardSetRepository
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.trading_card_set.mapper.TradingCardSetBaseSoapMapper
import com.cezarykluczynski.stapi.server.trading_card_set.mapper.TradingCardSetFullSoapMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

class TradingCardSetSoapQueryTest extends Specification {

	private TradingCardSetBaseSoapMapper tradingCardSetBaseSoapMapperMock

	private TradingCardSetFullSoapMapper tradingCardSetFullSoapMapperMock

	private PageMapper pageMapperMock

	private TradingCardSetRepository tradingCardSetRepositoryMock

	private TradingCardSetSoapQuery tradingCardSetSoapQuery

	void setup() {
		tradingCardSetBaseSoapMapperMock = Mock()
		tradingCardSetFullSoapMapperMock = Mock()
		pageMapperMock = Mock()
		tradingCardSetRepositoryMock = Mock()
		tradingCardSetSoapQuery = new TradingCardSetSoapQuery(tradingCardSetBaseSoapMapperMock, tradingCardSetFullSoapMapperMock, pageMapperMock,
				tradingCardSetRepositoryMock)
	}

	void "maps TradingCardSetBaseRequest to TradingCardSetRequestDTO and to PageRequest, then calls repository, then returns result"() {
		given:
		RequestPage requestPage = Mock()
		PageRequest pageRequest = Mock()
		TradingCardSetBaseRequest tradingCardSetRequest = Mock()
		tradingCardSetRequest.page >> requestPage
		TradingCardSetRequestDTO tradingCardSetRequestDTO = Mock()
		Page page = Mock()

		when:
		Page pageOutput = tradingCardSetSoapQuery.query(tradingCardSetRequest)

		then:
		1 * tradingCardSetBaseSoapMapperMock.mapBase(tradingCardSetRequest) >> tradingCardSetRequestDTO
		1 * pageMapperMock.fromRequestPageToPageRequest(requestPage) >> pageRequest
		1 * tradingCardSetRepositoryMock.findMatching(tradingCardSetRequestDTO, pageRequest) >> page
		pageOutput == page
	}

	void "maps TradingCardSetFullRequest to TradingCardSetRequestDTO, then calls repository, then returns result"() {
		given:
		PageRequest pageRequest = Mock()
		TradingCardSetFullRequest tradingCardSetRequest = Mock()
		TradingCardSetRequestDTO tradingCardSetRequestDTO = Mock()
		Page page = Mock()

		when:
		Page pageOutput = tradingCardSetSoapQuery.query(tradingCardSetRequest)

		then:
		1 * tradingCardSetFullSoapMapperMock.mapFull(tradingCardSetRequest) >> tradingCardSetRequestDTO
		1 * pageMapperMock.defaultPageRequest >> pageRequest
		1 * tradingCardSetRepositoryMock.findMatching(tradingCardSetRequestDTO, pageRequest) >> page
		pageOutput == page
	}

}
