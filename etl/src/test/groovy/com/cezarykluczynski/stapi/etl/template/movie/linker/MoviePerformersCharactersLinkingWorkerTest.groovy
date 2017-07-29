package com.cezarykluczynski.stapi.etl.template.movie.linker

import com.cezarykluczynski.stapi.etl.common.service.EntityLookupByNameService
import com.cezarykluczynski.stapi.etl.common.service.NonQualifiedCharacterFilter
import com.cezarykluczynski.stapi.model.character.entity.Character
import com.cezarykluczynski.stapi.model.movie.entity.Movie
import com.cezarykluczynski.stapi.model.performer.entity.Performer
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource
import com.google.common.collect.Lists
import com.google.common.collect.Sets
import spock.lang.Specification

class MoviePerformersCharactersLinkingWorkerTest extends Specification {

	private static final String CHARACTER_NAME = 'CHARACTER_NAME'
	private static final String PERFORMER_NAME = 'PERFORMER_NAME'

	private EntityLookupByNameService entityLookupByNameServiceMock

	private NonQualifiedCharacterFilter nonQualifiedCharacterFilterMock

	private MoviePerformersCharactersLinkingWorker moviePerformersCharactersLinkingWorker

	void setup() {
		nonQualifiedCharacterFilterMock = Mock()
		entityLookupByNameServiceMock = Mock()
		moviePerformersCharactersLinkingWorker = new MoviePerformersCharactersLinkingWorker(entityLookupByNameServiceMock,
				nonQualifiedCharacterFilterMock)
		moviePerformersCharactersLinkingWorker.log
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

	void "ignores link title list with two items if performer name contains phrase that mark it as ignored"() {
		given:
		Set<List<String>> source = Sets.newHashSet()
		source.add Lists.newArrayList(CHARACTER_NAME, MoviePerformersCharactersLinkingWorker.IGNORABLE_PAGE_PREFIXES[0])
		Movie baseEntity = new Movie()

		when:
		moviePerformersCharactersLinkingWorker.link(source, baseEntity)

		then:
		0 * _
		baseEntity.performers.empty
		baseEntity.characters.empty
	}

	void "ignores link title list with two items if character name contains phrase that mark it as ignored"() {
		given:
		Set<List<String>> source = Sets.newHashSet()
		source.add Lists.newArrayList(MoviePerformersCharactersLinkingWorker.IGNORABLE_PAGE_PREFIXES[0], PERFORMER_NAME)
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
		1 * entityLookupByNameServiceMock.findPerformerByName(PERFORMER_NAME, MediaWikiSource.MEMORY_ALPHA_EN) >> Optional.of(performer)
		1 * nonQualifiedCharacterFilterMock.shouldBeFilteredOut(CHARACTER_NAME) >> false
		1 * entityLookupByNameServiceMock.findCharacterByName(CHARACTER_NAME, MediaWikiSource.MEMORY_ALPHA_EN) >> Optional.of(character)
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
		1 * entityLookupByNameServiceMock.findPerformerByName(PERFORMER_NAME, MediaWikiSource.MEMORY_ALPHA_EN) >> Optional.empty()
		1 * nonQualifiedCharacterFilterMock.shouldBeFilteredOut(CHARACTER_NAME) >> false
		1 * entityLookupByNameServiceMock.findCharacterByName(CHARACTER_NAME, MediaWikiSource.MEMORY_ALPHA_EN) >> Optional.empty()
		0 * _
		baseEntity.performers.empty
		baseEntity.characters.empty
	}

	void "parses link title list with two items, character should be filtered out"() {
		given:
		Set<List<String>> source = Sets.newHashSet()
		source.add Lists.newArrayList(CHARACTER_NAME, PERFORMER_NAME)
		Movie baseEntity = new Movie()
		when:
		moviePerformersCharactersLinkingWorker.link(source, baseEntity)

		then:
		1 * entityLookupByNameServiceMock.findPerformerByName(PERFORMER_NAME, MediaWikiSource.MEMORY_ALPHA_EN) >> Optional.empty()
		1 * nonQualifiedCharacterFilterMock.shouldBeFilteredOut(CHARACTER_NAME) >> true
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
		1 * entityLookupByNameServiceMock.findPerformerByName(PERFORMER_NAME, MediaWikiSource.MEMORY_ALPHA_EN) >> Optional.of(performer)
		1 * nonQualifiedCharacterFilterMock.shouldBeFilteredOut(CHARACTER_NAME) >> false
		1 * entityLookupByNameServiceMock.findCharacterByName(CHARACTER_NAME, MediaWikiSource.MEMORY_ALPHA_EN) >> Optional.empty()
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
		1 * entityLookupByNameServiceMock.findPerformerByName(PERFORMER_NAME, MediaWikiSource.MEMORY_ALPHA_EN) >> Optional.empty()
		1 * nonQualifiedCharacterFilterMock.shouldBeFilteredOut(CHARACTER_NAME) >> false
		1 * entityLookupByNameServiceMock.findCharacterByName(CHARACTER_NAME, MediaWikiSource.MEMORY_ALPHA_EN) >> Optional.of(character)
		0 * _
		baseEntity.performers.empty
		baseEntity.characters.size() == 1
		baseEntity.characters.contains character
	}

	void "parses link title list with more than two items"() {
		given:
		Set<List<String>> source = Sets.newHashSet()
		source.add Lists.newArrayList(CHARACTER_NAME, PERFORMER_NAME, 'ANOTHER PAGE TITLE')
		Movie baseEntity = new Movie()
		Performer performer = Mock()
		Character character = Mock()

		when:
		moviePerformersCharactersLinkingWorker.link(source, baseEntity)

		then:
		1 * entityLookupByNameServiceMock.findPerformerByName(PERFORMER_NAME, MediaWikiSource.MEMORY_ALPHA_EN) >> Optional.of(performer)
		1 * nonQualifiedCharacterFilterMock.shouldBeFilteredOut(CHARACTER_NAME) >> false
		1 * entityLookupByNameServiceMock.findCharacterByName(CHARACTER_NAME, MediaWikiSource.MEMORY_ALPHA_EN) >> Optional.of(character)
		0 * _
		baseEntity.performers.size() == 1
		baseEntity.performers.contains performer
		baseEntity.characters.size() == 1
		baseEntity.characters.contains character
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
