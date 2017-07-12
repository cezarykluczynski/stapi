package com.cezarykluczynski.stapi.etl.trading_card.creation.processor;

import com.cezarykluczynski.stapi.model.trading_card.entity.TradingCard;
import com.google.common.collect.Sets;
import org.jsoup.nodes.Element;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class TradingCardsTableProcessor implements ItemProcessor<Element, Set<TradingCard>> {

	@Override
	public Set<TradingCard> process(Element item) throws Exception {
		// TODO
		return Sets.newHashSet();
	}

}
