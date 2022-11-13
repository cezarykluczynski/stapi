package com.cezarykluczynski.stapi.etl.astronomical_object.link.processor

import com.cezarykluczynski.stapi.etl.common.dto.FixedValueHolder
import com.cezarykluczynski.stapi.model.astronomical_object.entity.AstronomicalObject
import com.cezarykluczynski.stapi.model.astronomical_object.repository.AstronomicalObjectRepository
import com.cezarykluczynski.stapi.model.page.entity.enums.MediaWikiSource
import spock.lang.Specification

class AstronomicalObjectLocationFixedValueProviderTest extends Specification {

	private AstronomicalObjectRepository astronomicalObjectRepositoryMock

	private AstronomicalObjectLocationFixedValueProvider astronomicalObjectLocationFixedValueProvider

	void setup() {
		astronomicalObjectRepositoryMock = Mock()
		astronomicalObjectLocationFixedValueProvider = new AstronomicalObjectLocationFixedValueProvider(astronomicalObjectRepositoryMock)
	}

	void "when mapping is found in DB, it is returned"() {
		given:
		AstronomicalObject quadrant = new AstronomicalObject(name: 'Alpha Quadrant')
		AstronomicalObject galaxy = Mock()

		when:
		FixedValueHolder<AstronomicalObject> fixedValueHolder = astronomicalObjectLocationFixedValueProvider.getSearchedValue(quadrant)

		then:
		1 * astronomicalObjectRepositoryMock.findByPageTitleAndPageMediaWikiSource('Milky Way Galaxy',
				MediaWikiSource.MEMORY_ALPHA_EN) >> Optional.of(galaxy)
		0 * _
		fixedValueHolder.found
		fixedValueHolder.value == galaxy
	}

	void "when unknown astronomical object is passed, nothing is found"() {
		given:
		AstronomicalObject quadrant = new AstronomicalObject(name: 'Sol')

		when:
		FixedValueHolder<AstronomicalObject> fixedValueHolder = astronomicalObjectLocationFixedValueProvider.getSearchedValue(quadrant)

		then:
		0 * _
		!fixedValueHolder.found
		fixedValueHolder.value == null
	}

	void "when known astronomical object is passed, but repository does not have it, nothing is found"() {
		given:
		AstronomicalObject quadrant = new AstronomicalObject(name: 'Alpha Quadrant')

		when:
		FixedValueHolder<AstronomicalObject> fixedValueHolder = astronomicalObjectLocationFixedValueProvider.getSearchedValue(quadrant)

		then:
		1 * astronomicalObjectRepositoryMock.findByPageTitleAndPageMediaWikiSource('Milky Way Galaxy',
				MediaWikiSource.MEMORY_ALPHA_EN) >> Optional.empty()
		0 * _
		!fixedValueHolder.found
		fixedValueHolder.value == null
	}

}
