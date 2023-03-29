package com.cezarykluczynski.stapi.etl.template.movie.linker

import com.cezarykluczynski.stapi.etl.common.service.EntityRefreshingLookupByNameService
import com.cezarykluczynski.stapi.model.character.entity.Character
import com.cezarykluczynski.stapi.model.movie.entity.Movie
import com.cezarykluczynski.stapi.model.performer.entity.Performer
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource
import com.google.common.collect.Lists
import com.google.common.collect.Sets
import spock.lang.Specification

class MoviePerformersCharactersLinkingWorkerTest extends Specification {

	private static final String CHARACTER_NAME = 'CHARACTER_NAME'
	private static final String SECOND_CHARACTER_NAME = 'SECOND_CHARACTER_NAME'
	private static final String PERFORMER_NAME = 'PERFORMER_NAME'
	private static final MediaWikiSource SOURCE = MediaWikiSource.MEMORY_ALPHA_EN

	private EntityRefreshingLookupByNameService entityRefreshingLookupByNameServiceMock

	private MoviePerformersCharactersLinkingWorker moviePerformersCharactersLinkingWorker

	void setup() {
		entityRefreshingLookupByNameServiceMock = Mock()
		moviePerformersCharactersLinkingWorker = new MoviePerformersCharactersLinkingWorker(entityRefreshingLookupByNameServiceMock)
	}

	void "ignores empty link title list"() {
		given:
		Set<List<String>> source = Sets.newHashSet(Lists.newArrayList())
		Movie baseEntity = new Movie()

		when:
		moviePerformersCharactersLinkingWorker.link(source, baseEntity)

		then:
		0 * _
		baseEntity.performers.empty
		baseEntity.characters.empty
	}

	void "ignores link title list with one item"() {
		given:
		Set<List<String>> source = Sets.newHashSet()
		source.add Lists.newArrayList('IGNORE ME')
		Movie baseEntity = new Movie()

		when:
		moviePerformersCharactersLinkingWorker.link(source, baseEntity)

		then:
		0 * _
		baseEntity.performers.empty
		baseEntity.characters.empty
	}

	void "parses link title list with two items, both found by link title"() {
		given:
		Set<List<String>> source = Sets.newHashSet()
		source.add Lists.newArrayList(CHARACTER_NAME, PERFORMER_NAME)
		Movie baseEntity = new Movie()
		Performer performer = Mock()
		Character character = Mock()

		when:
		moviePerformersCharactersLinkingWorker.link(source, baseEntity)

		then:
		1 * entityRefreshingLookupByNameServiceMock.findPerformerByName(PERFORMER_NAME, SOURCE) >> Optional.of(performer)
		1 * entityRefreshingLookupByNameServiceMock.findCharacterByName(CHARACTER_NAME, SOURCE) >> Optional.of(character)
		0 * _
		baseEntity.performers.size() == 1
		baseEntity.performers.contains performer
		baseEntity.characters.size() == 1
		baseEntity.characters.contains character
	}

	void "parses link title list with two items, none found"() {
		given:
		Set<List<String>> source = Sets.newHashSet()
		source.add Lists.newArrayList(CHARACTER_NAME, PERFORMER_NAME)
		Movie baseEntity = new Movie()
		when:
		moviePerformersCharactersLinkingWorker.link(source, baseEntity)

		then:
		1 * entityRefreshingLookupByNameServiceMock.findPerformerByName(PERFORMER_NAME, SOURCE) >> Optional.empty()
		1 * entityRefreshingLookupByNameServiceMock.findCharacterByName(CHARACTER_NAME, SOURCE) >> Optional.empty()

		then:
		1 * entityRefreshingLookupByNameServiceMock.findPerformerByName(CHARACTER_NAME, SOURCE) >> Optional.empty()
		1 * entityRefreshingLookupByNameServiceMock.findCharacterByName(PERFORMER_NAME, SOURCE) >> Optional.empty()
		0 * _
		baseEntity.performers.empty
		baseEntity.characters.empty
	}

	void "parses link title list with two items, and only performer found"() {
		given:
		Set<List<String>> source = Sets.newHashSet()
		source.add Lists.newArrayList(CHARACTER_NAME, PERFORMER_NAME)
		Movie baseEntity = new Movie()
		Performer performer = Mock()

		when:
		moviePerformersCharactersLinkingWorker.link(source, baseEntity)

		then:
		1 * entityRefreshingLookupByNameServiceMock.findPerformerByName(PERFORMER_NAME, SOURCE) >> Optional.of(performer)
		1 * entityRefreshingLookupByNameServiceMock.findCharacterByName(CHARACTER_NAME, SOURCE) >> Optional.empty()

		then:
		1 * entityRefreshingLookupByNameServiceMock.findPerformerByName(CHARACTER_NAME, SOURCE) >> Optional.empty()
		1 * entityRefreshingLookupByNameServiceMock.findCharacterByName(PERFORMER_NAME, SOURCE) >> Optional.empty()
		0 * _
		baseEntity.performers.size() == 1
		baseEntity.performers.contains performer
		baseEntity.characters.empty
	}

