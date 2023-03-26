package com.cezarykluczynski.stapi.client.rest.facade;

import com.cezarykluczynski.stapi.client.rest.util.StapiRestSortSerializer;
import com.cezarykluczynski.stapi.client.rest.api.CompanyApi;
import com.cezarykluczynski.stapi.client.rest.invoker.ApiException;
import com.cezarykluczynski.stapi.client.rest.model.CompanyV2BaseResponse;
import com.cezarykluczynski.stapi.client.rest.model.CompanyV2FullResponse;
import com.cezarykluczynski.stapi.client.rest.model.CompanyV2SearchCriteria;

public class Company {

	private final CompanyApi companyApi;

	public Company(CompanyApi companyApi) {
		this.companyApi = companyApi;
	}

	public CompanyV2FullResponse getV2(String uid) throws ApiException {
		return companyApi.v2GetCompany(uid);
	}

	public CompanyV2BaseResponse searchV2(CompanyV2SearchCriteria companyV2SearchCriteria) throws ApiException {
		return companyApi.v2SearchCompanies(companyV2SearchCriteria.getPageNumber(), companyV2SearchCriteria.getPageSize(),
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
