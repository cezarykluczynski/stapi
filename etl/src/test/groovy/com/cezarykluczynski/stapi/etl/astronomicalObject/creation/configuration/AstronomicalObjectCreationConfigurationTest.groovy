package com.cezarykluczynski.stapi.etl.astronomicalObject.creation.configuration

import com.cezarykluczynski.stapi.etl.common.configuration.AbstractCreationConfigurationTest
import com.cezarykluczynski.stapi.etl.configuration.job.service.StepCompletenessDecider
import com.cezarykluczynski.stapi.etl.astronomicalObject.creation.processor.AstronomicalObjectReader
import com.cezarykluczynski.stapi.etl.util.constant.CategoryName
import com.cezarykluczynski.stapi.etl.util.constant.JobName
import com.cezarykluczynski.stapi.etl.util.constant.StepName
import com.cezarykluczynski.stapi.sources.mediawiki.api.CategoryApi
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource

class AstronomicalObjectCreationConfigurationTest extends AbstractCreationConfigurationTest {

	private static final String TITLE_ASTEROID = 'TITLE_ASTEROID'
	private static final String TITLE_ASTEROID_BELT = 'TITLE_ASTEROID_BELT'
	private static final String TITLE_COMET = 'TITLE_COMET'
	private static final String TITLE_CONSTELLATION = 'TITLE_CONSTELLATION'
	private static final String TITLE_GALAXY = 'TITLE_GALAXY'
	private static final String TITLE_HOMEWORLD = 'TITLE_HOMEWORLD'
	private static final String TITLE_MOON = 'TITLE_MOON'
	private static final String TITLE_NEBULA = 'TITLE_NEBULA'
	private static final String TITLE_PLANET = 'TITLE_PLANET'
	private static final String TITLE_PLANETOID = 'TITLE_PLANETOID'
	private static final String TITLE_QUASAR = 'TITLE_QUASAR'
	private static final String TITLE_STAR_SYSTEM = 'TITLE_STAR_SYSTEM'
	private static final String TITLE_STAR = 'TITLE_STAR'

	private CategoryApi categoryApiMock

	private StepCompletenessDecider jobCompletenessDeciderMock

	private AstronomicalObjectCreationConfiguration astronomicalObjectCreationConfiguration

	void setup() {
		categoryApiMock = Mock(CategoryApi)
		jobCompletenessDeciderMock = Mock(StepCompletenessDecider)
		astronomicalObjectCreationConfiguration = new AstronomicalObjectCreationConfiguration(
				categoryApi: categoryApiMock,
				stepCompletenessDecider: jobCompletenessDeciderMock)
	}

	void "PlanetReader is created with all pages when step is not completed"() {
		when:
		AstronomicalObjectReader planetReader = astronomicalObjectCreationConfiguration.astronomicalObjectReader()
		List<String> categoryHeaderTitleList = readerToList(planetReader)

		then:
		1 * jobCompletenessDeciderMock.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_ASTRONOMICAL_OBJECTS) >> false
		1 * categoryApiMock.getPages(CategoryName.ASTEROIDS, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_ASTEROID)
		1 * categoryApiMock.getPages(CategoryName.ASTEROID_BELTS, MediaWikiSource.MEMORY_ALPHA_EN) >>
				createListWithPageHeaderTitle(TITLE_ASTEROID_BELT)
		1 * categoryApiMock.getPages(CategoryName.COMETS, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_COMET)
		1 * categoryApiMock.getPages(CategoryName.CONSTELLATIONS, MediaWikiSource.MEMORY_ALPHA_EN) >>
				createListWithPageHeaderTitle(TITLE_CONSTELLATION)
		1 * categoryApiMock.getPages(CategoryName.GALAXIES, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_GALAXY)
		1 * categoryApiMock.getPages(CategoryName.HOMEWORLDS, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_HOMEWORLD)
		1 * categoryApiMock.getPages(CategoryName.MOONS, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_MOON)
		1 * categoryApiMock.getPages(CategoryName.NEBULAE, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_NEBULA)
		1 * categoryApiMock.getPages(CategoryName.PLANETS, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_PLANET)
		1 * categoryApiMock.getPages(CategoryName.PLANETOIDS, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_PLANETOID)
		1 * categoryApiMock.getPages(CategoryName.QUASARS, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_QUASAR)
		1 * categoryApiMock.getPages(CategoryName.STAR_SYSTEMS, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_STAR_SYSTEM)
		1 * categoryApiMock.getPages(CategoryName.STARS, MediaWikiSource.MEMORY_ALPHA_EN) >> createListWithPageHeaderTitle(TITLE_STAR)
		0 * _
		categoryHeaderTitleList.contains TITLE_ASTEROID
		categoryHeaderTitleList.contains TITLE_ASTEROID_BELT
		categoryHeaderTitleList.contains TITLE_COMET
		categoryHeaderTitleList.contains TITLE_CONSTELLATION
		categoryHeaderTitleList.contains TITLE_GALAXY
		categoryHeaderTitleList.contains TITLE_HOMEWORLD
		categoryHeaderTitleList.contains TITLE_MOON
		categoryHeaderTitleList.contains TITLE_NEBULA
		categoryHeaderTitleList.contains TITLE_PLANET
		categoryHeaderTitleList.contains TITLE_PLANETOID
		categoryHeaderTitleList.contains TITLE_QUASAR
		categoryHeaderTitleList.contains TITLE_STAR_SYSTEM
		categoryHeaderTitleList.contains TITLE_STAR
	}

	void "PlanetReader is created with no pages when step is completed"() {
		when:
		AstronomicalObjectReader planetReader = astronomicalObjectCreationConfiguration.astronomicalObjectReader()
		List<String> categoryHeaderTitleList = readerToList(planetReader)

		then:
		1 * jobCompletenessDeciderMock.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_ASTRONOMICAL_OBJECTS) >> true
		0 * _
		categoryHeaderTitleList.empty
	}

}
