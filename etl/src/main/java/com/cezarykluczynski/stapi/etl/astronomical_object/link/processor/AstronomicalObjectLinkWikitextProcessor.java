package com.cezarykluczynski.stapi.etl.astronomical_object.link.processor;

import com.cezarykluczynski.stapi.etl.common.service.ParagraphExtractor;
import com.cezarykluczynski.stapi.etl.mediawiki.api.WikitextApi;
import com.cezarykluczynski.stapi.etl.mediawiki.api.dto.PageLink;
import com.cezarykluczynski.stapi.model.astronomical_object.entity.AstronomicalObject;
import com.cezarykluczynski.stapi.model.astronomical_object.repository.AstronomicalObjectRepository;
import com.cezarykluczynski.stapi.model.page.entity.enums.MediaWikiSource;
import com.google.common.collect.Lists;
import jakarta.persistence.NonUniqueResultException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class AstronomicalObjectLinkWikitextProcessor implements ItemProcessor<String, List<AstronomicalObject>> {

	private final ParagraphExtractor paragraphExtractor;

	private final WikitextApi wikitextApi;

	private final AstronomicalObjectRepository astronomicalObjectRepository;

	public AstronomicalObjectLinkWikitextProcessor(ParagraphExtractor paragraphExtractor, WikitextApi wikitextApi,
			AstronomicalObjectRepository astronomicalObjectRepository) {
		this.wikitextApi = wikitextApi;
		this.paragraphExtractor = paragraphExtractor;
		this.astronomicalObjectRepository = astronomicalObjectRepository;
	}

	@Override
	@NonNull
	public List<AstronomicalObject> process(String item) throws Exception {
		List<String> paragraphList = paragraphExtractor.extractParagraphs(item);
		List<AstronomicalObject> candidates = Lists.newArrayList();

		if (CollectionUtils.isEmpty(paragraphList)) {
			return candidates;
		}

		String wikitextWithoutTemplates = wikitextApi.getWikitextWithoutTemplates(paragraphList.get(0));
		List<PageLink> pageLinkList = wikitextApi.getPageLinksFromWikitext(wikitextWithoutTemplates);

		for (PageLink pageLink : pageLinkList) {
			Optional<AstronomicalObject> astronomicalObjectOptional;
			try {
				astronomicalObjectOptional = astronomicalObjectRepository
						.findByPageTitleWithPageMediaWikiSource(pageLink.getTitle(), MediaWikiSource.MEMORY_ALPHA_EN);
			} catch (NonUniqueResultException e) {
				log.info("Could not find unique page by title \"{}\".", pageLink.getTitle());
				continue;
			}

			if (astronomicalObjectOptional.isPresent()) {
				AstronomicalObject astronomicalObject = astronomicalObjectOptional.get();
				if (isFalsePositive(item, pageLink)) {
					continue;
				}

				candidates.add(astronomicalObject);
			}
		}

		return candidates;
	}

	private boolean isFalsePositive(String item, PageLink pageLink) {
		String wikitextBefore = item.substring(Math.max(0, pageLink.getStartPosition() - 40), pageLink.getStartPosition());
		return (wikitextBefore.contains("light year") || wikitextBefore.contains("visible from")) && !wikitextBefore.contains("<br");
	}

}
