package com.cezarykluczynski.stapi.server.trading_card_set.query;

import com.cezarykluczynski.stapi.client.v1.soap.TradingCardSetBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardSetFullRequest;
import com.cezarykluczynski.stapi.model.trading_card_set.dto.TradingCardSetRequestDTO;
import com.cezarykluczynski.stapi.model.trading_card_set.entity.TradingCardSet;
import com.cezarykluczynski.stapi.model.trading_card_set.repository.TradingCardSetRepository;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.trading_card_set.mapper.TradingCardSetBaseSoapMapper;
import com.cezarykluczynski.stapi.server.trading_card_set.mapper.TradingCardSetFullSoapMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class TradingCardSetSoapQuery {

	private final TradingCardSetBaseSoapMapper tradingCardSetBaseSoapMapper;

	private final TradingCardSetFullSoapMapper tradingCardSetFullSoapMapper;

	private final PageMapper pageMapper;

	private final TradingCardSetRepository tradingCardSetRepository;

	public TradingCardSetSoapQuery(TradingCardSetBaseSoapMapper tradingCardSetBaseSoapMapper,
			TradingCardSetFullSoapMapper tradingCardSetFullSoapMapper, PageMapper pageMapper, TradingCardSetRepository tradingCardSetRepository) {
		this.tradingCardSetBaseSoapMapper = tradingCardSetBaseSoapMapper;
		this.tradingCardSetFullSoapMapper = tradingCardSetFullSoapMapper;
		this.pageMapper = pageMapper;
		this.tradingCardSetRepository = tradingCardSetRepository;
	}

	public Page<TradingCardSet> query(TradingCardSetBaseRequest tradingCardSetBaseRequest) {
		TradingCardSetRequestDTO tradingCardSetRequestDTO = tradingCardSetBaseSoapMapper.mapBase(tradingCardSetBaseRequest);
		PageRequest pageRequest = pageMapper.fromRequestPageToPageRequest(tradingCardSetBaseRequest.getPage());
		return tradingCardSetRepository.findMatching(tradingCardSetRequestDTO, pageRequest);
	}

	public Page<TradingCardSet> query(TradingCardSetFullRequest tradingCardSetFullRequest) {
		TradingCardSetRequestDTO tradingCardSetRequestDTO = tradingCardSetFullSoapMapper.mapFull(tradingCardSetFullRequest);
		return tradingCardSetRepository.findMatching(tradingCardSetRequestDTO, pageMapper.getDefaultPageRequest());
	}

}
