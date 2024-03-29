package com.cezarykluczynski.stapi.etl.series.creation.configuration

import com.cezarykluczynski.stapi.etl.common.configuration.AbstractCreationConfigurationTest
import com.cezarykluczynski.stapi.etl.configuration.job.service.StepCompletenessDecider
import com.cezarykluczynski.stapi.etl.series.creation.processor.SeriesReader
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle
import com.cezarykluczynski.stapi.etl.util.constant.JobName
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.etl.mediawiki.api.CategoryApi
import com.cezarykluczynski.stapi.etl.mediawiki.api.enums.MediaWikiSource
import com.cezarykluczynski.stapi.etl.mediawiki.dto.PageHeader
import com.google.common.collect.Lists

class SeriesCreationConfigurationTest extends AbstractCreationConfigurationTest {

	private static final String TITLE = 'TITLE'
	private static final String TITLE_2 = 'TITLE_2'

	private CategoryApi categoryApiMock

	private StepCompletenessDecider jobCompletenessDeciderMock

	private SeriesCreationConfiguration seriesCreationConfiguration

	void setup() {
		categoryApiMock = Mock()
		jobCompletenessDeciderMock = Mock()
		seriesCreationConfiguration = new SeriesCreationConfiguration(
				categoryApi: categoryApiMock,
				stepCompletenessDecider: jobCompletenessDeciderMock)
	}

	void "SeriesReader is created with all pages when step is not completed"() {
		given:
		List<PageHeader> pageHeaderList1 = Lists.newArrayList(new PageHeader(title: TITLE))
		List<PageHeader> pageHeaderList2 = Lists.newArrayList(new PageHeader(title: TITLE_2))

		when:
		SeriesReader seriesReader = seriesCreationConfiguration.seriesReader()

		then:
		1 * jobCompletenessDeciderMock.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_SERIES) >> false
		1 * categoryApiMock.getPages(CategoryTitle.STAR_TREK_SERIES, MediaWikiSource.MEMORY_ALPHA_EN) >> pageHeaderList1
		1 * categoryApiMock.getPages(CategoryTitle.STAR_TREK_COMPANION_SERIES, MediaWikiSource.MEMORY_ALPHA_EN) >> pageHeaderList2
		0 * _
		seriesReader.read().title == TITLE
		seriesReader.read().title == TITLE_2
		seriesReader.read() == null
	}

	void "SeriesReader is created with no pages when step is completed"() {
		when:
		SeriesReader seriesReader = seriesCreationConfiguration.seriesReader()
		List<String> categoryHeaderTitleList = pageHeaderReaderToList(seriesReader)

		then:
		1 * jobCompletenessDeciderMock.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_SERIES) >> true
		0 * _
		categoryHeaderTitleList.empty
	}

}
