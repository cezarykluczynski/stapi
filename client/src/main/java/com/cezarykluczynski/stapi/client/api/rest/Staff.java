package com.cezarykluczynski.stapi.client.api.rest;

import com.cezarykluczynski.stapi.client.api.StapiRestSortSerializer;
import com.cezarykluczynski.stapi.client.rest.api.StaffApi;
import com.cezarykluczynski.stapi.client.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.rest.model.StaffV2BaseResponse;
import com.cezarykluczynski.stapi.client.rest.model.StaffV2FullResponse;
import com.cezarykluczynski.stapi.client.rest.model.StaffV2SearchCriteria;

public class Staff {

	private final StaffApi staffApi;

	public Staff(StaffApi staffApi) {
		this.staffApi = staffApi;
	}

	public StaffV2FullResponse getV2(String uid) throws ApiException {
		return staffApi.v2GetStaff(uid);
	}

	public StaffV2BaseResponse searchV2(StaffV2SearchCriteria staffV2SearchCriteria) throws ApiException {
		return staffApi.v2SearchStaff(staffV2SearchCriteria.getPageNumber(), staffV2SearchCriteria.getPageSize(),
				StapiRestSortSerializer.serialize(staffV2SearchCriteria.getSort()), staffV2SearchCriteria.getName(),
				staffV2SearchCriteria.getBirthName(), staffV2SearchCriteria.getGender(), staffV2SearchCriteria.getDateOfBirthFrom(),
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

}
