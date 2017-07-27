package com.cezarykluczynski.stapi.server.trading_card.reader

import com.cezarykluczynski.stapi.client.v1.rest.model.ResponsePage
import com.cezarykluczynski.stapi.client.v1.rest.model.ResponseSort
import com.cezarykluczynski.stapi.client.v1.rest.model.TradingCardBase
import com.cezarykluczynski.stapi.client.v1.rest.model.TradingCardBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.TradingCardFull
import com.cezarykluczynski.stapi.client.v1.rest.model.TradingCardFullResponse
import com.cezarykluczynski.stapi.model.trading_card.entity.TradingCard
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper
import com.cezarykluczynski.stapi.server.common.validator.exceptions.MissingUIDException
import com.cezarykluczynski.stapi.server.trading_card.dto.TradingCardRestBeanParams
import com.cezarykluczynski.stapi.server.trading_card.mapper.TradingCardBaseRestMapper
import com.cezarykluczynski.stapi.server.trading_card.mapper.TradingCardFullRestMapper
import com.cezarykluczynski.stapi.server.trading_card.query.TradingCardRestQuery
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class TradingCardRestReaderTest extends Specification {

	private static final String UID = 'UID'
	private static final String SORT = 'SORT'

	private TradingCardRestQuery tradingCardRestQueryBuilderMock

	private TradingCardBaseRestMapper tradingCardBaseRestMapperMock

	private TradingCardFullRestMapper tradingCardFullRestMapperMock

	private PageMapper pageMapperMock

	private SortMapper sortMapperMock

	private TradingCardRestReader tradingCardRestReader

	void setup() {
		tradingCardRestQueryBuilderMock = Mock()
		tradingCardBaseRestMapperMock = Mock()
		tradingCardFullRestMapperMock = Mock()
		pageMapperMock = Mock()
		sortMapperMock = Mock()
		tradingCardRestReader = new TradingCardRestReader(tradingCardRestQueryBuilderMock, tradingCardBaseRestMapperMock,
				tradingCardFullRestMapperMock, pageMapperMock, sortMapperMock)
	}

	void "passed request to queryBuilder, then to mapper, and returns result"() {
		given:
		TradingCardBase tradingCardBase = Mock()
		TradingCard tradingCard = Mock()
		TradingCardRestBeanParams tradingCardRestBeanParams = Mock()
		List<TradingCardBase> restTradingCardList = Lists.newArrayList(tradingCardBase)
		List<TradingCard> tradingCardList = Lists.newArrayList(tradingCard)
		Page<TradingCard> tradingCardPage = Mock()
		ResponsePage responsePage = Mock()
		ResponseSort responseSort = Mock()

		when:
		TradingCardBaseResponse tradingCardResponseOutput = tradingCardRestReader.readBase(tradingCardRestBeanParams)

		then:
		1 * tradingCardRestQueryBuilderMock.query(tradingCardRestBeanParams) >> tradingCardPage
		1 * pageMapperMock.fromPageToRestResponsePage(tradingCardPage) >> responsePage
		1 * tradingCardRestBeanParams.sort >> SORT
		1 * sortMapperMock.map(SORT) >> responseSort
		1 * tradingCardPage.content >> tradingCardList
		1 * tradingCardBaseRestMapperMock.mapBase(tradingCardList) >> restTradingCardList
		0 * _
		tradingCardResponseOutput.tradingCards == restTradingCardList
		tradingCardResponseOutput.page == responsePage
		tradingCardResponseOutput.sort == responseSort
	}

	void "passed UID to queryBuilder, then to mapper, and returns result"() {
		given:
		TradingCardFull tradingCardFull = Mock()
		TradingCard tradingCard = Mock()
		List<TradingCard> tradingCardList = Lists.newArrayList(tradingCard)
		Page<TradingCard> tradingCardPage = Mock()

		when:
		TradingCardFullResponse tradingCardResponseOutput = tradingCardRestReader.readFull(UID)

		then:
		1 * tradingCardRestQueryBuilderMock.query(_ as TradingCardRestBeanParams) >> {
				TradingCardRestBeanParams tradingCardRestBeanParams ->
			assert tradingCardRestBeanParams.uid == UID
			tradingCardPage
		}
		1 * tradingCardPage.content >> tradingCardList
		1 * tradingCardFullRestMapperMock.mapFull(tradingCard) >> tradingCardFull
		0 * _
		tradingCardResponseOutput.tradingCard == tradingCardFull
	}

	void "requires UID in full request"() {
		when:
		tradingCardRestReader.readFull(null)

		then:
		thrown(MissingUIDException)
	}

}
