package com.cezarykluczynski.stapi.server.spacecraft.reader;

import com.cezarykluczynski.stapi.client.v1.rest.model.SpacecraftV2BaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.SpacecraftV2FullResponse;
import com.cezarykluczynski.stapi.model.spacecraft.entity.Spacecraft;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper;
import com.cezarykluczynski.stapi.server.common.reader.BaseReader;
import com.cezarykluczynski.stapi.server.common.reader.FullReader;
import com.cezarykluczynski.stapi.server.common.validator.StaticValidator;
import com.cezarykluczynski.stapi.server.spacecraft.dto.SpacecraftV2RestBeanParams;
import com.cezarykluczynski.stapi.server.spacecraft.mapper.SpacecraftBaseRestMapper;
import com.cezarykluczynski.stapi.server.spacecraft.mapper.SpacecraftFullRestMapper;
import com.cezarykluczynski.stapi.server.spacecraft.query.SpacecraftRestQuery;
import com.google.common.collect.Iterables;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class SpacecraftV2RestReader implements BaseReader<SpacecraftV2RestBeanParams, SpacecraftV2BaseResponse>,
		FullReader<SpacecraftV2FullResponse> {

	private final SpacecraftRestQuery spacecraftRestQuery;

	private final SpacecraftBaseRestMapper spacecraftBaseRestMapper;

	private final SpacecraftFullRestMapper spacecraftFullRestMapper;

	private final PageMapper pageMapper;

	private final SortMapper sortMapper;

	public SpacecraftV2RestReader(SpacecraftRestQuery spacecraftRestQuery, SpacecraftBaseRestMapper spacecraftBaseRestMapper,
			SpacecraftFullRestMapper spacecraftFullRestMapper, PageMapper pageMapper, SortMapper sortMapper) {
		this.spacecraftRestQuery = spacecraftRestQuery;
		this.spacecraftBaseRestMapper = spacecraftBaseRestMapper;
		this.spacecraftFullRestMapper = spacecraftFullRestMapper;
		this.pageMapper = pageMapper;
		this.sortMapper = sortMapper;
	}

	@Override
	public SpacecraftV2BaseResponse readBase(SpacecraftV2RestBeanParams input) {
		Page<Spacecraft> spacecraftPage = spacecraftRestQuery.query(input);
		SpacecraftV2BaseResponse spacecraftResponse = new SpacecraftV2BaseResponse();
		spacecraftResponse.setPage(pageMapper.fromPageToRestResponsePage(spacecraftPage));
		spacecraftResponse.setSort(sortMapper.map(input.getSort()));
		spacecraftResponse.getSpacecrafts().addAll(spacecraftBaseRestMapper.mapV2Base(spacecraftPage.getContent()));
		return spacecraftResponse;
	}

	@Override
	public SpacecraftV2FullResponse readFull(String uid) {
		StaticValidator.requireUid(uid);
		SpacecraftV2RestBeanParams spacecraftRestBeanParams = new SpacecraftV2RestBeanParams();
		spacecraftRestBeanParams.setUid(uid);
		Page<Spacecraft> spacecraftPage = spacecraftRestQuery.query(spacecraftRestBeanParams);
		SpacecraftV2FullResponse spacecraftResponse = new SpacecraftV2FullResponse();
		spacecraftResponse.setSpacecraft(spacecraftFullRestMapper.mapV2Full(Iterables.getOnlyElement(spacecraftPage.getContent(), null)));
		return spacecraftResponse;
	}

}
