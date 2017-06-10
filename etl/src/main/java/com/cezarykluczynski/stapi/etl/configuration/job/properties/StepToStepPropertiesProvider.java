package com.cezarykluczynski.stapi.etl.configuration.job.properties;

import com.cezarykluczynski.stapi.etl.util.constant.StepName;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Map;

@Service
public class StepToStepPropertiesProvider {

	private Map<String, StepProperties> stepPropertiesMap;

	private StepsProperties stepsProperties;

	@Inject
	public StepToStepPropertiesProvider(StepsProperties stepsProperties) {
		this.stepsProperties = stepsProperties;
		addAllToMap();
	}

	public synchronized Map<String, StepProperties> provide() {
		return stepPropertiesMap;
	}

	private void addAllToMap() {
		stepPropertiesMap = Maps.newHashMap();
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
		stepPropertiesMap.put(StepName.CREATE_COMIC_COLLECTIONS, stepsProperties.getCreateComicCollections());
		stepPropertiesMap.put(StepName.CREATE_ORGANIZATIONS, stepsProperties.getCreateOrganizations());
		stepPropertiesMap.put(StepName.CREATE_FOODS, stepsProperties.getCreateFoods());
		stepPropertiesMap.put(StepName.CREATE_LOCATIONS, stepsProperties.getCreateLocations());
		stepPropertiesMap.put(StepName.CREATE_BOOK_SERIES, stepsProperties.getCreateBookSeries());
		stepPropertiesMap.put(StepName.LINK_BOOK_SERIES, stepsProperties.getLinkBookSeries());
		stepPropertiesMap.put(StepName.CREATE_BOOKS, stepsProperties.getCreateBooks());
		stepPropertiesMap.put(StepName.CREATE_BOOK_COLLECTIONS, stepsProperties.getCreateBookCollections());
		stepPropertiesMap.put(StepName.CREATE_MAGAZINE_SERIES, stepsProperties.getCreateMagazineSeries());
		stepPropertiesMap.put(StepName.CREATE_MAGAZINES, stepsProperties.getCreateMagazines());
	}

}
