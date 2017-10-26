package com.cezarykluczynski.stapi.server.trading_card_set.query;

import com.cezarykluczynski.stapi.model.trading_card_set.dto.TradingCardSetRequestDTO;
import com.cezarykluczynski.stapi.model.trading_card_set.entity.TradingCardSet;
import com.cezarykluczynski.stapi.model.trading_card_set.repository.TradingCardSetRepository;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.trading_card_set.dto.TradingCardSetRestBeanParams;
import com.cezarykluczynski.stapi.server.trading_card_set.mapper.TradingCardSetBaseRestMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class TradingCardSetRestQuery {

	private final TradingCardSetBaseRestMapper tradingCardSetBaseRestMapper;

	private final PageMapper pageMapper;

	private final TradingCardSetRepository tradingCardSetRepository;

	public TradingCardSetRestQuery(TradingCardSetBaseRestMapper tradingCardSetBaseRestMapper, PageMapper pageMapper,
			TradingCardSetRepository tradingCardSetRepository) {
		this.tradingCardSetBaseRestMapper = tradingCardSetBaseRestMapper;
		this.pageMapper = pageMapper;
		this.tradingCardSetRepository = tradingCardSetRepository;
	}

	public Page<TradingCardSet> query(TradingCardSetRestBeanParams tradingCardSetRestBeanParams) {
		TradingCardSetRequestDTO tradingCardSetRequestDTO = tradingCardSetBaseRestMapper.mapBase(tradingCardSetRestBeanParams);
		PageRequest pageRequest = pageMapper.fromPageSortBeanParamsToPageRequest(tradingCardSetRestBeanParams);
		return tradingCardSetRepository.findMatching(tradingCardSetRequestDTO, pageRequest);
	}

}
