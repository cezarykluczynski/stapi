package com.cezarykluczynski.stapi.server.trading_card_set.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.TradingCardSetFull
import com.cezarykluczynski.stapi.model.trading_card_set.entity.TradingCardSet
import org.mapstruct.factory.Mappers

class TradingCardSetFullRestMapperTest extends AbstractTradingCardSetMapperTest {

	private TradingCardSetFullRestMapper tradingCardSetFullRestMapper

	void setup() {
		tradingCardSetFullRestMapper = Mappers.getMapper(TradingCardSetFullRestMapper)
	}

	void "maps DB entity to full REST entity"() {
		given:
		TradingCardSet tradingCardSet = createTradingCardSet()

		when:
		TradingCardSetFull tradingCardSetFull = tradingCardSetFullRestMapper.mapFull(tradingCardSet)

		then:
		tradingCardSetFull.uid == UID
		tradingCardSetFull.name == NAME
		tradingCardSetFull.releaseYear == RELEASE_YEAR
		tradingCardSetFull.releaseMonth == RELEASE_MONTH
		tradingCardSetFull.releaseDay == RELEASE_DAY
		tradingCardSetFull.cardsPerPack == CARDS_PER_PACK
		tradingCardSetFull.packsPerBox == PACKS_PER_BOX
		tradingCardSetFull.boxesPerCase == BOXES_PER_CASE
		tradingCardSetFull.productionRun == PRODUCTION_RUN
		tradingCardSetFull.productionRunUnit.name() == PRODUCTION_RUN_UNIT.name()
		tradingCardSetFull.cardWidth == CARD_WIDTH
		tradingCardSetFull.cardHeight == CARD_HEIGHT
		tradingCardSetFull.manufacturers.size() == tradingCardSet.manufacturers.size()
		tradingCardSetFull.tradingCards.size() == tradingCardSet.tradingCards.size()
		tradingCardSetFull.tradingCardDecks.size() == tradingCardSet.tradingCardDecks.size()
		tradingCardSetFull.countriesOfOrigin.size() == tradingCardSet.countriesOfOrigin.size()
	}

}
