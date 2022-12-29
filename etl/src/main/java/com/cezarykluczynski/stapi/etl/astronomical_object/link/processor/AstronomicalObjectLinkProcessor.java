package com.cezarykluczynski.stapi.etl.astronomical_object.link.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.dto.FixedValueHolder;
import com.cezarykluczynski.stapi.etl.common.mapper.MediaWikiSourceMapper;
import com.cezarykluczynski.stapi.etl.common.service.ParagraphExtractor;
import com.cezarykluczynski.stapi.etl.template.service.TemplateFinder;
import com.cezarykluczynski.stapi.model.astronomical_object.entity.AstronomicalObject;
import com.cezarykluczynski.stapi.model.page.entity.Page;
import com.cezarykluczynski.stapi.model.page.entity.enums.MediaWikiSource;
import com.cezarykluczynski.stapi.sources.mediawiki.api.PageApi;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import com.cezarykluczynski.stapi.util.constant.TemplateTitle;
import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AstronomicalObjectLinkProcessor implements ItemProcessor<AstronomicalObject, AstronomicalObject> {

	private static final String LOCATION = "location";

	private final PageApi pageApi;

	private final MediaWikiSourceMapper mediaWikiSourceMapper;

	private final TemplateFinder templateFinder;

	private final ParagraphExtractor paragraphExtractor;

	private final AstronomicalObjectLinkWikitextProcessor astronomicalObjectLinkWikitextProcessor;

	private final AstronomicalObjectLinkEnrichingProcessor astronomicalObjectLinkEnrichingProcessor;

	private final AstronomicalObjectLocationFixedValueProvider astronomicalObjectLocationFixedValueProvider;

	@Override
	public AstronomicalObject process(AstronomicalObject item) throws Exception {
		doProcess(item);
		return item;
	}

	@SuppressWarnings("NPathComplexity")
	private void doProcess(AstronomicalObject item) throws Exception {
		Page modelPage = item.getPage();
		String pageTitle = modelPage.getTitle();
		MediaWikiSource mediaWikiSource = modelPage.getMediaWikiSource();

		com.cezarykluczynski.stapi.sources.mediawiki.dto.Page page = pageApi
				.getPage(pageTitle, mediaWikiSourceMapper.fromEntityToSources(mediaWikiSource));

		if (page == null) {
			return;
		}

		final FixedValueHolder<AstronomicalObject> locationFixedValueProviderSearchedValue = astronomicalObjectLocationFixedValueProvider
				.getSearchedValue(item);
		if (locationFixedValueProviderSearchedValue.isFound()) {
			// fixed location is checked manually, so logic in AstronomicalObjectLinkEnrichingProcessor could only challenge it, thus best to skip it
			item.setLocation(locationFixedValueProviderSearchedValue.getValue());
			return;
		}

		List<Template> astronomicalObjectsTemplates = templateFinder.findTemplates(page, TemplateTitle.SIDEBAR_ASTRONOMICAL_OBJECT,
				TemplateTitle.SIDEBAR_PLANET, TemplateTitle.SIDEBAR_STAR, TemplateTitle.SIDEBAR_STAR_SYSTEM);
		if (astronomicalObjectsTemplates.size() > 1) {
			log.warn("More than one template found on page {}", pageTitle);
		}
		Template template = astronomicalObjectsTemplates.isEmpty() ? null : astronomicalObjectsTemplates.get(0);
		List<AstronomicalObject> astronomicalObjectsFromTemplate = Lists.newArrayList();
		if (template != null) {
			for (Template.Part part : template.getParts()) {
				String key = part.getKey();
				String value = part.getValue();

				if (LOCATION.equals(key)) {
					astronomicalObjectsFromTemplate.addAll(astronomicalObjectLinkWikitextProcessor.process(value));
				}
			}
		}
		if (!astronomicalObjectsFromTemplate.isEmpty()) {
			astronomicalObjectLinkEnrichingProcessor.enrich(EnrichablePair.of(astronomicalObjectsFromTemplate, item));
		}

		if (item.getLocation() == null) {
			final String firstParagraph = extractFirstParagraph(page.getWikitext());
			if (firstParagraph != null) {
				List<AstronomicalObject> astronomicalObjectsFromWikitext = astronomicalObjectLinkWikitextProcessor.process(firstParagraph);
				if (!astronomicalObjectsFromWikitext.isEmpty()) {
					astronomicalObjectLinkEnrichingProcessor.enrich(EnrichablePair.of(astronomicalObjectsFromWikitext, item));
				}
			}
		}

		if (item.getLocation() == null) {
			List<AstronomicalObject> astronomicalObjectsFromBgInfo = Lists.newArrayList();
			List<Template> bgInfoTemplates = templateFinder.findTemplates(page, TemplateTitle.BGINFO);
			if (!bgInfoTemplates.isEmpty()) {
				for (Template bgInfoTemplate : bgInfoTemplates) {
					for (Template.Part part : bgInfoTemplate.getParts()) {
						astronomicalObjectsFromBgInfo.addAll(astronomicalObjectLinkWikitextProcessor.process(part.getValue()));
					}
				}
			}
			if (!astronomicalObjectsFromBgInfo.isEmpty()) {
				astronomicalObjectLinkEnrichingProcessor.enrich(EnrichablePair.of(astronomicalObjectsFromBgInfo, item));
			}
		}
	}

	private String extractFirstParagraph(String wikitext) {
		return paragraphExtractor.extractLines(wikitext).stream()
				.filter(AstronomicalObjectLinkProcessor::isValidFirstParagraph)
				.findFirst()
				.orElse(null);
	}

	@SuppressWarnings("BooleanExpressionComplexity")
	private static boolean isValidFirstParagraph(String firstParagraphCandidate) {
		return firstParagraphCandidate != null
				&& firstParagraphCandidate.length() > 15
				&& !StringUtils.trim(firstParagraphCandidate).startsWith("{{")
				&& !firstParagraphCandidate.substring(0, 15).contains("sidebar")
				&& !firstParagraphCandidate.substring(0, 18).contains("disambiguation")
				&& !firstParagraphCandidate.substring(0, 10).contains("multiple")
				&& !StringUtils.trim(firstParagraphCandidate).startsWith("[[File:");
	}

}
