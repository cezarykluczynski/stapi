package com.cezarykluczynski.stapi.model.trading_card_set.repository;

import com.cezarykluczynski.stapi.model.trading_card_set.entity.TradingCardSet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TradingCardSetRepository extends JpaRepository<TradingCardSet, Long>, TradingCardSetRepositoryCustom {
}
