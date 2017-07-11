package com.cezarykluczynski.stapi.etl.template.common.dto.performance;

import com.cezarykluczynski.stapi.etl.template.common.dto.datetime.enums.StardateYearSource;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "of")
public class StardateYearCandidateDTO {

	private final String value;

	private final String title;

	private final StardateYearSource source;

}
