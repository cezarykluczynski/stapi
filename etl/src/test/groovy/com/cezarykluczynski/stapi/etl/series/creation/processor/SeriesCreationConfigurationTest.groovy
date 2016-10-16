package com.cezarykluczynski.stapi.etl.series.creation.processor

import com.cezarykluczynski.stapi.etl.common.listener.CommonStepExecutionListener
import com.cezarykluczynski.stapi.etl.util.Steps
import com.cezarykluczynski.stapi.util.constants.CategoryName
import com.cezarykluczynski.stapi.wiki.api.CategoryApi
import com.cezarykluczynski.stapi.wiki.dto.PageHeader
import com.google.common.collect.Lists
import org.springframework.batch.core.Step
import org.springframework.batch.core.StepExecutionListener
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.step.builder.SimpleStepBuilder
import org.springframework.batch.core.step.builder.StepBuilder
import org.springframework.batch.core.step.tasklet.TaskletStep
import org.springframework.batch.item.ItemProcessor
import org.springframework.batch.item.ItemReader
import org.springframework.batch.item.ItemWriter
import org.springframework.context.ApplicationContext
import spock.lang.Specification

class SeriesCreationConfigurationTest extends Specification {

	private static final String TITLE = 'TITLE'

	private CategoryApi categoryApiMock

	private StepBuilderFactory stepBuilderFactoryMock

	private ApplicationContext applicationContextMock

	private SeriesCreationConfiguration seriesCreationConfiguration

	def setup() {
		categoryApiMock = Mock(CategoryApi)
		stepBuilderFactoryMock = Mock(StepBuilderFactory)
		applicationContextMock = Mock(ApplicationContext)
		seriesCreationConfiguration = new SeriesCreationConfiguration(
				categoryApi: categoryApiMock,
				stepBuilderFactory: stepBuilderFactoryMock,
				applicationContext: applicationContextMock)
	}

	def "SeriesReader is created"() {
		given:
		List<PageHeader> pageHeaderList = Lists.newArrayList(PageHeader.builder().title(TITLE).build())

		when:
		SeriesReader seriesReader = seriesCreationConfiguration.seriesReader()

		then:
		1 * categoryApiMock.getPages(CategoryName.STAR_TREK_SERIES) >> pageHeaderList
		seriesReader.read().title == TITLE
		seriesReader.read() == null
	}

	def "Step is created"() {
		given:
		StepBuilder stepBuilderMock = Mock(StepBuilder)
		SimpleStepBuilder simpleStepBuilderMock = Mock(SimpleStepBuilder)
		ItemReader itemReaderMock = Mock(ItemReader)
		ItemProcessor itemProcessorMock = Mock(ItemProcessor)
		ItemWriter itemWriterMock = Mock(ItemWriter)
		StepExecutionListener stepExecutionListenerMock = Mock(StepExecutionListener)
		TaskletStep taskletStepMock = Mock(TaskletStep)

		when:
		Step step = seriesCreationConfiguration.step()

		then: 'StepBuilder is retrieved'
		1 * stepBuilderMock.chunk(*_) >> simpleStepBuilderMock
		1 * stepBuilderFactoryMock.get(Steps.STEP_001_CREATE_SERIES) >> stepBuilderMock

		then: 'beans are retrieved from application context, then passed to builder'
		1 * applicationContextMock.getBean(SeriesReader.class) >> itemReaderMock
		1 * simpleStepBuilderMock.reader(itemReaderMock) >> simpleStepBuilderMock
		1 * applicationContextMock.getBean(SeriesProcessor.class) >> itemProcessorMock
		1 * simpleStepBuilderMock.processor(itemProcessorMock) >> simpleStepBuilderMock
		1 * applicationContextMock.getBean(SeriesWriter.class) >> itemWriterMock
		1 * simpleStepBuilderMock.writer(itemWriterMock) >> simpleStepBuilderMock
		1 * applicationContextMock.getBean(CommonStepExecutionListener.class) >> stepExecutionListenerMock
		1 * simpleStepBuilderMock.listener(*_) >> simpleStepBuilderMock

		then: 'step is configured to run only once'
		1 * simpleStepBuilderMock.startLimit(1) >> simpleStepBuilderMock
		1 * simpleStepBuilderMock.allowStartIfComplete(false) >> simpleStepBuilderMock

		then: 'tasklet step is returned'
		1 * simpleStepBuilderMock.build() >> taskletStepMock

		then: 'step is being returned'
		step == taskletStepMock
	}

}