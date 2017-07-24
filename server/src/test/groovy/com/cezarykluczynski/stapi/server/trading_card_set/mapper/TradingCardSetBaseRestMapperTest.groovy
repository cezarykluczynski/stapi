package com.cezarykluczynski.stapi.server.trading_card_set.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.TradingCardSetBase
import com.cezarykluczynski.stapi.model.trading_card_set.dto.TradingCardSetRequestDTO
import com.cezarykluczynski.stapi.model.trading_card_set.entity.TradingCardSet
import com.cezarykluczynski.stapi.server.trading_card_set.dto.TradingCardSetRestBeanParams
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class TradingCardSetBaseRestMapperTest extends AbstractTradingCardSetMapperTest {

	private TradingCardSetBaseRestMapper tradingCardSetBaseRestMapper

	void setup() {
		tradingCardSetBaseRestMapper = Mappers.getMapper(TradingCardSetBaseRestMapper)
	}

	void "maps TradingCardSetRestBeanParams to TradingCardSetRequestDTO"() {
		given:
		TradingCardSetRestBeanParams tradingCardSetRestBeanParams = new TradingCardSetRestBeanParams(
				uid: UID,
				name: NAME,
				releaseYearFrom: RELEASE_YEAR_FROM,
				releaseYearTo: RELEASE_YEAR_TO,
				cardsPerPackFrom: CARDS_PER_PACK_FROM,
				cardsPerPackTo: CARDS_PER_PACK_TO,
				packsPerBoxFrom: PACKS_PER_BOX_FROM,
				packsPerBoxTo: PACKS_PER_BOX_TO,
				boxesPerCaseFrom: BOXES_PER_CASE_FROM,
				boxesPerCaseTo: BOXES_PER_CASE_TO,
				productionRunFrom: PRODUCTION_RUN_FROM,
				productionRunTo: PRODUCTION_RUN_TO,
				productionRunUnit: PRODUCTION_RUN_UNIT,
				cardWidthFrom: CARD_WIDTH_FROM,
				cardWidthTo: CARD_WIDTH_TO,
				cardHeightFrom: CARD_HEIGHT_FROM,
				cardHeightTo: CARD_HEIGHT_TO)

		when:
		TradingCardSetRequestDTO tradingCardSetRequestDTO = tradingCardSetBaseRestMapper.mapBase tradingCardSetRestBeanParams

		then:
		tradingCardSetRequestDTO.uid == UID
		tradingCardSetRequestDTO.name == NAME
		tradingCardSetRequestDTO.releaseYearFrom == RELEASE_YEAR_FROM
		tradingCardSetRequestDTO.releaseYearTo == RELEASE_YEAR_TO
		tradingCardSetRequestDTO.cardsPerPackFrom == CARDS_PER_PACK_FROM
		tradingCardSetRequestDTO.cardsPerPackTo == CARDS_PER_PACK_TO
		tradingCardSetRequestDTO.packsPerBoxFrom == PACKS_PER_BOX_FROM
		tradingCardSetRequestDTO.packsPerBoxTo == PACKS_PER_BOX_TO
		tradingCardSetRequestDTO.boxesPerCaseFrom == BOXES_PER_CASE_FROM
		tradingCardSetRequestDTO.boxesPerCaseTo == BOXES_PER_CASE_TO
		tradingCardSetRequestDTO.productionRunFrom == PRODUCTION_RUN_FROM
		tradingCardSetRequestDTO.productionRunTo == PRODUCTION_RUN_TO
		tradingCardSetRequestDTO.productionRunUnit == PRODUCTION_RUN_UNIT
		tradingCardSetRequestDTO.cardWidthFrom == CARD_WIDTH_FROM
		tradingCardSetRequestDTO.cardWidthTo == CARD_WIDTH_TO
		tradingCardSetRequestDTO.cardHeightFrom == CARD_HEIGHT_FROM
		tradingCardSetRequestDTO.cardHeightTo == CARD_HEIGHT_TO
	}

	void "maps DB entity to base REST entity"() {
		given:
		TradingCardSet tradingCardSet = createTradingCardSet()

		when:
		TradingCardSetBase tradingCardSetBase = tradingCardSetBaseRestMapper.mapBase(Lists.newArrayList(tradingCardSet))[0]

		then:
		tradingCardSetBase.uid == UID
		tradingCardSetBase.name == NAME
		tradingCardSetBase.releaseYear == RELEASE_YEAR
		tradingCardSetBase.releaseMonth == RELEASE_MONTH
		tradingCardSetBase.releaseDay == RELEASE_DAY
		tradingCardSetBase.cardsPerPack == CARDS_PER_PACK
		tradingCardSetBase.packsPerBox == PACKS_PER_BOX
		tradingCardSetBase.boxesPerCase == BOXES_PER_CASE
		tradingCardSetBase.productionRun == PRODUCTION_RUN
		tradingCardSetBase.productionRunUnit.name() == PRODUCTION_RUN_UNIT.name()
		tradingCardSetBase.cardWidth == CARD_WIDTH
		tradingCardSetBase.cardHeight == CARD_HEIGHT
	}

}
