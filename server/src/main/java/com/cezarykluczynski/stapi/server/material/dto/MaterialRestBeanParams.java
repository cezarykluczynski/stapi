package com.cezarykluczynski.stapi.server.material.dto;

import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;

import javax.ws.rs.FormParam;

public class MaterialRestBeanParams extends PageSortBeanParams {

	private String uid;

	@FormParam("name")
	private String name;

	@FormParam("chemicalCompound")
	private Boolean chemicalCompound;

	@FormParam("biochemicalCompound")
	private Boolean biochemicalCompound;

	@FormParam("drug")
	private Boolean drug;

	@FormParam("poisonousSubstance")
	private Boolean poisonousSubstance;

	@FormParam("explosive")
	private Boolean explosive;

	@FormParam("gemstone")
	private Boolean gemstone;

	@FormParam("alloyOrComposite")
	private Boolean alloyOrComposite;

	@FormParam("fuel")
	private Boolean fuel;

	@FormParam("mineral")
	private Boolean mineral;

	@FormParam("preciousMaterial")
	private Boolean preciousMaterial;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getName() {
		return name;
	}

	public Boolean getChemicalCompound() {
		return chemicalCompound;
	}

	public Boolean getBiochemicalCompound() {
		return biochemicalCompound;
	}

	public Boolean getDrug() {
		return drug;
	}

	public Boolean getPoisonousSubstance() {
		return poisonousSubstance;
	}

	public Boolean getExplosive() {
		return explosive;
	}

	public Boolean getGemstone() {
		return gemstone;
	}

	public Boolean getAlloyOrComposite() {
		return alloyOrComposite;
	}

	public Boolean getFuel() {
		return fuel;
	}

	public Boolean getMineral() {
		return mineral;
	}

	public Boolean getPreciousMaterial() {
		return preciousMaterial;
	}

	public static MaterialRestBeanParams fromPageSortBeanParams(PageSortBeanParams pageSortBeanParams) {
		if (pageSortBeanParams == null) {
			return null;
		}

		MaterialRestBeanParams materialRestBeanParams = new MaterialRestBeanParams();
		materialRestBeanParams.setPageNumber(pageSortBeanParams.getPageNumber());
		materialRestBeanParams.setPageSize(pageSortBeanParams.getPageSize());
		materialRestBeanParams.setSort(pageSortBeanParams.getSort());
		return materialRestBeanParams;
	}

}
