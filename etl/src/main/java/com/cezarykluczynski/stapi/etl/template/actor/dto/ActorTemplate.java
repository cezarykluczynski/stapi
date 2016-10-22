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

	private boolean animalPerformer;

	private boolean disPerformer;

	private boolean ds9Performer;

	private boolean entPerformer;

	private boolean filmPerformer;

	private boolean standInPerformer;

	private boolean stuntPerformer;

	private boolean tasPerformer;

	private boolean tngPerformer;

	private boolean tosPerformer;

	private boolean videoGamePerformer;

	private boolean voicePerformer;

	private boolean voyPerformer;

}
