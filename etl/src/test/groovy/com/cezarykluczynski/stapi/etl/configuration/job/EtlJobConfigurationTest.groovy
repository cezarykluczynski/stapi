package com.cezarykluczynski.stapi.etl.configuration.job

import com.cezarykluczynski.stapi.etl.astronomical_object.creation.processor.AstronomicalObjectProcessor
import com.cezarykluczynski.stapi.etl.astronomical_object.creation.processor.AstronomicalObjectReader
import com.cezarykluczynski.stapi.etl.astronomical_object.creation.processor.AstronomicalObjectWriter
import com.cezarykluczynski.stapi.etl.astronomical_object.link.processor.AstronomicalObjectLinkProcessor
import com.cezarykluczynski.stapi.etl.astronomical_object.link.processor.AstronomicalObjectLinkReader
import com.cezarykluczynski.stapi.etl.book.creation.processor.BookProcessor
import com.cezarykluczynski.stapi.etl.book.creation.processor.BookReader
import com.cezarykluczynski.stapi.etl.book.creation.processor.BookWriter
import com.cezarykluczynski.stapi.etl.book_collection.creation.processor.BookCollectionProcessor
import com.cezarykluczynski.stapi.etl.book_collection.creation.processor.BookCollectionReader
import com.cezarykluczynski.stapi.etl.book_collection.creation.processor.BookCollectionWriter
import com.cezarykluczynski.stapi.etl.book_series.creation.processor.BookSeriesProcessor
import com.cezarykluczynski.stapi.etl.book_series.creation.processor.BookSeriesReader
import com.cezarykluczynski.stapi.etl.book_series.creation.processor.BookSeriesWriter
import com.cezarykluczynski.stapi.etl.book_series.link.processor.BookSeriesLinkProcessor
import com.cezarykluczynski.stapi.etl.book_series.link.processor.BookSeriesLinkReader
import com.cezarykluczynski.stapi.etl.character.creation.processor.CharacterProcessor
import com.cezarykluczynski.stapi.etl.character.creation.processor.CharacterReader
import com.cezarykluczynski.stapi.etl.character.creation.processor.CharacterWriter
import com.cezarykluczynski.stapi.etl.comic_collection.creation.processor.ComicCollectionProcessor
import com.cezarykluczynski.stapi.etl.comic_collection.creation.processor.ComicCollectionReader
import com.cezarykluczynski.stapi.etl.comic_collection.creation.processor.ComicCollectionWriter
import com.cezarykluczynski.stapi.etl.comic_series.creation.processor.ComicSeriesProcessor
import com.cezarykluczynski.stapi.etl.comic_series.creation.processor.ComicSeriesReader
import com.cezarykluczynski.stapi.etl.comic_series.creation.processor.ComicSeriesWriter
import com.cezarykluczynski.stapi.etl.comic_series.link.processor.ComicSeriesLinkProcessor
import com.cezarykluczynski.stapi.etl.comic_series.link.processor.ComicSeriesLinkReader
import com.cezarykluczynski.stapi.etl.comic_strip.creation.processor.ComicStripProcessor
import com.cezarykluczynski.stapi.etl.comic_strip.creation.processor.ComicStripReader
import com.cezarykluczynski.stapi.etl.comic_strip.creation.processor.ComicStripWriter
import com.cezarykluczynski.stapi.etl.comics.creation.processor.ComicsProcessor
import com.cezarykluczynski.stapi.etl.comics.creation.processor.ComicsReader
import com.cezarykluczynski.stapi.etl.comics.creation.processor.ComicsWriter
import com.cezarykluczynski.stapi.etl.common.listener.CommonStepExecutionListener
import com.cezarykluczynski.stapi.etl.company.creation.processor.CompanyProcessor
import com.cezarykluczynski.stapi.etl.company.creation.processor.CompanyReader
import com.cezarykluczynski.stapi.etl.company.creation.processor.CompanyWriter
import com.cezarykluczynski.stapi.etl.configuration.job.properties.StepProperties
import com.cezarykluczynski.stapi.etl.configuration.job.properties.StepsProperties
import com.cezarykluczynski.stapi.etl.episode.creation.processor.EpisodeProcessor
import com.cezarykluczynski.stapi.etl.episode.creation.processor.EpisodeReader
import com.cezarykluczynski.stapi.etl.episode.creation.processor.EpisodeWriter
import com.cezarykluczynski.stapi.etl.food.creation.processor.FoodProcessor
import com.cezarykluczynski.stapi.etl.food.creation.processor.FoodReader
import com.cezarykluczynski.stapi.etl.food.creation.processor.FoodWriter
import com.cezarykluczynski.stapi.etl.literature.creation.processor.LiteratureProcessor
import com.cezarykluczynski.stapi.etl.literature.creation.processor.LiteratureReader
import com.cezarykluczynski.stapi.etl.literature.creation.processor.LiteratureWriter
import com.cezarykluczynski.stapi.etl.location.creation.processor.LocationProcessor
import com.cezarykluczynski.stapi.etl.location.creation.processor.LocationReader
import com.cezarykluczynski.stapi.etl.location.creation.processor.LocationWriter
import com.cezarykluczynski.stapi.etl.magazine.creation.processor.MagazineProcessor
import com.cezarykluczynski.stapi.etl.magazine.creation.processor.MagazineReader
import com.cezarykluczynski.stapi.etl.magazine.creation.processor.MagazineWriter
import com.cezarykluczynski.stapi.etl.magazine_series.creation.processor.MagazineSeriesProcessor
import com.cezarykluczynski.stapi.etl.magazine_series.creation.processor.MagazineSeriesReader
import com.cezarykluczynski.stapi.etl.magazine_series.creation.processor.MagazineSeriesWriter
import com.cezarykluczynski.stapi.etl.movie.creation.processor.MovieProcessor
import com.cezarykluczynski.stapi.etl.movie.creation.processor.MovieReader
import com.cezarykluczynski.stapi.etl.movie.creation.processor.MovieWriter
import com.cezarykluczynski.stapi.etl.organization.creation.processor.OrganizationProcessor
import com.cezarykluczynski.stapi.etl.organization.creation.processor.OrganizationReader
import com.cezarykluczynski.stapi.etl.organization.creation.processor.OrganizationWriter
import com.cezarykluczynski.stapi.etl.performer.creation.processor.PerformerProcessor
import com.cezarykluczynski.stapi.etl.performer.creation.processor.PerformerReader
import com.cezarykluczynski.stapi.etl.performer.creation.processor.PerformerWriter
import com.cezarykluczynski.stapi.etl.season.creation.processor.SeasonProcessor
import com.cezarykluczynski.stapi.etl.season.creation.processor.SeasonReader
import com.cezarykluczynski.stapi.etl.season.creation.processor.SeasonWriter
import com.cezarykluczynski.stapi.etl.series.creation.processor.SeriesProcessor
import com.cezarykluczynski.stapi.etl.series.creation.processor.SeriesReader
import com.cezarykluczynski.stapi.etl.series.creation.processor.SeriesWriter
import com.cezarykluczynski.stapi.etl.staff.creation.processor.StaffProcessor
import com.cezarykluczynski.stapi.etl.staff.creation.processor.StaffReader
import com.cezarykluczynski.stapi.etl.staff.creation.processor.StaffWriter
import com.cezarykluczynski.stapi.etl.trading_card.creation.processor.TradingCardSetReader
import com.cezarykluczynski.stapi.etl.trading_card.creation.processor.TradingCardSetWriter
import com.cezarykluczynski.stapi.etl.trading_card.creation.processor.set.TradingCardSetProcessor
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.etl.video_game.creation.processor.VideoGameProcessor
import com.cezarykluczynski.stapi.etl.video_game.creation.processor.VideoGameReader
import com.cezarykluczynski.stapi.etl.video_game.creation.processor.VideoGameWriter
import com.cezarykluczynski.stapi.etl.video_release.creation.processor.VideoReleaseProcessor
import com.cezarykluczynski.stapi.etl.video_release.creation.processor.VideoReleaseReader
import com.cezarykluczynski.stapi.etl.video_release.creation.processor.VideoReleaseWriter
import org.apache.commons.lang3.RandomUtils
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.StepExecutionListener
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.step.builder.SimpleStepBuilder
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.core.step.tasklet.TaskletStep
import org.springframework.batch.item.ItemProcessor
import org.springframework.batch.item.ItemReader
import org.springframework.batch.item.ItemWriter
import org.springframework.beans.factory.FactoryBean
import org.springframework.context.ApplicationContext
import spock.lang.Specification

