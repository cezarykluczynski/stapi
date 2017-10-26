package com.cezarykluczynski.stapi.etl.trading_card.creation.processor.deck;

import com.cezarykluczynski.stapi.model.trading_card_deck.entity.TradingCardDeck;
import com.cezarykluczynski.stapi.util.constant.AttributeName;
import com.cezarykluczynski.stapi.util.constant.TagName;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.common.primitives.Ints;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class TradingCardDecksTableProcessor implements ItemProcessor<Element, Set<TradingCardDeck>> {

	private final TradingCardDeckTableRowProcessor tradingCardDeckTableRowProcessor;

	public TradingCardDecksTableProcessor(TradingCardDeckTableRowProcessor tradingCardDeckTableRowProcessor) {
		this.tradingCardDeckTableRowProcessor = tradingCardDeckTableRowProcessor;
	}

	@Override
	public Set<TradingCardDeck> process(Element item) throws Exception {
		Set<TradingCardDeck> tradingCardDeckSet = Sets.newHashSet();

		Elements tbodyCandidates = item.getElementsByTag(TagName.TBODY);

		if (tbodyCandidates.isEmpty()) {
			return tradingCardDeckSet;
		}

		Elements trCandidates = tbodyCandidates.get(0).getElementsByTag(TagName.TR);

		if (trCandidates.isEmpty()) {
			return tradingCardDeckSet;
		}

		List<List<Element>> elementListList = Lists.newArrayList();
		List<Element> currentList = null;
		boolean flushed = false;

		for (Element trCandidate : trCandidates) {
			flushed = false;
			Elements tdCandiates = trCandidate.getElementsByTag(TagName.TD);

			if (!tdCandiates.isEmpty()) {
				for (Element tdCandiate : tdCandiates) {
					Integer colspan = Ints.tryParse(tdCandiate.attr(AttributeName.COLSPAN));

					if (colspan != null && colspan > 2) {
						if (currentList != null) {
							flushed = true;
							if (!CollectionUtils.isEmpty(currentList)) {
								elementListList.add(currentList);
							}
						}
						currentList = Lists.newArrayList();
					}
				}
			}

			if (currentList == null) {
				log.warn("Current list was null when trying to add \"{}\"", trCandidate);
			} else {
				currentList.add(trCandidate);
			}
		}

		if (!flushed && !CollectionUtils.isEmpty(currentList)) {
			elementListList.add(currentList);
		}

		for (List<Element> elementList : elementListList) {
			TradingCardDeck tradingCardDeck = tradingCardDeckTableRowProcessor.process(elementList);
			if (tradingCardDeck != null) {
				tradingCardDeckSet.add(tradingCardDeck);
			}
		}

		return tradingCardDeckSet;
	}

}
