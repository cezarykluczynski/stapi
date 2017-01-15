package com.cezarykluczynski.stapi.etl.common.service

import com.cezarykluczynski.stapi.etl.common.mapper.MediaWikiSourceMapper
import com.cezarykluczynski.stapi.model.character.entity.Character
import com.cezarykluczynski.stapi.model.character.repository.CharacterRepository
import com.cezarykluczynski.stapi.model.performer.entity.Performer
import com.cezarykluczynski.stapi.model.performer.repository.PerformerRepository
import com.cezarykluczynski.stapi.model.staff.entity.Staff
import com.cezarykluczynski.stapi.model.staff.repository.StaffRepository
import com.cezarykluczynski.stapi.sources.mediawiki.api.PageApi
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource as SourcesMediaWikiSource
import com.cezarykluczynski.stapi.model.page.entity.enums.MediaWikiSource as ModelMediaWikiSource
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page
import spock.lang.Specification

import javax.persistence.NonUniqueResultException

class EntityLookupByNameServiceTest extends Specification {

	private static final SourcesMediaWikiSource SOURCES_MEDIA_WIKI_SOURCE = SourcesMediaWikiSource.MEMORY_ALPHA_EN
	private static final ModelMediaWikiSource MODEL_MEDIA_WIKI_SOURCE = ModelMediaWikiSource.MEMORY_ALPHA_EN
	private static final String CHARACTER_NAME = 'CHARACTER_NAME'
	private static final String PERFORMER_NAME = 'PERFORMER_NAME'
	private static final String STAFF_NAME = 'STAFF_NAME'
	private static final Long PAGE_ID = 1L

	private PageApi pageApiMock

	private CharacterRepository characterRepositoryMock

	private PerformerRepository performerRepositoryMock

	private StaffRepository staffRepositoryMock

	private EntityLookupByNameService entityLookupByNameService

	private MediaWikiSourceMapper mediaWikiSourceMapper

	def setup() {
		pageApiMock = Mock(PageApi)
		characterRepositoryMock = Mock(CharacterRepository)
		performerRepositoryMock = Mock(PerformerRepository)
		staffRepositoryMock = Mock(StaffRepository)
		mediaWikiSourceMapper = Mock(MediaWikiSourceMapper)
		entityLookupByNameService = new EntityLookupByNameService(pageApiMock, characterRepositoryMock, performerRepositoryMock, staffRepositoryMock,
				mediaWikiSourceMapper)
	}

	def "gets character by name from repository"() {
		given:
		Character character = Mock(Character)

		when:
		Optional<Character> characterOptional = entityLookupByNameService.findCharacterByName(CHARACTER_NAME, SOURCES_MEDIA_WIKI_SOURCE)

		then:
		1 * mediaWikiSourceMapper.fromSourcesToEntity(SOURCES_MEDIA_WIKI_SOURCE) >> MODEL_MEDIA_WIKI_SOURCE
		1 * characterRepositoryMock.findByPageTitleAndPageMediaWikiSource(CHARACTER_NAME, MODEL_MEDIA_WIKI_SOURCE) >> Optional.of(character)
		0 * _
		characterOptional.get() == character
	}

	def "gets character by name from page api, then from repository"() {
		given:
		Character character = Mock(Character)
		Page page = Mock(Page)

		when:
		Optional<Character> characterOptional = entityLookupByNameService.findCharacterByName(CHARACTER_NAME, SOURCES_MEDIA_WIKI_SOURCE)

		then:
		2 * mediaWikiSourceMapper.fromSourcesToEntity(SOURCES_MEDIA_WIKI_SOURCE) >> MODEL_MEDIA_WIKI_SOURCE
		1 * characterRepositoryMock.findByPageTitleAndPageMediaWikiSource(CHARACTER_NAME, MODEL_MEDIA_WIKI_SOURCE) >> Optional.empty()
		1 * pageApiMock.getPage(CHARACTER_NAME, SOURCES_MEDIA_WIKI_SOURCE) >> page
		1 * page.getPageId() >> PAGE_ID
		1 * page.getMediaWikiSource() >> SOURCES_MEDIA_WIKI_SOURCE
		1 * characterRepositoryMock.findByPagePageIdAndPageMediaWikiSource(PAGE_ID, MODEL_MEDIA_WIKI_SOURCE) >> Optional.of(character)
		0 * _
		characterOptional.get() == character
	}

