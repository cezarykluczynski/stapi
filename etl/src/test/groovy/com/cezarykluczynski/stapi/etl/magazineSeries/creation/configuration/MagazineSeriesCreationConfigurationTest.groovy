package com.cezarykluczynski.stapi.etl.magazineSeries.creation.configuration

import com.cezarykluczynski.stapi.etl.common.configuration.AbstractCreationConfigurationTest
import com.cezarykluczynski.stapi.etl.configuration.job.service.StepCompletenessDecider
import com.cezarykluczynski.stapi.etl.magazineSeries.creation.processor.MagazineSeriesReader
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle
import com.cezarykluczynski.stapi.etl.util.constant.JobName
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.sources.mediawiki.api.CategoryApi
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource
import org.springframework.context.ApplicationContext

class MagazineSeriesCreationConfigurationTest extends AbstractCreationConfigurationTest {

	private static final String TITLE_MAGAZINE_SERIES = 'TITLE_MAGAZINE_SERIES'

	private ApplicationContext applicationContextMock

	private CategoryApi categoryApiMock

	private StepCompletenessDecider jobCompletenessDeciderMock

	private MagazineSeriesCreationConfiguration magazineCreationConfiguration

	void setup() {
		applicationContextMock = Mock()
		categoryApiMock = Mock()
		jobCompletenessDeciderMock = Mock()
		magazineCreationConfiguration = new MagazineSeriesCreationConfiguration(
				applicationContext: applicationContextMock,
				categoryApi: categoryApiMock,
				stepCompletenessDecider: jobCompletenessDeciderMock)
	}

	void "MagazineSeriesReader is created with all pages when step is not completed"() {
		when:
		MagazineSeriesReader magazineReader = magazineCreationConfiguration.magazineSeriesReader()
		List<String> categoryHeaderTitleList = readerToList(magazineReader)

		then:
		1 * jobCompletenessDeciderMock.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_MAGAZINE_SERIES) >> false
		1 * categoryApiMock.getPagesIncludingSubcategories(CategoryTitle.MAGAZINES, MediaWikiSource.MEMORY_ALPHA_EN) >>
				createListWithPageHeaderTitle(TITLE_MAGAZINE_SERIES)
		0 * _
		categoryHeaderTitleList.contains TITLE_MAGAZINE_SERIES
	}

	void "MagazineSeriesReader is created with no pages when step is completed"() {
		when:
		MagazineSeriesReader magazineReader = magazineCreationConfiguration.magazineSeriesReader()
		List<String> categoryHeaderTitleList = readerToList(magazineReader)

		then:
		1 * jobCompletenessDeciderMock.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_MAGAZINE_SERIES) >> true
		0 * _
		categoryHeaderTitleList.empty
	}

}
