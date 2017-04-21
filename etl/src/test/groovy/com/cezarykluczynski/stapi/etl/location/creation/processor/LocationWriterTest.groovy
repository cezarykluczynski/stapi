package com.cezarykluczynski.stapi.etl.location.creation.processor

import com.cezarykluczynski.stapi.model.location.entity.Location
import com.cezarykluczynski.stapi.model.location.repository.LocationRepository
import com.cezarykluczynski.stapi.model.page.service.DuplicateFilteringPreSavePageAwareFilter
import com.google.common.collect.Lists
import spock.lang.Specification

class LocationWriterTest extends Specification {

	private LocationRepository locationRepositoryMock

	private DuplicateFilteringPreSavePageAwareFilter duplicateFilteringPreSavePageAwareProcessorMock

	private LocationWriter locationWriterMock

	void setup() {
		locationRepositoryMock = Mock()
		duplicateFilteringPreSavePageAwareProcessorMock = Mock()
		locationWriterMock = new LocationWriter(locationRepositoryMock, duplicateFilteringPreSavePageAwareProcessorMock)
	}

	void "filters all entities using pre save processor, then writes all entities using repository"() {
		given:
		Location location = new Location()
		List<Location> locationList = Lists.newArrayList(location)

		when:
		locationWriterMock.write(locationList)

		then:
		1 * duplicateFilteringPreSavePageAwareProcessorMock.process(_, Location) >> { args ->
			assert args[0][0] == location
			locationList
		}
		1 * locationRepositoryMock.save(locationList)
		0 * _
	}

}