	def "gets character by name from page api, then from repository, when NonUniqueResultException was thrown"() {
		given:
		Character character = Mock(Character)
		Page page = Mock(Page)

		when:
		Optional<Character> characterOptional = entityLookupByNameService.findCharacterByName(CHARACTER_NAME, SOURCES_MEDIA_WIKI_SOURCE)

		then:
		2 * mediaWikiSourceMapper.fromSourcesToEntity(SOURCES_MEDIA_WIKI_SOURCE) >> MODEL_MEDIA_WIKI_SOURCE
		1 * characterRepositoryMock.findByPageTitleAndPageMediaWikiSource(CHARACTER_NAME, MODEL_MEDIA_WIKI_SOURCE) >> { args ->
			throw new NonUniqueResultException()
		}
		1 * pageApiMock.getPage(CHARACTER_NAME, SOURCES_MEDIA_WIKI_SOURCE) >> page
		1 * page.getPageId() >> PAGE_ID
		1 * page.getMediaWikiSource() >> SOURCES_MEDIA_WIKI_SOURCE
		1 * characterRepositoryMock.findByPagePageIdAndPageMediaWikiSource(PAGE_ID, MODEL_MEDIA_WIKI_SOURCE) >> Optional.of(character)
		0 * _
		characterOptional.get() == character
	}

	def "does not get character when page api returns null"() {
		when:
		Optional<Character> characterOptional = entityLookupByNameService.findCharacterByName(CHARACTER_NAME, SOURCES_MEDIA_WIKI_SOURCE)

		then:
		1 * mediaWikiSourceMapper.fromSourcesToEntity(SOURCES_MEDIA_WIKI_SOURCE) >> MODEL_MEDIA_WIKI_SOURCE
		1 * characterRepositoryMock.findByPageTitleAndPageMediaWikiSource(CHARACTER_NAME, MODEL_MEDIA_WIKI_SOURCE) >> Optional.empty()
		1 * pageApiMock.getPage(CHARACTER_NAME, SOURCES_MEDIA_WIKI_SOURCE) >> null
		0 * _
		!characterOptional.present
	}

	def "does not get character when page api returns page, but character repository returns empty optional"() {
		given:
		Page page = Mock(Page)

		when:
		Optional<Character> characterOptional = entityLookupByNameService.findCharacterByName(CHARACTER_NAME, SOURCES_MEDIA_WIKI_SOURCE)

		then:
		2 * mediaWikiSourceMapper.fromSourcesToEntity(SOURCES_MEDIA_WIKI_SOURCE) >> MODEL_MEDIA_WIKI_SOURCE
		1 * characterRepositoryMock.findByPageTitleAndPageMediaWikiSource(CHARACTER_NAME, MODEL_MEDIA_WIKI_SOURCE) >> Optional.empty()
		1 * pageApiMock.getPage(CHARACTER_NAME, SOURCES_MEDIA_WIKI_SOURCE) >> page
		1 * page.getPageId() >> PAGE_ID
		1 * page.getMediaWikiSource() >> SOURCES_MEDIA_WIKI_SOURCE
		1 * characterRepositoryMock.findByPagePageIdAndPageMediaWikiSource(PAGE_ID, MODEL_MEDIA_WIKI_SOURCE) >> Optional.empty()
		0 * _
		!characterOptional.present
	}

	def "gets performer by name from repository"() {
		given:
		Performer performer = Mock(Performer)

		when:
		Optional<Performer> performerOptional = entityLookupByNameService.findPerformerByName(PERFORMER_NAME, SOURCES_MEDIA_WIKI_SOURCE)

		then:
		1 * mediaWikiSourceMapper.fromSourcesToEntity(SOURCES_MEDIA_WIKI_SOURCE) >> MODEL_MEDIA_WIKI_SOURCE
		1 * performerRepositoryMock.findByPageTitleAndPageMediaWikiSource(PERFORMER_NAME, MODEL_MEDIA_WIKI_SOURCE) >> Optional.of(performer)
		0 * _
		performerOptional.get() == performer
	}

	def "gets performer by name from page api, then from repository"() {
		given:
		Performer performer = Mock(Performer)
		Page page = Mock(Page)

		when:
		Optional<Performer> performerOptional = entityLookupByNameService.findPerformerByName(PERFORMER_NAME, SOURCES_MEDIA_WIKI_SOURCE)

		then:
		2 * mediaWikiSourceMapper.fromSourcesToEntity(SOURCES_MEDIA_WIKI_SOURCE) >> MODEL_MEDIA_WIKI_SOURCE
		1 * performerRepositoryMock.findByPageTitleAndPageMediaWikiSource(PERFORMER_NAME, MODEL_MEDIA_WIKI_SOURCE) >> Optional.empty()
		1 * pageApiMock.getPage(PERFORMER_NAME, SOURCES_MEDIA_WIKI_SOURCE) >> page
		1 * page.getPageId() >> PAGE_ID
		1 * page.getMediaWikiSource() >> SOURCES_MEDIA_WIKI_SOURCE
		1 * performerRepositoryMock.findByPagePageIdAndPageMediaWikiSource(PAGE_ID, MODEL_MEDIA_WIKI_SOURCE) >> Optional.of(performer)
		0 * _
		performerOptional.get() == performer
	}

