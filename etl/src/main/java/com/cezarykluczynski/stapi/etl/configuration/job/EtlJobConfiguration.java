package com.cezarykluczynski.stapi.etl.configuration.job;

import com.cezarykluczynski.stapi.etl.animal.creation.processor.AnimalProcessor;
import com.cezarykluczynski.stapi.etl.animal.creation.processor.AnimalReader;
import com.cezarykluczynski.stapi.etl.animal.creation.processor.AnimalWriter;
import com.cezarykluczynski.stapi.etl.astronomical_object.creation.processor.AstronomicalObjectProcessor;
import com.cezarykluczynski.stapi.etl.astronomical_object.creation.processor.AstronomicalObjectReader;
import com.cezarykluczynski.stapi.etl.astronomical_object.creation.processor.AstronomicalObjectWriter;
import com.cezarykluczynski.stapi.etl.astronomical_object.link.processor.AstronomicalObjectLinkProcessor;
import com.cezarykluczynski.stapi.etl.astronomical_object.link.processor.AstronomicalObjectLinkReader;
import com.cezarykluczynski.stapi.etl.book.creation.processor.BookProcessor;
import com.cezarykluczynski.stapi.etl.book.creation.processor.BookReader;
import com.cezarykluczynski.stapi.etl.book.creation.processor.BookWriter;
import com.cezarykluczynski.stapi.etl.book_collection.creation.processor.BookCollectionProcessor;
import com.cezarykluczynski.stapi.etl.book_collection.creation.processor.BookCollectionReader;
import com.cezarykluczynski.stapi.etl.book_collection.creation.processor.BookCollectionWriter;
import com.cezarykluczynski.stapi.etl.book_series.creation.processor.BookSeriesProcessor;
import com.cezarykluczynski.stapi.etl.book_series.creation.processor.BookSeriesReader;
import com.cezarykluczynski.stapi.etl.book_series.creation.processor.BookSeriesWriter;
import com.cezarykluczynski.stapi.etl.book_series.link.processor.BookSeriesLinkProcessor;
import com.cezarykluczynski.stapi.etl.book_series.link.processor.BookSeriesLinkReader;
import com.cezarykluczynski.stapi.etl.character.creation.processor.CharacterProcessor;
import com.cezarykluczynski.stapi.etl.character.creation.processor.CharacterReader;
import com.cezarykluczynski.stapi.etl.character.creation.processor.CharacterWriter;
import com.cezarykluczynski.stapi.etl.character.link.processor.CharacterLinkProcessor;
import com.cezarykluczynski.stapi.etl.character.link.processor.CharacterLinkReader;
import com.cezarykluczynski.stapi.etl.character.link.processor.CharacterLinkWriter;
import com.cezarykluczynski.stapi.etl.comic_collection.creation.processor.ComicCollectionProcessor;
import com.cezarykluczynski.stapi.etl.comic_collection.creation.processor.ComicCollectionReader;
import com.cezarykluczynski.stapi.etl.comic_collection.creation.processor.ComicCollectionWriter;
import com.cezarykluczynski.stapi.etl.comic_series.creation.processor.ComicSeriesProcessor;
import com.cezarykluczynski.stapi.etl.comic_series.creation.processor.ComicSeriesReader;
import com.cezarykluczynski.stapi.etl.comic_series.creation.processor.ComicSeriesWriter;
import com.cezarykluczynski.stapi.etl.comic_series.link.processor.ComicSeriesLinkProcessor;
import com.cezarykluczynski.stapi.etl.comic_series.link.processor.ComicSeriesLinkReader;
import com.cezarykluczynski.stapi.etl.comic_strip.creation.processor.ComicStripProcessor;
import com.cezarykluczynski.stapi.etl.comic_strip.creation.processor.ComicStripReader;
import com.cezarykluczynski.stapi.etl.comic_strip.creation.processor.ComicStripWriter;
import com.cezarykluczynski.stapi.etl.comics.creation.processor.ComicsProcessor;
import com.cezarykluczynski.stapi.etl.comics.creation.processor.ComicsReader;
import com.cezarykluczynski.stapi.etl.comics.creation.processor.ComicsWriter;
import com.cezarykluczynski.stapi.etl.common.listener.CommonStepExecutionListener;
import com.cezarykluczynski.stapi.etl.company.creation.processor.CompanyProcessor;
import com.cezarykluczynski.stapi.etl.company.creation.processor.CompanyReader;
import com.cezarykluczynski.stapi.etl.company.creation.processor.CompanyWriter;
import com.cezarykluczynski.stapi.etl.configuration.job.properties.StepsProperties;
import com.cezarykluczynski.stapi.etl.conflict.creation.processor.ConflictProcessor;
import com.cezarykluczynski.stapi.etl.conflict.creation.processor.ConflictReader;
import com.cezarykluczynski.stapi.etl.conflict.creation.processor.ConflictWriter;
import com.cezarykluczynski.stapi.etl.element.creation.processor.ElementProcessor;
import com.cezarykluczynski.stapi.etl.element.creation.processor.ElementReader;
import com.cezarykluczynski.stapi.etl.element.creation.processor.ElementWriter;
import com.cezarykluczynski.stapi.etl.episode.creation.processor.EpisodeProcessor;
import com.cezarykluczynski.stapi.etl.episode.creation.processor.EpisodeReader;
import com.cezarykluczynski.stapi.etl.episode.creation.processor.EpisodeWriter;
import com.cezarykluczynski.stapi.etl.food.creation.processor.FoodProcessor;
import com.cezarykluczynski.stapi.etl.food.creation.processor.FoodReader;
import com.cezarykluczynski.stapi.etl.food.creation.processor.FoodWriter;
import com.cezarykluczynski.stapi.etl.literature.creation.processor.LiteratureProcessor;
import com.cezarykluczynski.stapi.etl.literature.creation.processor.LiteratureReader;
import com.cezarykluczynski.stapi.etl.literature.creation.processor.LiteratureWriter;
import com.cezarykluczynski.stapi.etl.location.creation.processor.LocationProcessor;
import com.cezarykluczynski.stapi.etl.location.creation.processor.LocationReader;
import com.cezarykluczynski.stapi.etl.location.creation.processor.LocationWriter;
import com.cezarykluczynski.stapi.etl.magazine.creation.processor.MagazineProcessor;
import com.cezarykluczynski.stapi.etl.magazine.creation.processor.MagazineReader;
import com.cezarykluczynski.stapi.etl.magazine.creation.processor.MagazineWriter;
import com.cezarykluczynski.stapi.etl.magazine_series.creation.processor.MagazineSeriesProcessor;
import com.cezarykluczynski.stapi.etl.magazine_series.creation.processor.MagazineSeriesReader;
import com.cezarykluczynski.stapi.etl.magazine_series.creation.processor.MagazineSeriesWriter;
import com.cezarykluczynski.stapi.etl.material.creation.processor.MaterialProcessor;
import com.cezarykluczynski.stapi.etl.material.creation.processor.MaterialReader;
import com.cezarykluczynski.stapi.etl.material.creation.processor.MaterialWriter;
import com.cezarykluczynski.stapi.etl.medical_condition.creation.processor.MedicalConditionProcessor;
import com.cezarykluczynski.stapi.etl.medical_condition.creation.processor.MedicalConditionReader;
import com.cezarykluczynski.stapi.etl.medical_condition.creation.processor.MedicalConditionWriter;
import com.cezarykluczynski.stapi.etl.movie.creation.processor.MovieProcessor;
import com.cezarykluczynski.stapi.etl.movie.creation.processor.MovieReader;
import com.cezarykluczynski.stapi.etl.movie.creation.processor.MovieWriter;
import com.cezarykluczynski.stapi.etl.occupation.creation.processor.OccupationProcessor;
import com.cezarykluczynski.stapi.etl.occupation.creation.processor.OccupationReader;
import com.cezarykluczynski.stapi.etl.occupation.creation.processor.OccupationWriter;
import com.cezarykluczynski.stapi.etl.organization.creation.processor.OrganizationProcessor;
import com.cezarykluczynski.stapi.etl.organization.creation.processor.OrganizationReader;
import com.cezarykluczynski.stapi.etl.organization.creation.processor.OrganizationWriter;
import com.cezarykluczynski.stapi.etl.performer.creation.processor.PerformerProcessor;
import com.cezarykluczynski.stapi.etl.performer.creation.processor.PerformerReader;
import com.cezarykluczynski.stapi.etl.performer.creation.processor.PerformerWriter;
import com.cezarykluczynski.stapi.etl.season.creation.processor.SeasonProcessor;
import com.cezarykluczynski.stapi.etl.season.creation.processor.SeasonReader;
import com.cezarykluczynski.stapi.etl.season.creation.processor.SeasonWriter;
import com.cezarykluczynski.stapi.etl.series.creation.processor.SeriesProcessor;
import com.cezarykluczynski.stapi.etl.series.creation.processor.SeriesReader;
import com.cezarykluczynski.stapi.etl.series.creation.processor.SeriesWriter;
import com.cezarykluczynski.stapi.etl.soundtrack.creation.processor.SoundtrackProcessor;
import com.cezarykluczynski.stapi.etl.soundtrack.creation.processor.SoundtrackReader;
import com.cezarykluczynski.stapi.etl.soundtrack.creation.processor.SoundtrackWriter;
import com.cezarykluczynski.stapi.etl.spacecraft.creation.processor.SpacecraftProcessor;
import com.cezarykluczynski.stapi.etl.spacecraft.creation.processor.SpacecraftReader;
import com.cezarykluczynski.stapi.etl.spacecraft.creation.processor.SpacecraftWriter;
import com.cezarykluczynski.stapi.etl.spacecraft_class.creation.processor.SpacecraftClassProcessor;
import com.cezarykluczynski.stapi.etl.spacecraft_class.creation.processor.SpacecraftClassReader;
import com.cezarykluczynski.stapi.etl.spacecraft_class.creation.processor.SpacecraftClassWriter;
import com.cezarykluczynski.stapi.etl.spacecraft_type.creation.processor.SpacecraftTypeProcessor;
import com.cezarykluczynski.stapi.etl.spacecraft_type.creation.processor.SpacecraftTypeReader;
import com.cezarykluczynski.stapi.etl.spacecraft_type.creation.processor.SpacecraftTypeWriter;
import com.cezarykluczynski.stapi.etl.species.creation.processor.SpeciesProcessor;
import com.cezarykluczynski.stapi.etl.species.creation.processor.SpeciesReader;
import com.cezarykluczynski.stapi.etl.species.creation.processor.SpeciesWriter;
import com.cezarykluczynski.stapi.etl.staff.creation.processor.StaffProcessor;
import com.cezarykluczynski.stapi.etl.staff.creation.processor.StaffReader;
import com.cezarykluczynski.stapi.etl.staff.creation.processor.StaffWriter;
import com.cezarykluczynski.stapi.etl.technology.creation.processor.TechnologyProcessor;
import com.cezarykluczynski.stapi.etl.technology.creation.processor.TechnologyReader;
import com.cezarykluczynski.stapi.etl.technology.creation.processor.TechnologyWriter;
import com.cezarykluczynski.stapi.etl.title.creation.processor.TitleProcessor;
import com.cezarykluczynski.stapi.etl.title.creation.processor.TitleReader;
import com.cezarykluczynski.stapi.etl.title.creation.processor.TitleWriter;
import com.cezarykluczynski.stapi.etl.title.creation.service.TitleListStepExecutionListener;
import com.cezarykluczynski.stapi.etl.trading_card.creation.processor.TradingCardSetReader;
import com.cezarykluczynski.stapi.etl.trading_card.creation.processor.TradingCardSetWriter;
import com.cezarykluczynski.stapi.etl.trading_card.creation.processor.set.TradingCardSetProcessor;
import com.cezarykluczynski.stapi.etl.util.constant.StepName;
import com.cezarykluczynski.stapi.etl.video_game.creation.processor.VideoGameProcessor;
import com.cezarykluczynski.stapi.etl.video_game.creation.processor.VideoGameReader;
import com.cezarykluczynski.stapi.etl.video_game.creation.processor.VideoGameWriter;
import com.cezarykluczynski.stapi.etl.video_release.creation.processor.VideoReleaseProcessor;
import com.cezarykluczynski.stapi.etl.video_release.creation.processor.VideoReleaseReader;
import com.cezarykluczynski.stapi.etl.video_release.creation.processor.VideoReleaseWriter;
import com.cezarykluczynski.stapi.etl.weapon.creation.processor.WeaponProcessor;
import com.cezarykluczynski.stapi.etl.weapon.creation.processor.WeaponReader;
import com.cezarykluczynski.stapi.etl.weapon.creation.processor.WeaponWriter;
import com.cezarykluczynski.stapi.model.animal.entity.Animal;
import com.cezarykluczynski.stapi.model.astronomical_object.entity.AstronomicalObject;
import com.cezarykluczynski.stapi.model.book.entity.Book;
import com.cezarykluczynski.stapi.model.book_collection.entity.BookCollection;
import com.cezarykluczynski.stapi.model.book_series.entity.BookSeries;
import com.cezarykluczynski.stapi.model.character.entity.Character;
import com.cezarykluczynski.stapi.model.comic_collection.entity.ComicCollection;
import com.cezarykluczynski.stapi.model.comic_series.entity.ComicSeries;
import com.cezarykluczynski.stapi.model.comic_strip.entity.ComicStrip;
import com.cezarykluczynski.stapi.model.comics.entity.Comics;
import com.cezarykluczynski.stapi.model.company.entity.Company;
import com.cezarykluczynski.stapi.model.conflict.entity.Conflict;
import com.cezarykluczynski.stapi.model.element.entity.Element;
import com.cezarykluczynski.stapi.model.episode.entity.Episode;
import com.cezarykluczynski.stapi.model.food.entity.Food;
import com.cezarykluczynski.stapi.model.literature.entity.Literature;
import com.cezarykluczynski.stapi.model.location.entity.Location;
import com.cezarykluczynski.stapi.model.magazine.entity.Magazine;
import com.cezarykluczynski.stapi.model.magazine_series.entity.MagazineSeries;
import com.cezarykluczynski.stapi.model.material.entity.Material;
import com.cezarykluczynski.stapi.model.medical_condition.entity.MedicalCondition;
import com.cezarykluczynski.stapi.model.movie.entity.Movie;
import com.cezarykluczynski.stapi.model.occupation.entity.Occupation;
import com.cezarykluczynski.stapi.model.organization.entity.Organization;
import com.cezarykluczynski.stapi.model.performer.entity.Performer;
import com.cezarykluczynski.stapi.model.season.entity.Season;
import com.cezarykluczynski.stapi.model.series.entity.Series;
import com.cezarykluczynski.stapi.model.soundtrack.entity.Soundtrack;
import com.cezarykluczynski.stapi.model.spacecraft.entity.Spacecraft;
import com.cezarykluczynski.stapi.model.spacecraft_class.entity.SpacecraftClass;
import com.cezarykluczynski.stapi.model.spacecraft_type.entity.SpacecraftType;
import com.cezarykluczynski.stapi.model.species.entity.Species;
import com.cezarykluczynski.stapi.model.staff.entity.Staff;
import com.cezarykluczynski.stapi.model.technology.entity.Technology;
import com.cezarykluczynski.stapi.model.title.entity.Title;
import com.cezarykluczynski.stapi.model.trading_card_set.entity.TradingCardSet;
import com.cezarykluczynski.stapi.model.video_game.entity.VideoGame;
import com.cezarykluczynski.stapi.model.video_release.entity.VideoRelease;
import com.cezarykluczynski.stapi.model.weapon.entity.Weapon;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader;
import com.cezarykluczynski.stapi.sources.wordpress.dto.Page;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.inject.Inject;

