package com.cezarykluczynski.stapi.etl.comicCollection.creation.processor;

import com.cezarykluczynski.stapi.etl.template.comics.dto.ComicCollectionTemplate;
import com.cezarykluczynski.stapi.model.comicCollection.entity.ComicCollection;
import com.cezarykluczynski.stapi.model.common.service.GuidGenerator;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class ComicCollectionTemplateProcessor implements ItemProcessor<ComicCollectionTemplate, ComicCollection> {

	private GuidGenerator guidGenerator;

	@Inject
	public ComicCollectionTemplateProcessor(GuidGenerator guidGenerator) {
		this.guidGenerator = guidGenerator;
	}

	@Override
	public ComicCollection process(ComicCollectionTemplate item) throws Exception {
		ComicCollection comicCollection = new ComicCollection();

		comicCollection.setTitle(item.getTitle());
		comicCollection.setPage(item.getPage());
		comicCollection.setGuid(guidGenerator.generateFromPage(item.getPage(), ComicCollection.class));
		comicCollection.setPublishedYear(item.getPublishedYear());
		comicCollection.setPublishedMonth(item.getPublishedMonth());
		comicCollection.setPublishedDay(item.getPublishedDay());
		comicCollection.setCoverYear(item.getCoverYear());
		comicCollection.setCoverMonth(item.getCoverMonth());
		comicCollection.setCoverDay(item.getCoverDay());
		comicCollection.setNumberOfPages(item.getNumberOfPages());
		comicCollection.setStardateFrom(item.getStardateFrom());
		comicCollection.setStardateTo(item.getStardateTo());
		comicCollection.setYearFrom(item.getYearFrom());
		comicCollection.setYearTo(item.getYearTo());
		comicCollection.setPhotonovel(item.isPhotonovel());
		comicCollection.getComicSeries().addAll(item.getComicSeries());
		comicCollection.getWriters().addAll(item.getWriters());
		comicCollection.getArtists().addAll(item.getArtists());
		comicCollection.getEditors().addAll(item.getEditors());
		comicCollection.getStaff().addAll(item.getStaff());
		comicCollection.getPublishers().addAll(item.getPublishers());
		comicCollection.getCharacters().addAll(item.getCharacters());
		comicCollection.getReferences().addAll(item.getReferences());
		comicCollection.getComics().addAll(item.getComics());

		return comicCollection;
	}
}
