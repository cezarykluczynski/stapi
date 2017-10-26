package com.cezarykluczynski.stapi.server.trading_card_deck.reader;

import com.cezarykluczynski.stapi.client.v1.rest.model.TradingCardDeckBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.TradingCardDeckFullResponse;
import com.cezarykluczynski.stapi.model.trading_card_deck.entity.TradingCardDeck;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper;
import com.cezarykluczynski.stapi.server.common.reader.BaseReader;
import com.cezarykluczynski.stapi.server.common.reader.FullReader;
import com.cezarykluczynski.stapi.server.common.validator.StaticValidator;
import com.cezarykluczynski.stapi.server.trading_card_deck.dto.TradingCardDeckRestBeanParams;
import com.cezarykluczynski.stapi.server.trading_card_deck.mapper.TradingCardDeckBaseRestMapper;
import com.cezarykluczynski.stapi.server.trading_card_deck.mapper.TradingCardDeckFullRestMapper;
import com.cezarykluczynski.stapi.server.trading_card_deck.query.TradingCardDeckRestQuery;
import com.google.common.collect.Iterables;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class TradingCardDeckRestReader implements BaseReader<TradingCardDeckRestBeanParams, TradingCardDeckBaseResponse>,
		FullReader<String, TradingCardDeckFullResponse> {

	private final TradingCardDeckRestQuery tradingCardDeckRestQuery;

	private final TradingCardDeckBaseRestMapper tradingCardDeckBaseRestMapper;

	private final TradingCardDeckFullRestMapper tradingCardDeckFullRestMapper;

	private final PageMapper pageMapper;

	private final SortMapper sortMapper;

	public TradingCardDeckRestReader(TradingCardDeckRestQuery tradingCardDeckRestQuery, TradingCardDeckBaseRestMapper tradingCardDeckBaseRestMapper,
			TradingCardDeckFullRestMapper tradingCardDeckFullRestMapper, PageMapper pageMapper, SortMapper sortMapper) {
		this.tradingCardDeckRestQuery = tradingCardDeckRestQuery;
		this.tradingCardDeckBaseRestMapper = tradingCardDeckBaseRestMapper;
		this.tradingCardDeckFullRestMapper = tradingCardDeckFullRestMapper;
		this.pageMapper = pageMapper;
		this.sortMapper = sortMapper;
	}

	@Override
	public TradingCardDeckBaseResponse readBase(TradingCardDeckRestBeanParams input) {
		Page<TradingCardDeck> tradingCardDeckPage = tradingCardDeckRestQuery.query(input);
		TradingCardDeckBaseResponse tradingCardDeckResponse = new TradingCardDeckBaseResponse();
		tradingCardDeckResponse.setPage(pageMapper.fromPageToRestResponsePage(tradingCardDeckPage));
		tradingCardDeckResponse.setSort(sortMapper.map(input.getSort()));
		tradingCardDeckResponse.getTradingCardDecks().addAll(tradingCardDeckBaseRestMapper.mapBase(tradingCardDeckPage.getContent()));
		return tradingCardDeckResponse;
	}

	@Override
	public TradingCardDeckFullResponse readFull(String uid) {
		StaticValidator.requireUid(uid);
		TradingCardDeckRestBeanParams tradingCardDeckRestBeanParams = new TradingCardDeckRestBeanParams();
		tradingCardDeckRestBeanParams.setUid(uid);
		Page<TradingCardDeck> tradingCardDeckPage = tradingCardDeckRestQuery.query(tradingCardDeckRestBeanParams);
		TradingCardDeckFullResponse tradingCardDeckResponse = new TradingCardDeckFullResponse();
		tradingCardDeckResponse.setTradingCardDeck(tradingCardDeckFullRestMapper
				.mapFull(Iterables.getOnlyElement(tradingCardDeckPage.getContent(), null)));
		return tradingCardDeckResponse;
	}

}
