package com.cezarykluczynski.stapi.etl.element.creation.processor

import com.cezarykluczynski.stapi.model.element.entity.Element
import com.cezarykluczynski.stapi.model.element.repository.ElementRepository
import com.cezarykluczynski.stapi.model.page.service.DuplicateFilteringPreSavePageAwareFilter
import com.google.common.collect.Lists
import spock.lang.Specification

class ElementWriterTest extends Specification {

	private ElementRepository elementRepositoryMock

	private DuplicateFilteringPreSavePageAwareFilter duplicateFilteringPreSavePageAwareProcessorMock

	private ElementWriter elementWriterMock

	void setup() {
		elementRepositoryMock = Mock()
		duplicateFilteringPreSavePageAwareProcessorMock = Mock()
		elementWriterMock = new ElementWriter(elementRepositoryMock, duplicateFilteringPreSavePageAwareProcessorMock)
	}

	void "filters all entities using pre save processor, then writes all entities using repository"() {
		given:
		Element element = new Element()
		List<Element> elementList = Lists.newArrayList(element)

		when:
		elementWriterMock.write(elementList)

		then:
		1 * duplicateFilteringPreSavePageAwareProcessorMock.process(_, Element) >> { args ->
			assert args[0][0] == element
			elementList
		}
		1 * elementRepositoryMock.save(elementList)
		0 * _
	}

}
