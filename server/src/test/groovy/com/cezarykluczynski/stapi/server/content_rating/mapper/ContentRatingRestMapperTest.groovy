package com.cezarykluczynski.stapi.server.content_rating.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.ContentRating as RestContentRating
import com.cezarykluczynski.stapi.model.content_rating.entity.ContentRating
import com.cezarykluczynski.stapi.model.content_rating.entity.enums.ContentRatingSystem
import com.cezarykluczynski.stapi.util.tool.RandomUtil
import org.mapstruct.factory.Mappers
import spock.lang.Specification

class ContentRatingRestMapperTest extends Specification {

	private static final String UID = 'UID'
	private static final ContentRatingSystem CONTENT_RATING_SYSTEM = RandomUtil.randomEnumValue(ContentRatingSystem)
	private static final String RATING = 'RATING'

	private ContentRatingRestMapper contentRatingRestMapper

	void setup() {
		contentRatingRestMapper = Mappers.getMapper(ContentRatingRestMapper)
	}

	void "maps db entity to SOAP entity"() {
		given:
		ContentRating contentRating = new ContentRating(
				uid: UID,
				contentRatingSystem: CONTENT_RATING_SYSTEM,
				rating: RATING)

		when:
		RestContentRating restContentRating = contentRatingRestMapper.map(contentRating)

		then:
		restContentRating.uid == UID
		restContentRating.contentRatingSystem.name() == CONTENT_RATING_SYSTEM.name()
		restContentRating.rating == RATING
	}

}
