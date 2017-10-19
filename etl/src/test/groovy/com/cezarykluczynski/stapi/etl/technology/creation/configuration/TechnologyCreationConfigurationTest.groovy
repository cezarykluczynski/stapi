package com.cezarykluczynski.stapi.etl.technology.creation.configuration

import com.cezarykluczynski.stapi.etl.common.configuration.AbstractCreationConfigurationTest
import com.cezarykluczynski.stapi.etl.configuration.job.service.StepCompletenessDecider
import com.cezarykluczynski.stapi.etl.technology.creation.processor.TechnologyReader
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle
import com.cezarykluczynski.stapi.etl.util.constant.JobName
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.sources.mediawiki.api.CategoryApi
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource

class TechnologyCreationConfigurationTest extends AbstractCreationConfigurationTest {

	private static final String TITLE_TECHNOLOGY = 'TITLE_TECHNOLOGY'
	private static final String TITLE_BORG_TECHNOLOGY = 'TITLE_BORG_TECHNOLOGY'
	private static final String TITLE_BORG_COMPONENTS = 'TITLE_BORG_COMPONENTS'
	private static final String TITLE_COMMUNICATIONS_TECHNOLOGY = 'TITLE_COMMUNICATIONS_TECHNOLOGY'
	private static final String TITLE_COMMUNICATIONS_TECHNOLOGY_RETCONNED = 'TITLE_COMMUNICATIONS_TECHNOLOGY_RETCONNED'
	private static final String TITLE_COMPUTER_TECHNOLOGY = 'TITLE_COMPUTER_TECHNOLOGY'
	private static final String TITLE_COMPUTER_PROGRAMMING = 'TITLE_COMPUTER_PROGRAMMING'
	private static final String TITLE_SUBROUTINES = 'TITLE_SUBROUTINES'
	private static final String TITLE_DATABASES = 'TITLE_DATABASES'
	private static final String TITLE_ENERGY_TECHNOLOGY = 'TITLE_ENERGY_TECHNOLOGY'
	private static final String TITLE_ENERGY_TECHNOLOGY_RETCONNED = 'TITLE_ENERGY_TECHNOLOGY_RETCONNED'
	private static final String TITLE_FICTIONAL_TECHNOLOGY = 'TITLE_FICTIONAL_TECHNOLOGY'
	private static final String TITLE_HOLOGRAPHIC_TECHNOLOGY = 'TITLE_HOLOGRAPHIC_TECHNOLOGY'
	private static final String TITLE_IDENTIFICATION_TECHNOLOGY = 'TITLE_IDENTIFICATION_TECHNOLOGY'
	private static final String TITLE_LIFE_SUPPORT_TECHNOLOGY = 'TITLE_LIFE_SUPPORT_TECHNOLOGY'
	private static final String TITLE_SENSOR_TECHNOLOGY = 'TITLE_SENSOR_TECHNOLOGY'
	private static final String TITLE_SENSOR_TECHNOLOGY_RETCONNED = 'TITLE_SENSOR_TECHNOLOGY_RETCONNED'
	private static final String TITLE_SHIELD_TECHNOLOGY = 'TITLE_SHIELD_TECHNOLOGY'
	private static final String TITLE_SHIELD_TECHNOLOGY_RETCONNED = 'TITLE_SHIELD_TECHNOLOGY_RETCONNED'
	private static final String TITLE_TOOLS = 'TITLE_TOOLS'
	private static final String TITLE_CULINARY_TOOLS = 'TITLE_CULINARY_TOOLS'
	private static final String TITLE_ENGINEERING_TOOLS = 'TITLE_ENGINEERING_TOOLS'
	private static final String TITLE_HOUSEHOLD_TOOLS = 'TITLE_HOUSEHOLD_TOOLS'
	private static final String TITLE_MEDICAL_EQUIPMENT = 'TITLE_MEDICAL_EQUIPMENT'
	private static final String TITLE_TRANSPORTER_TECHNOLOGY = 'TITLE_TRANSPORTER_TECHNOLOGY'

	private CategoryApi categoryApiMock

	private StepCompletenessDecider jobCompletenessDeciderMock

	private TechnologyCreationConfiguration technologyCreationConfiguration

	void setup() {
		categoryApiMock = Mock()
		jobCompletenessDeciderMock = Mock()
		technologyCreationConfiguration = new TechnologyCreationConfiguration(
				categoryApi: categoryApiMock,
				stepCompletenessDecider: jobCompletenessDeciderMock)
	}

