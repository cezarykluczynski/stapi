package com.cezarykluczynski.stapi.model.trading_card.repository;

import com.cezarykluczynski.stapi.model.common.query.QueryBuilder;
import com.cezarykluczynski.stapi.model.trading_card.dto.TradingCardRequestDTO;
import com.cezarykluczynski.stapi.model.trading_card.entity.TradingCard;
import com.cezarykluczynski.stapi.model.trading_card.entity.TradingCard_;
import com.cezarykluczynski.stapi.model.trading_card.query.TradingCardQueryBuilderFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class TradingCardRepositoryImpl implements TradingCardRepositoryCustom {

	private final TradingCardQueryBuilderFactory tradingCardQueryBuilderFactory;

	public TradingCardRepositoryImpl(TradingCardQueryBuilderFactory tradingCardQueryBuilderFactory) {
		this.tradingCardQueryBuilderFactory = tradingCardQueryBuilderFactory;
	}

	@Override
	public Page<TradingCard> findMatching(TradingCardRequestDTO criteria, Pageable pageable) {
		QueryBuilder<TradingCard> tradingCardQueryBuilder = tradingCardQueryBuilderFactory.createQueryBuilder(pageable);
		String uid = criteria.getUid();

		tradingCardQueryBuilder.equal(TradingCard_.uid, uid);
		tradingCardQueryBuilder.like(TradingCard_.name, criteria.getName());
		tradingCardQueryBuilder.joinPropertyEqual(TradingCard_.tradingCardDeck, "uid", criteria.getTradingCardDeckUid());
		tradingCardQueryBuilder.joinPropertyEqual(TradingCard_.tradingCardSet, "uid", criteria.getTradingCardSetUid());
		tradingCardQueryBuilder.setSort(criteria.getSort());
		tradingCardQueryBuilder.fetch(TradingCard_.tradingCardSet);

		return tradingCardQueryBuilder.findPage();
	}

}
