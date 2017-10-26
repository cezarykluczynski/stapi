package com.cezarykluczynski.stapi.server.magazine_series.query;

import com.cezarykluczynski.stapi.model.magazine_series.dto.MagazineSeriesRequestDTO;
import com.cezarykluczynski.stapi.model.magazine_series.entity.MagazineSeries;
import com.cezarykluczynski.stapi.model.magazine_series.repository.MagazineSeriesRepository;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.magazine_series.dto.MagazineSeriesRestBeanParams;
import com.cezarykluczynski.stapi.server.magazine_series.mapper.MagazineSeriesBaseRestMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class MagazineSeriesRestQuery {

	private final MagazineSeriesBaseRestMapper magazineSeriesBaseRestMapper;

	private final PageMapper pageMapper;

	private final MagazineSeriesRepository magazineSeriesRepository;

	public MagazineSeriesRestQuery(MagazineSeriesBaseRestMapper magazineSeriesBaseRestMapper, PageMapper pageMapper,
			MagazineSeriesRepository magazineSeriesRepository) {
		this.magazineSeriesBaseRestMapper = magazineSeriesBaseRestMapper;
		this.pageMapper = pageMapper;
		this.magazineSeriesRepository = magazineSeriesRepository;
	}

	public Page<MagazineSeries> query(MagazineSeriesRestBeanParams magazineSeriesRestBeanParams) {
		MagazineSeriesRequestDTO magazineSeriesRequestDTO = magazineSeriesBaseRestMapper.mapBase(magazineSeriesRestBeanParams);
		PageRequest pageRequest = pageMapper.fromPageSortBeanParamsToPageRequest(magazineSeriesRestBeanParams);
		return magazineSeriesRepository.findMatching(magazineSeriesRequestDTO, pageRequest);
	}

}
