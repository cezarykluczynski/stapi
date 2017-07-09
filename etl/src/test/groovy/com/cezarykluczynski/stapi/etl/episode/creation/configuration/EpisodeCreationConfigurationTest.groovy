package com.cezarykluczynski.stapi.etl.episode.creation.configuration

import com.cezarykluczynski.stapi.etl.common.configuration.AbstractCreationConfigurationTest
import com.cezarykluczynski.stapi.etl.configuration.job.service.StepCompletenessDecider
import com.cezarykluczynski.stapi.etl.episode.creation.processor.EpisodeReader
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitles
import com.cezarykluczynski.stapi.etl.util.constant.JobName
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.sources.mediawiki.api.CategoryApi
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource

class EpisodeCreationConfigurationTest extends AbstractCreationConfigurationTest {

	private static final String TITLE_EPISODES = 'TITLE_EPISODES'

	private CategoryApi categoryApiMock

	private StepCompletenessDecider jobCompletenessDeciderMock

	private EpisodeCreationConfiguration episodeCreationConfiguration

	void setup() {
		categoryApiMock = Mock()
		jobCompletenessDeciderMock = Mock()
		episodeCreationConfiguration = new EpisodeCreationConfiguration(
				categoryApi: categoryApiMock,
				stepCompletenessDecider: jobCompletenessDeciderMock)
	}

	void "EpisodeReader is created with all pages when step is not completed"() {
		when:
		EpisodeReader episodeReader = episodeCreationConfiguration.episodeReader()
		List<String> categoryHeaderTitleList = pageHeaderReaderToList(episodeReader)

		then:
		1 * jobCompletenessDeciderMock.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_EPISODES) >> false
		1 * categoryApiMock.getPages(CategoryTitles.EPISODES, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_EPISODES)
		0 * _
		categoryHeaderTitleList.contains TITLE_EPISODES
	}

	void "EpisodeReader is created with no pages when step is completed"() {
		when:
		EpisodeReader episodeReader = episodeCreationConfiguration.episodeReader()
		List<String> categoryHeaderTitleList = pageHeaderReaderToList(episodeReader)

		then:
		1 * jobCompletenessDeciderMock.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_EPISODES) >> true
		0 * _
		categoryHeaderTitleList.empty
	}

}
