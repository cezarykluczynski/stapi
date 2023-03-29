package com.cezarykluczynski.stapi.etl.common.service

import com.cezarykluczynski.stapi.model.character.entity.Character
import com.cezarykluczynski.stapi.model.character.repository.CharacterRepository
import com.cezarykluczynski.stapi.model.performer.entity.Performer
import com.cezarykluczynski.stapi.model.performer.repository.PerformerRepository
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource
import spock.lang.Specification

class EntityRefreshingLookupByNameServiceTest extends Specification {

	static final String PERFORMER_NAME = 'PERFORMER_NAME'
	static final String CHARACTER_NAME = 'CHARACTER_NAME'
	static final Long ID = 123L
	static final MediaWikiSource SOURCE = MediaWikiSource.MEMORY_ALPHA_EN

	EntityLookupByNameService entityLookupByNameServiceMock

	PerformerRepository performerRepositoryMock

	CharacterRepository characterRepositoryMock

	EntityRefreshingLookupByNameService entityRefreshingLookupByNameService

	void setup() {
		entityLookupByNameServiceMock = Mock()
		performerRepositoryMock = Mock()
		characterRepositoryMock = Mock()
		entityRefreshingLookupByNameService = new EntityRefreshingLookupByNameService(entityLookupByNameServiceMock, performerRepositoryMock,
				characterRepositoryMock)
	}

	void "returns empty optional when caching service returns empty optional for performer"() {
		when:
		Optional<Performer> performerOptional = entityRefreshingLookupByNameService.findPerformerByName(PERFORMER_NAME, SOURCE)

		then:
		1 * entityLookupByNameServiceMock.findPerformerByName(PERFORMER_NAME, SOURCE) >> Optional.empty()
		0 * _
		performerOptional.empty
	}

	void "reloads performer when caching service returns non-empty optional for performer"() {
		given:
		Performer oldPerformer = Mock()
		Performer newPerformer = Mock()

		when:
		Optional<Performer> performerOptional = entityRefreshingLookupByNameService.findPerformerByName(PERFORMER_NAME, SOURCE)

		then:
		1 * entityLookupByNameServiceMock.findPerformerByName(PERFORMER_NAME, SOURCE) >> Optional.of(oldPerformer)
		1 * oldPerformer.id >> ID
		1 * performerRepositoryMock.findById(ID) >> Optional.of(newPerformer)
		0 * _
		performerOptional.present
		performerOptional.get() == newPerformer
	}

	void "returns empty optional when caching service returns empty optional for character"() {
		when:
		Optional<Character> characterOptional = entityRefreshingLookupByNameService.findCharacterByName(CHARACTER_NAME, SOURCE)

		then:
		1 * entityLookupByNameServiceMock.findCharacterByName(CHARACTER_NAME, SOURCE) >> Optional.empty()
		0 * _
		characterOptional.empty
	}

	void "reloads character when caching service returns non-empty optional for character"() {
		given:
		Character oldCharacter = Mock()
		Character newCharacter = Mock()

		when:
		Optional<Character> characterOptional = entityRefreshingLookupByNameService.findCharacterByName(CHARACTER_NAME, SOURCE)

		then:
		1 * entityLookupByNameServiceMock.findCharacterByName(CHARACTER_NAME, SOURCE) >> Optional.of(oldCharacter)
		1 * oldCharacter.id >> ID
		1 * characterRepositoryMock.findById(ID) >> Optional.of(newCharacter)
		0 * _
		characterOptional.present
		characterOptional.get() == newCharacter
	}

}
