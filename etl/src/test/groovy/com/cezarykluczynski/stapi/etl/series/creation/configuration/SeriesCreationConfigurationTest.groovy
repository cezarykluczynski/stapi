package com.cezarykluczynski.stapi.etl.series.creation.configuration

import com.cezarykluczynski.stapi.etl.common.configuration.AbstractCreationConfigurationTest
import com.cezarykluczynski.stapi.etl.configuration.job.service.StepCompletenessDecider
import com.cezarykluczynski.stapi.etl.series.creation.processor.SeriesReader
import com.cezarykluczynski.stapi.etl.util.constant.CategoryName
import com.cezarykluczynski.stapi.etl.util.constant.JobName
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.sources.mediawiki.api.CategoryApi
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader
import com.google.common.collect.Lists

class SeriesCreationConfigurationTest extends AbstractCreationConfigurationTest {

	private static final String TITLE = 'TITLE'

	private CategoryApi categoryApiMock

	private StepCompletenessDecider jobCompletenessDeciderMock

	private SeriesCreationConfiguration seriesCreationConfiguration

	def setup() {
		categoryApiMock = Mock(CategoryApi)
		jobCompletenessDeciderMock = Mock(StepCompletenessDecider)
		seriesCreationConfiguration = new SeriesCreationConfiguration(
				categoryApi: categoryApiMock,
				stepCompletenessDecider: jobCompletenessDeciderMock)
	}

	def "SeriesReader is created with all pages when step is not completed"() {
		given:
		List<PageHeader> pageHeaderList = Lists.newArrayList(PageHeader.builder().title(TITLE).build())

		when:
		SeriesReader seriesReader = seriesCreationConfiguration.seriesReader()

		then:
		1 * jobCompletenessDeciderMock.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_SERIES) >> false
		1 * categoryApiMock.getPages(CategoryName.STAR_TREK_SERIES, MediaWikiSource.MEMORY_ALPHA_EN) >> pageHeaderList
		0 * _
		seriesReader.read().title == TITLE
		seriesReader.read() == null
	}

	def "SeriesReader is created with no pages when step is completed"() {
		when:
		SeriesReader seriesReader = seriesCreationConfiguration.seriesReader()
		List<String> categoryHeaderTitleList = readerToList(seriesReader)

		then:
		1 * jobCompletenessDeciderMock.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_SERIES) >> true
		0 * _
		categoryHeaderTitleList.empty
	}

}
