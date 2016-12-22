package com.cezarykluczynski.stapi.etl.series.creation.processor

import com.cezarykluczynski.stapi.etl.common.dto.FixedValueHolder
import com.cezarykluczynski.stapi.etl.series.creation.dto.SeriesEpisodeStatisticsDTO
import spock.lang.Specification

class SeriesEpisodeStatisticsFixedValueProviderTest extends Specification {

	private SeriesEpisodeStatisticsFixedValueProvider seriesEpisodeStatisticsFixedValueProvider

	def setup () {
		seriesEpisodeStatisticsFixedValueProvider = new SeriesEpisodeStatisticsFixedValueProvider()
	}

	def "gets fixed value holder when series is found"() {
		when:
		FixedValueHolder<SeriesEpisodeStatisticsDTO> seriesEpisodeStatisticsDTOFixedValueHolder =
				seriesEpisodeStatisticsFixedValueProvider.getSearchedValue('TNG')

		then:
		seriesEpisodeStatisticsDTOFixedValueHolder.found
		seriesEpisodeStatisticsDTOFixedValueHolder.value.seasonsCount == 7
		seriesEpisodeStatisticsDTOFixedValueHolder.value.episodesCount == 176
		seriesEpisodeStatisticsDTOFixedValueHolder.value.featureLengthEpisodesCount == 2
	}

	def "gets fixed value holder when episode is not found"() {
		when:
		FixedValueHolder<SeriesEpisodeStatisticsDTO> seriesEpisodeStatisticsDTOFixedValueHolder =
				seriesEpisodeStatisticsFixedValueProvider.getSearchedValue('NOT_AN_ABBREVIATION')

		then:
		!seriesEpisodeStatisticsDTOFixedValueHolder.found
		seriesEpisodeStatisticsDTOFixedValueHolder.value == null
	}

}
