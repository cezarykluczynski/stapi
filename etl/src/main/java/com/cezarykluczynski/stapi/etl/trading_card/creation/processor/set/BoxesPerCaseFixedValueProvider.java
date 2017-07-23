package com.cezarykluczynski.stapi.etl.trading_card.creation.processor.set;

import com.cezarykluczynski.stapi.etl.common.dto.FixedValueHolder;
import com.cezarykluczynski.stapi.etl.common.interfaces.FixedValueProvider;
import com.cezarykluczynski.stapi.etl.trading_card.creation.dto.ProductionRunDTO;
import com.cezarykluczynski.stapi.model.trading_card_set.entity.enums.ProductionRunUnit;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class BoxesPerCaseFixedValueProvider implements FixedValueProvider<String, ProductionRunDTO> {

	private static final Map<String, ProductionRunDTO> PRODUCTION_RUN_MAP = Maps.newHashMap();

	static {
		PRODUCTION_RUN_MAP.put("The Making of Star Trek: The Next Generation", ProductionRunDTO.of(150000, ProductionRunUnit.SET));
	}

	@Override
	public FixedValueHolder<ProductionRunDTO> getSearchedValue(String key) {
		return FixedValueHolder.of(PRODUCTION_RUN_MAP.containsKey(key), PRODUCTION_RUN_MAP.get(key));
	}

}
