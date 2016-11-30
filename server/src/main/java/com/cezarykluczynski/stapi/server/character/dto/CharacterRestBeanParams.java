package com.cezarykluczynski.stapi.server.character.dto;

import com.cezarykluczynski.stapi.model.common.entity.enums.Gender;
import com.cezarykluczynski.stapi.server.common.dto.PageAwareBeanParams;

import javax.ws.rs.FormParam;

public class CharacterRestBeanParams extends PageAwareBeanParams {

	@FormParam("guid")
	private String guid;

	@FormParam("name")
	private String name;

	@FormParam("deceased")
	private Boolean deceased;

	@FormParam("gender")
	private Gender gender;

	public String getGuid() {
		return guid;
	}

	public String getName() {
		return name;
	}

	public Boolean getDeceased() {
		return deceased;
	}

	public Gender getGender() {
		return gender;
	}

	public static CharacterRestBeanParams fromPageAwareBeanParams(PageAwareBeanParams pageAwareBeanParams) {
		if (pageAwareBeanParams == null) {
			return null;
		}

		CharacterRestBeanParams characterRestBeanParams = new CharacterRestBeanParams();
		characterRestBeanParams.setPageNumber(pageAwareBeanParams.getPageNumber());
		characterRestBeanParams.setPageSize(pageAwareBeanParams.getPageSize());
		return characterRestBeanParams;
	}

}
