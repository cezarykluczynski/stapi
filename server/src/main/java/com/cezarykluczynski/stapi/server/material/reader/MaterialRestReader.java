package com.cezarykluczynski.stapi.server.material.reader;

import com.cezarykluczynski.stapi.client.v1.rest.model.MaterialBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.MaterialFullResponse;
import com.cezarykluczynski.stapi.model.material.entity.Material;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper;
import com.cezarykluczynski.stapi.server.common.reader.BaseReader;
import com.cezarykluczynski.stapi.server.common.reader.FullReader;
import com.cezarykluczynski.stapi.server.common.validator.StaticValidator;
import com.cezarykluczynski.stapi.server.material.dto.MaterialRestBeanParams;
import com.cezarykluczynski.stapi.server.material.mapper.MaterialBaseRestMapper;
import com.cezarykluczynski.stapi.server.material.mapper.MaterialFullRestMapper;
import com.cezarykluczynski.stapi.server.material.query.MaterialRestQuery;
import com.google.common.collect.Iterables;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class MaterialRestReader implements BaseReader<MaterialRestBeanParams, MaterialBaseResponse>, FullReader<String, MaterialFullResponse> {

	private MaterialRestQuery materialRestQuery;

	private MaterialBaseRestMapper materialBaseRestMapper;

	private MaterialFullRestMapper materialFullRestMapper;

	private PageMapper pageMapper;

	private final SortMapper sortMapper;

	public MaterialRestReader(MaterialRestQuery materialRestQuery, MaterialBaseRestMapper materialBaseRestMapper,
			MaterialFullRestMapper materialFullRestMapper, PageMapper pageMapper, SortMapper sortMapper) {
		this.materialRestQuery = materialRestQuery;
		this.materialBaseRestMapper = materialBaseRestMapper;
		this.materialFullRestMapper = materialFullRestMapper;
		this.pageMapper = pageMapper;
		this.sortMapper = sortMapper;
	}

	@Override
	public MaterialBaseResponse readBase(MaterialRestBeanParams input) {
		Page<Material> materialPage = materialRestQuery.query(input);
		MaterialBaseResponse materialResponse = new MaterialBaseResponse();
		materialResponse.setPage(pageMapper.fromPageToRestResponsePage(materialPage));
		materialResponse.setSort(sortMapper.map(input.getSort()));
		materialResponse.getMaterials().addAll(materialBaseRestMapper.mapBase(materialPage.getContent()));
		return materialResponse;
	}

	@Override
	public MaterialFullResponse readFull(String uid) {
		StaticValidator.requireUid(uid);
		MaterialRestBeanParams materialRestBeanParams = new MaterialRestBeanParams();
		materialRestBeanParams.setUid(uid);
		Page<Material> materialPage = materialRestQuery.query(materialRestBeanParams);
		MaterialFullResponse materialResponse = new MaterialFullResponse();
		materialResponse.setMaterial(materialFullRestMapper.mapFull(Iterables.getOnlyElement(materialPage.getContent(), null)));
		return materialResponse;
	}
}
