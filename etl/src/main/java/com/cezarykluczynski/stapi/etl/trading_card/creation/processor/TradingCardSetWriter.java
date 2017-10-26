package com.cezarykluczynski.stapi.etl.trading_card.creation.processor;

import com.cezarykluczynski.stapi.model.trading_card_set.entity.TradingCardSet;
import com.cezarykluczynski.stapi.model.trading_card_set.repository.TradingCardSetRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TradingCardSetWriter implements ItemWriter<TradingCardSet> {

	private final TradingCardSetRepository tradingCardSetRepository;

	public TradingCardSetWriter(TradingCardSetRepository tradingCardSetRepository) {
		this.tradingCardSetRepository = tradingCardSetRepository;
	}

	@Override
	public void write(List<? extends TradingCardSet> items) throws Exception {
		tradingCardSetRepository.save(items);
	}

}
