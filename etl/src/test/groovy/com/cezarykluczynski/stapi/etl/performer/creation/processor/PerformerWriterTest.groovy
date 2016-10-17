package com.cezarykluczynski.stapi.etl.performer.creation.processor

import com.cezarykluczynski.stapi.model.page.entity.Page
import com.cezarykluczynski.stapi.model.page.service.DuplicateFilteringPreSavePageAwareProcessor
import com.cezarykluczynski.stapi.model.performer.entity.Performer
import com.cezarykluczynski.stapi.model.performer.repository.PerformerRepository
import com.google.common.collect.Lists
import spock.lang.Specification

class PerformerWriterTest extends Specification {

	private static final Long PAGE_ID = 1L

	private PerformerRepository performerRepositoryMock

	private DuplicateFilteringPreSavePageAwareProcessor duplicateFilteringPreSavePageAwareProcessorMock

	private PerformerWriter performerWriterMock

	def setup() {
		performerRepositoryMock = Mock(PerformerRepository)
		duplicateFilteringPreSavePageAwareProcessorMock = Mock(DuplicateFilteringPreSavePageAwareProcessor)
		performerWriterMock = new PerformerWriter(performerRepositoryMock,
				duplicateFilteringPreSavePageAwareProcessorMock)
	}

	def "filters all entities using pre save processor, then writes all entities using repository"() {
		given:
		Performer performer = new Performer(page: new Page(pageId: PAGE_ID))
		List<Performer> seriesList = Lists.newArrayList(performer)

		when:
		performerWriterMock.write(seriesList)

		then:
		1 * duplicateFilteringPreSavePageAwareProcessorMock.process(*_) >> { args ->
			assert args[0][0] == performer
			return seriesList
		}
		1 * performerRepositoryMock.save(seriesList)
	}

}
