package com.cezarykluczynski.stapi.server.trading_card_deck.mapper

import com.cezarykluczynski.stapi.model.trading_card.entity.TradingCard
import com.cezarykluczynski.stapi.model.trading_card_deck.entity.TradingCardDeck
import com.cezarykluczynski.stapi.model.trading_card_set.entity.TradingCardSet
import com.cezarykluczynski.stapi.util.AbstractTradingCardDeckTest

abstract class AbstractTradingCardDeckMapperTest extends AbstractTradingCardDeckTest {

	protected TradingCardDeck createTradingCardDeck() {
		new TradingCardDeck(
				uid: UID,
				name: NAME,
				frequency: FREQUENCY,
				tradingCardSet: new TradingCardSet(),
				tradingCards: createSetOfRandomNumberOfMocks(TradingCard))
	}

}
