package com.cezarykluczynski.stapi.etl.template.episode.dto;

import com.cezarykluczynski.stapi.model.episode.entity.Episode;
import com.cezarykluczynski.stapi.model.page.entity.Page;
import com.cezarykluczynski.stapi.model.series.entity.Series;
import lombok.Data;

@Data
public class EpisodeTemplate {

	private Page page;

	private Series series;

	private String title;

	private Episode episodeStub;

}
