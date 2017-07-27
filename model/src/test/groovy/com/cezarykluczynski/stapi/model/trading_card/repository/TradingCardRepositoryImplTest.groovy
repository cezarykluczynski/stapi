package com.cezarykluczynski.stapi.model.trading_card.repository

import com.cezarykluczynski.stapi.model.common.dto.RequestSortDTO
import com.cezarykluczynski.stapi.model.common.query.QueryBuilder
import com.cezarykluczynski.stapi.model.trading_card.dto.TradingCardRequestDTO
import com.cezarykluczynski.stapi.model.trading_card.entity.TradingCard
import com.cezarykluczynski.stapi.model.trading_card.entity.TradingCard_
import com.cezarykluczynski.stapi.model.trading_card.query.TradingCardQueryBuilderFactory
import com.cezarykluczynski.stapi.util.AbstractTradingCardTest
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

class TradingCardRepositoryImplTest extends AbstractTradingCardTest {

	private static final String UID = 'ABCD0123456789'
	private static final RequestSortDTO SORT = new RequestSortDTO()

	private TradingCardQueryBuilderFactory tradingCardQueryBuilderFactoryMock

	private TradingCardRepositoryImpl tradingCardRepositoryImpl

	private QueryBuilder<TradingCard> tradingCardQueryBuilder

	private Pageable pageable

	private TradingCardRequestDTO tradingCardRequestDTO

	private TradingCard tradingCard

	private Page page

	void setup() {
		tradingCardQueryBuilderFactoryMock = Mock()
		tradingCardRepositoryImpl = new TradingCardRepositoryImpl(tradingCardQueryBuilderFactoryMock)
		tradingCardQueryBuilder = Mock()
		pageable = Mock()
		tradingCardRequestDTO = Mock()
		page = Mock()
		tradingCard = Mock()
	}

	void "query is built and performed"() {
		when:
		Page pageOutput = tradingCardRepositoryImpl.findMatching(tradingCardRequestDTO, pageable)

		then: 'criteria builder is retrieved'
		1 * tradingCardQueryBuilderFactoryMock.createQueryBuilder(pageable) >> tradingCardQueryBuilder

		then: 'uid is retrieved, and it is not null'
		1 * tradingCardRequestDTO.uid >> UID
		1 * tradingCardQueryBuilder.equal(TradingCard_.uid, UID)

		then: 'string criteria are set'
		1 * tradingCardRequestDTO.name >> NAME
		1 * tradingCardQueryBuilder.like(TradingCard_.name, NAME)

		then: 'property join criteria are set'
		1 * tradingCardRequestDTO.tradingCardDeckUid >> TRADING_CARD_DECK_UID
		1 * tradingCardQueryBuilder.joinPropertyEqual(TradingCard_.tradingCardDeck, 'uid', TRADING_CARD_DECK_UID)
		1 * tradingCardRequestDTO.tradingCardSetUid >> TRADING_CARD_SET_UID
		1 * tradingCardQueryBuilder.joinPropertyEqual(TradingCard_.tradingCardSet, 'uid', TRADING_CARD_SET_UID)

		then: 'sort is set'
		1 * tradingCardRequestDTO.sort >> SORT
		1 * tradingCardQueryBuilder.setSort(SORT)

		then: 'fetch is performed'
		1 * tradingCardQueryBuilder.fetch(TradingCard_.tradingCardSet)

		then: 'page is retrieved'
		1 * tradingCardQueryBuilder.findPage() >> page

		then: 'page is returned'
		pageOutput == page

		then: 'no other interactions are expected'
		0 * _
	}

}
