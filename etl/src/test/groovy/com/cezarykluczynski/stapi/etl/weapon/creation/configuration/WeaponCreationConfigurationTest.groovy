package com.cezarykluczynski.stapi.etl.weapon.creation.configuration

import com.cezarykluczynski.stapi.etl.common.configuration.AbstractCreationConfigurationTest
import com.cezarykluczynski.stapi.etl.configuration.job.service.StepCompletenessDecider
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitles
import com.cezarykluczynski.stapi.etl.util.constant.JobName
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.etl.weapon.creation.processor.WeaponReader
import com.cezarykluczynski.stapi.etl.mediawiki.api.CategoryApi
import com.cezarykluczynski.stapi.etl.mediawiki.api.enums.MediaWikiSource

class WeaponCreationConfigurationTest extends AbstractCreationConfigurationTest {

	private static final String TITLE_WEAPONS = 'TITLE_WEAPONS'

	private CategoryApi categoryApiMock

	private StepCompletenessDecider jobCompletenessDeciderMock

	private WeaponCreationConfiguration weaponCreationConfiguration

	void setup() {
		categoryApiMock = Mock()
		jobCompletenessDeciderMock = Mock()
		weaponCreationConfiguration = new WeaponCreationConfiguration(
				categoryApi: categoryApiMock,
				stepCompletenessDecider: jobCompletenessDeciderMock)
	}

	void "WeaponReader is created is created with all pages when step is not completed"() {
		when:
		WeaponReader weaponReader = weaponCreationConfiguration.weaponReader()
		List<String> categoryHeaderTitleList = pageHeaderReaderToList(weaponReader)

		then:
		1 * jobCompletenessDeciderMock.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_WEAPONS) >> false
		1 * categoryApiMock.getPages(CategoryTitles.WEAPONS, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_WEAPONS)
		0 * _
		categoryHeaderTitleList.contains TITLE_WEAPONS
	}

	void "WeaponReader is created with no pages when step is completed"() {
		when:
		WeaponReader weaponReader = weaponCreationConfiguration.weaponReader()
		List<String> categoryHeaderTitleList = pageHeaderReaderToList(weaponReader)

		then:
		1 * jobCompletenessDeciderMock.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_WEAPONS) >> true
		0 * _
		categoryHeaderTitleList.empty
	}

}
