package com.cezarykluczynski.stapi.server.episode.mapper

import com.cezarykluczynski.stapi.client.v1.rest.model.EpisodeHeader
import com.cezarykluczynski.stapi.model.episode.entity.Episode
import com.google.common.collect.Lists
import org.mapstruct.factory.Mappers

class EpisodeHeaderRestMapperTest extends AbstractEpisodeMapperTest {

	private EpisodeHeaderRestMapper episodeHeaderRestMapper

	void setup() {
		episodeHeaderRestMapper = Mappers.getMapper(EpisodeHeaderRestMapper)
	}

	void "maps DB entity to REST header"() {
		given:
		Episode episode = new Episode(
				title: TITLE,
				uid: UID)

		when:
		EpisodeHeader episodeHeader = episodeHeaderRestMapper.map(Lists.newArrayList(episode))[0]

		then:
		episodeHeader.title == TITLE
		episodeHeader.uid == UID
	}

}
