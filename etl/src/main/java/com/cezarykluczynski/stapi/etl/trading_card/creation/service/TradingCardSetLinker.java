package com.cezarykluczynski.stapi.etl.trading_card.creation.service;

import com.cezarykluczynski.stapi.model.trading_card.entity.TradingCard;
import com.cezarykluczynski.stapi.model.trading_card_deck.entity.TradingCardDeck;
import com.cezarykluczynski.stapi.model.trading_card_set.entity.TradingCardSet;
import org.springframework.stereotype.Service;

@Service
public class TradingCardSetLinker {

	public void linkAll(TradingCardSet tradingCardSet) {
		for (TradingCardDeck tradingCardDeck : tradingCardSet.getTradingCardDecks()) {
			tradingCardDeck.setTradingCardSet(tradingCardSet);
			tradingCardSet.getTradingCards().addAll(tradingCardDeck.getTradingCards());
			for (TradingCard tradingCard : tradingCardSet.getTradingCards()) {
				tradingCard.setTradingCardSet(tradingCardSet);
			}
		}
	}

}
