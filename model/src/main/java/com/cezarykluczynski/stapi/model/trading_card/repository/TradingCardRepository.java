package com.cezarykluczynski.stapi.model.trading_card.repository;

import com.cezarykluczynski.stapi.model.trading_card.entity.TradingCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TradingCardRepository extends JpaRepository<TradingCard, Long>, TradingCardRepositoryCustom {
}
