package com.cezarykluczynski.stapi.etl.trading_card.creation.processor;

import com.cezarykluczynski.stapi.etl.common.dto.FixedValueHolder;
import com.cezarykluczynski.stapi.etl.trading_card.creation.dto.ProductionRunDTO;
import com.cezarykluczynski.stapi.etl.trading_card.creation.dto.TradingCardSetValueWithName;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class ProductionRunProcessor implements ItemProcessor<TradingCardSetValueWithName, ProductionRunDTO> {

	private final BoxesPerCaseFixedValueProvider boxesPerCaseFixedValueProvider;

	@Inject
	public ProductionRunProcessor(BoxesPerCaseFixedValueProvider boxesPerCaseFixedValueProvider) {
		this.boxesPerCaseFixedValueProvider = boxesPerCaseFixedValueProvider;
	}

	@Override
	public ProductionRunDTO process(TradingCardSetValueWithName item) throws Exception {
		if (item == null) {
			return null;
		}

		FixedValueHolder<ProductionRunDTO> productionRunDTOFixedValueHolder = boxesPerCaseFixedValueProvider.getSearchedValue(item.getName());
		if (productionRunDTOFixedValueHolder.isFound()) {
			return productionRunDTOFixedValueHolder.getValue();
		}

		// TODO
		return null;
	}

}
