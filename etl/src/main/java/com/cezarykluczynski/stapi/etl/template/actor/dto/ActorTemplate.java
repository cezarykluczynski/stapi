package com.cezarykluczynski.stapi.etl.template.actor.dto;

import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.DateRange;
import com.cezarykluczynski.stapi.etl.template.common.dto.enums.Gender;
import com.cezarykluczynski.stapi.model.page.entity.Page;
import lombok.Data;

@Data
public class ActorTemplate {

	private String name;

	private Page page;

	private String birthName;

	private String placeOfBirth;

	private String placeOfDeath;

	private Gender gender;

	private DateRange lifeRange;

	private boolean animalPerformer;

	private boolean disPerformer;

	private boolean ds9Performer;

	private boolean entPerformer;

	private boolean filmPerformer;

	private boolean standInPerformer;

	private boolean stuntPerformer;

	private boolean tasPerformer;

	private boolean tngPerformer;

	private boolean tosPerformer;

	private boolean videoGamePerformer;

	private boolean voicePerformer;

	private boolean voyPerformer;

	private boolean artDepartment;

	private boolean artDirector;

	private boolean productionDesigner;

	private boolean cameraAndElectricalDepartment;

	private boolean cinematographer;

	private boolean castingDepartment;

	private boolean costumeDepartment;

	private boolean costumeDesigner;

	private boolean director;

	private boolean assistantOrSecondUnitDirector;

	private boolean exhibitAndAttractionStaff;

	private boolean filmEditor;

	private boolean linguist;

	private boolean locationStaff;

	private boolean makeupStaff;

	private boolean musicDepartment;

	private boolean composer;

	private boolean personalAssistant;

	private boolean producer;

	private boolean productionAssociate;

	private boolean productionStaff;

	private boolean publicationStaff;

	private boolean scienceConsultant;

	private boolean soundDepartment;

	private boolean specialAndVisualEffectsStaff;

	private boolean author;

	private boolean audioAuthor;

	private boolean calendarArtist;

	private boolean comicArtist;

	private boolean comicAuthor;

	private boolean comicColorArtist;

	private boolean comicInteriorArtist;

	private boolean comicInkArtist;

	private boolean comicPencilArtist;

	private boolean comicLetterArtist;

	private boolean comicStripArtist;

	private boolean gameArtist;

	private boolean gameAuthor;

	private boolean novelArtist;

	private boolean novelAuthor;

	private boolean referenceArtist;

	private boolean referenceAuthor;

	private boolean publicationArtist;

	private boolean publicationDesigner;

	private boolean publicationEditor;

	private boolean publicityArtist;

	private boolean cbsDigitalStaff;

	private boolean ilmProductionStaff;

	private boolean specialFeaturesStaff;

	private boolean storyEditor;

	private boolean studioExecutive;

	private boolean stuntDepartment;

	private boolean transportationDepartment;

	private boolean videoGameProductionStaff;

	private boolean writer;

}
