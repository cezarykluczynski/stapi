package com.cezarykluczynski.stapi.client.api.rest;

import com.cezarykluczynski.stapi.client.v1.rest.api.StaffApi;
import com.cezarykluczynski.stapi.client.v1.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.v1.rest.model.StaffBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.StaffFullResponse;

import java.time.LocalDate;

@SuppressWarnings("ParameterNumber")
public class Staff {

	private final StaffApi staffApi;

	private final String apiKey;

	public Staff(StaffApi staffApi, String apiKey) {
		this.staffApi = staffApi;
		this.apiKey = apiKey;
	}

	public StaffFullResponse get(String uid) throws ApiException {
		return staffApi.staffGet(uid, apiKey);
	}

	public StaffBaseResponse search(Integer pageNumber, Integer pageSize, String sort, String name, String birthName, String gender,
			LocalDate dateOfBirthFrom, LocalDate dateOfBirthTo, String placeOfBirth, LocalDate dateOfDeathFrom, LocalDate dateOfDeathTo,
			String placeOfDeath, Boolean artDepartment, Boolean artDirector, Boolean productionDesigner, Boolean cameraAndElectricalDepartment,
			Boolean cinematographer, Boolean castingDepartment, Boolean costumeDepartment, Boolean costumeDesigner, Boolean director,
			Boolean assistantOrSecondUnitDirector, Boolean exhibitAndAttractionStaff, Boolean filmEditor, Boolean linguist, Boolean locationStaff,
			Boolean makeupStaff, Boolean musicDepartment, Boolean composer, Boolean personalAssistant, Boolean producer, Boolean productionAssociate,
			Boolean productionStaff, Boolean publicationStaff, Boolean scienceConsultant, Boolean soundDepartment,
			Boolean specialAndVisualEffectsStaff, Boolean author, Boolean audioAuthor, Boolean calendarArtist, Boolean comicArtist,
			Boolean comicAuthor, Boolean comicColorArtist, Boolean comicInteriorArtist, Boolean comicInkArtist, Boolean comicPencilArtist,
			Boolean comicLetterArtist, Boolean comicStripArtist, Boolean gameArtist, Boolean gameAuthor, Boolean novelArtist, Boolean novelAuthor,
			Boolean referenceArtist, Boolean referenceAuthor, Boolean publicationArtist, Boolean publicationDesigner, Boolean publicationEditor,
			Boolean publicityArtist, Boolean cbsDigitalStaff, Boolean ilmProductionStaff, Boolean specialFeaturesStaff, Boolean storyEditor,
			Boolean studioExecutive, Boolean stuntDepartment, Boolean transportationDepartment, Boolean videoGameProductionStaff, Boolean writer)
			throws ApiException {
		return staffApi.staffSearchPost(pageNumber, pageSize, sort, apiKey, name, birthName, gender, dateOfBirthFrom, dateOfBirthTo, placeOfBirth,
				dateOfDeathFrom, dateOfDeathTo, placeOfDeath, artDepartment, artDirector, productionDesigner, cameraAndElectricalDepartment,
				cinematographer, castingDepartment, costumeDepartment, costumeDesigner, director, assistantOrSecondUnitDirector,
				exhibitAndAttractionStaff, filmEditor, linguist, locationStaff, makeupStaff, musicDepartment, composer, personalAssistant, producer,
				productionAssociate, productionStaff, publicationStaff, scienceConsultant, soundDepartment, specialAndVisualEffectsStaff, author,
				audioAuthor, calendarArtist, comicArtist, comicAuthor, comicColorArtist, comicInteriorArtist, comicInkArtist, comicPencilArtist,
				comicLetterArtist, comicStripArtist, gameArtist, gameAuthor, novelArtist, novelAuthor, referenceArtist, referenceAuthor,
				publicationArtist, publicationDesigner, publicationEditor, publicityArtist, cbsDigitalStaff, ilmProductionStaff, specialFeaturesStaff,
				storyEditor, studioExecutive, stuntDepartment, transportationDepartment, videoGameProductionStaff, writer);
	}

}
