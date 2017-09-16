package com.cezarykluczynski.stapi.model.character.dto;

import com.cezarykluczynski.stapi.model.common.dto.RequestSortDTO;
import com.cezarykluczynski.stapi.model.common.entity.enums.Gender;
import lombok.Data;

@Data
public class CharacterRequestDTO {

	private String uid;

	private String name;

	private Gender gender;

	private Boolean deceased;

	private Boolean hologram;

	private Boolean fictionalCharacter;

	private Boolean mirror;

	private Boolean alternateReality;

	private RequestSortDTO sort;

}
