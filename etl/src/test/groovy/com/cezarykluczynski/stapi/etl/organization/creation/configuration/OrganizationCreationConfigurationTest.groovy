package com.cezarykluczynski.stapi.etl.organization.creation.configuration

import com.cezarykluczynski.stapi.etl.common.configuration.AbstractCreationConfigurationTest
import com.cezarykluczynski.stapi.etl.configuration.job.service.StepCompletenessDecider
import com.cezarykluczynski.stapi.etl.organization.creation.processor.OrganizationReader
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle
import com.cezarykluczynski.stapi.etl.util.constant.JobName
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.sources.mediawiki.api.CategoryApi
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource
import org.springframework.context.ApplicationContext

class OrganizationCreationConfigurationTest extends AbstractCreationConfigurationTest {

	private static final String TITLE_AGENCIES = 'TITLE_AGENCIES'
	private static final String TITLE_BAJORAN_AGENCIES = 'TITLE_BAJORAN_AGENCIES'
	private static final String TITLE_CARDASSIAN_AGENCIES = 'TITLE_CARDASSIAN_AGENCIES'
	private static final String TITLE_EARTH_AGENCIES = 'TITLE_EARTH_AGENCIES'
	private static final String TITLE_FEDERATION_AGENCIES = 'TITLE_FEDERATION_AGENCIES'
	private static final String TITLE_FERENGI_AGENCIES = 'TITLE_FERENGI_AGENCIES'
	private static final String TITLE_KLINGON_AGENCIES = 'TITLE_KLINGON_AGENCIES'
	private static final String TITLE_LAW_ENFORCEMENT_AGENCIES = 'TITLE_LAW_ENFORCEMENT_AGENCIES'
	private static final String TITLE_PRISONS_AND_PENAL_COLONIES = 'TITLE_PRISONS_AND_PENAL_COLONIES'
	private static final String TITLE_ROMULAN_AGENCIES = 'TITLE_ROMULAN_AGENCIES'
	private static final String TITLE_VULCAN_AGENCIES = 'TITLE_VULCAN_AGENCIES'
	private static final String TITLE_EARTH_ORGANIZATIONS = 'TITLE_EARTH_ORGANIZATIONS'
	private static final String TITLE_EARTH_ESTABLISHMENTS = 'TITLE_EARTH_ESTABLISHMENTS'
	private static final String TITLE_EARTH_INTERGOVERNMENTAL_ORGANIZATIONS = 'TITLE_EARTH_INTERGOVERNMENTAL_ORGANIZATIONS'
	private static final String TITLE_EARTH_MILITARY_ORGANIZATIONS = 'TITLE_EARTH_MILITARY_ORGANIZATIONS'
	private static final String TITLE_GOVERNMENTS = 'TITLE_GOVERNMENTS'
	private static final String TITLE_INTERGOVERNMENTAL_ORGANIZATIONS = 'TITLE_INTERGOVERNMENTAL_ORGANIZATIONS'
	private static final String TITLE_RESEARCH_ORGANIZATIONS = 'TITLE_RESEARCH_ORGANIZATIONS'
	private static final String TITLE_SPORTS_ORGANIZATIONS = 'TITLE_SPORTS_ORGANIZATIONS'
	private static final String TITLE_MEDICAL_ORGANIZATIONS = 'TITLE_MEDICAL_ORGANIZATIONS'
	private static final String TITLE_MEDICAL_ESTABLISHMENTS = 'TITLE_MEDICAL_ESTABLISHMENTS'
	private static final String TITLE_MEDICAL_ESTABLISHMENTS_RETCONNED = 'TITLE_MEDICAL_ESTABLISHMENTS_RETCONNED'
	private static final String TITLE_WARDS = 'TITLE_WARDS'
	private static final String TITLE_ESTABLISHMENTS = 'TITLE_ESTABLISHMENTS'
	private static final String TITLE_SCHOOLS = 'TITLE_SCHOOLS'
	private static final String TITLE_ESTABLISHMENTS_RETCONNED = 'TITLE_ESTABLISHMENTS_RETCONNED'
	private static final String TITLE_DS9_ESTABLISHMENTS = 'TITLE_DS9_ESTABLISHMENTS'
	private static final String TITLE_MILITARY_ORGANIZATIONS = 'TITLE_MILITARY_ORGANIZATIONS'
	private static final String TITLE_MILITARY_UNITS = 'TITLE_MILITARY_UNITS'

