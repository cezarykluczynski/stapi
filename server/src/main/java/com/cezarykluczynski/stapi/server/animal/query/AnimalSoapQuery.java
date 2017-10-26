package com.cezarykluczynski.stapi.server.animal.query;

import com.cezarykluczynski.stapi.client.v1.soap.AnimalBaseRequest;
import com.cezarykluczynski.stapi.client.v1.soap.AnimalFullRequest;
import com.cezarykluczynski.stapi.model.animal.dto.AnimalRequestDTO;
import com.cezarykluczynski.stapi.model.animal.entity.Animal;
import com.cezarykluczynski.stapi.model.animal.repository.AnimalRepository;
import com.cezarykluczynski.stapi.server.animal.mapper.AnimalBaseSoapMapper;
import com.cezarykluczynski.stapi.server.animal.mapper.AnimalFullSoapMapper;
import com.cezarykluczynski.stapi.server.common.mapper.PageMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class AnimalSoapQuery {

	private final AnimalBaseSoapMapper animalBaseSoapMapper;

	private final AnimalFullSoapMapper animalFullSoapMapper;

	private final PageMapper pageMapper;

	private final AnimalRepository animalRepository;

	public AnimalSoapQuery(AnimalBaseSoapMapper animalBaseSoapMapper, AnimalFullSoapMapper animalFullSoapMapper, PageMapper pageMapper,
			AnimalRepository animalRepository) {
		this.animalBaseSoapMapper = animalBaseSoapMapper;
		this.animalFullSoapMapper = animalFullSoapMapper;
		this.pageMapper = pageMapper;
		this.animalRepository = animalRepository;
	}

	public Page<Animal> query(AnimalBaseRequest animalBaseRequest) {
		AnimalRequestDTO animalRequestDTO = animalBaseSoapMapper.mapBase(animalBaseRequest);
		PageRequest pageRequest = pageMapper.fromRequestPageToPageRequest(animalBaseRequest.getPage());
		return animalRepository.findMatching(animalRequestDTO, pageRequest);
	}

	public Page<Animal> query(AnimalFullRequest animalFullRequest) {
		AnimalRequestDTO seriesRequestDTO = animalFullSoapMapper.mapFull(animalFullRequest);
		return animalRepository.findMatching(seriesRequestDTO, pageMapper.getDefaultPageRequest());
	}

}