	void "parses link title list with two items, and only character found"() {
		given:
		Set<List<String>> source = Sets.newHashSet()
		source.add Lists.newArrayList(CHARACTER_NAME, PERFORMER_NAME)
		Movie baseEntity = new Movie()
		Character character = Mock()

		when:
		moviePerformersCharactersLinkingWorker.link(source, baseEntity)

		then:
		1 * entityRefreshingLookupByNameServiceMock.findCharacterByName(CHARACTER_NAME, SOURCE) >> Optional.of(character)
		1 * entityRefreshingLookupByNameServiceMock.findPerformerByName(PERFORMER_NAME, SOURCE) >> Optional.empty()

		then:
		1 * entityRefreshingLookupByNameServiceMock.findPerformerByName(CHARACTER_NAME, SOURCE) >> Optional.empty()
		1 * entityRefreshingLookupByNameServiceMock.findCharacterByName(PERFORMER_NAME, SOURCE) >> Optional.empty()
		0 * _
		baseEntity.performers.empty
		baseEntity.characters.size() == 1
		baseEntity.characters.contains character
	}

	void "parses link title list with more than two items"() {
		given:
		Set<List<String>> source = Sets.newHashSet()
		source.add Lists.newArrayList(PERFORMER_NAME, CHARACTER_NAME, SECOND_CHARACTER_NAME)
		Movie baseEntity = new Movie()
		Performer performer = Mock()
		Character character = Mock()
		Character character2 = Mock()

		when:
		moviePerformersCharactersLinkingWorker.link(source, baseEntity)

		then:
		1 * entityRefreshingLookupByNameServiceMock.findPerformerByName(PERFORMER_NAME, SOURCE) >> Optional.of(performer)
		1 * entityRefreshingLookupByNameServiceMock.findCharacterByName(CHARACTER_NAME, SOURCE) >> Optional.of(character)

		1 * entityRefreshingLookupByNameServiceMock.findPerformerByName(PERFORMER_NAME, SOURCE) >> Optional.of(performer)
		1 * entityRefreshingLookupByNameServiceMock.findCharacterByName(SECOND_CHARACTER_NAME, SOURCE) >> Optional.of(character2)

		1 * entityRefreshingLookupByNameServiceMock.findPerformerByName(SECOND_CHARACTER_NAME, SOURCE) >> Optional.empty()
		1 * entityRefreshingLookupByNameServiceMock.findCharacterByName(PERFORMER_NAME, SOURCE) >> Optional.empty()

		1 * entityRefreshingLookupByNameServiceMock.findPerformerByName(SECOND_CHARACTER_NAME, SOURCE) >> Optional.empty()
		1 * entityRefreshingLookupByNameServiceMock.findCharacterByName(CHARACTER_NAME, SOURCE) >> Optional.of(character)

		1 * entityRefreshingLookupByNameServiceMock.findPerformerByName(CHARACTER_NAME, SOURCE) >> Optional.empty()
		1 * entityRefreshingLookupByNameServiceMock.findCharacterByName(SECOND_CHARACTER_NAME, SOURCE) >> Optional.of(character2)

		1 * entityRefreshingLookupByNameServiceMock.findPerformerByName(CHARACTER_NAME, SOURCE) >> Optional.empty()
		1 * entityRefreshingLookupByNameServiceMock.findCharacterByName(PERFORMER_NAME, SOURCE) >> Optional.empty()

		0 * _
		baseEntity.performers.size() == 1
		baseEntity.performers.contains performer
		baseEntity.characters.size() == 2
		baseEntity.characters.contains character
		baseEntity.characters.contains character2
	}

	void "ignores link title list with more than two items, if it is deleted scene"() {
		given:
		Set<List<String>> source = Sets.newHashSet()
		source.add Lists.newArrayList(CHARACTER_NAME, PERFORMER_NAME, MoviePerformersCharactersLinkingWorker.DELETED_SCENE)
		Movie baseEntity = new Movie()

		when:
		moviePerformersCharactersLinkingWorker.link(source, baseEntity)

		then:
		0 * _
		baseEntity.performers.empty
		baseEntity.characters.empty
	}

}
