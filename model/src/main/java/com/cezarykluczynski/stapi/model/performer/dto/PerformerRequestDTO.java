package com.cezarykluczynski.stapi.model.performer.dto;

import com.cezarykluczynski.stapi.model.common.dto.RealWorldPersonRequestDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PerformerRequestDTO extends RealWorldPersonRequestDTO {

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
