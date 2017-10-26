package com.cezarykluczynski.stapi.etl.trading_card.creation.processor.card;

import com.cezarykluczynski.stapi.model.trading_card.entity.TradingCard;
import com.cezarykluczynski.stapi.util.constant.TagName;
import com.google.common.collect.Sets;
import org.apache.commons.collections.CollectionUtils;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class TradingCardsProcessor implements ItemProcessor<List<Element>, Set<TradingCard>> {

	private final TradingCardProcessor tradingCardProcessor;

	public TradingCardsProcessor(TradingCardProcessor tradingCardProcessor) {
		this.tradingCardProcessor = tradingCardProcessor;
	}

	@Override
	public Set<TradingCard> process(List<Element> item) throws Exception {
		Set<TradingCard> tradingCardSet = Sets.newHashSet();

		if (CollectionUtils.isEmpty(item)) {
			return tradingCardSet;
		}

		for (Element element : item) {
			Elements tds = element.getElementsByTag(TagName.TD);
			for (Element td : tds) {
				TradingCard tradingCard = tradingCardProcessor.process(td);
				if (tradingCard != null) {
					tradingCardSet.add(tradingCard);
				}
			}
		}

		return tradingCardSet;
	}

}
