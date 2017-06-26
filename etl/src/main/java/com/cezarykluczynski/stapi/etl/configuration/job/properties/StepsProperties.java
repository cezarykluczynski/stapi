package com.cezarykluczynski.stapi.etl.configuration.job.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = StepsProperties.PREFIX)
public class StepsProperties {

	public static final String PREFIX = "step";

	private StepProperties createCompanies;

	private StepProperties createSeries;

	private StepProperties createSeasons;

	private StepProperties createPerformers;

	private StepProperties createStaff;

	private StepProperties createAstronomicalObjects;

	private StepProperties createSpecies;

	private StepProperties createCharacters;

	private StepProperties createEpisodes;

	private StepProperties createMovies;

	private StepProperties linkAstronomicalObjects;

	private StepProperties createComicSeries;

	private StepProperties linkComicSeries;

	private StepProperties createComics;

	private StepProperties createComicStrips;

	private StepProperties createComicCollections;

	private StepProperties createOrganizations;

	private StepProperties createFoods;

	private StepProperties createLocations;

	private StepProperties createBookSeries;

	private StepProperties linkBookSeries;

	private StepProperties createBooks;

	private StepProperties createBookCollections;

	private StepProperties createMagazines;

	private StepProperties createMagazineSeries;

	private StepProperties createLiterature;

	private StepProperties createVideoReleases;

}
