package com.cezarykluczynski.stapi.etl.content_language.service

import com.cezarykluczynski.stapi.etl.content_language.dto.ContentLanguageDTO
import spock.lang.Specification
import spock.lang.Unroll

class ContentLanguageDTOProviderTest extends Specification {

	private ContentLanguageDTOProvider contentLanguageDTOProvider

	void setup() {
		contentLanguageDTOProvider = new ContentLanguageDTOProvider()
	}

	@Unroll('when "#name" is passed, #contentLanguageDTOOptional is returned')
	void "when language is passed, ContentLanguageDTO is returned"() {
		expect:
		contentLanguageDTOOptional == contentLanguageDTOProvider.getByName(name)

		where:
		name                                                            | contentLanguageDTOOptional
		''                                                              | Optional.empty()
		'Abkhaz'                                                        | Optional.of(ContentLanguageDTO.of('Abkhaz', 'ab'))
		'Castillian'                                                    | Optional.of(ContentLanguageDTO.of('Spanish', 'es'))
		'Klingon'                                                       | Optional.empty()
		'English (Dolby Digital 5.1)'                                   | Optional.of(ContentLanguageDTO.of('English', 'en'))
		'and Polish'                                                    | Optional.of(ContentLanguageDTO.of('Polish', 'pl'))
		'English 5.1 Surround Ex'                                       | Optional.of(ContentLanguageDTO.of('English', 'en'))
		'Portuguese 2.0 Surround (Insurrection) 5.1 Surround (Nemesis)' | Optional.of(ContentLanguageDTO.of('Portuguese', 'pt'))
		'7.1 DTS-HD Master Audio: English;'                             | Optional.of(ContentLanguageDTO.of('English', 'en'))
		'Mandarin Chinese'                                              | Optional.of(ContentLanguageDTO.of('Chinese', 'zh'))
		'Brazilian  Portuguese'                                         | Optional.of(ContentLanguageDTO.of('Portuguese', 'pt'))
		'Traditional Chinese (Hong Kong)'                               | Optional.of(ContentLanguageDTO.of('Chinese', 'zh'))
	}

}
