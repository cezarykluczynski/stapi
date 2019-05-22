package com.cezarykluczynski.stapi.etl.template.planet.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.common.processor.CategoryTitlesExtractingProcessor
import com.cezarykluczynski.stapi.etl.template.planet.dto.PlanetTemplate
import com.cezarykluczynski.stapi.etl.template.planet.dto.enums.AstronomicalObjectType
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle
import com.cezarykluczynski.stapi.sources.mediawiki.dto.CategoryHeader
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.google.common.collect.Lists
import spock.lang.Specification
import spock.lang.Unroll

class AstronomicalObjectTypeEnrichingProcessorTest extends Specification {

	private CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessorMock

	private AstronomicalObjectTypeEnrichingProcessor astronomicalObjectTypeEnrichingProcessor

	void setup() {
		categoryTitlesExtractingProcessorMock = Mock()
		astronomicalObjectTypeEnrichingProcessor = new AstronomicalObjectTypeEnrichingProcessor(categoryTitlesExtractingProcessorMock)
	}

	@Unroll('when #categoryTitle is present in page, #astronomicalObjectType is set to planet template')
	void "types are determined from categories"() {
		given:
		categoryTitlesExtractingProcessorMock.process(_ as List<CategoryHeader>) >> {
			List<CategoryHeader> categoryHeaderList -> Lists.newArrayList(categoryHeaderList[0].title)
		}

		expect:
		Page page = new Page(categories: Lists.newArrayList(new CategoryHeader(title: categoryTitle)))
		PlanetTemplate planetTemplate = new PlanetTemplate()
		astronomicalObjectTypeEnrichingProcessor.enrich(EnrichablePair.of(page, planetTemplate))
		astronomicalObjectType == planetTemplate.astronomicalObjectType

		where:
		categoryTitle                   | astronomicalObjectType
		null                            | null
		CategoryTitle.PLANETS           | AstronomicalObjectType.PLANET
		CategoryTitle.PLANETS_RETCONNED | AstronomicalObjectType.PLANET
		CategoryTitle.UNNAMED_PLANETS   | AstronomicalObjectType.PLANET
		CategoryTitle.ASTEROIDS         | AstronomicalObjectType.ASTEROID
		CategoryTitle.ASTEROID_BELTS    | AstronomicalObjectType.ASTEROID_BELT
		CategoryTitle.CLUSTERS          | AstronomicalObjectType.CLUSTER
		CategoryTitle.COMETS            | AstronomicalObjectType.COMET
		CategoryTitle.CONSTELLATIONS    | AstronomicalObjectType.CONSTELLATION
		CategoryTitle.GALAXIES          | AstronomicalObjectType.GALAXY
		CategoryTitle.MOONS             | AstronomicalObjectType.MOON
		CategoryTitle.NEBULAE           | AstronomicalObjectType.NEBULA
		CategoryTitle.NEBULAE_RETCONNED | AstronomicalObjectType.NEBULA
		CategoryTitle.PLANETOIDS        | AstronomicalObjectType.PLANETOID
		CategoryTitle.QUASARS           | AstronomicalObjectType.QUASAR
		CategoryTitle.REGIONS           | AstronomicalObjectType.REGION
		CategoryTitle.SECTORS           | AstronomicalObjectType.SECTOR
		CategoryTitle.STAR_SYSTEMS      | AstronomicalObjectType.STAR_SYSTEM
		CategoryTitle.STARS             | AstronomicalObjectType.STAR
	}

}
