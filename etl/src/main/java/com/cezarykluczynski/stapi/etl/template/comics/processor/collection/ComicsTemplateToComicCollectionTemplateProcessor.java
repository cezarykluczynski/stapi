package com.cezarykluczynski.stapi.etl.template.comics.processor.collection;

import com.cezarykluczynski.stapi.etl.template.comics.dto.ComicCollectionTemplate;
import com.cezarykluczynski.stapi.etl.template.comics.dto.ComicsTemplate;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

@Service
public class ComicsTemplateToComicCollectionTemplateProcessor implements ItemProcessor<ComicsTemplate, ComicCollectionTemplate> {

	@Override
	public ComicCollectionTemplate process(ComicsTemplate item) throws Exception {
		ComicCollectionTemplate comicCollectionTemplate = new ComicCollectionTemplate();

		comicCollectionTemplate.setPage(item.getPage());
		comicCollectionTemplate.setTitle(item.getTitle());
		comicCollectionTemplate.setPublishedYear(item.getPublishedYear());
		comicCollectionTemplate.setPublishedMonth(item.getPublishedMonth());
		comicCollectionTemplate.setPublishedDay(item.getPublishedDay());
		comicCollectionTemplate.setCoverYear(item.getCoverYear());
		comicCollectionTemplate.setCoverMonth(item.getCoverMonth());
		comicCollectionTemplate.setCoverDay(item.getCoverDay());
		comicCollectionTemplate.setNumberOfPages(item.getNumberOfPages());
		comicCollectionTemplate.setStardateFrom(item.getStardateFrom());
		comicCollectionTemplate.setStardateTo(item.getStardateTo());
		comicCollectionTemplate.setYearFrom(item.getYearFrom());
		comicCollectionTemplate.setYearTo(item.getYearTo());
		comicCollectionTemplate.setPhotonovel(item.isPhotonovel());
		comicCollectionTemplate.getComicSeries().addAll(item.getComicSeries());
		comicCollectionTemplate.getWriters().addAll(item.getWriters());
		comicCollectionTemplate.getArtists().addAll(item.getArtists());
		comicCollectionTemplate.getEditors().addAll(item.getEditors());
		comicCollectionTemplate.getStaff().addAll(item.getStaff());
		comicCollectionTemplate.getPublishers().addAll(item.getPublishers());
		comicCollectionTemplate.getCharacters().addAll(item.getCharacters());
		comicCollectionTemplate.getReferences().addAll(item.getReferences());

		return comicCollectionTemplate;
	}

}
