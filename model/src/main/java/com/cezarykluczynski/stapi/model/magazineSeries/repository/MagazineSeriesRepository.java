package com.cezarykluczynski.stapi.model.magazineSeries.repository;

import com.cezarykluczynski.stapi.model.common.repository.PageAwareRepository;
import com.cezarykluczynski.stapi.model.magazineSeries.entity.MagazineSeries;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MagazineSeriesRepository extends JpaRepository<MagazineSeries, Long>, PageAwareRepository<MagazineSeries> {
}
