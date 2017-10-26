package com.cezarykluczynski.stapi.etl.comic_series.creation.processor;

import com.cezarykluczynski.stapi.etl.template.comic_series.dto.ComicSeriesTemplate;
import com.cezarykluczynski.stapi.model.comic_series.entity.ComicSeries;
import com.cezarykluczynski.stapi.model.common.service.UidGenerator;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

@Service
public class ComicSeriesTemplateProcessor implements ItemProcessor<ComicSeriesTemplate, ComicSeries> {

	private final UidGenerator uidGenerator;

	public ComicSeriesTemplateProcessor(UidGenerator uidGenerator) {
		this.uidGenerator = uidGenerator;
	}

	@Override
	public ComicSeries process(ComicSeriesTemplate item) throws Exception {
		ComicSeries comicSeries = new ComicSeries();

		comicSeries.setUid(uidGenerator.generateFromPage(item.getPage(), ComicSeries.class));
		comicSeries.setPage(item.getPage());
		comicSeries.setTitle(item.getTitle());
		comicSeries.setPublishedYearFrom(item.getPublishedYearFrom());
		comicSeries.setPublishedMonthFrom(item.getPublishedMonthFrom());
		comicSeries.setPublishedDayFrom(item.getPublishedDayFrom());
		comicSeries.setPublishedYearTo(item.getPublishedYearTo());
		comicSeries.setPublishedMonthTo(item.getPublishedMonthTo());
		comicSeries.setPublishedDayTo(item.getPublishedDayTo());
		comicSeries.setNumberOfIssues(item.getNumberOfIssues());
		comicSeries.setStardateFrom(item.getStardateFrom());
		comicSeries.setStardateTo(item.getStardateTo());
		comicSeries.setYearFrom(item.getYearFrom());
		comicSeries.setYearTo(item.getYearTo());
		comicSeries.setMiniseries(Boolean.TRUE.equals(item.getMiniseries()));
		comicSeries.setPhotonovelSeries(Boolean.TRUE.equals(item.getPhotonovelSeries()));
		comicSeries.getPublishers().addAll(item.getPublishers());

		return comicSeries;

	}

}
