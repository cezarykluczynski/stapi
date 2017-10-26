package com.cezarykluczynski.stapi.server.animal.reader;

import com.cezarykluczynski.stapi.client.v1.rest.model.AnimalBaseResponse;
import com.cezarykluczynski.stapi.client.v1.rest.model.AnimalFullResponse;
import com.cezarykluczynski.stapi.model.animal.entity.Animal;
import com.cezarykluczynski.stapi.server.animal.dto.AnimalRestBeanParams;
import com.cezarykluczynski.stapi.server.animal.mapper.AnimalBaseRestMapper;
import com.cezarykluczynski.stapi.server.animal.mapper.AnimalFullRestMapper;
import com.cezarykluczynski.stapi.server.animal.query.AnimalRestQuery;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper;
import com.cezarykluczynski.stapi.server.common.reader.BaseReader;
import com.cezarykluczynski.stapi.server.common.reader.FullReader;
import com.cezarykluczynski.stapi.server.common.validator.StaticValidator;
import com.google.common.collect.Iterables;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class AnimalRestReader implements BaseReader<AnimalRestBeanParams, AnimalBaseResponse>, FullReader<String, AnimalFullResponse> {

	private AnimalRestQuery animalRestQuery;

	private AnimalBaseRestMapper animalBaseRestMapper;

	private AnimalFullRestMapper animalFullRestMapper;

	private PageMapper pageMapper;

	private final SortMapper sortMapper;

	public AnimalRestReader(AnimalRestQuery animalRestQuery, AnimalBaseRestMapper animalBaseRestMapper, AnimalFullRestMapper animalFullRestMapper,
			PageMapper pageMapper, SortMapper sortMapper) {
		this.animalRestQuery = animalRestQuery;
		this.animalBaseRestMapper = animalBaseRestMapper;
		this.animalFullRestMapper = animalFullRestMapper;
		this.pageMapper = pageMapper;
		this.sortMapper = sortMapper;
	}

	@Override
	public AnimalBaseResponse readBase(AnimalRestBeanParams input) {
		Page<Animal> animalPage = animalRestQuery.query(input);
		AnimalBaseResponse animalResponse = new AnimalBaseResponse();
		animalResponse.setPage(pageMapper.fromPageToRestResponsePage(animalPage));
		animalResponse.setSort(sortMapper.map(input.getSort()));
		animalResponse.getAnimals().addAll(animalBaseRestMapper.mapBase(animalPage.getContent()));
		return animalResponse;
	}

	@Override
	public AnimalFullResponse readFull(String uid) {
		StaticValidator.requireUid(uid);
		AnimalRestBeanParams animalRestBeanParams = new AnimalRestBeanParams();
		animalRestBeanParams.setUid(uid);
		Page<Animal> animalPage = animalRestQuery.query(animalRestBeanParams);
		AnimalFullResponse animalResponse = new AnimalFullResponse();
		animalResponse.setAnimal(animalFullRestMapper.mapFull(Iterables.getOnlyElement(animalPage.getContent(), null)));
		return animalResponse;
	}
}