class EtlJobConfigurationTest extends Specification {

	private static final Integer STEP_SIZE = RandomUtils.nextInt(10, 20)

	private JobBuilder jobBuilderMock

	private ApplicationContext applicationContextMock

	private StepBuilderFactory stepBuilderFactoryMock

	private StepsProperties stepsPropertiesMock

	private EtlJobConfiguration etlJobConfiguration

	private StepBuilder stepBuilderMock

	private SimpleStepBuilder simpleStepBuilderMock

	private ItemReader itemReaderMock

	private ItemProcessor itemProcessorMock

	private ItemWriter itemWriterMock

	private StepExecutionListener stepExecutionListenerMock

	private TaskletStep taskletStepMock

	private StepProperties stepProperties

	void setup() {
		jobBuilderMock = Mock()
		applicationContextMock = Mock()
		stepBuilderFactoryMock = Mock()
		stepsPropertiesMock = Mock()
		etlJobConfiguration = new EtlJobConfiguration(
				jobBuilder: jobBuilderMock,
				applicationContext: applicationContextMock,
				stepBuilderFactory: stepBuilderFactoryMock,
				stepsProperties: stepsPropertiesMock)

		stepBuilderMock = Mock()
		simpleStepBuilderMock = Mock()
		itemReaderMock = Mock()
		itemProcessorMock = Mock()
		itemWriterMock = Mock()
		stepExecutionListenerMock = Mock()
		taskletStepMock = Mock()
		stepProperties = Mock()
	}

	void "passed JOB_CREATE bean creation to JobBuilder, and returns FactoryBean"() {
		given:
		Job job = Mock()

		when:
		FactoryBean<Job> factoryBean = etlJobConfiguration.jobCreate()

		then:
		1 * jobBuilderMock.build() >> job
		factoryBean.object == job
		0 * _
	}

	void "CREATE_COMPANIES step is created"() {
		when:
		Step step = etlJobConfiguration.stepCreateCompanies()

		then: 'StepBuilder is retrieved'
		1 * stepBuilderFactoryMock.get(StepName.CREATE_COMPANIES) >> stepBuilderMock

		then: 'commit interval is configured'
		1 * stepsPropertiesMock.createCompanies >> stepProperties
		1 * stepProperties.commitInterval >> STEP_SIZE
		1 * stepBuilderMock.chunk(STEP_SIZE) >> simpleStepBuilderMock

		then: 'beans are retrieved from application context, then passed to builder'
		1 * applicationContextMock.getBean(CompanyReader) >> itemReaderMock
		1 * simpleStepBuilderMock.reader(itemReaderMock) >> simpleStepBuilderMock
		1 * applicationContextMock.getBean(CompanyProcessor) >> itemProcessorMock
		1 * simpleStepBuilderMock.processor(itemProcessorMock) >> simpleStepBuilderMock
		1 * applicationContextMock.getBean(CompanyWriter) >> itemWriterMock
		1 * simpleStepBuilderMock.writer(itemWriterMock) >> simpleStepBuilderMock
		1 * applicationContextMock.getBean(CommonStepExecutionListener) >> stepExecutionListenerMock
		1 * simpleStepBuilderMock.listener(stepExecutionListenerMock) >> simpleStepBuilderMock

		then: 'step is configured to run only once'
		1 * simpleStepBuilderMock.startLimit(1) >> simpleStepBuilderMock
		1 * simpleStepBuilderMock.allowStartIfComplete(false) >> simpleStepBuilderMock

		then: 'tasklet step is returned'
		1 * simpleStepBuilderMock.build() >> taskletStepMock

		then: 'step is being returned'
		step == taskletStepMock
	}

	void "CREATE_SERIES step is created"() {
		when:
		Step step = etlJobConfiguration.stepCreateSeries()

		then: 'StepBuilder is retrieved'
		1 * stepBuilderFactoryMock.get(StepName.CREATE_SERIES) >> stepBuilderMock

		then: 'commit interval is configured'
		1 * stepsPropertiesMock.createSeries >> stepProperties
		1 * stepProperties.commitInterval >> STEP_SIZE
		1 * stepBuilderMock.chunk(STEP_SIZE) >> simpleStepBuilderMock

		then: 'beans are retrieved from application context, then passed to builder'
		1 * applicationContextMock.getBean(SeriesReader) >> itemReaderMock
		1 * simpleStepBuilderMock.reader(itemReaderMock) >> simpleStepBuilderMock
		1 * applicationContextMock.getBean(SeriesProcessor) >> itemProcessorMock
		1 * simpleStepBuilderMock.processor(itemProcessorMock) >> simpleStepBuilderMock
		1 * applicationContextMock.getBean(SeriesWriter) >> itemWriterMock
		1 * simpleStepBuilderMock.writer(itemWriterMock) >> simpleStepBuilderMock
		1 * applicationContextMock.getBean(CommonStepExecutionListener) >> stepExecutionListenerMock
		1 * simpleStepBuilderMock.listener(stepExecutionListenerMock) >> simpleStepBuilderMock

		then: 'step is configured to run only once'
		1 * simpleStepBuilderMock.startLimit(1) >> simpleStepBuilderMock
		1 * simpleStepBuilderMock.allowStartIfComplete(false) >> simpleStepBuilderMock

		then: 'tasklet step is returned'
		1 * simpleStepBuilderMock.build() >> taskletStepMock

		then: 'step is being returned'
		step == taskletStepMock
	}

	void "CREATE_SEASONS step is created"() {
		when:
		Step step = etlJobConfiguration.stepCreateSeasons()

		then: 'StepBuilder is retrieved'
		1 * stepBuilderFactoryMock.get(StepName.CREATE_SEASONS) >> stepBuilderMock

		then: 'commit interval is configured'
		1 * stepsPropertiesMock.createSeasons >> stepProperties
		1 * stepProperties.commitInterval >> STEP_SIZE
		1 * stepBuilderMock.chunk(STEP_SIZE) >> simpleStepBuilderMock

		then: 'beans are retrieved from application context, then passed to builder'
		1 * applicationContextMock.getBean(SeasonReader) >> itemReaderMock
		1 * simpleStepBuilderMock.reader(itemReaderMock) >> simpleStepBuilderMock
		1 * applicationContextMock.getBean(SeasonProcessor) >> itemProcessorMock
		1 * simpleStepBuilderMock.processor(itemProcessorMock) >> simpleStepBuilderMock
		1 * applicationContextMock.getBean(SeasonWriter) >> itemWriterMock
		1 * simpleStepBuilderMock.writer(itemWriterMock) >> simpleStepBuilderMock
		1 * applicationContextMock.getBean(CommonStepExecutionListener) >> stepExecutionListenerMock
		1 * simpleStepBuilderMock.listener(stepExecutionListenerMock) >> simpleStepBuilderMock

		then: 'step is configured to run only once'
		1 * simpleStepBuilderMock.startLimit(1) >> simpleStepBuilderMock
		1 * simpleStepBuilderMock.allowStartIfComplete(false) >> simpleStepBuilderMock

		then: 'tasklet step is returned'
		1 * simpleStepBuilderMock.build() >> taskletStepMock

		then: 'step is being returned'
		step == taskletStepMock
	}

