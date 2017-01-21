package com.cezarykluczynski.stapi.etl.staff.creation.configuration;

import com.cezarykluczynski.stapi.etl.common.service.PageBindingService;
import com.cezarykluczynski.stapi.etl.configuration.job.service.StepCompletenessDecider;
import com.cezarykluczynski.stapi.etl.staff.creation.processor.StaffCategoriesActorTemplateEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.staff.creation.processor.StaffReader;
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
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import javax.inject.Inject;
import java.util.List;

@Configuration
public class StaffCreationConfiguration {

	public static final String STAFF_ACTOR_TEMPLATE_SINGLE_PAGE_PROCESSOR = "StaffActorTemplateSinglePageProcessor";
	public static final String STAFF_ACTOR_TEMPLATE_PAGE_PROCESSOR = "StaffActorTemplatePageProcessor";

	@Inject
	private ApplicationContext applicationContext;

	@Inject
	private CategoryApi categoryApi;

	@Inject
	private StepCompletenessDecider stepCompletenessDecider;

	@Bean
	@DependsOn("batchDatabaseInitializer")
	public StaffReader staffReader() {
		List<PageHeader> staff = Lists.newArrayList();

		if (!stepCompletenessDecider.isStepComplete(JobName.JOB_CREATE, StepName.CREATE_STAFF)) {
			staff.addAll(categoryApi.getPages(CategoryName.ART_DEPARTMENT, MediaWikiSource.MEMORY_ALPHA_EN));
			staff.addAll(categoryApi.getPages(CategoryName.ART_DIRECTORS, MediaWikiSource.MEMORY_ALPHA_EN));
			staff.addAll(categoryApi.getPages(CategoryName.PRODUCTION_DESIGNERS, MediaWikiSource.MEMORY_ALPHA_EN));
			staff.addAll(categoryApi.getPages(CategoryName.CAMERA_AND_ELECTRICAL_DEPARTMENT, MediaWikiSource.MEMORY_ALPHA_EN));
			staff.addAll(categoryApi.getPages(CategoryName.CINEMATOGRAPHERS, MediaWikiSource.MEMORY_ALPHA_EN));
			staff.addAll(categoryApi.getPages(CategoryName.CASTING_DEPARTMENT, MediaWikiSource.MEMORY_ALPHA_EN));
			staff.addAll(categoryApi.getPages(CategoryName.COSTUME_DEPARTMENT, MediaWikiSource.MEMORY_ALPHA_EN));
			staff.addAll(categoryApi.getPages(CategoryName.COSTUME_DESIGNERS, MediaWikiSource.MEMORY_ALPHA_EN));
			staff.addAll(categoryApi.getPages(CategoryName.DIRECTORS, MediaWikiSource.MEMORY_ALPHA_EN));
			staff.addAll(categoryApi.getPages(CategoryName.ASSISTANT_AND_SECOND_UNIT_DIRECTORS, MediaWikiSource.MEMORY_ALPHA_EN));
			staff.addAll(categoryApi.getPages(CategoryName.EXHIBIT_AND_ATTRACTION_STAFF, MediaWikiSource.MEMORY_ALPHA_EN));
			staff.addAll(categoryApi.getPages(CategoryName.FILM_EDITORS, MediaWikiSource.MEMORY_ALPHA_EN));
			staff.addAll(categoryApi.getPages(CategoryName.LINGUISTS, MediaWikiSource.MEMORY_ALPHA_EN));
			staff.addAll(categoryApi.getPages(CategoryName.LOCATION_STAFF, MediaWikiSource.MEMORY_ALPHA_EN));
			staff.addAll(categoryApi.getPages(CategoryName.MAKEUP_STAFF, MediaWikiSource.MEMORY_ALPHA_EN));
			staff.addAll(categoryApi.getPages(CategoryName.MUSIC_DEPARTMENT, MediaWikiSource.MEMORY_ALPHA_EN));
			staff.addAll(categoryApi.getPages(CategoryName.COMPOSERS, MediaWikiSource.MEMORY_ALPHA_EN));
			staff.addAll(categoryApi.getPages(CategoryName.PERSONAL_ASSISTANTS, MediaWikiSource.MEMORY_ALPHA_EN));
			staff.addAll(categoryApi.getPages(CategoryName.PRODUCERS, MediaWikiSource.MEMORY_ALPHA_EN));
			staff.addAll(categoryApi.getPages(CategoryName.PRODUCTION_ASSOCIATES, MediaWikiSource.MEMORY_ALPHA_EN));
			staff.addAll(categoryApi.getPages(CategoryName.PRODUCTION_STAFF, MediaWikiSource.MEMORY_ALPHA_EN));
			staff.addAll(categoryApi.getPages(CategoryName.PUBLICATION_STAFF, MediaWikiSource.MEMORY_ALPHA_EN));
			staff.addAll(categoryApi.getPages(CategoryName.SCIENCE_CONSULTANTS, MediaWikiSource.MEMORY_ALPHA_EN));
			staff.addAll(categoryApi.getPages(CategoryName.SOUND_DEPARTMENT, MediaWikiSource.MEMORY_ALPHA_EN));
			staff.addAll(categoryApi.getPages(CategoryName.SPECIAL_AND_VISUAL_EFFECTS_STAFF, MediaWikiSource.MEMORY_ALPHA_EN));
			staff.addAll(categoryApi.getPages(CategoryName.STAR_TREK_AUTHORS, MediaWikiSource.MEMORY_ALPHA_EN));
			staff.addAll(categoryApi.getPages(CategoryName.STAR_TREK_AUDIO_AUTHORS, MediaWikiSource.MEMORY_ALPHA_EN));
			staff.addAll(categoryApi.getPages(CategoryName.STAR_TREK_CALENDAR_ARTISTS, MediaWikiSource.MEMORY_ALPHA_EN));
			staff.addAll(categoryApi.getPages(CategoryName.STAR_TREK_COMIC_ARTISTS, MediaWikiSource.MEMORY_ALPHA_EN));
			staff.addAll(categoryApi.getPages(CategoryName.STAR_TREK_COMIC_AUTHORS, MediaWikiSource.MEMORY_ALPHA_EN));
			staff.addAll(categoryApi.getPages(CategoryName.STAR_TREK_COMIC_COLOR_ARTISTS, MediaWikiSource.MEMORY_ALPHA_EN));
			staff.addAll(categoryApi.getPages(CategoryName.STAR_TREK_COMIC_INTERIOR_ARTISTS, MediaWikiSource.MEMORY_ALPHA_EN));
			staff.addAll(categoryApi.getPages(CategoryName.STAR_TREK_COMIC_INK_ARTISTS, MediaWikiSource.MEMORY_ALPHA_EN));
			staff.addAll(categoryApi.getPages(CategoryName.STAR_TREK_COMIC_PENCIL_ARTISTS, MediaWikiSource.MEMORY_ALPHA_EN));
			staff.addAll(categoryApi.getPages(CategoryName.STAR_TREK_COMIC_LETTER_ARTISTS, MediaWikiSource.MEMORY_ALPHA_EN));
			staff.addAll(categoryApi.getPages(CategoryName.STAR_TREK_COMIC_STRIP_ARTISTS, MediaWikiSource.MEMORY_ALPHA_EN));
			staff.addAll(categoryApi.getPages(CategoryName.STAR_TREK_GAME_ARTISTS, MediaWikiSource.MEMORY_ALPHA_EN));
			staff.addAll(categoryApi.getPages(CategoryName.STAR_TREK_GAME_AUTHORS, MediaWikiSource.MEMORY_ALPHA_EN));
			staff.addAll(categoryApi.getPages(CategoryName.STAR_TREK_NOVEL_ARTISTS, MediaWikiSource.MEMORY_ALPHA_EN));
			staff.addAll(categoryApi.getPages(CategoryName.STAR_TREK_NOVEL_AUTHORS, MediaWikiSource.MEMORY_ALPHA_EN));
			staff.addAll(categoryApi.getPages(CategoryName.STAR_TREK_REFERENCE_ARTISTS, MediaWikiSource.MEMORY_ALPHA_EN));
			staff.addAll(categoryApi.getPages(CategoryName.STAR_TREK_REFERENCE_AUTHORS, MediaWikiSource.MEMORY_ALPHA_EN));
			staff.addAll(categoryApi.getPages(CategoryName.STAR_TREK_PUBLICATION_ARTISTS, MediaWikiSource.MEMORY_ALPHA_EN));
			staff.addAll(categoryApi.getPages(CategoryName.STAR_TREK_PUBLICATION_DESIGNERS, MediaWikiSource.MEMORY_ALPHA_EN));
			staff.addAll(categoryApi.getPages(CategoryName.STAR_TREK_PUBLICATION_EDITORS, MediaWikiSource.MEMORY_ALPHA_EN));
			staff.addAll(categoryApi.getPages(CategoryName.STAR_TREK_PUBLICITY_ARTISTS, MediaWikiSource.MEMORY_ALPHA_EN));
			staff.addAll(categoryApi.getPages(CategoryName.CBS_DIGITAL_STAFF, MediaWikiSource.MEMORY_ALPHA_EN));
			staff.addAll(categoryApi.getPages(CategoryName.ILM_PRODUCTION_STAFF, MediaWikiSource.MEMORY_ALPHA_EN));
			staff.addAll(categoryApi.getPages(CategoryName.SPECIAL_FEATURES_STAFF, MediaWikiSource.MEMORY_ALPHA_EN));
			staff.addAll(categoryApi.getPages(CategoryName.STORY_EDITORS, MediaWikiSource.MEMORY_ALPHA_EN));
			staff.addAll(categoryApi.getPages(CategoryName.STUDIO_EXECUTIVES, MediaWikiSource.MEMORY_ALPHA_EN));
			staff.addAll(categoryApi.getPages(CategoryName.STUNT_DEPARTMENT, MediaWikiSource.MEMORY_ALPHA_EN));
			staff.addAll(categoryApi.getPages(CategoryName.TRANSPORTATION_DEPARTMENT, MediaWikiSource.MEMORY_ALPHA_EN));
			staff.addAll(categoryApi.getPages(CategoryName.VIDEO_GAME_PRODUCTION_STAFF, MediaWikiSource.MEMORY_ALPHA_EN));
			staff.addAll(categoryApi.getPages(CategoryName.WRITERS, MediaWikiSource.MEMORY_ALPHA_EN));
		}

		return new StaffReader(Lists.newArrayList(Sets.newHashSet(staff)));
	}

	@Bean(STAFF_ACTOR_TEMPLATE_SINGLE_PAGE_PROCESSOR)
	public ActorTemplateSinglePageProcessor actorTemplateSinglePageProcessor() {
		return new ActorTemplateSinglePageProcessor(
				applicationContext.getBean(PageToGenderProcessor.class),
				applicationContext.getBean(PageToLifeRangeProcessor.class),
				applicationContext.getBean(ActorTemplateTemplateProcessor.class),
				applicationContext.getBean(StaffCategoriesActorTemplateEnrichingProcessor.class),
				applicationContext.getBean(PageBindingService.class),
				applicationContext.getBean(TemplateFinder.class));
	}

	@Bean(STAFF_ACTOR_TEMPLATE_PAGE_PROCESSOR)
	public ActorTemplatePageProcessor actorTemplatePageProcessor() {
		return new ActorTemplatePageProcessor(
				applicationContext.getBean(STAFF_ACTOR_TEMPLATE_SINGLE_PAGE_PROCESSOR, ActorTemplateSinglePageProcessor.class),
				applicationContext.getBean(ActorTemplateListPageProcessor.class));
	}

}