@Configuration
@EnableConfigurationProperties(StepsProperties.class)
@SuppressWarnings("MethodCount")
public class EtlJobConfiguration {

	@Inject
	private ApplicationContext applicationContext;

	@Inject
	private JobBuilder jobBuilder;

	@Inject
	private StepBuilderFactory stepBuilderFactory;

	@Inject
	private StepsProperties stepsProperties;

	@Bean
	public FactoryBean<Job> jobCreate() {
		return new JobFactoryBean(jobBuilder.build());
	}

	@Bean(name = StepName.CREATE_COMPANIES)
	public Step stepCreateCompanies() {
		return stepBuilderFactory.get(StepName.CREATE_COMPANIES)
				.<PageHeader, Company>chunk(stepsProperties.getCreateCompanies().getCommitInterval())
				.reader(applicationContext.getBean(CompanyReader.class))
				.processor(applicationContext.getBean(CompanyProcessor.class))
				.writer(applicationContext.getBean(CompanyWriter.class))
				.listener(applicationContext.getBean(CommonStepExecutionListener.class))
				.startLimit(1)
				.allowStartIfComplete(false)
				.build();
	}

	@Bean(name = StepName.CREATE_SERIES)
	public Step stepCreateSeries() {
		return stepBuilderFactory.get(StepName.CREATE_SERIES)
				.<PageHeader, Series>chunk(stepsProperties.getCreateSeries().getCommitInterval())
				.reader(applicationContext.getBean(SeriesReader.class))
				.processor(applicationContext.getBean(SeriesProcessor.class))
				.writer(applicationContext.getBean(SeriesWriter.class))
				.listener(applicationContext.getBean(CommonStepExecutionListener.class))
				.startLimit(1)
				.allowStartIfComplete(false)
				.build();
	}

