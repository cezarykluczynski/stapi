package com.cezarykluczynski.stapi.model.comic_series.repository;

import com.cezarykluczynski.stapi.model.comic_series.dto.ComicSeriesRequestDTO;
import com.cezarykluczynski.stapi.model.comic_series.entity.ComicSeries;
import com.cezarykluczynski.stapi.model.common.repository.CriteriaMatcher;

public interface ComicSeriesRepositoryCustom extends CriteriaMatcher<ComicSeriesRequestDTO, ComicSeries> {
}
