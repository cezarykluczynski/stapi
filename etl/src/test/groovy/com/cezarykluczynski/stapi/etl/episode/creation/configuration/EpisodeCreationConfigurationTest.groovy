package com.cezarykluczynski.stapi.etl.episode.creation.configuration

import com.cezarykluczynski.stapi.etl.common.configuration.AbstractCreationConfigurationTest
import com.cezarykluczynski.stapi.etl.configuration.job.service.StepCompletenessDecider
import com.cezarykluczynski.stapi.etl.episode.creation.processor.EpisodeReader
import com.cezarykluczynski.stapi.etl.util.constant.CategoryName
import com.cezarykluczynski.stapi.etl.util.constant.JobName
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.sources.mediawiki.api.CategoryApi
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource

class EpisodeCreationConfigurationTest extends AbstractCreationConfigurationTest {

	private static final String TITLE_TOS_EPISODES = 'TITLE_TOS_EPISODES'
	private static final String TITLE_TAS_EPISODES = 'TITLE_TAS_EPISODES'
	private static final String TITLE_TNG_EPISODES = 'TITLE_TNG_EPISODES'
	private static final String TITLE_DS9_EPISODES = 'TITLE_DS9_EPISODES'
	private static final String TITLE_VOY_EPISODES = 'TITLE_VOY_EPISODES'
	private static final String TITLE_ENT_EPISODES = 'TITLE_ENT_EPISODES'
	private static final String TITLE_DIS_EPISODES = 'TITLE_DIS_EPISODES'

	private CategoryApi categoryApiMock

	private StepCompletenessDecider jobCompletenessDeciderMock

	private EpisodeCreationConfiguration episodeCreationConfiguration

	void setup() {
		categoryApiMock = Mock(CategoryApi)
		jobCompletenessDeciderMock = Mock(StepCompletenessDecider)
		episodeCreationConfiguration = new EpisodeCreationConfiguration(
				categoryApi: categoryApiMock,
				stepCompletenessDecider: jobCompletenessDeciderMock)
	}

	void "EpisodeReader is created with all pages when step is not completed"() {
		when:
		EpisodeReader episodeReader = episodeCreationConfiguration.episodeReader()
		List<String> categoryHeaderTitleList = readerToList(episodeReader)

		then:
		1 * jobCompletenessDeciderMock.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_EPISODES) >> false
		1 * categoryApiMock.getPages(CategoryName.TOS_EPISODES, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_TOS_EPISODES)
		1 * categoryApiMock.getPages(CategoryName.TAS_EPISODES, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_TAS_EPISODES)
		1 * categoryApiMock.getPages(CategoryName.TNG_EPISODES, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_TNG_EPISODES)
		1 * categoryApiMock.getPages(CategoryName.DS9_EPISODES, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_DS9_EPISODES)
		1 * categoryApiMock.getPages(CategoryName.VOY_EPISODES, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_VOY_EPISODES)
		1 * categoryApiMock.getPages(CategoryName.ENT_EPISODES, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_ENT_EPISODES)
		1 * categoryApiMock.getPages(CategoryName.DIS_EPISODES, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_DIS_EPISODES)
		0 * _
		categoryHeaderTitleList.contains TITLE_TOS_EPISODES
		categoryHeaderTitleList.contains TITLE_TAS_EPISODES
		categoryHeaderTitleList.contains TITLE_TNG_EPISODES
		categoryHeaderTitleList.contains TITLE_DS9_EPISODES
		categoryHeaderTitleList.contains TITLE_VOY_EPISODES
		categoryHeaderTitleList.contains TITLE_ENT_EPISODES
		categoryHeaderTitleList.contains TITLE_DIS_EPISODES
	}

	void "EpisodeReader is created with no pages when step is completed"() {
		when:
		EpisodeReader episodeReader = episodeCreationConfiguration.episodeReader()
		List<String> categoryHeaderTitleList = readerToList(episodeReader)

		then:
		1 * jobCompletenessDeciderMock.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_EPISODES) >> true
		0 * _
		categoryHeaderTitleList.empty
	}

}
