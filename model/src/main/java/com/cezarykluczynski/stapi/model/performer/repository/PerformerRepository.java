package com.cezarykluczynski.stapi.model.performer.repository;

import com.cezarykluczynski.stapi.model.performer.entity.Performer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PerformerRepository extends JpaRepository<Performer, Long>, PerformerRepositoryCustom {

	Optional<Performer> findByPageTitle(String title);

	Optional<Performer> findByPagePageId(Long pageId);
}
