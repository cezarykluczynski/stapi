package com.cezarykluczynski.stapi.server.spacecraft.reader;

import com.cezarykluczynski.stapi.client.v1.rest.model.SpacecraftBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.SpacecraftFullResponse;
import com.cezarykluczynski.stapi.model.spacecraft.entity.Spacecraft;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper;
import com.cezarykluczynski.stapi.server.common.reader.BaseReader;
import com.cezarykluczynski.stapi.server.common.reader.FullReader;
import com.cezarykluczynski.stapi.server.common.validator.StaticValidator;
import com.cezarykluczynski.stapi.server.spacecraft.dto.SpacecraftRestBeanParams;
import com.cezarykluczynski.stapi.server.spacecraft.mapper.SpacecraftBaseRestMapper;
import com.cezarykluczynski.stapi.server.spacecraft.mapper.SpacecraftFullRestMapper;
import com.cezarykluczynski.stapi.server.spacecraft.query.SpacecraftRestQuery;
import com.google.common.collect.Iterables;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class SpacecraftRestReader implements BaseReader<SpacecraftRestBeanParams, SpacecraftBaseResponse>,
		FullReader<String, SpacecraftFullResponse> {

	private final SpacecraftRestQuery spacecraftRestQuery;

	private final SpacecraftBaseRestMapper spacecraftBaseRestMapper;

	private final SpacecraftFullRestMapper spacecraftFullRestMapper;

	private final PageMapper pageMapper;

	private final SortMapper sortMapper;

	public SpacecraftRestReader(SpacecraftRestQuery spacecraftRestQuery, SpacecraftBaseRestMapper spacecraftBaseRestMapper,
			SpacecraftFullRestMapper spacecraftFullRestMapper, PageMapper pageMapper, SortMapper sortMapper) {
		this.spacecraftRestQuery = spacecraftRestQuery;
		this.spacecraftBaseRestMapper = spacecraftBaseRestMapper;
		this.spacecraftFullRestMapper = spacecraftFullRestMapper;
		this.pageMapper = pageMapper;
		this.sortMapper = sortMapper;
	}

	@Override
	public SpacecraftBaseResponse readBase(SpacecraftRestBeanParams input) {
		Page<Spacecraft> spacecraftPage = spacecraftRestQuery.query(input);
		SpacecraftBaseResponse spacecraftResponse = new SpacecraftBaseResponse();
		spacecraftResponse.setPage(pageMapper.fromPageToRestResponsePage(spacecraftPage));
		spacecraftResponse.setSort(sortMapper.map(input.getSort()));
		spacecraftResponse.getSpacecrafts().addAll(spacecraftBaseRestMapper.mapBase(spacecraftPage.getContent()));
		return spacecraftResponse;
	}

	@Override
	public SpacecraftFullResponse readFull(String uid) {
		StaticValidator.requireUid(uid);
		SpacecraftRestBeanParams spacecraftRestBeanParams = new SpacecraftRestBeanParams();
		spacecraftRestBeanParams.setUid(uid);
		Page<Spacecraft> spacecraftPage = spacecraftRestQuery.query(spacecraftRestBeanParams);
		SpacecraftFullResponse spacecraftResponse = new SpacecraftFullResponse();
		spacecraftResponse.setSpacecraft(spacecraftFullRestMapper.mapFull(Iterables.getOnlyElement(spacecraftPage.getContent(), null)));
		return spacecraftResponse;
	}

}
