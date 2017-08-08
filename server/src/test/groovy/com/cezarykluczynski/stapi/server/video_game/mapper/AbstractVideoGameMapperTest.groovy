package com.cezarykluczynski.stapi.server.video_game.mapper

import com.cezarykluczynski.stapi.model.company.entity.Company
import com.cezarykluczynski.stapi.model.content_rating.entity.ContentRating
import com.cezarykluczynski.stapi.model.genre.entity.Genre
import com.cezarykluczynski.stapi.model.platform.entity.Platform
import com.cezarykluczynski.stapi.model.reference.entity.Reference
import com.cezarykluczynski.stapi.model.video_game.entity.VideoGame
import com.cezarykluczynski.stapi.util.AbstractVideoGameTest

abstract class AbstractVideoGameMapperTest extends AbstractVideoGameTest {

	protected VideoGame createVideoGame() {
		new VideoGame(
				uid: UID,
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
	}

}
