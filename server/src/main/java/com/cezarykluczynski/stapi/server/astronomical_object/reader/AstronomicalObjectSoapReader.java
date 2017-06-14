package com.cezarykluczynski.stapi.server.astronomical_object.reader;

import com.cezarykluczynski.stapi.client.v1.soap.AstronomicalObjectBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.AstronomicalObjectBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.AstronomicalObjectFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.AstronomicalObjectFullResponse;
import com.cezarykluczynski.stapi.model.astronomical_object.entity.AstronomicalObject;
import com.cezarykluczynski.stapi.server.astronomical_object.mapper.AstronomicalObjectBaseSoapMapper;
import com.cezarykluczynski.stapi.server.astronomical_object.mapper.AstronomicalObjectFullSoapMapper;
import com.cezarykluczynski.stapi.server.astronomical_object.query.AstronomicalObjectSoapQuery;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper;
import com.cezarykluczynski.stapi.server.common.reader.BaseReader;
import com.cezarykluczynski.stapi.server.common.reader.FullReader;
import com.cezarykluczynski.stapi.server.common.validator.StaticValidator;
import com.google.common.collect.Iterables;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class AstronomicalObjectSoapReader implements BaseReader<AstronomicalObjectBaseRequest, AstronomicalObjectBaseResponse>,
		FullReader<AstronomicalObjectFullRequest, AstronomicalObjectFullResponse> {

	private final AstronomicalObjectSoapQuery astronomicalObjectSoapQuery;

	private final AstronomicalObjectBaseSoapMapper astronomicalObjectBaseSoapMapper;

	private final AstronomicalObjectFullSoapMapper astronomicalObjectFullSoapMapper;

	private final PageMapper pageMapper;

	private final SortMapper sortMapper;

	public AstronomicalObjectSoapReader(AstronomicalObjectSoapQuery astronomicalObjectSoapQuery,
			AstronomicalObjectBaseSoapMapper astronomicalObjectBaseSoapMapper, AstronomicalObjectFullSoapMapper astronomicalObjectFullSoapMapper,
			PageMapper pageMapper, SortMapper sortMapper) {
		this.astronomicalObjectSoapQuery = astronomicalObjectSoapQuery;
		this.astronomicalObjectBaseSoapMapper = astronomicalObjectBaseSoapMapper;
		this.astronomicalObjectFullSoapMapper = astronomicalObjectFullSoapMapper;
		this.pageMapper = pageMapper;
		this.sortMapper = sortMapper;
	}

	@Override
	public AstronomicalObjectBaseResponse readBase(AstronomicalObjectBaseRequest input) {
		Page<AstronomicalObject> astronomicalObjectPage = astronomicalObjectSoapQuery.query(input);
		AstronomicalObjectBaseResponse astronomicalObjectResponse = new AstronomicalObjectBaseResponse();
		astronomicalObjectResponse.setPage(pageMapper.fromPageToSoapResponsePage(astronomicalObjectPage));
		astronomicalObjectResponse.setSort(sortMapper.map(input.getSort()));
		astronomicalObjectResponse.getAstronomicalObjects().addAll(astronomicalObjectBaseSoapMapper.mapBase(astronomicalObjectPage.getContent()));
		return astronomicalObjectResponse;
	}

	@Override
	public AstronomicalObjectFullResponse readFull(AstronomicalObjectFullRequest input) {
		StaticValidator.requireUid(input.getUid());
		Page<AstronomicalObject> astronomicalObjectPage = astronomicalObjectSoapQuery.query(input);
		AstronomicalObjectFullResponse astronomicalObjectFullResponse = new AstronomicalObjectFullResponse();
		astronomicalObjectFullResponse.setAstronomicalObject(astronomicalObjectFullSoapMapper
				.mapFull(Iterables.getOnlyElement(astronomicalObjectPage.getContent(), null)));
		return astronomicalObjectFullResponse;
	}

}