	void "CREATE_PERFORMERS step is created"() {
		when:
		Step step = etlJobConfiguration.stepCreatePerformers()

		then: 'StepBuilder is retrieved'
		1 * stepBuilderFactoryMock.get(StepName.CREATE_PERFORMERS) >> stepBuilderMock

		then: 'commit interval is configured'
		1 * stepsPropertiesMock.createPerformers >> stepProperties
		1 * stepProperties.commitInterval >> STEP_SIZE
		1 * stepBuilderMock.chunk(STEP_SIZE) >> simpleStepBuilderMock

		then: 'beans are retrieved from application context, then passed to builder'
		1 * applicationContextMock.getBean(PerformerReader) >> itemReaderMock
		1 * simpleStepBuilderMock.reader(itemReaderMock) >> simpleStepBuilderMock
		1 * applicationContextMock.getBean(PerformerProcessor) >> itemProcessorMock
		1 * simpleStepBuilderMock.processor(itemProcessorMock) >> simpleStepBuilderMock
		1 * applicationContextMock.getBean(PerformerWriter) >> itemWriterMock
		1 * simpleStepBuilderMock.writer(itemWriterMock) >> simpleStepBuilderMock
		1 * applicationContextMock.getBean(CommonStepExecutionListener) >> stepExecutionListenerMock
		1 * simpleStepBuilderMock.listener(stepExecutionListenerMock) >> simpleStepBuilderMock

		then: 'step is configured to run only once'
		1 * simpleStepBuilderMock.startLimit(1) >> simpleStepBuilderMock
		1 * simpleStepBuilderMock.allowStartIfComplete(false) >> simpleStepBuilderMock

		then: 'tasklet step is returned'
		1 * simpleStepBuilderMock.build() >> taskletStepMock

		then: 'step is being returned'
		step == taskletStepMock
	}

	void "CREATE_STAFF step is created"() {
		when:
		Step step = etlJobConfiguration.stepCreateStaff()

		then: 'StepBuilder is retrieved'
		1 * stepBuilderFactoryMock.get(StepName.CREATE_STAFF) >> stepBuilderMock

		then: 'commit interval is configured'
		1 * stepsPropertiesMock.createStaff >> stepProperties
		1 * stepProperties.commitInterval >> STEP_SIZE
		1 * stepBuilderMock.chunk(STEP_SIZE) >> simpleStepBuilderMock

		then: 'beans are retrieved from application context, then passed to builder'
		1 * applicationContextMock.getBean(StaffReader) >> itemReaderMock
		1 * simpleStepBuilderMock.reader(itemReaderMock) >> simpleStepBuilderMock
		1 * applicationContextMock.getBean(StaffProcessor) >> itemProcessorMock
		1 * simpleStepBuilderMock.processor(itemProcessorMock) >> simpleStepBuilderMock
		1 * applicationContextMock.getBean(StaffWriter) >> itemWriterMock
		1 * simpleStepBuilderMock.writer(itemWriterMock) >> simpleStepBuilderMock
		1 * applicationContextMock.getBean(CommonStepExecutionListener) >> stepExecutionListenerMock
		1 * simpleStepBuilderMock.listener(stepExecutionListenerMock) >> simpleStepBuilderMock

		then: 'step is configured to run only once'
		1 * simpleStepBuilderMock.startLimit(1) >> simpleStepBuilderMock
		1 * simpleStepBuilderMock.allowStartIfComplete(false) >> simpleStepBuilderMock

		then: 'tasklet step is returned'
		1 * simpleStepBuilderMock.build() >> taskletStepMock

		then: 'step is being returned'
		step == taskletStepMock
	}

	void "CREATE_ASTRONOMICAL_OBJECTS step is created"() {
		when:
		Step step = etlJobConfiguration.stepCreateAstronomicalObject()

		then: 'StepBuilder is retrieved'
		1 * stepBuilderFactoryMock.get(StepName.CREATE_ASTRONOMICAL_OBJECTS) >> stepBuilderMock

		then: 'commit interval is configured'
		1 * stepsPropertiesMock.createAstronomicalObjects >> stepProperties
		1 * stepProperties.commitInterval >> STEP_SIZE
		1 * stepBuilderMock.chunk(STEP_SIZE) >> simpleStepBuilderMock

		then: 'beans are retrieved from application context, then passed to builder'
		1 * applicationContextMock.getBean(AstronomicalObjectReader) >> itemReaderMock
		1 * simpleStepBuilderMock.reader(itemReaderMock) >> simpleStepBuilderMock
		1 * applicationContextMock.getBean(AstronomicalObjectProcessor) >> itemProcessorMock
		1 * simpleStepBuilderMock.processor(itemProcessorMock) >> simpleStepBuilderMock
		1 * applicationContextMock.getBean(AstronomicalObjectWriter) >> itemWriterMock
		1 * simpleStepBuilderMock.writer(itemWriterMock) >> simpleStepBuilderMock
		1 * applicationContextMock.getBean(CommonStepExecutionListener) >> stepExecutionListenerMock
		1 * simpleStepBuilderMock.listener(stepExecutionListenerMock) >> simpleStepBuilderMock

		then: 'step is configured to run only once'
		1 * simpleStepBuilderMock.startLimit(1) >> simpleStepBuilderMock
		1 * simpleStepBuilderMock.allowStartIfComplete(false) >> simpleStepBuilderMock

		then: 'tasklet step is returned'
		1 * simpleStepBuilderMock.build() >> taskletStepMock

		then: 'step is being returned'
		step == taskletStepMock
	}

	void "CREATE_CHARACTERS step is created"() {
		when:
		Step step = etlJobConfiguration.stepCreateCharacters()

		then: 'StepBuilder is retrieved'
		1 * stepBuilderFactoryMock.get(StepName.CREATE_CHARACTERS) >> stepBuilderMock

		then: 'commit interval is configured'
		1 * stepsPropertiesMock.createCharacters >> stepProperties
		1 * stepProperties.commitInterval >> STEP_SIZE
		1 * stepBuilderMock.chunk(STEP_SIZE) >> simpleStepBuilderMock

		then: 'beans are retrieved from application context, then passed to builder'
		1 * applicationContextMock.getBean(CharacterReader) >> itemReaderMock
		1 * simpleStepBuilderMock.reader(itemReaderMock) >> simpleStepBuilderMock
		1 * applicationContextMock.getBean(CharacterProcessor) >> itemProcessorMock
		1 * simpleStepBuilderMock.processor(itemProcessorMock) >> simpleStepBuilderMock
		1 * applicationContextMock.getBean(CharacterWriter) >> itemWriterMock
		1 * simpleStepBuilderMock.writer(itemWriterMock) >> simpleStepBuilderMock
		1 * applicationContextMock.getBean(CommonStepExecutionListener) >> stepExecutionListenerMock
		1 * simpleStepBuilderMock.listener(stepExecutionListenerMock) >> simpleStepBuilderMock

		then: 'step is configured to run only once'
		1 * simpleStepBuilderMock.startLimit(1) >> simpleStepBuilderMock
		1 * simpleStepBuilderMock.allowStartIfComplete(false) >> simpleStepBuilderMock

		then: 'tasklet step is returned'
		1 * simpleStepBuilderMock.build() >> taskletStepMock

		then: 'step is being returned'
		step == taskletStepMock
	}

