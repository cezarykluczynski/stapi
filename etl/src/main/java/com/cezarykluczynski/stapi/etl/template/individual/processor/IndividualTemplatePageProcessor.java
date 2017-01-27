package com.cezarykluczynski.stapi.etl.template.individual.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.service.PageBindingService;
import com.cezarykluczynski.stapi.etl.template.characterbox.processor.CharacterboxIndividualTemplateEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.individual.dto.IndividualTemplate;
import com.cezarykluczynski.stapi.etl.template.service.TemplateFinder;
import com.cezarykluczynski.stapi.etl.util.TitleUtil;
import com.cezarykluczynski.stapi.etl.util.constant.CategoryName;
import com.cezarykluczynski.stapi.etl.util.constant.CategoryNames;
import com.cezarykluczynski.stapi.sources.mediawiki.api.WikitextApi;
import com.cezarykluczynski.stapi.sources.mediawiki.api.dto.PageLink;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.CategoryHeader;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import com.cezarykluczynski.stapi.util.constant.TemplateName;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class IndividualTemplatePageProcessor implements ItemProcessor<Page, IndividualTemplate> {

	private static final String UNNAMED_PREFIX = "Unnamed";
	private static final String LIST_OF_PREFIX = "List of ";
	private static final String MEMORY_ALPHA_IMAGES_PREFIX = "Memory Alpha images";
	private static final String PERSONNEL = "personnel";

	private static final Set<String> NOT_CHARACTERS_CATEGORY_TITLES = Sets.newHashSet();

	static {
		NOT_CHARACTERS_CATEGORY_TITLES.addAll(CategoryNames.LISTS);
		NOT_CHARACTERS_CATEGORY_TITLES.add(CategoryName.FAMILIES);
	}

	private IndividualDateOfDeathEnrichingProcessor individualDateOfDeathEnrichingProcessor;

	private WikitextApi wikitextApi;

	private PageBindingService pageBindingService;

	private TemplateFinder templateFinder;

	private IndividualTemplatePartsEnrichingProcessor individualTemplatePartsEnrichingProcessor;

	private IndividualMirrorAlternateUniverseEnrichingProcessor individualMirrorAlternateUniverseEnrichingProcessor;

	private CharacterboxIndividualTemplateEnrichingProcessor characterboxIndividualTemplateEnrichingProcessor;

	@Inject
	public IndividualTemplatePageProcessor(IndividualDateOfDeathEnrichingProcessor individualDateOfDeathEnrichingProcessor, WikitextApi wikitextApi,
			PageBindingService pageBindingService, TemplateFinder templateFinder,
			IndividualTemplatePartsEnrichingProcessor individualTemplatePartsEnrichingProcessor,
			IndividualMirrorAlternateUniverseEnrichingProcessor individualMirrorAlternateUniverseEnrichingProcessor,
			CharacterboxIndividualTemplateEnrichingProcessor characterboxIndividualTemplateEnrichingProcessor) {
		this.individualDateOfDeathEnrichingProcessor = individualDateOfDeathEnrichingProcessor;
		this.wikitextApi = wikitextApi;
		this.pageBindingService = pageBindingService;
		this.templateFinder = templateFinder;
		this.individualTemplatePartsEnrichingProcessor = individualTemplatePartsEnrichingProcessor;
		this.individualMirrorAlternateUniverseEnrichingProcessor = individualMirrorAlternateUniverseEnrichingProcessor;
		this.characterboxIndividualTemplateEnrichingProcessor = characterboxIndividualTemplateEnrichingProcessor;
	}

	@Override
	public IndividualTemplate process(Page item) throws Exception {
		if (shouldBeFilteredOut(item)) {
			return null;
		}

		IndividualTemplate individualTemplate = new IndividualTemplate();
		individualTemplate.setName(TitleUtil.getNameFromTitle(item.getTitle()));
		individualTemplate.setPage(pageBindingService.fromPageToPageEntity(item));
		individualTemplate.setProductOfRedirect(!item.getRedirectPath().isEmpty());

		Optional<Template> sidebarIndividualTemplateOptional = templateFinder.findTemplate(item, TemplateName.SIDEBAR_INDIVIDUAL);

		if (!sidebarIndividualTemplateOptional.isPresent()) {
			return individualTemplate;
		}

		Template template = sidebarIndividualTemplateOptional.get();

		individualDateOfDeathEnrichingProcessor.enrich(EnrichablePair.of(template, individualTemplate));
		individualTemplatePartsEnrichingProcessor.enrich(EnrichablePair.of(template.getParts(), individualTemplate));
		individualMirrorAlternateUniverseEnrichingProcessor.enrich(EnrichablePair.of(item, individualTemplate));

		Optional<Template> memoryBetaTemplateOptional = templateFinder.findTemplate(item, TemplateName.MBETA);

		if (memoryBetaTemplateOptional.isPresent()) {
			characterboxIndividualTemplateEnrichingProcessor.enrich(EnrichablePair.of(memoryBetaTemplateOptional.get(), individualTemplate));
		}

		return individualTemplate;
	}

	private boolean shouldBeFilteredOut(Page item) {
		String title = item.getTitle();
		if (StringUtils.startsWithAny(title, UNNAMED_PREFIX, LIST_OF_PREFIX, MEMORY_ALPHA_IMAGES_PREFIX) || item.getTitle().contains(PERSONNEL)) {
			return true;
		}

		// TODO CategoryTitlesExtractingProcessor
		List<String> categoryTitles = item.getCategories()
				.stream()
				.map(CategoryHeader::getTitle)
				.collect(Collectors.toList());

		if (categoryTitles.stream().anyMatch(NOT_CHARACTERS_CATEGORY_TITLES::contains)) {
			return true;
		}

		if (categoryTitles.stream().anyMatch(categoryTitle -> categoryTitle.startsWith(UNNAMED_PREFIX))) {
			return true;
		}

		List<PageLink> pageLinkList = wikitextApi.getPageLinksFromWikitext(item.getWikitext());

		List<PageLink> categoryPageLinkList = pageLinkList
				.stream()
				.filter(pageLink -> pageLink.getTitle().toLowerCase().startsWith("category:"))
				.filter(pageLink -> pageLink.getDescription() != null && pageLink.getDescription().length() == 0)
				.collect(Collectors.toList());

		if (!categoryPageLinkList.isEmpty()) {
			return true;
		}

		return false;
	}

}
