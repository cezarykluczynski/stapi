package com.cezarykluczynski.stapi.etl.configuration.job;

import com.cezarykluczynski.stapi.etl.configuration.job.properties.StepProperties;
import com.cezarykluczynski.stapi.etl.configuration.job.properties.StepsProperties;
import com.cezarykluczynski.stapi.etl.util.constant.StepName;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.batch.core.job.builder.JobBuilderException;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class StepConfigurationValidator {

	private static final long NUMBER_OF_STEPS = 5;

	private StepsProperties stepsProperties;

	private List<StepProperties> stepPropertiesList;

	private Map<String, StepProperties> stepPropertiesMap;

	@Inject
	public StepConfigurationValidator(StepsProperties stepsProperties) {
		this.stepsProperties = stepsProperties;
	}

	public void validate() {
		addAllToList();
		addAllToMap();
		validateNumberOfSteps();
		validateOrder();
	}

	private void addAllToList() {
		stepPropertiesList = Lists.newArrayList();
		stepPropertiesList.add(stepsProperties.getCreateSeries());
		stepPropertiesList.add(stepsProperties.getCreatePerformers());
		stepPropertiesList.add(stepsProperties.getCreateStaff());
		stepPropertiesList.add(stepsProperties.getCreateCharacters());
		stepPropertiesList.add(stepsProperties.getCreateEpisodes());
		stepPropertiesList = stepPropertiesList.stream()
				.filter(Objects::nonNull)
				.collect(Collectors.toList());
	}

	private void addAllToMap() {
		stepPropertiesMap = Maps.newHashMap();
		stepPropertiesMap.put(StepName.CREATE_SERIES, stepsProperties.getCreateSeries());
		stepPropertiesMap.put(StepName.CREATE_PERFORMERS, stepsProperties.getCreatePerformers());
		stepPropertiesMap.put(StepName.CREATE_STAFF, stepsProperties.getCreateStaff());
		stepPropertiesMap.put(StepName.CREATE_CHARACTERS, stepsProperties.getCreateCharacters());
		stepPropertiesMap.put(StepName.CREATE_EPISODES, stepsProperties.getCreateEpisodes());
	}

	private void validateNumberOfSteps() {
		long stepCount = stepPropertiesList.stream()
				.filter(Objects::nonNull)
				.count();

		if (stepCount != NUMBER_OF_STEPS) {
			doThrow(String.format("Number of configured steps is %s, but %s steps found", NUMBER_OF_STEPS, stepCount));
		}
	}

	private void validateOrder() {
		Map<String, Integer> stepOrder = Maps.newHashMap();
		Set<Map.Entry<String, StepProperties>> stepEntrySet = stepPropertiesMap.entrySet();

		stepEntrySet.forEach(stepPropertiesEntry ->
			stepOrder.put(stepPropertiesEntry.getKey(), stepPropertiesEntry.getValue().getOrder()));

		stepEntrySet.forEach(stepPropertiesEntry -> {
			String rightStepName = stepPropertiesEntry.getKey();
			Integer rightStepOrder = stepPropertiesEntry.getValue().getOrder();
			stepOrder.entrySet().forEach(stringIntegerEntry -> {
				String leftStepName = stringIntegerEntry.getKey();
				Integer leftStepOrder = stringIntegerEntry.getValue();
				if (!rightStepName.equals(leftStepName) && rightStepOrder.equals(leftStepOrder)) {
					doThrow(String.format("Step %s has order %s, but this order was already given to step %s",
							rightStepName, rightStepOrder, leftStepName));
				}
			});
		});
	}

	private void doThrow(String message) {
		throw new JobBuilderException(new RuntimeException(message));
	}


}
