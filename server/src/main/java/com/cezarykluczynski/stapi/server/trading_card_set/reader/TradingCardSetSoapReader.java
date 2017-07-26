package com.cezarykluczynski.stapi.server.trading_card_set.reader;

import com.cezarykluczynski.stapi.client.v1.soap.TradingCardSetBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardSetBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardSetFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardSetFullResponse;
import com.cezarykluczynski.stapi.model.trading_card_set.entity.TradingCardSet;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper;
import com.cezarykluczynski.stapi.server.common.reader.BaseReader;
import com.cezarykluczynski.stapi.server.common.reader.FullReader;
import com.cezarykluczynski.stapi.server.common.validator.StaticValidator;
import com.cezarykluczynski.stapi.server.trading_card_set.mapper.TradingCardSetBaseSoapMapper;
import com.cezarykluczynski.stapi.server.trading_card_set.mapper.TradingCardSetFullSoapMapper;
import com.cezarykluczynski.stapi.server.trading_card_set.query.TradingCardSetSoapQuery;
import com.google.common.collect.Iterables;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class TradingCardSetSoapReader implements BaseReader<TradingCardSetBaseRequest, TradingCardSetBaseResponse>,
		FullReader<TradingCardSetFullRequest, TradingCardSetFullResponse> {

	private final TradingCardSetSoapQuery tradingCardSetSoapQuery;

	private final TradingCardSetBaseSoapMapper tradingCardSetBaseSoapMapper;

	private final TradingCardSetFullSoapMapper tradingCardSetFullSoapMapper;

	private final PageMapper pageMapper;

	private final SortMapper sortMapper;

	public TradingCardSetSoapReader(TradingCardSetSoapQuery tradingCardSetSoapQuery, TradingCardSetBaseSoapMapper tradingCardSetBaseSoapMapper,
			TradingCardSetFullSoapMapper tradingCardSetFullSoapMapper, PageMapper pageMapper, SortMapper sortMapper) {
		this.tradingCardSetSoapQuery = tradingCardSetSoapQuery;
		this.tradingCardSetBaseSoapMapper = tradingCardSetBaseSoapMapper;
		this.tradingCardSetFullSoapMapper = tradingCardSetFullSoapMapper;
		this.pageMapper = pageMapper;
		this.sortMapper = sortMapper;
	}

	@Override
	public TradingCardSetBaseResponse readBase(TradingCardSetBaseRequest input) {
		Page<TradingCardSet> tradingCardSetPage = tradingCardSetSoapQuery.query(input);
		TradingCardSetBaseResponse tradingCardSetResponse = new TradingCardSetBaseResponse();
		tradingCardSetResponse.setPage(pageMapper.fromPageToSoapResponsePage(tradingCardSetPage));
		tradingCardSetResponse.setSort(sortMapper.map(input.getSort()));
		tradingCardSetResponse.getTradingCardSets().addAll(tradingCardSetBaseSoapMapper.mapBase(tradingCardSetPage.getContent()));
		return tradingCardSetResponse;
	}

	@Override
	public TradingCardSetFullResponse readFull(TradingCardSetFullRequest input) {
		StaticValidator.requireUid(input.getUid());
		Page<TradingCardSet> tradingCardSetPage = tradingCardSetSoapQuery.query(input);
		TradingCardSetFullResponse tradingCardSetFullResponse = new TradingCardSetFullResponse();
		tradingCardSetFullResponse.setTradingCardSet(tradingCardSetFullSoapMapper
				.mapFull(Iterables.getOnlyElement(tradingCardSetPage.getContent(), null)));
		return tradingCardSetFullResponse;
	}

}
