package com.cezarykluczynski.stapi.model.series.repository;

import com.cezarykluczynski.stapi.model.series.entity.Series;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeriesRepository extends JpaRepository<Series, Long> {

	Page<Series> findByTitleIgnoreCaseContaining(String title, Pageable pageable);

}
