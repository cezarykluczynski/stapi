package com.cezarykluczynski.stapi.server.trading_card.reader;

import com.cezarykluczynski.stapi.client.v1.soap.TradingCardBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardFullResponse;
import com.cezarykluczynski.stapi.model.trading_card.entity.TradingCard;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper;
import com.cezarykluczynski.stapi.server.common.reader.BaseReader;
import com.cezarykluczynski.stapi.server.common.reader.FullReader;
import com.cezarykluczynski.stapi.server.common.validator.StaticValidator;
import com.cezarykluczynski.stapi.server.trading_card.mapper.TradingCardBaseSoapMapper;
import com.cezarykluczynski.stapi.server.trading_card.mapper.TradingCardFullSoapMapper;
import com.cezarykluczynski.stapi.server.trading_card.query.TradingCardSoapQuery;
import com.google.common.collect.Iterables;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class TradingCardSoapReader implements BaseReader<TradingCardBaseRequest, TradingCardBaseResponse>,
		FullReader<TradingCardFullRequest, TradingCardFullResponse> {

	private final TradingCardSoapQuery tradingCardSoapQuery;

	private final TradingCardBaseSoapMapper tradingCardBaseSoapMapper;

	private final TradingCardFullSoapMapper tradingCardFullSoapMapper;

	private final PageMapper pageMapper;

	private final SortMapper sortMapper;

	public TradingCardSoapReader(TradingCardSoapQuery tradingCardSoapQuery, TradingCardBaseSoapMapper tradingCardBaseSoapMapper,
			TradingCardFullSoapMapper tradingCardFullSoapMapper, PageMapper pageMapper, SortMapper sortMapper) {
		this.tradingCardSoapQuery = tradingCardSoapQuery;
		this.tradingCardBaseSoapMapper = tradingCardBaseSoapMapper;
		this.tradingCardFullSoapMapper = tradingCardFullSoapMapper;
		this.pageMapper = pageMapper;
		this.sortMapper = sortMapper;
	}

	@Override
	public TradingCardBaseResponse readBase(TradingCardBaseRequest input) {
		Page<TradingCard> tradingCardPage = tradingCardSoapQuery.query(input);
		TradingCardBaseResponse tradingCardResponse = new TradingCardBaseResponse();
		tradingCardResponse.setPage(pageMapper.fromPageToSoapResponsePage(tradingCardPage));
		tradingCardResponse.setSort(sortMapper.map(input.getSort()));
		tradingCardResponse.getTradingCards().addAll(tradingCardBaseSoapMapper.mapBase(tradingCardPage.getContent()));
		return tradingCardResponse;
	}

	@Override
	public TradingCardFullResponse readFull(TradingCardFullRequest input) {
		StaticValidator.requireUid(input.getUid());
		Page<TradingCard> tradingCardPage = tradingCardSoapQuery.query(input);
		TradingCardFullResponse tradingCardFullResponse = new TradingCardFullResponse();
		tradingCardFullResponse.setTradingCard(tradingCardFullSoapMapper
				.mapFull(Iterables.getOnlyElement(tradingCardPage.getContent(), null)));
		return tradingCardFullResponse;
	}

}
