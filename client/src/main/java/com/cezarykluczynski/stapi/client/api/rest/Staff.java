package com.cezarykluczynski.stapi.client.api.rest;

import com.cezarykluczynski.stapi.client.api.StapiRestSortSerializer;
import com.cezarykluczynski.stapi.client.api.dto.StaffV2SearchCriteria;
import com.cezarykluczynski.stapi.client.rest.api.StaffApi;
import com.cezarykluczynski.stapi.client.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.rest.model.Gender;
import com.cezarykluczynski.stapi.client.rest.model.StaffBaseResponse;
import com.cezarykluczynski.stapi.client.rest.model.StaffFullResponse;
import com.cezarykluczynski.stapi.client.rest.model.StaffV2BaseResponse;
import com.cezarykluczynski.stapi.client.rest.model.StaffV2FullResponse;

import java.time.LocalDate;

@SuppressWarnings("ParameterNumber")
public class Staff {

	private final StaffApi staffApi;

	public Staff(StaffApi staffApi) {
		this.staffApi = staffApi;
	}

	@Deprecated
	public StaffFullResponse get(String uid) throws ApiException {
		return staffApi.v1RestStaffGet(uid);
	}

	public StaffV2FullResponse getV2(String uid) throws ApiException {
		return staffApi.v2RestStaffGet(uid);
	}

