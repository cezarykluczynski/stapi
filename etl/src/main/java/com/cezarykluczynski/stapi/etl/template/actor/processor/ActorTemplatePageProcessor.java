package com.cezarykluczynski.stapi.etl.template.actor.processor;

import com.cezarykluczynski.stapi.etl.template.actor.dto.ActorTemplate;
import com.cezarykluczynski.stapi.etl.template.common.dto.DateRange;
import com.cezarykluczynski.stapi.etl.template.common.processor.AbstractTemplateProcessor;
import com.cezarykluczynski.stapi.etl.template.common.processor.gender.PageToGenderProcessor;
import com.cezarykluczynski.stapi.etl.template.common.processor.datetime.PageToLifeRangeProcessor;
import com.cezarykluczynski.stapi.util.constants.CategoryName;
import com.cezarykluczynski.stapi.util.constants.PageNames;
import com.cezarykluczynski.stapi.util.constants.TemplateNames;
import com.cezarykluczynski.stapi.wiki.dto.CategoryHeader;
import com.cezarykluczynski.stapi.wiki.dto.Page;
import com.cezarykluczynski.stapi.wiki.dto.Template;
import com.google.common.collect.Lists;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@Service
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
		actorTemplate.setName(item.getTitle()); // TODO: remove brackets once pageId is added
		actorTemplate.setLifeRange(new DateRange());

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
		if (actorTemplate.getGender() == null) {
			actorTemplate.setGender(actorTemplateFromTemplate.getGender());
		}
	}

}
