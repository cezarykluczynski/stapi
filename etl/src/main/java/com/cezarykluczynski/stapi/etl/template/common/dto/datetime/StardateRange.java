package com.cezarykluczynski.stapi.etl.template.common.dto.datetime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor(staticName = "of")
@EqualsAndHashCode
public class StardateRange {

	private Float stardateFrom;

	private Float stardateTo;

}
