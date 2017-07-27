package com.cezarykluczynski.stapi.server.trading_card.reader

import com.cezarykluczynski.stapi.client.v1.soap.RequestSort
import com.cezarykluczynski.stapi.client.v1.soap.ResponsePage
import com.cezarykluczynski.stapi.client.v1.soap.ResponseSort
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardBase
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardFull
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardFullResponse
import com.cezarykluczynski.stapi.model.trading_card.entity.TradingCard
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper
import com.cezarykluczynski.stapi.server.common.validator.exceptions.MissingUIDException
import com.cezarykluczynski.stapi.server.trading_card.mapper.TradingCardBaseSoapMapper
import com.cezarykluczynski.stapi.server.trading_card.mapper.TradingCardFullSoapMapper
import com.cezarykluczynski.stapi.server.trading_card.query.TradingCardSoapQuery
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class TradingCardSoapReaderTest extends Specification {

	private static final String UID = 'UID'

	private TradingCardSoapQuery tradingCardSoapQueryBuilderMock

	private TradingCardBaseSoapMapper tradingCardBaseSoapMapperMock

	private TradingCardFullSoapMapper tradingCardFullSoapMapperMock

	private PageMapper pageMapperMock

	private SortMapper sortMapperMock

	private TradingCardSoapReader tradingCardSoapReader

	void setup() {
		tradingCardSoapQueryBuilderMock = Mock()
		tradingCardBaseSoapMapperMock = Mock()
		tradingCardFullSoapMapperMock = Mock()
		pageMapperMock = Mock()
		sortMapperMock = Mock()
		tradingCardSoapReader = new TradingCardSoapReader(tradingCardSoapQueryBuilderMock, tradingCardBaseSoapMapperMock,
				tradingCardFullSoapMapperMock, pageMapperMock, sortMapperMock)
	}

	void "passed base request to queryBuilder, then to mapper, and returns result"() {
		given:
		List<TradingCard> tradingCardList = Lists.newArrayList()
		Page<TradingCard> tradingCardPage = Mock()
		List<TradingCardBase> soapTradingCardList = Lists.newArrayList(new TradingCardBase(uid: UID))
		TradingCardBaseRequest tradingCardBaseRequest = Mock()
		ResponsePage responsePage = Mock()
		RequestSort requestSort = Mock()
		ResponseSort responseSort = Mock()

		when:
		TradingCardBaseResponse tradingCardResponse = tradingCardSoapReader.readBase(tradingCardBaseRequest)

		then:
		1 * tradingCardSoapQueryBuilderMock.query(tradingCardBaseRequest) >> tradingCardPage
		1 * tradingCardPage.content >> tradingCardList
		1 * pageMapperMock.fromPageToSoapResponsePage(tradingCardPage) >> responsePage
		1 * tradingCardBaseRequest.sort >> requestSort
		1 * sortMapperMock.map(requestSort) >> responseSort
		1 * tradingCardBaseSoapMapperMock.mapBase(tradingCardList) >> soapTradingCardList
		0 * _
		tradingCardResponse.tradingCards[0].uid == UID
		tradingCardResponse.page == responsePage
		tradingCardResponse.sort == responseSort
	}

	void "passed full request to queryBuilder, then to mapper, and returns result"() {
		given:
		TradingCardFull tradingCardFull = new TradingCardFull(uid: UID)
		TradingCard tradingCard = Mock()
		Page<TradingCard> tradingCardPage = Mock()
		TradingCardFullRequest tradingCardFullRequest = new TradingCardFullRequest(uid: UID)

		when:
		TradingCardFullResponse tradingCardFullResponse = tradingCardSoapReader.readFull(tradingCardFullRequest)

		then:
		1 * tradingCardSoapQueryBuilderMock.query(tradingCardFullRequest) >> tradingCardPage
		1 * tradingCardPage.content >> Lists.newArrayList(tradingCard)
		1 * tradingCardFullSoapMapperMock.mapFull(tradingCard) >> tradingCardFull
		0 * _
		tradingCardFullResponse.tradingCard.uid == UID
	}

	void "requires UID in full request"() {
		given:
		TradingCardFullRequest tradingCardFullRequest = Mock()

		when:
		tradingCardSoapReader.readFull(tradingCardFullRequest)

		then:
		thrown(MissingUIDException)
	}

}
