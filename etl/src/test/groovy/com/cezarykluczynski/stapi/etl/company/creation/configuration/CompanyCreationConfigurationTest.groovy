package com.cezarykluczynski.stapi.etl.company.creation.configuration

import com.cezarykluczynski.stapi.etl.common.configuration.AbstractCreationConfigurationTest
import com.cezarykluczynski.stapi.etl.company.creation.processor.CompanyReader
import com.cezarykluczynski.stapi.etl.configuration.job.service.StepCompletenessDecider
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle
import com.cezarykluczynski.stapi.etl.util.constant.JobName
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.sources.mediawiki.api.CategoryApi
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource
import org.springframework.context.ApplicationContext

class CompanyCreationConfigurationTest extends AbstractCreationConfigurationTest {

	private static final String TITLE_COMPANY = 'TITLE_COMPANY'

	private ApplicationContext applicationContextMock

	private CategoryApi categoryApiMock

	private StepCompletenessDecider jobCompletenessDeciderMock

	private CompanyCreationConfiguration companyCreationConfiguration

	void setup() {
		applicationContextMock = Mock()
		categoryApiMock = Mock()
		jobCompletenessDeciderMock = Mock()
		companyCreationConfiguration = new CompanyCreationConfiguration(
				applicationContext: applicationContextMock,
				categoryApi: categoryApiMock,
				stepCompletenessDecider: jobCompletenessDeciderMock)
	}

	void "CompanyReader is created with all pages when step is not completed"() {
		when:
		CompanyReader companyReader = companyCreationConfiguration.companyReader()
		List<String> categoryHeaderTitleList = readerToList(companyReader)

		then:
		1 * jobCompletenessDeciderMock.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_COMPANIES) >> false
		1 * categoryApiMock.getPagesIncludingSubcategories(CategoryTitle.COMPANIES, MediaWikiSource.MEMORY_ALPHA_EN) >>
				createListWithPageHeaderTitle(TITLE_COMPANY)
		0 * _
		categoryHeaderTitleList.contains TITLE_COMPANY
	}

	void "CompanyReader is created with no pages when step is completed"() {
		when:
		CompanyReader companyReader = companyCreationConfiguration.companyReader()
		List<String> categoryHeaderTitleList = readerToList(companyReader)

		then:
		1 * jobCompletenessDeciderMock.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_COMPANIES) >> true
		0 * _
		categoryHeaderTitleList.empty
	}

}
