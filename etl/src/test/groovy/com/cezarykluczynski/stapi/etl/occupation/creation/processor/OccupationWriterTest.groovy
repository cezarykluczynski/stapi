package com.cezarykluczynski.stapi.etl.occupation.creation.processor

import com.cezarykluczynski.stapi.model.occupation.entity.Occupation
import com.cezarykluczynski.stapi.model.occupation.repository.OccupationRepository
import com.cezarykluczynski.stapi.model.page.service.DuplicateFilteringPreSavePageAwareFilter
import com.google.common.collect.Lists
import spock.lang.Specification

class OccupationWriterTest extends Specification {

	private OccupationRepository occupationRepositoryMock

	private DuplicateFilteringPreSavePageAwareFilter duplicateFilteringPreSavePageAwareProcessorMock

	private OccupationWriter occupationWriterMock

	void setup() {
		occupationRepositoryMock = Mock()
		duplicateFilteringPreSavePageAwareProcessorMock = Mock()
		occupationWriterMock = new OccupationWriter(occupationRepositoryMock, duplicateFilteringPreSavePageAwareProcessorMock)
	}

	void "filters all entities using pre save processor, then writes all entities using repository"() {
		given:
		Occupation occupation = new Occupation()
		List<Occupation> occupationList = Lists.newArrayList(occupation)

		when:
		occupationWriterMock.write(occupationList)

		then:
		1 * duplicateFilteringPreSavePageAwareProcessorMock.process(_, Occupation) >> { args ->
			assert args[0][0] == occupation
			occupationList
		}
		1 * occupationRepositoryMock.save(occupationList)
		0 * _
	}

}
