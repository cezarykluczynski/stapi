package com.cezarykluczynski.stapi.etl.comics.creation.processor;

import com.cezarykluczynski.stapi.etl.template.comics.dto.ComicsTemplate;
import com.cezarykluczynski.stapi.model.comics.entity.Comics;
import com.cezarykluczynski.stapi.model.common.service.UidGenerator;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

@Service
public class ComicsTemplateProcessor implements ItemProcessor<ComicsTemplate, Comics> {

	private final UidGenerator uidGenerator;

	public ComicsTemplateProcessor(UidGenerator uidGenerator) {
		this.uidGenerator = uidGenerator;
	}

	@Override
	public Comics process(ComicsTemplate item) throws Exception {
		Comics comics = new Comics();

		comics.setTitle(item.getTitle());
		comics.setPage(item.getPage());
		comics.setUid(uidGenerator.generateFromPage(item.getPage(), Comics.class));
		comics.setPublishedYear(item.getPublishedYear());
		comics.setPublishedMonth(item.getPublishedMonth());
		comics.setPublishedDay(item.getPublishedDay());
		comics.setCoverYear(item.getCoverYear());
		comics.setCoverMonth(item.getCoverMonth());
		comics.setCoverDay(item.getCoverDay());
		comics.setNumberOfPages(item.getNumberOfPages());
		comics.setStardateFrom(item.getStardateFrom());
		comics.setStardateTo(item.getStardateTo());
		comics.setYearFrom(item.getYearFrom());
		comics.setYearTo(item.getYearTo());
		comics.setPhotonovel(item.isPhotonovel());
		comics.setAdaptation(item.isAdaptation());
		comics.getComicSeries().addAll(item.getComicSeries());
		comics.getWriters().addAll(item.getWriters());
		comics.getArtists().addAll(item.getArtists());
		comics.getEditors().addAll(item.getEditors());
		comics.getStaff().addAll(item.getStaff());
		comics.getPublishers().addAll(item.getPublishers());
		comics.getCharacters().addAll(item.getCharacters());
		comics.getReferences().addAll(item.getReferences());

		return comics;

	}

}
