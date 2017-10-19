package com.cezarykluczynski.stapi.etl.technology.creation.processor

import com.cezarykluczynski.stapi.model.technology.entity.Technology
import com.cezarykluczynski.stapi.model.technology.repository.TechnologyRepository
import com.cezarykluczynski.stapi.model.page.service.DuplicateFilteringPreSavePageAwareFilter
import com.google.common.collect.Lists
import spock.lang.Specification

class TechnologyWriterTest extends Specification {

	private TechnologyRepository technologyRepositoryMock

	private DuplicateFilteringPreSavePageAwareFilter duplicateFilteringPreSavePageAwareProcessorMock

	private TechnologyWriter technologyWriterMock

	void setup() {
		technologyRepositoryMock = Mock()
		duplicateFilteringPreSavePageAwareProcessorMock = Mock()
		technologyWriterMock = new TechnologyWriter(technologyRepositoryMock, duplicateFilteringPreSavePageAwareProcessorMock)
	}

	void "filters all entities using pre save processor, then writes all entities using repository"() {
		given:
		Technology technology = new Technology()
		List<Technology> technologyList = Lists.newArrayList(technology)

		when:
		technologyWriterMock.write(technologyList)

		then:
		1 * duplicateFilteringPreSavePageAwareProcessorMock.process(_, Technology) >> { args ->
			assert args[0][0] == technology
			technologyList
		}
		1 * technologyRepositoryMock.save(technologyList)
		0 * _
	}

}