	@Bean(name = StepName.CREATE_SEASONS)
	public Step stepCreateSeasons() {
		return stepBuilderFactory.get(StepName.CREATE_SEASONS)
				.<PageHeader, Season>chunk(stepsProperties.getCreateSeasons().getCommitInterval())
				.reader(applicationContext.getBean(SeasonReader.class))
				.processor(applicationContext.getBean(SeasonProcessor.class))
				.writer(applicationContext.getBean(SeasonWriter.class))
				.listener(applicationContext.getBean(CommonStepExecutionListener.class))
				.startLimit(1)
				.allowStartIfComplete(false)
				.build();
	}

	@Bean(name = StepName.CREATE_PERFORMERS)
	public Step stepCreatePerformers() {
		return stepBuilderFactory.get(StepName.CREATE_PERFORMERS)
				.<PageHeader, Performer>chunk(stepsProperties.getCreatePerformers().getCommitInterval())
				.reader(applicationContext.getBean(PerformerReader.class))
				.processor(applicationContext.getBean(PerformerProcessor.class))
				.writer(applicationContext.getBean(PerformerWriter.class))
				.listener(applicationContext.getBean(CommonStepExecutionListener.class))
				.startLimit(1)
				.allowStartIfComplete(false)
				.build();
	}

	@Bean(name = StepName.CREATE_STAFF)
	public Step stepCreateStaff() {
		return stepBuilderFactory.get(StepName.CREATE_STAFF)
				.<PageHeader, Staff>chunk(stepsProperties.getCreateStaff().getCommitInterval())
				.reader(applicationContext.getBean(StaffReader.class))
				.processor(applicationContext.getBean(StaffProcessor.class))
				.writer(applicationContext.getBean(StaffWriter.class))
				.listener(applicationContext.getBean(CommonStepExecutionListener.class))
				.startLimit(1)
				.allowStartIfComplete(false)
				.build();
	}

