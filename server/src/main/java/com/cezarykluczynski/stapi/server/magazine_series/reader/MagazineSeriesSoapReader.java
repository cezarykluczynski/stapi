package com.cezarykluczynski.stapi.server.magazine_series.reader;

import com.cezarykluczynski.stapi.client.v1.soap.MagazineSeriesBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.MagazineSeriesBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.MagazineSeriesFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.MagazineSeriesFullResponse;
import com.cezarykluczynski.stapi.model.magazine_series.entity.MagazineSeries;
import com.cezarykluczynski.stapi.server.magazine_series.mapper.MagazineSeriesBaseSoapMapper;
import com.cezarykluczynski.stapi.server.magazine_series.mapper.MagazineSeriesFullSoapMapper;
import com.cezarykluczynski.stapi.server.magazine_series.query.MagazineSeriesSoapQuery;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper;
import com.cezarykluczynski.stapi.server.common.reader.BaseReader;
import com.cezarykluczynski.stapi.server.common.reader.FullReader;
import com.cezarykluczynski.stapi.server.common.validator.StaticValidator;
import com.google.common.collect.Iterables;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class MagazineSeriesSoapReader implements BaseReader<MagazineSeriesBaseRequest, MagazineSeriesBaseResponse>,
		FullReader<MagazineSeriesFullRequest, MagazineSeriesFullResponse> {

	private final MagazineSeriesSoapQuery magazineSeriesSoapQuery;

	private final MagazineSeriesBaseSoapMapper magazineSeriesBaseSoapMapper;

	private final MagazineSeriesFullSoapMapper magazineSeriesFullSoapMapper;

	private final PageMapper pageMapper;

	private final SortMapper sortMapper;

	public MagazineSeriesSoapReader(MagazineSeriesSoapQuery magazineSeriesSoapQuery, MagazineSeriesBaseSoapMapper magazineSeriesBaseSoapMapper,
			MagazineSeriesFullSoapMapper magazineSeriesFullSoapMapper, PageMapper pageMapper, SortMapper sortMapper) {
		this.magazineSeriesSoapQuery = magazineSeriesSoapQuery;
		this.magazineSeriesBaseSoapMapper = magazineSeriesBaseSoapMapper;
		this.magazineSeriesFullSoapMapper = magazineSeriesFullSoapMapper;
		this.pageMapper = pageMapper;
		this.sortMapper = sortMapper;
	}

	@Override
	public MagazineSeriesBaseResponse readBase(MagazineSeriesBaseRequest input) {
		Page<MagazineSeries> magazineSeriesPage = magazineSeriesSoapQuery.query(input);
		MagazineSeriesBaseResponse magazineSeriesBaseResponse = new MagazineSeriesBaseResponse();
		magazineSeriesBaseResponse.setPage(pageMapper.fromPageToSoapResponsePage(magazineSeriesPage));
		magazineSeriesBaseResponse.setSort(sortMapper.map(input.getSort()));
		magazineSeriesBaseResponse.getMagazineSeries().addAll(magazineSeriesBaseSoapMapper.mapBase(magazineSeriesPage.getContent()));
		return magazineSeriesBaseResponse;
	}

	@Override
	public MagazineSeriesFullResponse readFull(MagazineSeriesFullRequest input) {
		StaticValidator.requireUid(input.getUid());
		Page<MagazineSeries> magazineSeriesPage = magazineSeriesSoapQuery.query(input);
		MagazineSeriesFullResponse magazineSeriesFullResponse = new MagazineSeriesFullResponse();
		magazineSeriesFullResponse.setMagazineSeries(magazineSeriesFullSoapMapper
				.mapFull(Iterables.getOnlyElement(magazineSeriesPage.getContent(), null)));
		return magazineSeriesFullResponse;
	}

}
