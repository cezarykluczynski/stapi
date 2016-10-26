package com.cezarykluczynski.stapi.etl.performer.creation.processor

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.template.actor.dto.ActorTemplate
import com.cezarykluczynski.stapi.etl.util.constant.CategoryName
import com.cezarykluczynski.stapi.sources.mediawiki.dto.CategoryHeader
import com.google.common.collect.Lists
import spock.lang.Specification

class CategoriesActorTemplateEnrichingProcessorTest extends Specification {

	private CategoriesActorTemplateEnrichingProcessor categoriesActorTemplateEnrichingProcessor

	def setup() {
		categoriesActorTemplateEnrichingProcessor = new CategoriesActorTemplateEnrichingProcessor()
	}

	def "should not set any flags from empty category list"() {
		given:
		ActorTemplate actorTemplate = new ActorTemplate()

		when:
		categoriesActorTemplateEnrichingProcessor.enrich(EnrichablePair.of(Lists.newArrayList(), actorTemplate))

		then:
		!actorTemplate.animalPerformer
		!actorTemplate.disPerformer
		!actorTemplate.ds9Performer
		!actorTemplate.entPerformer
		!actorTemplate.filmPerformer
		!actorTemplate.standInPerformer
		!actorTemplate.stuntPerformer
		!actorTemplate.tasPerformer
		!actorTemplate.tngPerformer
		!actorTemplate.tosPerformer
		!actorTemplate.videoGamePerformer
		!actorTemplate.voicePerformer
		!actorTemplate.voyPerformer
	}

	def "should set animalPerformer flag based on category"() {
		given:
		ActorTemplate actorTemplate = new ActorTemplate()
		List<CategoryHeader> categoryHeaderList = Lists.newArrayList(createCategoryHeaderList(CategoryName.ANIMAL_PERFORMERS))

		when:
		categoriesActorTemplateEnrichingProcessor.enrich(EnrichablePair.of(categoryHeaderList, actorTemplate))

		then:
		actorTemplate.animalPerformer
		!actorTemplate.disPerformer
		!actorTemplate.ds9Performer
		!actorTemplate.entPerformer
		!actorTemplate.filmPerformer
		!actorTemplate.standInPerformer
		!actorTemplate.stuntPerformer
		!actorTemplate.tasPerformer
		!actorTemplate.tngPerformer
		!actorTemplate.tosPerformer
		!actorTemplate.videoGamePerformer
		!actorTemplate.voicePerformer
		!actorTemplate.voyPerformer
	}

	def "should set disPerformer flag based on category"() {
		given:
		ActorTemplate actorTemplate = new ActorTemplate()
		List<CategoryHeader> categoryHeaderList = Lists.newArrayList(createCategoryHeaderList(CategoryName.DIS_PERFORMERS))

		when:
		categoriesActorTemplateEnrichingProcessor.enrich(EnrichablePair.of(categoryHeaderList, actorTemplate))

		then:
		!actorTemplate.animalPerformer
		actorTemplate.disPerformer
		!actorTemplate.ds9Performer
		!actorTemplate.entPerformer
		!actorTemplate.filmPerformer
		!actorTemplate.standInPerformer
		!actorTemplate.stuntPerformer
		!actorTemplate.tasPerformer
		!actorTemplate.tngPerformer
		!actorTemplate.tosPerformer
		!actorTemplate.videoGamePerformer
		!actorTemplate.voicePerformer
		!actorTemplate.voyPerformer
	}

	def "should set ds9Performer flag based on category"() {
		given:
		ActorTemplate actorTemplate = new ActorTemplate()
		List<CategoryHeader> categoryHeaderList = Lists.newArrayList(createCategoryHeaderList(CategoryName.DS9_PERFORMERS))

		when:
		categoriesActorTemplateEnrichingProcessor.enrich(EnrichablePair.of(categoryHeaderList, actorTemplate))

		then:
		!actorTemplate.animalPerformer
		!actorTemplate.disPerformer
		actorTemplate.ds9Performer
		!actorTemplate.entPerformer
		!actorTemplate.filmPerformer
		!actorTemplate.standInPerformer
		!actorTemplate.stuntPerformer
		!actorTemplate.tasPerformer
		!actorTemplate.tngPerformer
		!actorTemplate.tosPerformer
		!actorTemplate.videoGamePerformer
		!actorTemplate.voicePerformer
		!actorTemplate.voyPerformer
	}

