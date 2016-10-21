package com.cezarykluczynski.stapi.etl.performer.creation.processor

import com.cezarykluczynski.stapi.util.constants.CategoryName
import com.cezarykluczynski.stapi.wiki.api.CategoryApi
import com.cezarykluczynski.stapi.wiki.dto.PageHeader
import com.google.common.collect.Lists
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.context.ApplicationContext
import spock.lang.Specification

class PerformerCreationConfigurationTest extends Specification {

	private static final String TITLE = 'TITLE'

	private CategoryApi categoryApiMock

	private StepBuilderFactory stepBuilderFactoryMock

	private ApplicationContext applicationContextMock

	private PerformerCreationConfiguration performerCreationConfiguration

	def setup() {
		categoryApiMock = Mock(CategoryApi)
		stepBuilderFactoryMock = Mock(StepBuilderFactory)
		applicationContextMock = Mock(ApplicationContext)
		performerCreationConfiguration = new PerformerCreationConfiguration(
				categoryApi: categoryApiMock,
				stepBuilderFactory: stepBuilderFactoryMock,
				applicationContext: applicationContextMock)
	}

	def "PerformerReader is created"() {
		given:
		List<PageHeader> pageHeaderList = Lists.newArrayList(PageHeader.builder().title(TITLE).build())

		when:
		PerformerReader performerReader = performerCreationConfiguration.performerReader()

		then:
		1 * categoryApiMock.getPages(CategoryName.PERFORMERS) >> pageHeaderList
		performerReader.read().title == TITLE
		performerReader.read() == null
	}

}
