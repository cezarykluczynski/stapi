package com.cezarykluczynski.stapi.server.episode.mapper

import com.cezarykluczynski.stapi.model.character.entity.Character
import com.cezarykluczynski.stapi.model.episode.entity.Episode
import com.cezarykluczynski.stapi.model.performer.entity.Performer
import com.cezarykluczynski.stapi.model.series.entity.Series
import com.cezarykluczynski.stapi.model.staff.entity.Staff
import com.cezarykluczynski.stapi.util.AbstractEpisodeTest
import com.google.common.collect.Sets

@SuppressWarnings('DuplicateNumberLiteral')
abstract class AbstractEpisodeMapperTest extends AbstractEpisodeTest {

	protected Episode createEpisode() {
		new Episode(
				guid: GUID,
				title: TITLE,
				titleGerman: TITLE_GERMAN,
				titleItalian: TITLE_ITALIAN,
				titleJapanese: TITLE_JAPANESE,
				series: Mock(Series),
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
				writers: Sets.newHashSet(Mock(Staff)),
				teleplayAuthors: Sets.newHashSet(Mock(Staff)),
				storyAuthors: Sets.newHashSet(Mock(Staff)),
				directors: Sets.newHashSet(Mock(Staff)),
				performers: Sets.newHashSet(Mock(Performer)),
				stuntPerformers: Sets.newHashSet(Mock(Performer)),
				standInPerformers: Sets.newHashSet(Mock(Performer)),
				characters: Sets.newHashSet(Mock(Character)))
	}

}
