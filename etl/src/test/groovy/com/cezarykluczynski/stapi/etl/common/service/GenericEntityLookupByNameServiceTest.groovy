package com.cezarykluczynski.stapi.etl.common.service

import com.cezarykluczynski.stapi.etl.common.mapper.MediaWikiSourceMapper
import com.cezarykluczynski.stapi.model.character.entity.Character
import com.cezarykluczynski.stapi.model.character.repository.CharacterRepository
import com.cezarykluczynski.stapi.etl.mediawiki.api.PageApi
import com.cezarykluczynski.stapi.etl.mediawiki.api.enums.MediaWikiSource
import com.cezarykluczynski.stapi.etl.mediawiki.dto.Page
import com.cezarykluczynski.stapi.etl.mediawiki.dto.PageHeader
import com.google.common.collect.Lists
import jakarta.persistence.NonUniqueResultException
import org.springframework.data.repository.support.Repositories
import spock.lang.Specification

class GenericEntityLookupByNameServiceTest extends Specification {

	private static final MediaWikiSource SOURCES_MEDIA_WIKI_SOURCE = MediaWikiSource.MEMORY_ALPHA_EN
	private static final com.cezarykluczynski.stapi.model.page.entity.enums.MediaWikiSource MODEL_MEDIA_WIKI_SOURCE =
			com.cezarykluczynski.stapi.model.page.entity.enums.MediaWikiSource.MEMORY_ALPHA_EN
	private static final String CHARACTER_NAME = 'CHARACTER_NAME'
	private static final Long PAGE_ID = 1L

	private PageApi pageApiMock

	private CharacterRepository characterRepositoryMock

	private MediaWikiSourceMapper mediaWikiSourceMapper

	private Repositories repositoriesMock

	private GenericEntityLookupByNameService genericEntityLookupByNameService

	void setup() {
		pageApiMock = Mock()
		characterRepositoryMock = Mock()
		mediaWikiSourceMapper = Mock()
		repositoriesMock = Mock()
		genericEntityLookupByNameService = new GenericEntityLookupByNameService(pageApiMock, mediaWikiSourceMapper, repositoriesMock)
	}

	void "gets character by name from repository"() {
		given:
		Character character = Mock()

		when:
		Optional<Character> characterOptional = genericEntityLookupByNameService.findEntityByName(CHARACTER_NAME, SOURCES_MEDIA_WIKI_SOURCE, Character)

		then:
		1 * repositoriesMock.getRepositoryFor(Character) >> Optional.of(characterRepositoryMock)
		1 * mediaWikiSourceMapper.fromSourcesToEntity(SOURCES_MEDIA_WIKI_SOURCE) >> MODEL_MEDIA_WIKI_SOURCE
		1 * characterRepositoryMock.findByPageTitleWithPageMediaWikiSource(CHARACTER_NAME, MODEL_MEDIA_WIKI_SOURCE) >> Optional.of(character)
		0 * _
		characterOptional.get() == character
	}

	void "gets character by name from page api, then from repository"() {
		given:
		Character character = Mock()
		PageHeader pageHeader = Mock()
		Page page = new Page(
				pageId: PAGE_ID,
				mediaWikiSource: SOURCES_MEDIA_WIKI_SOURCE,
				redirectPath: Lists.newArrayList(pageHeader))

		when:
		Optional<Character> characterOptional = genericEntityLookupByNameService.findEntityByName(CHARACTER_NAME, SOURCES_MEDIA_WIKI_SOURCE, Character)

		then:
		1 * repositoriesMock.getRepositoryFor(Character) >> Optional.of(characterRepositoryMock)
		2 * mediaWikiSourceMapper.fromSourcesToEntity(SOURCES_MEDIA_WIKI_SOURCE) >> MODEL_MEDIA_WIKI_SOURCE
		1 * characterRepositoryMock.findByPageTitleWithPageMediaWikiSource(CHARACTER_NAME, MODEL_MEDIA_WIKI_SOURCE) >> Optional.empty()
		1 * pageApiMock.getPage(CHARACTER_NAME, SOURCES_MEDIA_WIKI_SOURCE) >> page
		1 * characterRepositoryMock.findByPagePageIdAndPageMediaWikiSource(PAGE_ID, MODEL_MEDIA_WIKI_SOURCE) >> Optional.of(character)
		0 * _
		characterOptional.get() == character
	}

