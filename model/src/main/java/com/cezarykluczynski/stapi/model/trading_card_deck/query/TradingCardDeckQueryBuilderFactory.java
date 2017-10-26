package com.cezarykluczynski.stapi.model.trading_card_deck.query;

import com.cezarykluczynski.stapi.model.common.cache.CachingStrategy;
import com.cezarykluczynski.stapi.model.common.query.AbstractQueryBuilderFactory;
import com.cezarykluczynski.stapi.model.trading_card_deck.entity.TradingCardDeck;
import org.springframework.data.jpa.repository.JpaContext;
import org.springframework.stereotype.Service;

@Service
public class TradingCardDeckQueryBuilderFactory extends AbstractQueryBuilderFactory<TradingCardDeck> {

	public TradingCardDeckQueryBuilderFactory(JpaContext jpaContext, CachingStrategy cachingStrategy) {
		super(jpaContext, cachingStrategy, TradingCardDeck.class);
	}

}