	@Bean(name = StepName.CREATE_ASTRONOMICAL_OBJECTS)
	public Step stepCreateAstronomicalObject() {
		return stepBuilderFactory.get(StepName.CREATE_ASTRONOMICAL_OBJECTS)
				.<PageHeader, AstronomicalObject>chunk(stepsProperties.getCreateAstronomicalObjects().getCommitInterval())
				.reader(applicationContext.getBean(AstronomicalObjectReader.class))
				.processor(applicationContext.getBean(AstronomicalObjectProcessor.class))
				.writer(applicationContext.getBean(AstronomicalObjectWriter.class))
				.listener(applicationContext.getBean(CommonStepExecutionListener.class))
				.startLimit(1)
				.allowStartIfComplete(false)
				.build();
	}

	@Bean(name = StepName.CREATE_SPECIES)
	public Step stepCreateSpecies() {
		return stepBuilderFactory.get(StepName.CREATE_SPECIES)
				.<PageHeader, Species>chunk(stepsProperties.getCreateSpecies().getCommitInterval())
				.reader(applicationContext.getBean(SpeciesReader.class))
				.processor(applicationContext.getBean(SpeciesProcessor.class))
				.writer(applicationContext.getBean(SpeciesWriter.class))
				.listener(applicationContext.getBean(CommonStepExecutionListener.class))
				.startLimit(1)
				.allowStartIfComplete(false)
				.build();
	}


	@Bean(name = StepName.CREATE_ORGANIZATIONS)
	public Step stepCreateOrganizations() {
		return stepBuilderFactory.get(StepName.CREATE_ORGANIZATIONS)
				.<PageHeader, Organization>chunk(stepsProperties.getCreateOrganizations().getCommitInterval())
				.reader(applicationContext.getBean(OrganizationReader.class))
				.processor(applicationContext.getBean(OrganizationProcessor.class))
				.writer(applicationContext.getBean(OrganizationWriter.class))
				.listener(applicationContext.getBean(CommonStepExecutionListener.class))
				.startLimit(1)
				.allowStartIfComplete(false)
				.build();
	}

	@Bean(name = StepName.CREATE_TITLES)
	public Step stepCreateTitles() {
		return stepBuilderFactory.get(StepName.CREATE_TITLES)
				.<PageHeader, Title>chunk(stepsProperties.getCreateTitles().getCommitInterval())
				.reader(applicationContext.getBean(TitleReader.class))
				.processor(applicationContext.getBean(TitleProcessor.class))
				.writer(applicationContext.getBean(TitleWriter.class))
				.listener(applicationContext.getBean(CommonStepExecutionListener.class))
				.listener(applicationContext.getBean(TitleListStepExecutionListener.class))
				.startLimit(1)
				.allowStartIfComplete(false)
				.build();
	}

