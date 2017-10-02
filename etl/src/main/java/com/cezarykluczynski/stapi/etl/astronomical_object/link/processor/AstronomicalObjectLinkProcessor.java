package com.cezarykluczynski.stapi.etl.astronomical_object.link.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.mapper.MediaWikiSourceMapper;
import com.cezarykluczynski.stapi.etl.common.service.ParagraphExtractor;
import com.cezarykluczynski.stapi.etl.template.service.TemplateFinder;
import com.cezarykluczynski.stapi.model.astronomical_object.entity.AstronomicalObject;
import com.cezarykluczynski.stapi.model.page.entity.Page;
import com.cezarykluczynski.stapi.model.page.entity.enums.MediaWikiSource;
import com.cezarykluczynski.stapi.sources.mediawiki.api.PageApi;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import com.cezarykluczynski.stapi.util.constant.TemplateTitle;
import com.cezarykluczynski.stapi.util.tool.LogicUtil;
import com.google.common.base.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class AstronomicalObjectLinkProcessor implements ItemProcessor<AstronomicalObject, AstronomicalObject> {

	private static final String LOCATION = "location";

	private final PageApi pageApi;

	private final MediaWikiSourceMapper mediaWikiSourceMapper;

	private final TemplateFinder templateFinder;

	private final ParagraphExtractor paragraphExtractor;

	private final AstronomicalObjectLinkWikitextProcessor astronomicalObjectLinkWikitextProcessor;

	private final AstronomicalObjectLinkEnrichingProcessor astronomicalObjectLinkEnrichingProcessor;

	public AstronomicalObjectLinkProcessor(PageApi pageApi, MediaWikiSourceMapper mediaWikiSourceMapper, TemplateFinder templateFinder,
			ParagraphExtractor paragraphExtractor, AstronomicalObjectLinkWikitextProcessor astronomicalObjectLinkWikitextProcessor,
			AstronomicalObjectLinkEnrichingProcessor astronomicalObjectLinkEnrichingProcessor) {
		this.pageApi = pageApi;
		this.mediaWikiSourceMapper = mediaWikiSourceMapper;
		this.templateFinder = templateFinder;
		this.paragraphExtractor = paragraphExtractor;
		this.astronomicalObjectLinkWikitextProcessor = astronomicalObjectLinkWikitextProcessor;
		this.astronomicalObjectLinkEnrichingProcessor = astronomicalObjectLinkEnrichingProcessor;
	}

	@Override
	public AstronomicalObject process(AstronomicalObject item) throws Exception {
		doProcess(item);
		return item;
	}

	private void doProcess(AstronomicalObject item) throws Exception {
		Page modelPage = item.getPage();
		String pageTitle = modelPage.getTitle();
		MediaWikiSource mediaWikiSource = modelPage.getMediaWikiSource();

		com.cezarykluczynski.stapi.sources.mediawiki.dto.Page page = pageApi
				.getPage(pageTitle, mediaWikiSourceMapper.fromEntityToSources(mediaWikiSource));

		if (page == null) {
			return;
		}

		AstronomicalObject astronomicalObjectFromTemplate = null;
		AstronomicalObject astronomicalObjectFromWikitext = astronomicalObjectLinkWikitextProcessor
				.process(extractFirstParagraph(page.getWikitext()));

		Optional<Template> templateOptional = templateFinder.findTemplate(page, TemplateTitle.SIDEBAR_PLANET);
		if (templateOptional.isPresent()) {
			Template template = templateOptional.get();

			for (Template.Part part : template.getParts()) {
				String key = part.getKey();
				String value = part.getValue();

				if (LOCATION.equals(key)) {
					astronomicalObjectFromTemplate = astronomicalObjectLinkWikitextProcessor.process(value);
				}
			}
		}

		if (astronomicalObjectFromTemplate == null && astronomicalObjectFromWikitext == null) {
			return;
		} else if (LogicUtil.xorNull(astronomicalObjectFromTemplate, astronomicalObjectFromWikitext)) {
			astronomicalObjectLinkEnrichingProcessor.enrich(EnrichablePair.of(Objects.firstNonNull(astronomicalObjectFromTemplate,
					astronomicalObjectFromWikitext), item));
		} else if (Objects.equal(astronomicalObjectFromTemplate, astronomicalObjectFromWikitext)) {
			astronomicalObjectLinkEnrichingProcessor.enrich(EnrichablePair.of(astronomicalObjectFromTemplate, item));
		} else {
			log.warn("Different location found for \"{}\", location says it is \"{}\", but wikitext says it is \"{}\". Using value from template",
					item, astronomicalObjectFromTemplate, astronomicalObjectFromWikitext);
			astronomicalObjectLinkEnrichingProcessor.enrich(EnrichablePair.of(astronomicalObjectFromTemplate, item));
		}
	}

	private String extractFirstParagraph(String wikitext) {
		return paragraphExtractor.extractParagraphs(wikitext).get(0);
	}

}
