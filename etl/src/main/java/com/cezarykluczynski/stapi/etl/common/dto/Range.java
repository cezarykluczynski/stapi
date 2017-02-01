package com.cezarykluczynski.stapi.etl.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(staticName = "of")
@Getter
public class Range<T> {

	private T from;

	private T to;

}
