package com.cezarykluczynski.stapi.server.magazine.reader;

import com.cezarykluczynski.stapi.client.v1.rest.model.MagazineBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.MagazineFullResponse;
import com.cezarykluczynski.stapi.model.magazine.entity.Magazine;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper;
import com.cezarykluczynski.stapi.server.common.reader.BaseReader;
import com.cezarykluczynski.stapi.server.common.reader.FullReader;
import com.cezarykluczynski.stapi.server.common.validator.StaticValidator;
import com.cezarykluczynski.stapi.server.magazine.dto.MagazineRestBeanParams;
import com.cezarykluczynski.stapi.server.magazine.mapper.MagazineBaseRestMapper;
import com.cezarykluczynski.stapi.server.magazine.mapper.MagazineFullRestMapper;
import com.cezarykluczynski.stapi.server.magazine.query.MagazineRestQuery;
import com.google.common.collect.Iterables;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class MagazineRestReader implements BaseReader<MagazineRestBeanParams, MagazineBaseResponse>, FullReader<String, MagazineFullResponse> {

	private final MagazineRestQuery magazineRestQuery;

	private final MagazineBaseRestMapper magazineBaseRestMapper;

	private final MagazineFullRestMapper magazineFullRestMapper;

	private final PageMapper pageMapper;

	private final SortMapper sortMapper;

	public MagazineRestReader(MagazineRestQuery magazineRestQuery, MagazineBaseRestMapper magazineBaseRestMapper,
			MagazineFullRestMapper magazineFullRestMapper, PageMapper pageMapper, SortMapper sortMapper) {
		this.magazineRestQuery = magazineRestQuery;
		this.magazineBaseRestMapper = magazineBaseRestMapper;
		this.magazineFullRestMapper = magazineFullRestMapper;
		this.pageMapper = pageMapper;
		this.sortMapper = sortMapper;
	}

	@Override
	public MagazineBaseResponse readBase(MagazineRestBeanParams input) {
		Page<Magazine> magazinePage = magazineRestQuery.query(input);
		MagazineBaseResponse magazineResponse = new MagazineBaseResponse();
		magazineResponse.setPage(pageMapper.fromPageToRestResponsePage(magazinePage));
		magazineResponse.setSort(sortMapper.map(input.getSort()));
		magazineResponse.getMagazines().addAll(magazineBaseRestMapper.mapBase(magazinePage.getContent()));
		return magazineResponse;
	}

	@Override
	public MagazineFullResponse readFull(String uid) {
		StaticValidator.requireUid(uid);
		MagazineRestBeanParams magazineRestBeanParams = new MagazineRestBeanParams();
		magazineRestBeanParams.setUid(uid);
		Page<Magazine> magazinePage = magazineRestQuery.query(magazineRestBeanParams);
		MagazineFullResponse magazineResponse = new MagazineFullResponse();
		magazineResponse.setMagazine(magazineFullRestMapper.mapFull(Iterables.getOnlyElement(magazinePage.getContent(), null)));
		return magazineResponse;
	}

}
