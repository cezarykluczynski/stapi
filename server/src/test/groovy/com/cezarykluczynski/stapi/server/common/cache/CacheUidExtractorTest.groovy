package com.cezarykluczynski.stapi.server.common.cache

import com.cezarykluczynski.stapi.model.staff.dto.StaffRequestDTO
import spock.lang.Specification

class CacheUidExtractorTest extends Specification {

	static final String UID = 'UID'

	CacheUidExtractor cacheUidExtractor

	void setup() {
		cacheUidExtractor = new CacheUidExtractor()
	}

	void "extracts UID when it is found on criteria class"() {
		given:
		StaffRequestDTO staffRequestDTO = new StaffRequestDTO(uid: UID)

		expect:
		cacheUidExtractor.extractUid(staffRequestDTO) == Optional.of(UID)
	}

	void "returns empty optional when UID is not present on criteria class"() {
		given:
		StaffRequestDTO staffRequestDTO = new StaffRequestDTO()

		expect:
		cacheUidExtractor.extractUid(staffRequestDTO) == Optional.empty()
	}

	void "returns empty optional when class does not have UID field"() {
		expect:
		cacheUidExtractor.extractUid(new Object()) == Optional.empty()
	}

}
