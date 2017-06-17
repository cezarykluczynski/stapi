package com.cezarykluczynski.stapi.etl.literature.creation.processor

import com.cezarykluczynski.stapi.model.literature.entity.Literature
import com.cezarykluczynski.stapi.model.literature.repository.LiteratureRepository
import com.cezarykluczynski.stapi.model.page.service.DuplicateFilteringPreSavePageAwareFilter
import com.google.common.collect.Lists
import spock.lang.Specification

class LiteratureWriterTest extends Specification {

	private LiteratureRepository literatureRepositoryMock

	private DuplicateFilteringPreSavePageAwareFilter duplicateFilteringPreSavePageAwareProcessorMock

	private LiteratureWriter literatureWriterMock

	void setup() {
		literatureRepositoryMock = Mock()
		duplicateFilteringPreSavePageAwareProcessorMock = Mock()
		literatureWriterMock = new LiteratureWriter(literatureRepositoryMock, duplicateFilteringPreSavePageAwareProcessorMock)
	}

	void "filters all entities using pre save processor, then writes all entities using repository"() {
		given:
		Literature literature = new Literature()
		List<Literature> literatureList = Lists.newArrayList(literature)

		when:
		literatureWriterMock.write(literatureList)

		then:
		1 * duplicateFilteringPreSavePageAwareProcessorMock.process(_, Literature) >> { args ->
			assert args[0][0] == literature
			literatureList
		}
		1 * literatureRepositoryMock.save(literatureList)
		0 * _
	}

}
