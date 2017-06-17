package com.cezarykluczynski.stapi.etl.configuration.job;

import com.cezarykluczynski.stapi.etl.configuration.job.properties.StepProperties;
import com.cezarykluczynski.stapi.etl.configuration.job.properties.StepsProperties;
import com.cezarykluczynski.stapi.etl.util.constant.StepName;
import com.cezarykluczynski.stapi.util.exception.StapiRuntimeException;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.batch.core.job.builder.JobBuilderException;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class StepConfigurationValidator {

	private static final long NUMBER_OF_STEPS = 25;

	private final StepsProperties stepsProperties;

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
		stepPropertiesList.add(stepsProperties.getCreateCompanies());
		stepPropertiesList.add(stepsProperties.getCreateSeries());
		stepPropertiesList.add(stepsProperties.getCreatePerformers());
		stepPropertiesList.add(stepsProperties.getCreateStaff());
		stepPropertiesList.add(stepsProperties.getCreateAstronomicalObjects());
		stepPropertiesList.add(stepsProperties.getCreateSpecies());
		stepPropertiesList.add(stepsProperties.getCreateCharacters());
		stepPropertiesList.add(stepsProperties.getCreateEpisodes());
		stepPropertiesList.add(stepsProperties.getCreateMovies());
		stepPropertiesList.add(stepsProperties.getLinkAstronomicalObjects());
		stepPropertiesList.add(stepsProperties.getCreateComicSeries());
		stepPropertiesList.add(stepsProperties.getLinkComicSeries());
		stepPropertiesList.add(stepsProperties.getCreateComics());
		stepPropertiesList.add(stepsProperties.getCreateComicStrips());
		stepPropertiesList.add(stepsProperties.getCreateComicCollections());
		stepPropertiesList.add(stepsProperties.getCreateOrganizations());
		stepPropertiesList.add(stepsProperties.getCreateFoods());
		stepPropertiesList.add(stepsProperties.getCreateLocations());
		stepPropertiesList.add(stepsProperties.getCreateBookSeries());
		stepPropertiesList.add(stepsProperties.getLinkBookSeries());
		stepPropertiesList.add(stepsProperties.getCreateBooks());
		stepPropertiesList.add(stepsProperties.getCreateBookCollections());
		stepPropertiesList.add(stepsProperties.getCreateMagazines());
		stepPropertiesList.add(stepsProperties.getCreateMagazineSeries());
		stepPropertiesList.add(stepsProperties.getCreateLiterature());
		stepPropertiesList = stepPropertiesList.stream()
				.filter(Objects::nonNull)
				.collect(Collectors.toList());
	}

	private void addAllToMap() {
		stepPropertiesMap = Maps.newLinkedHashMap();
		stepPropertiesMap.put(StepName.CREATE_COMPANIES, stepsProperties.getCreateCompanies());
		stepPropertiesMap.put(StepName.CREATE_SERIES, stepsProperties.getCreateSeries());
		stepPropertiesMap.put(StepName.CREATE_PERFORMERS, stepsProperties.getCreatePerformers());
		stepPropertiesMap.put(StepName.CREATE_STAFF, stepsProperties.getCreateStaff());
		stepPropertiesMap.put(StepName.CREATE_ASTRONOMICAL_OBJECTS, stepsProperties.getCreateAstronomicalObjects());
		stepPropertiesMap.put(StepName.CREATE_SPECIES, stepsProperties.getCreateSpecies());
		stepPropertiesMap.put(StepName.CREATE_CHARACTERS, stepsProperties.getCreateCharacters());
		stepPropertiesMap.put(StepName.CREATE_EPISODES, stepsProperties.getCreateEpisodes());
		stepPropertiesMap.put(StepName.CREATE_MOVIES, stepsProperties.getCreateMovies());
		stepPropertiesMap.put(StepName.LINK_ASTRONOMICAL_OBJECTS, stepsProperties.getLinkAstronomicalObjects());
		stepPropertiesMap.put(StepName.CREATE_COMIC_SERIES, stepsProperties.getCreateComicSeries());
		stepPropertiesMap.put(StepName.LINK_COMIC_SERIES, stepsProperties.getLinkComicSeries());
		stepPropertiesMap.put(StepName.CREATE_COMICS, stepsProperties.getCreateComics());
		stepPropertiesMap.put(StepName.CREATE_COMIC_STRIPS, stepsProperties.getCreateComicStrips());
		stepPropertiesMap.put(StepName.CREATE_ORGANIZATIONS, stepsProperties.getCreateOrganizations());
		stepPropertiesMap.put(StepName.CREATE_FOODS, stepsProperties.getCreateFoods());
		stepPropertiesMap.put(StepName.CREATE_LOCATIONS, stepsProperties.getCreateLocations());
		stepPropertiesMap.put(StepName.CREATE_BOOK_SERIES, stepsProperties.getCreateBookSeries());
		stepPropertiesMap.put(StepName.LINK_BOOK_SERIES, stepsProperties.getLinkBookSeries());
		stepPropertiesMap.put(StepName.CREATE_BOOKS, stepsProperties.getCreateBooks());
		stepPropertiesMap.put(StepName.CREATE_BOOK_COLLECTIONS, stepsProperties.getCreateBookCollections());
		stepPropertiesMap.put(StepName.CREATE_MAGAZINE_SERIES, stepsProperties.getCreateMagazineSeries());
		stepPropertiesMap.put(StepName.CREATE_MAGAZINES, stepsProperties.getCreateMagazines());
		stepPropertiesMap.put(StepName.CREATE_LITERATURE, stepsProperties.getCreateLiterature());
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
