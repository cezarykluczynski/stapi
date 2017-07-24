package com.cezarykluczynski.stapi.server.trading_card_set.reader

import com.cezarykluczynski.stapi.client.v1.soap.RequestSort
import com.cezarykluczynski.stapi.client.v1.soap.ResponsePage
import com.cezarykluczynski.stapi.client.v1.soap.ResponseSort
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardSetBase
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardSetBaseRequest
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardSetBaseResponse
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardSetFull
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardSetFullRequest
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardSetFullResponse
import com.cezarykluczynski.stapi.model.trading_card_set.entity.TradingCardSet
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper
import com.cezarykluczynski.stapi.server.common.validator.exceptions.MissingUIDException
import com.cezarykluczynski.stapi.server.trading_card_set.mapper.TradingCardSetBaseSoapMapper
import com.cezarykluczynski.stapi.server.trading_card_set.mapper.TradingCardSetFullSoapMapper
import com.cezarykluczynski.stapi.server.trading_card_set.query.TradingCardSetSoapQuery
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class TradingCardSetSoapReaderTest extends Specification {

	private static final String UID = 'UID'

	private TradingCardSetSoapQuery tradingCardSetSoapQueryBuilderMock

	private TradingCardSetBaseSoapMapper tradingCardSetBaseSoapMapperMock

	private TradingCardSetFullSoapMapper tradingCardSetFullSoapMapperMock

	private PageMapper pageMapperMock

	private SortMapper sortMapperMock

	private TradingCardSetSoapReader tradingCardSetSoapReader

	void setup() {
		tradingCardSetSoapQueryBuilderMock = Mock()
		tradingCardSetBaseSoapMapperMock = Mock()
		tradingCardSetFullSoapMapperMock = Mock()
		pageMapperMock = Mock()
		sortMapperMock = Mock()
		tradingCardSetSoapReader = new TradingCardSetSoapReader(tradingCardSetSoapQueryBuilderMock, tradingCardSetBaseSoapMapperMock,
				tradingCardSetFullSoapMapperMock, pageMapperMock, sortMapperMock)
	}

	void "passed base request to queryBuilder, then to mapper, and returns result"() {
		given:
		List<TradingCardSet> tradingCardSetList = Lists.newArrayList()
		Page<TradingCardSet> tradingCardSetPage = Mock()
		List<TradingCardSetBase> soapTradingCardSetList = Lists.newArrayList(new TradingCardSetBase(uid: UID))
		TradingCardSetBaseRequest tradingCardSetBaseRequest = Mock()
		ResponsePage responsePage = Mock()
		RequestSort requestSort = Mock()
		ResponseSort responseSort = Mock()

		when:
		TradingCardSetBaseResponse tradingCardSetResponse = tradingCardSetSoapReader.readBase(tradingCardSetBaseRequest)

		then:
		1 * tradingCardSetSoapQueryBuilderMock.query(tradingCardSetBaseRequest) >> tradingCardSetPage
		1 * tradingCardSetPage.content >> tradingCardSetList
		1 * pageMapperMock.fromPageToSoapResponsePage(tradingCardSetPage) >> responsePage
		1 * tradingCardSetBaseRequest.sort >> requestSort
		1 * sortMapperMock.map(requestSort) >> responseSort
		1 * tradingCardSetBaseSoapMapperMock.mapBase(tradingCardSetList) >> soapTradingCardSetList
		0 * _
		tradingCardSetResponse.tradingCardSets[0].uid == UID
		tradingCardSetResponse.page == responsePage
		tradingCardSetResponse.sort == responseSort
	}

	void "passed full request to queryBuilder, then to mapper, and returns result"() {
		given:
		TradingCardSetFull tradingCardSetFull = new TradingCardSetFull(uid: UID)
		TradingCardSet tradingCardSet = Mock()
		Page<TradingCardSet> tradingCardSetPage = Mock()
		TradingCardSetFullRequest tradingCardSetFullRequest = new TradingCardSetFullRequest(uid: UID)

		when:
		TradingCardSetFullResponse tradingCardSetFullResponse = tradingCardSetSoapReader.readFull(tradingCardSetFullRequest)

		then:
		1 * tradingCardSetSoapQueryBuilderMock.query(tradingCardSetFullRequest) >> tradingCardSetPage
		1 * tradingCardSetPage.content >> Lists.newArrayList(tradingCardSet)
		1 * tradingCardSetFullSoapMapperMock.mapFull(tradingCardSet) >> tradingCardSetFull
		0 * _
		tradingCardSetFullResponse.tradingCardSet.uid == UID
	}

	void "requires UID in full request"() {
		given:
		TradingCardSetFullRequest tradingCardSetFullRequest = Mock()

		when:
		tradingCardSetSoapReader.readFull(tradingCardSetFullRequest)

		then:
		thrown(MissingUIDException)
	}

}
