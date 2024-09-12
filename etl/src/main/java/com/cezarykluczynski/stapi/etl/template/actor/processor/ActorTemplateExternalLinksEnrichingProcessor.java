package com.cezarykluczynski.stapi.etl.template.actor.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.mediawiki.api.WikitextApi;
import com.cezarykluczynski.stapi.etl.mediawiki.api.dto.PageSection;
import com.cezarykluczynski.stapi.etl.mediawiki.dto.Page;
import com.cezarykluczynski.stapi.etl.template.actor.dto.ActorTemplate;
import com.cezarykluczynski.stapi.etl.template.common.service.ExternalLinkFilter;
import com.cezarykluczynski.stapi.model.external_link.entity.ExternalLink;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ActorTemplateExternalLinksEnrichingProcessor implements ItemEnrichingProcessor<EnrichablePair<Page, ActorTemplate>> {

	private static final List<String> SECTION_ANCHORS = List.of("External_links", "External_Links", "External_links;",
			"External_links:", "External_Links:");

	private final WikitextApi wikitextApi;
	private final ExternalLinkFilter externalLinkFilter;

	@Override
	public void enrich(EnrichablePair<Page, ActorTemplate> enrichablePair) throws Exception {
		Page page = enrichablePair.getInput();
		String pageTitle = page.getTitle();

		List<PageSection> matchingPageSection = page.getSections()
				.stream()
				.filter(pageSection -> {
					String anchor = pageSection.getAnchor();
					boolean anchorMatches = SECTION_ANCHORS.contains(anchor);
					if (!anchorMatches && anchor != null
							&& StringUtils.containsIgnoreCase(anchor, "external")
							&& StringUtils.containsIgnoreCase(anchor, "links")) {
						log.info("Section {} of page {} is a candidate for external links, but not an exact match.", anchor, pageTitle);
					}
					return anchorMatches;
				})
				.toList();

		if (matchingPageSection.isEmpty()) {
			return;
		}

		if (matchingPageSection.size() > 1) {
			log.warn("More than one external links section found for page {}", pageTitle);
		}

		List<ExternalLink> externalLinksWithVariants = Lists.newArrayList();
		for (PageSection pageSection : matchingPageSection) {
			externalLinksWithVariants.addAll(wikitextApi.getExternalLinksFromWikitext(pageSection.getWikitext(), page));
		}
		List<ExternalLink> externalLinks = externalLinksWithVariants.stream()
				.filter(externalLinkFilter::isPersonLink)
				.toList();
		ActorTemplate actorTemplate = enrichablePair.getOutput();
		actorTemplate.getExternalLinks().addAll(externalLinks);
	}

}
