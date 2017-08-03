package com.cezarykluczynski.stapi.etl.soundtrack.creation.configuration

import com.cezarykluczynski.stapi.etl.common.configuration.AbstractCreationConfigurationTest
import com.cezarykluczynski.stapi.etl.configuration.job.service.StepCompletenessDecider
import com.cezarykluczynski.stapi.etl.soundtrack.creation.processor.SoundtrackReader
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle
import com.cezarykluczynski.stapi.etl.util.constant.JobName
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.sources.mediawiki.api.CategoryApi
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource

class SoundtrackCreationConfigurationTest extends AbstractCreationConfigurationTest {

	private static final String TITLE_SOUNDTRACK = 'TITLE_SOUNDTRACK'

	private CategoryApi categoryApiMock

	private StepCompletenessDecider jobCompletenessDeciderMock

	private SoundtrackCreationConfiguration soundtrackCreationConfiguration

	void setup() {
		categoryApiMock = Mock()
		jobCompletenessDeciderMock = Mock()
		soundtrackCreationConfiguration = new SoundtrackCreationConfiguration(
				categoryApi: categoryApiMock,
				stepCompletenessDecider: jobCompletenessDeciderMock)
	}

	void "SoundtrackReader is created with all pages when step is not completed"() {
		when:
		SoundtrackReader soundtrackReader = soundtrackCreationConfiguration.soundtrackReader()
		List<String> categoryHeaderTitleList = pageHeaderReaderToList(soundtrackReader)

		then:
		1 * jobCompletenessDeciderMock.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_SOUNDTRACKS) >> false
		1 * categoryApiMock.getPages(CategoryTitle.SOUNDTRACKS, MediaWikiSource.MEMORY_ALPHA_EN) >>
				createListWithPageHeaderTitle(TITLE_SOUNDTRACK)
		0 * _
		categoryHeaderTitleList.contains TITLE_SOUNDTRACK
	}

	void "SoundtrackReader is created with no pages when step is completed"() {
		when:
		SoundtrackReader soundtrackReader = soundtrackCreationConfiguration.soundtrackReader()
		List<String> categoryHeaderTitleList = pageHeaderReaderToList(soundtrackReader)

		then:
		1 * jobCompletenessDeciderMock.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_SOUNDTRACKS) >> true
		0 * _
		categoryHeaderTitleList.empty
	}

}
