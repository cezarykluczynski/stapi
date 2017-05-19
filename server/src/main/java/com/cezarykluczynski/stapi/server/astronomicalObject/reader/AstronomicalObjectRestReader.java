package com.cezarykluczynski.stapi.server.astronomicalObject.reader;

import com.cezarykluczynski.stapi.client.v1.rest.model.AstronomicalObjectBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.AstronomicalObjectFullResponse;
import com.cezarykluczynski.stapi.model.astronomicalObject.entity.AstronomicalObject;
import com.cezarykluczynski.stapi.server.astronomicalObject.dto.AstronomicalObjectRestBeanParams;
import com.cezarykluczynski.stapi.server.astronomicalObject.mapper.AstronomicalObjectBaseRestMapper;
import com.cezarykluczynski.stapi.server.astronomicalObject.mapper.AstronomicalObjectFullRestMapper;
import com.cezarykluczynski.stapi.server.astronomicalObject.query.AstronomicalObjectRestQuery;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.reader.BaseReader;
import com.cezarykluczynski.stapi.server.common.reader.FullReader;
import com.cezarykluczynski.stapi.server.common.validator.StaticValidator;
import com.google.common.collect.Iterables;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

@Service
public class AstronomicalObjectRestReader implements BaseReader<AstronomicalObjectRestBeanParams, AstronomicalObjectBaseResponse>,
		FullReader<String, AstronomicalObjectFullResponse> {

	private AstronomicalObjectRestQuery astronomicalObjectRestQuery;

	private AstronomicalObjectBaseRestMapper astronomicalObjectBaseRestMapper;

	private AstronomicalObjectFullRestMapper astronomicalObjectFullRestMapper;

	private PageMapper pageMapper;

	@Inject
	public AstronomicalObjectRestReader(AstronomicalObjectRestQuery astronomicalObjectRestQuery,
			AstronomicalObjectBaseRestMapper astronomicalObjectBaseRestMapper, AstronomicalObjectFullRestMapper astronomicalObjectFullRestMapper,
			PageMapper pageMapper) {
		this.astronomicalObjectRestQuery = astronomicalObjectRestQuery;
		this.astronomicalObjectBaseRestMapper = astronomicalObjectBaseRestMapper;
		this.astronomicalObjectFullRestMapper = astronomicalObjectFullRestMapper;
		this.pageMapper = pageMapper;
	}

	@Override
	public AstronomicalObjectBaseResponse readBase(AstronomicalObjectRestBeanParams astronomicalObjectRestBeanParams) {
		Page<AstronomicalObject> astronomicalObjectPage = astronomicalObjectRestQuery.query(astronomicalObjectRestBeanParams);
		AstronomicalObjectBaseResponse astronomicalObjectResponse = new AstronomicalObjectBaseResponse();
		astronomicalObjectResponse.setPage(pageMapper.fromPageToRestResponsePage(astronomicalObjectPage));
		astronomicalObjectResponse.getAstronomicalObjects().addAll(astronomicalObjectBaseRestMapper.mapBase(astronomicalObjectPage.getContent()));
		return astronomicalObjectResponse;
	}

	@Override
	public AstronomicalObjectFullResponse readFull(String uid) {
		StaticValidator.requireUid(uid);
		AstronomicalObjectRestBeanParams astronomicalObjectRestBeanParams = new AstronomicalObjectRestBeanParams();
		astronomicalObjectRestBeanParams.setUid(uid);
		Page<AstronomicalObject> astronomicalObjectPage = astronomicalObjectRestQuery.query(astronomicalObjectRestBeanParams);
		AstronomicalObjectFullResponse astronomicalObjectResponse = new AstronomicalObjectFullResponse();
		astronomicalObjectResponse.setAstronomicalObject(astronomicalObjectFullRestMapper
				.mapFull(Iterables.getOnlyElement(astronomicalObjectPage.getContent(), null)));
		return astronomicalObjectResponse;
	}

}
