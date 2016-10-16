package com.cezarykluczynski.stapi.etl.performer.creation.processor

import com.cezarykluczynski.stapi.model.performer.entity.Performer
import com.cezarykluczynski.stapi.model.performer.repository.PerformerRepository
import com.google.common.collect.Lists
import spock.lang.Specification

class PerformerWriterTest extends Specification {

	private PerformerRepository performerRepositoryMock

	private PerformerWriter performerWriterMock

	def setup() {
		performerRepositoryMock = Mock(PerformerRepository)
		performerWriterMock = new PerformerWriter(performerRepositoryMock)
	}

	def "writes all entites using repository"() {
		given:
		List<Performer> seriesList = Lists.newArrayList()

		when:
		performerWriterMock.write(seriesList)

		then:
		1 * performerRepositoryMock.save(seriesList)
	}

}
