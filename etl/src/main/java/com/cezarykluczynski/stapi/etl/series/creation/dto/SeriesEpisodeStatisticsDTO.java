package com.cezarykluczynski.stapi.etl.series.creation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(staticName = "of")
@Getter
public class SeriesEpisodeStatisticsDTO {

	private final Integer seasonsCount;

	private final Integer episodesCount;

	private final Integer featureLengthEpisodesCount;

}
