package com.cezarykluczynski.stapi.server.series.mapper

import com.cezarykluczynski.stapi.client.v1.soap.SeriesFull
import com.cezarykluczynski.stapi.client.v1.soap.SeriesFullRequest
import com.cezarykluczynski.stapi.model.series.dto.SeriesRequestDTO
import com.cezarykluczynski.stapi.model.series.entity.Series
import org.mapstruct.factory.Mappers

class SeriesFullSoapMapperTest extends AbstractSeriesMapperTest {

	private SeriesFullSoapMapper seriesFullSoapMapper

	void setup() {
		seriesFullSoapMapper = Mappers.getMapper(SeriesFullSoapMapper)
	}

	void "maps SOAP SeriesFullRequest to SeriesBaseRequestDTO"() {
		given:
		SeriesFullRequest seriesFullRequest = new SeriesFullRequest(uid: UID)

		when:
		SeriesRequestDTO seriesRequestDTO = seriesFullSoapMapper.mapFull seriesFullRequest

		then:
		seriesRequestDTO.uid == UID
	}

	void "maps DB entity to full SOAP entity"() {
		given:
		Series series = createSeries()

		when:
		SeriesFull seriesFull = seriesFullSoapMapper.mapFull(series)

		then:
		seriesFull.uid == UID
		seriesFull.title == TITLE
		seriesFull.originalBroadcaster != null
		seriesFull.productionCompany != null
		seriesFull.abbreviation == ABBREVIATION
		seriesFull.productionStartYear == PRODUCTION_START_YEAR
		seriesFull.productionEndYear == PRODUCTION_END_YEAR
		seriesFull.originalRunStartDate == ORIGINAL_RUN_START_DATE_XML
		seriesFull.originalRunEndDate == ORIGINAL_RUN_END_DATE_XML
		seriesFull.seasonsCount == SEASONS_COUNT
		seriesFull.episodesCount == EPISODES_COUNT
		seriesFull.featureLengthEpisodesCount == FEATURE_LENGTH_EPISODES_COUNT
		seriesFull.episodes.size() == series.episodes.size()
		seriesFull.seasons.size() == series.seasons.size()
	}

}
