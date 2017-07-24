package com.cezarykluczynski.stapi.server.trading_card_set.mapper

import com.cezarykluczynski.stapi.model.company.entity.Company
import com.cezarykluczynski.stapi.model.country.entity.Country
import com.cezarykluczynski.stapi.model.trading_card.entity.TradingCard
import com.cezarykluczynski.stapi.model.trading_card_deck.entity.TradingCardDeck
import com.cezarykluczynski.stapi.model.trading_card_set.entity.TradingCardSet
import com.cezarykluczynski.stapi.model.trading_card_set.entity.enums.ProductionRunUnit
import com.cezarykluczynski.stapi.util.AbstractTradingCardSetTest

abstract class AbstractTradingCardSetMapperTest extends AbstractTradingCardSetTest {

	protected static final ProductionRunUnit PRODUCTION_RUN_UNIT = ProductionRunUnit.BOX

	protected TradingCardSet createTradingCardSet() {
		new TradingCardSet(
				uid: UID,
				name: NAME,
				releaseYear: RELEASE_YEAR,
				releaseMonth: RELEASE_MONTH,
				releaseDay: RELEASE_DAY,
				cardsPerPack: CARDS_PER_PACK,
				packsPerBox: PACKS_PER_BOX,
				boxesPerCase: BOXES_PER_CASE,
				productionRun: PRODUCTION_RUN,
				productionRunUnit: PRODUCTION_RUN_UNIT,
				cardWidth: CARD_WIDTH,
				cardHeight: CARD_HEIGHT,
				manufacturers: createSetOfRandomNumberOfMocks(Company),
				tradingCards: createSetOfRandomNumberOfMocks(TradingCard),
				tradingCardDecks: createSetOfRandomNumberOfMocks(TradingCardDeck),
				countriesOfOrigin: createSetOfRandomNumberOfMocks(Country))
	}

}
