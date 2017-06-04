package com.cezarykluczynski.stapi.etl.template.comicSeries.dto;

import com.cezarykluczynski.stapi.etl.template.publishableSeries.dto.PublishableSeriesTemplate;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ComicSeriesTemplate extends PublishableSeriesTemplate {

	private Integer numberOfIssues;

	private Boolean photonovelSeries;

}