	@Bean(name = StepName.CREATE_OCCUPATIONS)
	public Step stepCreateOccupations() {
		return stepBuilderFactory.get(StepName.CREATE_OCCUPATIONS)
				.<PageHeader, Occupation>chunk(stepsProperties.getCreateOccupations().getCommitInterval())
				.reader(applicationContext.getBean(OccupationReader.class))
				.processor(applicationContext.getBean(OccupationProcessor.class))
				.writer(applicationContext.getBean(OccupationWriter.class))
				.listener(applicationContext.getBean(CommonStepExecutionListener.class))
				.startLimit(1)
				.allowStartIfComplete(false)
				.build();
	}

	@Bean(name = StepName.CREATE_CHARACTERS)
	public Step stepCreateCharacters() {
		return stepBuilderFactory.get(StepName.CREATE_CHARACTERS)
				.<PageHeader, Character>chunk(stepsProperties.getCreateCharacters().getCommitInterval())
				.reader(applicationContext.getBean(CharacterReader.class))
				.processor(applicationContext.getBean(CharacterProcessor.class))
				.writer(applicationContext.getBean(CharacterWriter.class))
				.listener(applicationContext.getBean(CommonStepExecutionListener.class))
				.startLimit(1)
				.allowStartIfComplete(false)
				.build();
	}

	@Bean(name = StepName.LINK_CHARACTERS)
	public Step stepLinkCharacters() {
		return stepBuilderFactory.get(StepName.LINK_CHARACTERS)
				.<PageHeader, Character>chunk(stepsProperties.getLinkCharacters().getCommitInterval())
				.reader(applicationContext.getBean(CharacterLinkReader.class))
				.processor(applicationContext.getBean(CharacterLinkProcessor.class))
				.writer(applicationContext.getBean(CharacterLinkWriter.class))
				.listener(applicationContext.getBean(CommonStepExecutionListener.class))
				.startLimit(1)
				.allowStartIfComplete(false)
				.build();
	}

	@Bean(name = StepName.CREATE_EPISODES)
	public Step stepCreateEpisodes() {
		return stepBuilderFactory.get(StepName.CREATE_EPISODES)
				.<PageHeader, Episode>chunk(stepsProperties.getCreateEpisodes().getCommitInterval())
				.reader(applicationContext.getBean(EpisodeReader.class))
				.processor(applicationContext.getBean(EpisodeProcessor.class))
				.writer(applicationContext.getBean(EpisodeWriter.class))
				.listener(applicationContext.getBean(CommonStepExecutionListener.class))
				.startLimit(1)
				.allowStartIfComplete(false)
				.build();
	}

	@Bean(name = StepName.CREATE_MOVIES)
	public Step stepCreateMovies() {
		return stepBuilderFactory.get(StepName.CREATE_MOVIES)
				.<PageHeader, Movie>chunk(stepsProperties.getCreateMovies().getCommitInterval())
				.reader(applicationContext.getBean(MovieReader.class))
				.processor(applicationContext.getBean(MovieProcessor.class))
				.writer(applicationContext.getBean(MovieWriter.class))
				.listener(applicationContext.getBean(CommonStepExecutionListener.class))
				.startLimit(1)
				.allowStartIfComplete(false)
				.build();
	}

	@Bean(name = StepName.LINK_ASTRONOMICAL_OBJECTS)
	public Step stepLinkAstronomicalObject() {
		return stepBuilderFactory.get(StepName.LINK_ASTRONOMICAL_OBJECTS)
				.<AstronomicalObject, AstronomicalObject>chunk(stepsProperties.getLinkAstronomicalObjects().getCommitInterval())
				.reader(applicationContext.getBean(AstronomicalObjectLinkReader.class))
				.processor(applicationContext.getBean(AstronomicalObjectLinkProcessor.class))
				.writer(applicationContext.getBean(AstronomicalObjectWriter.class))
				.listener(applicationContext.getBean(CommonStepExecutionListener.class))
				.startLimit(1)
				.allowStartIfComplete(false)
				.build();
	}

	@Bean(name = StepName.CREATE_COMIC_SERIES)
	public Step stepCreateComicSeries() {
		return stepBuilderFactory.get(StepName.CREATE_COMIC_SERIES)
				.<PageHeader, ComicSeries>chunk(stepsProperties.getCreateComicSeries().getCommitInterval())
				.reader(applicationContext.getBean(ComicSeriesReader.class))
				.processor(applicationContext.getBean(ComicSeriesProcessor.class))
				.writer(applicationContext.getBean(ComicSeriesWriter.class))
				.listener(applicationContext.getBean(CommonStepExecutionListener.class))
				.startLimit(1)
				.allowStartIfComplete(false)
				.build();
	}

	@Bean(name = StepName.LINK_COMIC_SERIES)
	public Step stepLinkComicSeries() {
		return stepBuilderFactory.get(StepName.LINK_COMIC_SERIES)
				.<ComicSeries, ComicSeries>chunk(stepsProperties.getLinkComicSeries().getCommitInterval())
				.reader(applicationContext.getBean(ComicSeriesLinkReader.class))
				.processor(applicationContext.getBean(ComicSeriesLinkProcessor.class))
				.writer(applicationContext.getBean(ComicSeriesWriter.class))
				.listener(applicationContext.getBean(CommonStepExecutionListener.class))
				.startLimit(1)
				.allowStartIfComplete(false)
				.build();
	}

	@Bean(name = StepName.CREATE_COMICS)
	public Step stepCreateComics() {
		return stepBuilderFactory.get(StepName.CREATE_COMICS)
				.<PageHeader, Comics>chunk(stepsProperties.getCreateComics().getCommitInterval())
				.reader(applicationContext.getBean(ComicsReader.class))
				.processor(applicationContext.getBean(ComicsProcessor.class))
				.writer(applicationContext.getBean(ComicsWriter.class))
				.listener(applicationContext.getBean(CommonStepExecutionListener.class))
				.startLimit(1)
				.allowStartIfComplete(false)
				.build();
	}

