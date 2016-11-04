package com.cezarykluczynski.stapi.etl.performer.creation.configuration

import com.cezarykluczynski.stapi.etl.common.configuration.AbstractCreationConfigurationTest
import com.cezarykluczynski.stapi.etl.performer.creation.processor.PerformerCategoriesActorTemplateEnrichingProcessor
import com.cezarykluczynski.stapi.etl.performer.creation.processor.PerformerReader
import com.cezarykluczynski.stapi.etl.template.actor.processor.ActorTemplateListPageProcessor
import com.cezarykluczynski.stapi.etl.template.actor.processor.ActorTemplatePageProcessor
import com.cezarykluczynski.stapi.etl.template.actor.processor.ActorTemplateSinglePageProcessor
import com.cezarykluczynski.stapi.etl.template.actor.processor.ActorTemplateTemplateProcessor
import com.cezarykluczynski.stapi.etl.template.common.processor.datetime.PageToLifeRangeProcessor
import com.cezarykluczynski.stapi.etl.template.common.processor.gender.PageToGenderProcessor
import com.cezarykluczynski.stapi.etl.util.constant.CategoryName
import com.cezarykluczynski.stapi.sources.mediawiki.api.CategoryApi
import org.springframework.context.ApplicationContext

class PerformerCreationConfigurationTest extends AbstractCreationConfigurationTest {

	private static final String TITLE_PERFORMERS = 'TITLE_PERFORMERS'
	private static final String TITLE_ANIMAL_PERFORMERS = "TITLE_ANIMAL_PERFORMERS"
	private static final String TITLE_DIS_PERFORMERS = "TITLE_DIS_PERFORMERS"
	private static final String TITLE_DS9_PERFORMERS = "TITLE_DS9_PERFORMERS"
	private static final String TITLE_ENT_PERFORMERS = "TITLE_ENT_PERFORMERS"
	private static final String TITLE_FILM_PERFORMERS = "TITLE_FILM_PERFORMERS"
	private static final String TITLE_STAND_INS = "TITLE_STAND_INS"
	private static final String TITLE_STUNT_PERFORMERS = "TITLE_STUNT_PERFORMERS"
	private static final String TITLE_TAS_PERFORMERS = "TITLE_TAS_PERFORMERS"
	private static final String TITLE_TNG_PERFORMERS = "TITLE_TNG_PERFORMERS"
	private static final String TITLE_TOS_PERFORMERS = "TITLE_TOS_PERFORMERS"
	private static final String TITLE_VIDEO_GAME_PERFORMERS = "TITLE_VIDEO_GAME_PERFORMERS"
	private static final String TITLE_VOICE_PERFORMERS = "TITLE_VOICE_PERFORMERS"
	private static final String TITLE_VOY_PERFORMERS = "TITLE_VOY_PERFORMERS"

	private ApplicationContext applicationContextMock

	private CategoryApi categoryApiMock

	private PerformerCreationConfiguration performerCreationConfiguration

	def setup() {
		applicationContextMock = Mock(ApplicationContext)
		categoryApiMock = Mock(CategoryApi)
		performerCreationConfiguration = new PerformerCreationConfiguration(
				applicationContext: applicationContextMock,
				categoryApi: categoryApiMock)
	}

