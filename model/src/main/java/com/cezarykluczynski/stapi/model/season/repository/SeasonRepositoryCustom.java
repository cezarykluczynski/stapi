package com.cezarykluczynski.stapi.model.season.repository;

import com.cezarykluczynski.stapi.model.common.repository.CriteriaMatcher;
import com.cezarykluczynski.stapi.model.season.dto.SeasonRequestDTO;
import com.cezarykluczynski.stapi.model.season.entity.Season;

public interface SeasonRepositoryCustom extends CriteriaMatcher<SeasonRequestDTO, Season> {
}
