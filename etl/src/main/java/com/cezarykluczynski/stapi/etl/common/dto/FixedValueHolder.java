package com.cezarykluczynski.stapi.etl.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor(staticName = "of")
@NoArgsConstructor(staticName = "notFound")
@Getter
public class FixedValueHolder<T> {

	private boolean found;

	private T value;

	public static <T> FixedValueHolder<T> found(T value) {
		return FixedValueHolder.of(true, value);
	}

}
