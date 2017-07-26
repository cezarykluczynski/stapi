package com.cezarykluczynski.stapi.server.trading_card_deck.reader

import com.cezarykluczynski.stapi.client.v1.rest.model.ResponsePage
import com.cezarykluczynski.stapi.client.v1.rest.model.ResponseSort
import com.cezarykluczynski.stapi.client.v1.rest.model.TradingCardDeckBase
import com.cezarykluczynski.stapi.client.v1.rest.model.TradingCardDeckBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.TradingCardDeckFull
import com.cezarykluczynski.stapi.client.v1.rest.model.TradingCardDeckFullResponse
import com.cezarykluczynski.stapi.model.trading_card_deck.entity.TradingCardDeck
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper
import com.cezarykluczynski.stapi.server.common.validator.exceptions.MissingUIDException
import com.cezarykluczynski.stapi.server.trading_card_deck.dto.TradingCardDeckRestBeanParams
import com.cezarykluczynski.stapi.server.trading_card_deck.mapper.TradingCardDeckBaseRestMapper
import com.cezarykluczynski.stapi.server.trading_card_deck.mapper.TradingCardDeckFullRestMapper
import com.cezarykluczynski.stapi.server.trading_card_deck.query.TradingCardDeckRestQuery
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class TradingCardDeckRestReaderTest extends Specification {

	private static final String UID = 'UID'
	private static final String SORT = 'SORT'

	private TradingCardDeckRestQuery tradingCardDeckRestQueryBuilderMock

	private TradingCardDeckBaseRestMapper tradingCardDeckBaseRestMapperMock

	private TradingCardDeckFullRestMapper tradingCardDeckFullRestMapperMock

	private PageMapper pageMapperMock

	private SortMapper sortMapperMock

	private TradingCardDeckRestReader tradingCardDeckRestReader

	void setup() {
		tradingCardDeckRestQueryBuilderMock = Mock()
		tradingCardDeckBaseRestMapperMock = Mock()
		tradingCardDeckFullRestMapperMock = Mock()
		pageMapperMock = Mock()
		sortMapperMock = Mock()
		tradingCardDeckRestReader = new TradingCardDeckRestReader(tradingCardDeckRestQueryBuilderMock, tradingCardDeckBaseRestMapperMock,
				tradingCardDeckFullRestMapperMock, pageMapperMock, sortMapperMock)
	}

	void "passed request to queryBuilder, then to mapper, and returns result"() {
		given:
		TradingCardDeckBase tradingCardDeckBase = Mock()
		TradingCardDeck tradingCardDeck = Mock()
		TradingCardDeckRestBeanParams tradingCardDeckRestBeanParams = Mock()
		List<TradingCardDeckBase> restTradingCardDeckList = Lists.newArrayList(tradingCardDeckBase)
		List<TradingCardDeck> tradingCardDeckList = Lists.newArrayList(tradingCardDeck)
		Page<TradingCardDeck> tradingCardDeckPage = Mock()
		ResponsePage responsePage = Mock()
		ResponseSort responseSort = Mock()

		when:
		TradingCardDeckBaseResponse tradingCardDeckResponseOutput = tradingCardDeckRestReader.readBase(tradingCardDeckRestBeanParams)

		then:
		1 * tradingCardDeckRestQueryBuilderMock.query(tradingCardDeckRestBeanParams) >> tradingCardDeckPage
		1 * pageMapperMock.fromPageToRestResponsePage(tradingCardDeckPage) >> responsePage
		1 * tradingCardDeckRestBeanParams.sort >> SORT
		1 * sortMapperMock.map(SORT) >> responseSort
		1 * tradingCardDeckPage.content >> tradingCardDeckList
		1 * tradingCardDeckBaseRestMapperMock.mapBase(tradingCardDeckList) >> restTradingCardDeckList
		0 * _
		tradingCardDeckResponseOutput.tradingCardDecks == restTradingCardDeckList
		tradingCardDeckResponseOutput.page == responsePage
		tradingCardDeckResponseOutput.sort == responseSort
	}

	void "passed UID to queryBuilder, then to mapper, and returns result"() {
		given:
		TradingCardDeckFull tradingCardDeckFull = Mock()
		TradingCardDeck tradingCardDeck = Mock()
		List<TradingCardDeck> tradingCardDeckList = Lists.newArrayList(tradingCardDeck)
		Page<TradingCardDeck> tradingCardDeckPage = Mock()

		when:
		TradingCardDeckFullResponse tradingCardDeckResponseOutput = tradingCardDeckRestReader.readFull(UID)

		then:
		1 * tradingCardDeckRestQueryBuilderMock.query(_ as TradingCardDeckRestBeanParams) >> {
				TradingCardDeckRestBeanParams tradingCardDeckRestBeanParams ->
			assert tradingCardDeckRestBeanParams.uid == UID
			tradingCardDeckPage
		}
		1 * tradingCardDeckPage.content >> tradingCardDeckList
		1 * tradingCardDeckFullRestMapperMock.mapFull(tradingCardDeck) >> tradingCardDeckFull
		0 * _
		tradingCardDeckResponseOutput.tradingCardDeck == tradingCardDeckFull
	}

	void "requires UID in full request"() {
		when:
		tradingCardDeckRestReader.readFull(null)

		then:
		thrown(MissingUIDException)
	}

}
