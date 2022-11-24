package com.cezarykluczynski.stapi.etl.organization.creation.configuration

import com.cezarykluczynski.stapi.etl.common.configuration.AbstractCreationConfigurationTest
import com.cezarykluczynski.stapi.etl.configuration.job.service.StepCompletenessDecider
import com.cezarykluczynski.stapi.etl.organization.creation.processor.OrganizationReader
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitles
import com.cezarykluczynski.stapi.etl.util.constant.JobName
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.sources.mediawiki.api.CategoryApi
import com.cezarykluczynski.stapi.sources.mediawiki.api.PageApi
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource
import com.cezarykluczynski.stapi.sources.mediawiki.converter.PageHeaderConverter
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader
import com.cezarykluczynski.stapi.util.constant.PageTitle

class OrganizationCreationConfigurationTest extends AbstractCreationConfigurationTest {

	private static final String TITLE_ORGANIZATIONS = 'TITLE_ORGANIZATIONS'

	private CategoryApi categoryApiMock

	private PageApi pageApiMock

	private PageHeaderConverter pageHeaderConverterMock

	private StepCompletenessDecider jobCompletenessDeciderMock

	private OrganizationCreationConfiguration organizationCreationConfiguration

	void setup() {
		categoryApiMock = Mock()
		pageApiMock = Mock()
		pageHeaderConverterMock = Mock()
		jobCompletenessDeciderMock = Mock()
		organizationCreationConfiguration = new OrganizationCreationConfiguration(
				categoryApi: categoryApiMock,
				pageApi: pageApiMock,
				pageHeaderConverter: pageHeaderConverterMock,
				stepCompletenessDecider: jobCompletenessDeciderMock)
	}

	@SuppressWarnings('LineLength')
	void "OrganizationReader is created is created with all pages when step is not completed"() {
		given:
		Page pageStarfleet = Mock()
		PageHeader pageHeaderStarfleet = new PageHeader(title: PageTitle.STARFLEET)
		Page pageStarfleetMirror = Mock()
		PageHeader pageHeaderStarfleetMirror = new PageHeader(title: PageTitle.STARFLEET_MIRROR)

		when:
		OrganizationReader organizationReader = organizationCreationConfiguration.organizationReader()
		List<String> categoryHeaderTitleList = pageHeaderReaderToList(organizationReader)

		then:
		1 * jobCompletenessDeciderMock.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_ORGANIZATIONS) >> false
		1 * categoryApiMock.getPages(CategoryTitles.ORGANIZATIONS, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_ORGANIZATIONS)

		then:
		1 * pageApiMock.getPage(PageTitle.STARFLEET, MediaWikiSource.MEMORY_ALPHA_EN) >> pageStarfleet
		1 * pageHeaderConverterMock.fromPage(pageStarfleet) >> pageHeaderStarfleet

		then:
		1 * pageApiMock.getPage(PageTitle.STARFLEET_MIRROR, MediaWikiSource.MEMORY_ALPHA_EN) >> pageStarfleetMirror
		1 * pageHeaderConverterMock.fromPage(pageStarfleetMirror) >> pageHeaderStarfleetMirror

		then:
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
