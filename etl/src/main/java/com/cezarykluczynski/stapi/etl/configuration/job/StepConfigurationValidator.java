package com.cezarykluczynski.stapi.etl.configuration.job;

import com.cezarykluczynski.stapi.etl.configuration.job.properties.StepProperties;
import com.cezarykluczynski.stapi.etl.configuration.job.properties.StepToStepPropertiesProvider;
import com.cezarykluczynski.stapi.util.exception.StapiRuntimeException;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.batch.core.job.builder.JobBuilderException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class StepConfigurationValidator {

	private static final long NUMBER_OF_STEPS = 43;

	private final StepToStepPropertiesProvider stepToStepPropertiesProvider;

	private final List<StepProperties> stepPropertiesList = Lists.newArrayList();

	private final Map<String, StepProperties> stepPropertiesMap = Maps.newLinkedHashMap();

	public StepConfigurationValidator(StepToStepPropertiesProvider stepToStepPropertiesProvider) {
		this.stepToStepPropertiesProvider = stepToStepPropertiesProvider;
	}

	public void validate() {
		addAllToList();
		addAllToMap();
		validateNumberOfSteps();
		validateOrder();
	}

	private void addAllToList() {
		stepToStepPropertiesProvider.provide().forEach((stepName, stepProperties) -> {
			stepPropertiesList.add(Preconditions.checkNotNull(stepProperties, "StepProperties for step %s cannot be null", stepName));
		});
	}

	private void addAllToMap() {
		stepToStepPropertiesProvider.provide().forEach(stepPropertiesMap::put);
	}

	private void validateNumberOfSteps() {
		if (stepPropertiesList.size() != NUMBER_OF_STEPS) {
			doThrow(String.format("Number of configured steps is %s, but %s steps found", NUMBER_OF_STEPS, stepPropertiesList.size()));
		}
	}

	private void validateOrder() {
		List<Pair<String, Integer>> list = stepPropertiesMap.entrySet()
				.stream()
				.map(entrySet -> Pair.of(entrySet.getKey(), entrySet.getValue().getOrder()))
				.collect(Collectors.toList());

		Collections.reverse(list);

		list.forEach(rightPair -> {
			String rightStepName = rightPair.getKey();
			Integer rightStepOrder = rightPair.getValue();
			list.forEach(leftPair -> {
				String leftStepName = leftPair.getKey();
				Integer leftStepOrder = leftPair.getValue();

				if (!rightStepName.equals(leftStepName) && rightStepOrder.equals(leftStepOrder)) {
					doThrow(String.format("Step %s has order %s, but this order was already given to step %s",
							rightStepName, rightStepOrder, leftStepName));
				}
			});
		});
	}

	private void doThrow(String message) {
		throw new JobBuilderException(new StapiRuntimeException(message));
	}


}
