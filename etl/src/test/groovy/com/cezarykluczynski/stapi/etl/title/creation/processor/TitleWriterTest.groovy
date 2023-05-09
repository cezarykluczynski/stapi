package com.cezarykluczynski.stapi.etl.title.creation.processor

import com.cezarykluczynski.stapi.model.title.entity.Title
import com.cezarykluczynski.stapi.model.title.repository.TitleRepository
import com.google.common.collect.Lists
import org.springframework.batch.item.Chunk
import spock.lang.Specification

class TitleWriterTest extends Specification {

	private TitleRepository titleRepositoryMock

	private TitleWriter titleWriterMock

	void setup() {
		titleRepositoryMock = Mock()
		titleWriterMock = new TitleWriter(titleRepositoryMock)
	}

	void "writes all entities using repository"() {
		given:
		Title title = new Title()
		List<Title> titleList = Lists.newArrayList(title)

		when:
		titleWriterMock.write(new Chunk(titleList))

		then:
		1 * titleRepositoryMock.saveAll(titleList)
		0 * _
	}

}
