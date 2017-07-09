package com.cezarykluczynski.stapi.etl.trading_card.creation.processor;

import com.cezarykluczynski.stapi.etl.trading_card.creation.dto.TradingCardSetElements;
import com.cezarykluczynski.stapi.model.trading_card_set.entity.TradingCardSet;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

@Service
public class TradingCardSetElementsProcessor implements ItemProcessor<TradingCardSetElements, TradingCardSet> {

	@Override
	public TradingCardSet process(TradingCardSetElements item) throws Exception {
		// TODO
		return null;
	}

}
