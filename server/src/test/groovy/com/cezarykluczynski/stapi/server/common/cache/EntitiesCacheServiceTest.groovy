package com.cezarykluczynski.stapi.server.common.cache

import com.cezarykluczynski.stapi.model.astronomical_object.dto.AstronomicalObjectRequestDTO
import com.cezarykluczynski.stapi.model.astronomical_object.entity.AstronomicalObject
import com.cezarykluczynski.stapi.model.common.service.UidGenerator
import com.cezarykluczynski.stapi.model.staff.dto.StaffRequestDTO
import com.cezarykluczynski.stapi.model.title.dto.TitleRequestDTO
import com.cezarykluczynski.stapi.model.title.entity.Title
import spock.lang.Specification

class EntitiesCacheServiceTest extends Specification {

	private static final String UID = 'ABCD0123456789'

	CacheUidExtractor cacheUidExtractorMock

	UidGenerator uidGeneratorMock

	CacheableClassForFullEntitiesSearchProvider cacheableClassForFullEntitiesSearchProviderMock

	EntitiesCacheService entitiesCacheService

	void setup() {
		cacheUidExtractorMock = Mock()
		uidGeneratorMock = Mock()
		cacheableClassForFullEntitiesSearchProviderMock = Mock()
		entitiesCacheService = new EntitiesCacheService(cacheUidExtractorMock, uidGeneratorMock, cacheableClassForFullEntitiesSearchProviderMock)
	}

	void "response is not cacheable when UID is not present in request"() {
		given:
		StaffRequestDTO staffRequestDTO = new StaffRequestDTO(placeOfBirth: 'Canada', artDepartment: true)

		when:
		boolean cacheable = entitiesCacheService.isCacheable(staffRequestDTO)

		then:
		1 * cacheUidExtractorMock.extractUid(staffRequestDTO) >> Optional.empty()
		0 * _
		!cacheable
	}

	void "response is not cacheable when class is not cacheable"() {
		given:
		TitleRequestDTO titleRequestDTO = new TitleRequestDTO(uid: UID)

		when:
		boolean cacheable = entitiesCacheService.isCacheable(titleRequestDTO)

		then:
		1 * cacheUidExtractorMock.extractUid(titleRequestDTO) >> Optional.of(UID)
		1 * uidGeneratorMock.retrieveEntityClassFromUid(UID) >> Title
		1 * cacheableClassForFullEntitiesSearchProviderMock.isClassCacheable(Title) >> false
		0 * _
		!cacheable
	}

	void "response is cacheable when class is cacheable"() {
		given:
		AstronomicalObjectRequestDTO astronomicalObjectRequestDTO = new AstronomicalObjectRequestDTO(uid: UID)

		when:
		boolean cacheable = entitiesCacheService.isCacheable(astronomicalObjectRequestDTO)

		then:
		1 * cacheUidExtractorMock.extractUid(astronomicalObjectRequestDTO) >> Optional.of(UID)
		1 * uidGeneratorMock.retrieveEntityClassFromUid(UID) >> AstronomicalObject
		1 * cacheableClassForFullEntitiesSearchProviderMock.isClassCacheable(AstronomicalObject) >> false
		0 * _
		!cacheable
	}

	void "existing UID is resolved using UID extractor"() {
		given:
		AstronomicalObjectRequestDTO astronomicalObjectRequestDTO = new AstronomicalObjectRequestDTO(uid: UID)

		when:
		String uid = entitiesCacheService.resolveKey(astronomicalObjectRequestDTO)

		then:
		1 * cacheUidExtractorMock.extractUid(astronomicalObjectRequestDTO) >> Optional.of(UID)
		0 * _
		uid == UID
	}

	void "non-existing UID is resolved using UID extractor"() {
		given:
		AstronomicalObjectRequestDTO astronomicalObjectRequestDTO = new AstronomicalObjectRequestDTO()

		when:
		String uid = entitiesCacheService.resolveKey(astronomicalObjectRequestDTO)

		then:
		1 * cacheUidExtractorMock.extractUid(astronomicalObjectRequestDTO) >> Optional.empty()
		0 * _
		uid == null
	}

}
