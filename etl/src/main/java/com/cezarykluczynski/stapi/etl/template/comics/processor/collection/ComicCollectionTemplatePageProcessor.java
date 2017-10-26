package com.cezarykluczynski.stapi.etl.template.comics.processor.collection;

import com.cezarykluczynski.stapi.etl.template.comics.dto.ComicCollectionTemplate;
import com.cezarykluczynski.stapi.etl.template.comics.dto.ComicsTemplate;
import com.cezarykluczynski.stapi.etl.template.comics.processor.ComicsTemplatePageProcessor;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

@Service
public class ComicCollectionTemplatePageProcessor implements ItemProcessor<Page, ComicCollectionTemplate> {

	private final ComicsTemplatePageProcessor comicsTemplatePageProcessor;

	private final ComicsTemplateToComicCollectionTemplateProcessor comicsTemplateToComicCollectionTemplateProcessor;

	private final ComicCollectionTemplateWikitextComicsProcessor comicCollectionTemplateWikitextComicsProcessor;

	public ComicCollectionTemplatePageProcessor(ComicsTemplatePageProcessor comicsTemplatePageProcessor,
			ComicsTemplateToComicCollectionTemplateProcessor comicsTemplateToComicCollectionTemplateProcessor,
			ComicCollectionTemplateWikitextComicsProcessor comicCollectionTemplateWikitextComicsProcessor) {
		this.comicsTemplatePageProcessor = comicsTemplatePageProcessor;
		this.comicsTemplateToComicCollectionTemplateProcessor = comicsTemplateToComicCollectionTemplateProcessor;
		this.comicCollectionTemplateWikitextComicsProcessor = comicCollectionTemplateWikitextComicsProcessor;
	}

	@Override
	public ComicCollectionTemplate process(Page item) throws Exception {
		ComicsTemplate comicsTemplate = comicsTemplatePageProcessor.process(item);

		if (comicsTemplate == null) {
			return null;
		}

		ComicCollectionTemplate comicCollectionTemplate = comicsTemplateToComicCollectionTemplateProcessor.process(comicsTemplate);
		comicCollectionTemplate.getComics().addAll(comicCollectionTemplateWikitextComicsProcessor.process(item));
		comicCollectionTemplate.getComics().forEach(comics -> comicCollectionTemplate.getCharacters().addAll(comics.getCharacters()));

		return comicCollectionTemplate;
	}

}
