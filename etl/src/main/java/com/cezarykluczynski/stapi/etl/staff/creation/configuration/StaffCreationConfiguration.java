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
			staff.addAll(categoryApi.getPages(CategoryTitle.ART_DEPARTMENT, MediaWikiSource.MEMORY_ALPHA_EN));
			staff.addAll(categoryApi.getPages(CategoryTitle.ART_DIRECTORS, MediaWikiSource.MEMORY_ALPHA_EN));
			staff.addAll(categoryApi.getPages(CategoryTitle.PRODUCTION_DESIGNERS, MediaWikiSource.MEMORY_ALPHA_EN));
			staff.addAll(categoryApi.getPages(CategoryTitle.CAMERA_AND_ELECTRICAL_DEPARTMENT, MediaWikiSource.MEMORY_ALPHA_EN));
			staff.addAll(categoryApi.getPages(CategoryTitle.CINEMATOGRAPHERS, MediaWikiSource.MEMORY_ALPHA_EN));
			staff.addAll(categoryApi.getPages(CategoryTitle.CASTING_DEPARTMENT, MediaWikiSource.MEMORY_ALPHA_EN));
			staff.addAll(categoryApi.getPages(CategoryTitle.COSTUME_DEPARTMENT, MediaWikiSource.MEMORY_ALPHA_EN));
			staff.addAll(categoryApi.getPages(CategoryTitle.COSTUME_DESIGNERS, MediaWikiSource.MEMORY_ALPHA_EN));
			staff.addAll(categoryApi.getPages(CategoryTitle.DIRECTORS, MediaWikiSource.MEMORY_ALPHA_EN));
			staff.addAll(categoryApi.getPages(CategoryTitle.ASSISTANT_AND_SECOND_UNIT_DIRECTORS, MediaWikiSource.MEMORY_ALPHA_EN));
			staff.addAll(categoryApi.getPages(CategoryTitle.EXHIBIT_AND_ATTRACTION_STAFF, MediaWikiSource.MEMORY_ALPHA_EN));
			staff.addAll(categoryApi.getPages(CategoryTitle.FILM_EDITORS, MediaWikiSource.MEMORY_ALPHA_EN));
			staff.addAll(categoryApi.getPages(CategoryTitle.LINGUISTS, MediaWikiSource.MEMORY_ALPHA_EN));
			staff.addAll(categoryApi.getPages(CategoryTitle.LOCATION_STAFF, MediaWikiSource.MEMORY_ALPHA_EN));
			staff.addAll(categoryApi.getPages(CategoryTitle.MAKEUP_STAFF, MediaWikiSource.MEMORY_ALPHA_EN));
			staff.addAll(categoryApi.getPages(CategoryTitle.MUSIC_DEPARTMENT, MediaWikiSource.MEMORY_ALPHA_EN));
			staff.addAll(categoryApi.getPages(CategoryTitle.COMPOSERS, MediaWikiSource.MEMORY_ALPHA_EN));
			staff.addAll(categoryApi.getPages(CategoryTitle.PERSONAL_ASSISTANTS, MediaWikiSource.MEMORY_ALPHA_EN));
			staff.addAll(categoryApi.getPages(CategoryTitle.PRODUCERS, MediaWikiSource.MEMORY_ALPHA_EN));
			staff.addAll(categoryApi.getPages(CategoryTitle.PRODUCTION_ASSOCIATES, MediaWikiSource.MEMORY_ALPHA_EN));
			staff.addAll(categoryApi.getPages(CategoryTitle.PRODUCTION_STAFF, MediaWikiSource.MEMORY_ALPHA_EN));
			staff.addAll(categoryApi.getPages(CategoryTitle.PUBLICATION_STAFF, MediaWikiSource.MEMORY_ALPHA_EN));
			staff.addAll(categoryApi.getPages(CategoryTitle.SCIENCE_CONSULTANTS, MediaWikiSource.MEMORY_ALPHA_EN));
			staff.addAll(categoryApi.getPages(CategoryTitle.SOUND_DEPARTMENT, MediaWikiSource.MEMORY_ALPHA_EN));
			staff.addAll(categoryApi.getPages(CategoryTitle.SPECIAL_AND_VISUAL_EFFECTS_STAFF, MediaWikiSource.MEMORY_ALPHA_EN));
			staff.addAll(categoryApi.getPages(CategoryTitle.STAR_TREK_AUTHORS, MediaWikiSource.MEMORY_ALPHA_EN));
			staff.addAll(categoryApi.getPages(CategoryTitle.STAR_TREK_AUDIO_AUTHORS, MediaWikiSource.MEMORY_ALPHA_EN));
			staff.addAll(categoryApi.getPages(CategoryTitle.STAR_TREK_CALENDAR_ARTISTS, MediaWikiSource.MEMORY_ALPHA_EN));
			staff.addAll(categoryApi.getPages(CategoryTitle.STAR_TREK_COMIC_ARTISTS, MediaWikiSource.MEMORY_ALPHA_EN));
			staff.addAll(categoryApi.getPages(CategoryTitle.STAR_TREK_COMIC_AUTHORS, MediaWikiSource.MEMORY_ALPHA_EN));
			staff.addAll(categoryApi.getPages(CategoryTitle.STAR_TREK_COMIC_COLOR_ARTISTS, MediaWikiSource.MEMORY_ALPHA_EN));
			staff.addAll(categoryApi.getPages(CategoryTitle.STAR_TREK_COMIC_INTERIOR_ARTISTS, MediaWikiSource.MEMORY_ALPHA_EN));
			staff.addAll(categoryApi.getPages(CategoryTitle.STAR_TREK_COMIC_INK_ARTISTS, MediaWikiSource.MEMORY_ALPHA_EN));
			staff.addAll(categoryApi.getPages(CategoryTitle.STAR_TREK_COMIC_PENCIL_ARTISTS, MediaWikiSource.MEMORY_ALPHA_EN));
			staff.addAll(categoryApi.getPages(CategoryTitle.STAR_TREK_COMIC_LETTER_ARTISTS, MediaWikiSource.MEMORY_ALPHA_EN));
			staff.addAll(categoryApi.getPages(CategoryTitle.STAR_TREK_COMIC_STRIP_ARTISTS, MediaWikiSource.MEMORY_ALPHA_EN));
			staff.addAll(categoryApi.getPages(CategoryTitle.STAR_TREK_GAME_ARTISTS, MediaWikiSource.MEMORY_ALPHA_EN));
			staff.addAll(categoryApi.getPages(CategoryTitle.STAR_TREK_GAME_AUTHORS, MediaWikiSource.MEMORY_ALPHA_EN));
			staff.addAll(categoryApi.getPages(CategoryTitle.STAR_TREK_NOVEL_ARTISTS, MediaWikiSource.MEMORY_ALPHA_EN));
			staff.addAll(categoryApi.getPages(CategoryTitle.STAR_TREK_NOVEL_AUTHORS, MediaWikiSource.MEMORY_ALPHA_EN));
			staff.addAll(categoryApi.getPages(CategoryTitle.STAR_TREK_REFERENCE_ARTISTS, MediaWikiSource.MEMORY_ALPHA_EN));
			staff.addAll(categoryApi.getPages(CategoryTitle.STAR_TREK_REFERENCE_AUTHORS, MediaWikiSource.MEMORY_ALPHA_EN));
			staff.addAll(categoryApi.getPages(CategoryTitle.STAR_TREK_PUBLICATION_ARTISTS, MediaWikiSource.MEMORY_ALPHA_EN));
			staff.addAll(categoryApi.getPages(CategoryTitle.STAR_TREK_PUBLICATION_DESIGNERS, MediaWikiSource.MEMORY_ALPHA_EN));
			staff.addAll(categoryApi.getPages(CategoryTitle.STAR_TREK_PUBLICATION_EDITORS, MediaWikiSource.MEMORY_ALPHA_EN));
			staff.addAll(categoryApi.getPages(CategoryTitle.STAR_TREK_PUBLICITY_ARTISTS, MediaWikiSource.MEMORY_ALPHA_EN));
			staff.addAll(categoryApi.getPages(CategoryTitle.CBS_DIGITAL_STAFF, MediaWikiSource.MEMORY_ALPHA_EN));
			staff.addAll(categoryApi.getPages(CategoryTitle.ILM_PRODUCTION_STAFF, MediaWikiSource.MEMORY_ALPHA_EN));
			staff.addAll(categoryApi.getPages(CategoryTitle.SPECIAL_FEATURES_STAFF, MediaWikiSource.MEMORY_ALPHA_EN));
			staff.addAll(categoryApi.getPages(CategoryTitle.STORY_EDITORS, MediaWikiSource.MEMORY_ALPHA_EN));
			staff.addAll(categoryApi.getPages(CategoryTitle.STUDIO_EXECUTIVES, MediaWikiSource.MEMORY_ALPHA_EN));
			staff.addAll(categoryApi.getPages(CategoryTitle.STUNT_DEPARTMENT, MediaWikiSource.MEMORY_ALPHA_EN));
			staff.addAll(categoryApi.getPages(CategoryTitle.TRANSPORTATION_DEPARTMENT, MediaWikiSource.MEMORY_ALPHA_EN));
			staff.addAll(categoryApi.getPages(CategoryTitle.VIDEO_GAME_PRODUCTION_STAFF, MediaWikiSource.MEMORY_ALPHA_EN));
			staff.addAll(categoryApi.getPages(CategoryTitle.WRITERS, MediaWikiSource.MEMORY_ALPHA_EN));
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