	void "CREATE_EPISODES step is created"() {
		when:
		Step step = etlJobConfiguration.stepCreateEpisodes()

		then: 'StepBuilder is retrieved'
		1 * stepBuilderFactoryMock.get(StepName.CREATE_EPISODES) >> stepBuilderMock

		then: 'commit interval is configured'
		1 * stepsPropertiesMock.createEpisodes >> stepProperties
		1 * stepProperties.commitInterval >> STEP_SIZE
		1 * stepBuilderMock.chunk(STEP_SIZE) >> simpleStepBuilderMock

		then: 'beans are retrieved from application context, then passed to builder'
		1 * applicationContextMock.getBean(EpisodeReader) >> itemReaderMock
		1 * simpleStepBuilderMock.reader(itemReaderMock) >> simpleStepBuilderMock
		1 * applicationContextMock.getBean(EpisodeProcessor) >> itemProcessorMock
		1 * simpleStepBuilderMock.processor(itemProcessorMock) >> simpleStepBuilderMock
		1 * applicationContextMock.getBean(EpisodeWriter) >> itemWriterMock
		1 * simpleStepBuilderMock.writer(itemWriterMock) >> simpleStepBuilderMock
		1 * applicationContextMock.getBean(CommonStepExecutionListener) >> stepExecutionListenerMock
		1 * simpleStepBuilderMock.listener(stepExecutionListenerMock) >> simpleStepBuilderMock

		then: 'step is configured to run only once'
		1 * simpleStepBuilderMock.startLimit(1) >> simpleStepBuilderMock
		1 * simpleStepBuilderMock.allowStartIfComplete(false) >> simpleStepBuilderMock

		then: 'tasklet step is returned'
		1 * simpleStepBuilderMock.build() >> taskletStepMock

		then: 'step is being returned'
		step == taskletStepMock
	}

	void "CREATE_MOVIES step is created"() {
		when:
		Step step = etlJobConfiguration.stepCreateMovies()

		then: 'StepBuilder is retrieved'
		1 * stepBuilderFactoryMock.get(StepName.CREATE_MOVIES) >> stepBuilderMock

		then: 'commit interval is configured'
		1 * stepsPropertiesMock.createMovies >> stepProperties
		1 * stepProperties.commitInterval >> STEP_SIZE
		1 * stepBuilderMock.chunk(STEP_SIZE) >> simpleStepBuilderMock

		then: 'beans are retrieved from application context, then passed to builder'
		1 * applicationContextMock.getBean(MovieReader) >> itemReaderMock
		1 * simpleStepBuilderMock.reader(itemReaderMock) >> simpleStepBuilderMock
		1 * applicationContextMock.getBean(MovieProcessor) >> itemProcessorMock
		1 * simpleStepBuilderMock.processor(itemProcessorMock) >> simpleStepBuilderMock
		1 * applicationContextMock.getBean(MovieWriter) >> itemWriterMock
		1 * simpleStepBuilderMock.writer(itemWriterMock) >> simpleStepBuilderMock
		1 * applicationContextMock.getBean(CommonStepExecutionListener) >> stepExecutionListenerMock
		1 * simpleStepBuilderMock.listener(stepExecutionListenerMock) >> simpleStepBuilderMock

		then: 'step is configured to run only once'
		1 * simpleStepBuilderMock.startLimit(1) >> simpleStepBuilderMock
		1 * simpleStepBuilderMock.allowStartIfComplete(false) >> simpleStepBuilderMock

		then: 'tasklet step is returned'
		1 * simpleStepBuilderMock.build() >> taskletStepMock

		then: 'step is being returned'
		step == taskletStepMock
	}

	void "LINK_ASTRONOMICAL_OBJECTS step is created"() {
		when:
		Step step = etlJobConfiguration.stepLinkAstronomicalObject()

		then: 'StepBuilder is retrieved'
		1 * stepBuilderFactoryMock.get(StepName.LINK_ASTRONOMICAL_OBJECTS) >> stepBuilderMock

		then: 'commit interval is configured'
		1 * stepsPropertiesMock.linkAstronomicalObjects >> stepProperties
		1 * stepProperties.commitInterval >> STEP_SIZE
		1 * stepBuilderMock.chunk(STEP_SIZE) >> simpleStepBuilderMock

		then: 'beans are retrieved from application context, then passed to builder'
		1 * applicationContextMock.getBean(AstronomicalObjectLinkReader) >> itemReaderMock
		1 * simpleStepBuilderMock.reader(itemReaderMock) >> simpleStepBuilderMock
		1 * applicationContextMock.getBean(AstronomicalObjectLinkProcessor) >> itemProcessorMock
		1 * simpleStepBuilderMock.processor(itemProcessorMock) >> simpleStepBuilderMock
		1 * applicationContextMock.getBean(AstronomicalObjectWriter) >> itemWriterMock
		1 * simpleStepBuilderMock.writer(itemWriterMock) >> simpleStepBuilderMock
		1 * applicationContextMock.getBean(CommonStepExecutionListener) >> stepExecutionListenerMock
		1 * simpleStepBuilderMock.listener(stepExecutionListenerMock) >> simpleStepBuilderMock

		then: 'step is configured to run only once'
		1 * simpleStepBuilderMock.startLimit(1) >> simpleStepBuilderMock
		1 * simpleStepBuilderMock.allowStartIfComplete(false) >> simpleStepBuilderMock

		then: 'tasklet step is returned'
		1 * simpleStepBuilderMock.build() >> taskletStepMock

		then: 'step is being returned'
		step == taskletStepMock
	}

	void "CREATE_COMIC_SERIES step is created"() {
		when:
		Step step = etlJobConfiguration.stepCreateComicSeries()

		then: 'StepBuilder is retrieved'
		1 * stepBuilderFactoryMock.get(StepName.CREATE_COMIC_SERIES) >> stepBuilderMock

		then: 'commit interval is configured'
		1 * stepsPropertiesMock.createComicSeries >> stepProperties
		1 * stepProperties.commitInterval >> STEP_SIZE
		1 * stepBuilderMock.chunk(STEP_SIZE) >> simpleStepBuilderMock

		then: 'beans are retrieved from application context, then passed to builder'
		1 * applicationContextMock.getBean(ComicSeriesReader) >> itemReaderMock
		1 * simpleStepBuilderMock.reader(itemReaderMock) >> simpleStepBuilderMock
		1 * applicationContextMock.getBean(ComicSeriesProcessor) >> itemProcessorMock
		1 * simpleStepBuilderMock.processor(itemProcessorMock) >> simpleStepBuilderMock
		1 * applicationContextMock.getBean(ComicSeriesWriter) >> itemWriterMock
		1 * simpleStepBuilderMock.writer(itemWriterMock) >> simpleStepBuilderMock
		1 * applicationContextMock.getBean(CommonStepExecutionListener) >> stepExecutionListenerMock
		1 * simpleStepBuilderMock.listener(stepExecutionListenerMock) >> simpleStepBuilderMock

		then: 'step is configured to run only once'
		1 * simpleStepBuilderMock.startLimit(1) >> simpleStepBuilderMock
		1 * simpleStepBuilderMock.allowStartIfComplete(false) >> simpleStepBuilderMock

		then: 'tasklet step is returned'
		1 * simpleStepBuilderMock.build() >> taskletStepMock

		then: 'step is being returned'
		step == taskletStepMock
	}

