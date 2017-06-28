package com.cezarykluczynski.stapi.server.episode.mapper

import com.cezarykluczynski.stapi.model.character.entity.Character
import com.cezarykluczynski.stapi.model.episode.entity.Episode
import com.cezarykluczynski.stapi.model.performer.entity.Performer
import com.cezarykluczynski.stapi.model.season.entity.Season
import com.cezarykluczynski.stapi.model.series.entity.Series
import com.cezarykluczynski.stapi.model.staff.entity.Staff
import com.cezarykluczynski.stapi.util.AbstractEpisodeTest

@SuppressWarnings('DuplicateNumberLiteral')
abstract class AbstractEpisodeMapperTest extends AbstractEpisodeTest {

	protected Episode createEpisode() {
		new Episode(
				uid: UID,
				title: TITLE,
				titleGerman: TITLE_GERMAN,
				titleItalian: TITLE_ITALIAN,
				titleJapanese: TITLE_JAPANESE,
				series: new Series(),
				season: new Season(),
				seasonNumber: SEASON_NUMBER,
				episodeNumber: EPISODE_NUMBER,
				productionSerialNumber: PRODUCTION_SERIAL_NUMBER,
				featureLength: FEATURE_LENGTH,
				stardateFrom: STARDATE_FROM,
				stardateTo: STARDATE_TO,
				yearFrom: YEAR_FROM,
				yearTo: YEAR_TO,
				usAirDate: US_AIR_DATE,
				finalScriptDate: FINAL_SCRIPT_DATE,
				writers: createSetOfRandomNumberOfMocks(Staff),
				teleplayAuthors: createSetOfRandomNumberOfMocks(Staff),
				storyAuthors: createSetOfRandomNumberOfMocks(Staff),
				directors: createSetOfRandomNumberOfMocks(Staff),
				performers: createSetOfRandomNumberOfMocks(Performer),
				stuntPerformers: createSetOfRandomNumberOfMocks(Performer),
				standInPerformers: createSetOfRandomNumberOfMocks(Performer),
				characters: createSetOfRandomNumberOfMocks(Character))
	}

}
