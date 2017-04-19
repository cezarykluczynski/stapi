package com.cezarykluczynski.stapi.model.bookSeries.repository;

import com.cezarykluczynski.stapi.model.bookSeries.entity.BookSeries;
import com.cezarykluczynski.stapi.model.common.repository.PageAwareRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookSeriesRepository extends JpaRepository<BookSeries, Long>, PageAwareRepository<BookSeries>, BookSeriesRepositoryCustom {
}
