package com.cezarykluczynski.stapi.server.material.reader;

import com.cezarykluczynski.stapi.client.v1.soap.MaterialBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.MaterialBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.MaterialFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.MaterialFullResponse;
import com.cezarykluczynski.stapi.model.material.entity.Material;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper;
import com.cezarykluczynski.stapi.server.common.reader.BaseReader;
import com.cezarykluczynski.stapi.server.common.reader.FullReader;
import com.cezarykluczynski.stapi.server.common.validator.StaticValidator;
import com.cezarykluczynski.stapi.server.material.mapper.MaterialBaseSoapMapper;
import com.cezarykluczynski.stapi.server.material.mapper.MaterialFullSoapMapper;
import com.cezarykluczynski.stapi.server.material.query.MaterialSoapQuery;
import com.google.common.collect.Iterables;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class MaterialSoapReader implements BaseReader<MaterialBaseRequest, MaterialBaseResponse>,
		FullReader<MaterialFullRequest, MaterialFullResponse> {

	private final MaterialSoapQuery materialSoapQuery;

	private final MaterialBaseSoapMapper materialBaseSoapMapper;

	private final MaterialFullSoapMapper materialFullSoapMapper;

	private final PageMapper pageMapper;

	private final SortMapper sortMapper;

	public MaterialSoapReader(MaterialSoapQuery materialSoapQuery, MaterialBaseSoapMapper materialBaseSoapMapper,
			MaterialFullSoapMapper materialFullSoapMapper, PageMapper pageMapper, SortMapper sortMapper) {
		this.materialSoapQuery = materialSoapQuery;
		this.materialBaseSoapMapper = materialBaseSoapMapper;
		this.materialFullSoapMapper = materialFullSoapMapper;
		this.pageMapper = pageMapper;
		this.sortMapper = sortMapper;
	}

	@Override
	public MaterialBaseResponse readBase(MaterialBaseRequest input) {
		Page<Material> materialPage = materialSoapQuery.query(input);
		MaterialBaseResponse materialResponse = new MaterialBaseResponse();
		materialResponse.setPage(pageMapper.fromPageToSoapResponsePage(materialPage));
		materialResponse.setSort(sortMapper.map(input.getSort()));
		materialResponse.getMaterials().addAll(materialBaseSoapMapper.mapBase(materialPage.getContent()));
		return materialResponse;
	}

	@Override
	public MaterialFullResponse readFull(MaterialFullRequest input) {
		StaticValidator.requireUid(input.getUid());
		Page<Material> materialPage = materialSoapQuery.query(input);
		MaterialFullResponse materialFullResponse = new MaterialFullResponse();
		materialFullResponse.setMaterial(materialFullSoapMapper.mapFull(Iterables.getOnlyElement(materialPage.getContent(), null)));
		return materialFullResponse;
	}

}
