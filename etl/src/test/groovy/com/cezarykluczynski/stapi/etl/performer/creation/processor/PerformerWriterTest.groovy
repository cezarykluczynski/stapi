package com.cezarykluczynski.stapi.etl.performer.creation.processor

import com.cezarykluczynski.stapi.model.page.service.DuplicateFilteringPreSavePageAwareFilter
import com.cezarykluczynski.stapi.model.performer.entity.Performer
import com.cezarykluczynski.stapi.model.performer.repository.PerformerRepository
import com.google.common.collect.Lists
import spock.lang.Specification

class PerformerWriterTest extends Specification {

	private PerformerRepository performerRepositoryMock

	private DuplicateFilteringPreSavePageAwareFilter duplicateFilteringPreSavePageAwareProcessorMock

	private PerformerWriter performerWriterMock

	def setup() {
		performerRepositoryMock = Mock(PerformerRepository)
		duplicateFilteringPreSavePageAwareProcessorMock = Mock(DuplicateFilteringPreSavePageAwareFilter)
		performerWriterMock = new PerformerWriter(performerRepositoryMock,
				duplicateFilteringPreSavePageAwareProcessorMock)
	}

	def "filters all entities using pre save processor, then writes all entities using repository"() {
		given:
		Performer performer = new Performer()
		List<Performer> performerList = Lists.newArrayList(performer)

		when:
		performerWriterMock.write(performerList)

		then:
		1 * duplicateFilteringPreSavePageAwareProcessorMock.process(_, Performer) >> { args ->
			assert args[0][0] == performer
			return performerList
		}
		1 * performerRepositoryMock.save(performerList)
		0 * _
	}

}