	private ApplicationContext applicationContextMock

	private CategoryApi categoryApiMock

	private StepCompletenessDecider jobCompletenessDeciderMock

	private OrganizationCreationConfiguration organizationCreationConfiguration

	void setup() {
		applicationContextMock = Mock(ApplicationContext)
		categoryApiMock = Mock(CategoryApi)
		jobCompletenessDeciderMock = Mock(StepCompletenessDecider)
		organizationCreationConfiguration = new OrganizationCreationConfiguration(
				applicationContext: applicationContextMock,
				categoryApi: categoryApiMock,
				stepCompletenessDecider: jobCompletenessDeciderMock)
	}

	@SuppressWarnings('LineLength')
	void "OrganizationReader is created is created with all pages when step is not completed"() {
		when:
		OrganizationReader organizationReader = organizationCreationConfiguration.organizationReader()
		List<String> categoryHeaderTitleList = readerToList(organizationReader)

		then:
		1 * jobCompletenessDeciderMock.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_ORGANIZATIONS) >> false

		1 * categoryApiMock.getPages(CategoryTitle.AGENCIES, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_AGENCIES)
		1 * categoryApiMock.getPages(CategoryTitle.BAJORAN_AGENCIES, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_BAJORAN_AGENCIES)
		1 * categoryApiMock.getPages(CategoryTitle.CARDASSIAN_AGENCIES, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_CARDASSIAN_AGENCIES)
		1 * categoryApiMock.getPages(CategoryTitle.EARTH_AGENCIES , MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_EARTH_AGENCIES)
		1 * categoryApiMock.getPages(CategoryTitle.FEDERATION_AGENCIES, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_FEDERATION_AGENCIES)
		1 * categoryApiMock.getPages(CategoryTitle.FERENGI_AGENCIES, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_FERENGI_AGENCIES)
		1 * categoryApiMock.getPages(CategoryTitle.KLINGON_AGENCIES, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_KLINGON_AGENCIES)
		1 * categoryApiMock.getPages(CategoryTitle.LAW_ENFORCEMENT_AGENCIES, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_LAW_ENFORCEMENT_AGENCIES)
		1 * categoryApiMock.getPages(CategoryTitle.PRISONS_AND_PENAL_COLONIES, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_PRISONS_AND_PENAL_COLONIES)
		1 * categoryApiMock.getPages(CategoryTitle.ROMULAN_AGENCIES, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_ROMULAN_AGENCIES)
		1 * categoryApiMock.getPages(CategoryTitle.VULCAN_AGENCIES, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_VULCAN_AGENCIES)
		1 * categoryApiMock.getPages(CategoryTitle.EARTH_ORGANIZATIONS, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_EARTH_ORGANIZATIONS)
		1 * categoryApiMock.getPages(CategoryTitle.EARTH_ESTABLISHMENTS, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_EARTH_ESTABLISHMENTS)
		1 * categoryApiMock.getPages(CategoryTitle.EARTH_INTERGOVERNMENTAL_ORGANIZATIONS, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_EARTH_INTERGOVERNMENTAL_ORGANIZATIONS)
		1 * categoryApiMock.getPages(CategoryTitle.EARTH_MILITARY_ORGANIZATIONS, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_EARTH_MILITARY_ORGANIZATIONS)
		1 * categoryApiMock.getPages(CategoryTitle.GOVERNMENTS, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_GOVERNMENTS)
		1 * categoryApiMock.getPages(CategoryTitle.INTERGOVERNMENTAL_ORGANIZATIONS, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_INTERGOVERNMENTAL_ORGANIZATIONS)
		1 * categoryApiMock.getPages(CategoryTitle.RESEARCH_ORGANIZATIONS, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_RESEARCH_ORGANIZATIONS)
		1 * categoryApiMock.getPages(CategoryTitle.SPORTS_ORGANIZATIONS, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_SPORTS_ORGANIZATIONS)
		1 * categoryApiMock.getPages(CategoryTitle.MEDICAL_ORGANIZATIONS, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_MEDICAL_ORGANIZATIONS)
		1 * categoryApiMock.getPages(CategoryTitle.MEDICAL_ESTABLISHMENTS, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_MEDICAL_ESTABLISHMENTS)
		1 * categoryApiMock.getPages(CategoryTitle.MEDICAL_ESTABLISHMENTS_RETCONNED, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_MEDICAL_ESTABLISHMENTS_RETCONNED)
		1 * categoryApiMock.getPages(CategoryTitle.WARDS, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_WARDS)
		1 * categoryApiMock.getPages(CategoryTitle.ESTABLISHMENTS, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_ESTABLISHMENTS)
		1 * categoryApiMock.getPages(CategoryTitle.SCHOOLS, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_SCHOOLS)
		1 * categoryApiMock.getPages(CategoryTitle.ESTABLISHMENTS_RETCONNED, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_ESTABLISHMENTS_RETCONNED)
		1 * categoryApiMock.getPages(CategoryTitle.DS9_ESTABLISHMENTS, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_DS9_ESTABLISHMENTS)
		1 * categoryApiMock.getPages(CategoryTitle.MILITARY_ORGANIZATIONS, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_MILITARY_ORGANIZATIONS)
		1 * categoryApiMock.getPages(CategoryTitle.MILITARY_UNITS, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_MILITARY_UNITS)
		0 * _
		categoryHeaderTitleList.contains TITLE_AGENCIES
		categoryHeaderTitleList.contains TITLE_BAJORAN_AGENCIES
		categoryHeaderTitleList.contains TITLE_CARDASSIAN_AGENCIES
		categoryHeaderTitleList.contains TITLE_EARTH_AGENCIES
		categoryHeaderTitleList.contains TITLE_FEDERATION_AGENCIES
		categoryHeaderTitleList.contains TITLE_FERENGI_AGENCIES
		categoryHeaderTitleList.contains TITLE_KLINGON_AGENCIES
		categoryHeaderTitleList.contains TITLE_LAW_ENFORCEMENT_AGENCIES
		categoryHeaderTitleList.contains TITLE_PRISONS_AND_PENAL_COLONIES
		categoryHeaderTitleList.contains TITLE_ROMULAN_AGENCIES
		categoryHeaderTitleList.contains TITLE_VULCAN_AGENCIES
		categoryHeaderTitleList.contains TITLE_EARTH_ORGANIZATIONS
		categoryHeaderTitleList.contains TITLE_EARTH_ESTABLISHMENTS
		categoryHeaderTitleList.contains TITLE_EARTH_INTERGOVERNMENTAL_ORGANIZATIONS
		categoryHeaderTitleList.contains TITLE_EARTH_MILITARY_ORGANIZATIONS
		categoryHeaderTitleList.contains TITLE_GOVERNMENTS
		categoryHeaderTitleList.contains TITLE_INTERGOVERNMENTAL_ORGANIZATIONS
		categoryHeaderTitleList.contains TITLE_RESEARCH_ORGANIZATIONS
		categoryHeaderTitleList.contains TITLE_SPORTS_ORGANIZATIONS
		categoryHeaderTitleList.contains TITLE_MEDICAL_ORGANIZATIONS
		categoryHeaderTitleList.contains TITLE_MEDICAL_ESTABLISHMENTS
		categoryHeaderTitleList.contains TITLE_MEDICAL_ESTABLISHMENTS_RETCONNED
		categoryHeaderTitleList.contains TITLE_WARDS
		categoryHeaderTitleList.contains TITLE_ESTABLISHMENTS
		categoryHeaderTitleList.contains TITLE_SCHOOLS
		categoryHeaderTitleList.contains TITLE_ESTABLISHMENTS_RETCONNED
		categoryHeaderTitleList.contains TITLE_DS9_ESTABLISHMENTS
		categoryHeaderTitleList.contains TITLE_MILITARY_ORGANIZATIONS
		categoryHeaderTitleList.contains TITLE_MILITARY_UNITS
	}

	void "OrganizationReader is created with no pages when step is completed"() {
		when:
		OrganizationReader organizationReader = organizationCreationConfiguration.organizationReader()
		List<String> categoryHeaderTitleList = readerToList(organizationReader)

		then:
		1 * jobCompletenessDeciderMock.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_ORGANIZATIONS) >> true
		0 * _
		categoryHeaderTitleList.empty
	}

}
