package com.cezarykluczynski.stapi.etl.series.creation.processor;

import com.cezarykluczynski.stapi.etl.template.series.dto.SeriesTemplate;
import com.cezarykluczynski.stapi.model.series.entity.Series;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

@Service
public class SeriesTemplateProcessor implements ItemProcessor<SeriesTemplate, Series> {

	@Override
	public Series process(SeriesTemplate item) throws Exception {
		return Series.builder()
				.title(item.getTitle())
				.abbreviation(item.getAbbreviation())
				.productionStartYear(item.getProductionYearRange().getStartYear())
				.productionEndYear(item.getProductionYearRange().getEndYear())
				.originalRunStartDate(item.getOriginalRunDateRange().getStartDate())
				.originalRunEndDate(item.getOriginalRunDateRange().getEndDate())
				.build();
	}

}
