package com.cezarykluczynski.stapi.model.bookSeries.repository;

import com.cezarykluczynski.stapi.model.bookSeries.dto.BookSeriesRequestDTO;
import com.cezarykluczynski.stapi.model.bookSeries.entity.BookSeries;
import com.cezarykluczynski.stapi.model.common.repository.CriteriaMatcher;

public interface BookSeriesRepositoryCustom extends CriteriaMatcher<BookSeriesRequestDTO, BookSeries> {
}
