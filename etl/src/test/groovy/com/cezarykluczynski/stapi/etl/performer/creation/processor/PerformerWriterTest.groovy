package com.cezarykluczynski.stapi.etl.performer.creation.processor

import com.cezarykluczynski.stapi.model.page.entity.Page
import com.cezarykluczynski.stapi.model.page.service.DuplicateFilteringPreSavePageAwareFilter
import com.cezarykluczynski.stapi.model.page.service.DuplicateReattachingPreSavePageAwareFilter
import com.cezarykluczynski.stapi.model.performer.entity.Performer
import com.cezarykluczynski.stapi.model.performer.repository.PerformerRepository
import com.google.common.collect.Lists
import spock.lang.Specification

class PerformerWriterTest extends Specification {

	private static final Long PAGE_ID = 1L

	private PerformerRepository performerRepositoryMock

	private DuplicateFilteringPreSavePageAwareFilter duplicateFilteringPreSavePageAwareProcessorMock

	private DuplicateReattachingPreSavePageAwareFilter duplicateReattachingPreSavePageAwareFilterMock

	private PerformerWriter performerWriterMock

	void setup() {
		performerRepositoryMock = Mock()
		duplicateFilteringPreSavePageAwareProcessorMock = Mock()
		duplicateReattachingPreSavePageAwareFilterMock = Mock()
		performerWriterMock = new PerformerWriter(performerRepositoryMock, duplicateFilteringPreSavePageAwareProcessorMock,
				duplicateReattachingPreSavePageAwareFilterMock)
	}

	void "filters duplicates, then writes all entities using repository"() {
		given:
		Performer performer = new Performer(page: new Page(pageId: PAGE_ID))
		List<Performer> seriesList = Lists.newArrayList(performer)

		when:
		performerWriterMock.write(seriesList)

		then:
		1 * duplicateFilteringPreSavePageAwareProcessorMock.process(_, Performer) >> { args ->
			assert args[0][0] == performer
			seriesList
		}
		1 * duplicateReattachingPreSavePageAwareFilterMock.process(_, Performer) >> { args ->
			assert args[0][0] == performer
			seriesList
		}
		1 * duplicateFilteringPreSavePageAwareProcessorMock.process(_, Performer) >> { args ->
			assert args[0][0] == performer
			seriesList
		}
		1 * performerRepositoryMock.save(seriesList)
		0 * _
	}

}
