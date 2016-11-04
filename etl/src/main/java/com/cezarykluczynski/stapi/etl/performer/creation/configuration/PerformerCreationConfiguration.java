package com.cezarykluczynski.stapi.etl.performer.creation.configuration;

import com.cezarykluczynski.stapi.etl.performer.creation.processor.PerformerCategoriesActorTemplateEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.performer.creation.processor.PerformerReader;
import com.cezarykluczynski.stapi.etl.template.actor.processor.ActorTemplateListPageProcessor;
import com.cezarykluczynski.stapi.etl.template.actor.processor.ActorTemplatePageProcessor;
import com.cezarykluczynski.stapi.etl.template.actor.processor.ActorTemplateSinglePageProcessor;
import com.cezarykluczynski.stapi.etl.template.actor.processor.ActorTemplateTemplateProcessor;
import com.cezarykluczynski.stapi.etl.template.common.processor.datetime.PageToLifeRangeProcessor;
import com.cezarykluczynski.stapi.etl.template.common.processor.gender.PageToGenderProcessor;
import com.cezarykluczynski.stapi.etl.util.constant.CategoryName;
import com.cezarykluczynski.stapi.sources.mediawiki.api.CategoryApi;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.inject.Inject;
import java.util.List;

@Configuration
public class PerformerCreationConfiguration {

	public static final String PERFORMER_ACTOR_TEMPLATE_SINGLE_PAGE_PROCESSOR = "PerformerActorTemplateSinglePageProcessor";
	public static final String PERFORMER_ACTOR_TEMPLATE_PAGE_PROCESSOR = "PerformerActorTemplatePageProcessor";

	@Inject
	private ApplicationContext applicationContext;

	@Inject
	private CategoryApi categoryApi;

	@Bean
	public PerformerReader performerReader() {
		List<PageHeader> performers = Lists.newArrayList();

		performers.addAll(categoryApi.getPages(CategoryName.PERFORMERS));
		performers.addAll(categoryApi.getPages(CategoryName.ANIMAL_PERFORMERS));
		performers.addAll(categoryApi.getPages(CategoryName.DIS_PERFORMERS));
		performers.addAll(categoryApi.getPages(CategoryName.DS9_PERFORMERS));
		performers.addAll(categoryApi.getPages(CategoryName.ENT_PERFORMERS));
		performers.addAll(categoryApi.getPages(CategoryName.FILM_PERFORMERS));
		performers.addAll(categoryApi.getPages(CategoryName.STAND_INS));
		performers.addAll(categoryApi.getPages(CategoryName.STUNT_PERFORMERS));
		performers.addAll(categoryApi.getPages(CategoryName.TAS_PERFORMERS));
		performers.addAll(categoryApi.getPages(CategoryName.TNG_PERFORMERS));
		performers.addAll(categoryApi.getPages(CategoryName.TOS_PERFORMERS));
		performers.addAll(categoryApi.getPages(CategoryName.VIDEO_GAME_PERFORMERS));
		performers.addAll(categoryApi.getPages(CategoryName.VOICE_PERFORMERS));
		performers.addAll(categoryApi.getPages(CategoryName.VOY_PERFORMERS));

		return new PerformerReader(Lists.newArrayList(Sets.newHashSet(performers)));
	}

	@Bean(PERFORMER_ACTOR_TEMPLATE_SINGLE_PAGE_PROCESSOR)
	public ActorTemplateSinglePageProcessor actorTemplateSinglePageProcessor() {
		return new ActorTemplateSinglePageProcessor(
				applicationContext.getBean(PageToGenderProcessor.class),
				applicationContext.getBean(PageToLifeRangeProcessor.class),
				applicationContext.getBean(ActorTemplateTemplateProcessor.class),
				applicationContext.getBean(PerformerCategoriesActorTemplateEnrichingProcessor.class));
	}

	@Bean(PERFORMER_ACTOR_TEMPLATE_PAGE_PROCESSOR)
	public ActorTemplatePageProcessor actorTemplatePageProcessor() {
		return new ActorTemplatePageProcessor(
				applicationContext.getBean(PERFORMER_ACTOR_TEMPLATE_SINGLE_PAGE_PROCESSOR, ActorTemplateSinglePageProcessor.class),
				applicationContext.getBean(ActorTemplateListPageProcessor.class));
	}


}
