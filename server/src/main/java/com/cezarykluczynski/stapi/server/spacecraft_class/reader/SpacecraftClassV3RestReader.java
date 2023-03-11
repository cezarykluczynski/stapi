package com.cezarykluczynski.stapi.server.spacecraft_class.reader;

import com.cezarykluczynski.stapi.client.rest.model.SpacecraftClassV3FullResponse;
import com.cezarykluczynski.stapi.model.spacecraft_class.entity.SpacecraftClass;
import com.cezarykluczynski.stapi.server.common.reader.FullReader;
import com.cezarykluczynski.stapi.server.common.validator.StaticValidator;
import com.cezarykluczynski.stapi.server.spacecraft_class.dto.SpacecraftClassV2RestBeanParams;
import com.cezarykluczynski.stapi.server.spacecraft_class.mapper.SpacecraftClassFullRestMapper;
import com.cezarykluczynski.stapi.server.spacecraft_class.query.SpacecraftClassRestQuery;
import com.google.common.collect.Iterables;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class SpacecraftClassV3RestReader implements FullReader<SpacecraftClassV3FullResponse> {

	private final SpacecraftClassRestQuery spacecraftClassRestQuery;

	private final SpacecraftClassFullRestMapper spacecraftClassFullRestMapper;

	public SpacecraftClassV3RestReader(SpacecraftClassRestQuery spacecraftClassRestQuery,
			SpacecraftClassFullRestMapper spacecraftClassFullRestMapper) {
		this.spacecraftClassRestQuery = spacecraftClassRestQuery;
		this.spacecraftClassFullRestMapper = spacecraftClassFullRestMapper;
	}

	@Override
	public SpacecraftClassV3FullResponse readFull(String uid) {
		StaticValidator.requireUid(uid);
		SpacecraftClassV2RestBeanParams spacecraftClassV3RestBeanParams = new SpacecraftClassV2RestBeanParams();
		spacecraftClassV3RestBeanParams.setUid(uid);
		Page<SpacecraftClass> spacecraftClassPage = spacecraftClassRestQuery.query(spacecraftClassV3RestBeanParams);
		SpacecraftClassV3FullResponse spacecraftClassResponse = new SpacecraftClassV3FullResponse();
		spacecraftClassResponse.setSpacecraftClass(spacecraftClassFullRestMapper
				.mapV3Full(Iterables.getOnlyElement(spacecraftClassPage.getContent(), null)));
		return spacecraftClassResponse;
	}

}
