package com.cezarykluczynski.stapi.server.content_rating.mapper

import com.cezarykluczynski.stapi.client.v1.soap.ContentRating as SoapContentRating
import com.cezarykluczynski.stapi.model.content_rating.entity.ContentRating
import com.cezarykluczynski.stapi.model.content_rating.entity.enums.ContentRatingSystem
import com.cezarykluczynski.stapi.util.tool.RandomUtil
import org.mapstruct.factory.Mappers
import spock.lang.Specification

class ContentRatingSoapMapperTest extends Specification {

	private static final String UID = 'UID'
	private static final ContentRatingSystem CONTENT_RATING_SYSTEM = RandomUtil.randomEnumValue(ContentRatingSystem)
	private static final String RATING = 'RATING'

	private ContentRatingSoapMapper contentRatingSoapMapper

	void setup() {
		contentRatingSoapMapper = Mappers.getMapper(ContentRatingSoapMapper)
	}

	void "maps db entity to SOAP entity"() {
		given:
		ContentRating contentRating = new ContentRating(
				uid: UID,
				contentRatingSystem: CONTENT_RATING_SYSTEM,
				rating: RATING)

		when:
		SoapContentRating soapContentRating = contentRatingSoapMapper.map(contentRating)

		then:
		soapContentRating.uid == UID
		soapContentRating.contentRatingSystem.name() == CONTENT_RATING_SYSTEM.name()
		soapContentRating.rating == RATING
	}

}
