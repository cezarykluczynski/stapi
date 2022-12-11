package com.cezarykluczynski.stapi.server.company.dto;

import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;

import javax.ws.rs.FormParam;

public class CompanyV2RestBeanParams extends PageSortBeanParams {

	private String uid;

	@FormParam("name")
	private String name;

	@FormParam("broadcaster")
	private Boolean broadcaster;

	@FormParam("streamingService")
	private Boolean streamingService;

	@FormParam("collectibleCompany")
	private Boolean collectibleCompany;

	@FormParam("conglomerate")
	private Boolean conglomerate;

	@FormParam("visualEffectsCompany")
	private Boolean visualEffectsCompany;

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

	@FormParam("publisher")
	private Boolean publisher;

	@FormParam("publicationArtStudio")
	private Boolean publicationArtStudio;

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

	public Boolean getStreamingService() {
		return streamingService;
	}

	public Boolean getCollectibleCompany() {
		return collectibleCompany;
	}

	public Boolean getConglomerate() {
		return conglomerate;
	}

	public Boolean getVisualEffectsCompany() {
		return visualEffectsCompany;
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

	public Boolean getPublisher() {
		return publisher;
	}

	public Boolean getPublicationArtStudio() {
		return publicationArtStudio;
	}

	public static CompanyV2RestBeanParams fromPageSortBeanParams(PageSortBeanParams pageSortBeanParams) {
		if (pageSortBeanParams == null) {
			return null;
		}

		CompanyV2RestBeanParams companyV2RestBeanParams = new CompanyV2RestBeanParams();
		companyV2RestBeanParams.setPageNumber(pageSortBeanParams.getPageNumber());
		companyV2RestBeanParams.setPageSize(pageSortBeanParams.getPageSize());
		companyV2RestBeanParams.setSort(pageSortBeanParams.getSort());
		return companyV2RestBeanParams;
	}

}