	def "should set entPerformer flag based on category"() {
		given:
		ActorTemplate actorTemplate = new ActorTemplate()
		List<CategoryHeader> categoryHeaderList = Lists.newArrayList(createCategoryHeaderList(CategoryName.ENT_PERFORMERS))

		when:
		categoriesActorTemplateEnrichingProcessor.enrich(EnrichablePair.of(categoryHeaderList, actorTemplate))

		then:
		!actorTemplate.animalPerformer
		!actorTemplate.disPerformer
		!actorTemplate.ds9Performer
		actorTemplate.entPerformer
		!actorTemplate.filmPerformer
		!actorTemplate.standInPerformer
		!actorTemplate.stuntPerformer
		!actorTemplate.tasPerformer
		!actorTemplate.tngPerformer
		!actorTemplate.tosPerformer
		!actorTemplate.videoGamePerformer
		!actorTemplate.voicePerformer
		!actorTemplate.voyPerformer
	}

	def "should set filmPerformer flag based on category"() {
		given:
		ActorTemplate actorTemplate = new ActorTemplate()
		List<CategoryHeader> categoryHeaderList = Lists.newArrayList(createCategoryHeaderList(CategoryName.FILM_PERFORMERS))

		when:
		categoriesActorTemplateEnrichingProcessor.enrich(EnrichablePair.of(categoryHeaderList, actorTemplate))

		then:
		!actorTemplate.animalPerformer
		!actorTemplate.disPerformer
		!actorTemplate.ds9Performer
		!actorTemplate.entPerformer
		actorTemplate.filmPerformer
		!actorTemplate.standInPerformer
		!actorTemplate.stuntPerformer
		!actorTemplate.tasPerformer
		!actorTemplate.tngPerformer
		!actorTemplate.tosPerformer
		!actorTemplate.videoGamePerformer
		!actorTemplate.voicePerformer
		!actorTemplate.voyPerformer
	}

	def "should set standInPerformer flag based on category"() {
		given:
		ActorTemplate actorTemplate = new ActorTemplate()
		List<CategoryHeader> categoryHeaderList = Lists.newArrayList(createCategoryHeaderList(CategoryName.STAND_INS))

		when:
		categoriesActorTemplateEnrichingProcessor.enrich(EnrichablePair.of(categoryHeaderList, actorTemplate))

		then:
		!actorTemplate.animalPerformer
		!actorTemplate.disPerformer
		!actorTemplate.ds9Performer
		!actorTemplate.entPerformer
		!actorTemplate.filmPerformer
		actorTemplate.standInPerformer
		!actorTemplate.stuntPerformer
		!actorTemplate.tasPerformer
		!actorTemplate.tngPerformer
		!actorTemplate.tosPerformer
		!actorTemplate.videoGamePerformer
		!actorTemplate.voicePerformer
		!actorTemplate.voyPerformer
	}

	def "should set stuntPerformer flag based on category"() {
		given:
		ActorTemplate actorTemplate = new ActorTemplate()
		List<CategoryHeader> categoryHeaderList = Lists.newArrayList(createCategoryHeaderList(CategoryName.STUNT_PERFORMERS))

		when:
		categoriesActorTemplateEnrichingProcessor.enrich(EnrichablePair.of(categoryHeaderList, actorTemplate))

		then:
		!actorTemplate.animalPerformer
		!actorTemplate.disPerformer
		!actorTemplate.ds9Performer
		!actorTemplate.entPerformer
		!actorTemplate.filmPerformer
		!actorTemplate.standInPerformer
		actorTemplate.stuntPerformer
		!actorTemplate.tasPerformer
		!actorTemplate.tngPerformer
		!actorTemplate.tosPerformer
		!actorTemplate.videoGamePerformer
		!actorTemplate.voicePerformer
		!actorTemplate.voyPerformer
	}

	def "should set tasPerformer flag based on category"() {
		given:
		ActorTemplate actorTemplate = new ActorTemplate()
		List<CategoryHeader> categoryHeaderList = Lists.newArrayList(createCategoryHeaderList(CategoryName.TAS_PERFORMERS))

		when:
		categoriesActorTemplateEnrichingProcessor.enrich(EnrichablePair.of(categoryHeaderList, actorTemplate))

		then:
		!actorTemplate.animalPerformer
		!actorTemplate.disPerformer
		!actorTemplate.ds9Performer
		!actorTemplate.entPerformer
		!actorTemplate.filmPerformer
		!actorTemplate.standInPerformer
		!actorTemplate.stuntPerformer
		actorTemplate.tasPerformer
		!actorTemplate.tngPerformer
		!actorTemplate.tosPerformer
		!actorTemplate.videoGamePerformer
		!actorTemplate.voicePerformer
		!actorTemplate.voyPerformer
	}

