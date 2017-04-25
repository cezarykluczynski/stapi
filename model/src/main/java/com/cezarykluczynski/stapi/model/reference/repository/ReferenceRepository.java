package com.cezarykluczynski.stapi.model.reference.repository;

import com.cezarykluczynski.stapi.model.reference.entity.Reference;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReferenceRepository extends JpaRepository<Reference, Long> {

	Optional<Reference> findByUid(String uid);

}
