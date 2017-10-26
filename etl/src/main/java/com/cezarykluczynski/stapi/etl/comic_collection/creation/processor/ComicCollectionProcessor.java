package com.cezarykluczynski.stapi.etl.comic_collection.creation.processor;

import com.cezarykluczynski.stapi.etl.page.common.processor.PageHeaderProcessor;
import com.cezarykluczynski.stapi.etl.template.comics.processor.collection.ComicCollectionTemplatePageProcessor;
import com.cezarykluczynski.stapi.model.comic_collection.entity.ComicCollection;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader;
import com.google.common.collect.Lists;
import org.springframework.batch.item.support.CompositeItemProcessor;
import org.springframework.stereotype.Service;

@Service
public class ComicCollectionProcessor extends CompositeItemProcessor<PageHeader, ComicCollection> {

	public ComicCollectionProcessor(PageHeaderProcessor pageHeaderProcessor,
			ComicCollectionTemplatePageProcessor comicCollectionTemplatePageProcessor,
			ComicCollectionTemplateProcessor comicCollectionTemplateProcessor) {
		setDelegates(Lists.newArrayList(pageHeaderProcessor, comicCollectionTemplatePageProcessor,
				comicCollectionTemplateProcessor));
	}

}
