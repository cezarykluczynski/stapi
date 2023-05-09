package com.cezarykluczynski.stapi.etl.occupation.creation.processor

import com.cezarykluczynski.stapi.model.occupation.entity.Occupation
import com.cezarykluczynski.stapi.model.occupation.repository.OccupationRepository
import com.google.common.collect.Lists
import org.springframework.batch.item.Chunk
import spock.lang.Specification

class OccupationWriterTest extends Specification {

	private OccupationRepository occupationRepositoryMock

	private OccupationWriter occupationWriterMock

	void setup() {
		occupationRepositoryMock = Mock()
		occupationWriterMock = new OccupationWriter(occupationRepositoryMock)
	}

	void "writes all entities using repository"() {
		given:
		Occupation occupation = new Occupation()
		List<Occupation> occupationList = Lists.newArrayList(occupation)

		when:
		occupationWriterMock.write(new Chunk(occupationList))

		then:
		1 * occupationRepositoryMock.saveAll(occupationList)
		0 * _
	}

}
