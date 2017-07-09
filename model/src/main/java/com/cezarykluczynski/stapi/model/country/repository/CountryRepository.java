package com.cezarykluczynski.stapi.model.country.repository;

import com.cezarykluczynski.stapi.model.country.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Long> {
}
