package com.cezarykluczynski.stapi.server.content_language.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.ContentLanguage as RestContentLanguage
import com.cezarykluczynski.stapi.model.content_language.entity.ContentLanguage
import org.mapstruct.factory.Mappers
import spock.lang.Specification

class ContentLanguageRestMapperTest extends Specification {

	private static final String UID = 'UID'
	private static final String NAME = 'NAME'
	private static final String CODE = 'CODE'

	private ContentLanguageRestMapper contentLanguageRestMapper

	void setup() {
		contentLanguageRestMapper = Mappers.getMapper(ContentLanguageRestMapper)
	}

	void "maps db entity to REST entity"() {
		given:
		ContentLanguage contentLanguage = new ContentLanguage(
				uid: UID,
				name: NAME,
				iso6391Code: CODE)

		when:
		RestContentLanguage restContentLanguage = contentLanguageRestMapper.map(contentLanguage)

		then:
		restContentLanguage.uid == UID
		restContentLanguage.name == NAME
		restContentLanguage.iso6391Code == CODE
	}

}
