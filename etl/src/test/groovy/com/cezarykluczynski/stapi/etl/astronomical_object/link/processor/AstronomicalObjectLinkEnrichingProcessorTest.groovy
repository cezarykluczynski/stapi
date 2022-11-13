package com.cezarykluczynski.stapi.etl.astronomical_object.link.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.model.astronomical_object.entity.AstronomicalObject
import com.cezarykluczynski.stapi.model.astronomical_object.entity.enums.AstronomicalObjectType
import spock.lang.Specification

class AstronomicalObjectLinkEnrichingProcessorTest extends Specification {

	private static final String SUBJECT = 'SUBJECT'
	private static final String LOCATION_CANDIDATE = 'LOCATION_CANDIDATE'

	private AstronomicalObjectLinkEnrichingProcessor astronomicalObjectLinkEnrichingProcessor

	void setup() {
		astronomicalObjectLinkEnrichingProcessor = new AstronomicalObjectLinkEnrichingProcessor()
	}

	void "all enum values are in the map"() {
		expect:
		AstronomicalObjectLinkEnrichingProcessor.ASTRONOMICAL_OBJECTS_SIZE_MAP.size() == AstronomicalObjectType.values().size()
	}

	void "returns null when location candidates list is empty"() {
		given:
		AstronomicalObject subject = new AstronomicalObject(astronomicalObjectType: AstronomicalObjectType.M_CLASS_MOON)

		when:
		astronomicalObjectLinkEnrichingProcessor.enrich(EnrichablePair.of([], subject))

		then:
		subject.location == null
	}

	void "does not set location when size of both objects is equal"() {
		given:
		AstronomicalObject locationCandidate = new AstronomicalObject(astronomicalObjectType: AstronomicalObjectType.M_CLASS_MOON)
		AstronomicalObject subject = new AstronomicalObject(astronomicalObjectType: AstronomicalObjectType.M_CLASS_MOON)

		when:
		astronomicalObjectLinkEnrichingProcessor.enrich(EnrichablePair.of([locationCandidate], subject))

		then:
		subject.location == null
	}

	void "does not set location when subject is bigger than location candidate"() {
		given:
		AstronomicalObject locationCandidate = new AstronomicalObject(astronomicalObjectType: AstronomicalObjectType.STAR)
		AstronomicalObject subject = new AstronomicalObject(astronomicalObjectType: AstronomicalObjectType.GALAXY)

		when:
		astronomicalObjectLinkEnrichingProcessor.enrich(EnrichablePair.of([locationCandidate], subject))

		then:
		subject.location == null
	}

	void "does not set location when location candidate is bigger than subject"() {
		given:
		AstronomicalObject locationCandidate = new AstronomicalObject(astronomicalObjectType: AstronomicalObjectType.STAR)
		AstronomicalObject subject = new AstronomicalObject(astronomicalObjectType: AstronomicalObjectType.D_CLASS_PLANET)

		when:
		astronomicalObjectLinkEnrichingProcessor.enrich(EnrichablePair.of([locationCandidate], subject))

		then:
		subject.location == locationCandidate
	}

	void "throws exception when subject astronomical object type is null"() {
		given:
		AstronomicalObject locationCandidate = new AstronomicalObject(name: LOCATION_CANDIDATE, astronomicalObjectType: AstronomicalObjectType.STAR)
		AstronomicalObject subject = new AstronomicalObject(name: SUBJECT)

		when:
		astronomicalObjectLinkEnrichingProcessor.enrich(EnrichablePair.of([locationCandidate], subject))

		then:
		RuntimeException runtimeException = thrown(RuntimeException)
		runtimeException.message == 'Subject "SUBJECT" astronomical object type not set.'
	}

	void "sets location from single valid candidate"() {
		given:
		AstronomicalObject locationCandidate = new AstronomicalObject(name: LOCATION_CANDIDATE, astronomicalObjectType: AstronomicalObjectType.STAR_SYSTEM)
		AstronomicalObject subject = new AstronomicalObject(name: SUBJECT, astronomicalObjectType: AstronomicalObjectType.STAR)

		when:
		astronomicalObjectLinkEnrichingProcessor.enrich(EnrichablePair.of([locationCandidate], subject))

		then:
		subject.location == locationCandidate
	}

	void "sets location from list of candidates, picking the best one"() {
		given:
		AstronomicalObject locationCandidateGalaxy = new AstronomicalObject(
				name: 'Milky Way Galaxy', astronomicalObjectType: AstronomicalObjectType.GALAXY)
		AstronomicalObject locationCandidateQuadrant = new AstronomicalObject(
				name: 'Alpha Quadrant', astronomicalObjectType: AstronomicalObjectType.QUADRANT)
		AstronomicalObject locationCandidate = new AstronomicalObject(
				name: LOCATION_CANDIDATE, astronomicalObjectType: AstronomicalObjectType.STAR_SYSTEM)
		AstronomicalObject subject = new AstronomicalObject(name: SUBJECT, astronomicalObjectType: AstronomicalObjectType.STAR)
		List<AstronomicalObject> candidates = [locationCandidateGalaxy, locationCandidateQuadrant, locationCandidate]

		when:
		astronomicalObjectLinkEnrichingProcessor.enrich(EnrichablePair.of(candidates, subject))

		then:
		subject.location == locationCandidate
	}

}
