package com.cezarykluczynski.stapi.server.trading_card_set.reader;

import com.cezarykluczynski.stapi.client.v1.rest.model.TradingCardSetBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.TradingCardSetFullResponse;
import com.cezarykluczynski.stapi.model.trading_card_set.entity.TradingCardSet;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper;
import com.cezarykluczynski.stapi.server.common.reader.BaseReader;
import com.cezarykluczynski.stapi.server.common.reader.FullReader;
import com.cezarykluczynski.stapi.server.common.validator.StaticValidator;
import com.cezarykluczynski.stapi.server.trading_card_set.dto.TradingCardSetRestBeanParams;
import com.cezarykluczynski.stapi.server.trading_card_set.mapper.TradingCardSetBaseRestMapper;
import com.cezarykluczynski.stapi.server.trading_card_set.mapper.TradingCardSetFullRestMapper;
import com.cezarykluczynski.stapi.server.trading_card_set.query.TradingCardSetRestQuery;
import com.google.common.collect.Iterables;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class TradingCardSetRestReader implements BaseReader<TradingCardSetRestBeanParams, TradingCardSetBaseResponse>,
		FullReader<String, TradingCardSetFullResponse> {

	private final TradingCardSetRestQuery tradingCardSetRestQuery;

	private final TradingCardSetBaseRestMapper tradingCardSetBaseRestMapper;

	private final TradingCardSetFullRestMapper tradingCardSetFullRestMapper;

	private final PageMapper pageMapper;

	private final SortMapper sortMapper;

	public TradingCardSetRestReader(TradingCardSetRestQuery tradingCardSetRestQuery, TradingCardSetBaseRestMapper tradingCardSetBaseRestMapper,
			TradingCardSetFullRestMapper tradingCardSetFullRestMapper, PageMapper pageMapper, SortMapper sortMapper) {
		this.tradingCardSetRestQuery = tradingCardSetRestQuery;
		this.tradingCardSetBaseRestMapper = tradingCardSetBaseRestMapper;
		this.tradingCardSetFullRestMapper = tradingCardSetFullRestMapper;
		this.pageMapper = pageMapper;
		this.sortMapper = sortMapper;
	}

	@Override
	public TradingCardSetBaseResponse readBase(TradingCardSetRestBeanParams input) {
		Page<TradingCardSet> tradingCardSetPage = tradingCardSetRestQuery.query(input);
		TradingCardSetBaseResponse tradingCardSetResponse = new TradingCardSetBaseResponse();
		tradingCardSetResponse.setPage(pageMapper.fromPageToRestResponsePage(tradingCardSetPage));
		tradingCardSetResponse.setSort(sortMapper.map(input.getSort()));
		tradingCardSetResponse.getTradingCardSets().addAll(tradingCardSetBaseRestMapper.mapBase(tradingCardSetPage.getContent()));
		return tradingCardSetResponse;
	}

	@Override
	public TradingCardSetFullResponse readFull(String uid) {
		StaticValidator.requireUid(uid);
		TradingCardSetRestBeanParams tradingCardSetRestBeanParams = new TradingCardSetRestBeanParams();
		tradingCardSetRestBeanParams.setUid(uid);
		Page<TradingCardSet> tradingCardSetPage = tradingCardSetRestQuery.query(tradingCardSetRestBeanParams);
		TradingCardSetFullResponse tradingCardSetResponse = new TradingCardSetFullResponse();
		tradingCardSetResponse.setTradingCardSet(tradingCardSetFullRestMapper
				.mapFull(Iterables.getOnlyElement(tradingCardSetPage.getContent(), null)));
		return tradingCardSetResponse;
	}

}