	@Bean(name = StepName.CREATE_COMIC_STRIPS)
	public Step stepCreateComicStrips() {
		return stepBuilderFactory.get(StepName.CREATE_COMIC_STRIPS)
				.<PageHeader, ComicStrip>chunk(stepsProperties.getCreateComicStrips().getCommitInterval())
				.reader(applicationContext.getBean(ComicStripReader.class))
				.processor(applicationContext.getBean(ComicStripProcessor.class))
				.writer(applicationContext.getBean(ComicStripWriter.class))
				.listener(applicationContext.getBean(CommonStepExecutionListener.class))
				.startLimit(1)
				.allowStartIfComplete(false)
				.build();
	}

	@Bean(name = StepName.CREATE_COMIC_COLLECTIONS)
	public Step stepCreateComicCollections() {
		return stepBuilderFactory.get(StepName.CREATE_COMIC_COLLECTIONS)
				.<PageHeader, ComicCollection>chunk(stepsProperties.getCreateComicCollections().getCommitInterval())
				.reader(applicationContext.getBean(ComicCollectionReader.class))
				.processor(applicationContext.getBean(ComicCollectionProcessor.class))
				.writer(applicationContext.getBean(ComicCollectionWriter.class))
				.listener(applicationContext.getBean(CommonStepExecutionListener.class))
				.startLimit(1)
				.allowStartIfComplete(false)
				.build();
	}

	@Bean(name = StepName.CREATE_FOODS)
	public Step stepCreateFoods() {
		return stepBuilderFactory.get(StepName.CREATE_FOODS)
				.<PageHeader, Food>chunk(stepsProperties.getCreateFoods().getCommitInterval())
				.reader(applicationContext.getBean(FoodReader.class))
				.processor(applicationContext.getBean(FoodProcessor.class))
				.writer(applicationContext.getBean(FoodWriter.class))
				.listener(applicationContext.getBean(CommonStepExecutionListener.class))
				.startLimit(1)
				.allowStartIfComplete(false)
				.build();
	}

	@Bean(name = StepName.CREATE_LOCATIONS)
	public Step stepCreateLocations() {
		return stepBuilderFactory.get(StepName.CREATE_LOCATIONS)
				.<PageHeader, Location>chunk(stepsProperties.getCreateLocations().getCommitInterval())
				.reader(applicationContext.getBean(LocationReader.class))
				.processor(applicationContext.getBean(LocationProcessor.class))
				.writer(applicationContext.getBean(LocationWriter.class))
				.listener(applicationContext.getBean(CommonStepExecutionListener.class))
				.startLimit(1)
				.allowStartIfComplete(false)
				.build();
	}

	@Bean(name = StepName.CREATE_BOOK_SERIES)
	public Step stepCreateBookSeries() {
		return stepBuilderFactory.get(StepName.CREATE_BOOK_SERIES)
				.<PageHeader, BookSeries>chunk(stepsProperties.getCreateBookSeries().getCommitInterval())
				.reader(applicationContext.getBean(BookSeriesReader.class))
				.processor(applicationContext.getBean(BookSeriesProcessor.class))
				.writer(applicationContext.getBean(BookSeriesWriter.class))
				.listener(applicationContext.getBean(CommonStepExecutionListener.class))
				.startLimit(1)
				.allowStartIfComplete(false)
				.build();
	}

	@Bean(name = StepName.LINK_BOOK_SERIES)
	public Step stepLinkBookSeries() {
		return stepBuilderFactory.get(StepName.LINK_BOOK_SERIES)
				.<BookSeries, BookSeries>chunk(stepsProperties.getLinkBookSeries().getCommitInterval())
				.reader(applicationContext.getBean(BookSeriesLinkReader.class))
				.processor(applicationContext.getBean(BookSeriesLinkProcessor.class))
				.writer(applicationContext.getBean(BookSeriesWriter.class))
				.listener(applicationContext.getBean(CommonStepExecutionListener.class))
				.startLimit(1)
				.allowStartIfComplete(false)
				.build();
	}

	@Bean(name = StepName.CREATE_BOOKS)
	public Step stepCreateBooks() {
		return stepBuilderFactory.get(StepName.CREATE_BOOKS)
				.<PageHeader, Book>chunk(stepsProperties.getCreateBooks().getCommitInterval())
				.reader(applicationContext.getBean(BookReader.class))
				.processor(applicationContext.getBean(BookProcessor.class))
				.writer(applicationContext.getBean(BookWriter.class))
				.listener(applicationContext.getBean(CommonStepExecutionListener.class))
				.startLimit(1)
				.allowStartIfComplete(false)
				.build();
	}

	@Bean(name = StepName.CREATE_BOOK_COLLECTIONS)
	public Step stepCreateBookCollections() {
		return stepBuilderFactory.get(StepName.CREATE_BOOK_COLLECTIONS)
				.<PageHeader, BookCollection>chunk(stepsProperties.getCreateBookCollections().getCommitInterval())
				.reader(applicationContext.getBean(BookCollectionReader.class))
				.processor(applicationContext.getBean(BookCollectionProcessor.class))
				.writer(applicationContext.getBean(BookCollectionWriter.class))
				.listener(applicationContext.getBean(CommonStepExecutionListener.class))
				.startLimit(1)
				.allowStartIfComplete(false)
				.build();
	}

