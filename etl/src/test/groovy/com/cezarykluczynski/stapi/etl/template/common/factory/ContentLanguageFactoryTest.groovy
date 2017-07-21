package com.cezarykluczynski.stapi.etl.template.common.factory

import com.cezarykluczynski.stapi.etl.content_language.dto.ContentLanguageDTO
import com.cezarykluczynski.stapi.etl.content_language.service.ContentLanguageDTOProvider
import com.cezarykluczynski.stapi.model.common.service.UidGenerator
import com.cezarykluczynski.stapi.model.content_language.entity.ContentLanguage
import com.cezarykluczynski.stapi.model.content_language.repository.ContentLanguageRepository
import spock.lang.Specification

class ContentLanguageFactoryTest extends Specification {

	private static final String NAME = 'NAME'
	private static final String CODE = 'CODE'
	private static final String UID = 'UID'

	private ContentLanguageDTOProvider contentLanguageDTOProviderMock

	private ContentLanguageRepository contentLanguageRepositoryMock

	private UidGenerator uidGeneratorMock

	private ContentLanguageFactory contentLanguageFactory

	void setup() {
		contentLanguageDTOProviderMock = Mock()
		contentLanguageRepositoryMock = Mock()
		uidGeneratorMock = Mock()
		contentLanguageFactory = new ContentLanguageFactory(contentLanguageDTOProviderMock, contentLanguageRepositoryMock, uidGeneratorMock)
	}

	void "returns existing entity when it can be found"() {
		given:
		ContentLanguage contentLanguage = Mock()

		when:
		Optional<ContentLanguage> contentLanguageOptional = contentLanguageFactory.createForName(NAME)

		then:
		1 * contentLanguageDTOProviderMock.getByName(NAME) >> Optional.of(ContentLanguageDTO.of(NAME, CODE))
		1 * contentLanguageRepositoryMock.findByName(NAME) >> Optional.of(contentLanguage)
		0 * _
		contentLanguageOptional.isPresent()
		contentLanguageOptional.get() == contentLanguage
	}

	void "returns new entity when language name is name of an existing language"() {
		when:
		Optional<ContentLanguage> contentLanguageOptional = contentLanguageFactory.createForName(NAME)

		then:
		1 * contentLanguageDTOProviderMock.getByName(NAME) >> Optional.of(ContentLanguageDTO.of(NAME, CODE))
		1 * contentLanguageRepositoryMock.findByName(NAME) >> Optional.empty()
		1 * uidGeneratorMock.generateForContentLanguage(CODE) >> UID
		1 * contentLanguageRepositoryMock.save(_ as ContentLanguage) >> { ContentLanguage contentLanguageInput ->
			assert contentLanguageInput.name == NAME
			assert contentLanguageInput.iso6391Code == CODE
			assert contentLanguageInput.uid == UID
		}
		0 * _
		contentLanguageOptional.isPresent()
		contentLanguageOptional.get().name == NAME
		contentLanguageOptional.get().iso6391Code == CODE
		contentLanguageOptional.get().uid == UID
	}

	void "returns empty optional when language cannot be found by name"() {
		when:
		Optional<ContentLanguage> contentLanguageOptional = contentLanguageFactory.createForName(NAME)

		then:
		1 * contentLanguageDTOProviderMock.getByName(NAME) >> Optional.empty()
		0 * _
		!contentLanguageOptional.isPresent()
	}

}
