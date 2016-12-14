package com.cezarykluczynski.stapi.etl.performer.creation.configuration;

import com.cezarykluczynski.stapi.etl.configuration.job.service.StepCompletenessDecider;
import com.cezarykluczynski.stapi.etl.common.service.PageBindingService;
import com.cezarykluczynski.stapi.etl.performer.creation.processor.PerformerCategoriesActorTemplateEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.performer.creation.processor.PerformerReader;
import com.cezarykluczynski.stapi.etl.template.actor.processor.ActorTemplateListPageProcessor;
import com.cezarykluczynski.stapi.etl.template.actor.processor.ActorTemplatePageProcessor;
import com.cezarykluczynski.stapi.etl.template.actor.processor.ActorTemplateSinglePageProcessor;
import com.cezarykluczynski.stapi.etl.template.actor.processor.ActorTemplateTemplateProcessor;
import com.cezarykluczynski.stapi.etl.template.common.processor.datetime.PageToLifeRangeProcessor;
import com.cezarykluczynski.stapi.etl.template.common.processor.gender.PageToGenderProcessor;
import com.cezarykluczynski.stapi.etl.template.service.TemplateFinder;
import com.cezarykluczynski.stapi.etl.util.constant.CategoryName;
import com.cezarykluczynski.stapi.etl.util.constant.JobName;
import com.cezarykluczynski.stapi.etl.util.constant.StepName;
import com.cezarykluczynski.stapi.sources.mediawiki.api.CategoryApi;
import com.cezarykluczynski.stapi.sources.mediawiki.api.enums.MediaWikiSource;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.PageHeader;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.springframework.boot.autoconfigure.batch.BatchDatabaseInitializer;
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

	@Inject
	private StepCompletenessDecider stepCompletenessDecider;

	// ensure Spring Batch migrates it's schema before reader is instantiated
	@Inject
	private BatchDatabaseInitializer batchDatabaseInitializer;

	@Bean
	public PerformerReader performerReader() {
		List<PageHeader> performers = Lists.newArrayList();

		if (!stepCompletenessDecider.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_PERFORMERS)) {
			performers.addAll(categoryApi.getPages(CategoryName.PERFORMERS, MediaWikiSource.MEMORY_ALPHA_EN));
			performers.addAll(categoryApi.getPages(CategoryName.ANIMAL_PERFORMERS, MediaWikiSource.MEMORY_ALPHA_EN));
			performers.addAll(categoryApi.getPages(CategoryName.DIS_PERFORMERS, MediaWikiSource.MEMORY_ALPHA_EN));
			performers.addAll(categoryApi.getPages(CategoryName.DS9_PERFORMERS, MediaWikiSource.MEMORY_ALPHA_EN));
			performers.addAll(categoryApi.getPages(CategoryName.ENT_PERFORMERS, MediaWikiSource.MEMORY_ALPHA_EN));
			performers.addAll(categoryApi.getPages(CategoryName.FILM_PERFORMERS, MediaWikiSource.MEMORY_ALPHA_EN));
			performers.addAll(categoryApi.getPages(CategoryName.STAND_INS, MediaWikiSource.MEMORY_ALPHA_EN));
			performers.addAll(categoryApi.getPages(CategoryName.STUNT_PERFORMERS, MediaWikiSource.MEMORY_ALPHA_EN));
			performers.addAll(categoryApi.getPages(CategoryName.TAS_PERFORMERS, MediaWikiSource.MEMORY_ALPHA_EN));
			performers.addAll(categoryApi.getPages(CategoryName.TNG_PERFORMERS, MediaWikiSource.MEMORY_ALPHA_EN));
			performers.addAll(categoryApi.getPages(CategoryName.TOS_PERFORMERS, MediaWikiSource.MEMORY_ALPHA_EN));
			performers.addAll(categoryApi.getPages(CategoryName.TOS_REMASTERED_PERFORMERS, MediaWikiSource.MEMORY_ALPHA_EN));
			performers.addAll(categoryApi.getPages(CategoryName.VIDEO_GAME_PERFORMERS, MediaWikiSource.MEMORY_ALPHA_EN));
			performers.addAll(categoryApi.getPages(CategoryName.VOICE_PERFORMERS, MediaWikiSource.MEMORY_ALPHA_EN));
			performers.addAll(categoryApi.getPages(CategoryName.VOY_PERFORMERS, MediaWikiSource.MEMORY_ALPHA_EN));
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
