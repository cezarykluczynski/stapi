package com.cezarykluczynski.stapi.server.occupation.reader;

import com.cezarykluczynski.stapi.client.v1.soap.OccupationBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.OccupationBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.OccupationFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.OccupationFullResponse;
import com.cezarykluczynski.stapi.model.occupation.entity.Occupation;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper;
import com.cezarykluczynski.stapi.server.common.reader.BaseReader;
import com.cezarykluczynski.stapi.server.common.reader.FullReader;
import com.cezarykluczynski.stapi.server.common.validator.StaticValidator;
import com.cezarykluczynski.stapi.server.occupation.mapper.OccupationBaseSoapMapper;
import com.cezarykluczynski.stapi.server.occupation.mapper.OccupationFullSoapMapper;
import com.cezarykluczynski.stapi.server.occupation.query.OccupationSoapQuery;
import com.google.common.collect.Iterables;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class OccupationSoapReader implements BaseReader<OccupationBaseRequest, OccupationBaseResponse>,
		FullReader<OccupationFullRequest, OccupationFullResponse> {

	private final OccupationSoapQuery occupationSoapQuery;

	private final OccupationBaseSoapMapper occupationBaseSoapMapper;

	private final OccupationFullSoapMapper occupationFullSoapMapper;

	private final PageMapper pageMapper;

	private final SortMapper sortMapper;

	public OccupationSoapReader(OccupationSoapQuery occupationSoapQuery, OccupationBaseSoapMapper occupationBaseSoapMapper,
			OccupationFullSoapMapper occupationFullSoapMapper, PageMapper pageMapper, SortMapper sortMapper) {
		this.occupationSoapQuery = occupationSoapQuery;
		this.occupationBaseSoapMapper = occupationBaseSoapMapper;
		this.occupationFullSoapMapper = occupationFullSoapMapper;
		this.pageMapper = pageMapper;
		this.sortMapper = sortMapper;
	}

	@Override
	public OccupationBaseResponse readBase(OccupationBaseRequest input) {
		Page<Occupation> occupationPage = occupationSoapQuery.query(input);
		OccupationBaseResponse occupationResponse = new OccupationBaseResponse();
		occupationResponse.setPage(pageMapper.fromPageToSoapResponsePage(occupationPage));
		occupationResponse.setSort(sortMapper.map(input.getSort()));
		occupationResponse.getOccupations().addAll(occupationBaseSoapMapper.mapBase(occupationPage.getContent()));
		return occupationResponse;
	}

	@Override
	public OccupationFullResponse readFull(OccupationFullRequest input) {
		StaticValidator.requireUid(input.getUid());
		Page<Occupation> occupationPage = occupationSoapQuery.query(input);
		OccupationFullResponse occupationFullResponse = new OccupationFullResponse();
		occupationFullResponse.setOccupation(occupationFullSoapMapper.mapFull(Iterables.getOnlyElement(occupationPage.getContent(), null)));
		return occupationFullResponse;
	}

}
