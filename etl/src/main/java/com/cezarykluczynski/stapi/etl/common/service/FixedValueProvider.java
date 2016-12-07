package com.cezarykluczynski.stapi.etl.common.service;

import com.cezarykluczynski.stapi.etl.common.dto.FixedValueHolder;

public interface FixedValueProvider<KEY, VALUE> {

	FixedValueHolder<VALUE> getSearchedValue(KEY key);

}
