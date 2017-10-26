package com.cezarykluczynski.stapi.etl.template.publishable_series.processor;

import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PublishableSeriesTemplateMiniseriesProcessor implements ItemProcessor<String, Boolean> {

	private final WikitextApi wikitextApi;

	public PublishableSeriesTemplateMiniseriesProcessor(WikitextApi wikitextApi) {
		this.wikitextApi = wikitextApi;
	}

	@Override
	public Boolean process(String item) throws Exception {
		List<String> linkList = wikitextApi.getPageTitlesFromWikitext(item);
		return !linkList.isEmpty();
	}

}
