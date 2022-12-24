package com.cezarykluczynski.stapi.client.api.rest;

import com.cezarykluczynski.stapi.client.api.StapiRestSortSerializer;
import com.cezarykluczynski.stapi.client.api.dto.CompanyV2SearchCriteria;
import com.cezarykluczynski.stapi.client.v1.rest.api.CompanyApi;
import com.cezarykluczynski.stapi.client.v1.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.v1.rest.model.CompanyBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.CompanyFullResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.CompanyV2BaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.CompanyV2FullResponse;

@SuppressWarnings("ParameterNumber")
public class Company {

	private final CompanyApi companyApi;

	public Company(CompanyApi companyApi) {
		this.companyApi = companyApi;
	}

	public CompanyFullResponse get(String uid) throws ApiException {
		return companyApi.v1RestCompanyGet(uid, null);
	}

	public CompanyV2FullResponse getV2(String uid) throws ApiException {
		return companyApi.v2RestCompanyGet(uid);
	}

	@Deprecated
	public CompanyBaseResponse search(Integer pageNumber, Integer pageSize, String sort, String name, Boolean broadcaster,
			Boolean collectibleCompany, Boolean conglomerate, Boolean digitalVisualEffectsCompany, Boolean distributor, Boolean gameCompany,
			Boolean filmEquipmentCompany, Boolean makeUpEffectsStudio, Boolean mattePaintingCompany, Boolean modelAndMiniatureEffectsCompany,
			Boolean postProductionCompany, Boolean productionCompany, Boolean propCompany, Boolean recordLabel, Boolean specialEffectsCompany,
			Boolean tvAndFilmProductionCompany, Boolean videoGameCompany) throws ApiException {
		return companyApi.v1RestCompanySearchPost(pageNumber, pageSize, sort, null, name, broadcaster, collectibleCompany, conglomerate,
				digitalVisualEffectsCompany, distributor, gameCompany, filmEquipmentCompany, makeUpEffectsStudio, mattePaintingCompany,
				modelAndMiniatureEffectsCompany, postProductionCompany, productionCompany, propCompany, recordLabel, specialEffectsCompany,
				tvAndFilmProductionCompany, videoGameCompany);
	}

	@Deprecated
	public CompanyV2BaseResponse searchV2(Integer pageNumber, Integer pageSize, String sort, String name, Boolean broadcaster,
			Boolean streamingService, Boolean collectibleCompany, Boolean conglomerate, Boolean visualEffectsCompany,
			Boolean digitalVisualEffectsCompany, Boolean distributor, Boolean gameCompany, Boolean filmEquipmentCompany, Boolean makeUpEffectsStudio,
			Boolean mattePaintingCompany, Boolean modelAndMiniatureEffectsCompany, Boolean postProductionCompany, Boolean productionCompany,
			Boolean propCompany, Boolean recordLabel, Boolean specialEffectsCompany, Boolean tvAndFilmProductionCompany, Boolean videoGameCompany,
			Boolean publisher, Boolean publicationArtStudio) throws ApiException {
		return companyApi.v2RestCompanySearchPost(pageNumber, pageSize, sort, name, broadcaster, streamingService, collectibleCompany,
				conglomerate, visualEffectsCompany, digitalVisualEffectsCompany, distributor, gameCompany, filmEquipmentCompany, makeUpEffectsStudio,
				mattePaintingCompany, modelAndMiniatureEffectsCompany, postProductionCompany, productionCompany, propCompany, recordLabel,
				specialEffectsCompany, tvAndFilmProductionCompany, videoGameCompany, publisher, publicationArtStudio);
	}

	public CompanyV2BaseResponse searchV2(CompanyV2SearchCriteria companyV2SearchCriteria) throws ApiException {
		return companyApi.v2RestCompanySearchPost(companyV2SearchCriteria.getPageNumber(), companyV2SearchCriteria.getPageSize(),
				StapiRestSortSerializer.serialize(companyV2SearchCriteria.getSort()), companyV2SearchCriteria.getName(),
				companyV2SearchCriteria.getBroadcaster(), companyV2SearchCriteria.getStreamingService(),
				companyV2SearchCriteria.getCollectibleCompany(), companyV2SearchCriteria.getConglomerate(),
				companyV2SearchCriteria.getVisualEffectsCompany(), companyV2SearchCriteria.getDigitalVisualEffectsCompany(),
				companyV2SearchCriteria.getDistributor(), companyV2SearchCriteria.getGameCompany(),
				companyV2SearchCriteria.getFilmEquipmentCompany(), companyV2SearchCriteria.getMakeUpEffectsStudio(),
				companyV2SearchCriteria.getMattePaintingCompany(), companyV2SearchCriteria.getModelAndMiniatureEffectsCompany(),
				companyV2SearchCriteria.getPostProductionCompany(), companyV2SearchCriteria.getProductionCompany(),
				companyV2SearchCriteria.getPropCompany(), companyV2SearchCriteria.getRecordLabel(),
				companyV2SearchCriteria.getSpecialEffectsCompany(), companyV2SearchCriteria.getTvAndFilmProductionCompany(),
				companyV2SearchCriteria.getVideoGameCompany(), companyV2SearchCriteria.getPublisher(),
				companyV2SearchCriteria.getPublicationArtStudio());
	}

}
