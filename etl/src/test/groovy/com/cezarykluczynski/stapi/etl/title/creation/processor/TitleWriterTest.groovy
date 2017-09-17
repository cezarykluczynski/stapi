package com.cezarykluczynski.stapi.etl.title.creation.processor

import com.cezarykluczynski.stapi.model.title.entity.Title
import com.cezarykluczynski.stapi.model.title.repository.TitleRepository
import com.cezarykluczynski.stapi.model.page.service.DuplicateFilteringPreSavePageAwareFilter
import com.google.common.collect.Lists
import spock.lang.Specification

class TitleWriterTest extends Specification {

	private TitleRepository titleRepositoryMock

	private DuplicateFilteringPreSavePageAwareFilter duplicateFilteringPreSavePageAwareProcessorMock

	private TitleWriter titleWriterMock

	void setup() {
		titleRepositoryMock = Mock()
		duplicateFilteringPreSavePageAwareProcessorMock = Mock()
		titleWriterMock = new TitleWriter(titleRepositoryMock, duplicateFilteringPreSavePageAwareProcessorMock)
	}

	void "filters all entities using pre save processor, then writes all entities using repository"() {
		given:
		Title title = new Title()
		List<Title> titleList = Lists.newArrayList(title)

		when:
		titleWriterMock.write(titleList)

		then:
		1 * duplicateFilteringPreSavePageAwareProcessorMock.process(_, Title) >> { args ->
			assert args[0][0] == title
			titleList
		}
		1 * titleRepositoryMock.save(titleList)
		0 * _
	}

}
