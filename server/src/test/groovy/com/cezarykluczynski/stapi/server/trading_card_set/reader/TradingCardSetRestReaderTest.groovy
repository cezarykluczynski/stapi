package com.cezarykluczynski.stapi.server.trading_card_set.reader

import com.cezarykluczynski.stapi.client.v1.rest.model.ResponsePage
import com.cezarykluczynski.stapi.client.v1.rest.model.ResponseSort
import com.cezarykluczynski.stapi.client.v1.rest.model.TradingCardSetBase
import com.cezarykluczynski.stapi.client.v1.rest.model.TradingCardSetBaseResponse
import com.cezarykluczynski.stapi.client.v1.rest.model.TradingCardSetFull
import com.cezarykluczynski.stapi.client.v1.rest.model.TradingCardSetFullResponse
import com.cezarykluczynski.stapi.model.trading_card_set.entity.TradingCardSet
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper
import com.cezarykluczynski.stapi.server.common.validator.exceptions.MissingUIDException
import com.cezarykluczynski.stapi.server.trading_card_set.dto.TradingCardSetRestBeanParams
import com.cezarykluczynski.stapi.server.trading_card_set.mapper.TradingCardSetBaseRestMapper
import com.cezarykluczynski.stapi.server.trading_card_set.mapper.TradingCardSetFullRestMapper
import com.cezarykluczynski.stapi.server.trading_card_set.query.TradingCardSetRestQuery
import com.google.common.collect.Lists
import org.springframework.data.domain.Page
import spock.lang.Specification

class TradingCardSetRestReaderTest extends Specification {

	private static final String UID = 'UID'
	private static final String SORT = 'SORT'

	private TradingCardSetRestQuery tradingCardSetRestQueryBuilderMock

	private TradingCardSetBaseRestMapper tradingCardSetBaseRestMapperMock

	private TradingCardSetFullRestMapper tradingCardSetFullRestMapperMock

	private PageMapper pageMapperMock

	private SortMapper sortMapperMock

	private TradingCardSetRestReader tradingCardSetRestReader

	void setup() {
		tradingCardSetRestQueryBuilderMock = Mock()
		tradingCardSetBaseRestMapperMock = Mock()
		tradingCardSetFullRestMapperMock = Mock()
		pageMapperMock = Mock()
		sortMapperMock = Mock()
		tradingCardSetRestReader = new TradingCardSetRestReader(tradingCardSetRestQueryBuilderMock, tradingCardSetBaseRestMapperMock,
				tradingCardSetFullRestMapperMock, pageMapperMock, sortMapperMock)
	}

	void "passed request to queryBuilder, then to mapper, and returns result"() {
		given:
		TradingCardSetBase tradingCardSetBase = Mock()
		TradingCardSet tradingCardSet = Mock()
		TradingCardSetRestBeanParams tradingCardSetRestBeanParams = Mock()
		List<TradingCardSetBase> restTradingCardSetList = Lists.newArrayList(tradingCardSetBase)
		List<TradingCardSet> tradingCardSetList = Lists.newArrayList(tradingCardSet)
		Page<TradingCardSet> tradingCardSetPage = Mock()
		ResponsePage responsePage = Mock()
		ResponseSort responseSort = Mock()

		when:
		TradingCardSetBaseResponse tradingCardSetResponseOutput = tradingCardSetRestReader.readBase(tradingCardSetRestBeanParams)

		then:
		1 * tradingCardSetRestQueryBuilderMock.query(tradingCardSetRestBeanParams) >> tradingCardSetPage
		1 * pageMapperMock.fromPageToRestResponsePage(tradingCardSetPage) >> responsePage
		1 * tradingCardSetRestBeanParams.sort >> SORT
		1 * sortMapperMock.map(SORT) >> responseSort
		1 * tradingCardSetPage.content >> tradingCardSetList
		1 * tradingCardSetBaseRestMapperMock.mapBase(tradingCardSetList) >> restTradingCardSetList
		0 * _
		tradingCardSetResponseOutput.tradingCardSets == restTradingCardSetList
		tradingCardSetResponseOutput.page == responsePage
		tradingCardSetResponseOutput.sort == responseSort
	}

	void "passed UID to queryBuilder, then to mapper, and returns result"() {
		given:
		TradingCardSetFull tradingCardSetFull = Mock()
		TradingCardSet tradingCardSet = Mock()
		List<TradingCardSet> tradingCardSetList = Lists.newArrayList(tradingCardSet)
		Page<TradingCardSet> tradingCardSetPage = Mock()

		when:
		TradingCardSetFullResponse tradingCardSetResponseOutput = tradingCardSetRestReader.readFull(UID)

		then:
		1 * tradingCardSetRestQueryBuilderMock.query(_ as TradingCardSetRestBeanParams) >> { TradingCardSetRestBeanParams tradingCardSetRestBeanParams ->
			assert tradingCardSetRestBeanParams.uid == UID
			tradingCardSetPage
		}
		1 * tradingCardSetPage.content >> tradingCardSetList
		1 * tradingCardSetFullRestMapperMock.mapFull(tradingCardSet) >> tradingCardSetFull
		0 * _
		tradingCardSetResponseOutput.tradingCardSet == tradingCardSetFull
	}

	void "requires UID in full request"() {
		when:
		tradingCardSetRestReader.readFull(null)

		then:
		thrown(MissingUIDException)
	}

}
