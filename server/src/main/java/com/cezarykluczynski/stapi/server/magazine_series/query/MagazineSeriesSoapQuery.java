package com.cezarykluczynski.stapi.server.magazine_series.query;

import com.cezarykluczynski.stapi.client.v1.soap.MagazineSeriesBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.MagazineSeriesFullRequest;
import com.cezarykluczynski.stapi.model.magazine_series.dto.MagazineSeriesRequestDTO;
import com.cezarykluczynski.stapi.model.magazine_series.entity.MagazineSeries;
import com.cezarykluczynski.stapi.model.magazine_series.repository.MagazineSeriesRepository;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.magazine_series.mapper.MagazineSeriesBaseSoapMapper;
import com.cezarykluczynski.stapi.server.magazine_series.mapper.MagazineSeriesFullSoapMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class MagazineSeriesSoapQuery {

	private final MagazineSeriesBaseSoapMapper magazineSeriesBaseSoapMapper;

	private final MagazineSeriesFullSoapMapper magazineSeriesFullSoapMapper;

	private final PageMapper pageMapper;

	private final MagazineSeriesRepository magazineSeriesRepository;

	public MagazineSeriesSoapQuery(MagazineSeriesBaseSoapMapper magazineSeriesBaseSoapMapper,
			MagazineSeriesFullSoapMapper magazineSeriesFullSoapMapper, PageMapper pageMapper, MagazineSeriesRepository magazineSeriesRepository) {
		this.magazineSeriesBaseSoapMapper = magazineSeriesBaseSoapMapper;
		this.magazineSeriesFullSoapMapper = magazineSeriesFullSoapMapper;
		this.pageMapper = pageMapper;
		this.magazineSeriesRepository = magazineSeriesRepository;
	}

	public Page<MagazineSeries> query(MagazineSeriesBaseRequest magazineSeriesBaseRequest) {
		MagazineSeriesRequestDTO magazineSeriesRequestDTO = magazineSeriesBaseSoapMapper.mapBase(magazineSeriesBaseRequest);
		PageRequest pageRequest = pageMapper.fromRequestPageToPageRequest(magazineSeriesBaseRequest.getPage());
		return magazineSeriesRepository.findMatching(magazineSeriesRequestDTO, pageRequest);
	}

	public Page<MagazineSeries> query(MagazineSeriesFullRequest magazineSeriesFullRequest) {
		MagazineSeriesRequestDTO magazineSeriesRequestDTO = magazineSeriesFullSoapMapper.mapFull(magazineSeriesFullRequest);
		return magazineSeriesRepository.findMatching(magazineSeriesRequestDTO, pageMapper.getDefaultPageRequest());
	}

}
