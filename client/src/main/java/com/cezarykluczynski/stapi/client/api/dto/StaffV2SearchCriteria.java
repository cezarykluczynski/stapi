package com.cezarykluczynski.stapi.client.api.dto;

import com.cezarykluczynski.stapi.client.rest.model.Gender;

import java.time.LocalDate;

public class StaffV2SearchCriteria extends AbstractPageSortBaseCriteria {

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBirthName() {
		return birthName;
	}

	public void setBirthName(String birthName) {
		this.birthName = birthName;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public LocalDate getDateOfBirthFrom() {
		return dateOfBirthFrom;
	}

	public void setDateOfBirthFrom(LocalDate dateOfBirthFrom) {
		this.dateOfBirthFrom = dateOfBirthFrom;
	}

	public LocalDate getDateOfBirthTo() {
		return dateOfBirthTo;
	}

	public void setDateOfBirthTo(LocalDate dateOfBirthTo) {
		this.dateOfBirthTo = dateOfBirthTo;
	}

	public LocalDate getDateOfDeathFrom() {
		return dateOfDeathFrom;
	}

	public void setDateOfDeathFrom(LocalDate dateOfDeathFrom) {
		this.dateOfDeathFrom = dateOfDeathFrom;
	}

	public LocalDate getDateOfDeathTo() {
		return dateOfDeathTo;
	}

	public void setDateOfDeathTo(LocalDate dateOfDeathTo) {
		this.dateOfDeathTo = dateOfDeathTo;
	}

	public String getPlaceOfBirth() {
		return placeOfBirth;
	}

	public void setPlaceOfBirth(String placeOfBirth) {
		this.placeOfBirth = placeOfBirth;
	}

	public String getPlaceOfDeath() {
		return placeOfDeath;
	}

	public void setPlaceOfDeath(String placeOfDeath) {
		this.placeOfDeath = placeOfDeath;
	}

	public Boolean getArtDepartment() {
		return artDepartment;
	}

	public void setArtDepartment(Boolean artDepartment) {
		this.artDepartment = artDepartment;
	}

	public Boolean getArtDirector() {
		return artDirector;
	}

	public void setArtDirector(Boolean artDirector) {
		this.artDirector = artDirector;
	}

	public Boolean getProductionDesigner() {
		return productionDesigner;
	}

	public void setProductionDesigner(Boolean productionDesigner) {
		this.productionDesigner = productionDesigner;
	}

	public Boolean getCameraAndElectricalDepartment() {
		return cameraAndElectricalDepartment;
	}

	public void setCameraAndElectricalDepartment(Boolean cameraAndElectricalDepartment) {
		this.cameraAndElectricalDepartment = cameraAndElectricalDepartment;
	}

	public Boolean getCinematographer() {
		return cinematographer;
	}

	public void setCinematographer(Boolean cinematographer) {
		this.cinematographer = cinematographer;
	}

	public Boolean getCastingDepartment() {
		return castingDepartment;
	}

	public void setCastingDepartment(Boolean castingDepartment) {
		this.castingDepartment = castingDepartment;
	}

	public Boolean getCostumeDepartment() {
		return costumeDepartment;
	}

	public void setCostumeDepartment(Boolean costumeDepartment) {
		this.costumeDepartment = costumeDepartment;
	}

	public Boolean getCostumeDesigner() {
		return costumeDesigner;
	}

	public void setCostumeDesigner(Boolean costumeDesigner) {
		this.costumeDesigner = costumeDesigner;
	}

	public Boolean getDirector() {
		return director;
	}

	public void setDirector(Boolean director) {
		this.director = director;
	}

	public Boolean getAssistantOrSecondUnitDirector() {
		return assistantOrSecondUnitDirector;
	}

	public void setAssistantOrSecondUnitDirector(Boolean assistantOrSecondUnitDirector) {
		this.assistantOrSecondUnitDirector = assistantOrSecondUnitDirector;
	}

	public Boolean getExhibitAndAttractionStaff() {
		return exhibitAndAttractionStaff;
	}

	public void setExhibitAndAttractionStaff(Boolean exhibitAndAttractionStaff) {
		this.exhibitAndAttractionStaff = exhibitAndAttractionStaff;
	}

	public Boolean getFilmEditor() {
		return filmEditor;
	}

	public void setFilmEditor(Boolean filmEditor) {
		this.filmEditor = filmEditor;
	}

	public Boolean getFilmationProductionStaff() {
		return filmationProductionStaff;
	}

	public void setFilmationProductionStaff(Boolean filmationProductionStaff) {
		this.filmationProductionStaff = filmationProductionStaff;
	}

	public Boolean getLinguist() {
		return linguist;
	}

	public void setLinguist(Boolean linguist) {
		this.linguist = linguist;
	}

	public Boolean getLocationStaff() {
		return locationStaff;
	}

	public void setLocationStaff(Boolean locationStaff) {
		this.locationStaff = locationStaff;
	}

	public Boolean getMakeupStaff() {
		return makeupStaff;
	}

	public void setMakeupStaff(Boolean makeupStaff) {
		this.makeupStaff = makeupStaff;
	}

	public Boolean getMerchandiseStaff() {
		return merchandiseStaff;
	}

	public void setMerchandiseStaff(Boolean merchandiseStaff) {
		this.merchandiseStaff = merchandiseStaff;
	}

	public Boolean getMusicDepartment() {
		return musicDepartment;
	}

	public void setMusicDepartment(Boolean musicDepartment) {
		this.musicDepartment = musicDepartment;
	}

	public Boolean getComposer() {
		return composer;
	}

	public void setComposer(Boolean composer) {
		this.composer = composer;
	}

	public Boolean getPersonalAssistant() {
		return personalAssistant;
	}

	public void setPersonalAssistant(Boolean personalAssistant) {
		this.personalAssistant = personalAssistant;
	}

	public Boolean getProducer() {
		return producer;
	}

	public void setProducer(Boolean producer) {
		this.producer = producer;
	}

	public Boolean getProductionAssociate() {
		return productionAssociate;
	}

	public void setProductionAssociate(Boolean productionAssociate) {
		this.productionAssociate = productionAssociate;
	}

	public Boolean getProductionStaff() {
		return productionStaff;
	}

	public void setProductionStaff(Boolean productionStaff) {
		this.productionStaff = productionStaff;
	}

	public Boolean getPublicationStaff() {
		return publicationStaff;
	}

	public void setPublicationStaff(Boolean publicationStaff) {
		this.publicationStaff = publicationStaff;
	}

	public Boolean getScienceConsultant() {
		return scienceConsultant;
	}

	public void setScienceConsultant(Boolean scienceConsultant) {
		this.scienceConsultant = scienceConsultant;
	}

	public Boolean getSoundDepartment() {
		return soundDepartment;
	}

	public void setSoundDepartment(Boolean soundDepartment) {
		this.soundDepartment = soundDepartment;
	}

	public Boolean getSpecialAndVisualEffectsStaff() {
		return specialAndVisualEffectsStaff;
	}

	public void setSpecialAndVisualEffectsStaff(Boolean specialAndVisualEffectsStaff) {
		this.specialAndVisualEffectsStaff = specialAndVisualEffectsStaff;
	}

	public Boolean getAuthor() {
		return author;
	}

	public void setAuthor(Boolean author) {
		this.author = author;
	}

	public Boolean getAudioAuthor() {
		return audioAuthor;
	}

	public void setAudioAuthor(Boolean audioAuthor) {
		this.audioAuthor = audioAuthor;
	}

	public Boolean getCalendarArtist() {
		return calendarArtist;
	}

	public void setCalendarArtist(Boolean calendarArtist) {
		this.calendarArtist = calendarArtist;
	}

	public Boolean getComicArtist() {
		return comicArtist;
	}

	public void setComicArtist(Boolean comicArtist) {
		this.comicArtist = comicArtist;
	}

	public Boolean getComicAuthor() {
		return comicAuthor;
	}

	public void setComicAuthor(Boolean comicAuthor) {
		this.comicAuthor = comicAuthor;
	}

	public Boolean getComicColorArtist() {
		return comicColorArtist;
	}

	public void setComicColorArtist(Boolean comicColorArtist) {
		this.comicColorArtist = comicColorArtist;
	}

	public Boolean getComicCoverArtist() {
		return comicCoverArtist;
	}

	public void setComicCoverArtist(Boolean comicCoverArtist) {
		this.comicCoverArtist = comicCoverArtist;
	}

	public Boolean getComicInteriorArtist() {
		return comicInteriorArtist;
	}

	public void setComicInteriorArtist(Boolean comicInteriorArtist) {
		this.comicInteriorArtist = comicInteriorArtist;
	}

	public Boolean getComicInkArtist() {
		return comicInkArtist;
	}

	public void setComicInkArtist(Boolean comicInkArtist) {
		this.comicInkArtist = comicInkArtist;
	}

	public Boolean getComicPencilArtist() {
		return comicPencilArtist;
	}

	public void setComicPencilArtist(Boolean comicPencilArtist) {
		this.comicPencilArtist = comicPencilArtist;
	}

	public Boolean getComicLetterArtist() {
		return comicLetterArtist;
	}

	public void setComicLetterArtist(Boolean comicLetterArtist) {
		this.comicLetterArtist = comicLetterArtist;
	}

	public Boolean getComicStripArtist() {
		return comicStripArtist;
	}

	public void setComicStripArtist(Boolean comicStripArtist) {
		this.comicStripArtist = comicStripArtist;
	}

	public Boolean getGameArtist() {
		return gameArtist;
	}

	public void setGameArtist(Boolean gameArtist) {
		this.gameArtist = gameArtist;
	}

	public Boolean getGameAuthor() {
		return gameAuthor;
	}

	public void setGameAuthor(Boolean gameAuthor) {
		this.gameAuthor = gameAuthor;
	}

	public Boolean getNovelArtist() {
		return novelArtist;
	}

	public void setNovelArtist(Boolean novelArtist) {
		this.novelArtist = novelArtist;
	}

	public Boolean getNovelAuthor() {
		return novelAuthor;
	}

	public void setNovelAuthor(Boolean novelAuthor) {
		this.novelAuthor = novelAuthor;
	}

	public Boolean getReferenceArtist() {
		return referenceArtist;
	}

	public void setReferenceArtist(Boolean referenceArtist) {
		this.referenceArtist = referenceArtist;
	}

	public Boolean getReferenceAuthor() {
		return referenceAuthor;
	}

	public void setReferenceAuthor(Boolean referenceAuthor) {
		this.referenceAuthor = referenceAuthor;
	}

	public Boolean getPublicationArtist() {
		return publicationArtist;
	}

	public void setPublicationArtist(Boolean publicationArtist) {
		this.publicationArtist = publicationArtist;
	}

	public Boolean getPublicationDesigner() {
		return publicationDesigner;
	}

	public void setPublicationDesigner(Boolean publicationDesigner) {
		this.publicationDesigner = publicationDesigner;
	}

	public Boolean getPublicationEditor() {
		return publicationEditor;
	}

	public void setPublicationEditor(Boolean publicationEditor) {
		this.publicationEditor = publicationEditor;
	}

	public Boolean getPublicityArtist() {
		return publicityArtist;
	}

	public void setPublicityArtist(Boolean publicityArtist) {
		this.publicityArtist = publicityArtist;
	}

	public Boolean getCbsDigitalStaff() {
		return cbsDigitalStaff;
	}

	public void setCbsDigitalStaff(Boolean cbsDigitalStaff) {
		this.cbsDigitalStaff = cbsDigitalStaff;
	}

	public Boolean getIlmProductionStaff() {
		return ilmProductionStaff;
	}

	public void setIlmProductionStaff(Boolean ilmProductionStaff) {
		this.ilmProductionStaff = ilmProductionStaff;
	}

	public Boolean getSpecialFeaturesStaff() {
		return specialFeaturesStaff;
	}

	public void setSpecialFeaturesStaff(Boolean specialFeaturesStaff) {
		this.specialFeaturesStaff = specialFeaturesStaff;
	}

	public Boolean getStoryEditor() {
		return storyEditor;
	}

	public void setStoryEditor(Boolean storyEditor) {
		this.storyEditor = storyEditor;
	}

	public Boolean getStudioExecutive() {
		return studioExecutive;
	}

	public void setStudioExecutive(Boolean studioExecutive) {
		this.studioExecutive = studioExecutive;
	}

	public Boolean getStuntDepartment() {
		return stuntDepartment;
	}

	public void setStuntDepartment(Boolean stuntDepartment) {
		this.stuntDepartment = stuntDepartment;
	}

	public Boolean getTransportationDepartment() {
		return transportationDepartment;
	}

	public void setTransportationDepartment(Boolean transportationDepartment) {
		this.transportationDepartment = transportationDepartment;
	}

	public Boolean getVideoGameProductionStaff() {
		return videoGameProductionStaff;
	}

	public void setVideoGameProductionStaff(Boolean videoGameProductionStaff) {
		this.videoGameProductionStaff = videoGameProductionStaff;
	}

	public Boolean getWriter() {
		return writer;
	}

	public void setWriter(Boolean writer) {
		this.writer = writer;
	}

}
