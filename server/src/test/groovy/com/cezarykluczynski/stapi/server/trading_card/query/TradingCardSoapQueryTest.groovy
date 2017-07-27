package com.cezarykluczynski.stapi.server.trading_card.query

import com.cezarykluczynski.stapi.client.v1.soap.RequestPage
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardFullRequest
import com.cezarykluczynski.stapi.model.trading_card.dto.TradingCardRequestDTO
import com.cezarykluczynski.stapi.model.trading_card.repository.TradingCardRepository
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.trading_card.mapper.TradingCardBaseSoapMapper
import com.cezarykluczynski.stapi.server.trading_card.mapper.TradingCardFullSoapMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

class TradingCardSoapQueryTest extends Specification {

	private TradingCardBaseSoapMapper tradingCardBaseSoapMapperMock

	private TradingCardFullSoapMapper tradingCardFullSoapMapperMock

	private PageMapper pageMapperMock

	private TradingCardRepository tradingCardRepositoryMock

	private TradingCardSoapQuery tradingCardSoapQuery

	void setup() {
		tradingCardBaseSoapMapperMock = Mock()
		tradingCardFullSoapMapperMock = Mock()
		pageMapperMock = Mock()
		tradingCardRepositoryMock = Mock()
		tradingCardSoapQuery = new TradingCardSoapQuery(tradingCardBaseSoapMapperMock, tradingCardFullSoapMapperMock, pageMapperMock,
				tradingCardRepositoryMock)
	}

	void "maps TradingCardBaseRequest to TradingCardRequestDTO and to PageRequest, then calls repository, then returns result"() {
		given:
		RequestPage requestPage = Mock()
		PageRequest pageRequest = Mock()
		TradingCardBaseRequest tradingCardRequest = Mock()
		tradingCardRequest.page >> requestPage
		TradingCardRequestDTO tradingCardRequestDTO = Mock()
		Page page = Mock()

		when:
		Page pageOutput = tradingCardSoapQuery.query(tradingCardRequest)

		then:
		1 * tradingCardBaseSoapMapperMock.mapBase(tradingCardRequest) >> tradingCardRequestDTO
		1 * pageMapperMock.fromRequestPageToPageRequest(requestPage) >> pageRequest
		1 * tradingCardRepositoryMock.findMatching(tradingCardRequestDTO, pageRequest) >> page
		pageOutput == page
	}

	void "maps TradingCardFullRequest to TradingCardRequestDTO, then calls repository, then returns result"() {
		given:
		PageRequest pageRequest = Mock()
		TradingCardFullRequest tradingCardRequest = Mock()
		TradingCardRequestDTO tradingCardRequestDTO = Mock()
		Page page = Mock()

		when:
		Page pageOutput = tradingCardSoapQuery.query(tradingCardRequest)

		then:
		1 * tradingCardFullSoapMapperMock.mapFull(tradingCardRequest) >> tradingCardRequestDTO
		1 * pageMapperMock.defaultPageRequest >> pageRequest
		1 * tradingCardRepositoryMock.findMatching(tradingCardRequestDTO, pageRequest) >> page
		pageOutput == page
	}

}