	void "LINK_COMIC_SERIES step is created"() {
		when:
		Step step = etlJobConfiguration.stepLinkComicSeries()

		then: 'StepBuilder is retrieved'
		1 * stepBuilderFactoryMock.get(StepName.LINK_COMIC_SERIES) >> stepBuilderMock

		then: 'commit interval is configured'
		1 * stepsPropertiesMock.linkComicSeries >> stepProperties
		1 * stepProperties.commitInterval >> STEP_SIZE
		1 * stepBuilderMock.chunk(STEP_SIZE) >> simpleStepBuilderMock

		then: 'beans are retrieved from application context, then passed to builder'
		1 * applicationContextMock.getBean(ComicSeriesLinkReader) >> itemReaderMock
		1 * simpleStepBuilderMock.reader(itemReaderMock) >> simpleStepBuilderMock
		1 * applicationContextMock.getBean(ComicSeriesLinkProcessor) >> itemProcessorMock
		1 * simpleStepBuilderMock.processor(itemProcessorMock) >> simpleStepBuilderMock
		1 * applicationContextMock.getBean(ComicSeriesWriter) >> itemWriterMock
		1 * simpleStepBuilderMock.writer(itemWriterMock) >> simpleStepBuilderMock
		1 * applicationContextMock.getBean(CommonStepExecutionListener) >> stepExecutionListenerMock
		1 * simpleStepBuilderMock.listener(stepExecutionListenerMock) >> simpleStepBuilderMock

		then: 'step is configured to run only once'
		1 * simpleStepBuilderMock.startLimit(1) >> simpleStepBuilderMock
		1 * simpleStepBuilderMock.allowStartIfComplete(false) >> simpleStepBuilderMock

		then: 'tasklet step is returned'
		1 * simpleStepBuilderMock.build() >> taskletStepMock

		then: 'step is being returned'
		step == taskletStepMock
	}

	void "CREATE_COMICS step is created"() {
		when:
		Step step = etlJobConfiguration.stepCreateComics()

		then: 'StepBuilder is retrieved'
		1 * stepBuilderFactoryMock.get(StepName.CREATE_COMICS) >> stepBuilderMock

		then: 'commit interval is configured'
		1 * stepsPropertiesMock.createComics >> stepProperties
		1 * stepProperties.commitInterval >> STEP_SIZE
		1 * stepBuilderMock.chunk(STEP_SIZE) >> simpleStepBuilderMock

		then: 'beans are retrieved from application context, then passed to builder'
		1 * applicationContextMock.getBean(ComicsReader) >> itemReaderMock
		1 * simpleStepBuilderMock.reader(itemReaderMock) >> simpleStepBuilderMock
		1 * applicationContextMock.getBean(ComicsProcessor) >> itemProcessorMock
		1 * simpleStepBuilderMock.processor(itemProcessorMock) >> simpleStepBuilderMock
		1 * applicationContextMock.getBean(ComicsWriter) >> itemWriterMock
		1 * simpleStepBuilderMock.writer(itemWriterMock) >> simpleStepBuilderMock
		1 * applicationContextMock.getBean(CommonStepExecutionListener) >> stepExecutionListenerMock
		1 * simpleStepBuilderMock.listener(stepExecutionListenerMock) >> simpleStepBuilderMock

		then: 'step is configured to run only once'
		1 * simpleStepBuilderMock.startLimit(1) >> simpleStepBuilderMock
		1 * simpleStepBuilderMock.allowStartIfComplete(false) >> simpleStepBuilderMock

		then: 'tasklet step is returned'
		1 * simpleStepBuilderMock.build() >> taskletStepMock

		then: 'step is being returned'
		step == taskletStepMock
	}

	void "CREATE_COMIC_STRIPS step is created"() {
		when:
		Step step = etlJobConfiguration.stepCreateComicStrips()

		then: 'StepBuilder is retrieved'
		1 * stepBuilderFactoryMock.get(StepName.CREATE_COMIC_STRIPS) >> stepBuilderMock

		then: 'commit interval is configured'
		1 * stepsPropertiesMock.createComicStrips >> stepProperties
		1 * stepProperties.commitInterval >> STEP_SIZE
		1 * stepBuilderMock.chunk(STEP_SIZE) >> simpleStepBuilderMock

		then: 'beans are retrieved from application context, then passed to builder'
		1 * applicationContextMock.getBean(ComicStripReader) >> itemReaderMock
		1 * simpleStepBuilderMock.reader(itemReaderMock) >> simpleStepBuilderMock
		1 * applicationContextMock.getBean(ComicStripProcessor) >> itemProcessorMock
		1 * simpleStepBuilderMock.processor(itemProcessorMock) >> simpleStepBuilderMock
		1 * applicationContextMock.getBean(ComicStripWriter) >> itemWriterMock
		1 * simpleStepBuilderMock.writer(itemWriterMock) >> simpleStepBuilderMock
		1 * applicationContextMock.getBean(CommonStepExecutionListener) >> stepExecutionListenerMock
		1 * simpleStepBuilderMock.listener(stepExecutionListenerMock) >> simpleStepBuilderMock

		then: 'step is configured to run only once'
		1 * simpleStepBuilderMock.startLimit(1) >> simpleStepBuilderMock
		1 * simpleStepBuilderMock.allowStartIfComplete(false) >> simpleStepBuilderMock

		then: 'tasklet step is returned'
		1 * simpleStepBuilderMock.build() >> taskletStepMock

		then: 'step is being returned'
		step == taskletStepMock
	}

	void "CREATE_COMIC_COLLECTIONS step is created"() {
		when:
		Step step = etlJobConfiguration.stepCreateComicCollections()

		then: 'StepBuilder is retrieved'
		1 * stepBuilderFactoryMock.get(StepName.CREATE_COMIC_COLLECTIONS) >> stepBuilderMock

		then: 'commit interval is configured'
		1 * stepsPropertiesMock.createComicCollections >> stepProperties
		1 * stepProperties.commitInterval >> STEP_SIZE
		1 * stepBuilderMock.chunk(STEP_SIZE) >> simpleStepBuilderMock

		then: 'beans are retrieved from application context, then passed to builder'
		1 * applicationContextMock.getBean(ComicCollectionReader) >> itemReaderMock
		1 * simpleStepBuilderMock.reader(itemReaderMock) >> simpleStepBuilderMock
		1 * applicationContextMock.getBean(ComicCollectionProcessor) >> itemProcessorMock
		1 * simpleStepBuilderMock.processor(itemProcessorMock) >> simpleStepBuilderMock
		1 * applicationContextMock.getBean(ComicCollectionWriter) >> itemWriterMock
		1 * simpleStepBuilderMock.writer(itemWriterMock) >> simpleStepBuilderMock
		1 * applicationContextMock.getBean(CommonStepExecutionListener) >> stepExecutionListenerMock
		1 * simpleStepBuilderMock.listener(stepExecutionListenerMock) >> simpleStepBuilderMock

		then: 'step is configured to run only once'
		1 * simpleStepBuilderMock.startLimit(1) >> simpleStepBuilderMock
		1 * simpleStepBuilderMock.allowStartIfComplete(false) >> simpleStepBuilderMock

		then: 'tasklet step is returned'
		1 * simpleStepBuilderMock.build() >> taskletStepMock

		then: 'step is being returned'
		step == taskletStepMock
	}

