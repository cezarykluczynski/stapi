package com.cezarykluczynski.stapi.server.character.dto;

import com.cezarykluczynski.stapi.model.common.entity.enums.Gender;
import com.cezarykluczynski.stapi.server.common.dto.PageSortBeanParams;

import javax.ws.rs.FormParam;

public class CharacterRestBeanParams extends PageSortBeanParams {

	@FormParam("guid")
	private String guid;

	@FormParam("name")
	private String name;

	@FormParam("gender")
	private Gender gender;

	@FormParam("deceased")
	private Boolean deceased;

	@FormParam("mirror")
	private Boolean mirror;

	@FormParam("alternateReality")
	private Boolean alternateReality;

	public String getGuid() {
		return guid;
	}

	public String getName() {
		return name;
	}

	public Gender getGender() {
		return gender;
	}

	public Boolean getDeceased() {
		return deceased;
	}

	public Boolean getMirror() {
		return mirror;
	}

	public Boolean getAlternateReality() {
		return alternateReality;
	}

	public static CharacterRestBeanParams fromPageSortBeanParams(PageSortBeanParams pageSortBeanParams) {
		if (pageSortBeanParams == null) {
			return null;
		}

		CharacterRestBeanParams characterRestBeanParams = new CharacterRestBeanParams();
		characterRestBeanParams.setPageNumber(pageSortBeanParams.getPageNumber());
		characterRestBeanParams.setPageSize(pageSortBeanParams.getPageSize());
		characterRestBeanParams.setSort(pageSortBeanParams.getSort());
		return characterRestBeanParams;
	}

}
