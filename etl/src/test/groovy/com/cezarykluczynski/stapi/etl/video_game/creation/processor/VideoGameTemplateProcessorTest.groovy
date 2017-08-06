package com.cezarykluczynski.stapi.etl.video_game.creation.processor

import com.cezarykluczynski.stapi.etl.template.video_game.dto.VideoGameTemplate
import com.cezarykluczynski.stapi.model.common.service.UidGenerator
import com.cezarykluczynski.stapi.model.company.entity.Company
import com.cezarykluczynski.stapi.model.content_rating.entity.ContentRating
import com.cezarykluczynski.stapi.model.genre.entity.Genre
import com.cezarykluczynski.stapi.model.page.entity.Page
import com.cezarykluczynski.stapi.model.platform.entity.Platform
import com.cezarykluczynski.stapi.model.reference.entity.Reference
import com.cezarykluczynski.stapi.model.video_game.entity.VideoGame
import com.cezarykluczynski.stapi.util.AbstractVideoGameTest

class VideoGameTemplateProcessorTest extends AbstractVideoGameTest {

	private UidGenerator uidGeneratorMock

	private VideoGameTemplateProcessor videoGameTemplateProcessor

	private final Page page = Mock()

	void setup() {
		uidGeneratorMock = Mock()
		videoGameTemplateProcessor = new VideoGameTemplateProcessor(uidGeneratorMock)
	}

	void "converts VideoTemplate to VideoGame"() {
		given:
		VideoGameTemplate videoGameTemplate = new VideoGameTemplate(
				page: page,
				title: TITLE,
				releaseDate: RELEASE_DATE,
				stardateFrom: STARDATE_FROM,
				stardateTo: STARDATE_TO,
				yearFrom: YEAR_FROM,
				yearTo: YEAR_TO,
				systemRequirements: SYSTEM_REQUIREMENTS,
				publishers: createSetOfRandomNumberOfMocks(Company),
				developers: createSetOfRandomNumberOfMocks(Company),
				platforms: createSetOfRandomNumberOfMocks(Platform),
				genres: createSetOfRandomNumberOfMocks(Genre),
				ratings: createSetOfRandomNumberOfMocks(ContentRating),
				references: createSetOfRandomNumberOfMocks(Reference))

		when:
		VideoGame videoGame = videoGameTemplateProcessor.process(videoGameTemplate)

		then:
		1 * uidGeneratorMock.generateFromPage(page, VideoGame) >> UID
		0 * _
		videoGame.uid == UID
		videoGame.page == page
		videoGame.title == TITLE
		videoGame.releaseDate == RELEASE_DATE
		videoGame.stardateFrom == STARDATE_FROM
		videoGame.stardateTo == STARDATE_TO
		videoGame.yearFrom == YEAR_FROM
		videoGame.yearTo == YEAR_TO
		videoGame.systemRequirements == SYSTEM_REQUIREMENTS
		videoGame.publishers.size() == videoGameTemplate.publishers.size()
		videoGame.developers.size() == videoGameTemplate.developers.size()
		videoGame.platforms.size() == videoGameTemplate.platforms.size()
		videoGame.genres.size() == videoGameTemplate.genres.size()
		videoGame.ratings.size() == videoGameTemplate.ratings.size()
		videoGame.references.size() == videoGameTemplate.references.size()
	}

}
