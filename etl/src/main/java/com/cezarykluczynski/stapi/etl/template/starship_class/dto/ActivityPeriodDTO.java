package com.cezarykluczynski.stapi.etl.template.starship_class.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "of")
public class ActivityPeriodDTO {

	private String from;

	private String to;

}
