package com.cezarykluczynski.stapi.etl.template.comic_series.dto;

import com.cezarykluczynski.stapi.etl.template.publishable_series.dto.PublishableSeriesTemplate;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ComicSeriesTemplate extends PublishableSeriesTemplate {

	private Integer numberOfIssues;

	private Boolean photonovelSeries;

}
