package com.cezarykluczynski.stapi.model.series.repository;

import com.cezarykluczynski.stapi.model.series.entity.Series;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeriesRepository extends JpaRepository<Series, Long> {
}
