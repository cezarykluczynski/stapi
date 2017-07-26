package com.cezarykluczynski.stapi.model.trading_card_deck.repository;

import com.cezarykluczynski.stapi.model.common.repository.CriteriaMatcher;
import com.cezarykluczynski.stapi.model.trading_card_deck.dto.TradingCardDeckRequestDTO;
import com.cezarykluczynski.stapi.model.trading_card_deck.entity.TradingCardDeck;

public interface TradingCardDeckRepositoryCustom extends CriteriaMatcher<TradingCardDeckRequestDTO, TradingCardDeck> {
}
