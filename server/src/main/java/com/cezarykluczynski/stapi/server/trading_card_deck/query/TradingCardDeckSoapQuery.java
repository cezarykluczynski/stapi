package com.cezarykluczynski.stapi.server.trading_card_deck.query;

import com.cezarykluczynski.stapi.client.v1.soap.TradingCardDeckBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.TradingCardDeckFullRequest;
import com.cezarykluczynski.stapi.model.trading_card_deck.dto.TradingCardDeckRequestDTO;
import com.cezarykluczynski.stapi.model.trading_card_deck.entity.TradingCardDeck;
import com.cezarykluczynski.stapi.model.trading_card_deck.repository.TradingCardDeckRepository;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.trading_card_deck.mapper.TradingCardDeckBaseSoapMapper;
import com.cezarykluczynski.stapi.server.trading_card_deck.mapper.TradingCardDeckFullSoapMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class TradingCardDeckSoapQuery {

	private final TradingCardDeckBaseSoapMapper tradingCardDeckBaseSoapMapper;

	private final TradingCardDeckFullSoapMapper tradingCardDeckFullSoapMapper;

	private final PageMapper pageMapper;

	private final TradingCardDeckRepository tradingCardDeckRepository;

	public TradingCardDeckSoapQuery(TradingCardDeckBaseSoapMapper tradingCardDeckBaseSoapMapper,
			TradingCardDeckFullSoapMapper tradingCardDeckFullSoapMapper, PageMapper pageMapper, TradingCardDeckRepository tradingCardDeckRepository) {
		this.tradingCardDeckBaseSoapMapper = tradingCardDeckBaseSoapMapper;
		this.tradingCardDeckFullSoapMapper = tradingCardDeckFullSoapMapper;
		this.pageMapper = pageMapper;
		this.tradingCardDeckRepository = tradingCardDeckRepository;
	}

	public Page<TradingCardDeck> query(TradingCardDeckBaseRequest tradingCardDeckBaseRequest) {
		TradingCardDeckRequestDTO tradingCardDeckRequestDTO = tradingCardDeckBaseSoapMapper.mapBase(tradingCardDeckBaseRequest);
		PageRequest pageRequest = pageMapper.fromRequestPageToPageRequest(tradingCardDeckBaseRequest.getPage());
		return tradingCardDeckRepository.findMatching(tradingCardDeckRequestDTO, pageRequest);
	}

	public Page<TradingCardDeck> query(TradingCardDeckFullRequest tradingCardDeckFullRequest) {
		TradingCardDeckRequestDTO tradingCardDeckRequestDTO = tradingCardDeckFullSoapMapper.mapFull(tradingCardDeckFullRequest);
		return tradingCardDeckRepository.findMatching(tradingCardDeckRequestDTO, pageMapper.getDefaultPageRequest());
	}

}
