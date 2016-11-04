package com.cezarykluczynski.stapi.etl.performer.creation.processor

import com.cezarykluczynski.stapi.etl.EtlTestUtils
import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.template.actor.dto.ActorTemplate
import com.cezarykluczynski.stapi.etl.util.constant.CategoryName
import com.cezarykluczynski.stapi.sources.mediawiki.dto.CategoryHeader
import com.cezarykluczynski.stapi.util.ReflectionTestUtils
import com.google.common.collect.Lists
import spock.lang.Specification
import spock.lang.Unroll

class PerformerCategoriesActorTemplateEnrichingProcessorTest extends Specification {

	private PerformerCategoriesActorTemplateEnrichingProcessor performerCategoriesActorTemplateEnrichingProcessor

	def setup() {
		performerCategoriesActorTemplateEnrichingProcessor = new PerformerCategoriesActorTemplateEnrichingProcessor()
	}

	@Unroll("set #flagName flag when #categoryHeaderList is passed")
	def "set flagName when categoryHeaderList is passed"() {
		expect:
		performerCategoriesActorTemplateEnrichingProcessor.enrich(EnrichablePair.of(categoryHeaderList, actorTemplate))
		flag == actorTemplate[flagName]
		numberOfTrueBooleans == ReflectionTestUtils.getNumberOfTrueBooleanFields(actorTemplate)

		where:
		categoryHeaderList                                               | actorTemplate       | flagName             | flag  | numberOfTrueBooleans
		Lists.newArrayList()                                             | new ActorTemplate() | "animalPerformer"    | false | 0
		createCategoryHeaderList(CategoryName.ANIMAL_PERFORMERS)         | new ActorTemplate() | "animalPerformer"    | true  | 1
		createCategoryHeaderList(CategoryName.DIS_PERFORMERS)            | new ActorTemplate() | "disPerformer"       | true  | 1
		createCategoryHeaderList(CategoryName.DS9_PERFORMERS)            | new ActorTemplate() | "ds9Performer"       | true  | 1
		createCategoryHeaderList(CategoryName.ENT_PERFORMERS)            | new ActorTemplate() | "entPerformer"       | true  | 1
		createCategoryHeaderList(CategoryName.FILM_PERFORMERS)           | new ActorTemplate() | "filmPerformer"      | true  | 1
		createCategoryHeaderList(CategoryName.STAND_INS)                 | new ActorTemplate() | "standInPerformer"   | true  | 1
		createCategoryHeaderList(CategoryName.STUNT_PERFORMERS)          | new ActorTemplate() | "stuntPerformer"     | true  | 1
		createCategoryHeaderList(CategoryName.TAS_PERFORMERS)            | new ActorTemplate() | "tasPerformer"       | true  | 1
		createCategoryHeaderList(CategoryName.TNG_PERFORMERS)            | new ActorTemplate() | "tngPerformer"       | true  | 1
		createCategoryHeaderList(CategoryName.TOS_PERFORMERS)            | new ActorTemplate() | "tosPerformer"       | true  | 1
		createCategoryHeaderList(CategoryName.TOS_REMASTERED_PERFORMERS) | new ActorTemplate() | "tosPerformer"       | true  | 1
		createCategoryHeaderList(CategoryName.VIDEO_GAME_PERFORMERS)     | new ActorTemplate() | "videoGamePerformer" | true  | 1
		createCategoryHeaderList(CategoryName.VOICE_PERFORMERS)          | new ActorTemplate() | "voicePerformer"     | true  | 1
		createCategoryHeaderList(CategoryName.VOY_PERFORMERS)            | new ActorTemplate() | "voyPerformer"       | true  | 1
	}

	private static List<CategoryHeader> createCategoryHeaderList(String title) {
		return Lists.newArrayList(EtlTestUtils.createCategoryHeaderList(title))
	}

}
