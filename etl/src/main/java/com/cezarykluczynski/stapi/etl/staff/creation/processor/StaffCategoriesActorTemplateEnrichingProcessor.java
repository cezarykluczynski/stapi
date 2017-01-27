package com.cezarykluczynski.stapi.etl.staff.creation.processor;

import com.cezarykluczynski.stapi.etl.common.dto.EnrichablePair;
import com.cezarykluczynski.stapi.etl.common.processor.CategoryTitlesExtractingProcessor;
import com.cezarykluczynski.stapi.etl.performer.creation.processor.CategoriesActorTemplateEnrichingProcessor;
import com.cezarykluczynski.stapi.etl.template.actor.dto.ActorTemplate;
import com.cezarykluczynski.stapi.etl.util.constant.CategoryName;
import com.cezarykluczynski.stapi.sources.mediawiki.dto.CategoryHeader;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class StaffCategoriesActorTemplateEnrichingProcessor implements CategoriesActorTemplateEnrichingProcessor {

	private CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessor;

	@Inject
	public StaffCategoriesActorTemplateEnrichingProcessor(CategoryTitlesExtractingProcessor categoryTitlesExtractingProcessor) {
		this.categoryTitlesExtractingProcessor = categoryTitlesExtractingProcessor;
	}

	@Override
	public void enrich(EnrichablePair<List<CategoryHeader>, ActorTemplate> enrichablePair) throws Exception {
		List<String> categoryTitlesList = categoryTitlesExtractingProcessor.process(enrichablePair.getInput());
		ActorTemplate actorTemplate = enrichablePair.getOutput();

		actorTemplate.setCastingDepartment(categoryTitlesList.contains(CategoryName.CASTING_DEPARTMENT));
		actorTemplate.setExhibitAndAttractionStaff(categoryTitlesList.contains(CategoryName.EXHIBIT_AND_ATTRACTION_STAFF));
		actorTemplate.setFilmEditor(categoryTitlesList.contains(CategoryName.FILM_EDITORS));
		actorTemplate.setLinguist(categoryTitlesList.contains(CategoryName.LINGUISTS));
		actorTemplate.setLocationStaff(categoryTitlesList.contains(CategoryName.LOCATION_STAFF));
		actorTemplate.setMakeupStaff(categoryTitlesList.contains(CategoryName.MAKEUP_STAFF));
		actorTemplate.setPersonalAssistant(categoryTitlesList.contains(CategoryName.PERSONAL_ASSISTANTS));
		actorTemplate.setProducer(categoryTitlesList.contains(CategoryName.PRODUCERS));
		actorTemplate.setProductionAssociate(categoryTitlesList.contains(CategoryName.PRODUCTION_ASSOCIATES));
		actorTemplate.setProductionStaff(categoryTitlesList.contains(CategoryName.PRODUCTION_STAFF));
		actorTemplate.setPublicationStaff(categoryTitlesList.contains(CategoryName.PUBLICATION_STAFF));
		actorTemplate.setScienceConsultant(categoryTitlesList.contains(CategoryName.SCIENCE_CONSULTANTS));
		actorTemplate.setSoundDepartment(categoryTitlesList.contains(CategoryName.SOUND_DEPARTMENT));
		actorTemplate.setAudioAuthor(categoryTitlesList.contains(CategoryName.STAR_TREK_AUDIO_AUTHORS));
		actorTemplate.setCalendarArtist(categoryTitlesList.contains(CategoryName.STAR_TREK_CALENDAR_ARTISTS));
		actorTemplate.setGameArtist(categoryTitlesList.contains(CategoryName.STAR_TREK_GAME_ARTISTS));
		actorTemplate.setGameAuthor(categoryTitlesList.contains(CategoryName.STAR_TREK_GAME_AUTHORS));
		actorTemplate.setNovelArtist(categoryTitlesList.contains(CategoryName.STAR_TREK_NOVEL_ARTISTS));
		actorTemplate.setNovelAuthor(categoryTitlesList.contains(CategoryName.STAR_TREK_NOVEL_AUTHORS));
		actorTemplate.setReferenceArtist(categoryTitlesList.contains(CategoryName.STAR_TREK_REFERENCE_ARTISTS));
		actorTemplate.setReferenceAuthor(categoryTitlesList.contains(CategoryName.STAR_TREK_REFERENCE_AUTHORS));
		actorTemplate.setPublicationArtist(categoryTitlesList.contains(CategoryName.STAR_TREK_PUBLICATION_ARTISTS));
		actorTemplate.setPublicationDesigner(categoryTitlesList.contains(CategoryName.STAR_TREK_PUBLICATION_DESIGNERS));
		actorTemplate.setPublicationEditor(categoryTitlesList.contains(CategoryName.STAR_TREK_PUBLICATION_EDITORS));
		actorTemplate.setPublicityArtist(categoryTitlesList.contains(CategoryName.STAR_TREK_PUBLICITY_ARTISTS));
		actorTemplate.setSpecialFeaturesStaff(categoryTitlesList.contains(CategoryName.SPECIAL_FEATURES_STAFF));
		actorTemplate.setStoryEditor(categoryTitlesList.contains(CategoryName.STORY_EDITORS));
		actorTemplate.setStudioExecutive(categoryTitlesList.contains(CategoryName.STUDIO_EXECUTIVES));
		actorTemplate.setStuntDepartment(categoryTitlesList.contains(CategoryName.STUNT_DEPARTMENT));
		actorTemplate.setTransportationDepartment(categoryTitlesList.contains(CategoryName.TRANSPORTATION_DEPARTMENT));
		actorTemplate.setVideoGameProductionStaff(categoryTitlesList.contains(CategoryName.VIDEO_GAME_PRODUCTION_STAFF));
		actorTemplate.setWriter(categoryTitlesList.contains(CategoryName.WRITERS));

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
		actorTemplate.setAuthor(categoryTitlesList.contains(CategoryName.STAR_TREK_AUTHORS) || actorTemplate.isAudioAuthor()
				|| actorTemplate.isComicAuthor() || actorTemplate.isGameAuthor() || actorTemplate.isNovelAuthor()
				|| actorTemplate.isReferenceAuthor());
	}

	private void setArtFlags(ActorTemplate actorTemplate, List<String> categoryTitlesList) {
		actorTemplate.setArtDirector(categoryTitlesList.contains(CategoryName.ART_DIRECTORS));
		actorTemplate.setProductionDesigner(categoryTitlesList.contains(CategoryName.PRODUCTION_DESIGNERS));
		actorTemplate.setArtDepartment(categoryTitlesList.contains(CategoryName.ART_DEPARTMENT) || actorTemplate.isArtDirector()
				|| actorTemplate.isProductionDesigner());
	}

	private void setCostumeFlags(ActorTemplate actorTemplate, List<String> categoryTitlesList) {
		actorTemplate.setCostumeDesigner(categoryTitlesList.contains(CategoryName.COSTUME_DESIGNERS));
		actorTemplate.setCostumeDepartment(categoryTitlesList.contains(CategoryName.COSTUME_DEPARTMENT) || actorTemplate.isCostumeDesigner());
	}

	private void setDirectorsFlags(ActorTemplate actorTemplate, List<String> categoryTitlesList) {
		actorTemplate.setDirector(categoryTitlesList.contains(CategoryName.DIRECTORS));
		actorTemplate.setAssistantAndSecondUnitDirector(categoryTitlesList.contains(CategoryName.ASSISTANT_AND_SECOND_UNIT_DIRECTORS)
				|| actorTemplate.isDirector());
	}

	private void setMusicFlags(ActorTemplate actorTemplate, List<String> categoryTitlesList) {
		actorTemplate.setComposer(categoryTitlesList.contains(CategoryName.COMPOSERS));
		actorTemplate.setMusicDepartment(categoryTitlesList.contains(CategoryName.MUSIC_DEPARTMENT) || actorTemplate.isComposer());
	}

	private void setCameraFlags(ActorTemplate actorTemplate, List<String> categoryTitlesList) {
		actorTemplate.setCinematographer(categoryTitlesList.contains(CategoryName.CINEMATOGRAPHERS));
		actorTemplate.setCameraAndElectricalDepartment(categoryTitlesList.contains(CategoryName.CAMERA_AND_ELECTRICAL_DEPARTMENT)
				|| actorTemplate.isCinematographer());
	}

	private void setComicsFlags(ActorTemplate actorTemplate, List<String> categoryTitlesList) {
		actorTemplate.setComicAuthor(categoryTitlesList.contains(CategoryName.STAR_TREK_COMIC_AUTHORS));
		actorTemplate.setComicColorArtist(categoryTitlesList.contains(CategoryName.STAR_TREK_COMIC_COLOR_ARTISTS));
		actorTemplate.setComicInteriorArtist(categoryTitlesList.contains(CategoryName.STAR_TREK_COMIC_INTERIOR_ARTISTS));
		actorTemplate.setComicInkArtist(categoryTitlesList.contains(CategoryName.STAR_TREK_COMIC_INK_ARTISTS));
		actorTemplate.setComicPencilArtist(categoryTitlesList.contains(CategoryName.STAR_TREK_COMIC_PENCIL_ARTISTS));
		actorTemplate.setComicLetterArtist(categoryTitlesList.contains(CategoryName.STAR_TREK_COMIC_LETTER_ARTISTS));
		actorTemplate.setComicStripArtist(categoryTitlesList.contains(CategoryName.STAR_TREK_COMIC_STRIP_ARTISTS));
		actorTemplate.setComicArtist(categoryTitlesList.contains(CategoryName.STAR_TREK_COMIC_ARTISTS) || isComicActorTemplate(actorTemplate));
	}

	private boolean isComicActorTemplate(ActorTemplate actorTemplate) {
		return actorTemplate.isComicColorArtist() || actorTemplate.isComicInteriorArtist() || actorTemplate.isComicInkArtist()
				|| actorTemplate.isComicPencilArtist() || actorTemplate.isComicLetterArtist() || actorTemplate.isComicStripArtist();
	}

	private void setSpecialEffectsFlags(ActorTemplate actorTemplate, List<String> categoryTitlesList) {
		actorTemplate.setCbsDigitalStaff(categoryTitlesList.contains(CategoryName.CBS_DIGITAL_STAFF));
		actorTemplate.setIlmProductionStaff(categoryTitlesList.contains(CategoryName.ILM_PRODUCTION_STAFF));
		actorTemplate.setSpecialAndVisualEffectsStaff(categoryTitlesList.contains(CategoryName.SPECIAL_AND_VISUAL_EFFECTS_STAFF)
				|| actorTemplate.isCbsDigitalStaff() || actorTemplate.isIlmProductionStaff());
	}

}
