package com.cezarykluczynski.stapi.server.astronomicalObject.reader;

import com.cezarykluczynski.stapi.client.v1.soap.AstronomicalObjectRequest;
import com.cezarykluczynski.stapi.client.v1.soap.AstronomicalObjectResponse;
import com.cezarykluczynski.stapi.model.astronomicalObject.entity.AstronomicalObject;
import com.cezarykluczynski.stapi.server.astronomicalObject.mapper.AstronomicalObjectSoapMapper;
import com.cezarykluczynski.stapi.server.astronomicalObject.query.AstronomicalObjectSoapQuery;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.reader.BaseReader;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class AstronomicalObjectSoapReader implements BaseReader<AstronomicalObjectRequest, AstronomicalObjectResponse> {

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
	public AstronomicalObjectResponse readBase(AstronomicalObjectRequest input) {
		Page<AstronomicalObject> astronomicalObjectPage = astronomicalObjectSoapQuery.query(input);
		AstronomicalObjectResponse astronomicalObjectResponse = new AstronomicalObjectResponse();
		astronomicalObjectResponse.setPage(pageMapper.fromPageToSoapResponsePage(astronomicalObjectPage));
		astronomicalObjectResponse.getAstronomicalObjects().addAll(astronomicalObjectSoapMapper.map(astronomicalObjectPage.getContent()));
		return astronomicalObjectResponse;
	}

}
