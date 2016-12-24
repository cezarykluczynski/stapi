package com.cezarykluczynski.stapi.etl.template.common.dto.performance;

import com.cezarykluczynski.stapi.etl.template.common.dto.enums.StardateYearSource;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor(staticName = "of")
public class StardateYearCandidateDTO {

	private String value;

	private StardateYearSource source;

	private String title;

}
