package com.cezarykluczynski.stapi.model.common.cache

import com.cezarykluczynski.stapi.model.animal.dto.AnimalRequestDTO
import com.cezarykluczynski.stapi.util.exception.StapiRuntimeException
import org.springframework.data.domain.Pageable
import spock.lang.Specification

class EntitiesCacheServiceTest extends Specification {

	private static final String UID = 'ABCD0123456789'

	EntitiesCacheService entitiesCacheService

	void setup() {
		entitiesCacheService = new EntitiesCacheService()
	}

	void "criteria with UID is cacheable and key can be generated"() {
		given:
		AnimalRequestDTO animalRequestDTO = new AnimalRequestDTO(uid: UID)
		Pageable pageable = Pageable.ofSize(50)

		when:
		boolean cacheable = entitiesCacheService.isCacheable(animalRequestDTO, pageable)

		then:
		cacheable

		when:
		String key = entitiesCacheService.resolveKey(animalRequestDTO, pageable)

		then:
		key == "UID:$UID"
	}

	void "criteria without UID is not cacheable and key cannot be generated"() {
		given:
		AnimalRequestDTO animalRequestDTO = new AnimalRequestDTO()
		Pageable pageable = Pageable.ofSize(50)

		when:
		boolean cacheable = entitiesCacheService.isCacheable(animalRequestDTO, pageable)

		then:
		!cacheable

		when:
		entitiesCacheService.resolveKey(animalRequestDTO, pageable)

		then:
		thrown(StapiRuntimeException)
	}

}
