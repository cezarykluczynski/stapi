package com.cezarykluczynski.stapi.etl.performer.creation.processor

import com.cezarykluczynski.stapi.etl.EtlTestUtils
import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.common.processor.CategoryTitlesExtractingProcessor
import com.cezarykluczynski.stapi.etl.template.actor.dto.ActorTemplate
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle
import com.cezarykluczynski.stapi.sources.mediawiki.dto.CategoryHeader
import com.cezarykluczynski.stapi.util.ReflectionTestUtils
import com.google.common.collect.Lists
import spock.lang.Specification
import spock.lang.Unroll

class PerformerCategoriesActorTemplateEnrichingProcessorTest extends Specification {

	private CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessorMock

	private PerformerCategoriesActorTemplateEnrichingProcessor performerCategoriesActorTemplateEnrichingProcessor

	void setup() {
		categoryTitlesExtractingProcessorMock = Mock()
		performerCategoriesActorTemplateEnrichingProcessor = new PerformerCategoriesActorTemplateEnrichingProcessor(categoryTitlesExtractingProcessorMock)
	}

	@Unroll('set #flagName flag when #categoryHeaderList is passed')
	void "sets flagName when categoryHeaderList is passed"() {
		given:
		categoryTitlesExtractingProcessorMock.process(_ as List<CategoryHeader>) >> {
			List<CategoryHeader> categoryHeaderList -> Lists.newArrayList(categoryHeaderList[0].title)
		}

		expect:
		performerCategoriesActorTemplateEnrichingProcessor.enrich(EnrichablePair.of(categoryHeaderList, actorTemplate))
		flag == actorTemplate[flagName]
		numberOfTrueBooleans == ReflectionTestUtils.getNumberOfTrueBooleanFields(actorTemplate)

		where:
		categoryHeaderList                                                | actorTemplate       | flagName             | flag  | numberOfTrueBooleans
		Lists.newArrayList()                                              | new ActorTemplate() | 'animalPerformer'    | false | 0
		createCategoryHeaderList(CategoryTitle.ANIMAL_PERFORMERS)         | new ActorTemplate() | 'animalPerformer'    | true  | 1
		createCategoryHeaderList(CategoryTitle.DIS_PERFORMERS)            | new ActorTemplate() | 'disPerformer'       | true  | 1
		createCategoryHeaderList(CategoryTitle.DS9_PERFORMERS)            | new ActorTemplate() | 'ds9Performer'       | true  | 1
		createCategoryHeaderList(CategoryTitle.ENT_PERFORMERS)            | new ActorTemplate() | 'entPerformer'       | true  | 1
		createCategoryHeaderList(CategoryTitle.FILM_PERFORMERS)           | new ActorTemplate() | 'filmPerformer'      | true  | 1
		createCategoryHeaderList(CategoryTitle.STAND_INS)                 | new ActorTemplate() | 'standInPerformer'   | true  | 1
		createCategoryHeaderList(CategoryTitle.STUNT_PERFORMERS)          | new ActorTemplate() | 'stuntPerformer'     | true  | 1
		createCategoryHeaderList(CategoryTitle.TAS_PERFORMERS)            | new ActorTemplate() | 'tasPerformer'       | true  | 1
		createCategoryHeaderList(CategoryTitle.TNG_PERFORMERS)            | new ActorTemplate() | 'tngPerformer'       | true  | 1
		createCategoryHeaderList(CategoryTitle.TOS_PERFORMERS)            | new ActorTemplate() | 'tosPerformer'       | true  | 1
		createCategoryHeaderList(CategoryTitle.TOS_REMASTERED_PERFORMERS) | new ActorTemplate() | 'tosPerformer'       | true  | 1
		createCategoryHeaderList(CategoryTitle.VIDEO_GAME_PERFORMERS)     | new ActorTemplate() | 'videoGamePerformer' | true  | 1
		createCategoryHeaderList(CategoryTitle.VOICE_PERFORMERS)          | new ActorTemplate() | 'voicePerformer'     | true  | 1
		createCategoryHeaderList(CategoryTitle.VOY_PERFORMERS)            | new ActorTemplate() | 'voyPerformer'       | true  | 1
	}

	private static List<CategoryHeader> createCategoryHeaderList(String title) {
		Lists.newArrayList(EtlTestUtils.createCategoryHeaderList(title))
	}

}
