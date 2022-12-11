package com.cezarykluczynski.stapi.model.magazine_series.repository;

import com.cezarykluczynski.stapi.model.common.repository.CriteriaMatcher;
import com.cezarykluczynski.stapi.model.magazine_series.dto.MagazineSeriesRequestDTO;
import com.cezarykluczynski.stapi.model.magazine_series.entity.MagazineSeries;

public interface MagazineSeriesRepositoryCustom extends CriteriaMatcher<MagazineSeriesRequestDTO, MagazineSeries> {
}
