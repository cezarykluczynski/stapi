package com.cezarykluczynski.stapi.model.trading_card_set.repository

import com.cezarykluczynski.stapi.model.common.dto.RequestSortDTO
import com.cezarykluczynski.stapi.model.common.query.QueryBuilder
import com.cezarykluczynski.stapi.model.trading_card_set.dto.TradingCardSetRequestDTO
import com.cezarykluczynski.stapi.model.trading_card_set.entity.TradingCardSet
import com.cezarykluczynski.stapi.model.trading_card_set.entity.TradingCardSet_
import com.cezarykluczynski.stapi.model.trading_card_set.entity.enums.ProductionRunUnit
import com.cezarykluczynski.stapi.model.trading_card_set.query.TradingCardSetQueryBuilderFactory
import com.cezarykluczynski.stapi.util.AbstractTradingCardSetTest
import com.google.common.collect.Lists
import com.google.common.collect.Sets
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

class TradingCardSetRepositoryImplTest extends AbstractTradingCardSetTest {

	private static final String UID = 'ABCD0123456789'
	private static final RequestSortDTO SORT = new RequestSortDTO()
	private static final ProductionRunUnit PRODUCTION_RUN_UNIT = ProductionRunUnit.BOX

	private TradingCardSetQueryBuilderFactory tradingCardSetQueryBuilderFactoryMock

	private TradingCardSetRepositoryImpl tradingCardSetRepositoryImpl

	private QueryBuilder<TradingCardSet> tradingCardSetQueryBuilder

	private Pageable pageable

	private TradingCardSetRequestDTO tradingCardSetRequestDTO

	private TradingCardSet tradingCardSet

	private Page page

	void setup() {
		tradingCardSetQueryBuilderFactoryMock = Mock()
		tradingCardSetRepositoryImpl = new TradingCardSetRepositoryImpl(tradingCardSetQueryBuilderFactoryMock)
		tradingCardSetQueryBuilder = Mock()
		pageable = Mock()
		tradingCardSetRequestDTO = Mock()
		page = Mock()
		tradingCardSet = Mock()
	}

