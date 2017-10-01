package com.cezarykluczynski.stapi.etl.spacecraft.creation.configuration

import com.cezarykluczynski.stapi.etl.common.configuration.AbstractCreationConfigurationTest
import com.cezarykluczynski.stapi.etl.configuration.job.service.StepCompletenessDecider
import com.cezarykluczynski.stapi.etl.spacecraft.creation.processor.SpacecraftReader
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle
import com.cezarykluczynski.stapi.etl.util.constant.JobName
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.sources.mediawiki.api.CategoryApi
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource
import com.google.common.collect.Lists

class SpacecraftCreationConfigurationTest extends AbstractCreationConfigurationTest {

	private static final String TITLE_SPACECRAFT_ALTERNATE_REALITY = 'TITLE_SPACECRAFT_ALTERNATE_REALITY'
	private static final String TITLE_SHUTTLES_ALTERNATE_REALITY = 'TITLE_SHUTTLES_ALTERNATE_REALITY'
	private static final String TITLE_STARSHIPS_ALTERNATE_REALITY = 'TITLE_STARSHIPS_ALTERNATE_REALITY'
	private static final String TITLE_FEDERATION_STARSHIPS_ALTERNATE_REALITY = 'TITLE_FEDERATION_STARSHIPS_ALTERNATE_REALITY'
	private static final String TITLE_ESCAPE_PODS = 'TITLE_ESCAPE_PODS'
	private static final String TITLE_PROBES = 'TITLE_PROBES'
	private static final String TITLE_EARTH_SPACECRAFT = 'TITLE_EARTH_SPACECRAFT'
	private static final String TITLE_SHUTTLES = 'TITLE_SHUTTLES'
	private static final String TITLE_SPACE_STATIONS = 'TITLE_SPACE_STATIONS'
	private static final String TITLE_STARSHIPS = 'TITLE_STARSHIPS'

	private CategoryApi categoryApiMock

	private StepCompletenessDecider jobCompletenessDeciderMock

	private SpacecraftCreationConfiguration spacecraftCreationConfiguration

	void setup() {
		categoryApiMock = Mock()
		jobCompletenessDeciderMock = Mock()
		spacecraftCreationConfiguration = new SpacecraftCreationConfiguration(
				categoryApi: categoryApiMock,
				stepCompletenessDecider: jobCompletenessDeciderMock)
	}

	void "SpacecraftReader is created with all pages when step is not completed"() {
		when:
		SpacecraftReader planetReader = spacecraftCreationConfiguration.spacecraftReader()
		List<String> categoryHeaderTitleList = pageHeaderReaderToList(planetReader)

		then:
		1 * jobCompletenessDeciderMock.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_SPACECRAFTS) >> false
		1 * categoryApiMock.getPages(CategoryTitle.SPACECRAFT_ALTERNATE_REALITY, MediaWikiSource.MEMORY_ALPHA_EN) >>
				createListWithPageHeaderTitle(TITLE_SPACECRAFT_ALTERNATE_REALITY)
		1 * categoryApiMock.getPages(CategoryTitle.SHUTTLES_ALTERNATE_REALITY, MediaWikiSource.MEMORY_ALPHA_EN) >>
				createListWithPageHeaderTitle(TITLE_SHUTTLES_ALTERNATE_REALITY)
		1 * categoryApiMock.getPages(CategoryTitle.STARSHIPS_ALTERNATE_REALITY, MediaWikiSource.MEMORY_ALPHA_EN) >>
				createListWithPageHeaderTitle(TITLE_STARSHIPS_ALTERNATE_REALITY)
		1 * categoryApiMock.getPages(CategoryTitle.FEDERATION_STARSHIPS_ALTERNATE_REALITY, MediaWikiSource.MEMORY_ALPHA_EN) >>
				createListWithPageHeaderTitle(TITLE_FEDERATION_STARSHIPS_ALTERNATE_REALITY)
		1 * categoryApiMock.getPages(CategoryTitle.ESCAPE_PODS, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_ESCAPE_PODS)
		1 * categoryApiMock.getPages(CategoryTitle.PROBES, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_PROBES)
		1 * categoryApiMock.getPagesIncludingSubcategories(CategoryTitle.EARTH_SPACECRAFT, MediaWikiSource.MEMORY_ALPHA_EN) >>
				createListWithPageHeaderTitle(TITLE_EARTH_SPACECRAFT)
		1 * categoryApiMock.getPagesIncludingSubcategories(CategoryTitle.SHUTTLES, MediaWikiSource.MEMORY_ALPHA_EN) >>
				createListWithPageHeaderTitle(TITLE_SHUTTLES)
		1 * categoryApiMock.getPagesIncludingSubcategories(CategoryTitle.SPACE_STATIONS, MediaWikiSource.MEMORY_ALPHA_EN) >>
				createListWithPageHeaderTitle(TITLE_SPACE_STATIONS)
		1 * categoryApiMock.getPagesIncludingSubcategoriesExcept(CategoryTitle.STARSHIPS, Lists
				.newArrayList(CategoryTitle.MEMORY_ALPHA_NON_CANON_REDIRECTS_STARSHIPS), MediaWikiSource.MEMORY_ALPHA_EN) >>
				createListWithPageHeaderTitle(TITLE_STARSHIPS)
		0 * _
		categoryHeaderTitleList.contains TITLE_SPACECRAFT_ALTERNATE_REALITY
		categoryHeaderTitleList.contains TITLE_SHUTTLES_ALTERNATE_REALITY
		categoryHeaderTitleList.contains TITLE_STARSHIPS_ALTERNATE_REALITY
		categoryHeaderTitleList.contains TITLE_FEDERATION_STARSHIPS_ALTERNATE_REALITY
		categoryHeaderTitleList.contains TITLE_ESCAPE_PODS
		categoryHeaderTitleList.contains TITLE_PROBES
		categoryHeaderTitleList.contains TITLE_EARTH_SPACECRAFT
		categoryHeaderTitleList.contains TITLE_SHUTTLES
		categoryHeaderTitleList.contains TITLE_SPACE_STATIONS
		categoryHeaderTitleList.contains TITLE_STARSHIPS
	}

	void "SpacecraftReader is created with no pages when step is completed"() {
		when:
		SpacecraftReader planetReader = spacecraftCreationConfiguration.spacecraftReader()
		List<String> categoryHeaderTitleList = pageHeaderReaderToList(planetReader)

		then:
		1 * jobCompletenessDeciderMock.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_SPACECRAFTS) >> true
		0 * _
		categoryHeaderTitleList.empty
	}

}
