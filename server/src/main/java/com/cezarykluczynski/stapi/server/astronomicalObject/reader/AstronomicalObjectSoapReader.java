package com.cezarykluczynski.stapi.server.astronomicalObject.reader;

import com.cezarykluczynski.stapi.client.v1.soap.AstronomicalObjectBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.AstronomicalObjectBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.AstronomicalObjectFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.AstronomicalObjectFullResponse;
import com.cezarykluczynski.stapi.model.astronomicalObject.entity.AstronomicalObject;
import com.cezarykluczynski.stapi.server.astronomicalObject.mapper.AstronomicalObjectBaseSoapMapper;
import com.cezarykluczynski.stapi.server.astronomicalObject.mapper.AstronomicalObjectFullSoapMapper;
import com.cezarykluczynski.stapi.server.astronomicalObject.query.AstronomicalObjectSoapQuery;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.reader.BaseReader;
import com.cezarykluczynski.stapi.server.common.reader.FullReader;
import com.cezarykluczynski.stapi.server.common.validator.StaticValidator;
import com.google.common.collect.Iterables;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class AstronomicalObjectSoapReader implements BaseReader<AstronomicalObjectBaseRequest, AstronomicalObjectBaseResponse>,
		FullReader<AstronomicalObjectFullRequest, AstronomicalObjectFullResponse> {

	private AstronomicalObjectSoapQuery astronomicalObjectSoapQuery;

	private AstronomicalObjectBaseSoapMapper astronomicalObjectBaseSoapMapper;

	private AstronomicalObjectFullSoapMapper astronomicalObjectFullSoapMapper;

	private PageMapper pageMapper;

	public AstronomicalObjectSoapReader(AstronomicalObjectSoapQuery astronomicalObjectSoapQuery,
			AstronomicalObjectBaseSoapMapper astronomicalObjectBaseSoapMapper, AstronomicalObjectFullSoapMapper astronomicalObjectFullSoapMapper,
			PageMapper pageMapper) {
		this.astronomicalObjectSoapQuery = astronomicalObjectSoapQuery;
		this.astronomicalObjectBaseSoapMapper = astronomicalObjectBaseSoapMapper;
		this.astronomicalObjectFullSoapMapper = astronomicalObjectFullSoapMapper;
		this.pageMapper = pageMapper;
	}

	@Override
	public AstronomicalObjectBaseResponse readBase(AstronomicalObjectBaseRequest input) {
		Page<AstronomicalObject> astronomicalObjectPage = astronomicalObjectSoapQuery.query(input);
		AstronomicalObjectBaseResponse astronomicalObjectResponse = new AstronomicalObjectBaseResponse();
		astronomicalObjectResponse.setPage(pageMapper.fromPageToSoapResponsePage(astronomicalObjectPage));
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