	def "gets performer by name from page api, then from repository, when NonUniqueResultException was thrown"() {
		given:
		Performer performer = Mock(Performer)
		Page page = Mock(Page)

		when:
		Optional<Performer> performerOptional = entityLookupByNameService.findPerformerByName(PERFORMER_NAME, SOURCES_MEDIA_WIKI_SOURCE)

		then:
		2 * mediaWikiSourceMapper.fromSourcesToEntity(SOURCES_MEDIA_WIKI_SOURCE) >> MODEL_MEDIA_WIKI_SOURCE
		1 * performerRepositoryMock.findByPageTitleAndPageMediaWikiSource(PERFORMER_NAME, MODEL_MEDIA_WIKI_SOURCE) >> { args ->
			throw new NonUniqueResultException()
		}
		1 * pageApiMock.getPage(PERFORMER_NAME, SOURCES_MEDIA_WIKI_SOURCE) >> page
		1 * page.getPageId() >> PAGE_ID
		1 * page.getMediaWikiSource() >> SOURCES_MEDIA_WIKI_SOURCE
		1 * performerRepositoryMock.findByPagePageIdAndPageMediaWikiSource(PAGE_ID, MODEL_MEDIA_WIKI_SOURCE) >> Optional.of(performer)
		0 * _
		performerOptional.get() == performer
	}

	def "does not get performer when page api returns null"() {
		when:
		Optional<Performer> performerOptional = entityLookupByNameService.findPerformerByName(PERFORMER_NAME, SOURCES_MEDIA_WIKI_SOURCE)

		then:
		1 * mediaWikiSourceMapper.fromSourcesToEntity(SOURCES_MEDIA_WIKI_SOURCE) >> MODEL_MEDIA_WIKI_SOURCE
		1 * performerRepositoryMock.findByPageTitleAndPageMediaWikiSource(PERFORMER_NAME, MODEL_MEDIA_WIKI_SOURCE) >> Optional.empty()
		1 * pageApiMock.getPage(PERFORMER_NAME, SOURCES_MEDIA_WIKI_SOURCE) >> null
		0 * _
		!performerOptional.present
	}

	def "does not get performer when page api returns page, but performer repository returns empty optional"() {
		given:
		Page page = Mock(Page)

		when:
		Optional<Performer> performerOptional = entityLookupByNameService.findPerformerByName(PERFORMER_NAME, SOURCES_MEDIA_WIKI_SOURCE)

		then:
		2 * mediaWikiSourceMapper.fromSourcesToEntity(SOURCES_MEDIA_WIKI_SOURCE) >> MODEL_MEDIA_WIKI_SOURCE
		1 * performerRepositoryMock.findByPageTitleAndPageMediaWikiSource(PERFORMER_NAME, MODEL_MEDIA_WIKI_SOURCE) >> Optional.empty()
		1 * pageApiMock.getPage(PERFORMER_NAME, SOURCES_MEDIA_WIKI_SOURCE) >> page
		1 * page.getPageId() >> PAGE_ID
		1 * page.getMediaWikiSource() >> SOURCES_MEDIA_WIKI_SOURCE
		1 * performerRepositoryMock.findByPagePageIdAndPageMediaWikiSource(PAGE_ID, MODEL_MEDIA_WIKI_SOURCE) >> Optional.empty()
		0 * _
		!performerOptional.present
	}


	def "gets staff by name from repository"() {
		given:
		Staff staff = Mock(Staff)

		when:
		Optional<Staff> staffOptional = entityLookupByNameService.findStaffByName(STAFF_NAME, SOURCES_MEDIA_WIKI_SOURCE)

		then:
		1 * mediaWikiSourceMapper.fromSourcesToEntity(SOURCES_MEDIA_WIKI_SOURCE) >> MODEL_MEDIA_WIKI_SOURCE
		1 * staffRepositoryMock.findByPageTitleAndPageMediaWikiSource(STAFF_NAME, MODEL_MEDIA_WIKI_SOURCE) >> Optional.of(staff)
		0 * _
		staffOptional.get() == staff
	}

