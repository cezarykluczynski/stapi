package com.cezarykluczynski.stapi.server.series.mapper

import com.cezarykluczynski.stapi.model.company.entity.Company
import com.cezarykluczynski.stapi.model.episode.entity.Episode
import com.cezarykluczynski.stapi.model.season.entity.Season
import com.cezarykluczynski.stapi.model.series.entity.Series
import com.cezarykluczynski.stapi.util.AbstractSeriesTest

abstract class AbstractSeriesMapperTest extends AbstractSeriesTest {

	protected Series createSeries() {
		new Series(
				uid: UID,
				title: TITLE,
				abbreviation: ABBREVIATION,
				productionStartYear: PRODUCTION_START_YEAR,
				productionEndYear: PRODUCTION_END_YEAR,
				originalRunStartDate: ORIGINAL_RUN_START_DATE,
				originalRunEndDate: ORIGINAL_RUN_END_DATE,
				seasonsCount: SEASONS_COUNT,
				episodesCount: EPISODES_COUNT,
				featureLengthEpisodesCount: FEATURE_LENGTH_EPISODES_COUNT,
				originalBroadcaster: new Company(id: 1L),
				productionCompany: new Company(id: 2L),
				episodes: createSetOfRandomNumberOfMocks(Episode),
				seasons: createSetOfRandomNumberOfMocks(Season))
	}

}
