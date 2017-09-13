package com.cezarykluczynski.stapi.etl.template.character.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemWithTemplatePartEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.character.dto.CharacterTemplate;
import com.cezarykluczynski.stapi.model.page.entity.enums.MediaWikiSource;
import com.cezarykluczynski.stapi.model.performer.entity.Performer;
import com.cezarykluczynski.stapi.model.performer.repository.PerformerRepository;
import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi;
import com.cezarykluczynski.stapi.sources.mediawiki.api.dto.PageLink;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class CharacterTemplateActorLinkingEnrichingProcessor implements ItemWithTemplatePartEnrichingProcessor<CharacterTemplate> {

	private final WikitextApi wikitextApi;

	private final PerformerRepository performerRepository;

	@Inject
	public CharacterTemplateActorLinkingEnrichingProcessor(WikitextApi wikitextApi, PerformerRepository performerRepository) {
		this.wikitextApi = wikitextApi;
		this.performerRepository = performerRepository;
	}

	@Override
	// TODO: no need to pass part, value will be enough
	public void enrich(EnrichablePair<Template.Part, CharacterTemplate> enrichablePair) throws Exception {
		Template.Part actorTemplatePart = enrichablePair.getInput();
		CharacterTemplate characterTemplate = enrichablePair.getOutput();

		if (actorTemplatePart == null || characterTemplate == null) {
			return;
		}

		List<PageLink> pageLinkList = wikitextApi.getPageLinksFromWikitext(actorTemplatePart.getValue());
		Set<Performer> performerSet = Sets.newHashSet();

		for (PageLink pageLink : pageLinkList) {
			String title = pageLink.getTitle();

			if (title.toLowerCase().startsWith("unknown")) {
				continue;
			}

			Optional<Performer> performerOptional;
			try {
				performerOptional = performerRepository.findByPageTitleAndPageMediaWikiSource(title, MediaWikiSource.MEMORY_ALPHA_EN);
				performerOptional.ifPresent(performerSet::add);

				if (!performerOptional.isPresent()) {
					log.warn("Could not find performer {} playing {} in local database",
							pageLink.getTitle(), characterTemplate.getName());
				}
			} catch (Throwable e) {
				log.warn("Ooops", e);
			}
		}

		characterTemplate.getPerformers().addAll(performerSet);
	}
}
