package com.cezarykluczynski.stapi.etl.series.creation.processor

import com.cezarykluczynski.stapi.util.constants.CategoryName
import com.cezarykluczynski.stapi.wiki.api.CategoryApi
import com.cezarykluczynski.stapi.wiki.dto.PageHeader
import com.google.common.collect.Lists
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
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

}
