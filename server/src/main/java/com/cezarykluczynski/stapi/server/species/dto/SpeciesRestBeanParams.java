package com.cezarykluczynski.stapi.server.species.dto;

import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;

import javax.ws.rs.FormParam;

public class SpeciesRestBeanParams extends PageSortBeanParams {

	private String uid;

	@FormParam("name")
	private String name;

	@FormParam("extinctSpecies")
	private Boolean extinctSpecies;

	@FormParam("warpCapableSpecies")
	private Boolean warpCapableSpecies;

	@FormParam("extraGalacticSpecies")
	private Boolean extraGalacticSpecies;

	@FormParam("humanoidSpecies")
	private Boolean humanoidSpecies;

	@FormParam("reptilianSpecies")
	private Boolean reptilianSpecies;

	@FormParam("nonCorporealSpecies")
	private Boolean nonCorporealSpecies;

	@FormParam("shapeshiftingSpecies")
	private Boolean shapeshiftingSpecies;

	@FormParam("spaceborneSpecies")
	private Boolean spaceborneSpecies;

	@FormParam("telepathicSpecies")
	private Boolean telepathicSpecies;

	@FormParam("transDimensionalSpecies")
	private Boolean transDimensionalSpecies;

	@FormParam("unnamedSpecies")
	private Boolean unnamedSpecies;

	@FormParam("alternateReality")
	private Boolean alternateReality;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getName() {
		return name;
	}

	public Boolean getExtinctSpecies() {
		return extinctSpecies;
	}

	public Boolean getWarpCapableSpecies() {
		return warpCapableSpecies;
	}

	public Boolean getExtraGalacticSpecies() {
		return extraGalacticSpecies;
	}

	public Boolean getHumanoidSpecies() {
		return humanoidSpecies;
	}

	public Boolean getReptilianSpecies() {
		return reptilianSpecies;
	}

	public Boolean getNonCorporealSpecies() {
		return nonCorporealSpecies;
	}

	public Boolean getShapeshiftingSpecies() {
		return shapeshiftingSpecies;
	}

	public Boolean getSpaceborneSpecies() {
		return spaceborneSpecies;
	}

	public Boolean getTelepathicSpecies() {
		return telepathicSpecies;
	}

	public Boolean getTransDimensionalSpecies() {
		return transDimensionalSpecies;
	}

	public Boolean getUnnamedSpecies() {
		return unnamedSpecies;
	}

	public Boolean getAlternateReality() {
		return alternateReality;
	}

	public static SpeciesRestBeanParams fromPageSortBeanParams(PageSortBeanParams pageSortBeanParams) {
		if (pageSortBeanParams == null) {
			return null;
		}

		SpeciesRestBeanParams speciesRestBeanParams = new SpeciesRestBeanParams();
		speciesRestBeanParams.setPageNumber(pageSortBeanParams.getPageNumber());
		speciesRestBeanParams.setPageSize(pageSortBeanParams.getPageSize());
		speciesRestBeanParams.setSort(pageSortBeanParams.getSort());
		return speciesRestBeanParams;
	}

}
