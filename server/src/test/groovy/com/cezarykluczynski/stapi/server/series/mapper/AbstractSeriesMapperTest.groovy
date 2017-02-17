package com.cezarykluczynski.stapi.server.series.mapper

import com.cezarykluczynski.stapi.model.episode.entity.Episode
import com.cezarykluczynski.stapi.model.series.entity.Series
import com.cezarykluczynski.stapi.util.AbstractSeriesTest
import com.google.common.collect.Lists

abstract class AbstractSeriesMapperTest extends AbstractSeriesTest {

	protected Series createSeries() {
		new Series(
				guid: GUID,
				title: TITLE,
				abbreviation: ABBREVIATION,
				productionStartYear: PRODUCTION_START_YEAR,
				productionEndYear: PRODUCTION_END_YEAR,
				originalRunStartDate: ORIGINAL_RUN_START_DATE,
				originalRunEndDate: ORIGINAL_RUN_END_DATE,
				seasonsCount: SEASONS_COUNT,
				episodesCount: EPISODES_COUNT,
				featureLengthEpisodesCount: FEATURE_LENGTH_EPISODES_COUNT,
				episodes: Lists.newArrayList(Mock(Episode)))
	}

}
