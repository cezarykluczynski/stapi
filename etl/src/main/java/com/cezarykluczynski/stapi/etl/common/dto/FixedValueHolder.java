package com.cezarykluczynski.stapi.etl.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor(staticName = "of")
@NoArgsConstructor(staticName = "empty")
@Getter
public class FixedValueHolder<VALUE> {

	private boolean found;

	private VALUE value;

	public static <T> FixedValueHolder<T> found(T value) {
		return FixedValueHolder.of(true, value);
	}

	public static <T> FixedValueHolder<T> notFound(T value) {
		return FixedValueHolder.of(false, value);
	}

}
