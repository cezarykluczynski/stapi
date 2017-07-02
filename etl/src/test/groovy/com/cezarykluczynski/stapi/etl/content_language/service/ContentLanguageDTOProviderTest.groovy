package com.cezarykluczynski.stapi.etl.content_language.service

import com.cezarykluczynski.stapi.etl.content_language.dto.ContentLanguageDTO
import spock.lang.Specification

class ContentLanguageDTOProviderTest extends Specification {

	private ContentLanguageDTOProvider contentLanguageDTOProvider

	void setup() {
		contentLanguageDTOProvider = new ContentLanguageDTOProvider()
	}

	void "gets existing content language"() {
		when:
		Optional<ContentLanguageDTO> contentLanguageDTOOptional = contentLanguageDTOProvider.getByName('Abkhaz')

		then:
		contentLanguageDTOOptional.isPresent()
		contentLanguageDTOOptional.get().name == 'Abkhaz'
		contentLanguageDTOOptional.get().code == 'ab'
	}

	void "returns null for non-existing content language"() {
		when:
		Optional<ContentLanguageDTO> contentLanguageDTOOptional = contentLanguageDTOProvider.getByName('Klingon')

		then:
		!contentLanguageDTOOptional.isPresent()
	}

}
