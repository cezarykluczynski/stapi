package com.cezarykluczynski.stapi.model.trading_card_deck.repository;

import com.cezarykluczynski.stapi.model.common.query.QueryBuilder;
import com.cezarykluczynski.stapi.model.trading_card_deck.dto.TradingCardDeckRequestDTO;
import com.cezarykluczynski.stapi.model.trading_card_deck.entity.TradingCardDeck;
import com.cezarykluczynski.stapi.model.trading_card_deck.entity.TradingCardDeck_;
import com.cezarykluczynski.stapi.model.trading_card_deck.query.TradingCardDeckQueryBuilderFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class TradingCardDeckRepositoryImpl implements TradingCardDeckRepositoryCustom {

	private final TradingCardDeckQueryBuilderFactory tradingCardDeckQueryBuilderFactory;

	public TradingCardDeckRepositoryImpl(TradingCardDeckQueryBuilderFactory tradingCardDeckQueryBuilderFactory) {
		this.tradingCardDeckQueryBuilderFactory = tradingCardDeckQueryBuilderFactory;
	}

	@Override
	public Page<TradingCardDeck> findMatching(TradingCardDeckRequestDTO criteria, Pageable pageable) {
		QueryBuilder<TradingCardDeck> tradingCardDeckQueryBuilder = tradingCardDeckQueryBuilderFactory.createQueryBuilder(pageable);
		String uid = criteria.getUid();
		boolean doFetch = uid != null;

		tradingCardDeckQueryBuilder.equal(TradingCardDeck_.uid, uid);
		tradingCardDeckQueryBuilder.like(TradingCardDeck_.name, criteria.getName());
		tradingCardDeckQueryBuilder.joinPropertyEqual(TradingCardDeck_.tradingCardSet, "uid", criteria.getTradingCardSetUid());
		tradingCardDeckQueryBuilder.setSort(criteria.getSort());
		tradingCardDeckQueryBuilder.fetch(TradingCardDeck_.tradingCardSet);
		tradingCardDeckQueryBuilder.fetch(TradingCardDeck_.tradingCards, doFetch);

		return tradingCardDeckQueryBuilder.findPage();
	}

}
