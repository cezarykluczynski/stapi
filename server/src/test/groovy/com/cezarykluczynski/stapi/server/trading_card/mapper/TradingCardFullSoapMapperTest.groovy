package com.cezarykluczynski.stapi.server.trading_card.mapper

import com.cezarykluczynski.stapi.client.v1.soap.TradingCardFull
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardFullRequest
import com.cezarykluczynski.stapi.model.trading_card.dto.TradingCardRequestDTO
import com.cezarykluczynski.stapi.model.trading_card.entity.TradingCard
import org.mapstruct.factory.Mappers

class TradingCardFullSoapMapperTest extends AbstractTradingCardMapperTest {

	private TradingCardFullSoapMapper tradingCardFullSoapMapper

	void setup() {
		tradingCardFullSoapMapper = Mappers.getMapper(TradingCardFullSoapMapper)
	}

	void "maps SOAP TradingCardFullRequest to TradingCardBaseRequestDTO"() {
		given:
		TradingCardFullRequest tradingCardFullRequest = new TradingCardFullRequest(uid: UID)

		when:
		TradingCardRequestDTO tradingCardRequestDTO = tradingCardFullSoapMapper.mapFull tradingCardFullRequest

		then:
		tradingCardRequestDTO.uid == UID
	}

	void "maps DB entity to full SOAP entity"() {
		given:
		TradingCard tradingCard = createTradingCard()

		when:
		TradingCardFull tradingCardFull = tradingCardFullSoapMapper.mapFull(tradingCard)

		then:
		tradingCardFull.uid == UID
		tradingCardFull.name == NAME
		tradingCardFull.number == NUMBER
		tradingCardFull.releaseYear == RELEASE_YEAR
		tradingCardFull.productionRun == PRODUCTION_RUN
		tradingCardFull.tradingCardSet != null
		tradingCardFull.tradingCardDeck != null
	}

}
