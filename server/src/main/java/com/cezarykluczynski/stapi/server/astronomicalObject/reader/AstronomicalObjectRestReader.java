package com.cezarykluczynski.stapi.server.astronomicalObject.reader;

import com.cezarykluczynski.stapi.client.v1.rest.model.AstronomicalObjectResponse;
import com.cezarykluczynski.stapi.model.astronomicalObject.entity.AstronomicalObject;
import com.cezarykluczynski.stapi.server.astronomicalObject.dto.AstronomicalObjectRestBeanParams;
import com.cezarykluczynski.stapi.server.astronomicalObject.mapper.AstronomicalObjectRestMapper;
import com.cezarykluczynski.stapi.server.astronomicalObject.query.AstronomicalObjectRestQuery;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.reader.Reader;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class AstronomicalObjectRestReader implements Reader<AstronomicalObjectRestBeanParams, AstronomicalObjectResponse> {

	private AstronomicalObjectRestQuery astronomicalObjectRestQuery;

	private AstronomicalObjectRestMapper astronomicalObjectRestMapper;

	private PageMapper pageMapper;

	@Inject
	public AstronomicalObjectRestReader(AstronomicalObjectRestQuery astronomicalObjectRestQuery,
			AstronomicalObjectRestMapper astronomicalObjectRestMapper, PageMapper pageMapper) {
		this.astronomicalObjectRestQuery = astronomicalObjectRestQuery;
		this.astronomicalObjectRestMapper = astronomicalObjectRestMapper;
		this.pageMapper = pageMapper;
	}

	@Override
	public AstronomicalObjectResponse read(AstronomicalObjectRestBeanParams input) {
		Page<AstronomicalObject> astronomicalObjectPage = astronomicalObjectRestQuery.query(input);
		AstronomicalObjectResponse astronomicalObjectResponse = new AstronomicalObjectResponse();
		astronomicalObjectResponse.setPage(pageMapper.fromPageToRestResponsePage(astronomicalObjectPage));
		astronomicalObjectResponse.getAstronomicalObjects().addAll(astronomicalObjectRestMapper.map(astronomicalObjectPage.getContent()));
		return astronomicalObjectResponse;
	}

}