	@Deprecated
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
		return staffApi.v1RestStaffSearchPost(pageNumber, pageSize, sort, name, birthName, gender, dateOfBirthFrom, dateOfBirthTo,
				placeOfBirth, dateOfDeathFrom, dateOfDeathTo, placeOfDeath, artDepartment, artDirector, productionDesigner,
				cameraAndElectricalDepartment, cinematographer, castingDepartment, costumeDepartment, costumeDesigner, director,
				assistantOrSecondUnitDirector, exhibitAndAttractionStaff, filmEditor, linguist, locationStaff, makeupStaff, musicDepartment,
				composer, personalAssistant, producer, productionAssociate, productionStaff, publicationStaff, scienceConsultant, soundDepartment,
				specialAndVisualEffectsStaff, author, audioAuthor, calendarArtist, comicArtist, comicAuthor, comicColorArtist, comicInteriorArtist,
				comicInkArtist, comicPencilArtist, comicLetterArtist, comicStripArtist, gameArtist, gameAuthor, novelArtist, novelAuthor,
				referenceArtist, referenceAuthor, publicationArtist, publicationDesigner, publicationEditor, publicityArtist, cbsDigitalStaff,
				ilmProductionStaff, specialFeaturesStaff, storyEditor, studioExecutive, stuntDepartment, transportationDepartment,
				videoGameProductionStaff, writer);
	}

	@Deprecated
	public StaffV2BaseResponse searchV2(Integer pageNumber, Integer pageSize, String sort, String name, String birthName, String gender,
			LocalDate dateOfBirthFrom, LocalDate dateOfBirthTo, String placeOfBirth, LocalDate dateOfDeathFrom, LocalDate dateOfDeathTo,
			String placeOfDeath, Boolean artDepartment, Boolean artDirector, Boolean productionDesigner, Boolean cameraAndElectricalDepartment,
			Boolean cinematographer, Boolean castingDepartment, Boolean costumeDepartment, Boolean costumeDesigner, Boolean director,
			Boolean assistantOrSecondUnitDirector, Boolean exhibitAndAttractionStaff, Boolean filmEditor, Boolean filmationProductionStaff,
			Boolean linguist, Boolean locationStaff, Boolean makeupStaff, Boolean merchandiseStaff, Boolean musicDepartment, Boolean composer,
			Boolean personalAssistant, Boolean producer, Boolean productionAssociate, Boolean productionStaff, Boolean publicationStaff,
			Boolean scienceConsultant, Boolean soundDepartment, Boolean specialAndVisualEffectsStaff, Boolean author, Boolean audioAuthor,
			Boolean calendarArtist, Boolean comicArtist, Boolean comicAuthor, Boolean comicColorArtist, Boolean comicCoverArtist,
			Boolean comicInteriorArtist, Boolean comicInkArtist, Boolean comicPencilArtist, Boolean comicLetterArtist, Boolean comicStripArtist,
			Boolean gameArtist, Boolean gameAuthor, Boolean novelArtist, Boolean novelAuthor, Boolean referenceArtist, Boolean referenceAuthor,
			Boolean publicationArtist, Boolean publicationDesigner, Boolean publicationEditor, Boolean publicityArtist, Boolean cbsDigitalStaff,
			Boolean ilmProductionStaff, Boolean specialFeaturesStaff, Boolean storyEditor, Boolean studioExecutive, Boolean stuntDepartment,
			Boolean transportationDepartment, Boolean videoGameProductionStaff, Boolean writer) throws ApiException {
		return staffApi.v2RestStaffSearchPost(pageNumber, pageSize, sort, name, birthName, gender, dateOfBirthFrom, dateOfBirthTo,
				placeOfBirth, dateOfDeathFrom, dateOfDeathTo, placeOfDeath, artDepartment, artDirector, productionDesigner,
				cameraAndElectricalDepartment, cinematographer, castingDepartment, costumeDepartment, costumeDesigner, director,
				assistantOrSecondUnitDirector, exhibitAndAttractionStaff, filmEditor, filmationProductionStaff, linguist, locationStaff, makeupStaff,
				merchandiseStaff, musicDepartment, composer, personalAssistant, producer, productionAssociate, productionStaff, publicationStaff,
				scienceConsultant, soundDepartment, specialAndVisualEffectsStaff, author, audioAuthor, calendarArtist, comicArtist, comicAuthor,
				comicColorArtist, comicCoverArtist, comicInteriorArtist, comicInkArtist, comicPencilArtist, comicLetterArtist, comicStripArtist,
				gameArtist, gameAuthor, novelArtist, novelAuthor, referenceArtist, referenceAuthor, publicationArtist, publicationDesigner,
				publicationEditor, publicityArtist, cbsDigitalStaff, ilmProductionStaff, specialFeaturesStaff, storyEditor, studioExecutive,
				stuntDepartment, transportationDepartment, videoGameProductionStaff, writer);
	}

	public StaffV2BaseResponse searchV2(StaffV2SearchCriteria staffV2SearchCriteria) throws ApiException {
		return staffApi.v2RestStaffSearchPost(staffV2SearchCriteria.getPageNumber(), staffV2SearchCriteria.getPageSize(),
				StapiRestSortSerializer.serialize(staffV2SearchCriteria.getSort()), staffV2SearchCriteria.getName(),
				staffV2SearchCriteria.getBirthName(), nameIfPresent(staffV2SearchCriteria.getGender()), staffV2SearchCriteria.getDateOfBirthFrom(),
				staffV2SearchCriteria.getDateOfBirthTo(), staffV2SearchCriteria.getPlaceOfBirth(), staffV2SearchCriteria.getDateOfDeathFrom(),
				staffV2SearchCriteria.getDateOfDeathTo(), staffV2SearchCriteria.getPlaceOfDeath(), staffV2SearchCriteria.getArtDepartment(),
				staffV2SearchCriteria.getArtDirector(), staffV2SearchCriteria.getProductionDesigner(),
				staffV2SearchCriteria.getCameraAndElectricalDepartment(), staffV2SearchCriteria.getCinematographer(),
				staffV2SearchCriteria.getCastingDepartment(), staffV2SearchCriteria.getCostumeDepartment(),
				staffV2SearchCriteria.getCostumeDesigner(), staffV2SearchCriteria.getDirector(),
				staffV2SearchCriteria.getAssistantOrSecondUnitDirector(), staffV2SearchCriteria.getExhibitAndAttractionStaff(),
				staffV2SearchCriteria.getFilmEditor(), staffV2SearchCriteria.getFilmationProductionStaff(), staffV2SearchCriteria.getLinguist(),
				staffV2SearchCriteria.getLocationStaff(), staffV2SearchCriteria.getMakeupStaff(), staffV2SearchCriteria.getMerchandiseStaff(),
				staffV2SearchCriteria.getMusicDepartment(), staffV2SearchCriteria.getComposer(), staffV2SearchCriteria.getPersonalAssistant(),
				staffV2SearchCriteria.getProducer(), staffV2SearchCriteria.getProductionAssociate(), staffV2SearchCriteria.getProductionStaff(),
				staffV2SearchCriteria.getPublicationStaff(), staffV2SearchCriteria.getScienceConsultant(), staffV2SearchCriteria.getSoundDepartment(),
				staffV2SearchCriteria.getSpecialAndVisualEffectsStaff(), staffV2SearchCriteria.getAuthor(), staffV2SearchCriteria.getAudioAuthor(),
				staffV2SearchCriteria.getCalendarArtist(), staffV2SearchCriteria.getComicArtist(), staffV2SearchCriteria.getComicAuthor(),
				staffV2SearchCriteria.getComicColorArtist(), staffV2SearchCriteria.getComicCoverArtist(),
				staffV2SearchCriteria.getComicInteriorArtist(), staffV2SearchCriteria.getComicInkArtist(),
				staffV2SearchCriteria.getComicPencilArtist(), staffV2SearchCriteria.getComicLetterArtist(),
				staffV2SearchCriteria.getComicStripArtist(), staffV2SearchCriteria.getGameArtist(), staffV2SearchCriteria.getGameAuthor(),
				staffV2SearchCriteria.getNovelArtist(), staffV2SearchCriteria.getNovelAuthor(), staffV2SearchCriteria.getReferenceArtist(),
				staffV2SearchCriteria.getReferenceAuthor(), staffV2SearchCriteria.getPublicationArtist(),
				staffV2SearchCriteria.getPublicationDesigner(), staffV2SearchCriteria.getPublicationEditor(),
				staffV2SearchCriteria.getPublicityArtist(), staffV2SearchCriteria.getCbsDigitalStaff(), staffV2SearchCriteria.getIlmProductionStaff(),
				staffV2SearchCriteria.getSpecialFeaturesStaff(), staffV2SearchCriteria.getStoryEditor(), staffV2SearchCriteria.getStudioExecutive(),
				staffV2SearchCriteria.getStuntDepartment(), staffV2SearchCriteria.getTransportationDepartment(),
				staffV2SearchCriteria.getVideoGameProductionStaff(), staffV2SearchCriteria.getWriter());
	}

	private static String nameIfPresent(Gender gender) {
		return gender != null ? gender.name() : null;
	}

}
