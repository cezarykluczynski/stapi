package com.cezarykluczynski.stapi.etl.template.book_series.dto;

import com.cezarykluczynski.stapi.etl.template.publishable_series.dto.PublishableSeriesTemplate;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class BookSeriesTemplate extends PublishableSeriesTemplate {

	private Integer numberOfBooks;

	@SuppressWarnings("MemberName")
	private Boolean eBookSeries;

}
