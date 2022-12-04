package com.cezarykluczynski.stapi.etl.organization.creation.configuration

import com.cezarykluczynski.stapi.etl.common.configuration.AbstractCreationConfigurationTest
import com.cezarykluczynski.stapi.etl.configuration.job.service.StepCompletenessDecider
import com.cezarykluczynski.stapi.etl.organization.creation.processor.OrganizationReader
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitles
import com.cezarykluczynski.stapi.etl.util.constant.JobName
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.sources.mediawiki.api.CategoryApi
import com.cezarykluczynski.stapi.sources.mediawiki.api.PageApi
import com.cezarykluczynski.stapi.sources.mediawiki.api.SpecialApi
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource
import com.cezarykluczynski.stapi.util.constant.TemplateTitle

class OrganizationCreationConfigurationTest extends AbstractCreationConfigurationTest {

	private static final String TITLE_ORGANIZATIONS = 'TITLE_ORGANIZATIONS'
	private static final String TITLE_SIDEBAR_ORGANIZATION = 'TITLE_SIDEBAR_ORGANIZATION'

	private CategoryApi categoryApiMock

	private PageApi pageApiMock

	private SpecialApi specialApiMock

	private StepCompletenessDecider jobCompletenessDeciderMock

	private OrganizationCreationConfiguration organizationCreationConfiguration

	void setup() {
		categoryApiMock = Mock()
		pageApiMock = Mock()
		specialApiMock = Mock()
		jobCompletenessDeciderMock = Mock()
		organizationCreationConfiguration = new OrganizationCreationConfiguration(
				categoryApi: categoryApiMock,
				pageApi: pageApiMock,
				specialApi: specialApiMock,
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
		1 * specialApiMock.getPagesTranscludingTemplate(TemplateTitle.SIDEBAR_ORGANIZATION, MediaWikiSource.MEMORY_ALPHA_EN) >>
				createListWithPageHeaderTitle(TITLE_SIDEBAR_ORGANIZATION)

		then:
		0 * _
		categoryHeaderTitleList.contains TITLE_ORGANIZATIONS
		categoryHeaderTitleList.contains TITLE_SIDEBAR_ORGANIZATION
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
