package com.cezarykluczynski.stapi.etl.template.actor.dto;

import com.cezarykluczynski.stapi.etl.template.common.dto.DateRange;
import com.cezarykluczynski.stapi.etl.template.common.dto.Gender;
import com.cezarykluczynski.stapi.model.page.entity.Page;
import lombok.Data;

@Data
public class ActorTemplate {

	private String name;

	private Page page;

	private String birthName;

	private String placeOfBirth;

	private String placeOfDeath;

	private Gender gender;

	private DateRange lifeRange;

	private Boolean animalPerformer;

	private Boolean disPerformer;

	private Boolean ds9Performer;

	private Boolean entPerformer;

	private Boolean filmPerformer;

	private Boolean standInPerformer;

	private Boolean stuntPerformer;

	private Boolean tasPerformer;

	private Boolean tngPerformer;

	private Boolean tosPerformer;

	private Boolean videoGamePerformer;

	private Boolean voicePerformer;

	private Boolean voyPerformer;

}
