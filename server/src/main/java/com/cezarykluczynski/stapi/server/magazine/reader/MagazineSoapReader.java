package com.cezarykluczynski.stapi.server.magazine.reader;

import com.cezarykluczynski.stapi.client.v1.soap.MagazineBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.MagazineBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.MagazineFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.MagazineFullResponse;
import com.cezarykluczynski.stapi.model.magazine.entity.Magazine;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper;
import com.cezarykluczynski.stapi.server.common.reader.BaseReader;
import com.cezarykluczynski.stapi.server.common.reader.FullReader;
import com.cezarykluczynski.stapi.server.common.validator.StaticValidator;
import com.cezarykluczynski.stapi.server.magazine.mapper.MagazineBaseSoapMapper;
import com.cezarykluczynski.stapi.server.magazine.mapper.MagazineFullSoapMapper;
import com.cezarykluczynski.stapi.server.magazine.query.MagazineSoapQuery;
import com.google.common.collect.Iterables;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class MagazineSoapReader implements BaseReader<MagazineBaseRequest, MagazineBaseResponse>,
		FullReader<MagazineFullRequest, MagazineFullResponse> {

	private final MagazineSoapQuery magazineSoapQuery;

	private final MagazineBaseSoapMapper magazineBaseSoapMapper;

	private final MagazineFullSoapMapper magazineFullSoapMapper;

	private final PageMapper pageMapper;

	private final SortMapper sortMapper;

	public MagazineSoapReader(MagazineSoapQuery magazineSoapQuery, MagazineBaseSoapMapper magazineBaseSoapMapper,
			MagazineFullSoapMapper magazineFullSoapMapper, PageMapper pageMapper, SortMapper sortMapper) {
		this.magazineSoapQuery = magazineSoapQuery;
		this.magazineBaseSoapMapper = magazineBaseSoapMapper;
		this.magazineFullSoapMapper = magazineFullSoapMapper;
		this.pageMapper = pageMapper;
		this.sortMapper = sortMapper;
	}

	@Override
	public MagazineBaseResponse readBase(MagazineBaseRequest input) {
		Page<Magazine> magazinePage = magazineSoapQuery.query(input);
		MagazineBaseResponse magazineResponse = new MagazineBaseResponse();
		magazineResponse.setPage(pageMapper.fromPageToSoapResponsePage(magazinePage));
		magazineResponse.setSort(sortMapper.map(input.getSort()));
		magazineResponse.getMagazine().addAll(magazineBaseSoapMapper.mapBase(magazinePage.getContent()));
		return magazineResponse;
	}

	@Override
	public MagazineFullResponse readFull(MagazineFullRequest input) {
		StaticValidator.requireUid(input.getUid());
		Page<Magazine> magazinePage = magazineSoapQuery.query(input);
		MagazineFullResponse magazineFullResponse = new MagazineFullResponse();
		magazineFullResponse.setMagazine(magazineFullSoapMapper.mapFull(Iterables.getOnlyElement(magazinePage.getContent(), null)));
		return magazineFullResponse;
	}

}
