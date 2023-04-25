package com.cezarykluczynski.stapi.etl.template.comics.processor.collection;

import com.cezarykluczynski.stapi.etl.comic_collection.creation.service.ComicCollectionPageFilter;
import com.cezarykluczynski.stapi.etl.mediawiki.dto.Page;
import com.cezarykluczynski.stapi.etl.template.comics.dto.ComicCollectionContents;
import com.cezarykluczynski.stapi.etl.template.comics.dto.ComicCollectionTemplate;
import com.cezarykluczynski.stapi.etl.template.comics.dto.ComicsTemplate;
import com.cezarykluczynski.stapi.etl.template.comics.processor.ComicsTemplatePageProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ComicCollectionTemplatePageProcessor implements ItemProcessor<Page, ComicCollectionTemplate> {

	private final ComicCollectionPageFilter comicCollectionPageFilter;

	private final ComicsTemplatePageProcessor comicsTemplatePageProcessor;

	private final ComicsTemplateToComicCollectionTemplateProcessor comicsTemplateToComicCollectionTemplateProcessor;

	private final ComicCollectionTemplateWikitextComicsProcessor comicCollectionTemplateWikitextComicsProcessor;

	@Override
	public ComicCollectionTemplate process(Page item) throws Exception {
		if (comicCollectionPageFilter.shouldBeFilteredOut(item)) {
			return null;
		}

		ComicsTemplate comicsTemplate = comicsTemplatePageProcessor.process(item);

		if (comicsTemplate == null) {
			return null;
		}

		ComicCollectionTemplate comicCollectionTemplate = comicsTemplateToComicCollectionTemplateProcessor.process(comicsTemplate);
		ComicCollectionContents comicCollectionContents = comicCollectionTemplateWikitextComicsProcessor.process(item);
		comicCollectionTemplate.getComics().addAll(comicCollectionContents.getComics());
		comicCollectionTemplate.getChildComicSeries().addAll(comicCollectionContents.getComicSeries());
		comicCollectionTemplate.getComics().forEach(comics -> comicCollectionTemplate.getCharacters().addAll(comics.getCharacters()));

		return comicCollectionTemplate;
	}

}
