package com.cezarykluczynski.stapi.etl.template.comics.processor.collection;

import com.cezarykluczynski.stapi.etl.template.comics.dto.ComicCollectionTemplate;
import com.cezarykluczynski.stapi.etl.template.comics.dto.ComicsTemplate;
import com.cezarykluczynski.stapi.etl.template.comics.processor.ComicsTemplatePageProcessor;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class ComicCollectionTemplatePageProcessor implements ItemProcessor<Page, ComicCollectionTemplate> {

	private ComicsTemplatePageProcessor comicsTemplatePageProcessor;

	private ComicsTemplateToComicCollectionTemplateProcessor comicsTemplateToComicCollectionTemplateProcessor;

	private ComicCollectionTemplateWikitextComicsProcessor comicCollectionTemplateWikitextComicsProcessor;

	@Inject
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
		comicCollectionTemplate.getComics().addAll(comicCollectionTemplateWikitextComicsProcessor.process(item.getWikitext()));

		return comicCollectionTemplate;
	}
}
