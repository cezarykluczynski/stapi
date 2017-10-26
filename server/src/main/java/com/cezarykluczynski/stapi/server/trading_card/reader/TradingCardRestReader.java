package com.cezarykluczynski.stapi.server.trading_card.reader;

import com.cezarykluczynski.stapi.client.v1.rest.model.TradingCardBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.TradingCardFullResponse;
import com.cezarykluczynski.stapi.model.trading_card.entity.TradingCard;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper;
import com.cezarykluczynski.stapi.server.common.reader.BaseReader;
import com.cezarykluczynski.stapi.server.common.reader.FullReader;
import com.cezarykluczynski.stapi.server.common.validator.StaticValidator;
import com.cezarykluczynski.stapi.server.trading_card.dto.TradingCardRestBeanParams;
import com.cezarykluczynski.stapi.server.trading_card.mapper.TradingCardBaseRestMapper;
import com.cezarykluczynski.stapi.server.trading_card.mapper.TradingCardFullRestMapper;
import com.cezarykluczynski.stapi.server.trading_card.query.TradingCardRestQuery;
import com.google.common.collect.Iterables;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class TradingCardRestReader implements BaseReader<TradingCardRestBeanParams, TradingCardBaseResponse>,
		FullReader<String, TradingCardFullResponse> {

	private final TradingCardRestQuery tradingCardRestQuery;

	private final TradingCardBaseRestMapper tradingCardBaseRestMapper;

	private final TradingCardFullRestMapper tradingCardFullRestMapper;

	private final PageMapper pageMapper;

	private final SortMapper sortMapper;

	public TradingCardRestReader(TradingCardRestQuery tradingCardRestQuery, TradingCardBaseRestMapper tradingCardBaseRestMapper,
			TradingCardFullRestMapper tradingCardFullRestMapper, PageMapper pageMapper, SortMapper sortMapper) {
		this.tradingCardRestQuery = tradingCardRestQuery;
		this.tradingCardBaseRestMapper = tradingCardBaseRestMapper;
		this.tradingCardFullRestMapper = tradingCardFullRestMapper;
		this.pageMapper = pageMapper;
		this.sortMapper = sortMapper;
	}

	@Override
	public TradingCardBaseResponse readBase(TradingCardRestBeanParams input) {
		Page<TradingCard> tradingCardPage = tradingCardRestQuery.query(input);
		TradingCardBaseResponse tradingCardResponse = new TradingCardBaseResponse();
		tradingCardResponse.setPage(pageMapper.fromPageToRestResponsePage(tradingCardPage));
		tradingCardResponse.setSort(sortMapper.map(input.getSort()));
		tradingCardResponse.getTradingCards().addAll(tradingCardBaseRestMapper.mapBase(tradingCardPage.getContent()));
		return tradingCardResponse;
	}

	@Override
	public TradingCardFullResponse readFull(String uid) {
		StaticValidator.requireUid(uid);
		TradingCardRestBeanParams tradingCardRestBeanParams = new TradingCardRestBeanParams();
		tradingCardRestBeanParams.setUid(uid);
		Page<TradingCard> tradingCardPage = tradingCardRestQuery.query(tradingCardRestBeanParams);
		TradingCardFullResponse tradingCardResponse = new TradingCardFullResponse();
		tradingCardResponse.setTradingCard(tradingCardFullRestMapper
				.mapFull(Iterables.getOnlyElement(tradingCardPage.getContent(), null)));
		return tradingCardResponse;
	}

}
