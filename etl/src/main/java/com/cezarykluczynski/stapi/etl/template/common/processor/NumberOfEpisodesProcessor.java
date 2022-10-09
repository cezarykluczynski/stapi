package com.cezarykluczynski.stapi.etl.template.common.processor;

import com.cezarykluczynski.stapi.sources.mediawiki.api.PageApi;
import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi;
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import com.google.common.primitives.Ints;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Collections;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class NumberOfEpisodesProcessor implements ItemProcessor<Template.Part, Integer> {

	private final PageApi pageApi;
	private final WikitextApi wikitextApi;

	@Override
	public Integer process(Template.Part item) throws Exception {
		final List<Template> templates = item.getTemplates();
		if (Collections.isNullOrEmpty(templates)) {
			log.warn("No templates found for item {}, returning null number of episodes.", item);
			return null;
		}

		final Page page = pageApi.getTemplate(item.getTemplates().get(0).getOriginalTitle(), MediaWikiSource.MEMORY_ALPHA_EN);
		final String wikitextWithoutNoInclude = wikitextApi.getWikitextWithoutNoInclude(page.getWikitext());
		Integer numberOfEpisodes = Ints.tryParse(wikitextWithoutNoInclude);
		if (numberOfEpisodes != null) {
			return numberOfEpisodes;
		} else {
			log.warn("Could not get number of episodes from template part {}.", item);
			return null;
		}
	}

}
