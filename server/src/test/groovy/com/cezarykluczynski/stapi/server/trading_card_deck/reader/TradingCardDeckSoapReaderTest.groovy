package com.cezarykluczynski.stapi.server.trading_card_deck.reader

import com.cezarykluczynski.stapi.client.v1.soap.RequestSort
import com.cezarykluczynski.stapi.client.v1.soap.ResponsePage
import com.cezarykluczynski.stapi.client.v1.soap.ResponseSort
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardDeckBase
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardDeckBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardDeckBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardDeckFull
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardDeckFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardDeckFullResponse
import com.cezarykluczynski.stapi.model.trading_card_deck.entity.TradingCardDeck
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper
import com.cezarykluczynski.stapi.server.common.validator.exceptions.MissingUIDException
import com.cezarykluczynski.stapi.server.trading_card_deck.mapper.TradingCardDeckBaseSoapMapper
import com.cezarykluczynski.stapi.server.trading_card_deck.mapper.TradingCardDeckFullSoapMapper
import com.cezarykluczynski.stapi.server.trading_card_deck.query.TradingCardDeckSoapQuery
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class TradingCardDeckSoapReaderTest extends Specification {

	private static final String UID = 'UID'

	private TradingCardDeckSoapQuery tradingCardDeckSoapQueryBuilderMock

	private TradingCardDeckBaseSoapMapper tradingCardDeckBaseSoapMapperMock

	private TradingCardDeckFullSoapMapper tradingCardDeckFullSoapMapperMock

	private PageMapper pageMapperMock

	private SortMapper sortMapperMock

	private TradingCardDeckSoapReader tradingCardDeckSoapReader

	void setup() {
		tradingCardDeckSoapQueryBuilderMock = Mock()
		tradingCardDeckBaseSoapMapperMock = Mock()
		tradingCardDeckFullSoapMapperMock = Mock()
		pageMapperMock = Mock()
		sortMapperMock = Mock()
		tradingCardDeckSoapReader = new TradingCardDeckSoapReader(tradingCardDeckSoapQueryBuilderMock, tradingCardDeckBaseSoapMapperMock,
				tradingCardDeckFullSoapMapperMock, pageMapperMock, sortMapperMock)
	}

	void "passed base request to queryBuilder, then to mapper, and returns result"() {
		given:
		List<TradingCardDeck> tradingCardDeckList = Lists.newArrayList()
		Page<TradingCardDeck> tradingCardDeckPage = Mock()
		List<TradingCardDeckBase> soapTradingCardDeckList = Lists.newArrayList(new TradingCardDeckBase(uid: UID))
		TradingCardDeckBaseRequest tradingCardDeckBaseRequest = Mock()
		ResponsePage responsePage = Mock()
		RequestSort requestSort = Mock()
		ResponseSort responseSort = Mock()

		when:
		TradingCardDeckBaseResponse tradingCardDeckResponse = tradingCardDeckSoapReader.readBase(tradingCardDeckBaseRequest)

		then:
		1 * tradingCardDeckSoapQueryBuilderMock.query(tradingCardDeckBaseRequest) >> tradingCardDeckPage
		1 * tradingCardDeckPage.content >> tradingCardDeckList
		1 * pageMapperMock.fromPageToSoapResponsePage(tradingCardDeckPage) >> responsePage
		1 * tradingCardDeckBaseRequest.sort >> requestSort
		1 * sortMapperMock.map(requestSort) >> responseSort
		1 * tradingCardDeckBaseSoapMapperMock.mapBase(tradingCardDeckList) >> soapTradingCardDeckList
		0 * _
		tradingCardDeckResponse.tradingCardDecks[0].uid == UID
		tradingCardDeckResponse.page == responsePage
		tradingCardDeckResponse.sort == responseSort
	}

	void "passed full request to queryBuilder, then to mapper, and returns result"() {
		given:
		TradingCardDeckFull tradingCardDeckFull = new TradingCardDeckFull(uid: UID)
		TradingCardDeck tradingCardDeck = Mock()
		Page<TradingCardDeck> tradingCardDeckPage = Mock()
		TradingCardDeckFullRequest tradingCardDeckFullRequest = new TradingCardDeckFullRequest(uid: UID)

		when:
		TradingCardDeckFullResponse tradingCardDeckFullResponse = tradingCardDeckSoapReader.readFull(tradingCardDeckFullRequest)

		then:
		1 * tradingCardDeckSoapQueryBuilderMock.query(tradingCardDeckFullRequest) >> tradingCardDeckPage
		1 * tradingCardDeckPage.content >> Lists.newArrayList(tradingCardDeck)
		1 * tradingCardDeckFullSoapMapperMock.mapFull(tradingCardDeck) >> tradingCardDeckFull
		0 * _
		tradingCardDeckFullResponse.tradingCardDeck.uid == UID
	}

	void "requires UID in full request"() {
		given:
		TradingCardDeckFullRequest tradingCardDeckFullRequest = Mock()

		when:
		tradingCardDeckSoapReader.readFull(tradingCardDeckFullRequest)

		then:
		thrown(MissingUIDException)
	}

}
