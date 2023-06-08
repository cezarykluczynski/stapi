package com.cezarykluczynski.stapi.etl.season.creation.configuration

import com.cezarykluczynski.stapi.etl.common.configuration.AbstractCreationConfigurationTest
import com.cezarykluczynski.stapi.etl.configuration.job.service.StepCompletenessDecider
import com.cezarykluczynski.stapi.etl.mediawiki.api.PageApi
import com.cezarykluczynski.stapi.etl.season.creation.processor.SeasonReader
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle
import com.cezarykluczynski.stapi.etl.util.constant.JobName
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.etl.mediawiki.api.CategoryApi
import com.cezarykluczynski.stapi.etl.mediawiki.api.enums.MediaWikiSource
import com.cezarykluczynski.stapi.etl.mediawiki.dto.PageHeader
import com.cezarykluczynski.stapi.util.constant.PageTitle
import com.google.common.collect.Lists

class SeasonCreationConfigurationTest extends AbstractCreationConfigurationTest {

	private static final String TITLE = 'TITLE'
	private static final String TITLE_2 = 'TITLE_2'
	private static final String TITLE_3 = 'TITLE_3'

	private CategoryApi categoryApiMock

	private PageApi pageApiMock

	private StepCompletenessDecider jobCompletenessDeciderMock

	private SeasonCreationConfiguration seasonCreationConfiguration

	void setup() {
		categoryApiMock = Mock()
		pageApiMock = Mock()
		jobCompletenessDeciderMock = Mock()
		seasonCreationConfiguration = new SeasonCreationConfiguration(
				categoryApi: categoryApiMock,
				pageApi: pageApiMock,
				stepCompletenessDecider: jobCompletenessDeciderMock)
	}

	void "SeasonReader is created with all pages when step is not completed"() {
		given:
		List<PageHeader> pageHeaderList1 = Lists.newArrayList(new PageHeader(title: TITLE))
		List<PageHeader> pageHeaderList2 = Lists.newArrayList(new PageHeader(title: TITLE_2))
		List<PageHeader> pageHeaderList3 = Lists.newArrayList(new PageHeader(title: TITLE_3))

		when:
		SeasonReader seasonReader = seasonCreationConfiguration.seasonReader()

		then:
		1 * jobCompletenessDeciderMock.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_SEASONS) >> false
		1 * categoryApiMock.getPages(CategoryTitle.STAR_TREK_SEASONS, MediaWikiSource.MEMORY_ALPHA_EN) >> pageHeaderList1
		1 * categoryApiMock.getPages(CategoryTitle.STAR_TREK_COMPANION_SEASONS, MediaWikiSource.MEMORY_ALPHA_EN) >> pageHeaderList2
		1 * pageApiMock.getPageHeaders(PageTitle.ST_SEASONS_LIST, MediaWikiSource.MEMORY_ALPHA_EN) >> pageHeaderList3
		0 * _
		seasonReader.read().title == TITLE
		seasonReader.read().title == TITLE_2
		seasonReader.read().title == TITLE_3
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
