package com.cezarykluczynski.stapi.etl.trading_card.creation.processor;

import com.cezarykluczynski.stapi.model.trading_card_set.entity.TradingCardSet;
import com.cezarykluczynski.stapi.sources.wordpress.dto.Page;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

@Service
public class TradingCardSetProcessor implements ItemProcessor<Page, TradingCardSet> {

	@Override
	public TradingCardSet process(Page item) throws Exception {
		// TODO
		return null;
	}

}
