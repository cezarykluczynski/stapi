package com.cezarykluczynski.stapi.etl.magazine_series.creation.processor;

import com.cezarykluczynski.stapi.etl.template.magazine_series.dto.MagazineSeriesTemplate;
import com.cezarykluczynski.stapi.model.common.service.UidGenerator;
import com.cezarykluczynski.stapi.model.magazine_series.entity.MagazineSeries;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

@Service
public class MagazineSeriesTemplateProcessor implements ItemProcessor<MagazineSeriesTemplate, MagazineSeries> {

	private final UidGenerator uidGenerator;

	public MagazineSeriesTemplateProcessor(UidGenerator uidGenerator) {
		this.uidGenerator = uidGenerator;
	}

	@Override
	public MagazineSeries process(MagazineSeriesTemplate item) throws Exception {
		MagazineSeries magazineSeries = new MagazineSeries();

		magazineSeries.setUid(uidGenerator.generateFromPage(item.getPage(), MagazineSeries.class));
		magazineSeries.setPage(item.getPage());
		magazineSeries.setTitle(item.getTitle());
		magazineSeries.setPublishedYearFrom(item.getPublishedYearFrom());
		magazineSeries.setPublishedMonthFrom(item.getPublishedMonthFrom());
		magazineSeries.setPublishedYearTo(item.getPublishedYearTo());
		magazineSeries.setPublishedMonthTo(item.getPublishedMonthTo());
		magazineSeries.setNumberOfIssues(item.getNumberOfIssues());
		magazineSeries.getPublishers().addAll(item.getPublishers());
		magazineSeries.getEditors().addAll(item.getEditors());

		return magazineSeries;
	}


}
