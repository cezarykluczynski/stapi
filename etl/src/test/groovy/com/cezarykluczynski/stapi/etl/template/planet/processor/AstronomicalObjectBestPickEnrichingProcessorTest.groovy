package com.cezarykluczynski.stapi.etl.template.planet.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.template.planet.dto.PlanetTemplate
import com.cezarykluczynski.stapi.etl.template.planet.dto.enums.AstronomicalObjectType
import org.apache.commons.lang3.tuple.Pair
import spock.lang.Specification
import spock.lang.Unroll

class AstronomicalObjectBestPickEnrichingProcessorTest extends Specification {

	private AstronomicalObjectBestPickEnrichingProcessor astronomicalObjectBestPickEnrichingProcessor

	void setup() {
		astronomicalObjectBestPickEnrichingProcessor = new AstronomicalObjectBestPickEnrichingProcessor()
	}

	@Unroll('sets #result from #left and #right')
	void "sets best type from two types"() {
		expect:
		Pair<AstronomicalObjectType, AstronomicalObjectType> pair = Pair.of(left, right)
		PlanetTemplate planetTemplate = new PlanetTemplate()
		astronomicalObjectBestPickEnrichingProcessor.enrich(EnrichablePair.of(pair, planetTemplate))
		result == planetTemplate.astronomicalObjectType

		where:
		left                                   | right                                 | result
		null                                   | null                                  | null
		AstronomicalObjectType.MOON            | AstronomicalObjectType.M_CLASS_PLANET | AstronomicalObjectType.M_CLASS_MOON
		AstronomicalObjectType.PLANETOID       | AstronomicalObjectType.D_CLASS_PLANET | AstronomicalObjectType.D_CLASS_PLANETOID
		AstronomicalObjectType.MOON            | AstronomicalObjectType.ASTEROID       | AstronomicalObjectType.ASTEROIDAL_MOON
		AstronomicalObjectType.M_CLASS_PLANET  | null                                  | AstronomicalObjectType.M_CLASS_PLANET
		null                                   | AstronomicalObjectType.M_CLASS_PLANET | AstronomicalObjectType.M_CLASS_PLANET
		AstronomicalObjectType.D_CLASS_PLANET  | AstronomicalObjectType.M_CLASS_PLANET | AstronomicalObjectType.M_CLASS_PLANET
		AstronomicalObjectType.D_CLASS_PLANET  | AstronomicalObjectType.PLANET         | AstronomicalObjectType.D_CLASS_PLANET
		AstronomicalObjectType.ASTEROIDAL_MOON | AstronomicalObjectType.MOON           | AstronomicalObjectType.ASTEROIDAL_MOON
		AstronomicalObjectType.CLUSTER         | AstronomicalObjectType.CLUSTER        | AstronomicalObjectType.CLUSTER
	}

}
