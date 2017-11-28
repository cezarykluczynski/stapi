package com.cezarykluczynski.stapi.auth.account.api;

import com.cezarykluczynski.stapi.auth.account.dto.ConsentDTO;
import com.cezarykluczynski.stapi.auth.account.mapper.ConsentDTOMapper;
import com.cezarykluczynski.stapi.model.consent.repository.ConsentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConsentApi {

	private final ConsentRepository consentRepository;

	private final ConsentDTOMapper consentDTOMapper;

	public ConsentApi(ConsentRepository consentRepository, ConsentDTOMapper consentDTOMapper) {
		this.consentRepository = consentRepository;
		this.consentDTOMapper = consentDTOMapper;
	}

	public List<ConsentDTO> provideAll() {
		return consentRepository.findAll()
				.stream()
				.map(consentDTOMapper::map)
				.collect(Collectors.toList());
	}

}
