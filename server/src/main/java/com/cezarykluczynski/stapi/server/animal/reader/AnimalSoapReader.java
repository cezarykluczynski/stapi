package com.cezarykluczynski.stapi.server.animal.reader;

import com.cezarykluczynski.stapi.client.v1.soap.AnimalBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.AnimalBaseResponse;
import com.cezarykluczynski.stapi.client.v1.soap.AnimalFullRequest;
import com.cezarykluczynski.stapi.client.v1.soap.AnimalFullResponse;
import com.cezarykluczynski.stapi.model.animal.entity.Animal;
import com.cezarykluczynski.stapi.server.animal.mapper.AnimalBaseSoapMapper;
import com.cezarykluczynski.stapi.server.animal.mapper.AnimalFullSoapMapper;
import com.cezarykluczynski.stapi.server.animal.query.AnimalSoapQuery;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import com.cezarykluczynski.stapi.server.common.mapper.SortMapper;
import com.cezarykluczynski.stapi.server.common.reader.BaseReader;
import com.cezarykluczynski.stapi.server.common.reader.FullReader;
import com.cezarykluczynski.stapi.server.common.validator.StaticValidator;
import com.google.common.collect.Iterables;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class AnimalSoapReader implements BaseReader<AnimalBaseRequest, AnimalBaseResponse>, FullReader<AnimalFullRequest, AnimalFullResponse> {

	private final AnimalSoapQuery animalSoapQuery;

	private final AnimalBaseSoapMapper animalBaseSoapMapper;

	private final AnimalFullSoapMapper animalFullSoapMapper;

	private final PageMapper pageMapper;

	private final SortMapper sortMapper;

	public AnimalSoapReader(AnimalSoapQuery animalSoapQuery, AnimalBaseSoapMapper animalBaseSoapMapper, AnimalFullSoapMapper animalFullSoapMapper,
			PageMapper pageMapper, SortMapper sortMapper) {
		this.animalSoapQuery = animalSoapQuery;
		this.animalBaseSoapMapper = animalBaseSoapMapper;
		this.animalFullSoapMapper = animalFullSoapMapper;
		this.pageMapper = pageMapper;
		this.sortMapper = sortMapper;
	}

	@Override
	public AnimalBaseResponse readBase(AnimalBaseRequest input) {
		Page<Animal> animalPage = animalSoapQuery.query(input);
		AnimalBaseResponse animalResponse = new AnimalBaseResponse();
		animalResponse.setPage(pageMapper.fromPageToSoapResponsePage(animalPage));
		animalResponse.setSort(sortMapper.map(input.getSort()));
		animalResponse.getAnimals().addAll(animalBaseSoapMapper.mapBase(animalPage.getContent()));
		return animalResponse;
	}

	@Override
	public AnimalFullResponse readFull(AnimalFullRequest input) {
		StaticValidator.requireUid(input.getUid());
		Page<Animal> animalPage = animalSoapQuery.query(input);
		AnimalFullResponse animalFullResponse = new AnimalFullResponse();
		animalFullResponse.setAnimal(animalFullSoapMapper.mapFull(Iterables.getOnlyElement(animalPage.getContent(), null)));
		return animalFullResponse;
	}

}
