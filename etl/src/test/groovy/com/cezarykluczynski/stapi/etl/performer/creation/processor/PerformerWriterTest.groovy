package com.cezarykluczynski.stapi.etl.performer.creation.processor

import com.cezarykluczynski.stapi.model.page.entity.Page
import com.cezarykluczynski.stapi.model.performer.entity.Performer
import com.cezarykluczynski.stapi.model.performer.repository.PerformerRepository
import com.google.common.collect.Lists
import org.springframework.batch.item.Chunk
import spock.lang.Specification

class PerformerWriterTest extends Specification {

	private static final Long PAGE_ID = 1L

	private PerformerRepository performerRepositoryMock

	private PerformerWriter performerWriterMock

	void setup() {
		performerRepositoryMock = Mock()
		performerWriterMock = new PerformerWriter(performerRepositoryMock)
	}

	void "writes all entities using repository"() {
		given:
		Performer performer = new Performer(page: new Page(pageId: PAGE_ID))
		List<Performer> seriesList = Lists.newArrayList(performer)

		when:
		performerWriterMock.write(new Chunk(seriesList))

		then:
		1 * performerRepositoryMock.saveAll(seriesList)
		0 * _
	}

}
