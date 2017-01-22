package com.cezarykluczynski.stapi.etl.template.planet.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.template.planet.dto.PlanetTemplate
import com.cezarykluczynski.stapi.etl.template.planet.dto.enums.AstronomicalObjectType
import com.cezarykluczynski.stapi.etl.util.constant.CategoryName
import com.cezarykluczynski.stapi.sources.mediawiki.dto.CategoryHeader
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import com.google.common.collect.Lists
import spock.lang.Specification
import spock.lang.Unroll

class AstronomicalObjectTypeEnrichingProcessorTest extends Specification {

	private AstronomicalObjectTypeEnrichingProcessor astronomicalObjectTypeEnrichingProcessor

	void setup() {
		astronomicalObjectTypeEnrichingProcessor = new AstronomicalObjectTypeEnrichingProcessor()
	}

	@Unroll('when #categoryTitle is present in page, #astronomicalObjectType is set to planet template')
	void "types are determined from categories"() {
		expect:
		Page page = new Page(categories: Lists.newArrayList(new CategoryHeader(title: categoryTitle)))
		PlanetTemplate planetTemplate = new PlanetTemplate()
		astronomicalObjectTypeEnrichingProcessor.enrich(EnrichablePair.of(page, planetTemplate))
		astronomicalObjectType == planetTemplate.astronomicalObjectType

		where:
		categoryTitle               | astronomicalObjectType
		null                        | null
		CategoryName.PLANETS        | AstronomicalObjectType.PLANET
		CategoryName.ASTEROIDS      | AstronomicalObjectType.ASTEROID
		CategoryName.ASTEROID_BELTS | AstronomicalObjectType.ASTEROID_BELT
		CategoryName.COMETS         | AstronomicalObjectType.COMET
		CategoryName.CONSTELLATIONS | AstronomicalObjectType.CONSTELLATION
		CategoryName.GALAXIES       | AstronomicalObjectType.GALAXY
		CategoryName.MOONS          | AstronomicalObjectType.MOON
		CategoryName.NEBULAE        | AstronomicalObjectType.NEBULA
		CategoryName.PLANETOIDS     | AstronomicalObjectType.PLANETOID
		CategoryName.QUASARS        | AstronomicalObjectType.QUASAR
		CategoryName.STAR_SYSTEMS   | AstronomicalObjectType.STAR_SYSTEM
		CategoryName.STARS          | AstronomicalObjectType.STAR
	}

}
