package com.cezarykluczynski.stapi.etl.template.actor.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.service.PageBindingService;
import com.cezarykluczynski.stapi.etl.performer.creation.processor.CategoriesActorTemplateEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.actor.dto.ActorTemplate;
import com.cezarykluczynski.stapi.etl.template.common.dto.enums.Gender;
import com.cezarykluczynski.stapi.etl.template.common.processor.datetime.PageToLifeRangeProcessor;
import com.cezarykluczynski.stapi.etl.template.common.processor.gender.PageToGenderProcessor;
import com.cezarykluczynski.stapi.etl.template.service.TemplateFinder;
import com.cezarykluczynski.stapi.etl.util.TitleUtil;
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.CategoryHeader;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import com.cezarykluczynski.stapi.util.constant.PageTitle;
import com.cezarykluczynski.stapi.util.constant.TemplateTitle;
import com.cezarykluczynski.stapi.util.tool.LogicUtil;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class ActorTemplateSinglePageProcessor implements ItemProcessor<Page, ActorTemplate> {

	private static final Pattern BIRTH_NAME = Pattern.compile("'''(.+?)'''");

	private PageToGenderProcessor pageToGenderProcessor;

	private PageToLifeRangeProcessor pageToLifeRangeProcessor;

	private ActorTemplateTemplateProcessor actorTemplateTemplateProcessor;

	private CategoriesActorTemplateEnrichingProcessor categoriesActorTemplateEnrichingProcessor;

	private PageBindingService pageBindingService;

	private TemplateFinder templateFinder;

	public ActorTemplateSinglePageProcessor(PageToGenderProcessor pageToGenderProcessor, PageToLifeRangeProcessor pageToLifeRangeProcessor,
			ActorTemplateTemplateProcessor actorTemplateTemplateProcessor,
			CategoriesActorTemplateEnrichingProcessor categoriesActorTemplateEnrichingProcessor,
			PageBindingService pageBindingService, TemplateFinder templateFinder) {
		this.pageToGenderProcessor = pageToGenderProcessor;
		this.pageToLifeRangeProcessor = pageToLifeRangeProcessor;
		this.actorTemplateTemplateProcessor = actorTemplateTemplateProcessor;
		this.categoriesActorTemplateEnrichingProcessor = categoriesActorTemplateEnrichingProcessor;
		this.pageBindingService = pageBindingService;
		this.templateFinder = templateFinder;
	}

	@Override
	public ActorTemplate process(Page item) throws Exception {
		if (shouldBeFilteredOut(item)) {
			return null;
		}

		ActorTemplate actorTemplate = new ActorTemplate();
		actorTemplate.setName(TitleUtil.getNameFromTitle(item.getTitle()));
		actorTemplate.setBirthName(getBirthName(item.getWikitext()));
		actorTemplate.setPage(pageBindingService.fromPageToPageEntity(item));

		actorTemplate.setGender(pageToGenderProcessor.process(item));
		actorTemplate.setLifeRange(pageToLifeRangeProcessor.process(item));

		Optional<Template> templateOptional = templateFinder
				.findTemplate(item, TemplateTitle.SIDEBAR_ACTOR, TemplateTitle.SIDEBAR_CREW);

		if (templateOptional.isPresent()) {
			supplementUsingActorTemplateTemplateProcessor(actorTemplate, templateOptional.get());
		}

		removeBirthNameIfItEqualsName(actorTemplate);
		categoriesActorTemplateEnrichingProcessor.enrich(EnrichablePair.of(item.getCategories(), actorTemplate));

		return actorTemplate;
	}

	private boolean shouldBeFilteredOut(Page item) {
		List<CategoryHeader> categoryHeaderList = Optional.ofNullable(item.getCategories()).orElse(Lists.newArrayList());
		return PageTitle.UNKNOWN_PERFORMERS.equals(item.getTitle()) || categoryHeaderList.stream()
				.anyMatch(categoryHeader -> CategoryTitle.PRODUCTION_LISTS.equals(categoryHeader.getTitle()));
	}

	private void supplementUsingActorTemplateTemplateProcessor(ActorTemplate actorTemplate, Template template)
			throws Exception {
		ActorTemplate actorTemplateFromTemplate = actorTemplateTemplateProcessor.process(template);

		actorTemplate.setPlaceOfBirth(actorTemplateFromTemplate.getPlaceOfBirth());
		actorTemplate.setPlaceOfDeath(actorTemplateFromTemplate.getPlaceOfDeath());

		if (actorTemplateFromTemplate.getName() != null) {
			actorTemplate.setName(actorTemplateFromTemplate.getName());
		}
		if (actorTemplateFromTemplate.getBirthName() != null) {
			actorTemplate.setBirthName(actorTemplateFromTemplate.getBirthName());
		}

		Gender originalGender = actorTemplate.getGender();
		Gender supplementedGender = actorTemplateFromTemplate.getGender();

		if (originalGender != null && supplementedGender != null && !originalGender.equals(supplementedGender)) {
			log.warn("Gender {} found by ActorTemplatePageProcessor differs from gender {} found by "
					+ "ActorTemplateTemplateProcessor for {} - setting gender to null", originalGender, supplementedGender, actorTemplate.getName());

			actorTemplate.setGender(null);
			return;
		}

		if (originalGender == null) {
			actorTemplate.setGender(supplementedGender);
		}
	}

	private String getBirthName(String wikitext) {
		if (wikitext == null) {
			return null;
		}

		Matcher matcher = BIRTH_NAME.matcher(wikitext);
		return matcher.find() ? matcher.group(1) : null;
	}

	private void removeBirthNameIfItEqualsName(ActorTemplate actorTemplate) {
		String name = actorTemplate.getName();
		String birthName = actorTemplate.getBirthName();

		if (!LogicUtil.xorNull(name, birthName) && Objects.equals(name, birthName)) {
			actorTemplate.setBirthName(null);
		}
	}

}
