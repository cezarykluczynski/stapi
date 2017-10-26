package com.cezarykluczynski.stapi.server.trading_card_deck.query;

import com.cezarykluczynski.stapi.model.trading_card_deck.dto.TradingCardDeckRequestDTO;
import com.cezarykluczynski.stapi.model.trading_card_deck.entity.TradingCardDeck;
import com.cezarykluczynski.stapi.model.trading_card_deck.repository.TradingCardDeckRepository;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.trading_card_deck.dto.TradingCardDeckRestBeanParams;
import com.cezarykluczynski.stapi.server.trading_card_deck.mapper.TradingCardDeckBaseRestMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class TradingCardDeckRestQuery {

	private final TradingCardDeckBaseRestMapper tradingCardDeckBaseRestMapper;

	private final PageMapper pageMapper;

	private final TradingCardDeckRepository tradingCardDeckRepository;

	public TradingCardDeckRestQuery(TradingCardDeckBaseRestMapper tradingCardDeckBaseRestMapper, PageMapper pageMapper,
			TradingCardDeckRepository tradingCardDeckRepository) {
		this.tradingCardDeckBaseRestMapper = tradingCardDeckBaseRestMapper;
		this.pageMapper = pageMapper;
		this.tradingCardDeckRepository = tradingCardDeckRepository;
	}

	public Page<TradingCardDeck> query(TradingCardDeckRestBeanParams tradingCardDeckRestBeanParams) {
		TradingCardDeckRequestDTO tradingCardDeckRequestDTO = tradingCardDeckBaseRestMapper.mapBase(tradingCardDeckRestBeanParams);
		PageRequest pageRequest = pageMapper.fromPageSortBeanParamsToPageRequest(tradingCardDeckRestBeanParams);
		return tradingCardDeckRepository.findMatching(tradingCardDeckRequestDTO, pageRequest);
	}

}
