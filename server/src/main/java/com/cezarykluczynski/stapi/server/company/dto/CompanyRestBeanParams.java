package com.cezarykluczynski.stapi.server.company.dto;

import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;

import javax.ws.rs.FormParam;

public class CompanyRestBeanParams extends PageSortBeanParams {

	private String uid;

	@FormParam("name")
	private String name;

	@FormParam("broadcaster")
	private Boolean broadcaster;

	@FormParam("collectibleCompany")
	private Boolean collectibleCompany;

	@FormParam("conglomerate")
	private Boolean conglomerate;

	@FormParam("digitalVisualEffectsCompany")
	private Boolean digitalVisualEffectsCompany;

	@FormParam("distributor")
	private Boolean distributor;

	@FormParam("gameCompany")
	private Boolean gameCompany;

	@FormParam("filmEquipmentCompany")
	private Boolean filmEquipmentCompany;

	@FormParam("makeUpEffectsStudio")
	private Boolean makeUpEffectsStudio;

	@FormParam("mattePaintingCompany")
	private Boolean mattePaintingCompany;

	@FormParam("modelAndMiniatureEffectsCompany")
	private Boolean modelAndMiniatureEffectsCompany;

	@FormParam("postProductionCompany")
	private Boolean postProductionCompany;

	@FormParam("productionCompany")
	private Boolean productionCompany;

	@FormParam("propCompany")
	private Boolean propCompany;

	@FormParam("recordLabel")
	private Boolean recordLabel;

	@FormParam("specialEffectsCompany")
	private Boolean specialEffectsCompany;

	@FormParam("tvAndFilmProductionCompany")
	private Boolean tvAndFilmProductionCompany;

	@FormParam("videoGameCompany")
	private Boolean videoGameCompany;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getName() {
		return name;
	}

	public Boolean getBroadcaster() {
		return broadcaster;
	}

	public Boolean getCollectibleCompany() {
		return collectibleCompany;
	}

	public Boolean getConglomerate() {
		return conglomerate;
	}

	public Boolean getDigitalVisualEffectsCompany() {
		return digitalVisualEffectsCompany;
	}

	public Boolean getDistributor() {
		return distributor;
	}

	public Boolean getGameCompany() {
		return gameCompany;
	}

	public Boolean getFilmEquipmentCompany() {
		return filmEquipmentCompany;
	}

	public Boolean getMakeUpEffectsStudio() {
		return makeUpEffectsStudio;
	}

	public Boolean getMattePaintingCompany() {
		return mattePaintingCompany;
	}

	public Boolean getModelAndMiniatureEffectsCompany() {
		return modelAndMiniatureEffectsCompany;
	}

	public Boolean getPostProductionCompany() {
		return postProductionCompany;
	}

	public Boolean getProductionCompany() {
		return productionCompany;
	}

	public Boolean getPropCompany() {
		return propCompany;
	}

	public Boolean getRecordLabel() {
		return recordLabel;
	}

	public Boolean getSpecialEffectsCompany() {
		return specialEffectsCompany;
	}

	public Boolean getTvAndFilmProductionCompany() {
		return tvAndFilmProductionCompany;
	}

	public Boolean getVideoGameCompany() {
		return videoGameCompany;
	}

	public static CompanyRestBeanParams fromPageSortBeanParams(PageSortBeanParams pageSortBeanParams) {
		if (pageSortBeanParams == null) {
			return null;
		}

		CompanyRestBeanParams companyRestBeanParams = new CompanyRestBeanParams();
		companyRestBeanParams.setPageNumber(pageSortBeanParams.getPageNumber());
		companyRestBeanParams.setPageSize(pageSortBeanParams.getPageSize());
		companyRestBeanParams.setSort(pageSortBeanParams.getSort());
		return companyRestBeanParams;
	}

}
