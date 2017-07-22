package com.cezarykluczynski.stapi.etl.trading_card.creation.processor;

import com.cezarykluczynski.stapi.model.trading_card.entity.TradingCard;
import org.jsoup.nodes.Element;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class TradingCardsProcessor implements ItemProcessor<List<Element>, Set<TradingCard>> {

	@Override
	public Set<TradingCard> process(List<Element> item) throws Exception {
		// TODO
		return null;
	}

}
