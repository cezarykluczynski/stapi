package com.cezarykluczynski.stapi.model.trading_card_deck.repository;

import com.cezarykluczynski.stapi.model.trading_card_deck.entity.TradingCardDeck;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TradingCardDeckRepository extends JpaRepository<TradingCardDeck, Long>, TradingCardDeckRepositoryCustom {
}
