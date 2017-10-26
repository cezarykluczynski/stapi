package com.cezarykluczynski.stapi.etl.astronomical_object.link.processor;

import com.cezarykluczynski.stapi.etl.common.service.ParagraphExtractor;
import com.cezarykluczynski.stapi.model.astronomical_object.entity.AstronomicalObject;
import com.cezarykluczynski.stapi.model.astronomical_object.repository.AstronomicalObjectRepository;
import com.cezarykluczynski.stapi.model.page.entity.enums.MediaWikiSource;
import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi;
import com.cezarykluczynski.stapi.sources.mediawiki.api.dto.PageLink;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import javax.persistence.NonUniqueResultException;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class AstronomicalObjectLinkWikitextProcessor implements ItemProcessor<String, AstronomicalObject> {

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
	public AstronomicalObject process(String item) throws Exception {
		List<String> parahraphList = paragraphExtractor.extractParagraphs(item);

		if (CollectionUtils.isEmpty(parahraphList)) {
			return null;
		}

		String wikitextWithourTemplates = wikitextApi.getWikitextWithoutTemplates(parahraphList.get(0));
		List<PageLink> pageLinkList = wikitextApi.getPageLinksFromWikitext(wikitextWithourTemplates);

		for (PageLink pageLink : pageLinkList) {
			Optional<AstronomicalObject> astronomicalObjectOptional;
			try {
				astronomicalObjectOptional = astronomicalObjectRepository
						.findByPageTitleAndPageMediaWikiSource(pageLink.getTitle(), MediaWikiSource.MEMORY_ALPHA_EN);
			} catch (NonUniqueResultException e) {
				log.info("Could not find unique page by title {}", pageLink.getTitle());
				continue;
			}

			if (astronomicalObjectOptional.isPresent()) {
				AstronomicalObject astronomicalObject = astronomicalObjectOptional.get();
				if (isFalsePositive(item, pageLink)) {
					continue;
				}

				return astronomicalObject;
			}
		}

		return null;
	}

	private boolean isFalsePositive(String item, PageLink pageLink) {
		String wikitextBefore = item.substring(0, pageLink.getStartPosition());
		return wikitextBefore.contains("light year") || wikitextBefore.contains("visible from");
	}

}
