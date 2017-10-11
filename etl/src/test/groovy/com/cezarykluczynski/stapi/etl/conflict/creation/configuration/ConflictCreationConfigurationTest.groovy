package com.cezarykluczynski.stapi.etl.conflict.creation.configuration

import com.cezarykluczynski.stapi.etl.common.configuration.AbstractCreationConfigurationTest
import com.cezarykluczynski.stapi.etl.configuration.job.service.StepCompletenessDecider
import com.cezarykluczynski.stapi.etl.conflict.creation.processor.ConflictReader
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle
import com.cezarykluczynski.stapi.etl.util.constant.JobName
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.sources.mediawiki.api.CategoryApi
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource

class ConflictCreationConfigurationTest extends AbstractCreationConfigurationTest {

	private static final String TITLE_CONFLICTS = 'TITLE_CONFLICTS'

	private CategoryApi categoryApiMock

	private StepCompletenessDecider jobCompletenessDeciderMock

	private ConflictCreationConfiguration conflictCreationConfiguration

	void setup() {
		categoryApiMock = Mock()
		jobCompletenessDeciderMock = Mock()
		conflictCreationConfiguration = new ConflictCreationConfiguration(
				categoryApi: categoryApiMock,
				stepCompletenessDecider: jobCompletenessDeciderMock)
	}

	void "ConflictReader is created with all pages when step is not completed"() {
		when:
		ConflictReader planetReader = conflictCreationConfiguration.conflictReader()
		List<String> categoryHeaderTitleList = pageHeaderReaderToList(planetReader)

		then:
		1 * jobCompletenessDeciderMock.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_CONFLICTS) >> false
		1 * categoryApiMock.getPagesIncludingSubcategories(CategoryTitle.CONFLICTS, MediaWikiSource.MEMORY_ALPHA_EN) >>
				createListWithPageHeaderTitle(TITLE_CONFLICTS)
		0 * _
		categoryHeaderTitleList.contains TITLE_CONFLICTS
	}

	void "ConflictReader is created with no pages when step is completed"() {
		when:
		ConflictReader planetReader = conflictCreationConfiguration.conflictReader()
		List<String> categoryHeaderTitleList = pageHeaderReaderToList(planetReader)

		then:
		1 * jobCompletenessDeciderMock.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_CONFLICTS) >> true
		0 * _
		categoryHeaderTitleList.empty
	}

}
