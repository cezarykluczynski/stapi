package com.cezarykluczynski.stapi.server.animal.query;

import com.cezarykluczynski.stapi.model.animal.dto.AnimalRequestDTO;
import com.cezarykluczynski.stapi.model.animal.entity.Animal;
import com.cezarykluczynski.stapi.model.animal.repository.AnimalRepository;
import com.cezarykluczynski.stapi.server.animal.dto.AnimalRestBeanParams;
import com.cezarykluczynski.stapi.server.animal.mapper.AnimalBaseRestMapper;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class AnimalRestQuery {

	private final AnimalBaseRestMapper animalBaseRestMapper;

	private final PageMapper pageMapper;

	private final AnimalRepository animalRepository;

	public AnimalRestQuery(AnimalBaseRestMapper animalBaseRestMapper, PageMapper pageMapper, AnimalRepository animalRepository) {
		this.animalBaseRestMapper = animalBaseRestMapper;
		this.pageMapper = pageMapper;
		this.animalRepository = animalRepository;
	}

	public Page<Animal> query(AnimalRestBeanParams animalRestBeanParams) {
		AnimalRequestDTO animalRequestDTO = animalBaseRestMapper.mapBase(animalRestBeanParams);
		PageRequest pageRequest = pageMapper.fromPageSortBeanParamsToPageRequest(animalRestBeanParams);
		return animalRepository.findMatching(animalRequestDTO, pageRequest);
	}

}
