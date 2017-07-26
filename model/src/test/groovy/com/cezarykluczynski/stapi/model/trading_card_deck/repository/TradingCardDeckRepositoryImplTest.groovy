package com.cezarykluczynski.stapi.model.trading_card_deck.repository

import com.cezarykluczynski.stapi.model.common.dto.RequestSortDTO
import com.cezarykluczynski.stapi.model.common.query.QueryBuilder
import com.cezarykluczynski.stapi.model.trading_card_deck.dto.TradingCardDeckRequestDTO
import com.cezarykluczynski.stapi.model.trading_card_deck.entity.TradingCardDeck
import com.cezarykluczynski.stapi.model.trading_card_deck.entity.TradingCardDeck_
import com.cezarykluczynski.stapi.model.trading_card_deck.query.TradingCardDeckQueryBuilderFactory
import com.cezarykluczynski.stapi.util.AbstractTradingCardDeckTest
import com.google.common.collect.Lists
import com.google.common.collect.Sets
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

class TradingCardDeckRepositoryImplTest extends AbstractTradingCardDeckTest {

	private static final String UID = 'ABCD0123456789'
	private static final RequestSortDTO SORT = new RequestSortDTO()

	private TradingCardDeckQueryBuilderFactory tradingCardDeckQueryBuilderFactoryMock

	private TradingCardDeckRepositoryImpl tradingCardDeckRepositoryImpl

	private QueryBuilder<TradingCardDeck> tradingCardDeckQueryBuilder

	private Pageable pageable

	private TradingCardDeckRequestDTO tradingCardDeckRequestDTO

	private TradingCardDeck tradingCardDeck

	private Page page

	void setup() {
		tradingCardDeckQueryBuilderFactoryMock = Mock()
		tradingCardDeckRepositoryImpl = new TradingCardDeckRepositoryImpl(tradingCardDeckQueryBuilderFactoryMock)
		tradingCardDeckQueryBuilder = Mock()
		pageable = Mock()
		tradingCardDeckRequestDTO = Mock()
		page = Mock()
		tradingCardDeck = Mock()
	}

	void "query is built and performed"() {
		when:
		Page pageOutput = tradingCardDeckRepositoryImpl.findMatching(tradingCardDeckRequestDTO, pageable)

		then: 'criteria builder is retrieved'
		1 * tradingCardDeckQueryBuilderFactoryMock.createQueryBuilder(pageable) >> tradingCardDeckQueryBuilder

		then: 'uid is retrieved, and it is not null'
		1 * tradingCardDeckRequestDTO.uid >> UID
		1 * tradingCardDeckQueryBuilder.equal(TradingCardDeck_.uid, UID)

		then: 'string criteria are set'
		1 * tradingCardDeckRequestDTO.name >> NAME
		1 * tradingCardDeckQueryBuilder.like(TradingCardDeck_.name, NAME)

		then: 'property join criteria are set'
		1 * tradingCardDeckRequestDTO.tradingCardSetUid >> TRADING_CARD_SET_UID
		1 * tradingCardDeckQueryBuilder.joinPropertyEqual(TradingCardDeck_.tradingCardSet, 'uid', TRADING_CARD_SET_UID)

		then: 'sort is set'
		1 * tradingCardDeckRequestDTO.sort >> SORT
		1 * tradingCardDeckQueryBuilder.setSort(SORT)

		then: 'fetch is performed'
		1 * tradingCardDeckQueryBuilder.fetch(TradingCardDeck_.tradingCardSet)
		1 * tradingCardDeckQueryBuilder.fetch(TradingCardDeck_.tradingCards, true)

		then: 'page is retrieved'
		1 * tradingCardDeckQueryBuilder.findPage() >> page

		then: 'page is returned'
		pageOutput == page

		then: 'no other interactions are expected'
		0 * _
	}

	void "proxies are cleared when no related entities should be fetched"() {
		when:
		Page pageOutput = tradingCardDeckRepositoryImpl.findMatching(tradingCardDeckRequestDTO, pageable)

		then: 'criteria builder is retrieved'
		1 * tradingCardDeckQueryBuilderFactoryMock.createQueryBuilder(pageable) >> tradingCardDeckQueryBuilder

		then: 'uid criteria is set to null'
		1 * tradingCardDeckRequestDTO.uid >> null

		then: 'page is searched for and returned'
		1 * tradingCardDeckQueryBuilder.findPage() >> page

		then: 'proxies are cleared'
		1 * page.content >> Lists.newArrayList(tradingCardDeck)
		1 * tradingCardDeck.setTradingCards(Sets.newHashSet())
		pageOutput == page
	}

}
