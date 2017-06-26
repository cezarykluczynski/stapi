package com.cezarykluczynski.stapi.etl.template.episode.dto;

import com.cezarykluczynski.stapi.etl.template.common.dto.ImageTemplate;
import com.cezarykluczynski.stapi.model.episode.entity.Episode;
import com.cezarykluczynski.stapi.model.page.entity.Page;
import com.cezarykluczynski.stapi.model.season.entity.Season;
import com.cezarykluczynski.stapi.model.series.entity.Series;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
public class EpisodeTemplate extends ImageTemplate {

	private Page page;

	private Series series;

	private Season season;

	private Episode episodeStub;

	private String title;

	private String titleGerman;

	private String titleItalian;

	private String titleJapanese;

	private Integer seasonNumber;

	private Integer episodeNumber;

	private String productionSerialNumber;

	private Boolean featureLength;

	private LocalDate usAirDate;

	private LocalDate finalScriptDate;

}
