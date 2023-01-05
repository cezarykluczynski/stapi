package com.cezarykluczynski.stapi.server.species.dto;

import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;
import jakarta.ws.rs.FormParam;

public class SpeciesV2RestBeanParams extends PageSortBeanParams {

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

	@FormParam("avianSpecies")
	private Boolean avianSpecies;

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

	public Boolean getAvianSpecies() {
		return avianSpecies;
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

	public static SpeciesV2RestBeanParams fromPageSortBeanParams(PageSortBeanParams pageSortBeanParams) {
		if (pageSortBeanParams == null) {
			return null;
		}

		SpeciesV2RestBeanParams speciesV2RestBeanParams = new SpeciesV2RestBeanParams();
		speciesV2RestBeanParams.setPageNumber(pageSortBeanParams.getPageNumber());
		speciesV2RestBeanParams.setPageSize(pageSortBeanParams.getPageSize());
		speciesV2RestBeanParams.setSort(pageSortBeanParams.getSort());
		return speciesV2RestBeanParams;
	}

}
