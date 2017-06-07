package com.cezarykluczynski.stapi.server.staff.dto;

import com.cezarykluczynski.stapi.model.common.entity.enums.Gender;
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;

import javax.ws.rs.FormParam;
import java.time.LocalDate;

public class StaffRestBeanParams extends PageSortBeanParams {

	private String uid;

	@FormParam("name")
	private String name;

	@FormParam("birthName")
	private String birthName;

	@FormParam("gender")
	private Gender gender;

	@FormParam("dateOfBirthFrom")
	private LocalDate dateOfBirthFrom;

	@FormParam("dateOfBirthTo")
	private LocalDate dateOfBirthTo;

	@FormParam("dateOfDeathFrom")
	private LocalDate dateOfDeathFrom;

	@FormParam("dateOfDeathTo")
	private LocalDate dateOfDeathTo;

	@FormParam("placeOfBirth")
	private String placeOfBirth;

	@FormParam("placeOfDeath")
	private String placeOfDeath;

	@FormParam("artDepartment")
	private Boolean artDepartment;

	@FormParam("artDirector")
	private Boolean artDirector;

	@FormParam("productionDesigner")
	private Boolean productionDesigner;

	@FormParam("cameraAndElectricalDepartment")
	private Boolean cameraAndElectricalDepartment;

	@FormParam("cinematographer")
	private Boolean cinematographer;

	@FormParam("castingDepartment")
	private Boolean castingDepartment;

	@FormParam("costumeDepartment")
	private Boolean costumeDepartment;

	@FormParam("costumeDesigner")
	private Boolean costumeDesigner;

	@FormParam("director")
	private Boolean director;

	@FormParam("assistantOrSecondUnitDirector")
	private Boolean assistantOrSecondUnitDirector;

	@FormParam("exhibitAndAttractionStaff")
	private Boolean exhibitAndAttractionStaff;

	@FormParam("filmEditor")
	private Boolean filmEditor;

	@FormParam("linguist")
	private Boolean linguist;

	@FormParam("locationStaff")
	private Boolean locationStaff;

	@FormParam("makeupStaff")
	private Boolean makeupStaff;

	@FormParam("musicDepartment")
	private Boolean musicDepartment;

	@FormParam("composer")
	private Boolean composer;

	@FormParam("personalAssistant")
	private Boolean personalAssistant;

	@FormParam("producer")
	private Boolean producer;

	@FormParam("productionAssociate")
	private Boolean productionAssociate;

	@FormParam("productionStaff")
	private Boolean productionStaff;

	@FormParam("publicationStaff")
	private Boolean publicationStaff;

	@FormParam("scienceConsultant")
	private Boolean scienceConsultant;

	@FormParam("soundDepartment")
	private Boolean soundDepartment;

	@FormParam("specialAndVisualEffectsStaff")
	private Boolean specialAndVisualEffectsStaff;

	@FormParam("author")
	private Boolean author;

	@FormParam("audioAuthor")
	private Boolean audioAuthor;

	@FormParam("calendarArtist")
	private Boolean calendarArtist;

	@FormParam("comicArtist")
	private Boolean comicArtist;

	@FormParam("comicAuthor")
	private Boolean comicAuthor;

	@FormParam("comicColorArtist")
	private Boolean comicColorArtist;

	@FormParam("comicInteriorArtist")
	private Boolean comicInteriorArtist;

	@FormParam("comicInkArtist")
	private Boolean comicInkArtist;

	@FormParam("comicPencilArtist")
	private Boolean comicPencilArtist;

	@FormParam("comicLetterArtist")
	private Boolean comicLetterArtist;

	@FormParam("comicStripArtist")
	private Boolean comicStripArtist;

	@FormParam("gameArtist")
	private Boolean gameArtist;

	@FormParam("gameAuthor")
	private Boolean gameAuthor;

	@FormParam("novelArtist")
	private Boolean novelArtist;

	@FormParam("novelAuthor")
	private Boolean novelAuthor;

	@FormParam("referenceArtist")
	private Boolean referenceArtist;

	@FormParam("referenceAuthor")
	private Boolean referenceAuthor;

	@FormParam("publicationArtist")
	private Boolean publicationArtist;

	@FormParam("publicationDesigner")
	private Boolean publicationDesigner;

	@FormParam("publicationEditor")
	private Boolean publicationEditor;

	@FormParam("publicityArtist")
	private Boolean publicityArtist;

	@FormParam("cbsDigitalStaff")
	private Boolean cbsDigitalStaff;

	@FormParam("ilmProductionStaff")
	private Boolean ilmProductionStaff;

	@FormParam("specialFeaturesStaff")
	private Boolean specialFeaturesStaff;

	@FormParam("storyEditor")
	private Boolean storyEditor;

	@FormParam("studioExecutive")
	private Boolean studioExecutive;

	@FormParam("stuntDepartment")
	private Boolean stuntDepartment;

	@FormParam("transportationDepartment")
	private Boolean transportationDepartment;

	@FormParam("videoGameProductionStaff")
	private Boolean videoGameProductionStaff;

	@FormParam("writer")
	private Boolean writer;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getName() {
		return name;
	}

	public String getBirthName() {
		return birthName;
	}

	public Gender getGender() {
		return gender;
	}

