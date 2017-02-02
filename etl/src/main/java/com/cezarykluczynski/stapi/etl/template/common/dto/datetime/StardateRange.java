package com.cezarykluczynski.stapi.etl.template.common.dto.datetime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor(staticName = "of")
@NoArgsConstructor
@EqualsAndHashCode
public class StardateRange {

	private Float stardateFrom;

	private Float stardateTo;

}
