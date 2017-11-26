package com.cezarykluczynski.stapi.model.consent.repository;

import com.cezarykluczynski.stapi.model.consent.entity.Consent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConsentRepository extends JpaRepository<Consent, Long>, ConsentRepositoryCustom {
}
