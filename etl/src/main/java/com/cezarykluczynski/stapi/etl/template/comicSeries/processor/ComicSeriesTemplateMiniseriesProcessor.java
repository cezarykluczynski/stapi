package com.cezarykluczynski.stapi.etl.template.comicSeries.processor;

import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class ComicSeriesTemplateMiniseriesProcessor implements ItemProcessor<String, Boolean> {

	private WikitextApi wikitextApi;

	@Inject
	public ComicSeriesTemplateMiniseriesProcessor(WikitextApi wikitextApi) {
		this.wikitextApi = wikitextApi;
	}

	@Override
	public Boolean process(String item) throws Exception {
		List<String> linkList = wikitextApi.getPageTitlesFromWikitext(item);
		return !linkList.isEmpty();
	}

}