	def "gets staff by name from page api, then from repository"() {
		given:
		Staff staff = Mock(Staff)
		Page page = Mock(Page)

		when:
		Optional<Staff> staffOptional = entityLookupByNameService.findStaffByName(STAFF_NAME, SOURCES_MEDIA_WIKI_SOURCE)

		then:
		2 * mediaWikiSourceMapper.fromSourcesToEntity(SOURCES_MEDIA_WIKI_SOURCE) >> MODEL_MEDIA_WIKI_SOURCE
		1 * staffRepositoryMock.findByPageTitleAndPageMediaWikiSource(STAFF_NAME, MODEL_MEDIA_WIKI_SOURCE) >> Optional.empty()
		1 * pageApiMock.getPage(STAFF_NAME, SOURCES_MEDIA_WIKI_SOURCE) >> page
		1 * page.getPageId() >> PAGE_ID
		1 * page.getMediaWikiSource() >> SOURCES_MEDIA_WIKI_SOURCE
		1 * staffRepositoryMock.findByPagePageIdAndPageMediaWikiSource(PAGE_ID, MODEL_MEDIA_WIKI_SOURCE) >> Optional.of(staff)
		0 * _
		staffOptional.get() == staff
	}

	def "gets staff by name from page api, then from repository, when NonUniqueResultException was thrown"() {
		given:
		Staff staff = Mock(Staff)
		Page page = Mock(Page)

		when:
		Optional<Staff> staffOptional = entityLookupByNameService.findStaffByName(STAFF_NAME, SOURCES_MEDIA_WIKI_SOURCE)

		then:
		2 * mediaWikiSourceMapper.fromSourcesToEntity(SOURCES_MEDIA_WIKI_SOURCE) >> MODEL_MEDIA_WIKI_SOURCE
		1 * staffRepositoryMock.findByPageTitleAndPageMediaWikiSource(STAFF_NAME, MODEL_MEDIA_WIKI_SOURCE) >> { args ->
			throw new NonUniqueResultException()
		}
		1 * pageApiMock.getPage(STAFF_NAME, SOURCES_MEDIA_WIKI_SOURCE) >> page
		1 * page.getPageId() >> PAGE_ID
		1 * page.getMediaWikiSource() >> SOURCES_MEDIA_WIKI_SOURCE
		1 * staffRepositoryMock.findByPagePageIdAndPageMediaWikiSource(PAGE_ID, MODEL_MEDIA_WIKI_SOURCE) >> Optional.of(staff)
		0 * _
		staffOptional.get() == staff
	}

	def "does not get staff when page api returns null"() {
		when:
		Optional<Staff> staffOptional = entityLookupByNameService.findStaffByName(STAFF_NAME, SOURCES_MEDIA_WIKI_SOURCE)

		then:
		1 * mediaWikiSourceMapper.fromSourcesToEntity(SOURCES_MEDIA_WIKI_SOURCE) >> MODEL_MEDIA_WIKI_SOURCE
		1 * staffRepositoryMock.findByPageTitleAndPageMediaWikiSource(STAFF_NAME, MODEL_MEDIA_WIKI_SOURCE) >> Optional.empty()
		1 * pageApiMock.getPage(STAFF_NAME, SOURCES_MEDIA_WIKI_SOURCE) >> null
		0 * _
		!staffOptional.present
	}

	def "does not get staff when page api returns page, but staff repository returns empty optional"() {
		given:
		Page page = Mock(Page)

		when:
		Optional<Staff> staffOptional = entityLookupByNameService.findStaffByName(STAFF_NAME, SOURCES_MEDIA_WIKI_SOURCE)

		then:
		2 * mediaWikiSourceMapper.fromSourcesToEntity(SOURCES_MEDIA_WIKI_SOURCE) >> MODEL_MEDIA_WIKI_SOURCE
		1 * staffRepositoryMock.findByPageTitleAndPageMediaWikiSource(STAFF_NAME, MODEL_MEDIA_WIKI_SOURCE) >> Optional.empty()
		1 * pageApiMock.getPage(STAFF_NAME, SOURCES_MEDIA_WIKI_SOURCE) >> page
		1 * page.getPageId() >> PAGE_ID
		1 * page.getMediaWikiSource() >> SOURCES_MEDIA_WIKI_SOURCE
		1 * staffRepositoryMock.findByPagePageIdAndPageMediaWikiSource(PAGE_ID, MODEL_MEDIA_WIKI_SOURCE) >> Optional.empty()
		0 * _
		!staffOptional.present
	}

}
