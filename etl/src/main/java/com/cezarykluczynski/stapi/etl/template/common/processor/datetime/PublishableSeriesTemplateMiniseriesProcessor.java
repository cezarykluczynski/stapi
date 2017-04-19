package com.cezarykluczynski.stapi.etl.template.common.processor.datetime;

import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class PublishableSeriesTemplateMiniseriesProcessor implements ItemProcessor<String, Boolean> {

	private WikitextApi wikitextApi;

	@Inject
	public PublishableSeriesTemplateMiniseriesProcessor(WikitextApi wikitextApi) {
		this.wikitextApi = wikitextApi;
	}

	@Override
	public Boolean process(String item) throws Exception {
		List<String> linkList = wikitextApi.getPageTitlesFromWikitext(item);
		return !linkList.isEmpty();
	}

}
