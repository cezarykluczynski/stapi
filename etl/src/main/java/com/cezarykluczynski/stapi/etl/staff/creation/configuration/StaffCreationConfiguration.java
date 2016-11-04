package com.cezarykluczynski.stapi.etl.staff.creation.configuration;

import com.cezarykluczynski.stapi.etl.staff.creation.processor.StaffCategoriesActorTemplateEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.staff.creation.processor.StaffReader;
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
public class StaffCreationConfiguration {

	public static final String STAFF_ACTOR_TEMPLATE_SINGLE_PAGE_PROCESSOR = "StaffActorTemplateSinglePageProcessor";
	public static final String STAFF_ACTOR_TEMPLATE_PAGE_PROCESSOR = "StaffActorTemplatePageProcessor";

	@Inject
	private ApplicationContext applicationContext;

	@Inject
	private CategoryApi categoryApi;

	@Bean
	public StaffReader staffReader() {
		List<PageHeader> staff = Lists.newArrayList();

		staff.addAll(categoryApi.getPages(CategoryName.ART_DEPARTMENT));
		staff.addAll(categoryApi.getPages(CategoryName.ART_DIRECTORS));
		staff.addAll(categoryApi.getPages(CategoryName.PRODUCTION_DESIGNERS));
		staff.addAll(categoryApi.getPages(CategoryName.CAMERA_AND_ELECTRICAL_DEPARTMENT));
		staff.addAll(categoryApi.getPages(CategoryName.CINEMATOGRAPHERS));
		staff.addAll(categoryApi.getPages(CategoryName.CASTING_DEPARTMENT));
		staff.addAll(categoryApi.getPages(CategoryName.COSTUME_DEPARTMENT));
		staff.addAll(categoryApi.getPages(CategoryName.COSTUME_DESIGNERS));
		staff.addAll(categoryApi.getPages(CategoryName.DIRECTORS));
		staff.addAll(categoryApi.getPages(CategoryName.ASSISTANT_AND_SECOND_UNIT_DIRECTORS));
		staff.addAll(categoryApi.getPages(CategoryName.EXHIBIT_AND_ATTRACTION_STAFF));
		staff.addAll(categoryApi.getPages(CategoryName.FILM_EDITORS));
		staff.addAll(categoryApi.getPages(CategoryName.LINGUISTS));
		staff.addAll(categoryApi.getPages(CategoryName.LOCATION_STAFF));
		staff.addAll(categoryApi.getPages(CategoryName.MAKEUP_STAFF));
		staff.addAll(categoryApi.getPages(CategoryName.MUSIC_DEPARTMENT));
		staff.addAll(categoryApi.getPages(CategoryName.COMPOSERS));
		staff.addAll(categoryApi.getPages(CategoryName.PERSONAL_ASSISTANTS));
		staff.addAll(categoryApi.getPages(CategoryName.PRODUCERS));
		staff.addAll(categoryApi.getPages(CategoryName.PRODUCTION_ASSOCIATES));
		staff.addAll(categoryApi.getPages(CategoryName.PRODUCTION_STAFF));
		staff.addAll(categoryApi.getPages(CategoryName.PUBLICATION_STAFF));
		staff.addAll(categoryApi.getPages(CategoryName.SCIENCE_CONSULTANTS));
		staff.addAll(categoryApi.getPages(CategoryName.SOUND_DEPARTMENT));
		staff.addAll(categoryApi.getPages(CategoryName.SPECIAL_AND_VISUAL_EFFECTS_STAFF));
		staff.addAll(categoryApi.getPages(CategoryName.STAR_TREK_AUTHORS));
		staff.addAll(categoryApi.getPages(CategoryName.STAR_TREK_AUDIO_AUTHORS));
		staff.addAll(categoryApi.getPages(CategoryName.STAR_TREK_CALENDAR_ARTISTS));
		staff.addAll(categoryApi.getPages(CategoryName.STAR_TREK_COMIC_ARTISTS));
		staff.addAll(categoryApi.getPages(CategoryName.STAR_TREK_COMIC_AUTHORS));
		staff.addAll(categoryApi.getPages(CategoryName.STAR_TREK_COMIC_COLOR_ARTISTS));
		staff.addAll(categoryApi.getPages(CategoryName.STAR_TREK_COMIC_INTERIOR_ARTISTS));
		staff.addAll(categoryApi.getPages(CategoryName.STAR_TREK_COMIC_INK_ARTISTS));
		staff.addAll(categoryApi.getPages(CategoryName.STAR_TREK_COMIC_PENCIL_ARTISTS ));
		staff.addAll(categoryApi.getPages(CategoryName.STAR_TREK_COMIC_LETTER_ARTISTS));
		staff.addAll(categoryApi.getPages(CategoryName.STAR_TREK_COMIC_STRIP_ARTISTS));
		staff.addAll(categoryApi.getPages(CategoryName.STAR_TREK_GAME_ARTISTS));
		staff.addAll(categoryApi.getPages(CategoryName.STAR_TREK_GAME_AUTHORS));
		staff.addAll(categoryApi.getPages(CategoryName.STAR_TREK_NOVEL_ARTISTS));
		staff.addAll(categoryApi.getPages(CategoryName.STAR_TREK_NOVEL_AUTHORS));
		staff.addAll(categoryApi.getPages(CategoryName.STAR_TREK_REFERENCE_ARTISTS));
		staff.addAll(categoryApi.getPages(CategoryName.STAR_TREK_REFERENCE_AUTHORS));
		staff.addAll(categoryApi.getPages(CategoryName.STAR_TREK_PUBLICATION_ARTISTS));
		staff.addAll(categoryApi.getPages(CategoryName.STAR_TREK_PUBLICATION_DESIGNERS));
		staff.addAll(categoryApi.getPages(CategoryName.STAR_TREK_PUBLICATION_EDITORS));
		staff.addAll(categoryApi.getPages(CategoryName.STAR_TREK_PUBLICITY_ARTISTS));
		staff.addAll(categoryApi.getPages(CategoryName.CBS_DIGITAL_STAFF));
		staff.addAll(categoryApi.getPages(CategoryName.ILM_PRODUCTION_STAFF));
		staff.addAll(categoryApi.getPages(CategoryName.SPECIAL_FEATURES_STAFF));
		staff.addAll(categoryApi.getPages(CategoryName.STORY_EDITORS));
		staff.addAll(categoryApi.getPages(CategoryName.STUDIO_EXECUTIVES));
		staff.addAll(categoryApi.getPages(CategoryName.STUNT_DEPARTMENT));
		staff.addAll(categoryApi.getPages(CategoryName.TRANSPORTATION_DEPARTMENT));
		staff.addAll(categoryApi.getPages(CategoryName.VIDEO_GAME_PRODUCTION_STAFF));
		staff.addAll(categoryApi.getPages(CategoryName.WRITERS));

		return new StaffReader(Lists.newArrayList(Sets.newHashSet(staff)));
	}

	@Bean(STAFF_ACTOR_TEMPLATE_SINGLE_PAGE_PROCESSOR)
	public ActorTemplateSinglePageProcessor actorTemplateSinglePageProcessor() {
		return new ActorTemplateSinglePageProcessor(
				applicationContext.getBean(PageToGenderProcessor.class),
				applicationContext.getBean(PageToLifeRangeProcessor.class),
				applicationContext.getBean(ActorTemplateTemplateProcessor.class),
				applicationContext.getBean(StaffCategoriesActorTemplateEnrichingProcessor.class));
	}

	@Bean(STAFF_ACTOR_TEMPLATE_PAGE_PROCESSOR)
	public ActorTemplatePageProcessor actorTemplatePageProcessor() {
		return new ActorTemplatePageProcessor(
				applicationContext.getBean(STAFF_ACTOR_TEMPLATE_SINGLE_PAGE_PROCESSOR, ActorTemplateSinglePageProcessor.class),
				applicationContext.getBean(ActorTemplateListPageProcessor.class));
	}

}
