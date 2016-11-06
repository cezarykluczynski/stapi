package com.cezarykluczynski.stapi.model.series.repository;

import com.cezarykluczynski.stapi.model.common.repository.CriteriaMatcher;
import com.cezarykluczynski.stapi.model.series.dto.SeriesRequestDTO;
import com.cezarykluczynski.stapi.model.series.entity.Series;

public interface SeriesRepositoryCustom extends CriteriaMatcher<SeriesRequestDTO, Series> {
}
