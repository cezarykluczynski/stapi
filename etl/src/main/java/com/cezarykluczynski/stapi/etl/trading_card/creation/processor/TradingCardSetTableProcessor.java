package com.cezarykluczynski.stapi.etl.trading_card.creation.processor;

import com.cezarykluczynski.stapi.model.trading_card_set.entity.TradingCardSet;
import org.jsoup.nodes.Element;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

@Service
public class TradingCardSetTableProcessor implements ItemProcessor<Element, TradingCardSet> {

	@Override
	public TradingCardSet process(Element item) throws Exception {
		// TODO
		return null;
	}

}
