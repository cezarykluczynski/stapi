package com.cezarykluczynski.stapi.model.country.repository;

import com.cezarykluczynski.stapi.model.country.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CountryRepository extends JpaRepository<Country, Long> {

	Optional<Country> findByIso31661Alpha2Code(String code);

}
