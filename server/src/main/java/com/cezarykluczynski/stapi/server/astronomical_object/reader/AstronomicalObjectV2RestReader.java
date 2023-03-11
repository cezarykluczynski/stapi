package com.cezarykluczynski.stapi.server.astronomical_object.reader;

import com.cezarykluczynski.stapi.client.rest.model.AstronomicalObjectV2BaseResponse;
import com.cezarykluczynski.stapi.client.rest.model.AstronomicalObjectV2FullResponse;
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
public class AstronomicalObjectV2RestReader implements BaseReader<AstronomicalObjectRestBeanParams, AstronomicalObjectV2BaseResponse>,
		FullReader<AstronomicalObjectV2FullResponse> {

	private final AstronomicalObjectRestQuery astronomicalObjectRestQuery;

	private final AstronomicalObjectBaseRestMapper astronomicalObjectBaseRestMapper;

	private final AstronomicalObjectFullRestMapper astronomicalObjectFullRestMapper;

	private final PageMapper pageMapper;

	private final SortMapper sortMapper;

	public AstronomicalObjectV2RestReader(AstronomicalObjectRestQuery astronomicalObjectRestQuery,
			AstronomicalObjectBaseRestMapper astronomicalObjectBaseRestMapper, AstronomicalObjectFullRestMapper astronomicalObjectFullRestMapper,
			PageMapper pageMapper, SortMapper sortMapper) {
		this.astronomicalObjectRestQuery = astronomicalObjectRestQuery;
		this.astronomicalObjectBaseRestMapper = astronomicalObjectBaseRestMapper;
		this.astronomicalObjectFullRestMapper = astronomicalObjectFullRestMapper;
		this.pageMapper = pageMapper;
		this.sortMapper = sortMapper;
	}

	@Override
	public AstronomicalObjectV2BaseResponse readBase(AstronomicalObjectRestBeanParams input) {
		Page<AstronomicalObject> astronomicalObjectPage = astronomicalObjectRestQuery.query(input);
		AstronomicalObjectV2BaseResponse astronomicalObjectResponse = new AstronomicalObjectV2BaseResponse();
		astronomicalObjectResponse.setPage(pageMapper.fromPageToRestResponsePage(astronomicalObjectPage));
		astronomicalObjectResponse.setSort(sortMapper.map(input.getSort()));
		astronomicalObjectResponse.getAstronomicalObjects().addAll(astronomicalObjectBaseRestMapper.mapV2Base(astronomicalObjectPage.getContent()));
		return astronomicalObjectResponse;
	}

	@Override
	public AstronomicalObjectV2FullResponse readFull(String uid) {
		StaticValidator.requireUid(uid);
		AstronomicalObjectRestBeanParams astronomicalObjectV2RestBeanParams = new AstronomicalObjectRestBeanParams();
		astronomicalObjectV2RestBeanParams.setUid(uid);
		Page<AstronomicalObject> astronomicalObjectPage = astronomicalObjectRestQuery.query(astronomicalObjectV2RestBeanParams);
		AstronomicalObjectV2FullResponse astronomicalObjectResponse = new AstronomicalObjectV2FullResponse();
		astronomicalObjectResponse.setAstronomicalObject(astronomicalObjectFullRestMapper
				.mapV2Full(Iterables.getOnlyElement(astronomicalObjectPage.getContent(), null)));
		return astronomicalObjectResponse;
	}

}