	void "CREATE_ORGANIZATIONS step is created"() {
		when:
		Step step = etlJobConfiguration.stepCreateOrganizations()

		then: 'StepBuilder is retrieved'
		1 * stepBuilderFactoryMock.get(StepName.CREATE_ORGANIZATIONS) >> stepBuilderMock

		then: 'commit interval is configured'
		1 * stepsPropertiesMock.createOrganizations >> stepProperties
		1 * stepProperties.commitInterval >> STEP_SIZE
		1 * stepBuilderMock.chunk(STEP_SIZE) >> simpleStepBuilderMock

		then: 'beans are retrieved from application context, then passed to builder'
		1 * applicationContextMock.getBean(OrganizationReader) >> itemReaderMock
		1 * simpleStepBuilderMock.reader(itemReaderMock) >> simpleStepBuilderMock
		1 * applicationContextMock.getBean(OrganizationProcessor) >> itemProcessorMock
		1 * simpleStepBuilderMock.processor(itemProcessorMock) >> simpleStepBuilderMock
		1 * applicationContextMock.getBean(OrganizationWriter) >> itemWriterMock
		1 * simpleStepBuilderMock.writer(itemWriterMock) >> simpleStepBuilderMock
		1 * applicationContextMock.getBean(CommonStepExecutionListener) >> stepExecutionListenerMock
		1 * simpleStepBuilderMock.listener(stepExecutionListenerMock) >> simpleStepBuilderMock

		then: 'step is configured to run only once'
		1 * simpleStepBuilderMock.startLimit(1) >> simpleStepBuilderMock
		1 * simpleStepBuilderMock.allowStartIfComplete(false) >> simpleStepBuilderMock

		then: 'tasklet step is returned'
		1 * simpleStepBuilderMock.build() >> taskletStepMock

		then: 'step is being returned'
		step == taskletStepMock
	}

	void "CREATE_FOODS step is created"() {
		when:
		Step step = etlJobConfiguration.stepCreateFoods()

		then: 'StepBuilder is retrieved'
		1 * stepBuilderFactoryMock.get(StepName.CREATE_FOODS) >> stepBuilderMock

		then: 'commit interval is configured'
		1 * stepsPropertiesMock.createFoods >> stepProperties
		1 * stepProperties.commitInterval >> STEP_SIZE
		1 * stepBuilderMock.chunk(STEP_SIZE) >> simpleStepBuilderMock

		then: 'beans are retrieved from application context, then passed to builder'
		1 * applicationContextMock.getBean(FoodReader) >> itemReaderMock
		1 * simpleStepBuilderMock.reader(itemReaderMock) >> simpleStepBuilderMock
		1 * applicationContextMock.getBean(FoodProcessor) >> itemProcessorMock
		1 * simpleStepBuilderMock.processor(itemProcessorMock) >> simpleStepBuilderMock
		1 * applicationContextMock.getBean(FoodWriter) >> itemWriterMock
		1 * simpleStepBuilderMock.writer(itemWriterMock) >> simpleStepBuilderMock
		1 * applicationContextMock.getBean(CommonStepExecutionListener) >> stepExecutionListenerMock
		1 * simpleStepBuilderMock.listener(stepExecutionListenerMock) >> simpleStepBuilderMock

		then: 'step is configured to run only once'
		1 * simpleStepBuilderMock.startLimit(1) >> simpleStepBuilderMock
		1 * simpleStepBuilderMock.allowStartIfComplete(false) >> simpleStepBuilderMock

		then: 'tasklet step is returned'
		1 * simpleStepBuilderMock.build() >> taskletStepMock

		then: 'step is being returned'
		step == taskletStepMock
	}

	void "CREATE_LOCATIONS step is created"() {
		when:
		Step step = etlJobConfiguration.stepCreateLocations()

		then: 'StepBuilder is retrieved'
		1 * stepBuilderFactoryMock.get(StepName.CREATE_LOCATIONS) >> stepBuilderMock

		then: 'commit interval is configured'
		1 * stepsPropertiesMock.createLocations >> stepProperties
		1 * stepProperties.commitInterval >> STEP_SIZE
		1 * stepBuilderMock.chunk(STEP_SIZE) >> simpleStepBuilderMock

		then: 'beans are retrieved from application context, then passed to builder'
		1 * applicationContextMock.getBean(LocationReader) >> itemReaderMock
		1 * simpleStepBuilderMock.reader(itemReaderMock) >> simpleStepBuilderMock
		1 * applicationContextMock.getBean(LocationProcessor) >> itemProcessorMock
		1 * simpleStepBuilderMock.processor(itemProcessorMock) >> simpleStepBuilderMock
		1 * applicationContextMock.getBean(LocationWriter) >> itemWriterMock
		1 * simpleStepBuilderMock.writer(itemWriterMock) >> simpleStepBuilderMock
		1 * applicationContextMock.getBean(CommonStepExecutionListener) >> stepExecutionListenerMock
		1 * simpleStepBuilderMock.listener(stepExecutionListenerMock) >> simpleStepBuilderMock

		then: 'step is configured to run only once'
		1 * simpleStepBuilderMock.startLimit(1) >> simpleStepBuilderMock
		1 * simpleStepBuilderMock.allowStartIfComplete(false) >> simpleStepBuilderMock

		then: 'tasklet step is returned'
		1 * simpleStepBuilderMock.build() >> taskletStepMock

		then: 'step is being returned'
		step == taskletStepMock
	}

	void "CREATE_BOOK_SERIES step is created"() {
		when:
		Step step = etlJobConfiguration.stepCreateBookSeries()

		then: 'StepBuilder is retrieved'
		1 * stepBuilderFactoryMock.get(StepName.CREATE_BOOK_SERIES) >> stepBuilderMock

		then: 'commit interval is configured'
		1 * stepsPropertiesMock.createBookSeries >> stepProperties
		1 * stepProperties.commitInterval >> STEP_SIZE
		1 * stepBuilderMock.chunk(STEP_SIZE) >> simpleStepBuilderMock

		then: 'beans are retrieved from application context, then passed to builder'
		1 * applicationContextMock.getBean(BookSeriesReader) >> itemReaderMock
		1 * simpleStepBuilderMock.reader(itemReaderMock) >> simpleStepBuilderMock
		1 * applicationContextMock.getBean(BookSeriesProcessor) >> itemProcessorMock
		1 * simpleStepBuilderMock.processor(itemProcessorMock) >> simpleStepBuilderMock
		1 * applicationContextMock.getBean(BookSeriesWriter) >> itemWriterMock
		1 * simpleStepBuilderMock.writer(itemWriterMock) >> simpleStepBuilderMock
		1 * applicationContextMock.getBean(CommonStepExecutionListener) >> stepExecutionListenerMock
		1 * simpleStepBuilderMock.listener(stepExecutionListenerMock) >> simpleStepBuilderMock

		then: 'step is configured to run only once'
		1 * simpleStepBuilderMock.startLimit(1) >> simpleStepBuilderMock
		1 * simpleStepBuilderMock.allowStartIfComplete(false) >> simpleStepBuilderMock

		then: 'tasklet step is returned'
		1 * simpleStepBuilderMock.build() >> taskletStepMock

		then: 'step is being returned'
		step == taskletStepMock
	}

	void "LINK_BOOK_SERIES step is created"() {
		when:
		Step step = etlJobConfiguration.stepLinkBookSeries()

		then: 'StepBuilder is retrieved'
		1 * stepBuilderFactoryMock.get(StepName.LINK_BOOK_SERIES) >> stepBuilderMock

		then: 'commit interval is configured'
		1 * stepsPropertiesMock.linkBookSeries >> stepProperties
		1 * stepProperties.commitInterval >> STEP_SIZE
		1 * stepBuilderMock.chunk(STEP_SIZE) >> simpleStepBuilderMock

		then: 'beans are retrieved from application context, then passed to builder'
		1 * applicationContextMock.getBean(BookSeriesLinkReader) >> itemReaderMock
		1 * simpleStepBuilderMock.reader(itemReaderMock) >> simpleStepBuilderMock
		1 * applicationContextMock.getBean(BookSeriesLinkProcessor) >> itemProcessorMock
		1 * simpleStepBuilderMock.processor(itemProcessorMock) >> simpleStepBuilderMock
		1 * applicationContextMock.getBean(BookSeriesWriter) >> itemWriterMock
		1 * simpleStepBuilderMock.writer(itemWriterMock) >> simpleStepBuilderMock
		1 * applicationContextMock.getBean(CommonStepExecutionListener) >> stepExecutionListenerMock
		1 * simpleStepBuilderMock.listener(stepExecutionListenerMock) >> simpleStepBuilderMock

		then: 'step is configured to run only once'
		1 * simpleStepBuilderMock.startLimit(1) >> simpleStepBuilderMock
		1 * simpleStepBuilderMock.allowStartIfComplete(false) >> simpleStepBuilderMock

		then: 'tasklet step is returned'
		1 * simpleStepBuilderMock.build() >> taskletStepMock

		then: 'step is being returned'
		step == taskletStepMock
	}

