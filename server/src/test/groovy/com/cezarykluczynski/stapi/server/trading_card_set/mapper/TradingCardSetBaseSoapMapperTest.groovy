package com.cezarykluczynski.stapi.server.trading_card_set.mapper

import com.cezarykluczynski.stapi.client.v1.soap.DoubleRange
import com.cezarykluczynski.stapi.client.v1.soap.IntegerRange
import com.cezarykluczynski.stapi.client.v1.soap.ProductionRunUnitEnum
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardSetBase as TradingCardSetBase
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardSetBaseRequest
import com.cezarykluczynski.stapi.model.trading_card_set.dto.TradingCardSetRequestDTO
import com.cezarykluczynski.stapi.model.trading_card_set.entity.TradingCardSet
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class TradingCardSetBaseSoapMapperTest extends AbstractTradingCardSetMapperTest {

	private static final ProductionRunUnitEnum PRODUCTION_RUN_UNIT_ENUM = ProductionRunUnitEnum.SET

	private TradingCardSetBaseSoapMapper tradingCardSetBaseSoapMapper

	void setup() {
		tradingCardSetBaseSoapMapper = Mappers.getMapper(TradingCardSetBaseSoapMapper)
	}

	void "maps SOAP TradingCardSetRequest to TradingCardSetRequestDTO"() {

		given:
		TradingCardSetBaseRequest tradingCardSetBaseRequest = new TradingCardSetBaseRequest(
				name: NAME,
				releasedYear: new IntegerRange(
						from: RELEASE_YEAR_FROM,
						to: RELEASE_YEAR_TO,
				),
				cardsPerPack: new IntegerRange(
						from: CARDS_PER_PACK_FROM,
						to: CARDS_PER_PACK_TO
				),
				packsPerBox: new IntegerRange(
						from: PACKS_PER_BOX_FROM,
						to: PACKS_PER_BOX_TO
				),
				boxesPerCase: new IntegerRange(
						from: BOXES_PER_CASE_FROM,
						to: BOXES_PER_CASE_TO
				),
				productionRun: new IntegerRange(
						from: PRODUCTION_RUN_FROM,
						to: PRODUCTION_RUN_TO
				),
				productionRunUnit: PRODUCTION_RUN_UNIT_ENUM,
				cardWidth: new DoubleRange(
						from: CARD_WIDTH_FROM,
						to: CARD_WIDTH_TO
				),
				cardHeight: new DoubleRange(
						from: CARD_HEIGHT_FROM,
						to: CARD_HEIGHT_TO
				),
		)
		when:
		TradingCardSetRequestDTO tradingCardSetRequestDTO = tradingCardSetBaseSoapMapper.mapBase tradingCardSetBaseRequest

		then:
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
		tradingCardSetRequestDTO.productionRunUnit.name() == PRODUCTION_RUN_UNIT_ENUM.name()
		tradingCardSetRequestDTO.cardWidthFrom == CARD_WIDTH_FROM
		tradingCardSetRequestDTO.cardWidthTo == CARD_WIDTH_TO
		tradingCardSetRequestDTO.cardHeightFrom == CARD_HEIGHT_FROM
		tradingCardSetRequestDTO.cardHeightTo == CARD_HEIGHT_TO
	}

	void "maps DB entity to base SOAP entity"() {
		given:
		TradingCardSet tradingCardSet = createTradingCardSet()

		when:
		TradingCardSetBase tradingCardSetBase = tradingCardSetBaseSoapMapper.mapBase(Lists.newArrayList(tradingCardSet))[0]

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
