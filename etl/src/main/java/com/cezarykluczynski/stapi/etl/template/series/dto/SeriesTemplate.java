package com.cezarykluczynski.stapi.etl.template.series.dto;

import com.cezarykluczynski.stapi.etl.template.common.dto.DateRange;
import com.cezarykluczynski.stapi.etl.template.common.dto.YearRange;
import lombok.Data;

@Data
public class SeriesTemplate {

	private String title;

	private String abbreviation;

	private YearRange productionYearRange;

	private DateRange originalRunDateRange;
}
