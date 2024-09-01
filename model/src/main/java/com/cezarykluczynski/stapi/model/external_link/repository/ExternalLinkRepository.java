package com.cezarykluczynski.stapi.model.external_link.repository;

import com.cezarykluczynski.stapi.model.external_link.entity.ExternalLink;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExternalLinkRepository extends JpaRepository<ExternalLink, Long> {
}
