package com.cezarykluczynski.stapi.etl.series.creation.processor;

import com.cezarykluczynski.stapi.etl.template.series.dto.SeriesTemplate;
import com.cezarykluczynski.stapi.model.series.entity.Series;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

@Service
public class SeriesTemplateProcessor implements ItemProcessor<SeriesTemplate, Series> {

	@Override
	public Series process(SeriesTemplate item) throws Exception {
		Series series = new Series();

		series.setTitle(item.getTitle());
		series.setPage(item.getPage());
		series.setAbbreviation(item.getAbbreviation());
		series.setProductionStartYear(item.getProductionYearRange().getStartYear());
		series.setProductionEndYear(item.getProductionYearRange().getEndYear());
		series.setOriginalRunStartDate(item.getOriginalRunDateRange().getStartDate());
		series.setOriginalRunEndDate(item.getOriginalRunDateRange().getEndDate());

		return series;
	}

}
