package com.cezarykluczynski.stapi.model.series.repository;

import com.cezarykluczynski.stapi.model.common.repository.PageAwareRepository;
import com.cezarykluczynski.stapi.model.series.entity.Series;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SeriesRepository extends JpaRepository<Series, Long>, PageAwareRepository<Series>, SeriesRepositoryCustom {

	Optional<Series> findByAbbreviation(String abbreviation);

}
