package com.cezarykluczynski.stapi.etl.video_release.creation.configuration

import com.cezarykluczynski.stapi.etl.common.configuration.AbstractCreationConfigurationTest
import com.cezarykluczynski.stapi.etl.configuration.job.service.StepCompletenessDecider
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle
import com.cezarykluczynski.stapi.etl.util.constant.JobName
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.etl.video_release.creation.processor.VideoReleaseReader
import com.cezarykluczynski.stapi.sources.mediawiki.api.CategoryApi
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource

class VideoReleaseCreationConfigurationTest extends AbstractCreationConfigurationTest {

	private static final String TITLE_VIDEO_RELEASE = 'TITLE_VIDEO_RELEASE'

	private CategoryApi categoryApiMock

	private StepCompletenessDecider jobCompletenessDeciderMock

	private VideoReleaseCreationConfiguration videoReleaseCreationConfiguration

	void setup() {
		categoryApiMock = Mock()
		jobCompletenessDeciderMock = Mock()
		videoReleaseCreationConfiguration = new VideoReleaseCreationConfiguration(
				categoryApi: categoryApiMock,
				stepCompletenessDecider: jobCompletenessDeciderMock)
	}

	void "VideoReleaseReader is created with all pages when step is not completed"() {
		when:
		VideoReleaseReader videoReleaseReader = videoReleaseCreationConfiguration.videoReleaseReader()
		List<String> categoryHeaderTitleList = pageHeaderReaderToList(videoReleaseReader)

		then:
		1 * jobCompletenessDeciderMock.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_VIDEO_RELEASES) >> false
		1 * categoryApiMock.getPagesIncludingSubcategories(CategoryTitle.VIDEO_RELEASES, MediaWikiSource.MEMORY_ALPHA_EN) >>
				createListWithPageHeaderTitle(TITLE_VIDEO_RELEASE)
		0 * _
		categoryHeaderTitleList.contains TITLE_VIDEO_RELEASE
	}

	void "VideoReleaseReader is created with no pages when step is completed"() {
		when:
		VideoReleaseReader videoReleaseReader = videoReleaseCreationConfiguration.videoReleaseReader()
		List<String> categoryHeaderTitleList = pageHeaderReaderToList(videoReleaseReader)

		then:
		1 * jobCompletenessDeciderMock.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_VIDEO_RELEASES) >> true
		0 * _
		categoryHeaderTitleList.empty
	}

}
