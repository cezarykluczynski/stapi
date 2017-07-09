package com.cezarykluczynski.stapi.etl.organization.creation.configuration

import com.cezarykluczynski.stapi.etl.common.configuration.AbstractCreationConfigurationTest
import com.cezarykluczynski.stapi.etl.configuration.job.service.StepCompletenessDecider
import com.cezarykluczynski.stapi.etl.organization.creation.processor.OrganizationReader
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitles
import com.cezarykluczynski.stapi.etl.util.constant.JobName
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.sources.mediawiki.api.CategoryApi
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource

class OrganizationCreationConfigurationTest extends AbstractCreationConfigurationTest {

	private static final String TITLE_ORGANIZATIONS = 'TITLE_ORGANIZATIONS'

	private CategoryApi categoryApiMock

	private StepCompletenessDecider jobCompletenessDeciderMock

	private OrganizationCreationConfiguration organizationCreationConfiguration

	void setup() {
		categoryApiMock = Mock()
		jobCompletenessDeciderMock = Mock()
		organizationCreationConfiguration = new OrganizationCreationConfiguration(
				categoryApi: categoryApiMock,
				stepCompletenessDecider: jobCompletenessDeciderMock)
	}

	@SuppressWarnings('LineLength')
	void "OrganizationReader is created is created with all pages when step is not completed"() {
		when:
		OrganizationReader organizationReader = organizationCreationConfiguration.organizationReader()
		List<String> categoryHeaderTitleList = pageHeaderReaderToList(organizationReader)

		then:
		1 * jobCompletenessDeciderMock.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_ORGANIZATIONS) >> false
		1 * categoryApiMock.getPages(CategoryTitles.ORGANIZATIONS, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_ORGANIZATIONS)
		0 * _
		categoryHeaderTitleList.contains TITLE_ORGANIZATIONS
	}

	void "OrganizationReader is created with no pages when step is completed"() {
		when:
		OrganizationReader organizationReader = organizationCreationConfiguration.organizationReader()
		List<String> categoryHeaderTitleList = pageHeaderReaderToList(organizationReader)

		then:
		1 * jobCompletenessDeciderMock.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_ORGANIZATIONS) >> true
		0 * _
		categoryHeaderTitleList.empty
	}

}
