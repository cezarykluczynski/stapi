package com.cezarykluczynski.stapi.etl.configuration.job;

import com.cezarykluczynski.stapi.etl.astronomicalObject.creation.processor.AstronomicalObjectProcessor;
import com.cezarykluczynski.stapi.etl.astronomicalObject.creation.processor.AstronomicalObjectReader;
import com.cezarykluczynski.stapi.etl.astronomicalObject.creation.processor.AstronomicalObjectWriter;
import com.cezarykluczynski.stapi.etl.astronomicalObject.link.processor.AstronomicalObjectLinkProcessor;
import com.cezarykluczynski.stapi.etl.astronomicalObject.link.processor.AstronomicalObjectLinkReader;
import com.cezarykluczynski.stapi.etl.character.creation.processor.CharacterProcessor;
import com.cezarykluczynski.stapi.etl.character.creation.processor.CharacterReader;
import com.cezarykluczynski.stapi.etl.character.creation.processor.CharacterWriter;
import com.cezarykluczynski.stapi.etl.comicSeries.creation.processor.ComicSeriesProcessor;
import com.cezarykluczynski.stapi.etl.comicSeries.creation.processor.ComicSeriesReader;
import com.cezarykluczynski.stapi.etl.comicSeries.creation.processor.ComicSeriesWriter;
import com.cezarykluczynski.stapi.etl.common.listener.CommonStepExecutionListener;
import com.cezarykluczynski.stapi.etl.company.creation.processor.CompanyProcessor;
import com.cezarykluczynski.stapi.etl.company.creation.processor.CompanyReader;
import com.cezarykluczynski.stapi.etl.company.creation.processor.CompanyWriter;
import com.cezarykluczynski.stapi.etl.configuration.job.properties.StepsProperties;
import com.cezarykluczynski.stapi.etl.episode.creation.processor.EpisodeProcessor;
import com.cezarykluczynski.stapi.etl.episode.creation.processor.EpisodeReader;
import com.cezarykluczynski.stapi.etl.episode.creation.processor.EpisodeWriter;
import com.cezarykluczynski.stapi.etl.movie.creation.processor.MovieProcessor;
import com.cezarykluczynski.stapi.etl.movie.creation.processor.MovieReader;
import com.cezarykluczynski.stapi.etl.movie.creation.processor.MovieWriter;
import com.cezarykluczynski.stapi.etl.performer.creation.processor.PerformerProcessor;
import com.cezarykluczynski.stapi.etl.performer.creation.processor.PerformerReader;
import com.cezarykluczynski.stapi.etl.performer.creation.processor.PerformerWriter;
import com.cezarykluczynski.stapi.etl.series.creation.processor.SeriesProcessor;
import com.cezarykluczynski.stapi.etl.series.creation.processor.SeriesReader;
import com.cezarykluczynski.stapi.etl.series.creation.processor.SeriesWriter;
import com.cezarykluczynski.stapi.etl.staff.creation.processor.StaffProcessor;
import com.cezarykluczynski.stapi.etl.staff.creation.processor.StaffReader;
import com.cezarykluczynski.stapi.etl.staff.creation.processor.StaffWriter;
import com.cezarykluczynski.stapi.etl.util.constant.StepName;
import com.cezarykluczynski.stapi.model.astronomicalObject.entity.AstronomicalObject;
import com.cezarykluczynski.stapi.model.character.entity.Character;
import com.cezarykluczynski.stapi.model.comicSeries.entity.ComicSeries;
import com.cezarykluczynski.stapi.model.company.entity.Company;
import com.cezarykluczynski.stapi.model.episode.entity.Episode;
import com.cezarykluczynski.stapi.model.movie.entity.Movie;
import com.cezarykluczynski.stapi.model.performer.entity.Performer;
import com.cezarykluczynski.stapi.model.series.entity.Series;
import com.cezarykluczynski.stapi.model.staff.entity.Staff;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader;
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

}
