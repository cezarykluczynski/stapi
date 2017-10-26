package com.cezarykluczynski.stapi.model.trading_card_set.query;

import com.cezarykluczynski.stapi.model.common.cache.CachingStrategy;
import com.cezarykluczynski.stapi.model.common.query.AbstractQueryBuilderFactory;
import com.cezarykluczynski.stapi.model.trading_card_set.entity.TradingCardSet;
import org.springframework.data.jpa.repository.JpaContext;
import org.springframework.stereotype.Service;

@Service
public class TradingCardSetQueryBuilderFactory extends AbstractQueryBuilderFactory<TradingCardSet> {

	public TradingCardSetQueryBuilderFactory(JpaContext jpaContext, CachingStrategy cachingStrategy) {
		super(jpaContext, cachingStrategy, TradingCardSet.class);
	}

}
