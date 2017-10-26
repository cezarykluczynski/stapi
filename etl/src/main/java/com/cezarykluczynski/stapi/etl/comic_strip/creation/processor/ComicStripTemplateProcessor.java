package com.cezarykluczynski.stapi.etl.comic_strip.creation.processor;

import com.cezarykluczynski.stapi.etl.template.comic_strip.dto.ComicStripTemplate;
import com.cezarykluczynski.stapi.model.comic_strip.entity.ComicStrip;
import com.cezarykluczynski.stapi.model.common.service.UidGenerator;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

@Service
public class ComicStripTemplateProcessor implements ItemProcessor<ComicStripTemplate, ComicStrip> {

	private final UidGenerator uidGenerator;

	public ComicStripTemplateProcessor(UidGenerator uidGenerator) {
		this.uidGenerator = uidGenerator;
	}

	@Override
	public ComicStrip process(ComicStripTemplate item) throws Exception {
		ComicStrip comicStrip = new ComicStrip();

		comicStrip.setUid(uidGenerator.generateFromPage(item.getPage(), ComicStrip.class));
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
