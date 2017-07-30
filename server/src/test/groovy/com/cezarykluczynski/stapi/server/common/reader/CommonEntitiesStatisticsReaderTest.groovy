package com.cezarykluczynski.stapi.server.common.reader

import com.cezarykluczynski.stapi.model.common.entity.PageAwareEntity
import com.cezarykluczynski.stapi.model.common.statistics.size.EntitySizeStatisticsProvider
import com.cezarykluczynski.stapi.model.series.entity.Series
import com.cezarykluczynski.stapi.model.species.entity.Species
import com.cezarykluczynski.stapi.model.video_game.entity.VideoGame
import com.cezarykluczynski.stapi.server.common.dto.RestEndpointStatisticsDTO
import com.google.common.collect.Maps
import spock.lang.Specification

class CommonEntitiesStatisticsReaderTest extends Specification {

	private static final long SERIES_COUNT = 10
	private static final long SPECIES_COUNT = 20
	private static final long IGNORED_COUNT = 0

	private EntitySizeStatisticsProvider entitySizeStatisticsProviderMock

	private CommonEntitiesStatisticsReader commonEntitiesStatisticsReader

	void setup() {
		entitySizeStatisticsProviderMock = Mock()
		commonEntitiesStatisticsReader = new CommonEntitiesStatisticsReader(entitySizeStatisticsProviderMock)
	}

	void "reads entities statistics from EntitySizeStatisticsProvider"() {
		given:
		Map<Class<? extends PageAwareEntity>, Long> map = Maps.newLinkedHashMap()
		map.put(Series, SERIES_COUNT)
		map.put(Species, SPECIES_COUNT)
		map.put(VideoGame, IGNORED_COUNT)

		when:
		RestEndpointStatisticsDTO restEndpointStatisticsDTO = commonEntitiesStatisticsReader.entitiesStatistics()

		then:
		1 * entitySizeStatisticsProviderMock.provide() >> map
		0 * _
		restEndpointStatisticsDTO.totalCount == SERIES_COUNT + SPECIES_COUNT
		restEndpointStatisticsDTO.statistics.size() == 2
		restEndpointStatisticsDTO.statistics[0].name == 'Series'
		restEndpointStatisticsDTO.statistics[0].count == SERIES_COUNT
		restEndpointStatisticsDTO.statistics[1].name == 'Species'
		restEndpointStatisticsDTO.statistics[1].count == SPECIES_COUNT
	}

}
