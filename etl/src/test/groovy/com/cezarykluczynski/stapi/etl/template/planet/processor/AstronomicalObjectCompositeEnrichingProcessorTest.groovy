package com.cezarykluczynski.stapi.etl.template.planet.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.template.planet.dto.PlanetTemplate
import com.cezarykluczynski.stapi.etl.template.planet.dto.enums.AstronomicalObjectType
import org.apache.commons.lang3.tuple.Pair
import spock.lang.Specification
import spock.lang.Unroll

class AstronomicalObjectCompositeEnrichingProcessorTest extends Specification {

	private AstronomicalObjectCompositeEnrichingProcessor astronomicalObjectCompositeEnrichingProcessor

	void setup() {
		astronomicalObjectCompositeEnrichingProcessor = new AstronomicalObjectCompositeEnrichingProcessor()
	}

	@Unroll('sets #result from #left and #right')
	void "sets composite type from two simple types"() {
		expect:
		Pair<AstronomicalObjectType, AstronomicalObjectType> pair = Pair.of(left, right)
		PlanetTemplate planetTemplate = new PlanetTemplate()
		astronomicalObjectCompositeEnrichingProcessor.enrich(EnrichablePair.of(pair, planetTemplate))
		result == planetTemplate.astronomicalObjectType

		where:
		left                                  | right                                 | result
		AstronomicalObjectType.MOON           | AstronomicalObjectType.M_CLASS_PLANET | AstronomicalObjectType.M_CLASS_MOON
		AstronomicalObjectType.PLANETOID      | AstronomicalObjectType.D_CLASS_PLANET | AstronomicalObjectType.D_CLASS_PLANETOID
		AstronomicalObjectType.MOON           | AstronomicalObjectType.ASTEROID       | AstronomicalObjectType.ASTEROIDAL_MOON
		AstronomicalObjectType.M_CLASS_PLANET | null                                  | AstronomicalObjectType.M_CLASS_PLANET
		null                                  | AstronomicalObjectType.M_CLASS_PLANET | AstronomicalObjectType.M_CLASS_PLANET
		AstronomicalObjectType.D_CLASS_PLANET | AstronomicalObjectType.M_CLASS_PLANET | AstronomicalObjectType.M_CLASS_PLANET
	}

}
