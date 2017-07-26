package com.cezarykluczynski.stapi.server.trading_card_deck.query

import com.cezarykluczynski.stapi.client.v1.soap.RequestPage
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardDeckBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardDeckFullRequest
import com.cezarykluczynski.stapi.model.trading_card_deck.dto.TradingCardDeckRequestDTO
import com.cezarykluczynski.stapi.model.trading_card_deck.repository.TradingCardDeckRepository
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.trading_card_deck.mapper.TradingCardDeckBaseSoapMapper
import com.cezarykluczynski.stapi.server.trading_card_deck.mapper.TradingCardDeckFullSoapMapper
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import spock.lang.Specification

class TradingCardDeckSoapQueryTest extends Specification {

	private TradingCardDeckBaseSoapMapper tradingCardDeckBaseSoapMapperMock

	private TradingCardDeckFullSoapMapper tradingCardDeckFullSoapMapperMock

	private PageMapper pageMapperMock

	private TradingCardDeckRepository tradingCardDeckRepositoryMock

	private TradingCardDeckSoapQuery tradingCardDeckSoapQuery

	void setup() {
		tradingCardDeckBaseSoapMapperMock = Mock()
		tradingCardDeckFullSoapMapperMock = Mock()
		pageMapperMock = Mock()
		tradingCardDeckRepositoryMock = Mock()
		tradingCardDeckSoapQuery = new TradingCardDeckSoapQuery(tradingCardDeckBaseSoapMapperMock, tradingCardDeckFullSoapMapperMock, pageMapperMock,
				tradingCardDeckRepositoryMock)
	}

	void "maps TradingCardDeckBaseRequest to TradingCardDeckRequestDTO and to PageRequest, then calls repository, then returns result"() {
		given:
		RequestPage requestPage = Mock()
		PageRequest pageRequest = Mock()
		TradingCardDeckBaseRequest tradingCardDeckRequest = Mock()
		tradingCardDeckRequest.page >> requestPage
		TradingCardDeckRequestDTO tradingCardDeckRequestDTO = Mock()
		Page page = Mock()

		when:
		Page pageOutput = tradingCardDeckSoapQuery.query(tradingCardDeckRequest)

		then:
		1 * tradingCardDeckBaseSoapMapperMock.mapBase(tradingCardDeckRequest) >> tradingCardDeckRequestDTO
		1 * pageMapperMock.fromRequestPageToPageRequest(requestPage) >> pageRequest
		1 * tradingCardDeckRepositoryMock.findMatching(tradingCardDeckRequestDTO, pageRequest) >> page
		pageOutput == page
	}

	void "maps TradingCardDeckFullRequest to TradingCardDeckRequestDTO, then calls repository, then returns result"() {
		given:
		PageRequest pageRequest = Mock()
		TradingCardDeckFullRequest tradingCardDeckRequest = Mock()
		TradingCardDeckRequestDTO tradingCardDeckRequestDTO = Mock()
		Page page = Mock()

		when:
		Page pageOutput = tradingCardDeckSoapQuery.query(tradingCardDeckRequest)

		then:
		1 * tradingCardDeckFullSoapMapperMock.mapFull(tradingCardDeckRequest) >> tradingCardDeckRequestDTO
		1 * pageMapperMock.defaultPageRequest >> pageRequest
		1 * tradingCardDeckRepositoryMock.findMatching(tradingCardDeckRequestDTO, pageRequest) >> page
		pageOutput == page
	}

}
