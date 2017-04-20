package com.cezarykluczynski.stapi.etl.template.bookSeries.dto;

import com.cezarykluczynski.stapi.etl.template.publishableSeries.dto.PublishableSeriesTemplate;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class BookSeriesTemplate extends PublishableSeriesTemplate {

	private Integer numberOfBooks;

	@SuppressWarnings("MemberName")
	private Boolean eBookSeries;

}
