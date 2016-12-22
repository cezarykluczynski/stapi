package com.cezarykluczynski.stapi.etl.template.episode.dto;

import com.cezarykluczynski.stapi.model.episode.entity.Episode;
import com.cezarykluczynski.stapi.model.page.entity.Page;
import com.cezarykluczynski.stapi.model.series.entity.Series;
import lombok.Data;

import java.time.LocalDate;

@Data
public class EpisodeTemplate {

	private Page page;

	private Series series;

	private String title;

	private String titleGerman;

	private String titleItalian;

	private String titleJapanese;

	private Episode episodeStub;

	private Integer seasonNumber;

	private Integer episodeNumber;

	private String productionSerialNumber;

	private Boolean featureLength;

	private Float stardateFrom;

	private Float stardateTo;

	private Integer yearFrom;

	private Integer yearTo;

	private LocalDate usAirDate;

	private LocalDate finalScriptDate;

}
