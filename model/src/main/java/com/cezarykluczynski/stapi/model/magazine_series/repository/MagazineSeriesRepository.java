package com.cezarykluczynski.stapi.model.magazine_series.repository;

import com.cezarykluczynski.stapi.model.common.repository.PageAwareRepository;
import com.cezarykluczynski.stapi.model.magazine_series.entity.MagazineSeries;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MagazineSeriesRepository extends JpaRepository<MagazineSeries, Long>, PageAwareRepository<MagazineSeries>,
		MagazineSeriesRepositoryCustom {
}
