package com.cezarykluczynski.stapi.etl.trading_card.creation.processor;

import com.cezarykluczynski.stapi.etl.trading_card.creation.dto.ProductionRunDTO;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

@Service
public class ProductionRunProcessor implements ItemProcessor<String, ProductionRunDTO> {

	@Override
	public ProductionRunDTO process(String item) throws Exception {
		// TODO
		return null;
	}

}
