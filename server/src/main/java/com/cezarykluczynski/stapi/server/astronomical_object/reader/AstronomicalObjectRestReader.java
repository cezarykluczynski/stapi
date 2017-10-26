package com.cezarykluczynski.stapi.server.astronomical_object.reader;

import com.cezarykluczynski.stapi.client.v1.rest.model.AstronomicalObjectBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.AstronomicalObjectFullResponse;
import com.cezarykluczynski.stapi.model.astronomical_object.entity.AstronomicalObject;
import com.cezarykluczynski.stapi.server.astronomical_object.dto.AstronomicalObjectRestBeanParams;
import com.cezarykluczynski.stapi.server.astronomical_object.mapper.AstronomicalObjectBaseRestMapper;
import com.cezarykluczynski.stapi.server.astronomical_object.mapper.AstronomicalObjectFullRestMapper;
import com.cezarykluczynski.stapi.server.astronomical_object.query.AstronomicalObjectRestQuery;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper;
import com.cezarykluczynski.stapi.server.common.reader.BaseReader;
import com.cezarykluczynski.stapi.server.common.reader.FullReader;
import com.cezarykluczynski.stapi.server.common.validator.StaticValidator;
import com.google.common.collect.Iterables;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class AstronomicalObjectRestReader implements BaseReader<AstronomicalObjectRestBeanParams, AstronomicalObjectBaseResponse>,
		FullReader<String, AstronomicalObjectFullResponse> {

	private final AstronomicalObjectRestQuery astronomicalObjectRestQuery;

	private final AstronomicalObjectBaseRestMapper astronomicalObjectBaseRestMapper;

	private final AstronomicalObjectFullRestMapper astronomicalObjectFullRestMapper;

	private final PageMapper pageMapper;

	private final SortMapper sortMapper;

	public AstronomicalObjectRestReader(AstronomicalObjectRestQuery astronomicalObjectRestQuery,
			AstronomicalObjectBaseRestMapper astronomicalObjectBaseRestMapper, AstronomicalObjectFullRestMapper astronomicalObjectFullRestMapper,
			PageMapper pageMapper, SortMapper sortMapper) {
		this.astronomicalObjectRestQuery = astronomicalObjectRestQuery;
		this.astronomicalObjectBaseRestMapper = astronomicalObjectBaseRestMapper;
		this.astronomicalObjectFullRestMapper = astronomicalObjectFullRestMapper;
		this.pageMapper = pageMapper;
		this.sortMapper = sortMapper;
	}

	@Override
	public AstronomicalObjectBaseResponse readBase(AstronomicalObjectRestBeanParams input) {
		Page<AstronomicalObject> astronomicalObjectPage = astronomicalObjectRestQuery.query(input);
		AstronomicalObjectBaseResponse astronomicalObjectResponse = new AstronomicalObjectBaseResponse();
		astronomicalObjectResponse.setPage(pageMapper.fromPageToRestResponsePage(astronomicalObjectPage));
		astronomicalObjectResponse.setSort(sortMapper.map(input.getSort()));
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
