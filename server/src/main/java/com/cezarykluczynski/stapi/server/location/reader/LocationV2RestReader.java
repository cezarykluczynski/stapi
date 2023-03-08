package com.cezarykluczynski.stapi.server.location.reader;

import com.cezarykluczynski.stapi.client.v1.rest.model.LocationV2BaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.LocationV2FullResponse;
import com.cezarykluczynski.stapi.model.location.entity.Location;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper;
import com.cezarykluczynski.stapi.server.common.reader.BaseReader;
import com.cezarykluczynski.stapi.server.common.reader.FullReader;
import com.cezarykluczynski.stapi.server.common.validator.StaticValidator;
import com.cezarykluczynski.stapi.server.location.dto.LocationV2RestBeanParams;
import com.cezarykluczynski.stapi.server.location.mapper.LocationBaseRestMapper;
import com.cezarykluczynski.stapi.server.location.mapper.LocationFullRestMapper;
import com.cezarykluczynski.stapi.server.location.query.LocationRestQuery;
import com.google.common.collect.Iterables;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class LocationV2RestReader implements BaseReader<LocationV2RestBeanParams, LocationV2BaseResponse>,
		FullReader<LocationV2FullResponse> {

	private final LocationRestQuery locationRestQuery;

	private final LocationBaseRestMapper locationBaseRestMapper;

	private final LocationFullRestMapper locationFullRestMapper;

	private final PageMapper pageMapper;

	private final SortMapper sortMapper;

	public LocationV2RestReader(LocationRestQuery locationRestQuery, LocationBaseRestMapper locationBaseRestMapper,
			LocationFullRestMapper locationFullRestMapper, PageMapper pageMapper, SortMapper sortMapper) {
		this.locationRestQuery = locationRestQuery;
		this.locationBaseRestMapper = locationBaseRestMapper;
		this.locationFullRestMapper = locationFullRestMapper;
		this.pageMapper = pageMapper;
		this.sortMapper = sortMapper;
	}

	@Override
	public LocationV2BaseResponse readBase(LocationV2RestBeanParams input) {
		Page<Location> locationV2Page = locationRestQuery.query(input);
		LocationV2BaseResponse locationV2Response = new LocationV2BaseResponse();
		locationV2Response.setPage(pageMapper.fromPageToRestResponsePage(locationV2Page));
		locationV2Response.setSort(sortMapper.map(input.getSort()));
		locationV2Response.getLocations().addAll(locationBaseRestMapper.mapV2Base(locationV2Page.getContent()));
		return locationV2Response;
	}

	@Override
	public LocationV2FullResponse readFull(String uid) {
		StaticValidator.requireUid(uid);
		LocationV2RestBeanParams locationV2RestBeanParams = new LocationV2RestBeanParams();
		locationV2RestBeanParams.setUid(uid);
		Page<Location> locationPage = locationRestQuery.query(locationV2RestBeanParams);
		LocationV2FullResponse locationV2Response = new LocationV2FullResponse();
		locationV2Response.setLocation(locationFullRestMapper.mapV2Full(Iterables.getOnlyElement(locationPage.getContent(), null)));
		return locationV2Response;
	}
}
