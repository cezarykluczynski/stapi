package com.cezarykluczynski.stapi.etl.magazine.creation.processor

import com.cezarykluczynski.stapi.model.magazine.entity.Magazine
import com.cezarykluczynski.stapi.model.magazine.repository.MagazineRepository
import com.cezarykluczynski.stapi.model.page.service.DuplicateFilteringPreSavePageAwareFilter
import com.google.common.collect.Lists
import spock.lang.Specification

class MagazineWriterTest extends Specification {

	private MagazineRepository magazineRepositoryMock

	private DuplicateFilteringPreSavePageAwareFilter duplicateFilteringPreSavePageAwareProcessorMock

	private MagazineWriter magazineWriter

	void setup() {
		magazineRepositoryMock = Mock()
		duplicateFilteringPreSavePageAwareProcessorMock = Mock()
		magazineWriter = new MagazineWriter(magazineRepositoryMock, duplicateFilteringPreSavePageAwareProcessorMock)
	}

	void "filters all entities using pre save processor, then writes all entities using repository"() {
		given:
		Magazine magazine = new Magazine()
		List<Magazine> magazineList = Lists.newArrayList(magazine)

		when:
		magazineWriter.write(magazineList)

		then:
		1 * duplicateFilteringPreSavePageAwareProcessorMock.process(_, Magazine) >> { args ->
			assert args[0][0] == magazine
			magazineList
		}
		1 * magazineRepositoryMock.save(magazineList)
		0 * _
	}

}
