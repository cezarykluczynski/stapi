package com.cezarykluczynski.stapi.model.trading_card.query;

import com.cezarykluczynski.stapi.model.common.cache.CachingStrategy;
import com.cezarykluczynski.stapi.model.common.query.AbstractQueryBuilderFactory;
import com.cezarykluczynski.stapi.model.trading_card.entity.TradingCard;
import org.springframework.data.jpa.repository.JpaContext;
import org.springframework.stereotype.Service;

@Service
public class TradingCardQueryBuilderFactory extends AbstractQueryBuilderFactory<TradingCard> {

	public TradingCardQueryBuilderFactory(JpaContext jpaContext, CachingStrategy cachingStrategy) {
		super(jpaContext, cachingStrategy, TradingCard.class);
	}

}
