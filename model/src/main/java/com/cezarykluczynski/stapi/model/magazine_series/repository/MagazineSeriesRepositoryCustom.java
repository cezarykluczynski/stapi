package com.cezarykluczynski.stapi.model.magazine_series.repository;

import com.cezarykluczynski.stapi.model.magazine_series.dto.MagazineSeriesRequestDTO;
import com.cezarykluczynski.stapi.model.magazine_series.entity.MagazineSeries;
import com.cezarykluczynski.stapi.model.common.repository.CriteriaMatcher;

public interface MagazineSeriesRepositoryCustom extends CriteriaMatcher<MagazineSeriesRequestDTO, MagazineSeries> {
}
