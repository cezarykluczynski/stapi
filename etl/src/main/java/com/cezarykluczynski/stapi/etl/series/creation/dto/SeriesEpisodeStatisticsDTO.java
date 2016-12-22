package com.cezarykluczynski.stapi.etl.series.creation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor(staticName = "of")
@Getter
public class SeriesEpisodeStatisticsDTO {

	private Integer seasonsCount;

	private Integer episodesCount;

	private Integer featureLengthEpisodesCount;

}
