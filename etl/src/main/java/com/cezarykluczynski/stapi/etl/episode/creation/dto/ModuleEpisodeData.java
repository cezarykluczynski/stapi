package com.cezarykluczynski.stapi.etl.episode.creation.dto;

import lombok.Data;

@Data
public class ModuleEpisodeData {

	private String pageTitle;
	private String series;
	private Integer seasonNumber;
	private Integer episodeNumber;
	private Integer releaseDay;
	private Integer releaseMonth;
	private Integer releaseYear;
	private String productionNumber;

}
