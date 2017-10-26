package com.cezarykluczynski.stapi.model.trading_card_set.repository;

import com.cezarykluczynski.stapi.model.common.query.QueryBuilder;
import com.cezarykluczynski.stapi.model.common.repository.AbstractRepositoryImpl;
import com.cezarykluczynski.stapi.model.trading_card_set.dto.TradingCardSetRequestDTO;
import com.cezarykluczynski.stapi.model.trading_card_set.entity.TradingCardSet;
import com.cezarykluczynski.stapi.model.trading_card_set.entity.TradingCardSet_;
import com.cezarykluczynski.stapi.model.trading_card_set.query.TradingCardSetQueryBuilderFactory;
import com.google.common.collect.Sets;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class TradingCardSetRepositoryImpl extends AbstractRepositoryImpl<TradingCardSet> implements TradingCardSetRepositoryCustom {

	private final TradingCardSetQueryBuilderFactory tradingCardSetQueryBuilderFactory;

	public TradingCardSetRepositoryImpl(TradingCardSetQueryBuilderFactory tradingCardSetQueryBuilderFactory) {
		this.tradingCardSetQueryBuilderFactory = tradingCardSetQueryBuilderFactory;
	}

	@Override
	public Page<TradingCardSet> findMatching(TradingCardSetRequestDTO criteria, Pageable pageable) {
		QueryBuilder<TradingCardSet> tradingCardSetQueryBuilder = tradingCardSetQueryBuilderFactory.createQueryBuilder(pageable);
		String uid = criteria.getUid();
		boolean doFetch = uid != null;

		tradingCardSetQueryBuilder.equal(TradingCardSet_.uid, uid);
		tradingCardSetQueryBuilder.like(TradingCardSet_.name, criteria.getName());
		tradingCardSetQueryBuilder.between(TradingCardSet_.releaseYear, criteria.getReleaseYearFrom(), criteria.getReleaseYearTo());
		tradingCardSetQueryBuilder.between(TradingCardSet_.cardsPerPack, criteria.getCardsPerPackFrom(), criteria.getCardsPerPackTo());
		tradingCardSetQueryBuilder.between(TradingCardSet_.packsPerBox, criteria.getPacksPerBoxFrom(), criteria.getPacksPerBoxTo());
		tradingCardSetQueryBuilder.between(TradingCardSet_.boxesPerCase, criteria.getBoxesPerCaseFrom(), criteria.getBoxesPerCaseTo());
		tradingCardSetQueryBuilder.between(TradingCardSet_.productionRun, criteria.getProductionRunFrom(), criteria.getProductionRunTo());
		tradingCardSetQueryBuilder.between(TradingCardSet_.cardWidth, criteria.getCardWidthFrom(), criteria.getCardWidthTo());
		tradingCardSetQueryBuilder.between(TradingCardSet_.cardHeight, criteria.getCardHeightFrom(), criteria.getCardHeightTo());
		tradingCardSetQueryBuilder.equal(TradingCardSet_.productionRunUnit, criteria.getProductionRunUnit());
		tradingCardSetQueryBuilder.setSort(criteria.getSort());
		tradingCardSetQueryBuilder.fetch(TradingCardSet_.manufacturers, doFetch);
		tradingCardSetQueryBuilder.fetch(TradingCardSet_.tradingCardDecks, doFetch);
		tradingCardSetQueryBuilder.fetch(TradingCardSet_.countriesOfOrigin, doFetch);
		tradingCardSetQueryBuilder.fetch(TradingCardSet_.tradingCards, doFetch);

		Page<TradingCardSet> tradingCardSetPage = tradingCardSetQueryBuilder.findPage();
		clearProxies(tradingCardSetPage, !doFetch);
		return tradingCardSetPage;
	}

	@Override
	protected void clearProxies(Page<TradingCardSet> page, boolean doClearProxies) {
		if (!doClearProxies) {
			return;
		}

		page.getContent().forEach(tradingCardSet -> {
			tradingCardSet.setManufacturers(Sets.newHashSet());
			tradingCardSet.setTradingCards(Sets.newHashSet());
			tradingCardSet.setTradingCardDecks(Sets.newHashSet());
			tradingCardSet.setCountriesOfOrigin(Sets.newHashSet());
		});
	}

}