	def "should set tngPerformer flag based on category"() {
		given:
		ActorTemplate actorTemplate = new ActorTemplate()
		List<CategoryHeader> categoryHeaderList = Lists.newArrayList(createCategoryHeaderList(CategoryName.TNG_PERFORMERS))

		when:
		categoriesActorTemplateEnrichingProcessor.enrich(EnrichablePair.of(categoryHeaderList, actorTemplate))

		then:
		!actorTemplate.animalPerformer
		!actorTemplate.disPerformer
		!actorTemplate.ds9Performer
		!actorTemplate.entPerformer
		!actorTemplate.filmPerformer
		!actorTemplate.standInPerformer
		!actorTemplate.stuntPerformer
		!actorTemplate.tasPerformer
		actorTemplate.tngPerformer
		!actorTemplate.tosPerformer
		!actorTemplate.videoGamePerformer
		!actorTemplate.voicePerformer
		!actorTemplate.voyPerformer
	}

	def "should set tosPerformer flag based on category"() {
		given:
		ActorTemplate actorTemplate = new ActorTemplate()
		List<CategoryHeader> categoryHeaderList = Lists.newArrayList(createCategoryHeaderList(CategoryName.TOS_PERFORMERS))

		when:
		categoriesActorTemplateEnrichingProcessor.enrich(EnrichablePair.of(categoryHeaderList, actorTemplate))

		then:
		!actorTemplate.animalPerformer
		!actorTemplate.disPerformer
		!actorTemplate.ds9Performer
		!actorTemplate.entPerformer
		!actorTemplate.filmPerformer
		!actorTemplate.standInPerformer
		!actorTemplate.stuntPerformer
		!actorTemplate.tasPerformer
		!actorTemplate.tngPerformer
		actorTemplate.tosPerformer
		!actorTemplate.videoGamePerformer
		!actorTemplate.voicePerformer
		!actorTemplate.voyPerformer
	}

	def "should set videoGamePerformer flag based on category"() {
		given:
		ActorTemplate actorTemplate = new ActorTemplate()
		List<CategoryHeader> categoryHeaderList = Lists.newArrayList(createCategoryHeaderList(CategoryName.VIDEO_GAME_PERFORMERS))

		when:
		categoriesActorTemplateEnrichingProcessor.enrich(EnrichablePair.of(categoryHeaderList, actorTemplate))

		then:
		!actorTemplate.animalPerformer
		!actorTemplate.disPerformer
		!actorTemplate.ds9Performer
		!actorTemplate.entPerformer
		!actorTemplate.filmPerformer
		!actorTemplate.standInPerformer
		!actorTemplate.stuntPerformer
		!actorTemplate.tasPerformer
		!actorTemplate.tngPerformer
		!actorTemplate.tosPerformer
		actorTemplate.videoGamePerformer
		!actorTemplate.voicePerformer
		!actorTemplate.voyPerformer
	}

	def "should set voicePerformer flag based on category"() {
		given:
		ActorTemplate actorTemplate = new ActorTemplate()
		List<CategoryHeader> categoryHeaderList = Lists.newArrayList(createCategoryHeaderList(CategoryName.VOICE_PERFORMERS))

		when:
		categoriesActorTemplateEnrichingProcessor.enrich(EnrichablePair.of(categoryHeaderList, actorTemplate))

		then:
		!actorTemplate.animalPerformer
		!actorTemplate.disPerformer
		!actorTemplate.ds9Performer
		!actorTemplate.entPerformer
		!actorTemplate.filmPerformer
		!actorTemplate.standInPerformer
		!actorTemplate.stuntPerformer
		!actorTemplate.tasPerformer
		!actorTemplate.tngPerformer
		!actorTemplate.tosPerformer
		!actorTemplate.videoGamePerformer
		actorTemplate.voicePerformer
		!actorTemplate.voyPerformer
	}

	def "should set voyPerformer flag based on category"() {
		given:
		ActorTemplate actorTemplate = new ActorTemplate()
		List<CategoryHeader> categoryHeaderList = Lists.newArrayList(createCategoryHeaderList(CategoryName.VOY_PERFORMERS))

		when:
		categoriesActorTemplateEnrichingProcessor.enrich(EnrichablePair.of(categoryHeaderList, actorTemplate))

		then:
		!actorTemplate.animalPerformer
		!actorTemplate.disPerformer
		!actorTemplate.ds9Performer
		!actorTemplate.entPerformer
		!actorTemplate.filmPerformer
		!actorTemplate.standInPerformer
		!actorTemplate.stuntPerformer
		!actorTemplate.tasPerformer
		!actorTemplate.tngPerformer
		!actorTemplate.tosPerformer
		!actorTemplate.videoGamePerformer
		!actorTemplate.voicePerformer
		actorTemplate.voyPerformer
	}

	private static List<CategoryHeader> createCategoryHeaderList(String title) {
		return Lists.newArrayList(new CategoryHeader(title: title))
	}

}
