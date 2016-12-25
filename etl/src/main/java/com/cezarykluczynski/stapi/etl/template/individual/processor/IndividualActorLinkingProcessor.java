package com.cezarykluczynski.stapi.etl.template.individual.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.ItemEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.individual.dto.IndividualTemplate;
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
public class IndividualActorLinkingProcessor implements
		ItemEnrichingProcessor<EnrichablePair<Template.Part, IndividualTemplate>> {

	private WikitextApi wikitextApi;

	private PerformerRepository performerRepository;

	@Inject
	public IndividualActorLinkingProcessor(WikitextApi wikitextApi, PerformerRepository performerRepository) {
		this.wikitextApi = wikitextApi;
		this.performerRepository = performerRepository;
	}

	@Override
	public void enrich(EnrichablePair<Template.Part, IndividualTemplate> enrichablePair) {
		Template.Part actorTemplatePart = enrichablePair.getInput();
		IndividualTemplate individualTemplate = enrichablePair.getOutput();

		if (actorTemplatePart == null || individualTemplate == null) {
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
				performerOptional = performerRepository.findByPageTitle(title);
				performerOptional.ifPresent(performerSet::add);

				if (!performerOptional.isPresent()) {
					log.warn("Could not find performer {} playing {} in local database",
							pageLink.getTitle(), individualTemplate.getName());
				}
			} catch (Throwable e) {
				log.warn("Ooops {}", e);
			}
		}

		individualTemplate.getPerformers().addAll(performerSet);
	}
}
