package com.cezarykluczynski.stapi.server.common.cache

import com.cezarykluczynski.stapi.model.character.dto.CharacterRequestDTO
import com.cezarykluczynski.stapi.model.character.entity.Character
import com.cezarykluczynski.stapi.model.character.repository.CharacterRepository
import com.cezarykluczynski.stapi.model.common.service.RepositoryProvider
import com.cezarykluczynski.stapi.model.performer.dto.PerformerRequestDTO
import com.cezarykluczynski.stapi.model.performer.entity.Performer
import com.cezarykluczynski.stapi.model.performer.repository.PerformerRepository
import com.google.common.collect.Maps
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.CrudRepository
import spock.lang.Specification

class EagerCachingExecutorTest extends Specification {

	static final Pageable PAGEABLE = Pageable.ofSize(1)

	EagerCachingDecider eagerCachingDeciderMock

	CacheableClassForFullEntitiesSearchProvider cacheableClassForFullEntitiesSearchProviderMock

	RepositoryProvider repositoryProviderMock

	EagerCacheReflectionHelper eagerCacheReflectionHelperMock

	EagerCachingExecutor eagerCachingExecutor

	void setup() {
		eagerCachingDeciderMock = Mock()
		cacheableClassForFullEntitiesSearchProviderMock = Mock()
		repositoryProviderMock = Mock()
		eagerCacheReflectionHelperMock = Mock()
		eagerCachingExecutor = new EagerCachingExecutor(eagerCachingDeciderMock, cacheableClassForFullEntitiesSearchProviderMock,
				repositoryProviderMock, eagerCacheReflectionHelperMock)
	}

	void "when eager caching is disabled, nothing happens"() {
		when:
		eagerCachingExecutor.onApplicationReadyEvent(null)

		then:
		1 * eagerCachingDeciderMock.eagerCachingEnabled >> false
		0 * _
	}

	void "when eager caching is enabled, cache loading is performed"() {
		given:
		Map<Class, CrudRepository> classCrudRepositoryMap = Maps.newHashMap()
		CrudRepository performerRepository = Mock(PerformerRepository)
		CrudRepository characterRepository = Mock(CharacterRepository)
		classCrudRepositoryMap.put(Performer, performerRepository)
		classCrudRepositoryMap.put(Character, characterRepository)
		Performer performer1 = Mock()
		Performer performer2 = Mock()
		Character character1 = Mock()
		Character character2 = Mock()
		PerformerRequestDTO performerRequestDTO1 = Mock()
		PerformerRequestDTO performerRequestDTO2 = Mock()
		CharacterRequestDTO characterRequestDTO1 = Mock()
		CharacterRequestDTO characterRequestDTO2 = Mock()

		when:
		eagerCachingExecutor.onApplicationReadyEvent(null)

		then: 'initial setup is done'
		1 * eagerCachingDeciderMock.eagerCachingEnabled >> true
		1 * cacheableClassForFullEntitiesSearchProviderMock.listOfEntities >> [Performer, Character]
		1 * repositoryProviderMock.provide() >> classCrudRepositoryMap

		then: 'performer cache is loaded'
		1 * performerRepository.findAll() >> [performer1, performer2]
		1 * eagerCacheReflectionHelperMock.getCriteriaClass(performerRepository) >> PerformerRequestDTO
		1 * eagerCacheReflectionHelperMock.createCriteria(PerformerRequestDTO, performer1) >> performerRequestDTO1
		1 * performerRepository.findMatching(performerRequestDTO1, PAGEABLE)
		1 * eagerCacheReflectionHelperMock.createCriteria(PerformerRequestDTO, performer2) >> performerRequestDTO2
		1 * performerRepository.findMatching(performerRequestDTO2, PAGEABLE)

		then: 'character cache is loaded'
		1 * characterRepository.findAll() >> [character1, character2]
		1 * eagerCacheReflectionHelperMock.getCriteriaClass(characterRepository) >> CharacterRequestDTO
		1 * eagerCacheReflectionHelperMock.createCriteria(CharacterRequestDTO, character1) >> characterRequestDTO1
		1 * characterRepository.findMatching(characterRequestDTO1, PAGEABLE)
		1 * eagerCacheReflectionHelperMock.createCriteria(CharacterRequestDTO, character2) >> characterRequestDTO2
		1 * characterRepository.findMatching(characterRequestDTO2, PAGEABLE)

		then: 'no other interaction is expected'
		0 * _
	}

}
