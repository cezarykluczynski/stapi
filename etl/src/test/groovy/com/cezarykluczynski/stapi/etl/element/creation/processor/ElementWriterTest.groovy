package com.cezarykluczynski.stapi.etl.element.creation.processor

import com.cezarykluczynski.stapi.model.element.entity.Element
import com.cezarykluczynski.stapi.model.element.repository.ElementRepository
import com.google.common.collect.Lists
import org.springframework.batch.item.Chunk
import spock.lang.Specification

class ElementWriterTest extends Specification {

	private ElementRepository elementRepositoryMock

	private ElementWriter elementWriterMock

	void setup() {
		elementRepositoryMock = Mock()
		elementWriterMock = new ElementWriter(elementRepositoryMock)
	}

	void "writes all entities using repository"() {
		given:
		Element element = new Element()
		List<Element> elementList = Lists.newArrayList(element)

		when:
		elementWriterMock.write(new Chunk(elementList))

		then:
		1 * elementRepositoryMock.saveAll(elementList)
		0 * _
	}

}
