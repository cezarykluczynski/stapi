package com.cezarykluczynski.stapi.server.season.mapper

import com.cezarykluczynski.stapi.model.episode.entity.Episode
import com.cezarykluczynski.stapi.model.season.entity.Season
import com.cezarykluczynski.stapi.model.series.entity.Series
import com.cezarykluczynski.stapi.util.AbstractSeasonTest

abstract class AbstractSeasonMapperTest extends AbstractSeasonTest {

	protected Season createSeason() {
		new Season(
				uid: UID,
				title: TITLE,
				series: new Series(),
				seasonNumber: SEASON_NUMBER,
				numberOfEpisodes: NUMBER_OF_EPISODES,
				episodes: createSetOfRandomNumberOfMocks(Episode))
	}

}