	void "gets character by name from page api, then from repository, when NonUniqueResultException was thrown"() {
		given:
		Character character = Mock()
		PageHeader pageHeader = Mock()
		Page page = new Page(
				pageId: PAGE_ID,
				mediaWikiSource: SOURCES_MEDIA_WIKI_SOURCE,
				redirectPath: Lists.newArrayList(pageHeader))

		when:
		Optional<Character> characterOptional = genericEntityLookupByNameService.findEntityByName(CHARACTER_NAME, SOURCES_MEDIA_WIKI_SOURCE, Character)

		then:
		1 * repositoriesMock.getRepositoryFor(Character) >> Optional.of(characterRepositoryMock)
		2 * mediaWikiSourceMapper.fromSourcesToEntity(SOURCES_MEDIA_WIKI_SOURCE) >> MODEL_MEDIA_WIKI_SOURCE
		1 * characterRepositoryMock.findByPageTitleWithPageMediaWikiSource(CHARACTER_NAME, MODEL_MEDIA_WIKI_SOURCE) >> { args ->
			throw new NonUniqueResultException()
		}
		1 * pageApiMock.getPage(CHARACTER_NAME, SOURCES_MEDIA_WIKI_SOURCE) >> page
		1 * characterRepositoryMock.findByPagePageIdAndPageMediaWikiSource(PAGE_ID, MODEL_MEDIA_WIKI_SOURCE) >> Optional.of(character)
		0 * _
		characterOptional.get() == character
	}

	void "does not get character when page api returns null"() {
		when:
		Optional<Character> characterOptional = genericEntityLookupByNameService.findEntityByName(CHARACTER_NAME, SOURCES_MEDIA_WIKI_SOURCE, Character)

		then:
		1 * repositoriesMock.getRepositoryFor(Character) >> Optional.of(characterRepositoryMock)
		1 * mediaWikiSourceMapper.fromSourcesToEntity(SOURCES_MEDIA_WIKI_SOURCE) >> MODEL_MEDIA_WIKI_SOURCE
		1 * characterRepositoryMock.findByPageTitleWithPageMediaWikiSource(CHARACTER_NAME, MODEL_MEDIA_WIKI_SOURCE) >> Optional.empty()
		1 * pageApiMock.getPage(CHARACTER_NAME, SOURCES_MEDIA_WIKI_SOURCE) >> null
		0 * _
		!characterOptional.present
	}

	void "does not get character when name is null"() {
		when:
		Optional<Character> characterOptional = genericEntityLookupByNameService.findEntityByName(null, SOURCES_MEDIA_WIKI_SOURCE, Character)

		then:
		0 * _
		!characterOptional.present
	}

	void "does not get character when source is null"() {
		when:
		Optional<Character> characterOptional = genericEntityLookupByNameService.findEntityByName(CHARACTER_NAME, null, Character)

		then:
		0 * _
		!characterOptional.present
	}

	void "does not get character when page api returns page, but character repository returns empty optional"() {
		given:
		PageHeader pageHeader = Mock()
		Page page = new Page(
				pageId: PAGE_ID,
				mediaWikiSource: SOURCES_MEDIA_WIKI_SOURCE,
				redirectPath: Lists.newArrayList(pageHeader))

		when:
		Optional<Character> characterOptional = genericEntityLookupByNameService.findEntityByName(CHARACTER_NAME, SOURCES_MEDIA_WIKI_SOURCE, Character)

		then:
		1 * repositoriesMock.getRepositoryFor(Character) >> Optional.of(characterRepositoryMock)
		2 * mediaWikiSourceMapper.fromSourcesToEntity(SOURCES_MEDIA_WIKI_SOURCE) >> MODEL_MEDIA_WIKI_SOURCE
		1 * characterRepositoryMock.findByPageTitleWithPageMediaWikiSource(CHARACTER_NAME, MODEL_MEDIA_WIKI_SOURCE) >> Optional.empty()
		1 * pageApiMock.getPage(CHARACTER_NAME, SOURCES_MEDIA_WIKI_SOURCE) >> page
		1 * characterRepositoryMock.findByPagePageIdAndPageMediaWikiSource(PAGE_ID, MODEL_MEDIA_WIKI_SOURCE) >> Optional.empty()
		0 * _
		!characterOptional.present
	}

	void "returns empty optional when no repository can be found"() {
		when:
		Optional<Character> characterOptional = genericEntityLookupByNameService.findEntityByName(CHARACTER_NAME, SOURCES_MEDIA_WIKI_SOURCE, Character)

		then:
		1 * repositoriesMock.getRepositoryFor(Character) >> Optional.empty()
		0 * _
		!characterOptional.present
	}

}
