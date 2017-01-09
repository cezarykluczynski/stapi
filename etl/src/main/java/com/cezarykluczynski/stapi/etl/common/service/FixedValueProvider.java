package com.cezarykluczynski.stapi.etl.common.service;

import com.cezarykluczynski.stapi.etl.common.dto.FixedValueHolder;

public interface FixedValueProvider<K, V> {

	FixedValueHolder<V> getSearchedValue(K key);

}
