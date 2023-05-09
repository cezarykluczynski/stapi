package com.cezarykluczynski.stapi.etl.technology.creation.processor

import com.cezarykluczynski.stapi.model.technology.entity.Technology
import com.cezarykluczynski.stapi.model.technology.repository.TechnologyRepository
import com.google.common.collect.Lists
import org.springframework.batch.item.Chunk
import spock.lang.Specification

class TechnologyWriterTest extends Specification {

	private TechnologyRepository technologyRepositoryMock

	private TechnologyWriter technologyWriterMock

	void setup() {
		technologyRepositoryMock = Mock()
		technologyWriterMock = new TechnologyWriter(technologyRepositoryMock)
	}

	void "writes all entities using repository"() {
		given:
		Technology technology = new Technology()
		List<Technology> technologyList = Lists.newArrayList(technology)

		when:
		technologyWriterMock.write(new Chunk(technologyList))

		then:
		1 * technologyRepositoryMock.saveAll(technologyList)
		0 * _
	}

}
