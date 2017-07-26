package com.cezarykluczynski.stapi.server.trading_card_deck.reader;

import com.cezarykluczynski.stapi.client.v1.soap.TradingCardDeckBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardDeckBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardDeckFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardDeckFullResponse;
import com.cezarykluczynski.stapi.model.trading_card_deck.entity.TradingCardDeck;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper;
import com.cezarykluczynski.stapi.server.common.reader.BaseReader;
import com.cezarykluczynski.stapi.server.common.reader.FullReader;
import com.cezarykluczynski.stapi.server.common.validator.StaticValidator;
import com.cezarykluczynski.stapi.server.trading_card_deck.mapper.TradingCardDeckBaseSoapMapper;
import com.cezarykluczynski.stapi.server.trading_card_deck.mapper.TradingCardDeckFullSoapMapper;
import com.cezarykluczynski.stapi.server.trading_card_deck.query.TradingCardDeckSoapQuery;
import com.google.common.collect.Iterables;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class TradingCardDeckSoapReader implements BaseReader<TradingCardDeckBaseRequest, TradingCardDeckBaseResponse>,
		FullReader<TradingCardDeckFullRequest, TradingCardDeckFullResponse> {

	private final TradingCardDeckSoapQuery tradingCardDeckSoapQuery;

	private final TradingCardDeckBaseSoapMapper tradingCardDeckBaseSoapMapper;

	private final TradingCardDeckFullSoapMapper tradingCardDeckFullSoapMapper;

	private final PageMapper pageMapper;

	private final SortMapper sortMapper;

	public TradingCardDeckSoapReader(TradingCardDeckSoapQuery tradingCardDeckSoapQuery, TradingCardDeckBaseSoapMapper tradingCardDeckBaseSoapMapper,
			TradingCardDeckFullSoapMapper tradingCardDeckFullSoapMapper, PageMapper pageMapper, SortMapper sortMapper) {
		this.tradingCardDeckSoapQuery = tradingCardDeckSoapQuery;
		this.tradingCardDeckBaseSoapMapper = tradingCardDeckBaseSoapMapper;
		this.tradingCardDeckFullSoapMapper = tradingCardDeckFullSoapMapper;
		this.pageMapper = pageMapper;
		this.sortMapper = sortMapper;
	}

	@Override
	public TradingCardDeckBaseResponse readBase(TradingCardDeckBaseRequest input) {
		Page<TradingCardDeck> tradingCardDeckPage = tradingCardDeckSoapQuery.query(input);
		TradingCardDeckBaseResponse tradingCardDeckResponse = new TradingCardDeckBaseResponse();
		tradingCardDeckResponse.setPage(pageMapper.fromPageToSoapResponsePage(tradingCardDeckPage));
		tradingCardDeckResponse.setSort(sortMapper.map(input.getSort()));
		tradingCardDeckResponse.getTradingCardDecks().addAll(tradingCardDeckBaseSoapMapper.mapBase(tradingCardDeckPage.getContent()));
		return tradingCardDeckResponse;
	}

	@Override
	public TradingCardDeckFullResponse readFull(TradingCardDeckFullRequest input) {
		StaticValidator.requireUid(input.getUid());
		Page<TradingCardDeck> tradingCardDeckPage = tradingCardDeckSoapQuery.query(input);
		TradingCardDeckFullResponse tradingCardDeckFullResponse = new TradingCardDeckFullResponse();
		tradingCardDeckFullResponse.setTradingCardDeck(tradingCardDeckFullSoapMapper
				.mapFull(Iterables.getOnlyElement(tradingCardDeckPage.getContent(), null)));
		return tradingCardDeckFullResponse;
	}

}
