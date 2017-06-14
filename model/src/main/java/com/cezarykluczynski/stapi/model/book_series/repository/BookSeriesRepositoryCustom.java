package com.cezarykluczynski.stapi.model.book_series.repository;

import com.cezarykluczynski.stapi.model.book_series.dto.BookSeriesRequestDTO;
import com.cezarykluczynski.stapi.model.book_series.entity.BookSeries;
import com.cezarykluczynski.stapi.model.common.repository.CriteriaMatcher;

public interface BookSeriesRepositoryCustom extends CriteriaMatcher<BookSeriesRequestDTO, BookSeries> {
}
