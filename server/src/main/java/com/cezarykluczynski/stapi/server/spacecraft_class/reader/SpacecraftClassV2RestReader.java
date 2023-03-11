package com.cezarykluczynski.stapi.server.spacecraft_class.reader;

import com.cezarykluczynski.stapi.client.rest.model.SpacecraftClassV2BaseResponse;
import com.cezarykluczynski.stapi.client.rest.model.SpacecraftClassV2FullResponse;
import com.cezarykluczynski.stapi.model.spacecraft_class.entity.SpacecraftClass;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper;
import com.cezarykluczynski.stapi.server.common.reader.BaseReader;
import com.cezarykluczynski.stapi.server.common.reader.FullReader;
import com.cezarykluczynski.stapi.server.common.validator.StaticValidator;
import com.cezarykluczynski.stapi.server.spacecraft_class.dto.SpacecraftClassV2RestBeanParams;
import com.cezarykluczynski.stapi.server.spacecraft_class.mapper.SpacecraftClassBaseRestMapper;
import com.cezarykluczynski.stapi.server.spacecraft_class.mapper.SpacecraftClassFullRestMapper;
import com.cezarykluczynski.stapi.server.spacecraft_class.query.SpacecraftClassRestQuery;
import com.google.common.collect.Iterables;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class SpacecraftClassV2RestReader implements BaseReader<SpacecraftClassV2RestBeanParams, SpacecraftClassV2BaseResponse>,
		FullReader<SpacecraftClassV2FullResponse> {

	private final SpacecraftClassRestQuery spacecraftClassRestQuery;

	private final SpacecraftClassBaseRestMapper spacecraftClassBaseRestMapper;

	private final SpacecraftClassFullRestMapper spacecraftClassFullRestMapper;

	private final PageMapper pageMapper;

	private final SortMapper sortMapper;

	public SpacecraftClassV2RestReader(SpacecraftClassRestQuery spacecraftClassRestQuery, SpacecraftClassBaseRestMapper spacecraftClassBaseRestMapper,
			SpacecraftClassFullRestMapper spacecraftClassFullRestMapper, PageMapper pageMapper, SortMapper sortMapper) {
		this.spacecraftClassRestQuery = spacecraftClassRestQuery;
		this.spacecraftClassBaseRestMapper = spacecraftClassBaseRestMapper;
		this.spacecraftClassFullRestMapper = spacecraftClassFullRestMapper;
		this.pageMapper = pageMapper;
		this.sortMapper = sortMapper;
	}

	@Override
	public SpacecraftClassV2BaseResponse readBase(SpacecraftClassV2RestBeanParams input) {
		Page<SpacecraftClass> spacecraftClassPage = spacecraftClassRestQuery.query(input);
		SpacecraftClassV2BaseResponse spacecraftClassResponse = new SpacecraftClassV2BaseResponse();
		spacecraftClassResponse.setPage(pageMapper.fromPageToRestResponsePage(spacecraftClassPage));
		spacecraftClassResponse.setSort(sortMapper.map(input.getSort()));
		spacecraftClassResponse.getSpacecraftClasses().addAll(spacecraftClassBaseRestMapper.mapV2Base(spacecraftClassPage.getContent()));
		return spacecraftClassResponse;
	}

	@Override
	public SpacecraftClassV2FullResponse readFull(String uid) {
		StaticValidator.requireUid(uid);
		SpacecraftClassV2RestBeanParams spacecraftClassV2RestBeanParams = new SpacecraftClassV2RestBeanParams();
		spacecraftClassV2RestBeanParams.setUid(uid);
		Page<SpacecraftClass> spacecraftClassPage = spacecraftClassRestQuery.query(spacecraftClassV2RestBeanParams);
		SpacecraftClassV2FullResponse spacecraftClassResponse = new SpacecraftClassV2FullResponse();
		spacecraftClassResponse.setSpacecraftClass(spacecraftClassFullRestMapper
				.mapV2Full(Iterables.getOnlyElement(spacecraftClassPage.getContent(), null)));
		return spacecraftClassResponse;
	}

}
