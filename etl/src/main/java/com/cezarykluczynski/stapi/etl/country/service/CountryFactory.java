package com.cezarykluczynski.stapi.etl.country.service;

import com.cezarykluczynski.stapi.etl.country.dto.CountryDTO;
import com.cezarykluczynski.stapi.model.common.service.UidGenerator;
import com.cezarykluczynski.stapi.model.country.entity.Country;
import com.cezarykluczynski.stapi.model.country.repository.CountryRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CountryFactory {

	private final CountryRepository countryRepository;

	private final UidGenerator uidGenerator;

	public CountryFactory(CountryRepository countryRepository, UidGenerator uidGenerator) {
		this.countryRepository = countryRepository;
		this.uidGenerator = uidGenerator;
	}

	public synchronized Country create(CountryDTO countryDTO) {
		Optional<Country> countryOptional = countryRepository.findByIso31661Alpha2Code(countryDTO.getCode());
		if (countryOptional.isPresent()) {
			return countryOptional.get();
		} else {
			Country country = new Country();
			country.setName(countryDTO.getName());
			country.setIso31661Alpha2Code(countryDTO.getCode());
			country.setUid(uidGenerator.generateForCountry(countryDTO.getCode()));
			countryRepository.save(country);
			return country;
		}
	}

}
