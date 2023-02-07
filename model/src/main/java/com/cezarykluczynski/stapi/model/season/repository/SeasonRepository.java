package com.cezarykluczynski.stapi.model.season.repository;

import com.cezarykluczynski.stapi.model.common.repository.PageAwareRepository;
import com.cezarykluczynski.stapi.model.season.entity.Season;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeasonRepository extends JpaRepository<Season, Long>, PageAwareRepository<Season>, SeasonRepositoryCustom {

	Season findBySeriesAbbreviationAndSeasonNumber(String seriesAbbreviation, Integer seasonNumber);

	List<Season> findBySeriesAbbreviation(String seriesAbbreviation);

}
