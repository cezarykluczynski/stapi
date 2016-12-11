package com.cezarykluczynski.stapi.etl.configuration.job;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class StepDependency {

	private String name;

	private List<String> dependencies;

	private List<String> disallowConcurrent;

}
