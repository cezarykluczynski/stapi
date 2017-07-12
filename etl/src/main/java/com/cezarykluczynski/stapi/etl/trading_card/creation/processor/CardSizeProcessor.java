package com.cezarykluczynski.stapi.etl.trading_card.creation.processor;

import com.cezarykluczynski.stapi.etl.trading_card.creation.dto.CardSizeDTO;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

@Service
public class CardSizeProcessor implements ItemProcessor<String, CardSizeDTO> {

	@Override
	public CardSizeDTO process(String item) throws Exception {
		// TODO
		return null;
	}

}
