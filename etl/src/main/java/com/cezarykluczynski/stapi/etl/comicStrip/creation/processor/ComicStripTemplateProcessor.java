package com.cezarykluczynski.stapi.etl.comicStrip.creation.processor;

import com.cezarykluczynski.stapi.etl.template.comicStrip.dto.ComicStripTemplate;
import com.cezarykluczynski.stapi.model.comicStrip.entity.ComicStrip;
import com.cezarykluczynski.stapi.model.common.service.GuidGenerator;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class ComicStripTemplateProcessor implements ItemProcessor<ComicStripTemplate, ComicStrip> {

	private GuidGenerator guidGenerator;

	@Inject
	public ComicStripTemplateProcessor(GuidGenerator guidGenerator) {
		this.guidGenerator = guidGenerator;
	}

	@Override
	public ComicStrip process(ComicStripTemplate item) throws Exception {
		ComicStrip comicStrip = new ComicStrip();

		comicStrip.setGuid(guidGenerator.generateFromPage(item.getPage(), ComicStrip.class));
		comicStrip.setPage(item.getPage());
		comicStrip.setTitle(item.getTitle());
		comicStrip.setPeriodical(item.getPeriodical());
		comicStrip.setPublishedYearFrom(item.getPublishedYearFrom());
		comicStrip.setPublishedMonthFrom(item.getPublishedMonthFrom());
		comicStrip.setPublishedDayFrom(item.getPublishedDayFrom());
		comicStrip.setPublishedYearTo(item.getPublishedYearTo());
		comicStrip.setPublishedMonthTo(item.getPublishedMonthTo());
		comicStrip.setPublishedDayTo(item.getPublishedDayTo());
		comicStrip.setNumberOfPages(item.getNumberOfPages());
		comicStrip.setYearFrom(item.getYearFrom());
		comicStrip.setYearTo(item.getYearTo());
		comicStrip.getComicSeries().addAll(item.getComicSeries());
		comicStrip.getWriters().addAll(item.getWriters());
		comicStrip.getArtists().addAll(item.getArtists());
		comicStrip.getCharacters().addAll(item.getCharacters());

		return comicStrip;
	}
}
