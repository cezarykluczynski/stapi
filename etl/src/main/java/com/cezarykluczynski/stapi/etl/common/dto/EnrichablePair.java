package com.cezarykluczynski.stapi.etl.common.dto;

import lombok.Getter;

@Getter
public final class EnrichablePair<I, O> {

	private final I input;

	private final O output;

	private EnrichablePair(I input, O output) {
		this.input = input;
		this.output = output;
	}

	public static <I, O> EnrichablePair<I, O> of(I input, O output) {
		return new EnrichablePair<>(input, output);
	}

}
