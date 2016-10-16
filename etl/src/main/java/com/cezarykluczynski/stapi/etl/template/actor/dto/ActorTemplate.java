package com.cezarykluczynski.stapi.etl.template.actor.dto;

import com.cezarykluczynski.stapi.etl.template.common.dto.DateRange;
import com.cezarykluczynski.stapi.etl.template.common.dto.Gender;
import lombok.Data;

@Data
public class ActorTemplate {

	private String name;

	private String birthName;

	private Gender gender;

	private DateRange lifeRange;

}
