package com.cezarykluczynski.stapi.etl.template.characterbox.dto;

import com.cezarykluczynski.stapi.etl.template.common.dto.enums.Gender;
import com.cezarykluczynski.stapi.model.common.entity.enums.MaritalStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class CharacterboxTemplate {

	private Gender gender;

	private Integer height;

	private Integer weight;

	private MaritalStatus maritalStatus;

}
