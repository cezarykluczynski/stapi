package com.cezarykluczynski.stapi.server.spacecraft_class.reader;

import com.cezarykluczynski.stapi.client.v1.rest.model.SpacecraftClassBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.SpacecraftClassFullResponse;
import com.cezarykluczynski.stapi.model.spacecraft_class.entity.SpacecraftClass;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper;
import com.cezarykluczynski.stapi.server.common.reader.BaseReader;
import com.cezarykluczynski.stapi.server.common.reader.FullReader;
import com.cezarykluczynski.stapi.server.common.validator.StaticValidator;
import com.cezarykluczynski.stapi.server.spacecraft_class.dto.SpacecraftClassRestBeanParams;
import com.cezarykluczynski.stapi.server.spacecraft_class.mapper.SpacecraftClassBaseRestMapper;
import com.cezarykluczynski.stapi.server.spacecraft_class.mapper.SpacecraftClassFullRestMapper;
import com.cezarykluczynski.stapi.server.spacecraft_class.query.SpacecraftClassRestQuery;
import com.google.common.collect.Iterables;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class SpacecraftClassRestReader implements BaseReader<SpacecraftClassRestBeanParams, SpacecraftClassBaseResponse>,
		FullReader<String, SpacecraftClassFullResponse> {

	private final SpacecraftClassRestQuery spacecraftClassRestQuery;

	private final SpacecraftClassBaseRestMapper spacecraftClassBaseRestMapper;

	private final SpacecraftClassFullRestMapper spacecraftClassFullRestMapper;

	private final PageMapper pageMapper;

	private final SortMapper sortMapper;

	public SpacecraftClassRestReader(SpacecraftClassRestQuery spacecraftClassRestQuery, SpacecraftClassBaseRestMapper spacecraftClassBaseRestMapper,
			SpacecraftClassFullRestMapper spacecraftClassFullRestMapper, PageMapper pageMapper, SortMapper sortMapper) {
		this.spacecraftClassRestQuery = spacecraftClassRestQuery;
		this.spacecraftClassBaseRestMapper = spacecraftClassBaseRestMapper;
		this.spacecraftClassFullRestMapper = spacecraftClassFullRestMapper;
		this.pageMapper = pageMapper;
		this.sortMapper = sortMapper;
	}

	@Override
	public SpacecraftClassBaseResponse readBase(SpacecraftClassRestBeanParams input) {
		Page<SpacecraftClass> spacecraftClassPage = spacecraftClassRestQuery.query(input);
		SpacecraftClassBaseResponse spacecraftClassResponse = new SpacecraftClassBaseResponse();
		spacecraftClassResponse.setPage(pageMapper.fromPageToRestResponsePage(spacecraftClassPage));
		spacecraftClassResponse.setSort(sortMapper.map(input.getSort()));
		spacecraftClassResponse.getSpacecraftClasses().addAll(spacecraftClassBaseRestMapper.mapBase(spacecraftClassPage.getContent()));
		return spacecraftClassResponse;
	}

	@Override
	public SpacecraftClassFullResponse readFull(String uid) {
		StaticValidator.requireUid(uid);
		SpacecraftClassRestBeanParams spacecraftClassRestBeanParams = new SpacecraftClassRestBeanParams();
		spacecraftClassRestBeanParams.setUid(uid);
		Page<SpacecraftClass> spacecraftClassPage = spacecraftClassRestQuery.query(spacecraftClassRestBeanParams);
		SpacecraftClassFullResponse spacecraftClassResponse = new SpacecraftClassFullResponse();
		spacecraftClassResponse.setSpacecraftClass(spacecraftClassFullRestMapper
				.mapFull(Iterables.getOnlyElement(spacecraftClassPage.getContent(), null)));
		return spacecraftClassResponse;
	}

}