	void "CREATE_BOOKS step is created"() {
		when:
		Step step = etlJobConfiguration.stepCreateBooks()

		then: 'StepBuilder is retrieved'
		1 * stepBuilderFactoryMock.get(StepName.CREATE_BOOKS) >> stepBuilderMock

		then: 'commit interval is configured'
		1 * stepsPropertiesMock.createBooks >> stepProperties
		1 * stepProperties.commitInterval >> STEP_SIZE
		1 * stepBuilderMock.chunk(STEP_SIZE) >> simpleStepBuilderMock

		then: 'beans are retrieved from application context, then passed to builder'
		1 * applicationContextMock.getBean(BookReader) >> itemReaderMock
		1 * simpleStepBuilderMock.reader(itemReaderMock) >> simpleStepBuilderMock
		1 * applicationContextMock.getBean(BookProcessor) >> itemProcessorMock
		1 * simpleStepBuilderMock.processor(itemProcessorMock) >> simpleStepBuilderMock
		1 * applicationContextMock.getBean(BookWriter) >> itemWriterMock
		1 * simpleStepBuilderMock.writer(itemWriterMock) >> simpleStepBuilderMock
		1 * applicationContextMock.getBean(CommonStepExecutionListener) >> stepExecutionListenerMock
		1 * simpleStepBuilderMock.listener(stepExecutionListenerMock) >> simpleStepBuilderMock

		then: 'step is configured to run only once'
		1 * simpleStepBuilderMock.startLimit(1) >> simpleStepBuilderMock
		1 * simpleStepBuilderMock.allowStartIfComplete(false) >> simpleStepBuilderMock

		then: 'tasklet step is returned'
		1 * simpleStepBuilderMock.build() >> taskletStepMock

		then: 'step is being returned'
		step == taskletStepMock
	}

	void "CREATE_BOOK_COLLECTIONS step is created"() {
		when:
		Step step = etlJobConfiguration.stepCreateBookCollections()

		then: 'StepBuilder is retrieved'
		1 * stepBuilderFactoryMock.get(StepName.CREATE_BOOK_COLLECTIONS) >> stepBuilderMock

		then: 'commit interval is configured'
		1 * stepsPropertiesMock.createBookCollections >> stepProperties
		1 * stepProperties.commitInterval >> STEP_SIZE
		1 * stepBuilderMock.chunk(STEP_SIZE) >> simpleStepBuilderMock

		then: 'beans are retrieved from application context, then passed to builder'
		1 * applicationContextMock.getBean(BookCollectionReader) >> itemReaderMock
		1 * simpleStepBuilderMock.reader(itemReaderMock) >> simpleStepBuilderMock
		1 * applicationContextMock.getBean(BookCollectionProcessor) >> itemProcessorMock
		1 * simpleStepBuilderMock.processor(itemProcessorMock) >> simpleStepBuilderMock
		1 * applicationContextMock.getBean(BookCollectionWriter) >> itemWriterMock
		1 * simpleStepBuilderMock.writer(itemWriterMock) >> simpleStepBuilderMock
		1 * applicationContextMock.getBean(CommonStepExecutionListener) >> stepExecutionListenerMock
		1 * simpleStepBuilderMock.listener(stepExecutionListenerMock) >> simpleStepBuilderMock

		then: 'step is configured to run only once'
		1 * simpleStepBuilderMock.startLimit(1) >> simpleStepBuilderMock
		1 * simpleStepBuilderMock.allowStartIfComplete(false) >> simpleStepBuilderMock

		then: 'tasklet step is returned'
		1 * simpleStepBuilderMock.build() >> taskletStepMock

		then: 'step is being returned'
		step == taskletStepMock
	}

	void "CREATE_MAGAZINES step is created"() {
		when:
		Step step = etlJobConfiguration.stepCreateMagazines()

		then: 'StepBuilder is retrieved'
		1 * stepBuilderFactoryMock.get(StepName.CREATE_MAGAZINES) >> stepBuilderMock

		then: 'commit interval is configured'
		1 * stepsPropertiesMock.createMagazines >> stepProperties
		1 * stepProperties.commitInterval >> STEP_SIZE
		1 * stepBuilderMock.chunk(STEP_SIZE) >> simpleStepBuilderMock

		then: 'beans are retrieved from application context, then passed to builder'
		1 * applicationContextMock.getBean(MagazineReader) >> itemReaderMock
		1 * simpleStepBuilderMock.reader(itemReaderMock) >> simpleStepBuilderMock
		1 * applicationContextMock.getBean(MagazineProcessor) >> itemProcessorMock
		1 * simpleStepBuilderMock.processor(itemProcessorMock) >> simpleStepBuilderMock
		1 * applicationContextMock.getBean(MagazineWriter) >> itemWriterMock
		1 * simpleStepBuilderMock.writer(itemWriterMock) >> simpleStepBuilderMock
		1 * applicationContextMock.getBean(CommonStepExecutionListener) >> stepExecutionListenerMock
		1 * simpleStepBuilderMock.listener(stepExecutionListenerMock) >> simpleStepBuilderMock

		then: 'step is configured to run only once'
		1 * simpleStepBuilderMock.startLimit(1) >> simpleStepBuilderMock
		1 * simpleStepBuilderMock.allowStartIfComplete(false) >> simpleStepBuilderMock

		then: 'tasklet step is returned'
		1 * simpleStepBuilderMock.build() >> taskletStepMock

		then: 'step is being returned'
		step == taskletStepMock
	}

	void "CREATE_MAGAZINE_SERIES step is created"() {
		when:
		Step step = etlJobConfiguration.stepCreateMagazineSeries()

		then: 'StepBuilder is retrieved'
		1 * stepBuilderFactoryMock.get(StepName.CREATE_MAGAZINE_SERIES) >> stepBuilderMock

		then: 'commit interval is configured'
		1 * stepsPropertiesMock.createMagazineSeries >> stepProperties
		1 * stepProperties.commitInterval >> STEP_SIZE
		1 * stepBuilderMock.chunk(STEP_SIZE) >> simpleStepBuilderMock

		then: 'beans are retrieved from application context, then passed to builder'
		1 * applicationContextMock.getBean(MagazineSeriesReader) >> itemReaderMock
		1 * simpleStepBuilderMock.reader(itemReaderMock) >> simpleStepBuilderMock
		1 * applicationContextMock.getBean(MagazineSeriesProcessor) >> itemProcessorMock
		1 * simpleStepBuilderMock.processor(itemProcessorMock) >> simpleStepBuilderMock
		1 * applicationContextMock.getBean(MagazineSeriesWriter) >> itemWriterMock
		1 * simpleStepBuilderMock.writer(itemWriterMock) >> simpleStepBuilderMock
		1 * applicationContextMock.getBean(CommonStepExecutionListener) >> stepExecutionListenerMock
		1 * simpleStepBuilderMock.listener(stepExecutionListenerMock) >> simpleStepBuilderMock

		then: 'step is configured to run only once'
		1 * simpleStepBuilderMock.startLimit(1) >> simpleStepBuilderMock
		1 * simpleStepBuilderMock.allowStartIfComplete(false) >> simpleStepBuilderMock

		then: 'tasklet step is returned'
		1 * simpleStepBuilderMock.build() >> taskletStepMock

		then: 'step is being returned'
		step == taskletStepMock
	}

