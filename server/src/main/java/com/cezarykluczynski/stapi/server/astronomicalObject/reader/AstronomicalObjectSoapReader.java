package com.cezarykluczynski.stapi.server.astronomicalObject.reader;

import com.cezarykluczynski.stapi.client.v1.soap.AstronomicalObjectBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.AstronomicalObjectBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.AstronomicalObjectFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.AstronomicalObjectFullResponse;
import com.cezarykluczynski.stapi.model.astronomicalObject.entity.AstronomicalObject;
import com.cezarykluczynski.stapi.server.astronomicalObject.mapper.AstronomicalObjectSoapMapper;
import com.cezarykluczynski.stapi.server.astronomicalObject.query.AstronomicalObjectSoapQuery;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.reader.BaseReader;
import com.cezarykluczynski.stapi.server.common.reader.FullReader;
import com.google.common.collect.Iterables;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class AstronomicalObjectSoapReader implements BaseReader<AstronomicalObjectBaseRequest, AstronomicalObjectBaseResponse>,
		FullReader<AstronomicalObjectFullRequest, AstronomicalObjectFullResponse> {

	private AstronomicalObjectSoapQuery astronomicalObjectSoapQuery;

	private AstronomicalObjectSoapMapper astronomicalObjectSoapMapper;

	private PageMapper pageMapper;

	public AstronomicalObjectSoapReader(AstronomicalObjectSoapQuery astronomicalObjectSoapQuery,
			AstronomicalObjectSoapMapper astronomicalObjectSoapMapper, PageMapper pageMapper) {
		this.astronomicalObjectSoapQuery = astronomicalObjectSoapQuery;
		this.astronomicalObjectSoapMapper = astronomicalObjectSoapMapper;
		this.pageMapper = pageMapper;
	}

	@Override
	public AstronomicalObjectBaseResponse readBase(AstronomicalObjectBaseRequest input) {
		Page<AstronomicalObject> astronomicalObjectPage = astronomicalObjectSoapQuery.query(input);
		AstronomicalObjectBaseResponse astronomicalObjectResponse = new AstronomicalObjectBaseResponse();
		astronomicalObjectResponse.setPage(pageMapper.fromPageToSoapResponsePage(astronomicalObjectPage));
		astronomicalObjectResponse.getAstronomicalObjects().addAll(astronomicalObjectSoapMapper.mapBase(astronomicalObjectPage.getContent()));
		return astronomicalObjectResponse;
	}

	@Override
	public AstronomicalObjectFullResponse readFull(AstronomicalObjectFullRequest input) {
		Page<AstronomicalObject> astronomicalObjectPage = astronomicalObjectSoapQuery.query(input);
		AstronomicalObjectFullResponse astronomicalObjectFullResponse = new AstronomicalObjectFullResponse();
		astronomicalObjectFullResponse.setAstronomicalObject(astronomicalObjectSoapMapper
				.mapFull(Iterables.getOnlyElement(astronomicalObjectPage.getContent(), null)));
		return astronomicalObjectFullResponse;
	}

}
