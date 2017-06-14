package com.cezarykluczynski.stapi.model.comic_series.repository;

import com.cezarykluczynski.stapi.model.comic_series.entity.ComicSeries;
import com.cezarykluczynski.stapi.model.common.repository.PageAwareRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComicSeriesRepository extends JpaRepository<ComicSeries, Long>, PageAwareRepository<ComicSeries>, ComicSeriesRepositoryCustom {
}
