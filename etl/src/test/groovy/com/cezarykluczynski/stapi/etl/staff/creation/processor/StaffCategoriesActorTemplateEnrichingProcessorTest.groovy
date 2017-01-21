package com.cezarykluczynski.stapi.etl.staff.creation.processor

import com.cezarykluczynski.stapi.etl.EtlTestUtils
import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair
import com.cezarykluczynski.stapi.etl.template.actor.dto.ActorTemplate
import com.cezarykluczynski.stapi.etl.util.constant.CategoryName
import com.cezarykluczynski.stapi.sources.mediawiki.dto.CategoryHeader
import com.cezarykluczynski.stapi.util.ReflectionTestUtils
import com.google.common.collect.Lists
import spock.lang.Specification
import spock.lang.Unroll

class StaffCategoriesActorTemplateEnrichingProcessorTest extends Specification {

	private StaffCategoriesActorTemplateEnrichingProcessor staffCategoriesActorTemplateEnrichingProcessor

	void setup() {
		staffCategoriesActorTemplateEnrichingProcessor = new StaffCategoriesActorTemplateEnrichingProcessor()
	}

	@Unroll('set #flagName flag when #categoryHeaderList is passed')
	void "set flagName when categoryHeaderList is passed"() {
		expect:
		staffCategoriesActorTemplateEnrichingProcessor.enrich(EnrichablePair.of(categoryHeaderList, actorTemplate))
		flag == actorTemplate[flagName]
		trueBooleans == ReflectionTestUtils.getNumberOfTrueBooleanFields(actorTemplate)

		where:
		categoryHeaderList                                           | actorTemplate       | flagName                         | flag  | trueBooleans
		Lists.newArrayList()                                         | new ActorTemplate() | 'castingDepartment'              | false | 0
		createList(CategoryName.CASTING_DEPARTMENT)                  | new ActorTemplate() | 'castingDepartment'              | true  | 1
		createList(CategoryName.EXHIBIT_AND_ATTRACTION_STAFF)        | new ActorTemplate() | 'exhibitAndAttractionStaff'      | true  | 1
		createList(CategoryName.FILM_EDITORS)                        | new ActorTemplate() | 'filmEditor'                     | true  | 1
		createList(CategoryName.LINGUISTS)                           | new ActorTemplate() | 'linguist'                       | true  | 1
		createList(CategoryName.LOCATION_STAFF)                      | new ActorTemplate() | 'locationStaff'                  | true  | 1
		createList(CategoryName.MAKEUP_STAFF)                        | new ActorTemplate() | 'makeupStaff'                    | true  | 1
		createList(CategoryName.PERSONAL_ASSISTANTS)                 | new ActorTemplate() | 'personalAssistant'              | true  | 1
		createList(CategoryName.PRODUCERS)                           | new ActorTemplate() | 'producer'                       | true  | 1
		createList(CategoryName.PRODUCTION_ASSOCIATES)               | new ActorTemplate() | 'productionAssociate'            | true  | 1
		createList(CategoryName.PRODUCTION_STAFF)                    | new ActorTemplate() | 'productionStaff'                | true  | 1
		createList(CategoryName.PUBLICATION_STAFF)                   | new ActorTemplate() | 'publicationStaff'               | true  | 1
		createList(CategoryName.SCIENCE_CONSULTANTS)                 | new ActorTemplate() | 'scienceConsultant'              | true  | 1
		createList(CategoryName.SOUND_DEPARTMENT)                    | new ActorTemplate() | 'soundDepartment'                | true  | 1
		createList(CategoryName.STAR_TREK_AUTHORS)                   | new ActorTemplate() | 'author'                         | true  | 1
		createList(CategoryName.STAR_TREK_AUDIO_AUTHORS)             | new ActorTemplate() | 'audioAuthor'                    | true  | 2
		createList(CategoryName.STAR_TREK_AUDIO_AUTHORS)             | new ActorTemplate() | 'author'                         | true  | 2
		createList(CategoryName.STAR_TREK_CALENDAR_ARTISTS)          | new ActorTemplate() | 'calendarArtist'                 | true  | 1
		createList(CategoryName.STAR_TREK_GAME_ARTISTS)              | new ActorTemplate() | 'gameArtist'                     | true  | 1
		createList(CategoryName.STAR_TREK_GAME_AUTHORS)              | new ActorTemplate() | 'gameAuthor'                     | true  | 2
		createList(CategoryName.STAR_TREK_GAME_AUTHORS)              | new ActorTemplate() | 'author'                         | true  | 2
		createList(CategoryName.STAR_TREK_NOVEL_ARTISTS)             | new ActorTemplate() | 'novelArtist'                    | true  | 1
		createList(CategoryName.STAR_TREK_NOVEL_AUTHORS)             | new ActorTemplate() | 'novelAuthor'                    | true  | 2
		createList(CategoryName.STAR_TREK_NOVEL_AUTHORS)             | new ActorTemplate() | 'author'                         | true  | 2
		createList(CategoryName.STAR_TREK_REFERENCE_ARTISTS)         | new ActorTemplate() | 'referenceArtist'                | true  | 1
		createList(CategoryName.STAR_TREK_REFERENCE_AUTHORS)         | new ActorTemplate() | 'referenceAuthor'                | true  | 2
		createList(CategoryName.STAR_TREK_REFERENCE_AUTHORS)         | new ActorTemplate() | 'author'                         | true  | 2
		createList(CategoryName.STAR_TREK_PUBLICATION_ARTISTS)       | new ActorTemplate() | 'publicationArtist'              | true  | 1
		createList(CategoryName.STAR_TREK_PUBLICATION_DESIGNERS)     | new ActorTemplate() | 'publicationDesigner'            | true  | 1
		createList(CategoryName.STAR_TREK_PUBLICATION_EDITORS)       | new ActorTemplate() | 'publicationEditor'              | true  | 1
		createList(CategoryName.STAR_TREK_PUBLICITY_ARTISTS)         | new ActorTemplate() | 'publicityArtist'                | true  | 1
		createList(CategoryName.SPECIAL_FEATURES_STAFF)              | new ActorTemplate() | 'specialFeaturesStaff'           | true  | 1
		createList(CategoryName.STORY_EDITORS)                       | new ActorTemplate() | 'storyEditor'                    | true  | 1
		createList(CategoryName.STUDIO_EXECUTIVES)                   | new ActorTemplate() | 'studioExecutive'                | true  | 1
		createList(CategoryName.STUNT_DEPARTMENT)                    | new ActorTemplate() | 'stuntDepartment'                | true  | 1
		createList(CategoryName.TRANSPORTATION_DEPARTMENT)           | new ActorTemplate() | 'transportationDepartment'       | true  | 1
		createList(CategoryName.VIDEO_GAME_PRODUCTION_STAFF)         | new ActorTemplate() | 'videoGameProductionStaff'       | true  | 1
		createList(CategoryName.WRITERS)                             | new ActorTemplate() | 'writer'                         | true  | 1
		createList(CategoryName.ART_DIRECTORS)                       | new ActorTemplate() | 'artDirector'                    | true  | 2
		createList(CategoryName.ART_DIRECTORS)                       | new ActorTemplate() | 'artDepartment'                  | true  | 2
		createList(CategoryName.PRODUCTION_DESIGNERS)                | new ActorTemplate() | 'productionDesigner'             | true  | 2
		createList(CategoryName.PRODUCTION_DESIGNERS)                | new ActorTemplate() | 'artDepartment'                  | true  | 2
		createList(CategoryName.ART_DEPARTMENT)                      | new ActorTemplate() | 'artDepartment'                  | true  | 1
		createList(CategoryName.COSTUME_DESIGNERS)                   | new ActorTemplate() | 'costumeDesigner'                | true  | 2
		createList(CategoryName.COSTUME_DESIGNERS)                   | new ActorTemplate() | 'costumeDepartment'              | true  | 2
		createList(CategoryName.COSTUME_DEPARTMENT)                  | new ActorTemplate() | 'costumeDepartment'              | true  | 1
		createList(CategoryName.ASSISTANT_AND_SECOND_UNIT_DIRECTORS) | new ActorTemplate() | 'assistantAndSecondUnitDirector' | true  | 1
		createList(CategoryName.DIRECTORS)                           | new ActorTemplate() | 'assistantAndSecondUnitDirector' | true  | 2
		createList(CategoryName.DIRECTORS)                           | new ActorTemplate() | 'director'                       | true  | 2
		createList(CategoryName.COMPOSERS)                           | new ActorTemplate() | 'composer'                       | true  | 2
		createList(CategoryName.COMPOSERS)                           | new ActorTemplate() | 'musicDepartment'                | true  | 2
		createList(CategoryName.MUSIC_DEPARTMENT)                    | new ActorTemplate() | 'musicDepartment'                | true  | 1
		createList(CategoryName.CINEMATOGRAPHERS)                    | new ActorTemplate() | 'cinematographer'                | true  | 2
		createList(CategoryName.CINEMATOGRAPHERS)                    | new ActorTemplate() | 'cinematographer'                | true  | 2
		createList(CategoryName.CAMERA_AND_ELECTRICAL_DEPARTMENT)    | new ActorTemplate() | 'cameraAndElectricalDepartment'  | true  | 1
		createList(CategoryName.STAR_TREK_COMIC_AUTHORS)             | new ActorTemplate() | 'comicAuthor'                    | true  | 2
		createList(CategoryName.STAR_TREK_COMIC_AUTHORS)             | new ActorTemplate() | 'author'                         | true  | 2
		createList(CategoryName.STAR_TREK_COMIC_COLOR_ARTISTS)       | new ActorTemplate() | 'comicColorArtist'               | true  | 2
		createList(CategoryName.STAR_TREK_COMIC_COLOR_ARTISTS)       | new ActorTemplate() | 'comicArtist'                    | true  | 2
		createList(CategoryName.STAR_TREK_COMIC_INTERIOR_ARTISTS)    | new ActorTemplate() | 'comicInteriorArtist'            | true  | 2
		createList(CategoryName.STAR_TREK_COMIC_INTERIOR_ARTISTS)    | new ActorTemplate() | 'comicArtist'                    | true  | 2
		createList(CategoryName.STAR_TREK_COMIC_INK_ARTISTS)         | new ActorTemplate() | 'comicInkArtist'                 | true  | 2
		createList(CategoryName.STAR_TREK_COMIC_INK_ARTISTS)         | new ActorTemplate() | 'comicArtist'                    | true  | 2
		createList(CategoryName.STAR_TREK_COMIC_PENCIL_ARTISTS)      | new ActorTemplate() | 'comicPencilArtist'              | true  | 2
		createList(CategoryName.STAR_TREK_COMIC_PENCIL_ARTISTS)      | new ActorTemplate() | 'comicArtist'                    | true  | 2
		createList(CategoryName.STAR_TREK_COMIC_LETTER_ARTISTS)      | new ActorTemplate() | 'comicLetterArtist'              | true  | 2
		createList(CategoryName.STAR_TREK_COMIC_LETTER_ARTISTS)      | new ActorTemplate() | 'comicArtist'                    | true  | 2
		createList(CategoryName.STAR_TREK_COMIC_STRIP_ARTISTS)       | new ActorTemplate() | 'comicStripArtist'               | true  | 2
		createList(CategoryName.STAR_TREK_COMIC_STRIP_ARTISTS)       | new ActorTemplate() | 'comicArtist'                    | true  | 2
		createList(CategoryName.STAR_TREK_COMIC_ARTISTS)             | new ActorTemplate() | 'comicArtist'                    | true  | 1
		createList(CategoryName.CBS_DIGITAL_STAFF)                   | new ActorTemplate() | 'cbsDigitalStaff'                | true  | 2
		createList(CategoryName.CBS_DIGITAL_STAFF)                   | new ActorTemplate() | 'specialAndVisualEffectsStaff'   | true  | 2
		createList(CategoryName.ILM_PRODUCTION_STAFF)                | new ActorTemplate() | 'ilmProductionStaff'             | true  | 2
		createList(CategoryName.ILM_PRODUCTION_STAFF)                | new ActorTemplate() | 'specialAndVisualEffectsStaff'   | true  | 2
		createList(CategoryName.SPECIAL_AND_VISUAL_EFFECTS_STAFF)    | new ActorTemplate() | 'specialAndVisualEffectsStaff'   | true  | 1
	}

	private static List<CategoryHeader> createList(String title) {
		Lists.newArrayList(EtlTestUtils.createCategoryHeaderList(title))
	}

}
