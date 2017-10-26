package com.cezarykluczynski.stapi.etl.staff.creation.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.CategoryTitlesExtractingProcessor;
import com.cezarykluczynski.stapi.etl.performer.creation.processor.CategoriesActorTemplateEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.actor.dto.ActorTemplate;
import com.cezarykluczynski.stapi.etl.util.constant.CategoryTitle;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.CategoryHeader;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StaffCategoriesActorTemplateEnrichingProcessor implements CategoriesActorTemplateEnrichingProcessor {

	private final CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessor;

	public StaffCategoriesActorTemplateEnrichingProcessor(CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessor) {
		this.categoryTitlesExtractingProcessor = categoryTitlesExtractingProcessor;
	}

	@Override
	public void enrich(EnrichablePair<List<CategoryHeader>, ActorTemplate> enrichablePair) throws Exception {
		List<String> categoryTitlesList = categoryTitlesExtractingProcessor.process(enrichablePair.getInput());
		ActorTemplate actorTemplate = enrichablePair.getOutput();

		actorTemplate.setCastingDepartment(categoryTitlesList.contains(CategoryTitle.CASTING_DEPARTMENT));
		actorTemplate.setExhibitAndAttractionStaff(categoryTitlesList.contains(CategoryTitle.EXHIBIT_AND_ATTRACTION_STAFF));
		actorTemplate.setFilmEditor(categoryTitlesList.contains(CategoryTitle.FILM_EDITORS));
		actorTemplate.setLinguist(categoryTitlesList.contains(CategoryTitle.LINGUISTS));
		actorTemplate.setLocationStaff(categoryTitlesList.contains(CategoryTitle.LOCATION_STAFF));
		actorTemplate.setMakeupStaff(categoryTitlesList.contains(CategoryTitle.MAKEUP_STAFF));
		actorTemplate.setPersonalAssistant(categoryTitlesList.contains(CategoryTitle.PERSONAL_ASSISTANTS));
		actorTemplate.setProducer(categoryTitlesList.contains(CategoryTitle.PRODUCERS));
		actorTemplate.setProductionAssociate(categoryTitlesList.contains(CategoryTitle.PRODUCTION_ASSOCIATES));
		actorTemplate.setProductionStaff(categoryTitlesList.contains(CategoryTitle.PRODUCTION_STAFF));
		actorTemplate.setPublicationStaff(categoryTitlesList.contains(CategoryTitle.PUBLICATION_STAFF));
		actorTemplate.setScienceConsultant(categoryTitlesList.contains(CategoryTitle.SCIENCE_CONSULTANTS));
		actorTemplate.setSoundDepartment(categoryTitlesList.contains(CategoryTitle.SOUND_DEPARTMENT));
		actorTemplate.setAudioAuthor(categoryTitlesList.contains(CategoryTitle.STAR_TREK_AUDIO_AUTHORS));
		actorTemplate.setCalendarArtist(categoryTitlesList.contains(CategoryTitle.STAR_TREK_CALENDAR_ARTISTS));
		actorTemplate.setGameArtist(categoryTitlesList.contains(CategoryTitle.STAR_TREK_GAME_ARTISTS));
		actorTemplate.setGameAuthor(categoryTitlesList.contains(CategoryTitle.STAR_TREK_GAME_AUTHORS));
		actorTemplate.setNovelArtist(categoryTitlesList.contains(CategoryTitle.STAR_TREK_NOVEL_ARTISTS));
		actorTemplate.setNovelAuthor(categoryTitlesList.contains(CategoryTitle.STAR_TREK_NOVEL_AUTHORS));
		actorTemplate.setReferenceArtist(categoryTitlesList.contains(CategoryTitle.STAR_TREK_REFERENCE_ARTISTS));
		actorTemplate.setReferenceAuthor(categoryTitlesList.contains(CategoryTitle.STAR_TREK_REFERENCE_AUTHORS));
		actorTemplate.setPublicationArtist(categoryTitlesList.contains(CategoryTitle.STAR_TREK_PUBLICATION_ARTISTS));
		actorTemplate.setPublicationDesigner(categoryTitlesList.contains(CategoryTitle.STAR_TREK_PUBLICATION_DESIGNERS));
		actorTemplate.setPublicationEditor(categoryTitlesList.contains(CategoryTitle.STAR_TREK_PUBLICATION_EDITORS));
		actorTemplate.setPublicityArtist(categoryTitlesList.contains(CategoryTitle.STAR_TREK_PUBLICITY_ARTISTS));
		actorTemplate.setSpecialFeaturesStaff(categoryTitlesList.contains(CategoryTitle.SPECIAL_FEATURES_STAFF));
		actorTemplate.setStoryEditor(categoryTitlesList.contains(CategoryTitle.STORY_EDITORS));
		actorTemplate.setStudioExecutive(categoryTitlesList.contains(CategoryTitle.STUDIO_EXECUTIVES));
		actorTemplate.setStuntDepartment(categoryTitlesList.contains(CategoryTitle.STUNT_DEPARTMENT));
		actorTemplate.setTransportationDepartment(categoryTitlesList.contains(CategoryTitle.TRANSPORTATION_DEPARTMENT));
		actorTemplate.setVideoGameProductionStaff(categoryTitlesList.contains(CategoryTitle.VIDEO_GAME_PRODUCTION_STAFF));
		actorTemplate.setWriter(categoryTitlesList.contains(CategoryTitle.WRITERS));

		setArtFlags(actorTemplate, categoryTitlesList);
		setCostumeFlags(actorTemplate, categoryTitlesList);
		setDirectorsFlags(actorTemplate, categoryTitlesList);
		setComicsFlags(actorTemplate, categoryTitlesList);
		setMusicFlags(actorTemplate, categoryTitlesList);
		setSpecialEffectsFlags(actorTemplate, categoryTitlesList);
		setCameraFlags(actorTemplate, categoryTitlesList);
		setAuthorFlags(actorTemplate, categoryTitlesList);
	}

	private void setAuthorFlags(ActorTemplate actorTemplate, List<String> categoryTitlesList) {
		actorTemplate.setAuthor(categoryTitlesList.contains(CategoryTitle.STAR_TREK_AUTHORS) || actorTemplate.isAudioAuthor()
				|| actorTemplate.isComicAuthor() || actorTemplate.isGameAuthor() || actorTemplate.isNovelAuthor()
				|| actorTemplate.isReferenceAuthor());
	}

	private void setArtFlags(ActorTemplate actorTemplate, List<String> categoryTitlesList) {
		actorTemplate.setArtDirector(categoryTitlesList.contains(CategoryTitle.ART_DIRECTORS));
		actorTemplate.setProductionDesigner(categoryTitlesList.contains(CategoryTitle.PRODUCTION_DESIGNERS));
		actorTemplate.setArtDepartment(categoryTitlesList.contains(CategoryTitle.ART_DEPARTMENT) || actorTemplate.isArtDirector()
				|| actorTemplate.isProductionDesigner());
	}

	private void setCostumeFlags(ActorTemplate actorTemplate, List<String> categoryTitlesList) {
		actorTemplate.setCostumeDesigner(categoryTitlesList.contains(CategoryTitle.COSTUME_DESIGNERS));
		actorTemplate.setCostumeDepartment(categoryTitlesList.contains(CategoryTitle.COSTUME_DEPARTMENT) || actorTemplate.isCostumeDesigner());
	}

	private void setDirectorsFlags(ActorTemplate actorTemplate, List<String> categoryTitlesList) {
		actorTemplate.setDirector(categoryTitlesList.contains(CategoryTitle.DIRECTORS));
		actorTemplate.setAssistantOrSecondUnitDirector(categoryTitlesList.contains(CategoryTitle.ASSISTANT_AND_SECOND_UNIT_DIRECTORS)
				|| actorTemplate.isDirector());
	}

	private void setMusicFlags(ActorTemplate actorTemplate, List<String> categoryTitlesList) {
		actorTemplate.setComposer(categoryTitlesList.contains(CategoryTitle.COMPOSERS));
		actorTemplate.setMusicDepartment(categoryTitlesList.contains(CategoryTitle.MUSIC_DEPARTMENT) || actorTemplate.isComposer());
	}

	private void setCameraFlags(ActorTemplate actorTemplate, List<String> categoryTitlesList) {
		actorTemplate.setCinematographer(categoryTitlesList.contains(CategoryTitle.CINEMATOGRAPHERS));
		actorTemplate.setCameraAndElectricalDepartment(categoryTitlesList.contains(CategoryTitle.CAMERA_AND_ELECTRICAL_DEPARTMENT)
				|| actorTemplate.isCinematographer());
	}

	private void setComicsFlags(ActorTemplate actorTemplate, List<String> categoryTitlesList) {
		actorTemplate.setComicAuthor(categoryTitlesList.contains(CategoryTitle.STAR_TREK_COMIC_AUTHORS));
		actorTemplate.setComicColorArtist(categoryTitlesList.contains(CategoryTitle.STAR_TREK_COMIC_COLOR_ARTISTS));
		actorTemplate.setComicInteriorArtist(categoryTitlesList.contains(CategoryTitle.STAR_TREK_COMIC_INTERIOR_ARTISTS));
		actorTemplate.setComicInkArtist(categoryTitlesList.contains(CategoryTitle.STAR_TREK_COMIC_INK_ARTISTS));
		actorTemplate.setComicPencilArtist(categoryTitlesList.contains(CategoryTitle.STAR_TREK_COMIC_PENCIL_ARTISTS));
		actorTemplate.setComicLetterArtist(categoryTitlesList.contains(CategoryTitle.STAR_TREK_COMIC_LETTER_ARTISTS));
		actorTemplate.setComicStripArtist(categoryTitlesList.contains(CategoryTitle.STAR_TREK_COMIC_STRIP_ARTISTS));
		actorTemplate.setComicArtist(categoryTitlesList.contains(CategoryTitle.STAR_TREK_COMIC_ARTISTS) || isComicActorTemplate(actorTemplate));
	}

	private boolean isComicActorTemplate(ActorTemplate actorTemplate) {
		return actorTemplate.isComicColorArtist() || actorTemplate.isComicInteriorArtist() || actorTemplate.isComicInkArtist()
				|| actorTemplate.isComicPencilArtist() || actorTemplate.isComicLetterArtist() || actorTemplate.isComicStripArtist();
	}

	private void setSpecialEffectsFlags(ActorTemplate actorTemplate, List<String> categoryTitlesList) {
		actorTemplate.setCbsDigitalStaff(categoryTitlesList.contains(CategoryTitle.CBS_DIGITAL_STAFF));
		actorTemplate.setIlmProductionStaff(categoryTitlesList.contains(CategoryTitle.ILM_PRODUCTION_STAFF));
		actorTemplate.setSpecialAndVisualEffectsStaff(categoryTitlesList.contains(CategoryTitle.SPECIAL_AND_VISUAL_EFFECTS_STAFF)
				|| actorTemplate.isCbsDigitalStaff() || actorTemplate.isIlmProductionStaff());
	}

}
