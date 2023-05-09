package com.cezarykluczynski.stapi.etl.location.creation.processor

import com.cezarykluczynski.stapi.model.location.entity.Location
import com.cezarykluczynski.stapi.model.location.repository.LocationRepository
import com.google.common.collect.Lists
import org.springframework.batch.item.Chunk
import spock.lang.Specification

class LocationWriterTest extends Specification {

	private LocationRepository locationRepositoryMock

	private LocationWriter locationWriterMock

	void setup() {
		locationRepositoryMock = Mock()
		locationWriterMock = new LocationWriter(locationRepositoryMock)
	}

	void "writes all entities using repository"() {
		given:
		Location location = new Location()
		List<Location> locationList = Lists.newArrayList(location)

		when:
		locationWriterMock.write(new Chunk(locationList))

		then:
		1 * locationRepositoryMock.saveAll(locationList)
		0 * _
	}

}
