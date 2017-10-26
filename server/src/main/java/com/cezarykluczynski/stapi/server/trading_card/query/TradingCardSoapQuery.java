package com.cezarykluczynski.stapi.server.trading_card.query;

import com.cezarykluczynski.stapi.client.v1.soap.TradingCardBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardFullRequest;
import com.cezarykluczynski.stapi.model.trading_card.dto.TradingCardRequestDTO;
import com.cezarykluczynski.stapi.model.trading_card.entity.TradingCard;
import com.cezarykluczynski.stapi.model.trading_card.repository.TradingCardRepository;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.trading_card.mapper.TradingCardBaseSoapMapper;
import com.cezarykluczynski.stapi.server.trading_card.mapper.TradingCardFullSoapMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class TradingCardSoapQuery {

	private final TradingCardBaseSoapMapper tradingCardBaseSoapMapper;

	private final TradingCardFullSoapMapper tradingCardFullSoapMapper;

	private final PageMapper pageMapper;

	private final TradingCardRepository tradingCardRepository;

	public TradingCardSoapQuery(TradingCardBaseSoapMapper tradingCardBaseSoapMapper, TradingCardFullSoapMapper tradingCardFullSoapMapper,
			PageMapper pageMapper, TradingCardRepository tradingCardRepository) {
		this.tradingCardBaseSoapMapper = tradingCardBaseSoapMapper;
		this.tradingCardFullSoapMapper = tradingCardFullSoapMapper;
		this.pageMapper = pageMapper;
		this.tradingCardRepository = tradingCardRepository;
	}

	public Page<TradingCard> query(TradingCardBaseRequest tradingCardBaseRequest) {
		TradingCardRequestDTO tradingCardRequestDTO = tradingCardBaseSoapMapper.mapBase(tradingCardBaseRequest);
		PageRequest pageRequest = pageMapper.fromRequestPageToPageRequest(tradingCardBaseRequest.getPage());
		return tradingCardRepository.findMatching(tradingCardRequestDTO, pageRequest);
	}

	public Page<TradingCard> query(TradingCardFullRequest tradingCardFullRequest) {
		TradingCardRequestDTO tradingCardRequestDTO = tradingCardFullSoapMapper.mapFull(tradingCardFullRequest);
		return tradingCardRepository.findMatching(tradingCardRequestDTO, pageMapper.getDefaultPageRequest());
	}

}
