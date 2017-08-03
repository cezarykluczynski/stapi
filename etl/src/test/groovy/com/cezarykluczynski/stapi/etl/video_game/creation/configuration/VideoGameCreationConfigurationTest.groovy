package com.cezarykluczynski.stapi.etl.video_game.creation.configuration

import com.cezarykluczynski.stapi.etl.common.configuration.AbstractCreationConfigurationTest
import com.cezarykluczynski.stapi.etl.configuration.job.service.StepCompletenessDecider
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle
import com.cezarykluczynski.stapi.etl.util.constant.JobName
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.etl.video_game.creation.processor.VideoGameReader
import com.cezarykluczynski.stapi.sources.mediawiki.api.CategoryApi
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource

class VideoGameCreationConfigurationTest extends AbstractCreationConfigurationTest {

	private static final String TITLE_VIDEO_GAME = 'TITLE_VIDEO_GAME'

	private CategoryApi categoryApiMock

	private StepCompletenessDecider jobCompletenessDeciderMock

	private VideoGameCreationConfiguration videoGameCreationConfiguration

	void setup() {
		categoryApiMock = Mock()
		jobCompletenessDeciderMock = Mock()
		videoGameCreationConfiguration = new VideoGameCreationConfiguration(
				categoryApi: categoryApiMock,
				stepCompletenessDecider: jobCompletenessDeciderMock)
	}

	void "VideoGameReader is created with all pages when step is not completed"() {
		when:
		VideoGameReader videoGameReader = videoGameCreationConfiguration.videoGameReader()
		List<String> categoryHeaderTitleList = pageHeaderReaderToList(videoGameReader)

		then:
		1 * jobCompletenessDeciderMock.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_VIDEO_GAMES) >> false
		1 * categoryApiMock.getPages(CategoryTitle.VIDEO_GAMES, MediaWikiSource.MEMORY_ALPHA_EN) >>
				createListWithPageHeaderTitle(TITLE_VIDEO_GAME)
		0 * _
		categoryHeaderTitleList.contains TITLE_VIDEO_GAME
	}

	void "VideoGameReader is created with no pages when step is completed"() {
		when:
		VideoGameReader videoGameReader = videoGameCreationConfiguration.videoGameReader()
		List<String> categoryHeaderTitleList = pageHeaderReaderToList(videoGameReader)

		then:
		1 * jobCompletenessDeciderMock.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_VIDEO_GAMES) >> true
		0 * _
		categoryHeaderTitleList.empty
	}

}
