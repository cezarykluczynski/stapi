package com.cezarykluczynski.stapi.etl.common.dto;

import lombok.Getter;

@Getter
public class EnrichablePair<Input, Output> {

	private Input input;

	private Output output;

	public static <Input, Output> EnrichablePair<Input, Output> of(Input input, Output output) {
		return new EnrichablePair<>(input, output);
	}

	private EnrichablePair(Input input, Output output) {
		this.input = input;
		this.output = output;
	}

}
