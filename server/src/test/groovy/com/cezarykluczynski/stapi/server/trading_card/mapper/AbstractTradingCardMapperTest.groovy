package com.cezarykluczynski.stapi.server.trading_card.mapper

import com.cezarykluczynski.stapi.model.trading_card.entity.TradingCard
import com.cezarykluczynski.stapi.model.trading_card_deck.entity.TradingCardDeck
import com.cezarykluczynski.stapi.model.trading_card_set.entity.TradingCardSet
import com.cezarykluczynski.stapi.util.AbstractTradingCardTest

abstract class AbstractTradingCardMapperTest extends AbstractTradingCardTest {

	protected static TradingCard createTradingCard() {
		new TradingCard(
				uid: UID,
				name: NAME,
				number: NUMBER,
				releaseYear: RELEASE_YEAR,
				productionRun: PRODUCTION_RUN,
				tradingCardDeck: new TradingCardDeck(),
				tradingCardSet: new TradingCardSet())
	}

}
