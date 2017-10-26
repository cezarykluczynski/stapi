package com.cezarykluczynski.stapi.server.magazine_series.reader;

import com.cezarykluczynski.stapi.client.v1.rest.model.MagazineSeriesBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.MagazineSeriesFullResponse;
import com.cezarykluczynski.stapi.model.magazine_series.entity.MagazineSeries;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper;
import com.cezarykluczynski.stapi.server.common.reader.BaseReader;
import com.cezarykluczynski.stapi.server.common.reader.FullReader;
import com.cezarykluczynski.stapi.server.common.validator.StaticValidator;
import com.cezarykluczynski.stapi.server.magazine_series.dto.MagazineSeriesRestBeanParams;
import com.cezarykluczynski.stapi.server.magazine_series.mapper.MagazineSeriesBaseRestMapper;
import com.cezarykluczynski.stapi.server.magazine_series.mapper.MagazineSeriesFullRestMapper;
import com.cezarykluczynski.stapi.server.magazine_series.query.MagazineSeriesRestQuery;
import com.google.common.collect.Iterables;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class MagazineSeriesRestReader implements BaseReader<MagazineSeriesRestBeanParams, MagazineSeriesBaseResponse>,
		FullReader<String, MagazineSeriesFullResponse> {

	private final MagazineSeriesRestQuery magazineSeriesRestQuery;

	private final MagazineSeriesBaseRestMapper magazineSeriesBaseRestMapper;

	private final MagazineSeriesFullRestMapper magazineSeriesFullRestMapper;

	private final PageMapper pageMapper;

	private final SortMapper sortMapper;

	public MagazineSeriesRestReader(MagazineSeriesRestQuery magazineSeriesRestQuery, MagazineSeriesBaseRestMapper magazineSeriesBaseRestMapper,
			MagazineSeriesFullRestMapper magazineSeriesFullRestMapper, PageMapper pageMapper, SortMapper sortMapper) {
		this.magazineSeriesRestQuery = magazineSeriesRestQuery;
		this.magazineSeriesBaseRestMapper = magazineSeriesBaseRestMapper;
		this.magazineSeriesFullRestMapper = magazineSeriesFullRestMapper;
		this.pageMapper = pageMapper;
		this.sortMapper = sortMapper;
	}

	@Override
	public MagazineSeriesBaseResponse readBase(MagazineSeriesRestBeanParams input) {
		Page<MagazineSeries> magazineSeries = magazineSeriesRestQuery.query(input);
		MagazineSeriesBaseResponse magazineSeriesBaseResponse = new MagazineSeriesBaseResponse();
		magazineSeriesBaseResponse.setPage(pageMapper.fromPageToRestResponsePage(magazineSeries));
		magazineSeriesBaseResponse.setSort(sortMapper.map(input.getSort()));
		magazineSeriesBaseResponse.getMagazineSeries().addAll(magazineSeriesBaseRestMapper.mapBase(magazineSeries.getContent()));
		return magazineSeriesBaseResponse;
	}

	@Override
	public MagazineSeriesFullResponse readFull(String uid) {
		StaticValidator.requireUid(uid);
		MagazineSeriesRestBeanParams magazineSeriesRestBeanParams = new MagazineSeriesRestBeanParams();
		magazineSeriesRestBeanParams.setUid(uid);
		Page<MagazineSeries> magazineSeriesPage = magazineSeriesRestQuery.query(magazineSeriesRestBeanParams);
		MagazineSeriesFullResponse magazineSeriesFullResponse = new MagazineSeriesFullResponse();
		magazineSeriesFullResponse.setMagazineSeries(magazineSeriesFullRestMapper
				.mapFull(Iterables.getOnlyElement(magazineSeriesPage.getContent(), null)));
		return magazineSeriesFullResponse;
	}

}
