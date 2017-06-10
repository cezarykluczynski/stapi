package com.cezarykluczynski.stapi.etl.magazine.creation.configuration

import com.cezarykluczynski.stapi.etl.common.configuration.AbstractCreationConfigurationTest
import com.cezarykluczynski.stapi.etl.configuration.job.service.StepCompletenessDecider
import com.cezarykluczynski.stapi.etl.magazine.creation.processor.MagazineReader
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle
import com.cezarykluczynski.stapi.etl.util.constant.JobName
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.sources.mediawiki.api.CategoryApi
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource
import org.springframework.context.ApplicationContext

class MagazineCreationConfigurationTest extends AbstractCreationConfigurationTest {

	private static final String TITLE_MAGAZINE = 'TITLE_MAGAZINE'

	private ApplicationContext applicationContextMock

	private CategoryApi categoryApiMock

	private StepCompletenessDecider jobCompletenessDeciderMock

	private MagazineCreationConfiguration magazineCreationConfiguration

	void setup() {
		applicationContextMock = Mock()
		categoryApiMock = Mock()
		jobCompletenessDeciderMock = Mock()
		magazineCreationConfiguration = new MagazineCreationConfiguration(
				applicationContext: applicationContextMock,
				categoryApi: categoryApiMock,
				stepCompletenessDecider: jobCompletenessDeciderMock)
	}

	void "MagazineReader is created with all pages when step is not completed"() {
		when:
		MagazineReader magazineReader = magazineCreationConfiguration.magazineReader()
		List<String> categoryHeaderTitleList = readerToList(magazineReader)

		then:
		1 * jobCompletenessDeciderMock.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_MAGAZINES) >> false
		1 * categoryApiMock.getPagesIncludingSubcategories(CategoryTitle.MAGAZINES, MediaWikiSource.MEMORY_ALPHA_EN) >>
				createListWithPageHeaderTitle(TITLE_MAGAZINE)
		0 * _
		categoryHeaderTitleList.contains TITLE_MAGAZINE
	}

	void "ComicsReader is created with no pages when step is completed"() {
		when:
		MagazineReader magazineReader = magazineCreationConfiguration.magazineReader()
		List<String> categoryHeaderTitleList = readerToList(magazineReader)

		then:
		1 * jobCompletenessDeciderMock.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_MAGAZINES) >> true
		0 * _
		categoryHeaderTitleList.empty
	}

}
