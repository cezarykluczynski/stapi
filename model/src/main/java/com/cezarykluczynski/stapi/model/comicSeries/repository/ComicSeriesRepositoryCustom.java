package com.cezarykluczynski.stapi.model.comicSeries.repository;

import com.cezarykluczynski.stapi.model.comicSeries.dto.ComicSeriesRequestDTO;
import com.cezarykluczynski.stapi.model.comicSeries.entity.ComicSeries;
import com.cezarykluczynski.stapi.model.common.repository.CriteriaMatcher;

public interface ComicSeriesRepositoryCustom extends CriteriaMatcher<ComicSeriesRequestDTO, ComicSeries> {
}
