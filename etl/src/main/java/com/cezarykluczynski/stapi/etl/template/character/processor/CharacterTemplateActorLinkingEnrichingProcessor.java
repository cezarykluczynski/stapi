package com.cezarykluczynski.stapi.etl.template.character.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.mediawiki.api.WikitextApi;
import com.cezarykluczynski.stapi.etl.mediawiki.api.dto.PageLink;
import com.cezarykluczynski.stapi.etl.template.character.dto.CharacterTemplate;
import com.cezarykluczynski.stapi.model.page.entity.enums.MediaWikiSource;
import com.cezarykluczynski.stapi.model.performer.entity.Performer;
import com.cezarykluczynski.stapi.model.performer.repository.PerformerRepository;
import com.google.common.collect.Sets;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class CharacterTemplateActorLinkingEnrichingProcessor implements ItemEnrichingProcessor<EnrichablePair<String, CharacterTemplate>> {

	private final WikitextApi wikitextApi;

	private final PerformerRepository performerRepository;

	@Override
	public void enrich(EnrichablePair<String, CharacterTemplate> enrichablePair) throws Exception {
		String value = enrichablePair.getInput();
		CharacterTemplate characterTemplate = enrichablePair.getOutput();

		if (value == null || characterTemplate == null) {
			return;
		}

		List<PageLink> pageLinkList = wikitextApi.getPageLinksFromWikitext(value);
		Set<Performer> performerSet = Sets.newHashSet();

		for (PageLink pageLink : pageLinkList) {
			String title = pageLink.getTitle();

			if (title.toLowerCase().startsWith("unknown")) {
				continue;
			}

			Optional<Performer> performerOptional;
			try {
				performerOptional = performerRepository.findByPageTitleWithPageMediaWikiSource(title, MediaWikiSource.MEMORY_ALPHA_EN);
				performerOptional.ifPresent(performerSet::add);
				if (performerOptional.isEmpty()) {
					log.info("Could not find performer \"{}\" playing \"{}\" in local database.", pageLink.getTitle(), characterTemplate.getName());
				}
			} catch (Throwable e) {
				log.error("Ooops", e);
				throw e;
			}
		}

		characterTemplate.getPerformers().addAll(performerSet);
	}
}