	@Bean(name = StepName.CREATE_MAGAZINE_SERIES)
	public Step stepCreateMagazineSeries() {
		return stepBuilderFactory.get(StepName.CREATE_MAGAZINE_SERIES)
				.<PageHeader, MagazineSeries>chunk(stepsProperties.getCreateMagazineSeries().getCommitInterval())
				.reader(applicationContext.getBean(MagazineSeriesReader.class))
				.processor(applicationContext.getBean(MagazineSeriesProcessor.class))
				.writer(applicationContext.getBean(MagazineSeriesWriter.class))
				.listener(applicationContext.getBean(CommonStepExecutionListener.class))
				.startLimit(1)
				.allowStartIfComplete(false)
				.build();
	}

	@Bean(name = StepName.CREATE_MAGAZINES)
	public Step stepCreateMagazines() {
		return stepBuilderFactory.get(StepName.CREATE_MAGAZINES)
				.<PageHeader, Magazine>chunk(stepsProperties.getCreateMagazines().getCommitInterval())
				.reader(applicationContext.getBean(MagazineReader.class))
				.processor(applicationContext.getBean(MagazineProcessor.class))
				.writer(applicationContext.getBean(MagazineWriter.class))
				.listener(applicationContext.getBean(CommonStepExecutionListener.class))
				.startLimit(1)
				.allowStartIfComplete(false)
				.build();
	}

	@Bean(name = StepName.CREATE_LITERATURE)
	public Step stepCreateLiterature() {
		return stepBuilderFactory.get(StepName.CREATE_LITERATURE)
				.<PageHeader, Literature>chunk(stepsProperties.getCreateLiterature().getCommitInterval())
				.reader(applicationContext.getBean(LiteratureReader.class))
				.processor(applicationContext.getBean(LiteratureProcessor.class))
				.writer(applicationContext.getBean(LiteratureWriter.class))
				.listener(applicationContext.getBean(CommonStepExecutionListener.class))
				.startLimit(1)
				.allowStartIfComplete(false)
				.build();
	}

	@Bean(name = StepName.CREATE_VIDEO_RELEASES)
	public Step stepCreateVideoReleases() {
		return stepBuilderFactory.get(StepName.CREATE_VIDEO_RELEASES)
				.<PageHeader, VideoRelease>chunk(stepsProperties.getCreateVideoReleases().getCommitInterval())
				.reader(applicationContext.getBean(VideoReleaseReader.class))
				.processor(applicationContext.getBean(VideoReleaseProcessor.class))
				.writer(applicationContext.getBean(VideoReleaseWriter.class))
				.listener(applicationContext.getBean(CommonStepExecutionListener.class))
				.startLimit(1)
				.allowStartIfComplete(false)
				.build();
	}

	@Bean(name = StepName.CREATE_TRADING_CARDS)
	public Step stepCreateTradingCards() {
		return stepBuilderFactory.get(StepName.CREATE_TRADING_CARDS)
				.<Page, TradingCardSet>chunk(stepsProperties.getCreateTradingCards().getCommitInterval())
				.reader(applicationContext.getBean(TradingCardSetReader.class))
				.processor(applicationContext.getBean(TradingCardSetProcessor.class))
				.writer(applicationContext.getBean(TradingCardSetWriter.class))
				.listener(applicationContext.getBean(CommonStepExecutionListener.class))
				.startLimit(1)
				.allowStartIfComplete(false)
				.build();
	}

	@Bean(name = StepName.CREATE_VIDEO_GAMES)
	public Step stepCreateVideoGames() {
		return stepBuilderFactory.get(StepName.CREATE_VIDEO_GAMES)
				.<PageHeader, VideoGame>chunk(stepsProperties.getCreateVideoGames().getCommitInterval())
				.reader(applicationContext.getBean(VideoGameReader.class))
				.processor(applicationContext.getBean(VideoGameProcessor.class))
				.writer(applicationContext.getBean(VideoGameWriter.class))
				.listener(applicationContext.getBean(CommonStepExecutionListener.class))
				.startLimit(1)
				.allowStartIfComplete(false)
				.build();
	}

	@Bean(name = StepName.CREATE_SOUNDTRACKS)
	public Step stepCreateSoundtracks() {
		return stepBuilderFactory.get(StepName.CREATE_SOUNDTRACKS)
				.<PageHeader, Soundtrack>chunk(stepsProperties.getCreateSoundtracks().getCommitInterval())
				.reader(applicationContext.getBean(SoundtrackReader.class))
				.processor(applicationContext.getBean(SoundtrackProcessor.class))
				.writer(applicationContext.getBean(SoundtrackWriter.class))
				.listener(applicationContext.getBean(CommonStepExecutionListener.class))
				.startLimit(1)
				.allowStartIfComplete(false)
				.build();
	}

	@Bean(name = StepName.CREATE_WEAPONS)
	public Step stepCreateWeapons() {
		return stepBuilderFactory.get(StepName.CREATE_WEAPONS)
				.<PageHeader, Weapon>chunk(stepsProperties.getCreateWeapons().getCommitInterval())
				.reader(applicationContext.getBean(WeaponReader.class))
				.processor(applicationContext.getBean(WeaponProcessor.class))
				.writer(applicationContext.getBean(WeaponWriter.class))
				.listener(applicationContext.getBean(CommonStepExecutionListener.class))
				.startLimit(1)
				.allowStartIfComplete(false)
				.build();
	}

	@Bean(name = StepName.CREATE_SPACECRAFT_TYPES)
	public Step stepCreateSpacecraftTypes() {
		return stepBuilderFactory.get(StepName.CREATE_SPACECRAFT_TYPES)
				.<PageHeader, SpacecraftType>chunk(stepsProperties.getCreateSpacecraftTypes().getCommitInterval())
				.reader(applicationContext.getBean(SpacecraftTypeReader.class))
				.processor(applicationContext.getBean(SpacecraftTypeProcessor.class))
				.writer(applicationContext.getBean(SpacecraftTypeWriter.class))
				.listener(applicationContext.getBean(CommonStepExecutionListener.class))
				.startLimit(1)
				.allowStartIfComplete(false)
				.build();
	}

