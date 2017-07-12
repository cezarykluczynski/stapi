package com.cezarykluczynski.stapi.etl.trading_card.creation.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.trading_card.creation.dto.TradingCarSetHeaderValuePair;
import com.cezarykluczynski.stapi.etl.trading_card.creation.dto.TradingCardSetTableHeader;
import com.cezarykluczynski.stapi.model.trading_card_set.entity.TradingCardSet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class TradingCardSetTableValuesEnrichingProcessor
		implements ItemEnrichingProcessor<EnrichablePair<TradingCarSetHeaderValuePair, TradingCardSet>> {

	@Override
	public void enrich(EnrichablePair<TradingCarSetHeaderValuePair, TradingCardSet> enrichablePair) throws Exception {
		String key = enrichablePair.getInput().getHeaderText();

		switch(key) {
			case TradingCardSetTableHeader.RELEASED:
				// TODO
				break;
			case TradingCardSetTableHeader.CARDS_PER_PACK:
				// TODO:
				break;
			case TradingCardSetTableHeader.PACKS_PER_BOX:
				// TODO:
				break;
			case TradingCardSetTableHeader.BOXES_PER_CASE:
				// TODO:
				break;
			case TradingCardSetTableHeader.PRODUCTION_RUN:
				// TODO:
				break;
			case TradingCardSetTableHeader.CARDS_SIZE:
				// TODO:
				break;
			case TradingCardSetTableHeader.MANUFACTURER:
				// TODO:
				break;
			case TradingCardSetTableHeader.COUNTRY:
				// TODO:
				break;
			default:
				if (!TradingCardSetTableHeader.SPARSE_HEADERS.contains(key)) {
					log.info("Unknown trading card set key: {}", key);
				}
				break;
		}

	}

}
