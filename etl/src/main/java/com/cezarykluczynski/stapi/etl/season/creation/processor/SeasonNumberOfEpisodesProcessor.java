package com.cezarykluczynski.stapi.etl.season.creation.processor;

import com.cezarykluczynski.stapi.etl.mediawiki.api.PageApi;
import com.cezarykluczynski.stapi.etl.mediawiki.api.enums.MediaWikiSource;
import com.cezarykluczynski.stapi.etl.mediawiki.dto.Page;
import com.cezarykluczynski.stapi.etl.mediawiki.dto.Template;
import com.cezarykluczynski.stapi.etl.template.service.TemplateFinder;
import com.cezarykluczynski.stapi.util.constant.TemplateTitle;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SeasonNumberOfEpisodesProcessor implements ItemProcessor<Page, Integer> {

	private final PageApi pageApi;
	private final TemplateFinder templateFinder;

	@Override
	public Integer process(Page item) throws Exception {
		final String pageTitle = item.getTitle();
		final Page templatePage = pageApi.getTemplate(pageTitle, MediaWikiSource.MEMORY_ALPHA_EN);
		if (templatePage == null) {
			log.info("Could not get template {}, returning null number of episodes.", pageTitle);
			return null;
		}
		final List<Template> episodesTemplates = templateFinder.findTemplates(templatePage, TemplateTitle.ROW);
		int numberOfEpisodes = 0;
		for (Template episodeTemplate : episodesTemplates) {
			if (TemplateTitle.ROW.equalsIgnoreCase(episodeTemplate.getTitle())) {
				numberOfEpisodes++;
			}
		}

		if (numberOfEpisodes > 0) {
			return numberOfEpisodes;
		}

		log.info("Returning null for episode number for page {} (most likely season has not yet started).", pageTitle);
		return null;
	}

}