	@Bean(name = StepName.CREATE_SPACECRAFT_CLASSES)
	public Step stepCreateSpacecraftClasses() {
		return stepBuilderFactory.get(StepName.CREATE_SPACECRAFT_CLASSES)
				.<PageHeader, SpacecraftClass>chunk(stepsProperties.getCreateSpacecraftClasses().getCommitInterval())
				.reader(applicationContext.getBean(SpacecraftClassReader.class))
				.processor(applicationContext.getBean(SpacecraftClassProcessor.class))
				.writer(applicationContext.getBean(SpacecraftClassWriter.class))
				.listener(applicationContext.getBean(CommonStepExecutionListener.class))
				.startLimit(1)
				.allowStartIfComplete(false)
				.build();
	}

	@Bean(name = StepName.CREATE_SPACECRAFTS)
	public Step stepCreateSpacecrafts() {
		return stepBuilderFactory.get(StepName.CREATE_SPACECRAFTS)
				.<PageHeader, Spacecraft>chunk(stepsProperties.getCreateSpacecrafts().getCommitInterval())
				.reader(applicationContext.getBean(SpacecraftReader.class))
				.processor(applicationContext.getBean(SpacecraftProcessor.class))
				.writer(applicationContext.getBean(SpacecraftWriter.class))
				.listener(applicationContext.getBean(CommonStepExecutionListener.class))
				.startLimit(1)
				.allowStartIfComplete(false)
				.build();
	}

	@Bean(name = StepName.CREATE_MATERIALS)
	public Step stepCreateMaterials() {
		return stepBuilderFactory.get(StepName.CREATE_MATERIALS)
				.<PageHeader, Material>chunk(stepsProperties.getCreateMaterials().getCommitInterval())
				.reader(applicationContext.getBean(MaterialReader.class))
				.processor(applicationContext.getBean(MaterialProcessor.class))
				.writer(applicationContext.getBean(MaterialWriter.class))
				.listener(applicationContext.getBean(CommonStepExecutionListener.class))
				.startLimit(1)
				.allowStartIfComplete(false)
				.build();
	}

	@Bean(name = StepName.CREATE_CONFLICTS)
	public Step stepCreateConflicts() {
		return stepBuilderFactory.get(StepName.CREATE_CONFLICTS)
				.<PageHeader, Conflict>chunk(stepsProperties.getCreateConflicts().getCommitInterval())
				.reader(applicationContext.getBean(ConflictReader.class))
				.processor(applicationContext.getBean(ConflictProcessor.class))
				.writer(applicationContext.getBean(ConflictWriter.class))
				.listener(applicationContext.getBean(CommonStepExecutionListener.class))
				.startLimit(1)
				.allowStartIfComplete(false)
				.build();
	}

	@Bean(name = StepName.CREATE_ANIMALS)
	public Step stepCreateAnimals() {
		return stepBuilderFactory.get(StepName.CREATE_ANIMALS)
				.<PageHeader, Animal>chunk(stepsProperties.getCreateAnimals().getCommitInterval())
				.reader(applicationContext.getBean(AnimalReader.class))
				.processor(applicationContext.getBean(AnimalProcessor.class))
				.writer(applicationContext.getBean(AnimalWriter.class))
				.listener(applicationContext.getBean(CommonStepExecutionListener.class))
				.startLimit(1)
				.allowStartIfComplete(false)
				.build();
	}

	@Bean(name = StepName.CREATE_ELEMENTS)
	public Step stepCreateElements() {
		return stepBuilderFactory.get(StepName.CREATE_ELEMENTS)
				.<PageHeader, Element>chunk(stepsProperties.getCreateElements().getCommitInterval())
				.reader(applicationContext.getBean(ElementReader.class))
				.processor(applicationContext.getBean(ElementProcessor.class))
				.writer(applicationContext.getBean(ElementWriter.class))
				.listener(applicationContext.getBean(CommonStepExecutionListener.class))
				.startLimit(1)
				.allowStartIfComplete(false)
				.build();
	}

	@Bean(name = StepName.CREATE_MEDICAL_CONDITIONS)
	public Step stepCreateMedicalConditions() {
		return stepBuilderFactory.get(StepName.CREATE_MEDICAL_CONDITIONS)
				.<PageHeader, MedicalCondition>chunk(stepsProperties.getCreateMedicalConditions().getCommitInterval())
				.reader(applicationContext.getBean(MedicalConditionReader.class))
				.processor(applicationContext.getBean(MedicalConditionProcessor.class))
				.writer(applicationContext.getBean(MedicalConditionWriter.class))
				.listener(applicationContext.getBean(CommonStepExecutionListener.class))
				.startLimit(1)
				.allowStartIfComplete(false)
				.build();
	}

	@Bean(name = StepName.CREATE_TECHNOLOGY)
	public Step stepCreateTechnology() {
		return stepBuilderFactory.get(StepName.CREATE_TECHNOLOGY)
				.<PageHeader, Technology>chunk(stepsProperties.getCreateTechnology().getCommitInterval())
				.reader(applicationContext.getBean(TechnologyReader.class))
				.processor(applicationContext.getBean(TechnologyProcessor.class))
				.writer(applicationContext.getBean(TechnologyWriter.class))
				.listener(applicationContext.getBean(CommonStepExecutionListener.class))
				.startLimit(1)
				.allowStartIfComplete(false)
				.build();
	}

}
