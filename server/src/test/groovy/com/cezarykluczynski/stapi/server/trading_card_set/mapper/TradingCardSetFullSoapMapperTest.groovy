package com.cezarykluczynski.stapi.server.trading_card_set.mapper

import com.cezarykluczynski.stapi.client.v1.soap.TradingCardSetFull
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardSetFullRequest
import com.cezarykluczynski.stapi.model.trading_card_set.dto.TradingCardSetRequestDTO
import com.cezarykluczynski.stapi.model.trading_card_set.entity.TradingCardSet
import org.mapstruct.factory.Mappers

class TradingCardSetFullSoapMapperTest extends AbstractTradingCardSetMapperTest {

	private TradingCardSetFullSoapMapper tradingCardSetFullSoapMapper

	void setup() {
		tradingCardSetFullSoapMapper = Mappers.getMapper(TradingCardSetFullSoapMapper)
	}

	void "maps SOAP TradingCardSetFullRequest to TradingCardSetBaseRequestDTO"() {
		given:
		TradingCardSetFullRequest tradingCardSetFullRequest = new TradingCardSetFullRequest(uid: UID)

		when:
		TradingCardSetRequestDTO tradingCardSetRequestDTO = tradingCardSetFullSoapMapper.mapFull tradingCardSetFullRequest

		then:
		tradingCardSetRequestDTO.uid == UID
	}

	void "maps DB entity to full SOAP entity"() {
		given:
		TradingCardSet tradingCardSet = createTradingCardSet()

		when:
		TradingCardSetFull tradingCardSetFull = tradingCardSetFullSoapMapper.mapFull(tradingCardSet)

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
