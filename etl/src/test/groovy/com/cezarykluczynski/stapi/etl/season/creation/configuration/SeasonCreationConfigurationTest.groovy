package com.cezarykluczynski.stapi.etl.season.creation.configuration

import com.cezarykluczynski.stapi.etl.common.configuration.AbstractCreationConfigurationTest
import com.cezarykluczynski.stapi.etl.configuration.job.service.StepCompletenessDecider
import com.cezarykluczynski.stapi.etl.season.creation.processor.SeasonReader
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle
import com.cezarykluczynski.stapi.etl.util.constant.JobName
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.sources.mediawiki.api.CategoryApi
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader
import com.google.common.collect.Lists

class SeasonCreationConfigurationTest extends AbstractCreationConfigurationTest {

	private static final String TITLE = 'TITLE'

	private CategoryApi categoryApiMock

	private StepCompletenessDecider jobCompletenessDeciderMock

	private SeasonCreationConfiguration seasonCreationConfiguration

	void setup() {
		categoryApiMock = Mock()
		jobCompletenessDeciderMock = Mock()
		seasonCreationConfiguration = new SeasonCreationConfiguration(
				categoryApi: categoryApiMock,
				stepCompletenessDecider: jobCompletenessDeciderMock)
	}

	void "SeasonReader is created with all pages when step is not completed"() {
		given:
		List<PageHeader> pageHeaderList = Lists.newArrayList(new PageHeader(title: TITLE))

		when:
		SeasonReader seasonReader = seasonCreationConfiguration.seasonReader()

		then:
		1 * jobCompletenessDeciderMock.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_SEASONS) >> false
		1 * categoryApiMock.getPages(CategoryTitle.STAR_TREK_SEASONS, MediaWikiSource.MEMORY_ALPHA_EN) >> pageHeaderList
		0 * _
		seasonReader.read().title == TITLE
		seasonReader.read() == null
	}

	void "SeasonReader is created with no pages when step is completed"() {
		when:
		SeasonReader seasonReader = seasonCreationConfiguration.seasonReader()
		List<String> categoryHeaderTitleList = pageHeaderReaderToList(seasonReader)

		then:
		1 * jobCompletenessDeciderMock.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_SEASONS) >> true
		0 * _
		categoryHeaderTitleList.empty
	}

}