	void "query is built and performed"() {
		when:
		Page pageOutput = tradingCardSetRepositoryImpl.findMatching(tradingCardSetRequestDTO, pageable)

		then: 'criteria builder is retrieved'
		1 * tradingCardSetQueryBuilderFactoryMock.createQueryBuilder(pageable) >> tradingCardSetQueryBuilder

		then: 'uid is retrieved, and it is not null'
		1 * tradingCardSetRequestDTO.uid >> UID
		1 * tradingCardSetQueryBuilder.equal(TradingCardSet_.uid, UID)

		then: 'string criteria are set'
		1 * tradingCardSetRequestDTO.name >> NAME
		1 * tradingCardSetQueryBuilder.like(TradingCardSet_.name, NAME)

		then: 'integer criteria are set'
		1 * tradingCardSetRequestDTO.releaseYearFrom >> RELEASE_YEAR_FROM
		1 * tradingCardSetRequestDTO.releaseYearTo >> RELEASE_YEAR_TO
		1 * tradingCardSetQueryBuilder.between(TradingCardSet_.releaseYear, RELEASE_YEAR_FROM, RELEASE_YEAR_TO)

		1 * tradingCardSetRequestDTO.cardsPerPackFrom >> CARDS_PER_PACK_FROM
		1 * tradingCardSetRequestDTO.cardsPerPackTo >> CARDS_PER_PACK_TO
		1 * tradingCardSetQueryBuilder.between(TradingCardSet_.cardsPerPack, CARDS_PER_PACK_FROM, CARDS_PER_PACK_TO)

		1 * tradingCardSetRequestDTO.packsPerBoxFrom >> PACKS_PER_BOX_FROM
		1 * tradingCardSetRequestDTO.packsPerBoxTo >> PACKS_PER_BOX_TO
		1 * tradingCardSetQueryBuilder.between(TradingCardSet_.packsPerBox, PACKS_PER_BOX_FROM, PACKS_PER_BOX_TO)

		1 * tradingCardSetRequestDTO.boxesPerCaseFrom >> BOXES_PER_CASE_FROM
		1 * tradingCardSetRequestDTO.boxesPerCaseTo >> BOXES_PER_CASE_TO
		1 * tradingCardSetQueryBuilder.between(TradingCardSet_.boxesPerCase, BOXES_PER_CASE_FROM, BOXES_PER_CASE_TO)

		1 * tradingCardSetRequestDTO.productionRunFrom >> PRODUCTION_RUN_FROM
		1 * tradingCardSetRequestDTO.productionRunTo >> PRODUCTION_RUN_TO
		1 * tradingCardSetQueryBuilder.between(TradingCardSet_.productionRun, PRODUCTION_RUN_FROM, PRODUCTION_RUN_TO)

		then: 'double criteria are set'
		1 * tradingCardSetRequestDTO.cardWidthFrom >> CARD_WIDTH_FROM
		1 * tradingCardSetRequestDTO.cardWidthTo >> CARD_WIDTH_TO
		1 * tradingCardSetQueryBuilder.between(TradingCardSet_.cardWidth, CARD_WIDTH_FROM, CARD_WIDTH_TO)

		1 * tradingCardSetRequestDTO.cardHeightFrom >> CARD_HEIGHT_FROM
		1 * tradingCardSetRequestDTO.cardHeightTo >> CARD_HEIGHT_TO
		1 * tradingCardSetQueryBuilder.between(TradingCardSet_.cardHeight, CARD_HEIGHT_FROM, CARD_HEIGHT_TO)

		then: 'enum criteria is set'
		1 * tradingCardSetRequestDTO.productionRunUnit >> PRODUCTION_RUN_UNIT
		1 * tradingCardSetQueryBuilder.equal(TradingCardSet_.productionRunUnit, PRODUCTION_RUN_UNIT)

		then: 'sort is set'
		1 * tradingCardSetRequestDTO.sort >> SORT
		1 * tradingCardSetQueryBuilder.setSort(SORT)

		then: 'fetch is performed'
		1 * tradingCardSetQueryBuilder.fetch(TradingCardSet_.manufacturers, true)
		1 * tradingCardSetQueryBuilder.fetch(TradingCardSet_.tradingCards, true)
		1 * tradingCardSetQueryBuilder.fetch(TradingCardSet_.tradingCardDecks, true)
		1 * tradingCardSetQueryBuilder.fetch(TradingCardSet_.countriesOfOrigin, true)

		then: 'page is retrieved'
		1 * tradingCardSetQueryBuilder.findPage() >> page

		then: 'page is returned'
		pageOutput == page

		then: 'no other interactions are expected'
		0 * _
	}

	void "proxies are cleared when no related entities should be fetched"() {
		when:
		Page pageOutput = tradingCardSetRepositoryImpl.findMatching(tradingCardSetRequestDTO, pageable)

		then: 'criteria builder is retrieved'
		1 * tradingCardSetQueryBuilderFactoryMock.createQueryBuilder(pageable) >> tradingCardSetQueryBuilder

		then: 'uid criteria is set to null'
		1 * tradingCardSetRequestDTO.uid >> null

		then: 'page is searched for and returned'
		1 * tradingCardSetQueryBuilder.findPage() >> page

		then: 'proxies are cleared'
		1 * page.content >> Lists.newArrayList(tradingCardSet)
		1 * tradingCardSet.setManufacturers(Sets.newHashSet())
		1 * tradingCardSet.setTradingCards(Sets.newHashSet())
		1 * tradingCardSet.setTradingCardDecks(Sets.newHashSet())
		1 * tradingCardSet.setCountriesOfOrigin(Sets.newHashSet())
		pageOutput == page
	}

}