	def "PerformerReader is created"() {
		when:
		PerformerReader performerReader = performerCreationConfiguration.performerReader()
		List<String> categoryHeaderTitleList = readerToList(performerReader)

		then:
		1 * categoryApiMock.getPages(CategoryName.PERFORMERS) >> createListWithPageHeaderTitle(TITLE_PERFORMERS)
		1 * categoryApiMock.getPages(CategoryName.ANIMAL_PERFORMERS) >> createListWithPageHeaderTitle(TITLE_ANIMAL_PERFORMERS)
		1 * categoryApiMock.getPages(CategoryName.DIS_PERFORMERS) >> createListWithPageHeaderTitle(TITLE_DIS_PERFORMERS)
		1 * categoryApiMock.getPages(CategoryName.DS9_PERFORMERS) >> createListWithPageHeaderTitle(TITLE_DS9_PERFORMERS)
		1 * categoryApiMock.getPages(CategoryName.ENT_PERFORMERS) >> createListWithPageHeaderTitle(TITLE_ENT_PERFORMERS)
		1 * categoryApiMock.getPages(CategoryName.FILM_PERFORMERS) >> createListWithPageHeaderTitle(TITLE_FILM_PERFORMERS)
		1 * categoryApiMock.getPages(CategoryName.STAND_INS) >> createListWithPageHeaderTitle(TITLE_STAND_INS)
		1 * categoryApiMock.getPages(CategoryName.STUNT_PERFORMERS) >> createListWithPageHeaderTitle(TITLE_STUNT_PERFORMERS)
		1 * categoryApiMock.getPages(CategoryName.TAS_PERFORMERS) >> createListWithPageHeaderTitle(TITLE_TAS_PERFORMERS)
		1 * categoryApiMock.getPages(CategoryName.TNG_PERFORMERS) >> createListWithPageHeaderTitle(TITLE_TNG_PERFORMERS)
		1 * categoryApiMock.getPages(CategoryName.TOS_PERFORMERS) >> createListWithPageHeaderTitle(TITLE_TOS_PERFORMERS)
		1 * categoryApiMock.getPages(CategoryName.VIDEO_GAME_PERFORMERS) >> createListWithPageHeaderTitle(TITLE_VIDEO_GAME_PERFORMERS)
		1 * categoryApiMock.getPages(CategoryName.VOICE_PERFORMERS) >> createListWithPageHeaderTitle(TITLE_VOICE_PERFORMERS)
		1 * categoryApiMock.getPages(CategoryName.VOY_PERFORMERS) >> createListWithPageHeaderTitle(TITLE_VOY_PERFORMERS)
		0 * _
		categoryHeaderTitleList.contains TITLE_PERFORMERS
		categoryHeaderTitleList.contains TITLE_ANIMAL_PERFORMERS
		categoryHeaderTitleList.contains TITLE_DIS_PERFORMERS
		categoryHeaderTitleList.contains TITLE_DS9_PERFORMERS
		categoryHeaderTitleList.contains TITLE_ENT_PERFORMERS
		categoryHeaderTitleList.contains TITLE_FILM_PERFORMERS
		categoryHeaderTitleList.contains TITLE_STAND_INS
		categoryHeaderTitleList.contains TITLE_STUNT_PERFORMERS
		categoryHeaderTitleList.contains TITLE_TAS_PERFORMERS
		categoryHeaderTitleList.contains TITLE_TNG_PERFORMERS
		categoryHeaderTitleList.contains TITLE_TOS_PERFORMERS
		categoryHeaderTitleList.contains TITLE_VIDEO_GAME_PERFORMERS
		categoryHeaderTitleList.contains TITLE_VOICE_PERFORMERS
		categoryHeaderTitleList.contains TITLE_VOY_PERFORMERS
	}

	def "ActorTemplateSinglePageProcessor is created"() {
		given:
		PageToGenderProcessor pageToGenderProcessorMock = Mock(PageToGenderProcessor)
		PageToLifeRangeProcessor pageToLifeRangeProcessorMock = Mock(PageToLifeRangeProcessor)
		ActorTemplateTemplateProcessor actorTemplateTemplateProcessorMock = Mock(ActorTemplateTemplateProcessor)
		PerformerCategoriesActorTemplateEnrichingProcessor performerCategoriesActorTemplateEnrichingProcessorMock =
				Mock(PerformerCategoriesActorTemplateEnrichingProcessor)

		when:
		ActorTemplateSinglePageProcessor actorTemplateSinglePageProcessor = performerCreationConfiguration
				.actorTemplateSinglePageProcessor()

		then:
		1 * applicationContextMock.getBean(PageToGenderProcessor) >> pageToGenderProcessorMock
		1 * applicationContextMock.getBean(PageToLifeRangeProcessor) >> pageToLifeRangeProcessorMock
		1 * applicationContextMock.getBean(ActorTemplateTemplateProcessor) >> actorTemplateTemplateProcessorMock
		1 * applicationContextMock.getBean(PerformerCategoriesActorTemplateEnrichingProcessor) >>
				performerCategoriesActorTemplateEnrichingProcessorMock
		actorTemplateSinglePageProcessor.pageToGenderProcessor == pageToGenderProcessorMock
		actorTemplateSinglePageProcessor.pageToLifeRangeProcessor == pageToLifeRangeProcessorMock
		actorTemplateSinglePageProcessor.actorTemplateTemplateProcessor == actorTemplateTemplateProcessorMock
		actorTemplateSinglePageProcessor.categoriesActorTemplateEnrichingProcessor == performerCategoriesActorTemplateEnrichingProcessorMock

	}

	def "ActorTemplatePageProcessor is created"() {
		given:
		ActorTemplateSinglePageProcessor actorTemplateSinglePageProcessorMock = Mock(ActorTemplateSinglePageProcessor)
		ActorTemplateListPageProcessor actorTemplateListPageProcessorMock = Mock(ActorTemplateListPageProcessor)

		when:
		ActorTemplatePageProcessor actorTemplatePageProcessor = performerCreationConfiguration
				.actorTemplatePageProcessor()

		then:
		1 * applicationContextMock.getBean(PerformerCreationConfiguration.PERFORMER_ACTOR_TEMPLATE_SINGLE_PAGE_PROCESSOR,
				ActorTemplateSinglePageProcessor) >> actorTemplateSinglePageProcessorMock
		1 * applicationContextMock.getBean(ActorTemplateListPageProcessor) >> actorTemplateListPageProcessorMock
		actorTemplatePageProcessor.actorTemplateSinglePageProcessor == actorTemplateSinglePageProcessorMock
		actorTemplatePageProcessor.actorTemplateListPageProcessor == actorTemplateListPageProcessorMock
	}

}
