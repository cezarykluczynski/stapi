package com.cezarykluczynski.stapi.etl.character.creation.processor

import com.cezarykluczynski.stapi.model.character.entity.Character
import com.cezarykluczynski.stapi.model.character.repository.CharacterRepository
import com.cezarykluczynski.stapi.model.page.entity.Page
import com.cezarykluczynski.stapi.model.page.service.DuplicateFilteringPreSavePageAwareFilter
import com.google.common.collect.Lists
import spock.lang.Specification

class CharacterWriterTest extends Specification {

	private static final Long PAGE_ID = 1L

	private CharacterRepository characterRepositoryMock

	private DuplicateFilteringPreSavePageAwareFilter duplicateFilteringPreSavePageAwareProcessorMock

	private CharacterWriter characterWriterMock

	def setup() {
		characterRepositoryMock = Mock(CharacterRepository)
		duplicateFilteringPreSavePageAwareProcessorMock = Mock(DuplicateFilteringPreSavePageAwareFilter)
		characterWriterMock = new CharacterWriter(characterRepositoryMock,
				duplicateFilteringPreSavePageAwareProcessorMock)
	}

	def "filters all entities using pre save processor, then writes all entities using repository"() {
		given:
		Character character = new Character(page: new Page(pageId: PAGE_ID))
		List<Character> seriesList = Lists.newArrayList(character)

		when:
		characterWriterMock.write(seriesList)

		then:
		1 * duplicateFilteringPreSavePageAwareProcessorMock.process(_, Character) >> { args ->
			assert args[0][0] == character
			return seriesList
		}
		1 * characterRepositoryMock.save(seriesList)
	}

}
