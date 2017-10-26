package com.cezarykluczynski.stapi.etl.trading_card.creation.service;

import com.cezarykluczynski.stapi.model.common.service.UidGenerator;
import com.cezarykluczynski.stapi.model.trading_card.entity.TradingCard;
import com.cezarykluczynski.stapi.model.trading_card_deck.entity.TradingCardDeck;
import com.cezarykluczynski.stapi.model.trading_card_set.entity.TradingCardSet;
import org.springframework.stereotype.Service;

@Service
public class TradingCardSetLinker {

	private final UidGenerator uidGenerator;

	public TradingCardSetLinker(UidGenerator uidGenerator) {
		this.uidGenerator = uidGenerator;
	}

	public void linkAll(TradingCardSet tradingCardSet) {
		int index = 0;
		for (TradingCardDeck tradingCardDeck : tradingCardSet.getTradingCardDecks()) {
			index++;
			tradingCardDeck.setTradingCardSet(tradingCardSet);
			tradingCardDeck.setUid(uidGenerator.generateForTradingCardDeck(tradingCardSet, index));
			tradingCardSet.getTradingCards().addAll(tradingCardDeck.getTradingCards());
			for (TradingCard tradingCard : tradingCardSet.getTradingCards()) {
				tradingCard.setTradingCardSet(tradingCardSet);
			}
		}
	}

}