	void "CREATE_LITERATURE step is created"() {
		when:
		Step step = etlJobConfiguration.stepCreateLiterature()

		then: 'StepBuilder is retrieved'
		1 * stepBuilderFactoryMock.get(StepName.CREATE_LITERATURE) >> stepBuilderMock

		then: 'commit interval is configured'
		1 * stepsPropertiesMock.createLiterature >> stepProperties
		1 * stepProperties.commitInterval >> STEP_SIZE
		1 * stepBuilderMock.chunk(STEP_SIZE) >> simpleStepBuilderMock

		then: 'beans are retrieved from application context, then passed to builder'
		1 * applicationContextMock.getBean(LiteratureReader) >> itemReaderMock
		1 * simpleStepBuilderMock.reader(itemReaderMock) >> simpleStepBuilderMock
		1 * applicationContextMock.getBean(LiteratureProcessor) >> itemProcessorMock
		1 * simpleStepBuilderMock.processor(itemProcessorMock) >> simpleStepBuilderMock
		1 * applicationContextMock.getBean(LiteratureWriter) >> itemWriterMock
		1 * simpleStepBuilderMock.writer(itemWriterMock) >> simpleStepBuilderMock
		1 * applicationContextMock.getBean(CommonStepExecutionListener) >> stepExecutionListenerMock
		1 * simpleStepBuilderMock.listener(stepExecutionListenerMock) >> simpleStepBuilderMock

		then: 'step is configured to run only once'
		1 * simpleStepBuilderMock.startLimit(1) >> simpleStepBuilderMock
		1 * simpleStepBuilderMock.allowStartIfComplete(false) >> simpleStepBuilderMock

		then: 'tasklet step is returned'
		1 * simpleStepBuilderMock.build() >> taskletStepMock

		then: 'step is being returned'
		step == taskletStepMock
	}

	void "CREATE_VIDEO_RELEASES step is created"() {
		when:
		Step step = etlJobConfiguration.stepCreateVideoReleases()

		then: 'StepBuilder is retrieved'
		1 * stepBuilderFactoryMock.get(StepName.CREATE_VIDEO_RELEASES) >> stepBuilderMock

		then: 'commit interval is configured'
		1 * stepsPropertiesMock.createVideoReleases >> stepProperties
		1 * stepProperties.commitInterval >> STEP_SIZE
		1 * stepBuilderMock.chunk(STEP_SIZE) >> simpleStepBuilderMock

		then: 'beans are retrieved from application context, then passed to builder'
		1 * applicationContextMock.getBean(VideoReleaseReader) >> itemReaderMock
		1 * simpleStepBuilderMock.reader(itemReaderMock) >> simpleStepBuilderMock
		1 * applicationContextMock.getBean(VideoReleaseProcessor) >> itemProcessorMock
		1 * simpleStepBuilderMock.processor(itemProcessorMock) >> simpleStepBuilderMock
		1 * applicationContextMock.getBean(VideoReleaseWriter) >> itemWriterMock
		1 * simpleStepBuilderMock.writer(itemWriterMock) >> simpleStepBuilderMock
		1 * applicationContextMock.getBean(CommonStepExecutionListener) >> stepExecutionListenerMock
		1 * simpleStepBuilderMock.listener(stepExecutionListenerMock) >> simpleStepBuilderMock

		then: 'step is configured to run only once'
		1 * simpleStepBuilderMock.startLimit(1) >> simpleStepBuilderMock
		1 * simpleStepBuilderMock.allowStartIfComplete(false) >> simpleStepBuilderMock

		then: 'tasklet step is returned'
		1 * simpleStepBuilderMock.build() >> taskletStepMock

		then: 'step is being returned'
		step == taskletStepMock
	}

	void "CREATE_TRADING_CARDS step is created"() {
		when:
		Step step = etlJobConfiguration.stepCreateTradingCards()

		then: 'StepBuilder is retrieved'
		1 * stepBuilderFactoryMock.get(StepName.CREATE_TRADING_CARDS) >> stepBuilderMock

		then: 'commit interval is configured'
		1 * stepsPropertiesMock.createTradingCards >> stepProperties
		1 * stepProperties.commitInterval >> STEP_SIZE
		1 * stepBuilderMock.chunk(STEP_SIZE) >> simpleStepBuilderMock

		then: 'beans are retrieved from application context, then passed to builder'
		1 * applicationContextMock.getBean(TradingCardSetReader) >> itemReaderMock
		1 * simpleStepBuilderMock.reader(itemReaderMock) >> simpleStepBuilderMock
		1 * applicationContextMock.getBean(TradingCardSetProcessor) >> itemProcessorMock
		1 * simpleStepBuilderMock.processor(itemProcessorMock) >> simpleStepBuilderMock
		1 * applicationContextMock.getBean(TradingCardSetWriter) >> itemWriterMock
		1 * simpleStepBuilderMock.writer(itemWriterMock) >> simpleStepBuilderMock
		1 * applicationContextMock.getBean(CommonStepExecutionListener) >> stepExecutionListenerMock
		1 * simpleStepBuilderMock.listener(stepExecutionListenerMock) >> simpleStepBuilderMock

		then: 'step is configured to run only once'
		1 * simpleStepBuilderMock.startLimit(1) >> simpleStepBuilderMock
		1 * simpleStepBuilderMock.allowStartIfComplete(false) >> simpleStepBuilderMock

		then: 'tasklet step is returned'
		1 * simpleStepBuilderMock.build() >> taskletStepMock

		then: 'step is being returned'
		step == taskletStepMock
	}

	void "CREATE_VIDEO_GAMES step is created"() {
		when:
		Step step = etlJobConfiguration.stepCreateVideoGames()

		then: 'StepBuilder is retrieved'
		1 * stepBuilderFactoryMock.get(StepName.CREATE_VIDEO_GAMES) >> stepBuilderMock

		then: 'commit interval is configured'
		1 * stepsPropertiesMock.createVideoGames >> stepProperties
		1 * stepProperties.commitInterval >> STEP_SIZE
		1 * stepBuilderMock.chunk(STEP_SIZE) >> simpleStepBuilderMock

		then: 'beans are retrieved from application context, then passed to builder'
		1 * applicationContextMock.getBean(VideoGameReader) >> itemReaderMock
		1 * simpleStepBuilderMock.reader(itemReaderMock) >> simpleStepBuilderMock
		1 * applicationContextMock.getBean(VideoGameProcessor) >> itemProcessorMock
		1 * simpleStepBuilderMock.processor(itemProcessorMock) >> simpleStepBuilderMock
		1 * applicationContextMock.getBean(VideoGameWriter) >> itemWriterMock
		1 * simpleStepBuilderMock.writer(itemWriterMock) >> simpleStepBuilderMock
		1 * applicationContextMock.getBean(CommonStepExecutionListener) >> stepExecutionListenerMock
		1 * simpleStepBuilderMock.listener(stepExecutionListenerMock) >> simpleStepBuilderMock

		then: 'step is configured to run only once'
		1 * simpleStepBuilderMock.startLimit(1) >> simpleStepBuilderMock
		1 * simpleStepBuilderMock.allowStartIfComplete(false) >> simpleStepBuilderMock

		then: 'tasklet step is returned'
		1 * simpleStepBuilderMock.build() >> taskletStepMock

		then: 'step is being returned'
		step == taskletStepMock
	}

}
