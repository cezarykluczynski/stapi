package com.cezarykluczynski.stapi.model.character.dto;

import com.cezarykluczynski.stapi.model.common.dto.RequestOrderDTO;
import com.cezarykluczynski.stapi.model.common.entity.enums.Gender;
import lombok.Data;

@Data
public class CharacterRequestDTO {

	private String guid;

	private String name;

	private Gender gender;

	private Boolean deceased;

	private RequestOrderDTO order;

}
