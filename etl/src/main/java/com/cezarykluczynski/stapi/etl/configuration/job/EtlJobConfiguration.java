package com.cezarykluczynski.stapi.etl.configuration.job;

import com.cezarykluczynski.stapi.etl.character.creation.processor.CharacterProcessor;
import com.cezarykluczynski.stapi.etl.character.creation.processor.CharacterReader;
import com.cezarykluczynski.stapi.etl.character.creation.processor.CharacterWriter;
import com.cezarykluczynski.stapi.etl.common.listener.CommonStepExecutionListener;
import com.cezarykluczynski.stapi.etl.configuration.job.properties.StepsProperties;
import com.cezarykluczynski.stapi.etl.episode.creation.processor.EpisodeProcessor;
import com.cezarykluczynski.stapi.etl.episode.creation.processor.EpisodeReader;
import com.cezarykluczynski.stapi.etl.episode.creation.processor.EpisodeWriter;
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
import com.cezarykluczynski.stapi.model.character.entity.Character;
import com.cezarykluczynski.stapi.model.episode.entity.Episode;
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
@EnableConfigurationProperties(value = StepsProperties.class)
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

	@Bean(name = StepName.CREATE_SERIES)
	public Step stepCreateSeries() {
		return stepBuilderFactory.get(StepName.CREATE_SERIES)
				.<PageHeader, Series> chunk(stepsProperties.getCreateSeries().getCommitInterval())
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
				.<PageHeader, Performer> chunk(stepsProperties.getCreatePerformers().getCommitInterval())
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
				.<PageHeader, Staff> chunk(stepsProperties.getCreateStaff().getCommitInterval())
				.reader(applicationContext.getBean(StaffReader.class))
				.processor(applicationContext.getBean(StaffProcessor.class))
				.writer(applicationContext.getBean(StaffWriter.class))
				.listener(applicationContext.getBean(CommonStepExecutionListener.class))
				.startLimit(1)
				.allowStartIfComplete(false)
				.build();
	}

	@Bean(name = StepName.CREATE_CHARACTERS)
	public Step stepCreateCharacters() {
		return stepBuilderFactory.get(StepName.CREATE_CHARACTERS)
				.<PageHeader, Character> chunk(stepsProperties.getCreateCharacters().getCommitInterval())
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
				.<PageHeader, Episode> chunk(stepsProperties.getCreateEpisodes().getCommitInterval())
				.reader(applicationContext.getBean(EpisodeReader.class))
				.processor(applicationContext.getBean(EpisodeProcessor.class))
				.writer(applicationContext.getBean(EpisodeWriter.class))
				.listener(applicationContext.getBean(CommonStepExecutionListener.class))
				.startLimit(1)
				.allowStartIfComplete(false)
				.build();
	}

}
