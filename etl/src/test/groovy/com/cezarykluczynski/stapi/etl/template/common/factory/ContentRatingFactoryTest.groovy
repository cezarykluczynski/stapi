package com.cezarykluczynski.stapi.etl.template.common.factory

import com.cezarykluczynski.stapi.model.content_rating.entity.ContentRating
import com.cezarykluczynski.stapi.model.content_rating.entity.enums.ContentRatingSystem
import com.cezarykluczynski.stapi.model.content_rating.repository.ContentRatingRepository
import spock.lang.Specification

class ContentRatingFactoryTest extends Specification {

	private static final ContentRatingSystem CONTENT_RATING_SYSTEM = ContentRatingSystem.ESRB
	private static final String RATING = 'M'

	private ContentRatingRepository contentRatingRepositoryMock

	private ContentRatingFactory contentRatingFactory

	void setup() {
		contentRatingRepositoryMock = Mock()
		contentRatingFactory = new ContentRatingFactory(contentRatingRepositoryMock)
	}

	void "returns existing ContentRating when it can be found"() {
		given:
		ContentRating contentRating = Mock()

		when:
		ContentRating contentRatingOutput = contentRatingFactory.create(CONTENT_RATING_SYSTEM, RATING)

		then:
		1 * contentRatingRepositoryMock.findByContentRatingSystemAndRating(CONTENT_RATING_SYSTEM, RATING) >> Optional.of(contentRating)
		0 * _
		contentRatingOutput == contentRating
	}

	void "creates ContentRating when none can be found"() {
		when:
		ContentRating contentRating = contentRatingFactory.create(CONTENT_RATING_SYSTEM, RATING)

		then:
		1 * contentRatingRepositoryMock.findByContentRatingSystemAndRating(CONTENT_RATING_SYSTEM, RATING) >> Optional.empty()
		1 * contentRatingRepositoryMock.save(_ as ContentRating) >> { ContentRating contentRatingInput ->
			assert contentRatingInput.contentRatingSystem == CONTENT_RATING_SYSTEM
			assert contentRatingInput.rating == RATING
		}
		0 * _
		contentRating.contentRatingSystem == CONTENT_RATING_SYSTEM
		contentRating.rating == RATING
	}

}
