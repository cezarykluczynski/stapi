package com.cezarykluczynski.stapi.etl.common.dto;

import lombok.Getter;

@Getter
public final class EnrichablePair<INPUT, OUTPUT> {

	private final INPUT input;

	private final OUTPUT output;

	private EnrichablePair(INPUT input, OUTPUT output) {
		this.input = input;
		this.output = output;
	}

	public static <INPUT, OUTPUT> EnrichablePair<INPUT, OUTPUT> of(INPUT input, OUTPUT output) {
		return new EnrichablePair<>(input, output);
	}

}
