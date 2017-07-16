package com.cezarykluczynski.stapi.etl.trading_card.creation.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

@Service
public class BoxesPerCaseProcessor implements ItemProcessor<String, Integer> {

	@Override
	public Integer process(String item) throws Exception {
		// TODO
		return null;
	}

}
