package com.cezarykluczynski.stapi.etl.performer.creation.configuration;

import com.cezarykluczynski.stapi.etl.common.service.PageBindingService;
import com.cezarykluczynski.stapi.etl.configuration.job.service.StepCompletenessDecider;
import com.cezarykluczynski.stapi.etl.performer.creation.processor.PerformerCategoriesActorTemplateEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.performer.creation.processor.PerformerReader;
import com.cezarykluczynski.stapi.etl.template.actor.processor.ActorTemplateListPageProcessor;
import com.cezarykluczynski.stapi.etl.template.actor.processor.ActorTemplatePageProcessor;
import com.cezarykluczynski.stapi.etl.template.actor.processor.ActorTemplateSinglePageProcessor;
import com.cezarykluczynski.stapi.etl.template.actor.processor.ActorTemplateTemplateProcessor;
import com.cezarykluczynski.stapi.etl.template.common.processor.datetime.PageToLifeRangeProcessor;
import com.cezarykluczynski.stapi.etl.template.common.processor.gender.PageToGenderProcessor;
import com.cezarykluczynski.stapi.etl.template.service.TemplateFinder;
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle;
import com.cezarykluczynski.stapi.etl.util.constant.JobName;
import com.cezarykluczynski.stapi.etl.util.constant.StepName;
import com.cezarykluczynski.stapi.sources.mediawiki.api.CategoryApi;
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

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

	@Inject
	private StepCompletenessDecider stepCompletenessDecider;

	@Bean
	@DependsOn("batchDatabaseInitializer")
	public PerformerReader performerReader() {
		List<PageHeader> performers = Lists.newArrayList();

		if (!stepCompletenessDecider.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_PERFORMERS)) {
			performers.addAll(categoryApi.getPages(CategoryTitle.PERFORMERS, MediaWikiSource.MEMORY_ALPHA_EN));
			performers.addAll(categoryApi.getPages(CategoryTitle.ANIMAL_PERFORMERS, MediaWikiSource.MEMORY_ALPHA_EN));
			performers.addAll(categoryApi.getPages(CategoryTitle.DIS_PERFORMERS, MediaWikiSource.MEMORY_ALPHA_EN));
			performers.addAll(categoryApi.getPages(CategoryTitle.DS9_PERFORMERS, MediaWikiSource.MEMORY_ALPHA_EN));
			performers.addAll(categoryApi.getPages(CategoryTitle.ENT_PERFORMERS, MediaWikiSource.MEMORY_ALPHA_EN));
			performers.addAll(categoryApi.getPages(CategoryTitle.FILM_PERFORMERS, MediaWikiSource.MEMORY_ALPHA_EN));
			performers.addAll(categoryApi.getPages(CategoryTitle.STAND_INS, MediaWikiSource.MEMORY_ALPHA_EN));
			performers.addAll(categoryApi.getPages(CategoryTitle.STUNT_PERFORMERS, MediaWikiSource.MEMORY_ALPHA_EN));
			performers.addAll(categoryApi.getPages(CategoryTitle.TAS_PERFORMERS, MediaWikiSource.MEMORY_ALPHA_EN));
			performers.addAll(categoryApi.getPages(CategoryTitle.TNG_PERFORMERS, MediaWikiSource.MEMORY_ALPHA_EN));
			performers.addAll(categoryApi.getPages(CategoryTitle.TOS_PERFORMERS, MediaWikiSource.MEMORY_ALPHA_EN));
			performers.addAll(categoryApi.getPages(CategoryTitle.TOS_REMASTERED_PERFORMERS, MediaWikiSource.MEMORY_ALPHA_EN));
			performers.addAll(categoryApi.getPages(CategoryTitle.VIDEO_GAME_PERFORMERS, MediaWikiSource.MEMORY_ALPHA_EN));
			performers.addAll(categoryApi.getPages(CategoryTitle.VOICE_PERFORMERS, MediaWikiSource.MEMORY_ALPHA_EN));
			performers.addAll(categoryApi.getPages(CategoryTitle.VOY_PERFORMERS, MediaWikiSource.MEMORY_ALPHA_EN));
		}

		return new PerformerReader(Lists.newArrayList(Sets.newHashSet(performers)));
	}

	@Bean(PERFORMER_ACTOR_TEMPLATE_SINGLE_PAGE_PROCESSOR)
	public ActorTemplateSinglePageProcessor actorTemplateSinglePageProcessor() {
		return new ActorTemplateSinglePageProcessor(
				applicationContext.getBean(PageToGenderProcessor.class),
				applicationContext.getBean(PageToLifeRangeProcessor.class),
				applicationContext.getBean(ActorTemplateTemplateProcessor.class),
				applicationContext.getBean(PerformerCategoriesActorTemplateEnrichingProcessor.class),
				applicationContext.getBean(PageBindingService.class),
				applicationContext.getBean(TemplateFinder.class));
	}

	@Bean(PERFORMER_ACTOR_TEMPLATE_PAGE_PROCESSOR)
	public ActorTemplatePageProcessor actorTemplatePageProcessor() {
		return new ActorTemplatePageProcessor(
				applicationContext.getBean(PERFORMER_ACTOR_TEMPLATE_SINGLE_PAGE_PROCESSOR, ActorTemplateSinglePageProcessor.class),
				applicationContext.getBean(ActorTemplateListPageProcessor.class));
	}

}
