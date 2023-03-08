package com.cezarykluczynski.stapi.model.staff.dto;

import com.cezarykluczynski.stapi.model.common.dto.RequestSortDTO;
import com.cezarykluczynski.stapi.model.common.entity.enums.Gender;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Data
@EqualsAndHashCode
public class StaffRequestDTO {

	private String uid;

	private String name;

	private String birthName;

	private Gender gender;

	private LocalDate dateOfBirthFrom;

	private LocalDate dateOfBirthTo;

	private LocalDate dateOfDeathFrom;

	private LocalDate dateOfDeathTo;

	private String placeOfBirth;

	private String placeOfDeath;

	private Boolean artDepartment;

	private Boolean artDirector;

	private Boolean productionDesigner;

	private Boolean cameraAndElectricalDepartment;

	private Boolean cinematographer;

	private Boolean castingDepartment;

	private Boolean costumeDepartment;

	private Boolean costumeDesigner;

	private Boolean director;

	private Boolean assistantOrSecondUnitDirector;

	private Boolean exhibitAndAttractionStaff;

	private Boolean filmEditor;

	private Boolean filmationProductionStaff;

	private Boolean linguist;

	private Boolean locationStaff;

	private Boolean makeupStaff;

	private Boolean merchandiseStaff;

	private Boolean musicDepartment;

	private Boolean composer;

	private Boolean personalAssistant;

	private Boolean producer;

	private Boolean productionAssociate;

	private Boolean productionStaff;

	private Boolean publicationStaff;

	private Boolean scienceConsultant;

	private Boolean soundDepartment;

	private Boolean specialAndVisualEffectsStaff;

	private Boolean author;

	private Boolean audioAuthor;

	private Boolean calendarArtist;

	private Boolean comicArtist;

	private Boolean comicAuthor;

	private Boolean comicColorArtist;

	private Boolean comicCoverArtist;

	private Boolean comicInteriorArtist;

	private Boolean comicInkArtist;

	private Boolean comicPencilArtist;

	private Boolean comicLetterArtist;

	private Boolean comicStripArtist;

	private Boolean gameArtist;

	private Boolean gameAuthor;

	private Boolean novelArtist;

	private Boolean novelAuthor;

	private Boolean referenceArtist;

	private Boolean referenceAuthor;

	private Boolean publicationArtist;

	private Boolean publicationDesigner;

	private Boolean publicationEditor;

	private Boolean publicityArtist;

	private Boolean cbsDigitalStaff;

	private Boolean ilmProductionStaff;

	private Boolean specialFeaturesStaff;

	private Boolean storyEditor;

	private Boolean studioExecutive;

	private Boolean stuntDepartment;

	private Boolean transportationDepartment;

	private Boolean videoGameProductionStaff;

	private Boolean writer;

	private RequestSortDTO sort;

}
