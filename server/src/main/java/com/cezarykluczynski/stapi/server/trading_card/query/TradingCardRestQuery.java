package com.cezarykluczynski.stapi.server.trading_card.query;

import com.cezarykluczynski.stapi.model.trading_card.dto.TradingCardRequestDTO;
import com.cezarykluczynski.stapi.model.trading_card.entity.TradingCard;
import com.cezarykluczynski.stapi.model.trading_card.repository.TradingCardRepository;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.trading_card.dto.TradingCardRestBeanParams;
import com.cezarykluczynski.stapi.server.trading_card.mapper.TradingCardBaseRestMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class TradingCardRestQuery {

	private final TradingCardBaseRestMapper tradingCardBaseRestMapper;

	private final PageMapper pageMapper;

	private final TradingCardRepository tradingCardRepository;

	public TradingCardRestQuery(TradingCardBaseRestMapper tradingCardBaseRestMapper, PageMapper pageMapper,
			TradingCardRepository tradingCardRepository) {
		this.tradingCardBaseRestMapper = tradingCardBaseRestMapper;
		this.pageMapper = pageMapper;
		this.tradingCardRepository = tradingCardRepository;
	}

	public Page<TradingCard> query(TradingCardRestBeanParams tradingCardRestBeanParams) {
		TradingCardRequestDTO tradingCardRequestDTO = tradingCardBaseRestMapper.mapBase(tradingCardRestBeanParams);
		PageRequest pageRequest = pageMapper.fromPageSortBeanParamsToPageRequest(tradingCardRestBeanParams);
		return tradingCardRepository.findMatching(tradingCardRequestDTO, pageRequest);
	}

}
