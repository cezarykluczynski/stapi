package com.cezarykluczynski.stapi.etl.trading_card.creation.service

import com.cezarykluczynski.stapi.model.common.service.UidGenerator
import com.cezarykluczynski.stapi.model.trading_card.entity.TradingCard
import com.cezarykluczynski.stapi.model.trading_card_deck.entity.TradingCardDeck
import com.cezarykluczynski.stapi.model.trading_card_set.entity.TradingCardSet
import com.google.common.collect.Lists
import com.google.common.collect.Sets
import spock.lang.Specification

class TradingCardSetLinkerTest extends Specification {

	private static final String UID1 = 'UID1'
	private static final String UID2 = 'UID2'

	private UidGenerator uidGeneratorMock

	private TradingCardSetLinker tradingCardSetLinker

	void setup() {
		uidGeneratorMock = Mock()
		tradingCardSetLinker = new TradingCardSetLinker(uidGeneratorMock)
	}

	void "when trading card set table and trading cards tabled are found, and TradingCardsSet with cards is returned"() {
		given:
		TradingCard tradingCard1 = new TradingCard(id: 1)
		TradingCard tradingCard2 = new TradingCard(id: 2)
		TradingCard tradingCard3 = new TradingCard(id: 3)
		TradingCard tradingCard4 = new TradingCard(id: 4)
		TradingCardDeck tradingCardDeck1 = new TradingCardDeck(id: 5, tradingCards: Sets.newHashSet(tradingCard1, tradingCard2))
		TradingCardDeck tradingCardDeck2 = new TradingCardDeck(id: 6, tradingCards: Sets.newHashSet(tradingCard3, tradingCard4))
		TradingCardSet tradingCardSet = new TradingCardSet(id: 7, tradingCardDecks: Sets.newHashSet(tradingCardDeck1, tradingCardDeck2))

		when:
		tradingCardSetLinker.linkAll(tradingCardSet)

		then:
		1 * uidGeneratorMock.generateForTradingCardDeck(tradingCardSet, 1) >> UID1
		1 * uidGeneratorMock.generateForTradingCardDeck(tradingCardSet, 2) >> UID2
		0 * _
		tradingCardSet.tradingCardDecks.size() == 2
		Lists.newArrayList(tradingCardSet.tradingCardDecks).contains tradingCardDeck1
		Lists.newArrayList(tradingCardSet.tradingCardDecks).contains tradingCardDeck2
		tradingCardDeck1.tradingCardSet == tradingCardSet
		tradingCardDeck2.tradingCardSet == tradingCardSet
		tradingCard1.tradingCardSet == tradingCardSet
		tradingCard2.tradingCardSet == tradingCardSet
		tradingCard3.tradingCardSet == tradingCardSet
		tradingCard4.tradingCardSet == tradingCardSet
	}

}