	public LocalDate getDateOfBirthFrom() {
		return dateOfBirthFrom;
	}

	public LocalDate getDateOfBirthTo() {
		return dateOfBirthTo;
	}

	public LocalDate getDateOfDeathFrom() {
		return dateOfDeathFrom;
	}

	public LocalDate getDateOfDeathTo() {
		return dateOfDeathTo;
	}

	public String getPlaceOfBirth() {
		return placeOfBirth;
	}

	public String getPlaceOfDeath() {
		return placeOfDeath;
	}

	public Boolean getArtDepartment() {
		return artDepartment;
	}

	public Boolean getArtDirector() {
		return artDirector;
	}

	public Boolean getProductionDesigner() {
		return productionDesigner;
	}

	public Boolean getCameraAndElectricalDepartment() {
		return cameraAndElectricalDepartment;
	}

	public Boolean getCinematographer() {
		return cinematographer;
	}

	public Boolean getCastingDepartment() {
		return castingDepartment;
	}

	public Boolean getCostumeDepartment() {
		return costumeDepartment;
	}

	public Boolean getCostumeDesigner() {
		return costumeDesigner;
	}

	public Boolean getDirector() {
		return director;
	}

	public Boolean getAssistantOrSecondUnitDirector() {
		return assistantOrSecondUnitDirector;
	}

	public Boolean getExhibitAndAttractionStaff() {
		return exhibitAndAttractionStaff;
	}

	public Boolean getFilmEditor() {
		return filmEditor;
	}

	public Boolean getLinguist() {
		return linguist;
	}

	public Boolean getLocationStaff() {
		return locationStaff;
	}

	public Boolean getMakeupStaff() {
		return makeupStaff;
	}

	public Boolean getMusicDepartment() {
		return musicDepartment;
	}

	public Boolean getComposer() {
		return composer;
	}

	public Boolean getPersonalAssistant() {
		return personalAssistant;
	}

	public Boolean getProducer() {
		return producer;
	}

	public Boolean getProductionAssociate() {
		return productionAssociate;
	}

	public Boolean getProductionStaff() {
		return productionStaff;
	}

	public Boolean getPublicationStaff() {
		return publicationStaff;
	}

	public Boolean getScienceConsultant() {
		return scienceConsultant;
	}

	public Boolean getSoundDepartment() {
		return soundDepartment;
	}

	public Boolean getSpecialAndVisualEffectsStaff() {
		return specialAndVisualEffectsStaff;
	}

	public Boolean getAuthor() {
		return author;
	}

	public Boolean getAudioAuthor() {
		return audioAuthor;
	}

	public Boolean getCalendarArtist() {
		return calendarArtist;
	}

	public Boolean getComicArtist() {
		return comicArtist;
	}

	public Boolean getComicAuthor() {
		return comicAuthor;
	}

	public Boolean getComicColorArtist() {
		return comicColorArtist;
	}

	public Boolean getComicInteriorArtist() {
		return comicInteriorArtist;
	}

	public Boolean getComicInkArtist() {
		return comicInkArtist;
	}

	public Boolean getComicPencilArtist() {
		return comicPencilArtist;
	}

	public Boolean getComicLetterArtist() {
		return comicLetterArtist;
	}

	public Boolean getComicStripArtist() {
		return comicStripArtist;
	}

	public Boolean getGameArtist() {
		return gameArtist;
	}

	public Boolean getGameAuthor() {
		return gameAuthor;
	}

	public Boolean getNovelArtist() {
		return novelArtist;
	}

	public Boolean getNovelAuthor() {
		return novelAuthor;
	}

	public Boolean getReferenceArtist() {
		return referenceArtist;
	}

	public Boolean getReferenceAuthor() {
		return referenceAuthor;
	}

	public Boolean getPublicationArtist() {
		return publicationArtist;
	}

	public Boolean getPublicationDesigner() {
		return publicationDesigner;
	}

	public Boolean getPublicationEditor() {
		return publicationEditor;
	}

	public Boolean getPublicityArtist() {
		return publicityArtist;
	}

	public Boolean getCbsDigitalStaff() {
		return cbsDigitalStaff;
	}

	public Boolean getIlmProductionStaff() {
		return ilmProductionStaff;
	}

	public Boolean getSpecialFeaturesStaff() {
		return specialFeaturesStaff;
	}

	public Boolean getStoryEditor() {
		return storyEditor;
	}

	public Boolean getStudioExecutive() {
		return studioExecutive;
	}

	public Boolean getStuntDepartment() {
		return stuntDepartment;
	}

	public Boolean getTransportationDepartment() {
		return transportationDepartment;
	}

	public Boolean getVideoGameProductionStaff() {
		return videoGameProductionStaff;
	}

	public Boolean getWriter() {
		return writer;
	}

	public static StaffRestBeanParams fromPageSortBeanParams(PageSortBeanParams pageSortBeanParams) {
		if (pageSortBeanParams == null) {
			return null;
		}

		StaffRestBeanParams staffRestBeanParams = new StaffRestBeanParams();
		staffRestBeanParams.setPageNumber(pageSortBeanParams.getPageNumber());
		staffRestBeanParams.setPageSize(pageSortBeanParams.getPageSize());
		staffRestBeanParams.setSort(pageSortBeanParams.getSort());
		return staffRestBeanParams;
	}

}
