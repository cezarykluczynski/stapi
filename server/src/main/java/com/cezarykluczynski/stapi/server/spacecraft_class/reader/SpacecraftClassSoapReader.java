package com.cezarykluczynski.stapi.server.spacecraft_class.reader;

import com.cezarykluczynski.stapi.client.v1.soap.SpacecraftClassBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.SpacecraftClassBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.SpacecraftClassFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.SpacecraftClassFullResponse;
import com.cezarykluczynski.stapi.model.spacecraft_class.entity.SpacecraftClass;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper;
import com.cezarykluczynski.stapi.server.common.reader.BaseReader;
import com.cezarykluczynski.stapi.server.common.reader.FullReader;
import com.cezarykluczynski.stapi.server.common.validator.StaticValidator;
import com.cezarykluczynski.stapi.server.spacecraft_class.mapper.SpacecraftClassBaseSoapMapper;
import com.cezarykluczynski.stapi.server.spacecraft_class.mapper.SpacecraftClassFullSoapMapper;
import com.cezarykluczynski.stapi.server.spacecraft_class.query.SpacecraftClassSoapQuery;
import com.google.common.collect.Iterables;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class SpacecraftClassSoapReader implements BaseReader<SpacecraftClassBaseRequest, SpacecraftClassBaseResponse>,
		FullReader<SpacecraftClassFullRequest, SpacecraftClassFullResponse> {

	private final SpacecraftClassSoapQuery spacecraftClassSoapQuery;

	private final SpacecraftClassBaseSoapMapper spacecraftClassBaseSoapMapper;

	private final SpacecraftClassFullSoapMapper spacecraftClassFullSoapMapper;

	private final PageMapper pageMapper;

	private final SortMapper sortMapper;

	public SpacecraftClassSoapReader(SpacecraftClassSoapQuery spacecraftClassSoapQuery, SpacecraftClassBaseSoapMapper spacecraftClassBaseSoapMapper,
			SpacecraftClassFullSoapMapper spacecraftClassFullSoapMapper, PageMapper pageMapper, SortMapper sortMapper) {
		this.spacecraftClassSoapQuery = spacecraftClassSoapQuery;
		this.spacecraftClassBaseSoapMapper = spacecraftClassBaseSoapMapper;
		this.spacecraftClassFullSoapMapper = spacecraftClassFullSoapMapper;
		this.pageMapper = pageMapper;
		this.sortMapper = sortMapper;
	}

	@Override
	public SpacecraftClassBaseResponse readBase(SpacecraftClassBaseRequest input) {
		Page<SpacecraftClass> spacecraftClassPage = spacecraftClassSoapQuery.query(input);
		SpacecraftClassBaseResponse spacecraftClassResponse = new SpacecraftClassBaseResponse();
		spacecraftClassResponse.setPage(pageMapper.fromPageToSoapResponsePage(spacecraftClassPage));
		spacecraftClassResponse.setSort(sortMapper.map(input.getSort()));
		spacecraftClassResponse.getSpacecraftClasss().addAll(spacecraftClassBaseSoapMapper.mapBase(spacecraftClassPage.getContent()));
		return spacecraftClassResponse;
	}

	@Override
	public SpacecraftClassFullResponse readFull(SpacecraftClassFullRequest input) {
		StaticValidator.requireUid(input.getUid());
		Page<SpacecraftClass> spacecraftClassPage = spacecraftClassSoapQuery.query(input);
		SpacecraftClassFullResponse spacecraftClassFullResponse = new SpacecraftClassFullResponse();
		spacecraftClassFullResponse.setSpacecraftClass(spacecraftClassFullSoapMapper
				.mapFull(Iterables.getOnlyElement(spacecraftClassPage.getContent(), null)));
		return spacecraftClassFullResponse;
	}

}
