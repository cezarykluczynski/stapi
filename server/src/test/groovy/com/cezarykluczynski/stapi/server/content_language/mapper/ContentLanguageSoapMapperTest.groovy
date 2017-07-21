package com.cezarykluczynski.stapi.server.content_language.mapper

import com.cezarykluczynski.stapi.client.v1.soap.ContentLanguage as SoapContentLanguage
import com.cezarykluczynski.stapi.model.content_language.entity.ContentLanguage
import org.mapstruct.factory.Mappers
import spock.lang.Specification

class ContentLanguageSoapMapperTest extends Specification {

	private static final String UID = 'UID'
	private static final String NAME = 'NAME'
	private static final String CODE = 'CODE'

	private ContentLanguageSoapMapper contentLanguageSoapMapper

	void setup() {
		contentLanguageSoapMapper = Mappers.getMapper(ContentLanguageSoapMapper)
	}

	void "maps db entity to REST entity"() {
		given:
		ContentLanguage contentLanguage = new ContentLanguage(
				uid: UID,
				name: NAME,
				iso6391Code: CODE)

		when:
		SoapContentLanguage soapContentRating = contentLanguageSoapMapper.map(contentLanguage)

		then:
		soapContentRating.uid == UID
		soapContentRating.name == NAME
		soapContentRating.iso6391Code == CODE
	}

}
