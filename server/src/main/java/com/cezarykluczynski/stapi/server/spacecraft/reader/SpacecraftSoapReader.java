package com.cezarykluczynski.stapi.server.spacecraft.reader;

import com.cezarykluczynski.stapi.client.v1.soap.SpacecraftBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.SpacecraftBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.SpacecraftFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.SpacecraftFullResponse;
import com.cezarykluczynski.stapi.model.spacecraft.entity.Spacecraft;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper;
import com.cezarykluczynski.stapi.server.common.reader.BaseReader;
import com.cezarykluczynski.stapi.server.common.reader.FullReader;
import com.cezarykluczynski.stapi.server.common.validator.StaticValidator;
import com.cezarykluczynski.stapi.server.spacecraft.mapper.SpacecraftBaseSoapMapper;
import com.cezarykluczynski.stapi.server.spacecraft.mapper.SpacecraftFullSoapMapper;
import com.cezarykluczynski.stapi.server.spacecraft.query.SpacecraftSoapQuery;
import com.google.common.collect.Iterables;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class SpacecraftSoapReader implements BaseReader<SpacecraftBaseRequest, SpacecraftBaseResponse>,
		FullReader<SpacecraftFullRequest, SpacecraftFullResponse> {

	private final SpacecraftSoapQuery spacecraftSoapQuery;

	private final SpacecraftBaseSoapMapper spacecraftBaseSoapMapper;

	private final SpacecraftFullSoapMapper spacecraftFullSoapMapper;

	private final PageMapper pageMapper;

	private final SortMapper sortMapper;

	public SpacecraftSoapReader(SpacecraftSoapQuery spacecraftSoapQuery, SpacecraftBaseSoapMapper spacecraftBaseSoapMapper,
			SpacecraftFullSoapMapper spacecraftFullSoapMapper, PageMapper pageMapper, SortMapper sortMapper) {
		this.spacecraftSoapQuery = spacecraftSoapQuery;
		this.spacecraftBaseSoapMapper = spacecraftBaseSoapMapper;
		this.spacecraftFullSoapMapper = spacecraftFullSoapMapper;
		this.pageMapper = pageMapper;
		this.sortMapper = sortMapper;
	}

	@Override
	public SpacecraftBaseResponse readBase(SpacecraftBaseRequest input) {
		Page<Spacecraft> spacecraftPage = spacecraftSoapQuery.query(input);
		SpacecraftBaseResponse spacecraftResponse = new SpacecraftBaseResponse();
		spacecraftResponse.setPage(pageMapper.fromPageToSoapResponsePage(spacecraftPage));
		spacecraftResponse.setSort(sortMapper.map(input.getSort()));
		spacecraftResponse.getSpacecrafts().addAll(spacecraftBaseSoapMapper.mapBase(spacecraftPage.getContent()));
		return spacecraftResponse;
	}

	@Override
	public SpacecraftFullResponse readFull(SpacecraftFullRequest input) {
		StaticValidator.requireUid(input.getUid());
		Page<Spacecraft> spacecraftPage = spacecraftSoapQuery.query(input);
		SpacecraftFullResponse spacecraftFullResponse = new SpacecraftFullResponse();
		spacecraftFullResponse.setSpacecraft(spacecraftFullSoapMapper.mapFull(Iterables.getOnlyElement(spacecraftPage.getContent(), null)));
		return spacecraftFullResponse;
	}

}
