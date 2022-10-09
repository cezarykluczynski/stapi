package com.cezarykluczynski.stapi.etl.season.creation.processor;

import com.cezarykluczynski.stapi.etl.template.service.TemplateFinder;
import com.cezarykluczynski.stapi.sources.mediawiki.api.PageApi;
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
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

	private static final String DIS_TEMPLATE_EPISODE_PART_VALUE = "episode";

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
		final List<Template> episodesTemplates = templateFinder.findTemplates(templatePage, TemplateTitle.DIS, TemplateTitle.E);
		int numberOfEpisodes = 0;
		for (Template episodeTemplate : episodesTemplates) {
			final boolean hasEpisodePart = episodeTemplate.getParts().stream()
					.anyMatch(part -> DIS_TEMPLATE_EPISODE_PART_VALUE.equals(part.getValue()));
			if (TemplateTitle.E.equals(episodeTemplate.getTitle()) || hasEpisodePart) {
				numberOfEpisodes++;
			} else {
				log.warn("Found dis template that is not a episode disambiguation for page {}: {}.", pageTitle, episodeTemplate);
			}
		}

		if (numberOfEpisodes > 0) {
			return numberOfEpisodes;
		}

		log.info("Returning null for episode number for page {} (most likely season has not yet started).", pageTitle);
		return null;
	}

}
