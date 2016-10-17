package com.cezarykluczynski.stapi.etl.template.actor.processor;

import com.cezarykluczynski.stapi.etl.template.actor.dto.ActorTemplate;
import com.cezarykluczynski.stapi.etl.template.common.dto.DateRange;
import com.cezarykluczynski.stapi.etl.template.common.dto.Gender;
import com.cezarykluczynski.stapi.etl.template.common.processor.AbstractTemplateProcessor;
import com.cezarykluczynski.stapi.etl.template.common.processor.datetime.PageToLifeRangeProcessor;
import com.cezarykluczynski.stapi.etl.template.common.processor.gender.PageToGenderProcessor;
import com.cezarykluczynski.stapi.util.constants.CategoryName;
import com.cezarykluczynski.stapi.util.constants.PageNames;
import com.cezarykluczynski.stapi.util.constants.TemplateNames;
import com.cezarykluczynski.stapi.wiki.dto.CategoryHeader;
import com.cezarykluczynski.stapi.wiki.dto.Page;
import com.cezarykluczynski.stapi.wiki.dto.Template;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ActorTemplatePageProcessor extends AbstractTemplateProcessor implements ItemProcessor<Page, ActorTemplate> {

	private PageToGenderProcessor pageToGenderProcessor;

	private PageToLifeRangeProcessor pageToLifeRangeProcessor;

	private ActorTemplateTemplateProcessor actorTemplateTemplateProcessor;

	@Inject
	public ActorTemplatePageProcessor(PageToGenderProcessor pageToGenderProcessor,
			PageToLifeRangeProcessor pageToLifeRangeProcessor,
			ActorTemplateTemplateProcessor actorTemplateTemplateProcessor) {
		this.pageToGenderProcessor = pageToGenderProcessor;
		this.pageToLifeRangeProcessor = pageToLifeRangeProcessor;
		this.actorTemplateTemplateProcessor = actorTemplateTemplateProcessor;
	}

	@Override
	public ActorTemplate process(Page item) throws Exception {
		if (shouldBeFilteredOut(item)) {
			return null;
		}

		ActorTemplate actorTemplate = new ActorTemplate();
		actorTemplate.setName(StringUtils.trim(StringUtils.substringBefore(item.getTitle(), "(")));
		actorTemplate.setLifeRange(new DateRange());
		actorTemplate.setPage(toPageEntity(item));

		actorTemplate.setGender(pageToGenderProcessor.process(item));
		actorTemplate.setLifeRange(pageToLifeRangeProcessor.process(item));

		Optional<Template> templateOptional = findTemplate(item, TemplateNames.SIDEBAR_ACTOR);

		if (templateOptional.isPresent()) {
			supplementUsingActorTemplateTemplateProcessor(actorTemplate, templateOptional.get());
		}

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

}
