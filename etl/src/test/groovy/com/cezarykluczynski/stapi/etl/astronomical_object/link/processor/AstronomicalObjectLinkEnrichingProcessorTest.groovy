package com.cezarykluczynski.stapi.etl.astronomical_object.link.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.model.astronomical_object.entity.AstronomicalObject
import com.cezarykluczynski.stapi.model.astronomical_object.entity.enums.AstronomicalObjectType
import spock.lang.Specification

class AstronomicalObjectLinkEnrichingProcessorTest extends Specification {

	private AstronomicalObjectLinkEnrichingProcessor astronomicalObjectLinkEnrichingProcessor

	void setup() {
		astronomicalObjectLinkEnrichingProcessor = new AstronomicalObjectLinkEnrichingProcessor()
	}

	void "all enum values are in the map"() {
		expect:
		AstronomicalObjectLinkEnrichingProcessor.ASTRONOMICAL_OBJECTS_SIZE_MAP.size() == AstronomicalObjectType.values().size()
	}

	void "returns null when location candidate is null"() {
		given:
		AstronomicalObject subject = new AstronomicalObject(astronomicalObjectType: AstronomicalObjectType.M_CLASS_MOON)

		when:
		astronomicalObjectLinkEnrichingProcessor.enrich(EnrichablePair.of(null, subject))

		then:
		subject.location == null
	}

	void "does not set location when size of both objects is equal"() {
		given:
		AstronomicalObject locationCandidate = new AstronomicalObject(astronomicalObjectType: AstronomicalObjectType.M_CLASS_MOON)
		AstronomicalObject subject = new AstronomicalObject(astronomicalObjectType: AstronomicalObjectType.M_CLASS_MOON)

		when:
		astronomicalObjectLinkEnrichingProcessor.enrich(EnrichablePair.of(locationCandidate, subject))

		then:
		subject.location == null
	}

	void "does not set location when subject is bigger than location candidate"() {
		given:
		AstronomicalObject locationCandidate = new AstronomicalObject(astronomicalObjectType: AstronomicalObjectType.STAR)
		AstronomicalObject subject = new AstronomicalObject(astronomicalObjectType: AstronomicalObjectType.GALAXY)

		when:
		astronomicalObjectLinkEnrichingProcessor.enrich(EnrichablePair.of(locationCandidate, subject))

		then:
		subject.location == null
	}

	void "does not set location when location candidate is bigger thansubject"() {
		given:
		AstronomicalObject locationCandidate = new AstronomicalObject(astronomicalObjectType: AstronomicalObjectType.STAR)
		AstronomicalObject subject = new AstronomicalObject(astronomicalObjectType: AstronomicalObjectType.D_CLASS_PLANET)

		when:
		astronomicalObjectLinkEnrichingProcessor.enrich(EnrichablePair.of(locationCandidate, subject))

		then:
		subject.location == locationCandidate
	}

}
