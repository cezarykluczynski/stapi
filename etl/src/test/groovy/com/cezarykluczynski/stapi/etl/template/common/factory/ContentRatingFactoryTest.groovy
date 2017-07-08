package com.cezarykluczynski.stapi.etl.template.common.factory

import com.cezarykluczynski.stapi.model.common.service.UidGenerator
import com.cezarykluczynski.stapi.model.content_rating.entity.ContentRating
import com.cezarykluczynski.stapi.model.content_rating.entity.enums.ContentRatingSystem
import com.cezarykluczynski.stapi.model.content_rating.repository.ContentRatingRepository
import spock.lang.Specification

class ContentRatingFactoryTest extends Specification {

	private static final ContentRatingSystem CONTENT_RATING_SYSTEM = ContentRatingSystem.ESRB
	private static final String RATING = 'M'
	private static final String RATING_PG_13 = 'PG-13'
	private static final String RATING_PG_13_CLEAN = 'PG13'
	private static final String UID = 'UID'

	private ContentRatingRepository contentRatingRepositoryMock

	private UidGenerator uidGeneratorMock

	private ContentRatingFactory contentRatingFactory

	void setup() {
		contentRatingRepositoryMock = Mock()
		uidGeneratorMock = Mock()
		contentRatingFactory = new ContentRatingFactory(contentRatingRepositoryMock, uidGeneratorMock)
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
			assert contentRatingInput.uid == UID
		}
		1 * uidGeneratorMock.generateForContentRating(CONTENT_RATING_SYSTEM, RATING) >> UID
		0 * _
		contentRating.contentRatingSystem == CONTENT_RATING_SYSTEM
		contentRating.rating == RATING
		contentRating.uid == UID
	}

	void "cleans PG-13"() {
		when:
		ContentRating contentRating = contentRatingFactory.create(CONTENT_RATING_SYSTEM, RATING_PG_13)

		then:
		1 * contentRatingRepositoryMock.findByContentRatingSystemAndRating(CONTENT_RATING_SYSTEM, RATING_PG_13_CLEAN) >> Optional.empty()
		1 * contentRatingRepositoryMock.save(_ as ContentRating) >> { ContentRating contentRatingInput ->
			assert contentRatingInput.rating == RATING_PG_13_CLEAN
		}
		contentRating.rating == RATING_PG_13_CLEAN
	}

}
