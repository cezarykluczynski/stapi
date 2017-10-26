package com.cezarykluczynski.stapi.etl.trading_card.creation.processor.set;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.trading_card.creation.dto.TradingCarSetHeaderValuePair;
import com.cezarykluczynski.stapi.etl.trading_card.creation.dto.TradingCardSetElements;
import com.cezarykluczynski.stapi.model.trading_card_set.entity.TradingCardSet;
import com.cezarykluczynski.stapi.util.constant.TagName;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TradingCardSetElementsProcessor implements ItemProcessor<TradingCardSetElements, TradingCardSet> {

	private final TradingCardSetTableValuesEnrichingProcessor tradingCardSetTableValuesEnrichingProcessor;

	public TradingCardSetElementsProcessor(TradingCardSetTableValuesEnrichingProcessor tradingCardSetTableValuesEnrichingProcessor) {
		this.tradingCardSetTableValuesEnrichingProcessor = tradingCardSetTableValuesEnrichingProcessor;
	}

	@Override
	public TradingCardSet process(TradingCardSetElements item) throws Exception {
		Element nextToLastRow = item.getNextToLastRow();
		Element lastRow = item.getLastRow();

		Elements headers = nextToLastRow.getElementsByTag(TagName.TH);
		Elements values = lastRow.getElementsByTag(TagName.TD);

		if (headers.size() == 0) {
			log.info("No header cells found");
			return null;
		}

		if (headers.size() != values.size()) {
			log.info("Number of header cells does not equal number of value cells");
			return null;
		}

		TradingCardSet tradingCardSet = new TradingCardSet();

		for (int i = 0; i < headers.size(); i++) {
			TradingCarSetHeaderValuePair tradingCarSetHeaderValuePair = new TradingCarSetHeaderValuePair();
			tradingCarSetHeaderValuePair.setHeaderText(StringUtils.trim(headers.get(i).text()));
			tradingCarSetHeaderValuePair.setValueText(StringUtils.trim(values.get(i).text()));
			tradingCardSetTableValuesEnrichingProcessor.enrich(EnrichablePair.of(tradingCarSetHeaderValuePair, tradingCardSet));
		}

		return tradingCardSet;
	}

}
