package com.cezarykluczynski.stapi.etl.template.common.dto.datetime;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "of")
public class PublicationDates {

	private DayMonthYear publicationDate;

	private DayMonthYear coverDate;

}
