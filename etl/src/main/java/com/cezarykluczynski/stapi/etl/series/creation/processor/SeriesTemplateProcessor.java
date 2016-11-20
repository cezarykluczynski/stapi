package com.cezarykluczynski.stapi.etl.series.creation.processor;

import com.cezarykluczynski.stapi.etl.template.series.dto.SeriesTemplate;
import com.cezarykluczynski.stapi.model.common.service.GuidGenerator;
import com.cezarykluczynski.stapi.model.series.entity.Series;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class SeriesTemplateProcessor implements ItemProcessor<SeriesTemplate, Series> {

	private GuidGenerator guidGenerator;

	@Inject
	public SeriesTemplateProcessor(GuidGenerator guidGenerator) {
		this.guidGenerator = guidGenerator;
	}

	@Override
	public Series process(SeriesTemplate item) throws Exception {
		Series series = new Series();

		series.setTitle(item.getTitle());
		series.setPage(item.getPage());
		series.setGuid(guidGenerator.generateFromPage(item.getPage(), Series.class));
		series.setAbbreviation(item.getAbbreviation());
		series.setProductionStartYear(item.getProductionYearRange().getStartYear());
		series.setProductionEndYear(item.getProductionYearRange().getEndYear());
		series.setOriginalRunStartDate(item.getOriginalRunDateRange().getStartDate());
		series.setOriginalRunEndDate(item.getOriginalRunDateRange().getEndDate());

		return series;
	}

}
