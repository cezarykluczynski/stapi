package com.cezarykluczynski.stapi.etl.template.actor.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.performer.creation.processor.CategoriesActorTemplateEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.actor.dto.ActorTemplate;
import com.cezarykluczynski.stapi.etl.template.common.dto.Gender;
import com.cezarykluczynski.stapi.etl.template.common.processor.AbstractTemplateProcessor;
import com.cezarykluczynski.stapi.etl.template.common.processor.datetime.PageToLifeRangeProcessor;
import com.cezarykluczynski.stapi.etl.template.common.processor.gender.PageToGenderProcessor;
import com.cezarykluczynski.stapi.etl.util.constant.CategoryName;
import com.cezarykluczynski.stapi.etl.util.constant.PageNames;
import com.cezarykluczynski.stapi.util.constant.TemplateNames;
import com.cezarykluczynski.stapi.util.tool.LogicUtil;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.CategoryHeader;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Page;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.Template;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
public class ActorTemplatePageProcessor extends AbstractTemplateProcessor
		implements ItemProcessor<Page, ActorTemplate> {

	private static final Pattern BIRTH_NAME = Pattern.compile("'''(.+?)'''");

	private PageToGenderProcessor pageToGenderProcessor;

	private PageToLifeRangeProcessor pageToLifeRangeProcessor;

	private ActorTemplateTemplateProcessor actorTemplateTemplateProcessor;

	private CategoriesActorTemplateEnrichingProcessor categoriesActorTemplateEnrichingProcessor;

	@Inject
	public ActorTemplatePageProcessor(PageToGenderProcessor pageToGenderProcessor,
			PageToLifeRangeProcessor pageToLifeRangeProcessor,
			ActorTemplateTemplateProcessor actorTemplateTemplateProcessor,
			CategoriesActorTemplateEnrichingProcessor categoriesActorTemplateEnrichingProcessor) {
		this.pageToGenderProcessor = pageToGenderProcessor;
		this.pageToLifeRangeProcessor = pageToLifeRangeProcessor;
		this.actorTemplateTemplateProcessor = actorTemplateTemplateProcessor;
		this.categoriesActorTemplateEnrichingProcessor = categoriesActorTemplateEnrichingProcessor;
	}

	@Override
	public ActorTemplate process(Page item) throws Exception {
		if (shouldBeFilteredOut(item)) {
			return null;
		}

		ActorTemplate actorTemplate = new ActorTemplate();
		actorTemplate.setName(StringUtils.trim(StringUtils.substringBefore(item.getTitle(), "(")));
		actorTemplate.setBirthName(getBirthName(item.getWikitext()));

		actorTemplate.setPage(toPageEntity(item));

		actorTemplate.setGender(pageToGenderProcessor.process(item));
		actorTemplate.setLifeRange(pageToLifeRangeProcessor.process(item));

		Optional<Template> templateOptional = findTemplate(item, TemplateNames.SIDEBAR_ACTOR);

		if (templateOptional.isPresent()) {
			supplementUsingActorTemplateTemplateProcessor(actorTemplate, templateOptional.get());
		}

		removeBirthNameIfItEqualsName(actorTemplate);

		categoriesActorTemplateEnrichingProcessor.enrich(EnrichablePair.of(item.getCategories(), actorTemplate));

		return actorTemplate;
	}

	private boolean shouldBeFilteredOut(Page item) {
		List<CategoryHeader> categoryHeaderList = Optional.ofNullable(item.getCategories()).orElse(Lists.newArrayList());
		return PageNames.UNKNOWN_PERFORMERS.equals(item.getTitle()) || categoryHeaderList.stream()
				.anyMatch(categoryHeader -> CategoryName.PRODUCTION_LISTS.equals(categoryHeader.getTitle()));
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

		if (originalGender != null && supplementedGender != null && originalGender != supplementedGender) {
			log.error("Gender {} found by ActorTemplatePageProcessor differs from gender {} found by " +
					"ActorTemplateTemplateProcessor for {} - setting gender to null", originalGender,
					supplementedGender, actorTemplate.getName());

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

		if (LogicUtil.xorNull(name, birthName)) {
			return;
		} else if (Objects.equals(name, birthName)) {
			actorTemplate.setBirthName(null);
		}
	}

}
