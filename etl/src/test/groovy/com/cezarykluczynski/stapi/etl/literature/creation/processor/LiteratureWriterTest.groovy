package com.cezarykluczynski.stapi.etl.literature.creation.processor

import com.cezarykluczynski.stapi.model.literature.entity.Literature
import com.cezarykluczynski.stapi.model.literature.repository.LiteratureRepository
import com.google.common.collect.Lists
import org.springframework.batch.item.Chunk
import spock.lang.Specification

class LiteratureWriterTest extends Specification {

	private LiteratureRepository literatureRepositoryMock

	private LiteratureWriter literatureWriterMock

	void setup() {
		literatureRepositoryMock = Mock()
		literatureWriterMock = new LiteratureWriter(literatureRepositoryMock)
	}

	void "writes all entities using repository"() {
		given:
		Literature literature = new Literature()
		List<Literature> literatureList = Lists.newArrayList(literature)

		when:
		literatureWriterMock.write(new Chunk(literatureList))

		then:
		1 * literatureRepositoryMock.saveAll(literatureList)
		0 * _
	}

}
