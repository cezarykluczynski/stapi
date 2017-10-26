package com.cezarykluczynski.stapi.etl.trading_card.creation.processor.set;

import com.cezarykluczynski.stapi.etl.trading_card.creation.dto.TradingCardSetElements;
import com.cezarykluczynski.stapi.model.trading_card_set.entity.TradingCardSet;
import com.cezarykluczynski.stapi.util.constant.TagName;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TradingCardSetTableProcessor implements ItemProcessor<Element, TradingCardSet> {

	private final TradingCardSetElementsProcessor tradingCardSetElementsProcessor;

	public TradingCardSetTableProcessor(TradingCardSetElementsProcessor tradingCardSetElementsProcessor) {
		this.tradingCardSetElementsProcessor = tradingCardSetElementsProcessor;
	}

	@Override
	public TradingCardSet process(Element item) throws Exception {
		Elements tbodyCandidates = item.getElementsByTag(TagName.TBODY);

		if (tbodyCandidates.isEmpty()) {
			log.warn("Could not find {}", TagName.TBODY);
			return null;
		}

		Element tbody = tbodyCandidates.get(0);
		Elements trs = tbody.getElementsByTag(TagName.TR);

		if (trs.isEmpty()) {
			log.warn("Could not find {}", TagName.TR);
			return null;
		}

		if (trs.size() < 3) {
			log.warn("Not enough {} tags", TagName.TR);
			return null;
		}

		Element nextToLastRow = trs.get(trs.size() - 2);
		Element lastRow = trs.get(trs.size() - 1);

		TradingCardSetElements tradingCardSetElements = new TradingCardSetElements();
		tradingCardSetElements.setNextToLastRow(nextToLastRow);
		tradingCardSetElements.setLastRow(lastRow);

		return tradingCardSetElementsProcessor.process(tradingCardSetElements);
	}

}