	@SuppressWarnings('LineLength')
	void "TechnologyReader is created is created with all pages when step is not completed"() {
		when:
		TechnologyReader technologyReader = technologyCreationConfiguration.technologyReader()
		List<String> categoryHeaderTitleList = pageHeaderReaderToList(technologyReader)

		then:
		1 * jobCompletenessDeciderMock.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_TECHNOLOGY) >> false
		1 * categoryApiMock.getPages(CategoryTitle.TECHNOLOGY, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_TECHNOLOGY)
		1 * categoryApiMock.getPages(CategoryTitle.BORG_TECHNOLOGY, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_BORG_TECHNOLOGY)
		1 * categoryApiMock.getPages(CategoryTitle.BORG_COMPONENTS, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_BORG_COMPONENTS)
		1 * categoryApiMock.getPages(CategoryTitle.COMMUNICATIONS_TECHNOLOGY, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_COMMUNICATIONS_TECHNOLOGY)
		1 * categoryApiMock.getPages(CategoryTitle.COMMUNICATIONS_TECHNOLOGY_RETCONNED, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_COMMUNICATIONS_TECHNOLOGY_RETCONNED)
		1 * categoryApiMock.getPages(CategoryTitle.COMPUTER_TECHNOLOGY, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_COMPUTER_TECHNOLOGY)
		1 * categoryApiMock.getPages(CategoryTitle.COMPUTER_PROGRAMMING, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_COMPUTER_PROGRAMMING)
		1 * categoryApiMock.getPages(CategoryTitle.SUBROUTINES, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_SUBROUTINES)
		1 * categoryApiMock.getPages(CategoryTitle.DATABASES, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_DATABASES)
		1 * categoryApiMock.getPages(CategoryTitle.ENERGY_TECHNOLOGY, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_ENERGY_TECHNOLOGY)
		1 * categoryApiMock.getPages(CategoryTitle.ENERGY_TECHNOLOGY_RETCONNED, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_ENERGY_TECHNOLOGY_RETCONNED)
		1 * categoryApiMock.getPages(CategoryTitle.FICTIONAL_TECHNOLOGY, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_FICTIONAL_TECHNOLOGY)
		1 * categoryApiMock.getPages(CategoryTitle.HOLOGRAPHIC_TECHNOLOGY, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_HOLOGRAPHIC_TECHNOLOGY)
		1 * categoryApiMock.getPages(CategoryTitle.IDENTIFICATION_TECHNOLOGY, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_IDENTIFICATION_TECHNOLOGY)
		1 * categoryApiMock.getPages(CategoryTitle.LIFE_SUPPORT_TECHNOLOGY, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_LIFE_SUPPORT_TECHNOLOGY)
		1 * categoryApiMock.getPages(CategoryTitle.SENSOR_TECHNOLOGY, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_SENSOR_TECHNOLOGY)
		1 * categoryApiMock.getPages(CategoryTitle.SENSOR_TECHNOLOGY_RETCONNED, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_SENSOR_TECHNOLOGY_RETCONNED)
		1 * categoryApiMock.getPages(CategoryTitle.SHIELD_TECHNOLOGY, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_SHIELD_TECHNOLOGY)
		1 * categoryApiMock.getPages(CategoryTitle.SHIELD_TECHNOLOGY_RETCONNED, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_SHIELD_TECHNOLOGY_RETCONNED)
		1 * categoryApiMock.getPages(CategoryTitle.TOOLS, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_TOOLS)
		1 * categoryApiMock.getPages(CategoryTitle.CULINARY_TOOLS, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_CULINARY_TOOLS)
		1 * categoryApiMock.getPages(CategoryTitle.ENGINEERING_TOOLS, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_ENGINEERING_TOOLS)
		1 * categoryApiMock.getPages(CategoryTitle.HOUSEHOLD_TOOLS, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_HOUSEHOLD_TOOLS)
		1 * categoryApiMock.getPages(CategoryTitle.MEDICAL_EQUIPMENT, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_MEDICAL_EQUIPMENT)
		1 * categoryApiMock.getPages(CategoryTitle.TRANSPORTER_TECHNOLOGY, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_TRANSPORTER_TECHNOLOGY)
		0 * _
		categoryHeaderTitleList.contains TITLE_BORG_TECHNOLOGY
		categoryHeaderTitleList.contains TITLE_BORG_COMPONENTS
		categoryHeaderTitleList.contains TITLE_COMMUNICATIONS_TECHNOLOGY
		categoryHeaderTitleList.contains TITLE_COMMUNICATIONS_TECHNOLOGY_RETCONNED
		categoryHeaderTitleList.contains TITLE_COMPUTER_TECHNOLOGY
		categoryHeaderTitleList.contains TITLE_COMPUTER_PROGRAMMING
		categoryHeaderTitleList.contains TITLE_SUBROUTINES
		categoryHeaderTitleList.contains TITLE_DATABASES
		categoryHeaderTitleList.contains TITLE_ENERGY_TECHNOLOGY
		categoryHeaderTitleList.contains TITLE_ENERGY_TECHNOLOGY_RETCONNED
		categoryHeaderTitleList.contains TITLE_FICTIONAL_TECHNOLOGY
		categoryHeaderTitleList.contains TITLE_HOLOGRAPHIC_TECHNOLOGY
		categoryHeaderTitleList.contains TITLE_IDENTIFICATION_TECHNOLOGY
		categoryHeaderTitleList.contains TITLE_LIFE_SUPPORT_TECHNOLOGY
		categoryHeaderTitleList.contains TITLE_SENSOR_TECHNOLOGY
		categoryHeaderTitleList.contains TITLE_SENSOR_TECHNOLOGY_RETCONNED
		categoryHeaderTitleList.contains TITLE_SHIELD_TECHNOLOGY
		categoryHeaderTitleList.contains TITLE_SHIELD_TECHNOLOGY_RETCONNED
		categoryHeaderTitleList.contains TITLE_TOOLS
		categoryHeaderTitleList.contains TITLE_CULINARY_TOOLS
		categoryHeaderTitleList.contains TITLE_ENGINEERING_TOOLS
		categoryHeaderTitleList.contains TITLE_HOUSEHOLD_TOOLS
		categoryHeaderTitleList.contains TITLE_MEDICAL_EQUIPMENT
		categoryHeaderTitleList.contains TITLE_TRANSPORTER_TECHNOLOGY
	}

	void "TechnologyReader is created with no pages when step is completed"() {
		when:
		TechnologyReader technologyReader = technologyCreationConfiguration.technologyReader()
		List<String> categoryHeaderTitleList = pageHeaderReaderToList(technologyReader)

		then:
		1 * jobCompletenessDeciderMock.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_TECHNOLOGY) >> true
		0 * _
		categoryHeaderTitleList.empty
	}

}
